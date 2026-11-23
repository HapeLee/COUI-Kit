package com.itos.coui.navigation3

import android.graphics.Path
import android.os.SystemClock
import android.view.MotionEvent
import android.view.VelocityTracker
import android.view.animation.PathInterpolator
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.FloatSpringSpec
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animate
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.runtime.withFrameNanos
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.lerp
import androidx.compose.ui.unit.IntSize
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.scene.Scene
import androidx.navigation3.scene.SceneState
import androidx.navigationevent.DirectNavigationEventInput
import androidx.navigationevent.NavigationEvent
import androidx.navigationevent.NavigationEventDispatcher
import androidx.navigationevent.NavigationEventDispatcherOwner
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.max
import kotlin.math.min
import kotlin.math.sign
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

private const val BACK_SCALE_FACTOR = 0.85f
private const val BACK_ENTERING_INITIAL_SCALE = 0.95f
private val backGestureEasing = CubicBezierEasing(0.1f, 0.1f, 0f, 1f)
private val backCommitInterpolator = PathInterpolator(
    Path().apply {
        moveTo(0f, 0f)
        cubicTo(0.05f, 0f, 0.133333f, 0.06f, 0.166666f, 0.4f)
        cubicTo(0.208333f, 0.82f, 0.25f, 1f, 1f, 1f)
    }
)
private val backFlingSpring = FloatSpringSpec(dampingRatio = 0.75f, stiffness = 200f)

private fun List<NavEntry<*>>.hasSameKeys(other: List<NavEntry<*>>): Boolean =
    this === other || (size == other.size && indices.all { this[it].contentKey == other[it].contentKey })

private fun NavigationEvent.withClampedProgress(): NavigationEvent {
    val safeProgress = if (progress.isFinite()) progress.coerceIn(0f, 1f) else 0f
    if (safeProgress == progress) return this
    return NavigationEvent(
        swipeEdge = swipeEdge,
        progress = safeProgress,
        touchX = touchX,
        touchY = touchY,
        frameTimeMillis = frameTimeMillis,
    )
}

internal data class BackFrame(
    val closing: Rect,
    val entering: Rect,
    val closingAlpha: Float = 1f,
    val scrimFraction: Float = 1f,
)

private fun Rect.scaleCentered(scale: Float): Rect =
    Rect(
        left = center.x - width * scale / 2f,
        top = center.y - height * scale / 2f,
        right = center.x + width * scale / 2f,
        bottom = center.y + height * scale / 2f,
    )

private fun backGestureFrame(
    bounds: Rect,
    margin: Float,
    enteringOffset: Float,
    cornerRadius: Float,
    swipeEdge: Int,
    progress: Float,
    touchDeltaY: Float,
): BackFrame {
    val p = backGestureEasing.transform(progress.coerceIn(0f, 1f))
    val closingTarget = bounds.scaleCentered(BACK_SCALE_FACTOR).let { centered ->
        if (swipeEdge == NavigationEvent.EDGE_RIGHT) centered
        else centered.translate(bounds.right - centered.right - margin, 0f)
    }
    val closing = lerp(bounds, closingTarget, p)
    val yRatio = (abs(touchDeltaY) / (bounds.height / 2f)).coerceIn(0f, 1f)
    val yOffset = max(0f, (bounds.height - closing.height) / 2f - margin) *
        (1f - (1f - yRatio) * (1f - yRatio)) * sign(touchDeltaY)
    // 固定采用参考源码中启用 entering 预缩放的分支, 不读取 ROM 的隐藏 flag。
    val initialScale = min(
        BACK_ENTERING_INITIAL_SCALE,
        ((bounds.height - 2f * cornerRadius) / bounds.height).coerceIn(0f, 1f),
    )
    val enteringStart = bounds.scaleCentered(initialScale).let { scaled ->
        scaled.translate(-max(scaled.width * (1f - BACK_SCALE_FACTOR), enteringOffset), 0f)
    }
    return BackFrame(
        closing = closing.translate(0f, yOffset),
        entering = lerp(enteringStart, enteringStart.scaleCentered(BACK_SCALE_FACTOR), p)
            .translate(0f, yOffset),
    )
}

private fun backCommitFrame(
    start: BackFrame,
    bounds: Rect,
    enteringOffset: Float,
    timeFraction: Float,
    flingVelocity: Float,
): BackFrame {
    // ValueAnimator 默认先做 AccelerateDecelerate, 再由 Default 子类应用 EMPHASIZED。
    val q = ((1.0 - cos(Math.PI * timeFraction)) / 2.0).toFloat()
    val e = backCommitInterpolator.getInterpolation(q)
    val flingScale = min(
        backFlingSpring.getValueFromNanos(
            playTimeNanos = (timeFraction * CouiBackPreset.AOSP.backReleaseDurationMillis * 1_000_000L).toLong(),
            initialValue = 1f,
            targetValue = 1f,
            initialVelocity = -flingVelocity / 100f,
        ),
        1f,
    )
    return BackFrame(
        closing = lerp(
            start.closing,
            bounds.translate(start.closing.left + enteringOffset, 0f),
            e,
        ).scaleCentered(flingScale),
        entering = lerp(start.entering, bounds, e).scaleCentered(flingScale),
        closingAlpha = max(1f - 5f * q, 0f),
        scrimFraction = 1f - q,
    )
}

internal class BackSession<T : Any>(
    val sceneState: SceneState<T>,
    val firstEvent: NavigationEvent,
    val bounds: Rect,
    val margin: Float,
    val enteringOffset: Float,
    val cornerRadius: Float,
) {
    val closingKey = sceneState.currentScene.key
    val enteringKey = sceneState.previousScenes.last().key
    val velocityTracker: VelocityTracker = VelocityTracker.obtain()
    private val downTime = firstEvent.frameTimeMillis.takeIf { it > 0 } ?: SystemClock.uptimeMillis()
    var committing = false
    var cancelling = false
    var rawProgress = 0f
    var touchDeltaY = 0f
    var frame by mutableStateOf(gestureFrame())
    var lastRenderedFrame: BackFrame? = null

    fun gestureFrame(): BackFrame = backGestureFrame(
        bounds, margin, enteringOffset, cornerRadius, firstEvent.swipeEdge,
        rawProgress, touchDeltaY,
    )

    fun update(event: NavigationEvent) {
        rawProgress = if (event.progress.isFinite()) event.progress.coerceIn(0f, 1f) else 0f
        touchDeltaY = if (event.touchY.isFinite() && firstEvent.touchY.isFinite()) {
            event.touchY - firstEvent.touchY
        } else {
            0f
        }
        frame = gestureFrame()
        val time = event.frameTimeMillis.takeIf { it > 0 } ?: SystemClock.uptimeMillis()
        val movement = MotionEvent.obtain(
            downTime,
            time,
            MotionEvent.ACTION_MOVE,
            backGestureEasing.transform(rawProgress),
            0f,
            0,
        )
        velocityTracker.addMovement(movement)
        movement.recycle()
    }

    fun flingVelocity(): Float {
        velocityTracker.computeCurrentVelocity(1000)
        val velocity = velocityTracker.xVelocity.takeIf { it.isFinite() } ?: 0f
        var result = velocity * 100f * (1f - BACK_SCALE_FACTOR) * 2f
        if (backGestureEasing.transform(rawProgress) < 0.1f) result = max(result, 120f)
        return result.coerceIn(0f, 1000f)
    }
}

/**
 * 会话结束后继续绘制的最终几何。
 *
 * 只参与绘制, 不参与输入拦截与导航排队; 目标 scene 回报稳定或交接目标不再是当前页时清除。
 */
private data class BackFrameHold(
    val frame: BackFrame,
    val closingKey: Any,
    val enteringKey: Any,
    val committed: Boolean,
) {
    /** 回报稳定后才允许清除保持帧的目标 scene。 */
    val settledKey: Any
        get() = if (committed) enteringKey else closingKey
}

/**
 * 将系统手势与 Nav3 的 scene 保留周期分开, 提交期间不让 Nav3 按剩余进度提前清理旧页。
 * 独立 dispatcher 只驱动显示状态, 不接收系统输入, 不承载业务返回回调。
 */
internal class BackMotionState<T : Any>(
    private val scope: CoroutineScope,
    private val usePredictiveTransition: (Scene<T>, Scene<T>) -> Boolean,
) : NavigationEventDispatcherOwner {
    override val navigationEventDispatcher = NavigationEventDispatcher()
    private val displayInput = DirectNavigationEventInput()
    private var animationJob: Job? = null
    private var pendingStartJob: Job? = null
    private var gestureSource: SceneState<T>? = null
    private var gestureInvalidated = false
    private val settledScenes = mutableStateMapOf<Any, Boolean>()
    private var handoffHold by mutableStateOf<BackFrameHold?>(null)
    private val pendingBackEvents = ArrayDeque<NavigationEvent?>()
    private var pendingGestureStart: NavigationEvent? = null
    private var pendingGestureEvent: NavigationEvent? = null
    private var animationStyle = CouiBackPreset.CHATGPT
    var sceneState: SceneState<T>? = null
    var onBack: () -> Unit = {}
    var size = IntSize.Zero
    var margin = 0f
    var enteringOffset = 0f
    var cornerRadius = 0f
    var session by mutableStateOf<BackSession<T>?>(null)
        private set
    private var directPredictiveActive = false
    var completedScenePair by mutableStateOf<Pair<Any, Any>?>(null)
        private set
    var cancelledScenePair by mutableStateOf<Pair<Any, Any>?>(null)
        private set

    /** 当前绘制使用的几何来源: 跟手会话优先, 会话结束后由保持帧延续。 */
    fun renderFrame(): BackFrame? = session?.frame ?: handoffHold?.frame

    /** 该 scene 是否位于关闭侧, 即当前页。 */
    fun isClosingScene(key: Any): Boolean =
        (session?.closingKey ?: handoffHold?.closingKey) == key

    /** 该 scene 是否位于进入侧, 即上一页。 */
    fun isEnteringScene(key: Any): Boolean =
        (session?.enteringKey ?: handoffHold?.enteringKey) == key

    /** 保持帧期望稳定回报的目标 scene, 没有保持帧时为 null。 */
    fun holdSettledKey(): Any? = handoffHold?.settledKey

    init {
        navigationEventDispatcher.addInput(displayInput)
    }

    /** 切换动画风格前终止并清理残留手势状态, 保留 settledScenes 避免切回 AOSP 时卡顿。 */
    fun updateStyle(newStyle: CouiBackPreset) {
        if (animationStyle == newStyle) return
        abort()
        completedScenePair = null
        cancelledScenePair = null
        animationStyle = newStyle
    }

    /** 取消直接由 Nav3 驱动的预测性返回手势。 */
    private fun cancelDirectPredictive() {
        if (!directPredictiveActive) return
        directPredictiveActive = false
        displayInput.backCancelled()
    }

    fun onBackStarted(event: NavigationEvent) {
        gestureSource = sceneState
        gestureInvalidated = false
        start(event)
    }

    private fun start(event: NavigationEvent) {
        if (animationStyle == CouiBackPreset.CHATGPT) {
            cancelDirectPredictive()
            if (event.swipeEdge == NavigationEvent.EDGE_NONE) return
            val scenes = sceneState ?: return
            if (scenes.previousScenes.isEmpty()) return
            if (!usePredictiveTransition(scenes.currentScene, scenes.previousScenes.last())) return
            directPredictiveActive = true
            displayInput.backStarted(event.withClampedProgress())
            return
        }
        if (session?.let { it.committing || it.cancelling } == true) {
            pendingGestureStart = event.takeUnless { it.swipeEdge == NavigationEvent.EDGE_NONE }
            pendingGestureEvent = pendingGestureStart
            return
        }
        cancelPendingStart()
        animationJob?.cancel()
        finishSession(committed = false)
        if (event.swipeEdge == NavigationEvent.EDGE_NONE) return
        val scenes = sceneState ?: return
        if (scenes.previousScenes.isEmpty() || size.width <= 0 || size.height <= 0) return
        if (!usePredictiveTransition(scenes.currentScene, scenes.previousScenes.last())) return
        if (settledScenes[scenes.currentScene.key] != true) {
            pendingGestureStart = event
            pendingGestureEvent = event
            pendingStartJob = scope.launch {
                snapshotFlow { settledScenes[scenes.currentScene.key] == true }.first { it }
                val latest = pendingGestureEvent ?: return@launch
                pendingStartJob = null
                pendingGestureStart = null
                pendingGestureEvent = null
                if (sceneState?.currentScene?.key == scenes.currentScene.key) {
                    startSession(scenes, event, latest)
                }
            }
            return
        }
        startSession(scenes, event, event)
    }

    private fun startSession(
        scenes: SceneState<T>,
        firstEvent: NavigationEvent,
        latestEvent: NavigationEvent,
    ) {
        // 新会话接管绘制, 上一轮交接的保持帧与转场屏蔽同时失效。
        completedScenePair = null
        cancelledScenePair = null
        handoffHold = null
        session = BackSession(
            scenes, firstEvent, Rect(0f, 0f, size.width.toFloat(), size.height.toFloat()),
            margin, enteringOffset, cornerRadius,
        ).also { it.update(latestEvent) }
        // 固定 seek 到中间只负责挂载两页; 系统进度只在几何模型中使用一次。
        displayInput.backStarted(NavigationEvent(swipeEdge = firstEvent.swipeEdge, progress = 0.5f))
    }

    fun progress(event: NavigationEvent) {
        if (gestureInvalidated) return
        if (animationStyle == CouiBackPreset.CHATGPT) {
            if (directPredictiveActive) {
                displayInput.backProgressed(event.withClampedProgress())
            }
            return
        }
        if (pendingStartJob != null ||
            session?.let { it.committing || it.cancelling } == true
        ) {
            pendingGestureEvent = event
        } else {
            session?.update(event)
        }
    }

    fun onBackCancelled() {
        gestureSource = null
        gestureInvalidated = false
        cancel()
    }

    private fun cancel() {
        if (animationStyle == CouiBackPreset.CHATGPT) {
            cancelDirectPredictive()
            return
        }
        cancelPendingStart()
        val active = session ?: return
        if (active.committing || active.cancelling) return
        active.cancelling = true
        animationJob?.cancel()
        animationJob = scope.launch {
            // Android 已回传取消弹簧的进度时通常已到零, 不再叠加第二次回位。
            if (active.rawProgress > 0f) {
                animate(
                    initialValue = active.rawProgress,
                    targetValue = 0f,
                    animationSpec = spring(dampingRatio = 1f, stiffness = 1500f),
                ) { value, _ ->
                    active.rawProgress = value.coerceIn(0f, 1f)
                    active.frame = active.gestureFrame()
                }
            }
            if (session === active) {
                handOffSession(committed = false)
                animationJob = null
                drainPendingBack()
            }
        }
    }

    fun onBackCompleted(enabled: Boolean) {
        // 系统仍会向起手时的 handler 发送完成事件。已失效的手势不能弹出新的页面。
        val shouldPop = enabled && !gestureInvalidated
        gestureSource = null
        gestureInvalidated = false
        if (shouldPop) complete()
    }

    private fun complete() {
        if (animationStyle == CouiBackPreset.CHATGPT) {
            if (directPredictiveActive) {
                directPredictiveActive = false
                // 先通知 displayInput 回到 Idle, 再弹出当前页, 让 SeekableTransitionState 从当前进度顺滑续播。
                displayInput.backCompleted()
            }
            sceneState?.let(::popScene)
            return
        }
        if (session?.let { it.committing || it.cancelling } == true) {
            pendingBackEvents.addLast(pendingGestureEvent)
            pendingGestureStart = null
            pendingGestureEvent = null
            return
        }
        val active = session
        if (active == null) {
            cancelPendingStart()
            animationJob?.cancel()
            finishSession(committed = false)
            sceneState?.let(::popScene)
            return
        }
        animationJob?.cancel()
        active.committing = true
        val start = active.lastRenderedFrame ?: active.frame
        val velocity = active.flingVelocity()
        active.frame = start
        // 业务栈只弹出一次; NavDisplay 暂时持有不可变的旧 SceneState 直至提交完成。
        popScene(active.sceneState)
        animationJob = scope.launch {
            animate(
                initialValue = 0f,
                targetValue = 1f,
                animationSpec = tween(CouiBackPreset.AOSP.backReleaseDurationMillis, easing = LinearEasing),
            ) { value, _ ->
                active.frame = backCommitFrame(
                    start, active.bounds, active.enteringOffset, value, velocity,
                )
            }
            if (session === active) {
                handOffSession(committed = true)
                // 等待实际出栈后的 composition 更新, 再把排队的返回交给下一页。
                withFrameNanos { }
                animationJob = null
                drainPendingBack()
            }
        }
    }

    private fun popScene(scenes: SceneState<T>) {
        if (scenes.currentScene.previousEntries.isEmpty()) return
        repeat((scenes.entries.size - scenes.currentScene.previousEntries.size).coerceAtLeast(0)) {
            onBack()
        }
    }

    private suspend fun drainPendingBack() {
        while (pendingBackEvents.isNotEmpty() && session == null) {
            val scenes = sceneState ?: break
            if (scenes.previousScenes.isEmpty()) {
                pendingBackEvents.clear()
                break
            }
            val event = pendingBackEvents.removeFirst()
            if (event != null && event.swipeEdge != NavigationEvent.EDGE_NONE) {
                start(
                    NavigationEvent(
                        swipeEdge = event.swipeEdge,
                        touchY = event.touchY,
                        frameTimeMillis = event.frameTimeMillis,
                    )
                )
                complete()
                if (session != null) return
            } else {
                popScene(scenes)
            }
            withFrameNanos { }
        }
        val pendingStart = pendingGestureStart
        val pendingLatest = pendingGestureEvent
        pendingGestureStart = null
        pendingGestureEvent = null
        if (pendingStart != null && pendingLatest != null && session == null) {
            start(pendingStart)
            progress(pendingLatest)
        }
    }

    fun reconcile(scenes: SceneState<T>) {
        val source = gestureSource
        if (source != null && source !== scenes &&
            (source.currentScene.key != scenes.currentScene.key ||
                source.previousScenes.lastOrNull()?.key != scenes.previousScenes.lastOrNull()?.key ||
                !source.entries.hasSameKeys(scenes.entries) ||
                !source.currentScene.previousEntries.hasSameKeys(scenes.currentScene.previousEntries))
        ) {
            abort()
            return
        }
        val active = session ?: return
        if (scenes === active.sceneState) return
        val expectedPop = active.committing &&
            scenes.currentScene.key == active.enteringKey &&
            scenes.entries.hasSameKeys(active.sceneState.currentScene.previousEntries)
        if (!expectedPop && !scenes.entries.hasSameKeys(active.sceneState.entries)) {
            // 外部导航替换了目标, 结束视觉会话但不追加业务出栈。
            abort()
        }
    }

    fun clearCompletedScene() {
        completedScenePair = null
        handoffHold = null
    }

    fun resize(newSize: IntSize) {
        if (size != IntSize.Zero && size != newSize) abort()
        size = newSize
    }

    fun reportSceneState(key: Any, settled: Boolean) {
        settledScenes[key] = settled
        val hold = handoffHold ?: return
        if (settled && key == hold.settledKey) handoffHold = null
    }

    fun forgetScene(key: Any) {
        settledScenes.remove(key)
    }

    private fun cancelPendingStart() {
        pendingStartJob?.cancel()
        pendingStartJob = null
        pendingGestureStart = null
        pendingGestureEvent = null
    }

    /**
     * 结束私有显示手势并立即释放会话。
     *
     * 输入拦截与返回排队只覆盖跟手与提交 / 取消动画阶段, 交接后的画面由保持帧延续,
     * 不等待 Nav3 的转场状态, 避免其状态残留导致会话无法释放。
     */
    private fun handOffSession(committed: Boolean) {
        val active = session ?: return
        finishDisplayGesture(active, committed)
        handoffHold = BackFrameHold(active.frame, active.closingKey, active.enteringKey, committed)
        releaseSession(active)
    }

    private fun finishSession(committed: Boolean) {
        val active = session ?: return
        finishDisplayGesture(active, committed)
        releaseSession(active)
    }

    private fun releaseSession(active: BackSession<T>) {
        active.velocityTracker.recycle()
        session = null
    }

    private fun finishDisplayGesture(active: BackSession<T>, committed: Boolean) {
        if (committed) {
            completedScenePair = active.closingKey to active.enteringKey
            displayInput.backProgressed(NavigationEvent(progress = 1f))
            displayInput.backCompleted()
        } else {
            cancelledScenePair = active.closingKey to active.enteringKey
            displayInput.backProgressed(NavigationEvent(progress = 0f))
            displayInput.backCancelled()
        }
    }

    private fun abort() {
        invalidateGesture()
        cancelPendingStart()
        animationJob?.cancel()
        pendingBackEvents.clear()
        pendingGestureStart = null
        pendingGestureEvent = null
        handoffHold = null
        finishSession(committed = session?.committing == true)
        cancelDirectPredictive()
    }

    fun disableBackInput() {
        invalidateGesture()
        // 提交期间业务栈已更新，只释放输入并让正在提交的动画完成。
        pendingBackEvents.clear()
        cancelPendingStart()
        if (session?.committing != true && session?.cancelling != true) cancel()
    }

    private fun invalidateGesture() {
        if (gestureSource != null) {
            gestureSource = null
            gestureInvalidated = true
        }
    }

    fun dispose() {
        abort()
        navigationEventDispatcher.dispose()
    }
}

package com.itos.coui.navigation3

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.view.RoundedCorner
import android.view.View
import androidx.compose.animation.AnimatedContentTransitionScope.SlideDirection
import androidx.compose.animation.EnterExitState
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.get
import androidx.navigation3.scene.Scene
import androidx.navigation3.scene.SceneDecoratorStrategy
import androidx.navigation3.scene.SceneInfo
import androidx.navigation3.scene.SceneStrategy
import androidx.navigation3.scene.rememberNavigationEventState
import androidx.navigation3.scene.rememberSceneState
import androidx.navigation3.ui.LocalNavAnimatedContentScope
import androidx.navigation3.ui.NavDisplay
import androidx.navigationevent.NavigationEvent
import androidx.navigationevent.NavigationEventHandler
import androidx.navigationevent.compose.LocalNavigationEventDispatcherOwner
import androidx.navigationevent.compose.NavigationBackHandler
import com.itos.coui.shape.CouiShape
import com.itos.coui.theme.CouiTheme

private val BACK_DISPLAY_MARGIN = 8.dp
private val NAVIGATION_TRANSLATION = 96.dp

/**
 * 在宿主提供的 Nav3 条目之间应用 [preset]，每次 [onBack] 移除栈顶一个条目。
 * [backHandlerEnabled] 只控制本容器的系统返回输入。分栏可通过 [usePredictiveTransition]
 * 关闭指定页面之间的整页预览，提交后仍执行正常返回。
 * [entries] 至少包含一个条目。自定义非浮层 Scene 应在本容器内使用唯一的 key。
 * [contentColor] 与 [backgroundColor] 用于计算遮罩深浅。
 * [deviceCornerShape] 根据设备半径创建卡片轮廓；系统未报告半径时使用 [fallbackShape]。
 */
@Composable
fun <T : Any> PredictiveBackNavDisplay(
    modifier: Modifier = Modifier,
    entries: List<NavEntry<T>>,
    preset: CouiBackPreset,
    backHandlerEnabled: Boolean = true,
    sceneStrategies: List<SceneStrategy<T>> = emptyList(),
    usePredictiveTransition: (from: Scene<T>, to: Scene<T>) -> Boolean = { _, _ -> true },
    backgroundColor: Color = CouiTheme.colorScheme.background,
    contentColor: Color = CouiTheme.colorScheme.onSurface,
    fallbackShape: Shape = CouiShape(24.dp),
    deviceCornerShape: (Dp) -> Shape = { CouiShape(it) },
    onBack: () -> Unit,
) {
    val currentAnimationStyle by rememberUpdatedState(preset)
    val currentPredictiveTransition by rememberUpdatedState(usePredictiveTransition)
    val density = LocalDensity.current
    val navigationTranslationPx = with(density) { NAVIGATION_TRANSLATION.roundToPx() }
    val scope = rememberCoroutineScope()
    val backMotionState = remember(scope) {
        BackMotionState<T>(scope) { from, to -> currentPredictiveTransition(from.original(), to.original()) }
    }
    val context = LocalContext.current
    val view = LocalView.current
    val radius = remember(context, view, view.rootWindowInsets) { getDeviceCornerRadiusPx(context, view) }
    val resolvedShape = remember(radius, density, fallbackShape, deviceCornerShape) {
        if (radius > 0) deviceCornerShape(with(density) { radius.toDp() }) else fallbackShape
    }
    val cornerShape = rememberUpdatedState(resolvedShape)
    val scrimAlpha = rememberUpdatedState(
        if (backgroundColor.luminance() < contentColor.luminance()) 0.8f else 0.2f
    )
    val transitionSpec = remember(navigationTranslationPx) {
        defaultNavTransitionSpec<T>(navigationTranslationPx)
    }
    val popTransitionSpec = remember(navigationTranslationPx) {
        backCloseTransitionSpec<T>(navigationTranslationPx)
    }
    val sceneDecoratorStrategies = remember(backMotionState) {
        listOf(SceneDecoratorStrategy<T> { scene -> BackMotionScene(scene, backMotionState, cornerShape, scrimAlpha) })
    }
    val sceneState = rememberSceneState(
        entries = entries,
        sceneStrategies = sceneStrategies,
        sceneDecoratorStrategies = sceneDecoratorStrategies,
        onBack = onBack,
    )
    SideEffect {
        backMotionState.sceneState = sceneState
        backMotionState.onBack = onBack
        backMotionState.updateStyle(preset)
        backMotionState.margin = with(density) { BACK_DISPLAY_MARGIN.toPx() }
        backMotionState.enteringOffset = with(density) { NAVIGATION_TRANSLATION.toPx() }
        backMotionState.cornerRadius = radius.toFloat()
        backMotionState.reconcile(sceneState)
        val completed = backMotionState.completedScenePair
        val holdKey = backMotionState.holdSettledKey() ?: completed?.second
        if (holdKey != null && sceneState.currentScene.key != holdKey) {
            // 交接目标已不是当前页时, 不得继续沿用已退出页面的透明状态与保持帧。
            backMotionState.clearCompletedScene()
        }
    }

    val dispatcher = checkNotNull(LocalNavigationEventDispatcherOwner.current)
        .navigationEventDispatcher
    val handler = remember(backMotionState) {
        object : NavigationEventHandler<SceneInfo<T>>(
            initialInfo = SceneInfo(sceneState.currentScene),
            isBackEnabled = false,
        ) {
            override fun onBackStarted(event: NavigationEvent) {
                backMotionState.onBackStarted(event)
            }

            override fun onBackProgressed(event: NavigationEvent) {
                if (event.swipeEdge != NavigationEvent.EDGE_NONE) backMotionState.progress(event)
            }

            override fun onBackCancelled() {
                backMotionState.onBackCancelled()
            }

            override fun onBackCompleted() {
                backMotionState.onBackCompleted(isBackEnabled)
            }
        }
    }
    SideEffect {
        if (!backHandlerEnabled) backMotionState.disableBackInput()
        handler.isBackEnabled = backHandlerEnabled && sceneState.previousScenes.isNotEmpty()
        handler.setInfo(
            currentInfo = SceneInfo(sceneState.currentScene),
            backInfo = sceneState.previousScenes.map { SceneInfo(it) },
        )
    }
    DisposableEffect(dispatcher, handler) {
        dispatcher.addHandler(handler)
        onDispose { handler.remove() }
    }
    DisposableEffect(backMotionState) {
        onDispose { backMotionState.dispose() }
    }

    val displaySceneState = backMotionState.session?.sceneState ?: sceneState
    val displayEventState = rememberNavigationEventState(displaySceneState)
    // 只给显示状态的 handler 提供私有 dispatcher; 页面内弹窗/IME 的 handler 仍接收系统事件。
    CompositionLocalProvider(LocalNavigationEventDispatcherOwner provides backMotionState) {
        NavigationBackHandler(state = displayEventState, onBackCompleted = {})
    }
    NavDisplay(
        sceneState = displaySceneState,
        navigationEventState = displayEventState,
        modifier = modifier.fillMaxSize()
            .background(backgroundColor)
            .onSizeChanged(backMotionState::resize)
            .pointerInput(backMotionState) {
                awaitPointerEventScope {
                    while (true) {
                        val event = awaitPointerEvent(PointerEventPass.Initial)
                        if (backMotionState.session != null) {
                            event.changes.forEach { it.consume() }
                        }
                    }
                }
            },
        transitionSpec = {
            when (currentAnimationStyle) {
                CouiBackPreset.AOSP -> {
                    val cancelled = backMotionState.cancelledScenePair
                    if (cancelled?.first == initialState.key && cancelled.second == targetState.key) {
                        EnterTransition.None togetherWith ExitTransition.None
                    } else {
                        transitionSpec(this)
                    }
                }
                CouiBackPreset.CHATGPT -> chatGptTransition(SlideDirection.Start)
            }
        },
        popTransitionSpec = {
            when (currentAnimationStyle) {
                CouiBackPreset.AOSP -> {
                    val completed = backMotionState.completedScenePair
                    if (completed?.first == initialState.key && completed.second == targetState.key) {
                        EnterTransition.None togetherWith ExitTransition.None
                    } else {
                        popTransitionSpec(this)
                    }
                }
                CouiBackPreset.CHATGPT -> chatGptTransition(SlideDirection.End)
            }
        },
        predictivePopTransitionSpec = { _ ->
            when (currentAnimationStyle) {
                CouiBackPreset.AOSP -> predictiveBackContentTransform
                CouiBackPreset.CHATGPT -> chatGptTransition(SlideDirection.End)
            }
        },
    )
}

private fun <T : Any> Scene<T>.original(): Scene<T> = if (this is BackMotionScene<T>) delegate else this

private class BackMotionScene<T : Any>(
    val delegate: Scene<T>,
    private val backMotionState: BackMotionState<T>,
    private val cornerShape: State<Shape>,
    private val scrimAlpha: State<Float>,
) : Scene<T> {
    override val key: Any = delegate.key
    override val entries: List<NavEntry<T>> = delegate.entries
    override val previousEntries: List<NavEntry<T>> = delegate.previousEntries
    override val metadata: Map<String, Any> = buildMap {
        putAll(delegate.metadata)
        // 跟手预览由预设驱动。AOSP 的固定 seek 仅用于同时挂载两个页面。
        remove(NavDisplay.PredictivePopTransitionKey.toString())
        delegate.metadata[NavDisplay.TransitionKey]?.let { hostTransition ->
            putAll(NavDisplay.transitionSpec {
                val pair = backMotionState.cancelledScenePair
                if (pair?.first == initialState.key && pair.second == targetState.key) null
                else hostTransition(this)
            })
        }
        delegate.metadata[NavDisplay.PopTransitionKey]?.let { hostPop ->
            putAll(NavDisplay.popTransitionSpec {
                val pair = backMotionState.completedScenePair
                if (pair?.first == initialState.key && pair.second == targetState.key) null
                else hostPop(this)
            })
        }
    }
    override val content: @Composable () -> Unit = {
        val transition = LocalNavAnimatedContentScope.current.transition
        val lifecycleState by LocalLifecycleOwner.current.lifecycle.currentStateFlow.collectAsState()
        // Nav3 在父转场稳定后恢复 RESUMED; seek 取消后子转场 currentState 可能残留 PostExit。
        val settled = lifecycleState == Lifecycle.State.RESUMED &&
            transition.targetState == EnterExitState.Visible && !transition.isRunning
        SideEffect { backMotionState.reportSceneState(key, settled) }
        DisposableEffect(key, backMotionState) {
            onDispose { backMotionState.forgetScene(key) }
        }
        Box(
            Modifier.fillMaxSize().drawWithContent {
                drawContent()
                val frame = backMotionState.renderFrame()
                // 在变换层外记帧, 避免跟手进度额外使页面内容层失效。
                val active = backMotionState.session
                if (active?.closingKey == key) active.lastRenderedFrame = frame
                if (frame != null && backMotionState.isEnteringScene(key)) {
                    // 遮罩覆盖整个未变换的区域, 位于两页之间, 不跟着上一页一起缩放。
                    drawRect(Color.Black.copy(alpha = scrimAlpha.value * frame.scrimFraction))
                }
            }
        ) {
            Box(
                Modifier.fillMaxSize()
                    .graphicsLayer {
                        val frame = backMotionState.renderFrame()
                        val isClosing = backMotionState.isClosingScene(key)
                        val isEntering = backMotionState.isEnteringScene(key)
                        val rect = when {
                            isClosing -> frame?.closing
                            isEntering -> frame?.entering
                            else -> null
                        }
                        // 先在页面原坐标内完成背景替换, 避免滤镜 patch 随分数变换产生接缝。
                        compositingStrategy = if (rect != null) {
                            CompositingStrategy.Offscreen
                        } else {
                            CompositingStrategy.Auto
                        }
                        clip = rect != null
                        shape = cornerShape.value
                        transformOrigin = TransformOrigin(0f, 0f)
                        translationX = rect?.left ?: 0f
                        translationY = rect?.top ?: 0f
                        scaleX = if (rect != null && size.width > 0f) rect.width / size.width else 1f
                        scaleY = if (rect != null && size.height > 0f) rect.height / size.height else 1f
                        alpha = when {
                            isClosing -> frame?.closingAlpha ?: 1f
                            backMotionState.completedScenePair?.first == key -> 0f
                            else -> 1f
                        }
                    }
            ) {
                delegate.content()
            }
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is BackMotionScene<*>) return false
        return delegate == other.delegate && backMotionState == other.backMotionState
    }

    override fun hashCode(): Int = 31 * delegate.hashCode() + backMotionState.hashCode()
}

/**
 * 读取设备真实屏幕圆角半径。
 *
 * @param context 用于读取旧系统的系统圆角资源。
 * @param view 用于读取当前窗口的 RoundedCorner 信息。
 * @return 设备圆角半径像素值, 读取失败时返回 0。
 */
private fun getDeviceCornerRadiusPx(
    context: Context,
    view: View,
): Int {
    val windowInsetsRadius = getWindowInsetsCornerRadiusPx(view)
    if (windowInsetsRadius > 0) {
        return windowInsetsRadius
    }

    return getSystemCornerRadiusPx(context)
}

/**
 * 通过 Android 12+ RoundedCorner API 读取当前窗口圆角。
 *
 * @param view 用于访问 rootWindowInsets 的当前 Compose 宿主 View。
 * @return 系统公开 API 报告的圆角半径像素值, 不可用时返回 0。
 */
private fun getWindowInsetsCornerRadiusPx(view: View): Int {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S) return 0
    val insets = view.rootWindowInsets ?: return 0
    return insets.getRoundedCorner(RoundedCorner.POSITION_TOP_LEFT)?.radius
        ?: insets.getRoundedCorner(RoundedCorner.POSITION_TOP_RIGHT)?.radius
        ?: insets.getRoundedCorner(RoundedCorner.POSITION_BOTTOM_LEFT)?.radius
        ?: insets.getRoundedCorner(RoundedCorner.POSITION_BOTTOM_RIGHT)?.radius
        ?: 0
}

/**
 * 读取旧系统暴露的屏幕圆角资源。
 *
 * @param context 用于访问系统 Resources。
 * @return 系统资源中的圆角半径像素值, 不存在时返回 0。
 */
@SuppressLint("DiscouragedApi")
private fun getSystemCornerRadiusPx(context: Context): Int {
    val resources = context.resources
    val bottomCornerRadiusId = resources.getIdentifier(
        "rounded_corner_radius_bottom",
        "dimen",
        "android"
    )
    if (bottomCornerRadiusId > 0) {
        return resources.getDimensionPixelSize(bottomCornerRadiusId)
    }

    val cornerRadiusId = resources.getIdentifier(
        "rounded_corner_radius",
        "dimen",
        "android"
    )
    return if (cornerRadiusId > 0) {
        resources.getDimensionPixelSize(cornerRadiusId)
    } else {
        0
    }
}

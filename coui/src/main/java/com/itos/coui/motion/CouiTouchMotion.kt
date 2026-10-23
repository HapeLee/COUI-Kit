package com.itos.coui.motion

import android.view.animation.PathInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.spring
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.unit.IntSize
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.math.abs
import kotlin.math.exp
import kotlin.math.ln
import kotlin.math.max
import kotlin.math.min
import kotlin.math.sign

/** COUIDeformAnimatorUnit / COUIRubberAnimatorUnit 的共享计算, 输入归各组件持有。 */
class CouiTouchMotion(
    private val scope: CoroutineScope,
    private val density: Float,
    private val deformationEnabled: Boolean = true,
) {
    private val jobs = mutableMapOf<Animatable<Float, AnimationVector1D>, Job>()
    private val stretchDistance = Math.rint((28f * density).toDouble()).toFloat()
    private val deformDistance = 150f * density
    private var rubberOrigin = Offset.Zero
    private var deformOrigin = Offset.Zero
    private var targetSize = IntSize.Zero
    private var maximumScale = 1.2f
    private var dragging = false
    private var moved = false

    val scaleX = Animatable(1f, visibilityThreshold = 0.0005f)
    val scaleY = Animatable(1f, visibilityThreshold = 0.0005f)
    val translationX = Animatable(0f, visibilityThreshold = 0.5f)
    val translationY = Animatable(0f, visibilityThreshold = 0.5f)
    var coordinates: LayoutCoordinates? = null

    fun begin(position: Offset, size: IntSize) {
        cancel()
        targetSize = size
        maximumScale = maximumScale(size)
        rubberOrigin = position - Offset(
            inverseRubber(translationX.value, 0.05f, stretchDistance),
            inverseRubber(translationY.value, 0.05f, stretchDistance),
        )
        val xDominant = scaleX.value >= scaleY.value
        val currentScale = if (xDominant) scaleX.value else scaleY.value
        val distance = inverseRubber(
            min((currentScale - 1f) / (maximumScale - 1f), 1f) * deformDistance,
            0.55f, deformDistance,
        )
        deformOrigin = position - if (xDominant) Offset(distance, 0f) else Offset(0f, distance)
        dragging = true
        moved = translationX.value != 0f || translationY.value != 0f ||
            scaleX.value != 1f || scaleY.value != 1f
    }

    fun switchPointer(previous: Offset?, next: Offset) {
        if (previous == null) return
        val adjustment = next - previous
        rubberOrigin += adjustment
        deformOrigin += adjustment
    }

    fun move(position: Offset) {
        if (!dragging) return
        val rubberDelta = position - rubberOrigin
        if (abs(rubberDelta.x) >= 5f || abs(rubberDelta.y) >= 5f) {
            moved = true
            animate(translationX, rubber(rubberDelta.x, 0.05f, stretchDistance), 0.15f, 0.15f, 0.5f)
            animate(translationY, rubber(rubberDelta.y, 0.05f, stretchDistance), 0.15f, 0.15f, 0.5f)
        }
        if (!deformationEnabled) return
        val delta = position - deformOrigin
        val x = abs(delta.x)
        val y = abs(delta.y)
        if (x < 5f && y < 5f) return
        moved = true
        val sx = 1f + (maximumScale - 1f) * rubber(x, 0.55f, deformDistance) / deformDistance
        val sy = 1f + (maximumScale - 1f) * rubber(y, 0.55f, deformDistance) / deformDistance
        val xWeight = x / (x + y)
        val yWeight = y / (x + y)
        val targetX = when {
            y == 0f -> sx
            x == 0f -> 1f / sy
            else -> exp(ln(1f / sy) * yWeight + ln(sx) * xWeight)
        }
        val targetY = when {
            y == 0f -> 1f / sx
            x == 0f -> sy
            else -> exp(yWeight * ln(sy) + xWeight * ln(1f / sx))
        }
        animate(scaleX, targetX, 0.15f, 0.15f, 0.0005f)
        animate(scaleY, targetY, 0.15f, 0.15f, 0.0005f)
    }

    fun release(velocity: Offset) {
        if (!dragging) return
        dragging = false
        if (!moved) return
        val smallSide = 56f * density
        val largeSide = 100f * density
        val area = targetSize.width.toFloat() * targetSize.height
        val fraction = ((area - smallSide * smallSide) /
            (largeSide * largeSide - smallSide * smallSide)).coerceIn(0f, 1f)
        val response = 0.42f + (0.45f - 0.42f) * HandUpAreaInterpolator.getInterpolation(fraction)
        releaseAxis(translationX, 0f, velocity.x, response, 0.5f)
        releaseAxis(translationY, 0f, velocity.y, response, 0.5f)
        if (!deformationEnabled) return
        releaseAxis(scaleX, 1f, velocity.x, response, 0.0005f)
        releaseAxis(scaleY, 1f, velocity.y, response, 0.0005f)
    }

    fun cancel() {
        jobs.values.forEach { it.cancel() }
        jobs.clear()
        dragging = false
    }

    private fun releaseAxis(
        axis: Animatable<Float, AnimationVector1D>, target: Float, velocity: Float,
        response: Float, threshold: Float,
    ) {
        val rest = abs(axis.value - target)
        if (target == 1f && rest < 0.002f) {
            jobs.remove(axis)?.cancel()
            jobs[axis] = scope.launch { axis.snapTo(1f) }
            return
        }
        // 两个厂商 unit 均传 true, 手抬归一化距离是 maxStretchDistance 而非形变上限。
        val initialVelocity = (velocity / max(28f * density, 1f) * rest).coerceIn(-1000f, 1000f)
        val bounce = max(0.65f, min(abs(initialVelocity) / 1000f, 1f))
        animate(axis, target, response, bounce, threshold, initialVelocity)
    }

    private fun animate(
        axis: Animatable<Float, AnimationVector1D>, target: Float, response: Float,
        bounce: Float, threshold: Float, velocity: Float = axis.velocity,
    ) {
        val stiffness = Math.pow(2.0 * Math.PI / response.toDouble(), 2.0).toFloat()
        // animateTo 会先保存上一帧时间再接续; MOVE 提前 cancel 会清空时间并反复停在首帧。
        jobs[axis] = scope.launch {
            axis.animateTo(target, spring(1f - bounce, stiffness, threshold), initialVelocity = velocity)
        }
    }

    private fun maximumScale(size: IntSize): Float {
        val area = size.width.toFloat() * size.height
        val shortSide = min(size.width, size.height)
        if (shortSide == 0 || area <= 38416f) return 1.2f
        val aspect = max(size.width, size.height).toFloat() / shortSide
        val decay = min(0.015f * (aspect - 1f) + 0.35f * ln(area / 38416f), 1f)
        return max(1.03f, 1.03f + (1.2f - 1.03f) * (1f - decay))
    }

    private fun rubber(distance: Float, ratio: Float, maximum: Float): Float =
        if (maximum <= 0f) 0f else
            sign(distance) * (1f - 1f / (abs(distance) * ratio / maximum + 1f)) * maximum

    private fun inverseRubber(value: Float, ratio: Float, maximum: Float): Float {
        if (maximum <= 0f) return value
        val magnitude = abs(value)
        if (magnitude >= maximum || ratio <= 0f) return sign(value) * maximum
        return sign(value) * magnitude * maximum / (ratio * (maximum - magnitude))
    }

    private companion object {
        val HandUpAreaInterpolator = PathInterpolator(0.55026454f, 0f, 0.8915344f, 0.18783069f)
    }
}

package com.itos.coui.motion

import android.view.animation.PathInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.spring
import androidx.compose.ui.unit.IntSize
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.math.PI
import kotlin.math.max
import kotlin.math.min

/** OlivelinkDefault 普通按压, 与 rubber/deform 的缩放相乘, 不替换其状态。 */
class CouiPressMotion(private val scope: CoroutineScope, private val density: Float) {
    val scale = Animatable(1f, visibilityThreshold = 0.001f)
    private var candy = false

    fun press(size: IntSize) {
        if (size.width <= 0 || size.height <= 0) return
        val width = size.width.toFloat()
        val height = size.height.toFloat()
        val minArea = 20f * density * 20f * density
        val maxArea = 150f * density * 150f * density
        val areaProgress = ((width * height - minArea) / (maxArea - minArea)).coerceIn(0f, 1f)
        val areaScale = 0.85f + 0.12f * Interpolator.getInterpolation(areaProgress)
        val aspect = max(width, height) / min(width, height)
        candy = aspect > 2f
        val aspectScale = 0.85f + 0.15f * Interpolator.getInterpolation(((aspect - 2f) / 13f).coerceIn(0f, 1f))
        val target = (if (candy) (areaScale + aspectScale) * 0.5f else areaScale).coerceIn(0.4f, 1f)
        scope.launch {
            scale.animateTo(target, spring(1f, stiffness(if (candy) 0.3f else 0.25f), 0.001f), initialVelocity = 0f)
        }
    }

    fun release() {
        scope.launch {
            scale.animateTo(1f, spring(if (candy) 0.5f else 0.4f, stiffness(0.5f), 0.0001f))
        }
    }

    private fun stiffness(response: Float): Float = ((2.0 * PI / response) * (2.0 * PI / response)).toFloat()

    private companion object {
        val Interpolator = PathInterpolator(0f, 0f, 0f, 1f)
    }
}

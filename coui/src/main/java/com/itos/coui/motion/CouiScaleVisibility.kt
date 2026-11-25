package com.itos.coui.motion

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer

/**
 * 为同一绘制主体提供显隐动效，配合 [Modifier.couiScaleVisibility] 或在自绘绘制链直接读取
 * [CouiScaleVisibilityState.scale] 与 [CouiScaleVisibilityState.alpha]。交互由宿主按 [visible] 控制。
 */
@Composable
fun rememberCouiScaleVisibility(visible: Boolean): CouiScaleVisibilityState {
    val state = remember { CouiScaleVisibilityState(visible) }
    DisposableEffect(state) {
        onDispose { state.dispose() }
    }
    SideEffect { state.updateTarget(visible) }
    return state
}

/** 在绘制阶段以主体中心同时应用双轴 [CouiScaleVisibilityState.scale] 与整体 [CouiScaleVisibilityState.alpha]。 */
fun Modifier.couiScaleVisibility(state: CouiScaleVisibilityState): Modifier =
    graphicsLayer {
        val scale = state.scale.value
        scaleX = scale
        scaleY = scale
        alpha = state.alpha.value
    }

/** 缩放倍率、整体透明度和退场期间的内容保留状态。 */
@Stable
class CouiScaleVisibilityState internal constructor(initialVisible: Boolean) {
    private val scaleSpring = CouiSpring(if (initialVisible) 1f else HiddenScale, MinimumVisibleChange)
    private val alphaSpring = CouiSpring(if (initialVisible) 1f else 0f, MinimumVisibleChange)
    private var targetVisible = initialVisible

    val scale: State<Float> get() = scaleSpring.state
    val alpha: State<Float> get() = alphaSpring.state

    /** 从显示开始到退场淡出完成期间保留内容。 */
    var isPresent by mutableStateOf(initialVisible)
        private set

    internal fun updateTarget(visible: Boolean) {
        if (visible == targetVisible) return
        targetVisible = visible
        if (visible) {
            scaleSpring.snapTo(HiddenScale)
            alphaSpring.snapTo(1f)
            isPresent = true
            scaleSpring.animateTo(1f, response = ScaleResponse, bounce = ScaleBounce)
        } else {
            // 退场沿用当前缩放，重新启动弹簧，避免入场速度继续放大主体。
            scaleSpring.cancelComplete()
            scaleSpring.animateTo(HiddenScale, response = ScaleResponse, bounce = ScaleBounce)
            alphaSpring.animateTo(0f, response = ExitAlphaResponse, onEnd = { isPresent = false })
        }
    }

    internal fun dispose() {
        scaleSpring.cancel()
        alphaSpring.cancel()
    }

    private companion object {
        const val HiddenScale = 0.8f
        const val ScaleResponse = 0.35f
        const val ScaleBounce = 0.35f
        const val ExitAlphaResponse = 0.2f
        const val MinimumVisibleChange = 1f / 256f
    }
}

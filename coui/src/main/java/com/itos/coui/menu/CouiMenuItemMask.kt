package com.itos.coui.menu

import android.animation.ArgbEvaluator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationState
import androidx.compose.animation.core.animateTo
import androidx.compose.animation.core.spring
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.HoverInteraction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

/** 普通菜单项沿用 COUIMaskEffectDrawable 的色值、弹簧和短按最低进度。 */
@Composable
fun couiMenuItemMask(
    interactionSource: MutableInteractionSource,
    enabled: Boolean,
    sharedMaskVisible: Boolean,
    dark: Boolean,
): Modifier {
    val pressColor = if (dark) 0x33FFFFFF else 0x1F000000
    val hoverColor = if (dark) 0x26FFFFFF else 0x14000000
    val evaluator = remember { ArgbEvaluator() }
    var pressProgress by remember { mutableStateOf(AnimationState(0f)) }
    val hoverProgress = remember { Animatable(0f) }
    // COUISpringForce: stiffness = (2π / response)², dampingRatio = 1 - bounce。
    val animation = remember { spring<Float>(dampingRatio = 1f, stiffness = 438.6491f) }

    LaunchedEffect(interactionSource, enabled) {
        pressProgress = AnimationState(0f)
        hoverProgress.snapTo(0f)
        if (!enabled) return@LaunchedEffect
        val presses = mutableSetOf<PressInteraction.Press>()
        val hovers = mutableSetOf<HoverInteraction.Enter>()
        var pressAnimation: Job? = null
        var hoverAnimation: Job? = null
        interactionSource.interactions.collect { interaction ->
            when (interaction) {
                is PressInteraction.Press,
                is PressInteraction.Release,
                is PressInteraction.Cancel -> {
                    when (interaction) {
                        is PressInteraction.Press -> presses.add(interaction)
                        is PressInteraction.Release -> presses.remove(interaction.press)
                        is PressInteraction.Cancel -> presses.remove(interaction.press)
                    }
                    val wasRunning = pressProgress.isRunning
                    pressAnimation?.cancel()
                    pressAnimation = launch {
                        if (presses.isNotEmpty()) {
                            pressProgress.animateTo(1f, animation, sequentialAnimation = wasRunning)
                        } else {
                            if (wasRunning && pressProgress.value <= 0.7f) {
                                // 厂商在首个超过阈值的实际帧反向, 保留该帧进度和速度。
                                pressProgress.animateTo(1f, animation, sequentialAnimation = true) {
                                    if (value > 0.7f) cancelAnimation()
                                }
                            }
                            pressProgress.animateTo(0f, animation, sequentialAnimation = wasRunning)
                        }
                    }
                }
                is HoverInteraction.Enter,
                is HoverInteraction.Exit -> {
                    when (interaction) {
                        is HoverInteraction.Enter -> hovers.add(interaction)
                        is HoverInteraction.Exit -> hovers.remove(interaction.enter)
                    }
                    val velocity = hoverProgress.velocity
                    hoverAnimation?.cancel()
                    hoverAnimation = launch {
                        hoverProgress.animateTo(
                            if (hovers.isNotEmpty()) 1f else 0f,
                            animation,
                            initialVelocity = velocity,
                        )
                    }
                }
                else -> Unit
            }
        }
    }
    return Modifier
        .hoverable(interactionSource, enabled)
        .drawBehind {
            // 共享预选框独占交互高亮; 保留状态收集, 退场后恢复当前悬停状态。
            if (!sharedMaskVisible) {
                // StateEffectAnimator 从透明黑到目标色使用 ArgbEvaluator, 同步保留 RGB 插值。
                drawRect(Color(evaluator.evaluate(hoverProgress.value.coerceIn(0f, 1f), 0, hoverColor) as Int))
                drawRect(Color(evaluator.evaluate(pressProgress.value.coerceIn(0f, 1f), 0, pressColor) as Int))
            }
        }
}

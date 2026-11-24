package com.itos.coui.navigation3

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.AnimatedContentTransitionScope.SlideDirection
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.ui.unit.IntOffset
import androidx.navigation3.scene.Scene

// 普通前进转场时长，单位毫秒。
private const val ANIMATION_DURATION = 300
// 普通返回退出动画时长，单位毫秒。
private const val BACK_CLOSE_ANIMATION_DURATION = 300

private val navigationFadeAnimationSpec = tween<Float>(
    durationMillis = ANIMATION_DURATION,
    easing = FastOutSlowInEasing
)

private val navigationSlideAnimationSpec = tween<IntOffset>(
    durationMillis = ANIMATION_DURATION,
    easing = FastOutSlowInEasing
)

private val backCloseEasing = Easing { fraction ->
    val remaining = 1f - fraction.coerceIn(0f, 1f)
    1f - remaining * remaining * remaining
}

private val backCloseFadeAnimationSpec = tween<Float>(
    durationMillis = BACK_CLOSE_ANIMATION_DURATION,
    easing = backCloseEasing
)

private val backCloseSlideAnimationSpec = tween<IntOffset>(
    durationMillis = BACK_CLOSE_ANIMATION_DURATION,
    easing = backCloseEasing
)

internal fun <T : Any> defaultNavTransitionSpec(
    translationPx: Int,
): (AnimatedContentTransitionScope<Scene<T>>) -> ContentTransform = {
    (slideInHorizontally(
        animationSpec = navigationSlideAnimationSpec,
        initialOffsetX = { translationPx }
    ) + fadeIn(animationSpec = navigationFadeAnimationSpec)) togetherWith
        slideOutHorizontally(
            animationSpec = navigationSlideAnimationSpec,
            targetOffsetX = { -translationPx }
        )
}

internal fun <T : Any> backCloseTransitionSpec(
    closeTranslationPx: Int,
): (AnimatedContentTransitionScope<Scene<T>>) -> ContentTransform = {
    slideInHorizontally(
        animationSpec = backCloseSlideAnimationSpec,
        initialOffsetX = { -closeTranslationPx }
    ) togetherWith
        (slideOutHorizontally(
            animationSpec = backCloseSlideAnimationSpec,
            targetOffsetX = { closeTranslationPx }
        ) + fadeOut(animationSpec = backCloseFadeAnimationSpec))
}

// 这里只为 Nav3 保留两个真实 scene; 像素变换及提交时钟由 BackMotionState 控制。
internal val predictiveBackContentTransform =
    fadeIn(initialAlpha = 1f, animationSpec = tween(1)) togetherWith
        fadeOut(targetAlpha = 1f, animationSpec = tween(1))

private val ChatGptFastOutSlowIn = CubicBezierEasing(0.4f, 0f, 0.2f, 1.0f)
private val ChatGptLinearOutSlowIn = CubicBezierEasing(0f, 0f, 0.2f, 1.0f)
private val ChatGptFastOutLinearIn = CubicBezierEasing(0.4f, 0f, 1.0f, 1.0f)

private val ChatGptEnterFadeSpec = tween<Float>(
    durationMillis = 210,
    delayMillis = 100,
    easing = ChatGptLinearOutSlowIn,
)
private val ChatGptExitFadeSpec = tween<Float>(
    durationMillis = 100,
    delayMillis = 0,
    easing = ChatGptFastOutLinearIn,
)
private val ChatGptSlideSpec = tween<IntOffset>(
    durationMillis = CouiBackPreset.CHATGPT.backReleaseDurationMillis,
    delayMillis = 0,
    easing = ChatGptFastOutSlowIn,
)
private val ChatGptSharedAxisOffset: (Int) -> Int = { fullWidth -> fullWidth / 3 }

internal fun <T : Any> AnimatedContentTransitionScope<Scene<T>>.chatGptTransition(
    direction: SlideDirection,
): ContentTransform =
    (slideIntoContainer(direction, ChatGptSlideSpec, ChatGptSharedAxisOffset) +
        fadeIn(ChatGptEnterFadeSpec)) togetherWith
        (slideOutOfContainer(direction, ChatGptSlideSpec, ChatGptSharedAxisOffset) +
            fadeOut(ChatGptExitFadeSpec))

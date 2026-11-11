package com.itos.coui.scroll

import android.view.View
import androidx.compose.foundation.OverscrollEffect
import androidx.compose.foundation.OverscrollFactory
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalView
import kotlinx.coroutines.CoroutineScope

/**
 * 给 LocalOverscrollFactory 提供纵向边界回弹；每个容器创建独立 effect，节点移除即停止运动。
 * 不替换容器的正常惯性，不提供横向回弹；空、短内容的输入仍由容器决定。
 * 需要完整纵向惯性及同帧触边交接时使用 couiVerticalScroll。
 */
@Composable
fun rememberCouiOverscrollFactory(hapticFeedbackEnabled: Boolean = true): OverscrollFactory {
    val scope = rememberCoroutineScope()
    val view = LocalView.current
    return remember(scope, view, hapticFeedbackEnabled) { CouiOverscrollFactory(scope, view, hapticFeedbackEnabled) }
}

private data class CouiOverscrollFactory(
    val scope: CoroutineScope,
    val view: View,
    val hapticFeedbackEnabled: Boolean,
) : OverscrollFactory {
    override fun createOverscrollEffect(): OverscrollEffect {
        val refresh = checkNotNull(view.display).refreshRate
        require(refresh.isFinite() && refresh > 0f)
        return CouiOverscrollEffect(scope, view.resources.displayMetrics.heightPixels.coerceAtLeast(1),
            1f / refresh, view, hapticFeedbackEnabled)
    }
}

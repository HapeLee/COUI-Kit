package com.itos.coui.menu

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import com.itos.coui.backdrop.CouiBackdrop
import com.itos.coui.backdrop.couiBackdrop
import com.itos.coui.backdrop.rememberCouiBackdrop

internal val LocalCouiMenuBackdrop = staticCompositionLocalOf<CouiBackdrop?> { null }

/** 内容修饰符挂到所属窗口的内容根节点; Popup 菜单自身不进入采样树。 */
@Composable
fun ProvideCouiMenuBackdrop(content: @Composable (Modifier) -> Unit) {
    val backdrop = rememberCouiBackdrop(parent = LocalCouiMenuBackdrop.current, diagnosticName = "menu-window")
    CompositionLocalProvider(LocalCouiMenuBackdrop provides backdrop) {
        content(Modifier.couiBackdrop(backdrop))
    }
}

package com.itos.coui.demo

import androidx.compose.runtime.Composable
import com.itos.coui.backdrop.CouiBackdrop
import com.itos.coui.dialog.CouiDialogDefaults
import com.itos.coui.dialog.CouiDialogMaterial
import com.itos.coui.menu.CouiMenuDefaults
import com.itos.coui.menu.CouiMenuStyle
import com.itos.coui.menu.rememberCouiWindowBackdrop
import com.itos.coui.theme.CouiTheme

@Composable
internal fun demoDialogMaterial(backdrop: CouiBackdrop? = null): CouiDialogMaterial? {
    val windowBackdrop = backdrop ?: rememberCouiWindowBackdrop()
    return if (windowBackdrop != null && CouiDialogDefaults.isMaterialSupported()) {
        CouiDialogDefaults.material(CouiTheme.isDark)
    } else null
}

@Composable
internal fun demoMenuStyle(dark: Boolean): CouiMenuStyle {
    return CouiMenuDefaults.style(dark).copy(
        shape = DemoShapes.menu,
        itemShape = DemoShapes.menuItem,
        checkboxShape = DemoShapes.checkbox,
        checkboxBorderShape = DemoShapes.checkboxBorder,
    )
}

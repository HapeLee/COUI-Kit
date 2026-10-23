package com.itos.coui.demo

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import com.itos.coui.checkbox.CouiCheckboxDefaults
import com.itos.coui.menu.CouiMenuColors
import com.itos.coui.menu.CouiMenuStyle
import com.itos.coui.topappbar.CouiToolbarStyle
import com.itos.coui.topappbar.CouiTopAppBarButtonDefaults

@Composable
internal fun demoToolbarStyle(dark: Boolean, contour: Boolean, search: Boolean = false): CouiToolbarStyle {
    val density = LocalDensity.current.density
    val colors = MaterialTheme.colorScheme
    return CouiToolbarStyle(
        dark = dark,
        circleShape = DemoShapes.couiCircle,
        capsuleShape = DemoShapes.couiCapsule,
        material = { capsule -> CouiTopAppBarButtonDefaults.material(dark, capsule, search) },
        shadow = { capsule -> CouiTopAppBarButtonDefaults.shadow(dark, capsule, density, contour, search) },
        contentColor = colors.onSurface,
        textStyle = MaterialTheme.typography.bodyLarge,
        cursorColor = colors.primary,
        contourGlowEnabled = contour,
    )
}

@Composable
internal fun demoMenuStyle(dark: Boolean): CouiMenuStyle {
    val colors = MaterialTheme.colorScheme
    return CouiMenuStyle(
        colors = CouiMenuColors(colors.surface, colors.surfaceBright, colors.primary,
            colors.onSurface, colors.onSurfaceVariant, colors.outlineVariant),
        shape = DemoShapes.menu,
        itemShape = DemoShapes.menuItem,
        checkboxShape = DemoShapes.checkbox,
        checkboxBorderShape = DemoShapes.checkboxBorder,
        checkboxColors = CouiCheckboxDefaults.colors(if (dark) colors.inversePrimary else colors.primary, dark),
        radius = DemoShapes.menu.radius,
        itemTextStyle = MaterialTheme.typography.bodyLarge,
        headerTextStyle = MaterialTheme.typography.labelLarge,
        themeTint = colors.surface,
    )
}

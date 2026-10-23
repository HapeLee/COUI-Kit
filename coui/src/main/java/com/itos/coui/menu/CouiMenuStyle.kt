package com.itos.coui.menu

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import com.itos.coui.checkbox.CouiCheckboxColors
import com.itos.coui.effect.SpotlightDiagnostic

data class CouiMenuColors(
    val surface: Color,
    val container: Color,
    val primary: Color,
    val onSurface: Color,
    val onSurfaceVariant: Color,
    val outlineVariant: Color,
)

object CouiMenuDefaults {
    const val spotlightDiffuse: Float = 0.5f
}

/** 仅描述宿主外观; COUI 不读取应用主题、偏好或日志。 */
data class CouiMenuStyle(
    val colors: CouiMenuColors,
    val shape: Shape,
    val itemShape: Shape,
    val checkboxShape: Shape,
    val checkboxBorderShape: Shape,
    val checkboxColors: CouiCheckboxColors,
    val radius: Dp,
    val itemTextStyle: TextStyle,
    val headerTextStyle: TextStyle,
    val themeTint: Color = Color.Transparent,
    val spotlightDiffuse: Float? = null,
)

internal val LocalCouiMenuStyle = staticCompositionLocalOf<CouiMenuStyle> {
    error("菜单样式必须由 CouiMenu 提供")
}
internal val LocalCouiMenuDiagnostic = staticCompositionLocalOf<((SpotlightDiagnostic) -> Unit)?> { null }

package com.itos.coui.checkbox

import androidx.compose.ui.graphics.Color

object CouiCheckboxDefaults {
    fun colors(checked: Color, dark: Boolean): CouiCheckboxColors = CouiCheckboxColors(
        checked = checked,
        unchecked = Color(if (dark) 0x4dffffff else 0x42000000),
        disabledChecked = checked.copy(alpha = 0.3f),
        disabledUnchecked = Color(if (dark) 0x26ffffff else 0x14000000),
        checkmark = Color.White,
        disabledCheckmark = Color(if (dark) 0x29ffffff else 0x8affffff),
    )
}

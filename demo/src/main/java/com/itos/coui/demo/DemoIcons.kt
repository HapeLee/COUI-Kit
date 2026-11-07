package com.itos.coui.demo

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import com.itos.coui.icons.CouiIcons

/** 各演示场景复用已核对调用来源的库图标。 */
internal object DemoIcons {
    val Effects: ImageVector
        @Composable get() = CouiIcons.Lab
    val EffectsSelected: ImageVector
        @Composable get() = CouiIcons.Lab
    val Settings: ImageVector
        @Composable get() = CouiIcons.Setting
    val SettingsSelected: ImageVector
        @Composable get() = CouiIcons.Setting
    val Tune: ImageVector
        @Composable get() = CouiIcons.Sun
    val Switch: ImageVector
        @Composable get() = CouiIcons.Bluetooth
    val Checkbox: ImageVector
        @Composable get() = CouiIcons.Grid
    val Dialogs: ImageVector
        @Composable get() = CouiIcons.Bell
    val Title: ImageVector
        @Composable get() = CouiIcons.Foldable
    val Date: ImageVector
        @Composable get() = CouiIcons.Health
    val SimOne: ImageVector
        @Composable get() = CouiIcons.Transfer
    val SimTwo: ImageVector
        @Composable get() = CouiIcons.Transfer
}

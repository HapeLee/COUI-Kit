package com.itos.coui.icons

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.PathParser
import androidx.compose.ui.unit.dp
import com.itos.coui.theme.CouiTheme

/**
 * 系统实际消费者矢量和已核实的 ColorOS 设置主页矢量；来源见 ICON_SOURCES.md。
 * 使用 M3 Icon 保留矢量内置色时，需传入 Color.Unspecified。
 */
object CouiIcons {

    /** com.android.settings:drawable/coui_search_view_icon_normal。 */
    private val searchVariants: Pair<ImageVector, ImageVector> by lazy {
        themedVectors("COUI.Search", 20.dp, 20.dp, 20f, 20f, autoMirror = false) {
            addPath(PathParser().parsePathString("M2.822,8.191C2.822,5.226 5.226,2.822 8.191,2.822C11.156,2.822 13.56,5.226 13.56,8.191C13.56,11.156 11.156,13.56 8.191,13.56C5.226,13.56 2.822,11.156 2.822,8.191ZM8.191,1.422C4.452,1.422 1.422,4.452 1.422,8.191C1.422,11.929 4.452,14.96 8.191,14.96C9.735,14.96 11.159,14.442 12.298,13.571L17.075,18.348C17.426,18.699 17.996,18.699 18.347,18.348C18.699,17.997 18.699,17.427 18.347,17.075L13.571,12.299C14.442,11.16 14.96,9.736 14.96,8.191C14.96,4.452 11.929,1.422 8.191,1.422Z").toNodes(),
                fill = SolidColor(Color(0xFF000000)), fillAlpha = 1f, stroke = null, strokeAlpha = 1f,
                strokeLineWidth = 0f, strokeLineCap = StrokeCap.Butt, strokeLineJoin = StrokeJoin.Miter,
                pathFillType = PathFillType.EvenOdd)
        }
    }

    val Search: ImageVector
        @Composable get() = if (CouiTheme.isDark) searchVariants.second else searchVariants.first

    /** com.android.settings:drawable/coui_back_arrow_normal。 */
    private val backVariants: Pair<ImageVector, ImageVector> by lazy {
        themedVectors("COUI.Back", 24.dp, 24.dp, 24f, 24f, autoMirror = true) {
            addPath(PathParser().parsePathString("M10.1,4.4L2.5,12M2.5,12L10.1,19.6M2.5,12L21.5,12.001").toNodes(),
                fill = null, fillAlpha = 1f, stroke = SolidColor(Color(0xFF000000)), strokeAlpha = 1f,
                strokeLineWidth = 1.399999976158142f, strokeLineCap = StrokeCap.Round, strokeLineJoin = StrokeJoin.Round,
                pathFillType = PathFillType.NonZero)
        }
    }

    val Back: ImageVector
        @Composable get() = if (CouiTheme.isDark) backVariants.second else backVariants.first

    /** com.android.settings:drawable/ic_close。 */
    private val closeVariants: Pair<ImageVector, ImageVector> by lazy {
        themedVectors("COUI.Close", 24.dp, 24.dp, 24f, 24f, autoMirror = false) {
            addPath(PathParser().parsePathString("M19.000000,6.400000l-1.400000,-1.400000 -5.600000,5.600000 -5.600000,-5.600000 -1.400000,1.400000 5.600000,5.600000 -5.600000,5.600000 1.400000,1.400000 5.600000,-5.600000 5.600000,5.600000 1.400000,-1.400000 -5.600000,-5.600000z").toNodes(),
                fill = SolidColor(Color(0xFF000000)), fillAlpha = 1f, stroke = null, strokeAlpha = 1f,
                strokeLineWidth = 0f, strokeLineCap = StrokeCap.Butt, strokeLineJoin = StrokeJoin.Miter,
                pathFillType = PathFillType.NonZero)
        }
    }

    val Close: ImageVector
        @Composable get() = if (CouiTheme.isDark) closeVariants.second else closeVariants.first

    /** com.coloros.alarmclock:drawable/color_floating_button_icon_add。 */
    private val addVariants: Pair<ImageVector, ImageVector> by lazy {
        themedVectors("COUI.Add", 24.dp, 24.dp, 24f, 24f, autoMirror = false) {
            addGroup(rotate = 0f, pivotX = 0f, pivotY = 0f, scaleX = 1f, scaleY = 1f, translationX = 0f, translationY = 0f)
            addGroup(clipPathData = PathParser().parsePathString("M0,0h24v24h-24z").toNodes())
            addPath(PathParser().parsePathString("M12.3,1.8C12.852,1.8 13.3,2.248 13.3,2.8V11.3H21.8C22.352,11.3 22.8,11.748 22.8,12.3C22.8,12.852 22.352,13.3 21.8,13.3H13.3V21.8C13.3,22.352 12.852,22.8 12.3,22.8C11.748,22.8 11.3,22.352 11.3,21.8V13.3H2.8C2.248,13.3 1.8,12.852 1.8,12.3C1.8,11.748 2.248,11.3 2.8,11.3H11.3V2.8C11.3,2.248 11.748,1.8 12.3,1.8Z").toNodes(),
                fill = SolidColor(Color(0xFF000000)), fillAlpha = 1f, stroke = null, strokeAlpha = 1f,
                strokeLineWidth = 0f, strokeLineCap = StrokeCap.Butt, strokeLineJoin = StrokeJoin.Miter,
                pathFillType = PathFillType.EvenOdd)
            clearGroup()
            clearGroup()
        }
    }

    val Add: ImageVector
        @Composable get() = if (CouiTheme.isDark) addVariants.second else addVariants.first

    /** com.android.settings:drawable/dark_theme。 */
    private val themeVariants: Pair<ImageVector, ImageVector> by lazy {
        themedVectors("COUI.DarkLight", 24.dp, 24.dp, 24f, 24f, autoMirror = false) {
            addPath(PathParser().parsePathString("M12,22C17.52,22 22,17.52 22,12 22,6.48 17.52,2 12,2 6.48,2 2,6.48 2,12 2,17.52 6.48,22 12,22ZM12,3.915c3.889,0 8,4.005 8,8.085 0,4.08 -3.927,7.992 -7.928,7.992z").toNodes(),
                fill = SolidColor(Color(0xFF000000)), fillAlpha = 1f, stroke = null, strokeAlpha = 1f,
                strokeLineWidth = 0f, strokeLineCap = StrokeCap.Butt, strokeLineJoin = StrokeJoin.Miter,
                pathFillType = PathFillType.NonZero)
        }
    }

    val DarkLight: ImageVector
        @Composable get() = if (CouiTheme.isDark) themeVariants.second else themeVariants.first

    /** com.android.settings:drawable/settings_brightness_ic。 */
    val Sun: ImageVector
        @Composable get() = if (CouiTheme.isDark) CouiIconVectors.SunNight else CouiIconVectors.SunDay

    /** com.android.settings:drawable/settings_notify_center_ic。 */
    val Bell: ImageVector
        @Composable get() = if (CouiTheme.isDark) CouiIconVectors.BellNight else CouiIconVectors.BellDay

    /** com.android.settings:drawable/settings_personalise_ic。 */
    val PhoneVibrate: ImageVector
        @Composable get() = if (CouiTheme.isDark) CouiIconVectors.PhoneVibrateNight else CouiIconVectors.PhoneVibrateDay

    /** com.android.settings:drawable/settings_sound_settings_ic。 */
    val Volume: ImageVector
        @Composable get() = if (CouiTheme.isDark) CouiIconVectors.VolumeNight else CouiIconVectors.VolumeDay

    val Plane: ImageVector
        @Composable get() = if (CouiTheme.isDark) CouiIconVectors.PlaneNight else CouiIconVectors.PlaneDay

    val Wifi: ImageVector
        @Composable get() = if (CouiTheme.isDark) CouiIconVectors.WifiNight else CouiIconVectors.WifiDay

    val Bluetooth: ImageVector
        @Composable get() = if (CouiTheme.isDark) CouiIconVectors.BluetoothNight else CouiIconVectors.BluetoothDay

    val Transfer: ImageVector
        @Composable get() = if (CouiTheme.isDark) CouiIconVectors.TransferNight else CouiIconVectors.TransferDay

    val Satellite: ImageVector
        @Composable get() = if (CouiTheme.isDark) CouiIconVectors.SatelliteNight else CouiIconVectors.SatelliteDay

    val SatelliteBD: ImageVector
        @Composable get() = if (CouiTheme.isDark) CouiIconVectors.SatelliteBDNight else CouiIconVectors.SatelliteBDDay

    val Radar: ImageVector
        @Composable get() = if (CouiTheme.isDark) CouiIconVectors.RadarNight else CouiIconVectors.RadarDay

    val Foldable: ImageVector
        @Composable get() = if (CouiTheme.isDark) CouiIconVectors.FoldableNight else CouiIconVectors.FoldableDay

    val FlipPhone: ImageVector
        @Composable get() = if (CouiTheme.isDark) CouiIconVectors.FlipPhoneNight else CouiIconVectors.FlipPhoneDay

    val Lock: ImageVector
        @Composable get() = if (CouiTheme.isDark) CouiIconVectors.LockNight else CouiIconVectors.LockDay

    val UserShield: ImageVector
        @Composable get() = if (CouiTheme.isDark) CouiIconVectors.UserShieldNight else CouiIconVectors.UserShieldDay

    val Sos: ImageVector
        @Composable get() = if (CouiTheme.isDark) CouiIconVectors.SosNight else CouiIconVectors.SosDay

    val Location: ImageVector
        @Composable get() = if (CouiTheme.isDark) CouiIconVectors.LocationNight else CouiIconVectors.LocationDay

    val Wallet: ImageVector
        @Composable get() = if (CouiTheme.isDark) CouiIconVectors.WalletNight else CouiIconVectors.WalletDay

    val Health: ImageVector
        @Composable get() = if (CouiTheme.isDark) CouiIconVectors.HealthNight else CouiIconVectors.HealthDay

    val Grid: ImageVector
        @Composable get() = if (CouiTheme.isDark) CouiIconVectors.GridNight else CouiIconVectors.GridDay

    val Battery: ImageVector
        @Composable get() = if (CouiTheme.isDark) CouiIconVectors.BatteryNight else CouiIconVectors.BatteryDay

    val PanelEnterRight: ImageVector
        @Composable get() = if (CouiTheme.isDark) CouiIconVectors.PanelEnterRightNight else CouiIconVectors.PanelEnterRightDay

    val PanelEnterLeft: ImageVector
        @Composable get() = if (CouiTheme.isDark) CouiIconVectors.PanelEnterLeftNight else CouiIconVectors.PanelEnterLeftDay

    val Fan: ImageVector
        @Composable get() = if (CouiTheme.isDark) CouiIconVectors.FanNight else CouiIconVectors.FanDay

    val Rings: ImageVector
        @Composable get() = if (CouiTheme.isDark) CouiIconVectors.RingsNight else CouiIconVectors.RingsDay

    val Accessibility: ImageVector
        @Composable get() = if (CouiTheme.isDark) CouiIconVectors.AccessibilityNight else CouiIconVectors.AccessibilityDay

    val AI: ImageVector get() = CouiIconVectors.AIDay

    val Gamepad: ImageVector
        @Composable get() = if (CouiTheme.isDark) CouiIconVectors.GamepadNight else CouiIconVectors.GamepadDay

    val CloudLink: ImageVector
        @Composable get() = if (CouiTheme.isDark) CouiIconVectors.CloudLinkNight else CouiIconVectors.CloudLinkDay

    val Setting: ImageVector
        @Composable get() = if (CouiTheme.isDark) CouiIconVectors.SettingNight else CouiIconVectors.SettingDay

    val Phone: ImageVector
        @Composable get() = if (CouiTheme.isDark) CouiIconVectors.PhoneNight else CouiIconVectors.PhoneDay

    val UserCircle: ImageVector
        @Composable get() = if (CouiTheme.isDark) CouiIconVectors.UserCircleNight else CouiIconVectors.UserCircleDay

    val Google: ImageVector get() = CouiIconVectors.GoogleDay

    val Certificate: ImageVector
        @Composable get() = if (CouiTheme.isDark) CouiIconVectors.CertificateNight else CouiIconVectors.CertificateDay

    val Lab: ImageVector
        @Composable get() = if (CouiTheme.isDark) CouiIconVectors.LabNight else CouiIconVectors.LabDay

    val BookOpen: ImageVector
        @Composable get() = if (CouiTheme.isDark) CouiIconVectors.BookOpenNight else CouiIconVectors.BookOpenDay

    /** 当前日夜模式的应用升级图，含原始白色前景。 */
    val AppUpdate: ImageVector
        @Composable get() = if (CouiTheme.isDark) CouiIconVectors.AppUpdateNight else CouiIconVectors.AppUpdateDay

    /** 生成仅更改背景色的应用升级图，并保留原始白色前景。 */
    @Composable
    fun AppUpdate(color: Color): ImageVector = if (color == Color.Unspecified) {
        AppUpdate
    } else {
        remember(color) { CouiIconVectors.appUpdate(color) }
    }

    private fun themedVectors(
        name: String,
        width: androidx.compose.ui.unit.Dp,
        height: androidx.compose.ui.unit.Dp,
        viewportWidth: Float,
        viewportHeight: Float,
        autoMirror: Boolean,
        content: ImageVector.Builder.() -> Unit,
    ): Pair<ImageVector, ImageVector> {
        val black = ImageVector.Builder(
            name, width, height, viewportWidth, viewportHeight,
            tintColor = Color.Black,
            autoMirror = autoMirror,
        ).apply(content).build()
        val white = ImageVector.Builder(
            name, width, height, viewportWidth, viewportHeight,
            tintColor = Color.White,
            autoMirror = autoMirror,
        ).apply(content).build()
        return black to white
    }
}

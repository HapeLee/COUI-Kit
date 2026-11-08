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
    val MultiPhone: ImageVector
        @Composable get() = if (CouiTheme.isDark) CouiIconVectors.MultiPhoneNight else CouiIconVectors.MultiPhoneDay

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

    val ShieldUser: ImageVector
        @Composable get() = if (CouiTheme.isDark) CouiIconVectors.ShieldUserNight else CouiIconVectors.ShieldUserDay

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

    /** com.oplus.securitypermission:drawable/permission_list_ic_locationgroup_default。 */
    val LocationMarker: ImageVector
        @Composable get() = if (CouiTheme.isDark) CouiIconVectors.LocationMarkerNight else CouiIconVectors.LocationMarkerDay

    /** com.oplus.securitypermission:drawable/permission_list_ic_cameragroup_default。 */
    val Camera: ImageVector
        @Composable get() = if (CouiTheme.isDark) CouiIconVectors.CameraNight else CouiIconVectors.CameraDay

    /** com.oplus.securitypermission:drawable/permission_list_ic_microphonegroup_default。 */
    val Microphone: ImageVector
        @Composable get() = if (CouiTheme.isDark) CouiIconVectors.MicrophoneNight else CouiIconVectors.MicrophoneDay

    /** com.oplus.securitypermission:drawable/permission_list_ic_readmediavisual_default。 */
    val Photo: ImageVector
        @Composable get() = if (CouiTheme.isDark) CouiIconVectors.PhotoNight else CouiIconVectors.PhotoDay

    /** com.oplus.securitypermission:drawable/permission_list_ic_contactsgroup_default。 */
    val Users: ImageVector
        @Composable get() = if (CouiTheme.isDark) CouiIconVectors.UsersNight else CouiIconVectors.UsersDay

    /** com.oplus.securitypermission:drawable/permission_list_ic_callloggroup_default。 */
    val Call: ImageVector
        @Composable get() = if (CouiTheme.isDark) CouiIconVectors.CallNight else CouiIconVectors.CallDay

    /** com.oplus.securitypermission:drawable/privacy_permission_manager_ic。 */
    val GridSliders: ImageVector
        @Composable get() = if (CouiTheme.isDark) CouiIconVectors.GridSlidersNight else CouiIconVectors.GridSlidersDay

    /** com.oplus.securitypermission:drawable/privacy_apps_lock_ic。 */
    val SquareLock: ImageVector
        @Composable get() = if (CouiTheme.isDark) CouiIconVectors.SquareLockNight else CouiIconVectors.SquareLockDay

    /** com.oplus.securitypermission:drawable/privacy_apps_hidden_ic。 */
    val SquareEyeClosed: ImageVector
        @Composable get() = if (CouiTheme.isDark) CouiIconVectors.SquareEyeClosedNight else CouiIconVectors.SquareEyeClosedDay

    /** com.oplus.securitypermission:drawable/security_data_protect_kit_ic。 */
    val SafeBox: ImageVector
        @Composable get() = if (CouiTheme.isDark) CouiIconVectors.SafeBoxNight else CouiIconVectors.SafeBoxDay

    /** com.oplus.securitypermission:drawable/security_private_info_protect_ic。 */
    val PrivacyLock: ImageVector
        @Composable get() = if (CouiTheme.isDark) CouiIconVectors.PrivacyLockNight else CouiIconVectors.PrivacyLockDay

    /** com.oplus.securitypermission:drawable/privacy_phone_manager_ic。 */
    val ShieldFlash: ImageVector
        @Composable get() = if (CouiTheme.isDark) CouiIconVectors.ShieldFlashNight else CouiIconVectors.ShieldFlashDay

    /** com.oplus.securitypermission:drawable/privacy_payment_protection_ic。 */
    val WalletShield: ImageVector
        @Composable get() = if (CouiTheme.isDark) CouiIconVectors.WalletShieldNight else CouiIconVectors.WalletShieldDay

    /** com.oplus.securitypermission:drawable/privacy_common_information_password_ic。 */
    val ShieldKey: ImageVector
        @Composable get() = if (CouiTheme.isDark) CouiIconVectors.ShieldKeyNight else CouiIconVectors.ShieldKeyDay

    /** com.oplus.securitypermission:drawable/multi_system_ic。 */
    val PhoneStack: ImageVector
        @Composable get() = if (CouiTheme.isDark) CouiIconVectors.PhoneStackNight else CouiIconVectors.PhoneStackDay

    /** com.oplus.securitypermission:drawable/privacy_system_enhanced_protection_ic。 */
    val ShieldCheck: ImageVector
        @Composable get() = if (CouiTheme.isDark) CouiIconVectors.ShieldCheckNight else CouiIconVectors.ShieldCheckDay

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

    object Filled {
        val Search: ImageVector
            @Composable get() = CouiIcons.Search

        val Back: ImageVector
            @Composable get() = CouiIcons.Back

        val Close: ImageVector
            @Composable get() = CouiIcons.Close

        val Add: ImageVector
            @Composable get() = CouiIcons.Add

        val DarkLight: ImageVector
            @Composable get() = CouiIcons.DarkLight

        val Sun: ImageVector
            @Composable get() = CouiIcons.Sun

        val Bell: ImageVector
            @Composable get() = CouiIcons.Bell

        val MultiPhone: ImageVector
            @Composable get() = CouiIcons.MultiPhone

        val Volume: ImageVector
            @Composable get() = CouiIcons.Volume

        val Plane: ImageVector
            @Composable get() = CouiIcons.Plane

        val Wifi: ImageVector
            @Composable get() = CouiIcons.Wifi

        val Bluetooth: ImageVector
            @Composable get() = CouiIcons.Bluetooth

        val Transfer: ImageVector
            @Composable get() = CouiIcons.Transfer

        val Satellite: ImageVector
            @Composable get() = CouiIcons.Satellite

        val SatelliteBD: ImageVector
            @Composable get() = CouiIcons.SatelliteBD

        val Radar: ImageVector
            @Composable get() = CouiIcons.Radar

        val Foldable: ImageVector
            @Composable get() = CouiIcons.Foldable

        val FlipPhone: ImageVector
            @Composable get() = CouiIcons.FlipPhone

        val Lock: ImageVector
            @Composable get() = CouiIcons.Lock

        val ShieldUser: ImageVector
            @Composable get() = CouiIcons.ShieldUser

        val Sos: ImageVector
            @Composable get() = CouiIcons.Sos

        val Location: ImageVector
            @Composable get() = CouiIcons.Location

        val Wallet: ImageVector
            @Composable get() = CouiIcons.Wallet

        val Health: ImageVector
            @Composable get() = CouiIcons.Health

        val Grid: ImageVector
            @Composable get() = CouiIcons.Grid

        val Battery: ImageVector
            @Composable get() = CouiIcons.Battery

        val PanelEnterRight: ImageVector
            @Composable get() = CouiIcons.PanelEnterRight

        val PanelEnterLeft: ImageVector
            @Composable get() = CouiIcons.PanelEnterLeft

        val Fan: ImageVector
            @Composable get() = CouiIcons.Fan

        val Rings: ImageVector
            @Composable get() = CouiIcons.Rings

        val Accessibility: ImageVector
            @Composable get() = CouiIcons.Accessibility

        val AI: ImageVector get() = CouiIcons.AI

        val Gamepad: ImageVector
            @Composable get() = CouiIcons.Gamepad

        val CloudLink: ImageVector
            @Composable get() = CouiIcons.CloudLink

        val Setting: ImageVector
            @Composable get() = CouiIcons.Setting

        val Phone: ImageVector
            @Composable get() = CouiIcons.Phone

        val UserCircle: ImageVector
            @Composable get() = CouiIcons.UserCircle

        val Google: ImageVector get() = CouiIcons.Google

        val Certificate: ImageVector
            @Composable get() = CouiIcons.Certificate

        val Lab: ImageVector
            @Composable get() = CouiIcons.Lab

        val BookOpen: ImageVector
            @Composable get() = CouiIcons.BookOpen

        val LocationMarker: ImageVector
            @Composable get() = CouiIcons.LocationMarker

        val Camera: ImageVector
            @Composable get() = CouiIcons.Camera

        val Microphone: ImageVector
            @Composable get() = CouiIcons.Microphone

        val Photo: ImageVector
            @Composable get() = CouiIcons.Photo

        val Users: ImageVector
            @Composable get() = CouiIcons.Users

        val Call: ImageVector
            @Composable get() = CouiIcons.Call

        val GridSliders: ImageVector
            @Composable get() = CouiIcons.GridSliders

        val SquareLock: ImageVector
            @Composable get() = CouiIcons.SquareLock

        val SquareEyeClosed: ImageVector
            @Composable get() = CouiIcons.SquareEyeClosed

        val SafeBox: ImageVector
            @Composable get() = CouiIcons.SafeBox

        val PrivacyLock: ImageVector
            @Composable get() = CouiIcons.PrivacyLock

        val ShieldFlash: ImageVector
            @Composable get() = CouiIcons.ShieldFlash

        val WalletShield: ImageVector
            @Composable get() = CouiIcons.WalletShield

        val ShieldKey: ImageVector
            @Composable get() = CouiIcons.ShieldKey

        val PhoneStack: ImageVector
            @Composable get() = CouiIcons.PhoneStack

        val ShieldCheck: ImageVector
            @Composable get() = CouiIcons.ShieldCheck

        val AppUpdate: ImageVector
            @Composable get() = CouiIcons.AppUpdate

        @Composable
        fun AppUpdate(color: Color): ImageVector = CouiIcons.AppUpdate(color)
    }

    object Outlined {
        val Search: ImageVector
            @Composable get() = CouiIcons.Search

        val Back: ImageVector
            @Composable get() = CouiIcons.Back

        val Close: ImageVector
            @Composable get() = CouiIcons.Close

        val Add: ImageVector
            @Composable get() = CouiIcons.Add

        val DarkLight: ImageVector
            @Composable get() = CouiIcons.DarkLight
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

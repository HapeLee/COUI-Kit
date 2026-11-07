package com.itos.coui.icons

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.PathParser
import androidx.compose.ui.unit.dp
import com.itos.coui.theme.CouiColor

internal object CouiIconVectors {
    private fun airplaneModeVector(name: String, tintColor: Color): ImageVector =
        iconVector(name, 24f.dp, 24f.dp, 24f, 24f, tintColor = tintColor) {
            iconPath(
                pathData = "M12.196,1.5C13.376,1.5 14.097,3.132 14.097,4.615V8.917C14.097,9.207 14.223,9.483 14.442,9.673L21.386,15.701C21.605,15.891 21.73,16.166 21.73,16.456V17.28C21.73,17.558 21.454,17.751 21.193,17.655L13.946,15C13.847,16.741 13.701,18.518 13.628,19.472L16.239,21.167C16.466,21.314 16.604,21.567 16.604,21.838V22.1C16.604,22.321 16.425,22.5 16.204,22.5H8.13C7.91,22.5 7.73,22.321 7.73,22.1V21.841C7.73,21.568 7.869,21.314 8.099,21.167L10.713,19.491C10.642,18.538 10.562,16.755 10.463,15L3.269,17.654C3.008,17.75 2.73,17.557 2.73,17.279V16.452C2.73,16.164 2.854,15.891 3.07,15.701L9.97,9.63C10.186,9.44 10.309,9.167 10.309,8.88V4.615C10.309,3.141 11.017,1.5 12.196,1.5Z",
                fill = SolidColor(Color(0xFF000000)),
                fillAlpha = 1f,
                stroke = null,
                strokeAlpha = 1f,
                strokeLineWidth = 0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 4f,
                pathFillType = PathFillType.NonZero,
                trimPathStart = 0f,
                trimPathEnd = 1f,
                trimPathOffset = 0f,
            )
        }

    val PlaneDay: ImageVector by lazy {
        airplaneModeVector("CouiIconVectors.PlaneDay", CouiColor.Day.YELLOW)
    }
    val PlaneNight: ImageVector by lazy {
        airplaneModeVector("CouiIconVectors.PlaneNight", CouiColor.Night.YELLOW)
    }

    val WifiDay: ImageVector by lazy {
        iconVector("CouiIconVectors.WifiDay", 24f.dp, 24f.dp, 24f, 24f, tintColor = CouiColor.Day.BLUE) {
            iconPath(
                pathData = "M11.9981,17.335C12.5507,17.335 13.077,17.449 13.5538,17.6553C13.7969,17.7604 13.9186,17.8135 14.0167,17.959C14.0904,18.0686 14.1322,18.2633 14.1104,18.3936C14.0813,18.5664 13.9687,18.6912 13.7432,18.9404L12.714,20.0781C12.4673,20.3508 12.3444,20.4877 12.1993,20.5381C12.0719,20.5823 11.9332,20.5823 11.8057,20.5381C11.6606,20.4877 11.5368,20.3509 11.2901,20.0781L10.2589,18.9385C10.0328,18.6886 9.9196,18.563 9.8907,18.3896C9.8691,18.2593 9.9115,18.0646 9.9854,17.9551C10.0839,17.8095 10.2065,17.7572 10.4503,17.6523C10.9253,17.4481 11.4484,17.335 11.9981,17.335ZM12.4386,12.7051C13.916,12.7801 15.2948,13.2297 16.4815,13.9609C16.8141,14.1658 16.9803,14.2691 17.0821,14.459C17.1634,14.6107 17.195,14.8346 17.1593,15.0029C17.1145,15.2136 16.9641,15.3811 16.6622,15.7148L16.5567,15.8311C16.2734,16.1443 16.1314,16.3009 15.9864,16.3691C15.8381,16.439 15.7396,16.4567 15.5763,16.4424C15.4166,16.4284 15.1831,16.3059 14.7169,16.0615C13.9042,15.6356 12.9792,15.3947 11.9981,15.3945C11.0183,15.3946 10.0935,15.6347 9.2813,16.0596C8.8156,16.3032 8.5825,16.4256 8.4229,16.4395C8.2598,16.4536 8.1611,16.436 8.0128,16.3662C7.8679,16.2979 7.7266,16.141 7.4434,15.8281L7.339,15.7119C7.0367,15.3779 6.8847,15.2109 6.8399,15C6.8043,14.8316 6.8367,14.6078 6.9181,14.4561C7.0201,14.2661 7.1865,14.1629 7.5196,13.958C8.8226,13.1567 10.3565,12.6944 11.9981,12.6943ZM12.339,8.0586C15.0546,8.1275 17.5653,9.0183 19.6348,10.4893C19.9702,10.7276 20.1382,10.8465 20.2276,11.0332C20.3015,11.1875 20.3241,11.3939 20.2852,11.5605C20.2382,11.7621 20.0868,11.9294 19.7843,12.2637L19.6856,12.373C19.3926,12.6969 19.2458,12.8589 19.086,12.9277C18.9335,12.9935 18.8026,13.0095 18.6388,12.9824C18.4671,12.9539 18.2629,12.8164 17.8556,12.542C16.1822,11.4147 14.1674,10.755 11.9981,10.7549C9.8301,10.7549 7.8155,11.4123 6.1427,12.5381C5.7354,12.8121 5.532,12.95 5.3604,12.9785C5.1965,13.0057 5.0648,12.9896 4.9122,12.9238C4.7526,12.8549 4.6063,12.6926 4.3136,12.3691L4.214,12.2588C3.9113,11.9244 3.76,11.7573 3.713,11.5557C3.6742,11.389 3.6967,11.1826 3.7706,11.0283C3.8601,10.8417 4.0278,10.7225 4.3634,10.4844C6.5189,8.9545 9.1537,8.0547 11.9981,8.0547ZM12.4591,3.4209C16.3274,3.5191 19.8893,4.849 22.7686,7.0322C23.104,7.2865 23.2718,7.4139 23.3556,7.5986C23.4258,7.7539 23.4445,7.9516 23.4044,8.1172C23.3564,8.3146 23.2049,8.4821 22.9024,8.8164L22.8067,8.9229C22.5111,9.2495 22.3629,9.4125 22.1973,9.4814C22.0431,9.5456 21.8994,9.5606 21.7354,9.5283C21.5595,9.4936 21.3671,9.3513 20.9825,9.0674C18.4692,7.2124 15.3616,6.1154 11.9981,6.1152C8.6363,6.1153 5.5303,7.2111 3.0177,9.0645C2.6327,9.3484 2.4398,9.4908 2.2638,9.5254C2.0999,9.5576 1.9561,9.5427 1.8018,9.4785C1.6363,9.4095 1.4881,9.2458 1.1925,8.9189L1.0968,8.8125C0.7946,8.4784 0.6428,8.3115 0.5948,8.1143C0.5546,7.9487 0.5734,7.751 0.6436,7.5957C0.7274,7.4107 0.8957,7.2828 1.2315,7.0283C4.2241,4.7608 7.954,3.4151 11.9981,3.415Z",
                fill = SolidColor(Color(0xFF000000)),
                fillAlpha = 1f,
                stroke = null,
                strokeAlpha = 1f,
                strokeLineWidth = 0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 4f,
                pathFillType = PathFillType.NonZero,
                trimPathStart = 0f,
                trimPathEnd = 1f,
                trimPathOffset = 0f,
            )
        }
    }

    val WifiNight: ImageVector by lazy {
        iconVector("CouiIconVectors.WifiNight", 24f.dp, 24f.dp, 24f, 24f, tintColor = CouiColor.Night.BLUE) {
            iconPath(
                pathData = "M11.9981,17.335C12.5507,17.335 13.077,17.449 13.5538,17.6553C13.7969,17.7604 13.9186,17.8135 14.0167,17.959C14.0904,18.0686 14.1322,18.2633 14.1104,18.3936C14.0813,18.5664 13.9687,18.6912 13.7432,18.9404L12.714,20.0781C12.4673,20.3508 12.3444,20.4877 12.1993,20.5381C12.0719,20.5823 11.9332,20.5823 11.8057,20.5381C11.6606,20.4877 11.5368,20.3509 11.2901,20.0781L10.2589,18.9385C10.0328,18.6886 9.9196,18.563 9.8907,18.3896C9.8691,18.2593 9.9115,18.0646 9.9854,17.9551C10.0839,17.8095 10.2065,17.7572 10.4503,17.6523C10.9253,17.4481 11.4484,17.335 11.9981,17.335ZM12.4386,12.7051C13.916,12.7801 15.2948,13.2297 16.4815,13.9609C16.8141,14.1658 16.9803,14.2691 17.0821,14.459C17.1634,14.6107 17.195,14.8346 17.1593,15.0029C17.1145,15.2136 16.9641,15.3811 16.6622,15.7148L16.5567,15.8311C16.2734,16.1443 16.1314,16.3009 15.9864,16.3691C15.8381,16.439 15.7396,16.4567 15.5763,16.4424C15.4166,16.4284 15.1831,16.3059 14.7169,16.0615C13.9042,15.6356 12.9792,15.3947 11.9981,15.3945C11.0183,15.3946 10.0935,15.6347 9.2813,16.0596C8.8156,16.3032 8.5825,16.4256 8.4229,16.4395C8.2598,16.4536 8.1611,16.436 8.0128,16.3662C7.8679,16.2979 7.7266,16.141 7.4434,15.8281L7.339,15.7119C7.0367,15.3779 6.8847,15.2109 6.8399,15C6.8043,14.8316 6.8367,14.6078 6.9181,14.4561C7.0201,14.2661 7.1865,14.1629 7.5196,13.958C8.8226,13.1567 10.3565,12.6944 11.9981,12.6943ZM12.339,8.0586C15.0546,8.1275 17.5653,9.0183 19.6348,10.4893C19.9702,10.7276 20.1382,10.8465 20.2276,11.0332C20.3015,11.1875 20.3241,11.3939 20.2852,11.5605C20.2382,11.7621 20.0868,11.9294 19.7843,12.2637L19.6856,12.373C19.3926,12.6969 19.2458,12.8589 19.086,12.9277C18.9335,12.9935 18.8026,13.0095 18.6388,12.9824C18.4671,12.9539 18.2629,12.8164 17.8556,12.542C16.1822,11.4147 14.1674,10.755 11.9981,10.7549C9.8301,10.7549 7.8155,11.4123 6.1427,12.5381C5.7354,12.8121 5.532,12.95 5.3604,12.9785C5.1965,13.0057 5.0648,12.9896 4.9122,12.9238C4.7526,12.8549 4.6063,12.6926 4.3136,12.3691L4.214,12.2588C3.9113,11.9244 3.76,11.7573 3.713,11.5557C3.6742,11.389 3.6967,11.1826 3.7706,11.0283C3.8601,10.8417 4.0278,10.7225 4.3634,10.4844C6.5189,8.9545 9.1537,8.0547 11.9981,8.0547ZM12.4591,3.4209C16.3274,3.5191 19.8893,4.849 22.7686,7.0322C23.104,7.2865 23.2718,7.4139 23.3556,7.5986C23.4258,7.7539 23.4445,7.9516 23.4044,8.1172C23.3564,8.3146 23.2049,8.4821 22.9024,8.8164L22.8067,8.9229C22.5111,9.2495 22.3629,9.4125 22.1973,9.4814C22.0431,9.5456 21.8994,9.5606 21.7354,9.5283C21.5595,9.4936 21.3671,9.3513 20.9825,9.0674C18.4692,7.2124 15.3616,6.1154 11.9981,6.1152C8.6363,6.1153 5.5303,7.2111 3.0177,9.0645C2.6327,9.3484 2.4398,9.4908 2.2638,9.5254C2.0999,9.5576 1.9561,9.5427 1.8018,9.4785C1.6363,9.4095 1.4881,9.2458 1.1925,8.9189L1.0968,8.8125C0.7946,8.4784 0.6428,8.3115 0.5948,8.1143C0.5546,7.9487 0.5734,7.751 0.6436,7.5957C0.7274,7.4107 0.8957,7.2828 1.2315,7.0283C4.2241,4.7608 7.954,3.4151 11.9981,3.415Z",
                fill = SolidColor(Color(0xFF000000)),
                fillAlpha = 1f,
                stroke = null,
                strokeAlpha = 1f,
                strokeLineWidth = 0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 4f,
                pathFillType = PathFillType.NonZero,
                trimPathStart = 0f,
                trimPathEnd = 1f,
                trimPathOffset = 0f,
            )
        }
    }

    private fun bluetoothVector(name: String, tintColor: Color): ImageVector =
        iconVector(name, 24f.dp, 24f.dp, 24f, 24f, tintColor = tintColor) {
            iconPath(
                pathData = "M10.645,2.523C10.646,1.38 11.931,0.734 12.842,1.371L12.929,1.437L18.224,5.749C19.007,6.387 19.01,7.582 18.231,8.225L13.718,11.941L18.253,15.776C19.016,16.422 19.007,17.603 18.232,18.236L12.931,22.566C12.017,23.313 10.645,22.663 10.645,21.483V14.903L7.236,18.313C7.038,18.51 6.939,18.609 6.825,18.647C6.724,18.679 6.616,18.679 6.516,18.647C6.402,18.61 6.302,18.511 6.104,18.313L5.539,17.747C5.341,17.549 5.242,17.45 5.205,17.336C5.172,17.236 5.172,17.127 5.205,17.027C5.242,16.912 5.341,16.813 5.539,16.615L9.586,12.568C9.784,12.37 9.884,12.27 9.921,12.156C9.954,12.056 9.954,11.947 9.921,11.847C9.884,11.733 9.784,11.634 9.586,11.436L5.539,7.388C5.341,7.19 5.242,7.091 5.205,6.977C5.172,6.876 5.172,6.767 5.205,6.667C5.242,6.553 5.341,6.454 5.539,6.256L6.104,5.691C6.302,5.493 6.402,5.394 6.516,5.357C6.617,5.324 6.724,5.324 6.825,5.357C6.939,5.394 7.038,5.493 7.236,5.691L10.645,9.1V2.523ZM13.045,19.374L15.968,16.986L13.045,14.514V19.374ZM13.045,9.387L15.951,6.993L13.045,4.626V9.387Z",
                fill = SolidColor(Color(0xFF000000)),
                fillAlpha = 1f,
                stroke = null,
                strokeAlpha = 1f,
                strokeLineWidth = 0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 4f,
                pathFillType = PathFillType.NonZero,
                trimPathStart = 0f,
                trimPathEnd = 1f,
                trimPathOffset = 0f,
            )
        }

    val BluetoothDay: ImageVector by lazy {
        bluetoothVector("CouiIconVectors.BluetoothDay", CouiColor.Day.BLUE)
    }
    val BluetoothNight: ImageVector by lazy {
        bluetoothVector("CouiIconVectors.BluetoothNight", CouiColor.Night.BLUE)
    }

    private fun mobileNetworkVector(name: String, tintColor: Color): ImageVector =
        iconVector(name, 24f.dp, 24f.dp, 24f, 24f, tintColor = tintColor) {
            iconPath(
                pathData = "M8,11V21.3C8,21.686 8.313,22 8.7,22H9.3C9.687,22 10,21.686 10,21.3V2.637C10,2.049 9.242,1.812 8.907,2.295L3.628,9.901C3.305,10.365 3.638,11 4.203,11L8,11Z",
                fill = SolidColor(Color(0xFF000000)),
                fillAlpha = 1f,
                stroke = null,
                strokeAlpha = 1f,
                strokeLineWidth = 0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 4f,
                pathFillType = PathFillType.NonZero,
                trimPathStart = 0f,
                trimPathEnd = 1f,
                trimPathOffset = 0f,
            )
            iconPath(
                pathData = "M16,13V2.7C16,2.313 15.687,2 15.3,2H14.7C14.313,2 14,2.313 14,2.7V21.363C14,21.95 14.758,22.188 15.093,21.705L20.372,14.099C20.694,13.635 20.362,13 19.797,13L16,13Z",
                fill = SolidColor(Color(0xFF000000)),
                fillAlpha = 1f,
                stroke = null,
                strokeAlpha = 1f,
                strokeLineWidth = 0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 4f,
                pathFillType = PathFillType.NonZero,
                trimPathStart = 0f,
                trimPathEnd = 1f,
                trimPathOffset = 0f,
            )
        }

    val TransferDay: ImageVector by lazy {
        mobileNetworkVector("CouiIconVectors.TransferDay", CouiColor.Day.GREEN)
    }
    val TransferNight: ImageVector by lazy {
        mobileNetworkVector("CouiIconVectors.TransferNight", CouiColor.Night.GREEN)
    }

    val SatelliteDay: ImageVector by lazy {
        iconVector("CouiIconVectors.SatelliteDay", 24f.dp, 24f.dp, 24f, 24f, tintColor = CouiColor.Day.YELLOW) {
            iconPath(
                pathData = "M5.373,1.547L2.568,4.352C2.315,4.605 2.315,5.015 2.568,5.269L7.209,9.909C7.462,10.163 7.872,10.163 8.126,9.909L10.93,7.105C11.183,6.851 11.183,6.441 10.93,6.188L6.289,1.547C6.036,1.294 5.626,1.294 5.373,1.547Z",
                fill = SolidColor(Color(0xFF000000)),
                fillAlpha = 1f,
                stroke = null,
                strokeAlpha = 1f,
                strokeLineWidth = 0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 4f,
                pathFillType = PathFillType.NonZero,
                trimPathStart = 0f,
                trimPathEnd = 1f,
                trimPathOffset = 0f,
            )
            iconPath(
                pathData = "M16.896,13.068L14.091,15.873C13.838,16.126 13.838,16.536 14.091,16.789L18.732,21.43C18.985,21.684 19.396,21.684 19.649,21.43L22.454,18.626C22.707,18.372 22.707,17.962 22.454,17.709L17.813,13.068C17.56,12.815 17.149,12.815 16.896,13.068Z",
                fill = SolidColor(Color(0xFF000000)),
                fillAlpha = 1f,
                stroke = null,
                strokeAlpha = 1f,
                strokeLineWidth = 0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 4f,
                pathFillType = PathFillType.NonZero,
                trimPathStart = 0f,
                trimPathEnd = 1f,
                trimPathOffset = 0f,
            )
            iconPath(
                pathData = "M12.964,16.341V17.329C12.964,17.742 12.465,17.948 12.173,17.656L6.344,11.827C6.052,11.536 6.259,11.037 6.672,11.037H7.659C8.091,11.037 8.506,10.869 8.817,10.569L12.818,6.71C13.469,6.058 14.525,6.058 15.176,6.71L17.29,8.824C17.941,9.475 17.941,10.531 17.29,11.182L13.431,15.184C13.131,15.494 12.964,15.909 12.964,16.341Z",
                fill = SolidColor(Color(0xFF000000)),
                fillAlpha = 1f,
                stroke = null,
                strokeAlpha = 1f,
                strokeLineWidth = 0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 4f,
                pathFillType = PathFillType.NonZero,
                trimPathStart = 0f,
                trimPathEnd = 1f,
                trimPathOffset = 0f,
            )
            iconPath(
                pathData = "M9.259,21.135C5.734,21.135 2.867,18.267 2.867,14.743C2.867,14.283 3.24,13.909 3.701,13.909C4.161,13.909 4.534,14.283 4.534,14.743C4.534,17.348 6.654,19.467 9.259,19.467C9.719,19.467 10.092,19.84 10.092,20.301C10.092,20.761 9.719,21.135 9.259,21.135Z",
                fill = SolidColor(Color(0xFF000000)),
                fillAlpha = 0.4f,
                stroke = null,
                strokeAlpha = 0.4f,
                strokeLineWidth = 0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 4f,
                pathFillType = PathFillType.NonZero,
                trimPathStart = 0f,
                trimPathEnd = 1f,
                trimPathOffset = 0f,
            )
            iconPath(
                pathData = "M9.259,18.356C7.267,18.356 5.646,16.735 5.646,14.743C5.646,14.283 6.019,13.909 6.48,13.909C6.94,13.909 7.313,14.283 7.313,14.743C7.313,15.816 8.186,16.688 9.259,16.688C9.719,16.688 10.092,17.062 10.092,17.522C10.092,17.983 9.719,18.356 9.259,18.356Z",
                fill = SolidColor(Color(0xFF000000)),
                fillAlpha = 0.4f,
                stroke = null,
                strokeAlpha = 0.4f,
                strokeLineWidth = 0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 4f,
                pathFillType = PathFillType.NonZero,
                trimPathStart = 0f,
                trimPathEnd = 1f,
                trimPathOffset = 0f,
            )
            iconPath(
                pathData = "M7.456,5.426L6.277,6.605L17.393,17.721L18.572,16.541L7.456,5.426Z",
                fill = SolidColor(Color(0xFF000000)),
                fillAlpha = 1f,
                stroke = null,
                strokeAlpha = 1f,
                strokeLineWidth = 0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 4f,
                pathFillType = PathFillType.NonZero,
                trimPathStart = 0f,
                trimPathEnd = 1f,
                trimPathOffset = 0f,
            )
        }
    }

    val SatelliteNight: ImageVector by lazy {
        iconVector("CouiIconVectors.SatelliteNight", 24f.dp, 24f.dp, 24f, 24f, tintColor = CouiColor.Night.YELLOW) {
            iconPath(
                pathData = "M5.373,1.547L2.568,4.352C2.315,4.605 2.315,5.015 2.568,5.269L7.209,9.909C7.462,10.163 7.872,10.163 8.126,9.909L10.93,7.105C11.183,6.851 11.183,6.441 10.93,6.188L6.289,1.547C6.036,1.294 5.626,1.294 5.373,1.547Z",
                fill = SolidColor(Color(0xFF000000)),
                fillAlpha = 1f,
                stroke = null,
                strokeAlpha = 1f,
                strokeLineWidth = 0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 4f,
                pathFillType = PathFillType.NonZero,
                trimPathStart = 0f,
                trimPathEnd = 1f,
                trimPathOffset = 0f,
            )
            iconPath(
                pathData = "M16.896,13.068L14.091,15.873C13.838,16.126 13.838,16.536 14.091,16.789L18.732,21.43C18.985,21.684 19.396,21.684 19.649,21.43L22.454,18.626C22.707,18.372 22.707,17.962 22.454,17.709L17.813,13.068C17.56,12.815 17.149,12.815 16.896,13.068Z",
                fill = SolidColor(Color(0xFF000000)),
                fillAlpha = 1f,
                stroke = null,
                strokeAlpha = 1f,
                strokeLineWidth = 0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 4f,
                pathFillType = PathFillType.NonZero,
                trimPathStart = 0f,
                trimPathEnd = 1f,
                trimPathOffset = 0f,
            )
            iconPath(
                pathData = "M12.964,16.341V17.329C12.964,17.742 12.465,17.948 12.173,17.656L6.344,11.827C6.052,11.536 6.259,11.037 6.672,11.037H7.659C8.091,11.037 8.506,10.869 8.817,10.569L12.818,6.71C13.469,6.058 14.525,6.058 15.176,6.71L17.29,8.824C17.941,9.475 17.941,10.531 17.29,11.182L13.431,15.184C13.131,15.494 12.964,15.909 12.964,16.341Z",
                fill = SolidColor(Color(0xFF000000)),
                fillAlpha = 1f,
                stroke = null,
                strokeAlpha = 1f,
                strokeLineWidth = 0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 4f,
                pathFillType = PathFillType.NonZero,
                trimPathStart = 0f,
                trimPathEnd = 1f,
                trimPathOffset = 0f,
            )
            iconPath(
                pathData = "M9.259,21.135C5.734,21.135 2.867,18.267 2.867,14.743C2.867,14.283 3.24,13.909 3.701,13.909C4.161,13.909 4.534,14.283 4.534,14.743C4.534,17.348 6.654,19.467 9.259,19.467C9.719,19.467 10.092,19.84 10.092,20.301C10.092,20.761 9.719,21.135 9.259,21.135Z",
                fill = SolidColor(Color(0xFF000000)),
                fillAlpha = 0.6f,
                stroke = null,
                strokeAlpha = 0.6f,
                strokeLineWidth = 0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 4f,
                pathFillType = PathFillType.NonZero,
                trimPathStart = 0f,
                trimPathEnd = 1f,
                trimPathOffset = 0f,
            )
            iconPath(
                pathData = "M9.259,18.356C7.267,18.356 5.646,16.735 5.646,14.743C5.646,14.283 6.019,13.909 6.48,13.909C6.94,13.909 7.313,14.283 7.313,14.743C7.313,15.816 8.186,16.688 9.259,16.688C9.719,16.688 10.092,17.062 10.092,17.522C10.092,17.983 9.719,18.356 9.259,18.356Z",
                fill = SolidColor(Color(0xFF000000)),
                fillAlpha = 0.6f,
                stroke = null,
                strokeAlpha = 0.6f,
                strokeLineWidth = 0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 4f,
                pathFillType = PathFillType.NonZero,
                trimPathStart = 0f,
                trimPathEnd = 1f,
                trimPathOffset = 0f,
            )
            iconPath(
                pathData = "M7.456,5.426L6.277,6.605L17.393,17.721L18.572,16.541L7.456,5.426Z",
                fill = SolidColor(Color(0xFF000000)),
                fillAlpha = 1f,
                stroke = null,
                strokeAlpha = 1f,
                strokeLineWidth = 0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 4f,
                pathFillType = PathFillType.NonZero,
                trimPathStart = 0f,
                trimPathEnd = 1f,
                trimPathOffset = 0f,
            )
        }
    }

    val SatelliteBDDay: ImageVector by lazy {
        iconVector("CouiIconVectors.SatelliteBDDay", 24f.dp, 24f.dp, 24f, 24f, tintColor = CouiColor.Day.YELLOW) {
            iconPath(
                pathData = "M5.373,1.552L2.568,4.356C2.315,4.61 2.315,5.02 2.568,5.273L7.209,9.914C7.462,10.167 7.872,10.167 8.126,9.914L10.93,7.11C11.183,6.856 11.183,6.446 10.93,6.193L6.289,1.552C6.036,1.299 5.626,1.299 5.373,1.552Z",
                fill = SolidColor(Color(0xFF000000)),
                fillAlpha = 1f,
                stroke = null,
                strokeAlpha = 1f,
                strokeLineWidth = 0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 4f,
                pathFillType = PathFillType.NonZero,
                trimPathStart = 0f,
                trimPathEnd = 1f,
                trimPathOffset = 0f,
            )
            iconPath(
                pathData = "M16.896,13.073L14.091,15.877C13.838,16.131 13.838,16.541 14.091,16.794L18.732,21.435C18.985,21.688 19.396,21.688 19.649,21.435L22.454,18.631C22.707,18.377 22.707,17.967 22.454,17.714L17.813,13.073C17.56,12.82 17.149,12.82 16.896,13.073Z",
                fill = SolidColor(Color(0xFF000000)),
                fillAlpha = 1f,
                stroke = null,
                strokeAlpha = 1f,
                strokeLineWidth = 0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 4f,
                pathFillType = PathFillType.NonZero,
                trimPathStart = 0f,
                trimPathEnd = 1f,
                trimPathOffset = 0f,
            )
            iconPath(
                pathData = "M21.972,4.27L19.734,2.033C19.57,1.869 19.332,1.805 19.108,1.865L16.051,2.684C15.828,2.744 15.653,2.919 15.593,3.142L14.774,6.199C14.714,6.423 14.778,6.661 14.942,6.825L17.179,9.063C17.343,9.227 17.582,9.291 17.806,9.231L20.862,8.412C21.086,8.352 21.261,8.177 21.321,7.953L22.14,4.897C22.199,4.673 22.136,4.434 21.972,4.27Z",
                fill = SolidColor(Color(0xFF000000)),
                fillAlpha = 0.4f,
                stroke = null,
                strokeAlpha = 0.4f,
                strokeLineWidth = 0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 4f,
                pathFillType = PathFillType.NonZero,
                trimPathStart = 0f,
                trimPathEnd = 1f,
                trimPathOffset = 0f,
            )
            iconPath(
                pathData = "M9.259,21.14C5.734,21.14 2.867,18.272 2.867,14.748C2.867,14.288 3.24,13.914 3.701,13.914C4.161,13.914 4.534,14.288 4.534,14.748C4.534,17.353 6.654,19.472 9.259,19.472C9.719,19.472 10.092,19.845 10.092,20.306C10.092,20.766 9.719,21.14 9.259,21.14Z",
                fill = SolidColor(Color(0xFF000000)),
                fillAlpha = 0.4f,
                stroke = null,
                strokeAlpha = 0.4f,
                strokeLineWidth = 0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 4f,
                pathFillType = PathFillType.NonZero,
                trimPathStart = 0f,
                trimPathEnd = 1f,
                trimPathOffset = 0f,
            )
            iconPath(
                pathData = "M9.259,18.361C7.267,18.361 5.646,16.74 5.646,14.748C5.646,14.288 6.019,13.914 6.48,13.914C6.94,13.914 7.313,14.288 7.313,14.748C7.313,15.821 8.186,16.693 9.259,16.693C9.719,16.693 10.092,17.066 10.092,17.527C10.092,17.987 9.719,18.361 9.259,18.361Z",
                fill = SolidColor(Color(0xFF000000)),
                fillAlpha = 0.4f,
                stroke = null,
                strokeAlpha = 0.4f,
                strokeLineWidth = 0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 4f,
                pathFillType = PathFillType.NonZero,
                trimPathStart = 0f,
                trimPathEnd = 1f,
                trimPathOffset = 0f,
            )
            iconPath(
                pathData = "M8.544,13.222C7.886,12.564 7.894,11.495 8.562,10.847L11.833,7.699C12.484,7.048 13.54,7.048 14.191,7.699L16.305,9.813C16.956,10.464 16.956,11.52 16.305,12.171L13.157,15.443C12.509,16.111 11.44,16.119 10.782,15.461L8.544,13.222Z",
                fill = SolidColor(Color(0xFF000000)),
                fillAlpha = 1f,
                stroke = null,
                strokeAlpha = 1f,
                strokeLineWidth = 0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 4f,
                pathFillType = PathFillType.NonZero,
                trimPathStart = 0f,
                trimPathEnd = 1f,
                trimPathOffset = 0f,
            )
            iconPath(
                pathData = "M7.457,5.431L6.278,6.61L17.393,17.725L18.572,16.546L7.457,5.431Z",
                fill = SolidColor(Color(0xFF000000)),
                fillAlpha = 1f,
                stroke = null,
                strokeAlpha = 1f,
                strokeLineWidth = 0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 4f,
                pathFillType = PathFillType.NonZero,
                trimPathStart = 0f,
                trimPathEnd = 1f,
                trimPathOffset = 0f,
            )
        }
    }

    val SatelliteBDNight: ImageVector by lazy {
        iconVector("CouiIconVectors.SatelliteBDNight", 24f.dp, 24f.dp, 24f, 24f, tintColor = CouiColor.Night.YELLOW) {
            iconPath(
                pathData = "M5.373,1.552L2.568,4.356C2.315,4.61 2.315,5.02 2.568,5.273L7.209,9.914C7.462,10.167 7.872,10.167 8.126,9.914L10.93,7.11C11.183,6.856 11.183,6.446 10.93,6.193L6.289,1.552C6.036,1.299 5.626,1.299 5.373,1.552Z",
                fill = SolidColor(Color(0xFF000000)),
                fillAlpha = 1f,
                stroke = null,
                strokeAlpha = 1f,
                strokeLineWidth = 0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 4f,
                pathFillType = PathFillType.NonZero,
                trimPathStart = 0f,
                trimPathEnd = 1f,
                trimPathOffset = 0f,
            )
            iconPath(
                pathData = "M16.896,13.073L14.091,15.877C13.838,16.131 13.838,16.541 14.091,16.794L18.732,21.435C18.985,21.688 19.396,21.688 19.649,21.435L22.454,18.631C22.707,18.377 22.707,17.967 22.454,17.714L17.813,13.073C17.56,12.82 17.149,12.82 16.896,13.073Z",
                fill = SolidColor(Color(0xFF000000)),
                fillAlpha = 1f,
                stroke = null,
                strokeAlpha = 1f,
                strokeLineWidth = 0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 4f,
                pathFillType = PathFillType.NonZero,
                trimPathStart = 0f,
                trimPathEnd = 1f,
                trimPathOffset = 0f,
            )
            iconPath(
                pathData = "M21.972,4.27L19.734,2.033C19.57,1.869 19.332,1.805 19.108,1.865L16.051,2.684C15.828,2.744 15.653,2.919 15.593,3.142L14.774,6.199C14.714,6.423 14.778,6.661 14.942,6.825L17.179,9.063C17.343,9.227 17.582,9.291 17.806,9.231L20.862,8.412C21.086,8.352 21.261,8.177 21.321,7.953L22.14,4.897C22.199,4.673 22.136,4.434 21.972,4.27Z",
                fill = SolidColor(Color(0xFF000000)),
                fillAlpha = 0.6f,
                stroke = null,
                strokeAlpha = 0.6f,
                strokeLineWidth = 0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 4f,
                pathFillType = PathFillType.NonZero,
                trimPathStart = 0f,
                trimPathEnd = 1f,
                trimPathOffset = 0f,
            )
            iconPath(
                pathData = "M9.259,21.14C5.734,21.14 2.867,18.272 2.867,14.748C2.867,14.288 3.24,13.914 3.701,13.914C4.161,13.914 4.534,14.288 4.534,14.748C4.534,17.353 6.654,19.472 9.259,19.472C9.719,19.472 10.092,19.845 10.092,20.306C10.092,20.766 9.719,21.14 9.259,21.14Z",
                fill = SolidColor(Color(0xFF000000)),
                fillAlpha = 0.6f,
                stroke = null,
                strokeAlpha = 0.6f,
                strokeLineWidth = 0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 4f,
                pathFillType = PathFillType.NonZero,
                trimPathStart = 0f,
                trimPathEnd = 1f,
                trimPathOffset = 0f,
            )
            iconPath(
                pathData = "M9.259,18.361C7.267,18.361 5.646,16.74 5.646,14.748C5.646,14.288 6.019,13.914 6.48,13.914C6.94,13.914 7.313,14.288 7.313,14.748C7.313,15.821 8.186,16.693 9.259,16.693C9.719,16.693 10.092,17.066 10.092,17.527C10.092,17.987 9.719,18.361 9.259,18.361Z",
                fill = SolidColor(Color(0xFF000000)),
                fillAlpha = 0.6f,
                stroke = null,
                strokeAlpha = 0.6f,
                strokeLineWidth = 0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 4f,
                pathFillType = PathFillType.NonZero,
                trimPathStart = 0f,
                trimPathEnd = 1f,
                trimPathOffset = 0f,
            )
            iconPath(
                pathData = "M8.544,13.222C7.886,12.564 7.894,11.495 8.562,10.847L11.833,7.699C12.484,7.048 13.54,7.048 14.191,7.699L16.305,9.813C16.956,10.464 16.956,11.52 16.305,12.171L13.157,15.443C12.509,16.111 11.44,16.119 10.782,15.461L8.544,13.222Z",
                fill = SolidColor(Color(0xFF000000)),
                fillAlpha = 1f,
                stroke = null,
                strokeAlpha = 1f,
                strokeLineWidth = 0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 4f,
                pathFillType = PathFillType.NonZero,
                trimPathStart = 0f,
                trimPathEnd = 1f,
                trimPathOffset = 0f,
            )
            iconPath(
                pathData = "M7.457,5.431L6.278,6.61L17.393,17.725L18.572,16.546L7.457,5.431Z",
                fill = SolidColor(Color(0xFF000000)),
                fillAlpha = 1f,
                stroke = null,
                strokeAlpha = 1f,
                strokeLineWidth = 0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 4f,
                pathFillType = PathFillType.NonZero,
                trimPathStart = 0f,
                trimPathEnd = 1f,
                trimPathOffset = 0f,
            )
        }
    }

    val RadarDay: ImageVector by lazy {
        iconVector("CouiIconVectors.RadarDay", 24f.dp, 24f.dp, 24f, 24f, tintColor = CouiColor.Day.GREEN) {
            iconPath(
                pathData = "M3.261,19.028a1.65,1.65 0,1 0,3.3 0a1.65,1.65 0,1 0,-3.3 0z",
                fill = SolidColor(Color(0xFF000000)),
                fillAlpha = 1f,
                stroke = null,
                strokeAlpha = 1f,
                strokeLineWidth = 0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 4f,
                pathFillType = PathFillType.NonZero,
                trimPathStart = 0f,
                trimPathEnd = 1f,
                trimPathOffset = 0f,
            )
            iconPath(
                pathData = "M12,12.002m-3.1,0a3.1,3.1 0,1 1,6.2 0a3.1,3.1 0,1 1,-6.2 0",
                fill = SolidColor(Color(0xFF000000)),
                fillAlpha = 1f,
                stroke = null,
                strokeAlpha = 1f,
                strokeLineWidth = 0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 4f,
                pathFillType = PathFillType.NonZero,
                trimPathStart = 0f,
                trimPathEnd = 1f,
                trimPathOffset = 0f,
            )
            iconPath(
                pathData = "M15.722,9.012a1.65,1.65 0,1 0,3.3 0a1.65,1.65 0,1 0,-3.3 0z",
                fill = SolidColor(Color(0xFF000000)),
                fillAlpha = 1f,
                stroke = null,
                strokeAlpha = 1f,
                strokeLineWidth = 0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 4f,
                pathFillType = PathFillType.NonZero,
                trimPathStart = 0f,
                trimPathEnd = 1f,
                trimPathOffset = 0f,
            )
            iconPath(
                pathData = "M3.253,4.84a1.65,1.65 0,1 0,3.3 0a1.65,1.65 0,1 0,-3.3 0z",
                fill = SolidColor(Color(0xFF000000)),
                fillAlpha = 1f,
                stroke = null,
                strokeAlpha = 1f,
                strokeLineWidth = 0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 4f,
                pathFillType = PathFillType.NonZero,
                trimPathStart = 0f,
                trimPathEnd = 1f,
                trimPathOffset = 0f,
            )
            iconPath(
                pathData = "M12.004,1.156C17.992,1.157 22.846,6.011 22.847,11.999C22.847,17.988 17.992,22.842 12.004,22.843C10.073,22.843 8.257,22.338 6.686,21.452L6.535,21.367C7.013,21.035 7.381,20.56 7.584,20.005C8.893,20.729 10.399,21.144 12.004,21.144C17.053,21.143 21.147,17.049 21.147,11.999C21.147,6.95 17.053,2.856 12.004,2.855C10.538,2.855 9.155,3.2 7.93,3.812L7.615,3.968C7.433,3.403 7.079,2.916 6.615,2.566L7.171,2.29C8.627,1.564 10.269,1.156 12.004,1.156ZM11.88,4.711C13.34,4.711 14.702,5.141 15.844,5.882L16.491,6.302C15.896,6.495 15.387,6.88 15.035,7.383L14.919,7.308C14.045,6.74 13.002,6.41 11.88,6.41C8.792,6.41 6.289,8.914 6.289,12.001C6.289,15.088 8.792,17.592 11.88,17.592C14.967,17.592 17.471,15.088 17.471,12.001V11.856C18.114,11.835 18.703,11.603 19.17,11.223V12.001C19.17,16.027 15.906,19.291 11.88,19.291C7.854,19.291 4.59,16.027 4.59,12.001C4.59,7.975 7.854,4.711 11.88,4.711ZM2.615,6.536C2.961,7.002 3.445,7.359 4.008,7.545L3.858,7.839C3.22,9.086 2.859,10.499 2.859,11.999C2.859,13.573 3.257,15.052 3.956,16.344C3.399,16.542 2.921,16.908 2.585,17.382L2.475,17.179C1.636,15.639 1.16,13.873 1.16,11.999C1.16,10.224 1.587,8.546 2.346,7.064L2.615,6.536Z",
                fill = SolidColor(Color(0xFF000000)),
                fillAlpha = 0.4f,
                stroke = null,
                strokeAlpha = 0.4f,
                strokeLineWidth = 0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 4f,
                pathFillType = PathFillType.NonZero,
                trimPathStart = 0f,
                trimPathEnd = 1f,
                trimPathOffset = 0f,
            )
        }
    }

    val RadarNight: ImageVector by lazy {
        iconVector("CouiIconVectors.RadarNight", 24f.dp, 24f.dp, 24f, 24f, tintColor = CouiColor.Night.GREEN) {
            iconPath(
                pathData = "M3.261,19.028a1.65,1.65 0,1 0,3.3 0a1.65,1.65 0,1 0,-3.3 0z",
                fill = SolidColor(Color(0xFF000000)),
                fillAlpha = 1f,
                stroke = null,
                strokeAlpha = 1f,
                strokeLineWidth = 0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 4f,
                pathFillType = PathFillType.NonZero,
                trimPathStart = 0f,
                trimPathEnd = 1f,
                trimPathOffset = 0f,
            )
            iconPath(
                pathData = "M12,12.002m-3.1,0a3.1,3.1 0,1 1,6.2 0a3.1,3.1 0,1 1,-6.2 0",
                fill = SolidColor(Color(0xFF000000)),
                fillAlpha = 1f,
                stroke = null,
                strokeAlpha = 1f,
                strokeLineWidth = 0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 4f,
                pathFillType = PathFillType.NonZero,
                trimPathStart = 0f,
                trimPathEnd = 1f,
                trimPathOffset = 0f,
            )
            iconPath(
                pathData = "M15.722,9.012a1.65,1.65 0,1 0,3.3 0a1.65,1.65 0,1 0,-3.3 0z",
                fill = SolidColor(Color(0xFF000000)),
                fillAlpha = 1f,
                stroke = null,
                strokeAlpha = 1f,
                strokeLineWidth = 0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 4f,
                pathFillType = PathFillType.NonZero,
                trimPathStart = 0f,
                trimPathEnd = 1f,
                trimPathOffset = 0f,
            )
            iconPath(
                pathData = "M3.253,4.84a1.65,1.65 0,1 0,3.3 0a1.65,1.65 0,1 0,-3.3 0z",
                fill = SolidColor(Color(0xFF000000)),
                fillAlpha = 1f,
                stroke = null,
                strokeAlpha = 1f,
                strokeLineWidth = 0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 4f,
                pathFillType = PathFillType.NonZero,
                trimPathStart = 0f,
                trimPathEnd = 1f,
                trimPathOffset = 0f,
            )
            iconPath(
                pathData = "M12.004,1.156C17.992,1.157 22.846,6.011 22.847,11.999C22.847,17.988 17.992,22.842 12.004,22.843C10.073,22.843 8.257,22.338 6.686,21.452L6.535,21.367C7.013,21.035 7.381,20.56 7.584,20.005C8.893,20.729 10.399,21.144 12.004,21.144C17.053,21.143 21.147,17.049 21.147,11.999C21.147,6.95 17.053,2.856 12.004,2.855C10.538,2.855 9.155,3.2 7.93,3.812L7.615,3.968C7.433,3.403 7.079,2.916 6.615,2.566L7.171,2.29C8.627,1.564 10.269,1.156 12.004,1.156ZM11.88,4.711C13.34,4.711 14.702,5.141 15.844,5.882L16.491,6.302C15.896,6.495 15.387,6.88 15.035,7.383L14.919,7.308C14.045,6.74 13.002,6.41 11.88,6.41C8.792,6.41 6.289,8.914 6.289,12.001C6.289,15.088 8.792,17.592 11.88,17.592C14.967,17.592 17.471,15.088 17.471,12.001V11.856C18.114,11.835 18.703,11.603 19.17,11.223V12.001C19.17,16.027 15.906,19.291 11.88,19.291C7.854,19.291 4.59,16.027 4.59,12.001C4.59,7.975 7.854,4.711 11.88,4.711ZM2.615,6.536C2.961,7.002 3.445,7.359 4.008,7.545L3.858,7.839C3.22,9.086 2.859,10.499 2.859,11.999C2.859,13.573 3.257,15.052 3.956,16.344C3.399,16.542 2.921,16.908 2.585,17.382L2.475,17.179C1.636,15.639 1.16,13.873 1.16,11.999C1.16,10.224 1.587,8.546 2.346,7.064L2.615,6.536Z",
                fill = SolidColor(Color(0xFF000000)),
                fillAlpha = 0.6f,
                stroke = null,
                strokeAlpha = 0.6f,
                strokeLineWidth = 0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 4f,
                pathFillType = PathFillType.NonZero,
                trimPathStart = 0f,
                trimPathEnd = 1f,
                trimPathOffset = 0f,
            )
        }
    }

    val FoldableDay: ImageVector by lazy {
        iconVector("CouiIconVectors.FoldableDay", 24f.dp, 24f.dp, 24f, 24f, tintColor = CouiColor.Day.BLUE) {
            iconPath(
                pathData = "M19.728,2.359L12,4.543L4.272,2.359C3.634,2.179 3,2.658 3,3.321V19.789C3,20.505 3.476,21.134 4.165,21.329L11.184,23.312C11.717,23.463 12.282,23.463 12.816,23.312L19.835,21.329C20.524,21.134 21,20.505 21,19.789V3.321C21,2.658 20.366,2.179 19.728,2.359ZM10.317,21.509L5.01,20.009C4.708,19.924 4.5,19.649 4.5,19.335V4.908C4.5,4.444 4.944,4.108 5.39,4.234L10.698,5.733C10.999,5.819 11.207,6.094 11.207,6.407V20.835C11.207,21.299 10.764,21.635 10.317,21.509Z",
                fill = SolidColor(Color(0xFF000000)),
                fillAlpha = 1f,
                stroke = null,
                strokeAlpha = 1f,
                strokeLineWidth = 0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 4f,
                pathFillType = PathFillType.NonZero,
                trimPathStart = 0f,
                trimPathEnd = 1f,
                trimPathOffset = 0f,
            )
            iconPath(
                pathData = "M5.6,19.033V5.437L10.107,6.71V20.306L5.6,19.033Z",
                fill = SolidColor(Color(0xFF000000)),
                fillAlpha = 0.4f,
                stroke = null,
                strokeAlpha = 0.4f,
                strokeLineWidth = 0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 4f,
                pathFillType = PathFillType.NonZero,
                trimPathStart = 0f,
                trimPathEnd = 1f,
                trimPathOffset = 0f,
            )
        }
    }

    val FoldableNight: ImageVector by lazy {
        iconVector("CouiIconVectors.FoldableNight", 24f.dp, 24f.dp, 24f, 24f, tintColor = CouiColor.Night.BLUE) {
            iconPath(
                pathData = "M19.728,2.359L12,4.543L4.272,2.359C3.634,2.179 3,2.658 3,3.321V19.789C3,20.505 3.476,21.134 4.165,21.329L11.184,23.312C11.717,23.463 12.282,23.463 12.816,23.312L19.835,21.329C20.524,21.134 21,20.505 21,19.789V3.321C21,2.658 20.366,2.179 19.728,2.359ZM10.317,21.509L5.01,20.009C4.708,19.924 4.5,19.649 4.5,19.335V4.908C4.5,4.444 4.944,4.108 5.39,4.234L10.698,5.733C10.999,5.819 11.207,6.094 11.207,6.407V20.835C11.207,21.299 10.764,21.635 10.317,21.509Z",
                fill = SolidColor(Color(0xFF000000)),
                fillAlpha = 1f,
                stroke = null,
                strokeAlpha = 1f,
                strokeLineWidth = 0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 4f,
                pathFillType = PathFillType.NonZero,
                trimPathStart = 0f,
                trimPathEnd = 1f,
                trimPathOffset = 0f,
            )
            iconPath(
                pathData = "M5.6,19.033V5.437L10.107,6.71V20.306L5.6,19.033Z",
                fill = SolidColor(Color(0xFF000000)),
                fillAlpha = 0.6f,
                stroke = null,
                strokeAlpha = 0.6f,
                strokeLineWidth = 0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 4f,
                pathFillType = PathFillType.NonZero,
                trimPathStart = 0f,
                trimPathEnd = 1f,
                trimPathOffset = 0f,
            )
        }
    }

    val FlipPhoneDay: ImageVector by lazy {
        iconVector("CouiIconVectors.FlipPhoneDay", 24f.dp, 24f.dp, 24f, 24f, tintColor = CouiColor.Day.BLUE) {
            iconPath(
                pathData = "M15.4,3H3.6C2.716,3 2,3.716 2,4.6V19.4C2,20.284 2.716,21 3.6,21H15.4C16.284,21 17,20.284 17,19.4V4.6C17,3.716 16.284,3 15.4,3ZM15.5,18.8C15.5,19.185 15.185,19.5 14.8,19.5H4.2C3.815,19.5 3.5,19.185 3.5,18.8V5.2C3.5,4.815 3.815,4.5 4.2,4.5H15.4C15.455,4.5 15.5,4.545 15.5,4.6V18.8Z",
                fill = SolidColor(Color(0xFF000000)),
                fillAlpha = 1f,
                stroke = null,
                strokeAlpha = 1f,
                strokeLineWidth = 0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 4f,
                pathFillType = PathFillType.NonZero,
                trimPathStart = 0f,
                trimPathEnd = 1f,
                trimPathOffset = 0f,
            )
            iconPath(
                pathData = "M14.4,18.4V5.6L4.6,5.6V18.4H14.4Z",
                fill = SolidColor(Color(0xFF000000)),
                fillAlpha = 0.4f,
                stroke = null,
                strokeAlpha = 0.4f,
                strokeLineWidth = 0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 4f,
                pathFillType = PathFillType.NonZero,
                trimPathStart = 0f,
                trimPathEnd = 1f,
                trimPathOffset = 0f,
            )
            iconPath(
                pathData = "M22.18,17H10.055C9.415,17 8.837,16.619 8.584,16.03L3.268,3.626C3.141,3.33 3.359,3 3.681,3H16.945C17.585,3 18.163,3.381 18.416,3.97L23.283,15.327C23.622,16.119 23.042,17 22.18,17Z",
                fill = SolidColor(Color(0xFF000000)),
                fillAlpha = 1f,
                stroke = null,
                strokeAlpha = 1f,
                strokeLineWidth = 0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 4f,
                pathFillType = PathFillType.NonZero,
                trimPathStart = 0f,
                trimPathEnd = 1f,
                trimPathOffset = 0f,
            )
        }
    }

    val FlipPhoneNight: ImageVector by lazy {
        iconVector("CouiIconVectors.FlipPhoneNight", 24f.dp, 24f.dp, 24f, 24f, tintColor = CouiColor.Night.BLUE) {
            iconPath(
                pathData = "M15.4,3H3.6C2.716,3 2,3.716 2,4.6V19.4C2,20.284 2.716,21 3.6,21H15.4C16.284,21 17,20.284 17,19.4V4.6C17,3.716 16.284,3 15.4,3ZM15.5,18.8C15.5,19.185 15.185,19.5 14.8,19.5H4.2C3.815,19.5 3.5,19.185 3.5,18.8V5.2C3.5,4.815 3.815,4.5 4.2,4.5H15.4C15.455,4.5 15.5,4.545 15.5,4.6V18.8Z",
                fill = SolidColor(Color(0xFF000000)),
                fillAlpha = 1f,
                stroke = null,
                strokeAlpha = 1f,
                strokeLineWidth = 0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 4f,
                pathFillType = PathFillType.NonZero,
                trimPathStart = 0f,
                trimPathEnd = 1f,
                trimPathOffset = 0f,
            )
            iconPath(
                pathData = "M14.4,18.4V5.6L4.6,5.6V18.4H14.4Z",
                fill = SolidColor(Color(0xFF000000)),
                fillAlpha = 0.6f,
                stroke = null,
                strokeAlpha = 0.6f,
                strokeLineWidth = 0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 4f,
                pathFillType = PathFillType.NonZero,
                trimPathStart = 0f,
                trimPathEnd = 1f,
                trimPathOffset = 0f,
            )
            iconPath(
                pathData = "M22.18,17H10.055C9.415,17 8.837,16.619 8.584,16.03L3.268,3.626C3.141,3.33 3.359,3 3.681,3H16.945C17.585,3 18.163,3.381 18.416,3.97L23.283,15.327C23.622,16.119 23.042,17 22.18,17Z",
                fill = SolidColor(Color(0xFF000000)),
                fillAlpha = 1f,
                stroke = null,
                strokeAlpha = 1f,
                strokeLineWidth = 0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 4f,
                pathFillType = PathFillType.NonZero,
                trimPathStart = 0f,
                trimPathEnd = 1f,
                trimPathOffset = 0f,
            )
        }
    }

    val PhoneVibrateDay: ImageVector by lazy {
        iconVector("CouiIconVectors.PhoneVibrateDay", 24f.dp, 24f.dp, 24f, 24f, tintColor = CouiColor.Day.ORANGE) {
            iconPath(
                pathData = "M19.6,4C18.716,4 18,4.716 18,5.6V18.4C18,19.284 18.716,20 19.6,20H21.3C21.687,20 22,19.687 22,19.3V4.7C22,4.313 21.687,4 21.3,4H19.6ZM4.4,20C5.284,20 6,19.284 6,18.4V5.6C6,4.716 5.284,4 4.4,4H2.7C2.313,4 2,4.313 2,4.7V19.3C2,19.687 2.313,20 2.7,20H4.4Z",
                fill = SolidColor(Color(0xFF000000)),
                fillAlpha = 0.4f,
                stroke = null,
                strokeAlpha = 0.4f,
                strokeLineWidth = 0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 4f,
                pathFillType = PathFillType.NonZero,
                trimPathStart = 0f,
                trimPathEnd = 1f,
                trimPathOffset = 0f,
            )
            iconPath(
                pathData = "M15.2,2C16.194,2 17,2.806 17,3.8V20.2C17,21.194 16.194,22 15.2,22H8.8C7.806,22 7,21.194 7,20.2V3.8C7,2.806 7.806,2 8.8,2H15.2ZM10.555,19.684C10.281,19.684 10.059,19.887 10.059,20.138C10.059,20.389 10.281,20.593 10.555,20.593H13.446C13.72,20.593 13.942,20.389 13.942,20.138C13.942,19.887 13.72,19.684 13.446,19.684H10.555ZM11.999,3.41C11.598,3.41 11.273,3.736 11.273,4.138C11.274,4.539 11.598,4.864 11.999,4.864C12.4,4.864 12.724,4.539 12.725,4.138C12.725,3.736 12.4,3.41 11.999,3.41Z",
                fill = SolidColor(Color(0xFF000000)),
                fillAlpha = 1f,
                stroke = null,
                strokeAlpha = 1f,
                strokeLineWidth = 0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 4f,
                pathFillType = PathFillType.NonZero,
                trimPathStart = 0f,
                trimPathEnd = 1f,
                trimPathOffset = 0f,
            )
        }
    }

    val PhoneVibrateNight: ImageVector by lazy {
        iconVector("CouiIconVectors.PhoneVibrateNight", 24f.dp, 24f.dp, 24f, 24f, tintColor = CouiColor.Night.ORANGE) {
            iconPath(
                pathData = "M19.6,4C18.716,4 18,4.716 18,5.6V18.4C18,19.284 18.716,20 19.6,20H21.3C21.687,20 22,19.687 22,19.3V4.7C22,4.313 21.687,4 21.3,4H19.6ZM4.4,20C5.284,20 6,19.284 6,18.4V5.6C6,4.716 5.284,4 4.4,4H2.7C2.313,4 2,4.313 2,4.7V19.3C2,19.687 2.313,20 2.7,20H4.4Z",
                fill = SolidColor(Color(0xFF000000)),
                fillAlpha = 0.6f,
                stroke = null,
                strokeAlpha = 0.6f,
                strokeLineWidth = 0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 4f,
                pathFillType = PathFillType.NonZero,
                trimPathStart = 0f,
                trimPathEnd = 1f,
                trimPathOffset = 0f,
            )
            iconPath(
                pathData = "M15.2,2C16.194,2 17,2.806 17,3.8V20.2C17,21.194 16.194,22 15.2,22H8.8C7.806,22 7,21.194 7,20.2V3.8C7,2.806 7.806,2 8.8,2H15.2ZM10.555,19.684C10.281,19.684 10.059,19.887 10.059,20.138C10.059,20.389 10.281,20.593 10.555,20.593H13.446C13.72,20.593 13.942,20.389 13.942,20.138C13.942,19.887 13.72,19.684 13.446,19.684H10.555ZM11.999,3.41C11.598,3.41 11.273,3.736 11.273,4.138C11.274,4.539 11.598,4.864 11.999,4.864C12.4,4.864 12.724,4.539 12.725,4.138C12.725,3.736 12.4,3.41 11.999,3.41Z",
                fill = SolidColor(Color(0xFF000000)),
                fillAlpha = 1f,
                stroke = null,
                strokeAlpha = 1f,
                strokeLineWidth = 0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 4f,
                pathFillType = PathFillType.NonZero,
                trimPathStart = 0f,
                trimPathEnd = 1f,
                trimPathOffset = 0f,
            )
        }
    }

    private fun brightnessVector(name: String, tintColor: Color): ImageVector =
        iconVector(name, 24f.dp, 24f.dp, 24f, 24f, tintColor = tintColor) {
            iconPath(
                pathData = "M12,18.221C12.386,18.221 12.7,18.534 12.7,18.921V22.297C12.699,22.683 12.386,22.997 12,22.997C11.613,22.997 11.3,22.683 11.299,22.297V18.921C11.299,18.535 11.613,18.221 12,18.221ZM6.611,16.398C6.884,16.125 7.328,16.125 7.601,16.398C7.874,16.672 7.874,17.115 7.601,17.389L5.214,19.775C4.941,20.049 4.497,20.049 4.224,19.775C3.951,19.502 3.951,19.058 4.224,18.785L6.611,16.398ZM16.399,16.398C16.672,16.125 17.116,16.125 17.389,16.398L19.776,18.785C20.049,19.059 20.049,19.502 19.776,19.775C19.503,20.049 19.059,20.049 18.786,19.775L16.399,17.389C16.126,17.115 16.126,16.672 16.399,16.398ZM12.004,7.089C14.716,7.089 16.915,9.287 16.916,11.999C16.916,14.711 14.717,16.91 12.004,16.91C9.293,16.91 7.094,14.711 7.094,11.999C7.094,9.287 9.293,7.089 12.004,7.089ZM22.297,11.3C22.684,11.3 22.997,11.614 22.997,12C22.997,12.387 22.683,12.7 22.296,12.7H18.92C18.534,12.7 18.22,12.386 18.22,12C18.22,11.613 18.535,11.3 18.921,11.3H22.297ZM5.08,11.299C5.466,11.299 5.779,11.613 5.78,11.999C5.78,12.386 5.466,12.699 5.08,12.699H1.704C1.317,12.699 1.003,12.386 1.003,11.999C1.004,11.613 1.317,11.299 1.704,11.299H5.08ZM18.786,4.224C19.059,3.95 19.503,3.951 19.776,4.224C20.049,4.497 20.049,4.941 19.776,5.214L17.389,7.602C17.116,7.875 16.672,7.875 16.399,7.602C16.126,7.328 16.126,6.885 16.399,6.611L18.786,4.224ZM4.224,4.224C4.497,3.951 4.941,3.951 5.214,4.224L7.602,6.61C7.875,6.884 7.875,7.327 7.602,7.601C7.329,7.874 6.885,7.874 6.612,7.601L4.224,5.214C3.951,4.941 3.951,4.497 4.224,4.224ZM12,1.003C12.387,1.003 12.701,1.317 12.701,1.703V5.079C12.701,5.466 12.387,5.779 12,5.779C11.614,5.779 11.3,5.466 11.3,5.079V1.703C11.3,1.317 11.614,1.003 12,1.003Z",
                fill = SolidColor(Color(0xFF000000)),
                fillAlpha = 1f,
                stroke = null,
                strokeAlpha = 1f,
                strokeLineWidth = 0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 4f,
                pathFillType = PathFillType.NonZero,
                trimPathStart = 0f,
                trimPathEnd = 1f,
                trimPathOffset = 0f,
            )
        }

    val SunDay: ImageVector by lazy {
        brightnessVector("CouiIconVectors.SunDay", CouiColor.Day.YELLOW)
    }
    val SunNight: ImageVector by lazy {
        brightnessVector("CouiIconVectors.SunNight", CouiColor.Night.YELLOW)
    }

    private fun soundVector(name: String, tintColor: Color): ImageVector =
        iconVector(name, 24f.dp, 24f.dp, 24f, 24f, tintColor = tintColor) {
            iconPath(
                pathData = "M11.082,1.73C11.248,1.743 11.408,1.798 11.547,1.888C12.002,2.184 12.002,3.148 12.002,5.076V18.927C12.002,20.853 12.002,21.816 11.547,22.112C11.408,22.203 11.248,22.257 11.082,22.27C10.541,22.313 9.952,21.548 8.775,20.018L7.404,18.235C6.985,17.691 6.775,17.419 6.506,17.219C6.416,17.153 6.323,17.093 6.225,17.04C5.931,16.879 5.595,16.804 4.925,16.654L2.837,16.185C2.061,16.011 1.673,15.925 1.413,15.677C1.328,15.596 1.254,15.504 1.194,15.403C1.009,15.094 1.009,14.697 1.009,13.902V10.098C1.009,9.304 1.009,8.906 1.194,8.597C1.254,8.497 1.328,8.405 1.413,8.324C1.673,8.076 2.061,7.989 2.837,7.815L4.925,7.346C5.596,7.196 5.931,7.12 6.225,6.96C6.323,6.907 6.416,6.847 6.506,6.781C6.775,6.581 6.985,6.31 7.404,5.765L8.775,3.981C9.952,2.452 10.541,1.687 11.082,1.73ZM18.835,4.356C19.108,4.083 19.552,4.083 19.825,4.356C24.046,8.578 24.045,15.423 19.824,19.644C19.551,19.917 19.109,19.917 18.836,19.644C18.562,19.371 18.562,18.927 18.835,18.654C22.509,14.979 22.509,9.021 18.835,5.346C18.561,5.073 18.561,4.63 18.835,4.356ZM16.432,6.481C16.706,6.208 17.149,6.208 17.423,6.481C20.471,9.529 20.47,14.471 17.423,17.519C17.149,17.793 16.706,17.793 16.432,17.519C16.159,17.246 16.16,16.803 16.433,16.53C18.934,14.028 18.934,9.972 16.432,7.47C16.159,7.197 16.159,6.755 16.432,6.481ZM14.198,8.714C14.471,8.44 14.915,8.44 15.188,8.714C17.003,10.529 17.003,13.471 15.188,15.286C14.915,15.559 14.471,15.559 14.198,15.286C13.925,15.012 13.926,14.569 14.199,14.296C15.467,13.027 15.466,10.971 14.198,9.703C13.925,9.43 13.925,8.987 14.198,8.714Z",
                fill = SolidColor(Color(0xFF000000)),
                fillAlpha = 1f,
                stroke = null,
                strokeAlpha = 1f,
                strokeLineWidth = 0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 4f,
                pathFillType = PathFillType.NonZero,
                trimPathStart = 0f,
                trimPathEnd = 1f,
                trimPathOffset = 0f,
            )
        }

    val VolumeDay: ImageVector by lazy {
        soundVector("CouiIconVectors.VolumeDay", CouiColor.Day.GREEN)
    }
    val VolumeNight: ImageVector by lazy {
        soundVector("CouiIconVectors.VolumeNight", CouiColor.Night.GREEN)
    }

    val BellDay: ImageVector by lazy {
        iconVector("CouiIconVectors.BellDay", 24f.dp, 24f.dp, 24f, 24f, tintColor = CouiColor.Day.BLUE) {
            iconPath(
                pathData = "M14.207,19.934C14.33,19.934 14.425,20.045 14.394,20.164C14.366,20.275 14.331,20.384 14.287,20.49C14.161,20.793 13.977,21.069 13.745,21.301C13.513,21.533 13.238,21.717 12.934,21.843C12.631,21.968 12.306,22.033 11.977,22.033C11.649,22.033 11.324,21.968 11.02,21.843C10.717,21.717 10.442,21.533 10.21,21.301C9.978,21.069 9.793,20.793 9.668,20.49C9.624,20.384 9.589,20.275 9.56,20.164C9.53,20.045 9.625,19.934 9.748,19.934H14.207ZM11.976,1.967C12.423,1.967 12.851,2.144 13.167,2.46C13.402,2.695 13.559,2.993 13.625,3.313C13.747,3.346 13.866,3.383 13.985,3.423C13.784,3.92 13.673,4.464 13.673,5.033C13.673,7.408 15.598,9.333 17.972,9.333C18.088,9.333 18.202,9.326 18.315,9.317C18.316,9.347 18.318,9.376 18.318,9.405L18.473,12.596C18.518,13.497 18.866,14.357 19.461,15.036L20.576,16.31C21.368,17.215 20.726,18.632 19.523,18.633H4.426C3.223,18.632 2.58,17.216 3.373,16.31L4.49,15.034C5.083,14.356 5.431,13.498 5.475,12.599L5.635,9.404C5.65,6.488 7.634,4.038 10.326,3.314C10.392,2.993 10.551,2.695 10.786,2.46C11.102,2.144 11.53,1.967 11.976,1.967Z",
                fill = SolidColor(Color(0xFF000000)),
                fillAlpha = 1f,
                stroke = null,
                strokeAlpha = 1f,
                strokeLineWidth = 0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 4f,
                pathFillType = PathFillType.NonZero,
                trimPathStart = 0f,
                trimPathEnd = 1f,
                trimPathOffset = 0f,
            )
            iconPath(
                pathData = "M17.977,5.033m-3,0a3,3 0,1 1,6 0a3,3 0,1 1,-6 0",
                fill = SolidColor(Color(0xFF000000)),
                fillAlpha = 0.4f,
                stroke = null,
                strokeAlpha = 0.4f,
                strokeLineWidth = 0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 4f,
                pathFillType = PathFillType.NonZero,
                trimPathStart = 0f,
                trimPathEnd = 1f,
                trimPathOffset = 0f,
            )
        }
    }

    val BellNight: ImageVector by lazy {
        iconVector("CouiIconVectors.BellNight", 24f.dp, 24f.dp, 24f, 24f, tintColor = CouiColor.Night.BLUE) {
            iconPath(
                pathData = "M14.207,19.934C14.33,19.934 14.425,20.045 14.394,20.164C14.366,20.275 14.331,20.384 14.287,20.49C14.161,20.793 13.977,21.069 13.745,21.301C13.513,21.533 13.238,21.717 12.934,21.843C12.631,21.968 12.306,22.033 11.977,22.033C11.649,22.033 11.324,21.968 11.02,21.843C10.717,21.717 10.442,21.533 10.21,21.301C9.978,21.069 9.793,20.793 9.668,20.49C9.624,20.384 9.589,20.275 9.56,20.164C9.53,20.045 9.625,19.934 9.748,19.934H14.207ZM11.976,1.967C12.423,1.967 12.851,2.144 13.167,2.46C13.402,2.695 13.559,2.993 13.625,3.313C13.747,3.346 13.866,3.383 13.985,3.423C13.784,3.92 13.673,4.464 13.673,5.033C13.673,7.408 15.598,9.333 17.972,9.333C18.088,9.333 18.202,9.326 18.315,9.317C18.316,9.347 18.318,9.376 18.318,9.405L18.473,12.596C18.518,13.497 18.866,14.357 19.461,15.036L20.576,16.31C21.368,17.215 20.726,18.632 19.523,18.633H4.426C3.223,18.632 2.58,17.216 3.373,16.31L4.49,15.034C5.083,14.356 5.431,13.498 5.475,12.599L5.635,9.404C5.65,6.488 7.634,4.038 10.326,3.314C10.392,2.993 10.551,2.695 10.786,2.46C11.102,2.144 11.53,1.967 11.976,1.967Z",
                fill = SolidColor(Color(0xFF000000)),
                fillAlpha = 1f,
                stroke = null,
                strokeAlpha = 1f,
                strokeLineWidth = 0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 4f,
                pathFillType = PathFillType.NonZero,
                trimPathStart = 0f,
                trimPathEnd = 1f,
                trimPathOffset = 0f,
            )
            iconPath(
                pathData = "M17.977,5.033m-3,0a3,3 0,1 1,6 0a3,3 0,1 1,-6 0",
                fill = SolidColor(Color(0xFF000000)),
                fillAlpha = 0.6f,
                stroke = null,
                strokeAlpha = 0.6f,
                strokeLineWidth = 0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 4f,
                pathFillType = PathFillType.NonZero,
                trimPathStart = 0f,
                trimPathEnd = 1f,
                trimPathOffset = 0f,
            )
        }
    }

    val LockDay: ImageVector by lazy {
        iconVector("CouiIconVectors.LockDay", 24f.dp, 24f.dp, 24f, 24f, tintColor = CouiColor.Day.BLUE) {
            iconPath(
                pathData = "M17.27,11.778C18.137,11.778 18.572,11.778 18.903,11.947C19.195,12.095 19.433,12.333 19.581,12.625C19.75,12.956 19.75,13.391 19.75,14.258V17.535C19.75,19.395 19.75,20.325 19.388,21.036C19.069,21.66 18.561,22.168 17.937,22.487C17.226,22.849 16.296,22.849 14.436,22.849H9.564C7.704,22.849 6.774,22.849 6.063,22.487C5.439,22.168 4.931,21.66 4.612,21.036C4.25,20.325 4.25,19.395 4.25,17.535L4.25,14.258C4.25,13.391 4.25,12.956 4.419,12.625C4.568,12.333 4.805,12.095 5.097,11.947C5.428,11.778 5.863,11.778 6.73,11.778L17.27,11.778Z",
                fill = SolidColor(Color(0xFF000000)),
                fillAlpha = 1f,
                stroke = null,
                strokeAlpha = 1f,
                strokeLineWidth = 0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 4f,
                pathFillType = PathFillType.NonZero,
                trimPathStart = 0f,
                trimPathEnd = 1f,
                trimPathOffset = 0f,
            )
            iconPath(
                pathData = "M12.004,1.258C14.879,1.258 17.211,3.589 17.211,6.465V10.008C17.211,10.375 16.913,10.672 16.546,10.672H15.875C15.508,10.672 15.211,10.375 15.211,10.008V6.465C15.211,4.694 13.775,3.258 12.004,3.258C10.233,3.258 8.797,4.694 8.797,6.465V10.008C8.797,10.375 8.499,10.672 8.132,10.672H7.461C7.094,10.672 6.797,10.375 6.797,10.008V6.465C6.797,3.589 9.128,1.258 12.004,1.258Z",
                fill = SolidColor(Color(0xFF000000)),
                fillAlpha = 0.4f,
                stroke = null,
                strokeAlpha = 0.4f,
                strokeLineWidth = 0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 4f,
                pathFillType = PathFillType.NonZero,
                trimPathStart = 0f,
                trimPathEnd = 1f,
                trimPathOffset = 0f,
            )
        }
    }

    val LockNight: ImageVector by lazy {
        iconVector("CouiIconVectors.LockNight", 24f.dp, 24f.dp, 24f, 24f, tintColor = CouiColor.Night.BLUE) {
            iconPath(
                pathData = "M17.27,11.778C18.137,11.778 18.572,11.778 18.903,11.946C19.195,12.095 19.433,12.333 19.581,12.624C19.75,12.956 19.75,13.39 19.75,14.258V17.534C19.75,19.395 19.75,20.325 19.388,21.035C19.069,21.66 18.561,22.168 17.937,22.487C17.226,22.849 16.296,22.849 14.436,22.849H9.564C7.704,22.849 6.774,22.849 6.063,22.487C5.439,22.168 4.931,21.66 4.612,21.035C4.25,20.325 4.25,19.395 4.25,17.534L4.25,14.258C4.25,13.39 4.25,12.956 4.419,12.624C4.568,12.333 4.805,12.095 5.097,11.946C5.428,11.778 5.863,11.778 6.73,11.778L17.27,11.778Z",
                fill = SolidColor(Color(0xFF000000)),
                fillAlpha = 1f,
                stroke = null,
                strokeAlpha = 1f,
                strokeLineWidth = 0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 4f,
                pathFillType = PathFillType.NonZero,
                trimPathStart = 0f,
                trimPathEnd = 1f,
                trimPathOffset = 0f,
            )
            iconPath(
                pathData = "M12.004,1.258C14.879,1.258 17.211,3.589 17.211,6.465V10.008C17.211,10.375 16.913,10.672 16.546,10.672H15.875C15.508,10.672 15.211,10.375 15.211,10.008V6.465C15.211,4.694 13.775,3.258 12.004,3.258C10.233,3.258 8.797,4.694 8.797,6.465V10.008C8.797,10.375 8.499,10.672 8.132,10.672H7.461C7.094,10.672 6.797,10.375 6.797,10.008V6.465C6.797,3.589 9.128,1.258 12.004,1.258Z",
                fill = SolidColor(Color(0xFF000000)),
                fillAlpha = 0.6f,
                stroke = null,
                strokeAlpha = 0.6f,
                strokeLineWidth = 0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 4f,
                pathFillType = PathFillType.NonZero,
                trimPathStart = 0f,
                trimPathEnd = 1f,
                trimPathOffset = 0f,
            )
        }
    }

    private fun privacyShieldVector(name: String, tintColor: Color): ImageVector =
        iconVector(name, 24f.dp, 24f.dp, 24f, 24f, tintColor = tintColor) {
            iconPath(
                pathData = "M11.478,1.217C11.824,1.167 12.176,1.167 12.521,1.217C12.931,1.276 13.318,1.428 13.818,1.616L13.819,1.615L13.837,1.621L19.155,3.616C19.87,3.884 20.481,4.096 20.944,4.504C21.324,4.839 21.616,5.261 21.796,5.734C22.015,6.31 22,6.957 22,7.721V10.97C22,13.916 21.153,16.269 19.651,18.152C18.165,20.015 16.088,21.353 13.73,22.38C13.325,22.557 12.994,22.707 12.557,22.78C12.222,22.835 11.778,22.835 11.443,22.78C11.006,22.707 10.675,22.557 10.269,22.38C7.911,21.353 5.835,20.015 4.348,18.152C2.847,16.269 2,13.916 2,10.97V7.721C2,6.958 1.985,6.31 2.204,5.734C2.384,5.261 2.676,4.839 3.056,4.504C3.518,4.096 4.13,3.884 4.845,3.616L10.163,1.621C10.672,1.43 11.064,1.277 11.478,1.217ZM17.265,15.309C15.369,11.233 8.624,11.224 6.728,15.309C6.642,15.496 6.682,15.728 6.825,15.876C9.551,18.708 14.442,18.708 17.169,15.876C17.312,15.728 17.353,15.496 17.265,15.309ZM11.997,6C10.714,6 9.707,7.043 9.707,8.566C9.707,10.089 10.714,11.131 11.997,11.131C13.28,11.131 14.288,10.089 14.288,8.566C14.288,7.043 13.28,6 11.997,6Z",
                fill = SolidColor(Color(0xFF000000)),
                fillAlpha = 1f,
                stroke = null,
                strokeAlpha = 1f,
                strokeLineWidth = 0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 4f,
                pathFillType = PathFillType.NonZero,
                trimPathStart = 0f,
                trimPathEnd = 1f,
                trimPathOffset = 0f,
            )
        }

    val UserShieldDay: ImageVector by lazy {
        privacyShieldVector("CouiIconVectors.UserShieldDay", CouiColor.Day.BLUE)
    }
    val UserShieldNight: ImageVector by lazy {
        privacyShieldVector("CouiIconVectors.UserShieldNight", CouiColor.Night.BLUE)
    }

    val SosDay: ImageVector by lazy {
        iconVector("CouiIconVectors.SosDay", 24f.dp, 24f.dp, 24f, 24f, tintColor = CouiColor.Day.RED) {
            iconPath(
                pathData = "M16,4C20.4183,4 24,7.5817 24,12C24,16.4183 20.4183,20 16,20H8C3.5817,20 0,16.4183 0,12C0,7.5817 3.5817,4 8,4H16ZM6.208,8.1436C5.5551,8.1436 5.0331,8.3249 4.6426,8.6875C4.2522,9.0503 4.0568,9.5155 4.0566,10.082V10.1709C4.0567,10.6234 4.1729,11.0069 4.4043,11.3213C4.6393,11.6323 5.0474,11.9663 5.6279,12.3223C6.1529,12.6539 6.5006,12.904 6.6699,13.0732C6.8426,13.2425 6.9287,13.4525 6.9287,13.7012V13.8301C6.9287,14.0339 6.8627,14.195 6.7314,14.3125C6.6036,14.4265 6.4238,14.4834 6.1924,14.4834C6.0232,14.4834 5.8749,14.4503 5.7471,14.3848C5.6227,14.3157 5.5171,14.2191 5.4307,14.0947C5.3443,13.9669 5.2716,13.8201 5.2129,13.6543C5.1576,13.4851 5.1141,13.3952 5.083,13.3848C5.0554,13.3744 5.0261,13.3745 4.9951,13.3848L3.917,13.7939C3.886,13.8077 3.861,13.8303 3.8438,13.8613C3.8266,13.8926 3.8529,14.0396 3.9219,14.3018C3.9944,14.5644 4.1292,14.8205 4.3262,15.0693C4.5266,15.3181 4.7804,15.5122 5.0879,15.6504C5.3955,15.7886 5.7677,15.8574 6.2031,15.8574C6.9043,15.8574 7.4571,15.6603 7.8613,15.2666C8.2655,14.8693 8.4687,14.3524 8.4688,13.7168V13.5967C8.4687,13.1338 8.3435,12.7331 8.0947,12.3945C7.8459,12.0559 7.4364,11.7151 6.8662,11.373C6.341,11.0621 5.9974,10.8283 5.835,10.6729C5.6727,10.5174 5.5909,10.3347 5.5908,10.124V10.0508C5.591,9.8783 5.6464,9.7431 5.7568,9.6465C5.8674,9.5464 6.0181,9.4961 6.208,9.4961C6.3358,9.4961 6.4482,9.5224 6.5449,9.5742C6.6451,9.6226 6.7321,9.6991 6.8047,9.8027C6.877,9.9062 6.9371,10.0219 6.9854,10.1494C7.0372,10.2773 7.0754,10.3471 7.0996,10.3574C7.1272,10.3643 7.1565,10.3604 7.1875,10.3467L8.2354,9.958C8.2662,9.9476 8.289,9.9288 8.3027,9.9014C8.3198,9.8699 8.2921,9.7311 8.2197,9.4863C8.1472,9.2376 8.0204,9.0062 7.8408,8.792C7.6611,8.5743 7.4365,8.4118 7.167,8.3047C6.8975,8.1976 6.5778,8.1436 6.208,8.1436ZM12.0059,8.1436C11.1765,8.1436 10.5133,8.4327 10.0156,9.0098C9.5181,9.5868 9.2687,10.4434 9.2686,11.5801V12.4043C9.2686,13.5514 9.5158,14.4141 10.0098,14.9912C10.504,15.5684 11.1681,15.8574 12.001,15.8574C12.8199,15.8573 13.4804,15.563 13.9814,14.9756C14.4822,14.3881 14.7324,13.5306 14.7324,12.4043V11.5859C14.7324,10.4457 14.4853,9.5869 13.9912,9.0098C13.5005,8.4327 12.8387,8.1436 12.0059,8.1436ZM17.9033,8.1436C17.2504,8.1436 16.7284,8.3249 16.3379,8.6875C15.9475,9.0503 15.7521,9.5155 15.752,10.082V10.1709C15.752,10.6234 15.8682,11.0069 16.0996,11.3213C16.3346,11.6323 16.7418,11.9664 17.3223,12.3223C17.8472,12.6538 18.1948,12.9039 18.3643,13.0732C18.537,13.2425 18.624,13.4524 18.624,13.7012V13.8301C18.624,14.0339 18.558,14.195 18.4268,14.3125C18.2989,14.4265 18.1192,14.4834 17.8877,14.4834C17.7184,14.4834 17.5693,14.4504 17.4414,14.3848C17.3172,14.3157 17.2122,14.2189 17.126,14.0947C17.0396,13.9669 16.9669,13.8201 16.9082,13.6543C16.853,13.4852 16.8094,13.3953 16.7783,13.3848C16.7508,13.3744 16.7214,13.3745 16.6904,13.3848L15.6123,13.7939C15.5813,13.8077 15.5563,13.8303 15.5391,13.8613C15.5218,13.8924 15.5482,14.0394 15.6172,14.3018C15.6898,14.5644 15.8245,14.8205 16.0215,15.0693C16.2219,15.3181 16.4757,15.5122 16.7832,15.6504C17.0908,15.7886 17.463,15.8574 17.8984,15.8574C18.5996,15.8573 19.1524,15.6603 19.5566,15.2666C19.9608,14.8693 20.163,14.3524 20.1631,13.7168V13.5967C20.163,13.1338 20.0388,12.7331 19.79,12.3945C19.5412,12.0559 19.1317,11.7152 18.5615,11.373C18.0363,11.0621 17.6927,10.8284 17.5303,10.6729C17.368,10.5174 17.2862,10.3347 17.2861,10.124V10.0508C17.2863,9.8783 17.3417,9.7431 17.4521,9.6465C17.5627,9.5464 17.7134,9.4961 17.9033,9.4961C18.0311,9.4961 18.1435,9.5224 18.2402,9.5742C18.3404,9.6226 18.4265,9.6991 18.499,9.8027C18.5715,9.9063 18.6323,10.0217 18.6807,10.1494C18.7325,10.2773 18.7707,10.3471 18.7949,10.3574C18.8225,10.3643 18.8518,10.3604 18.8828,10.3467L19.9307,9.958C19.9614,9.9476 19.9843,9.9288 19.998,9.9014C20.0151,9.8699 19.9874,9.7311 19.915,9.4863C19.8425,9.2376 19.7157,9.0062 19.5361,8.792C19.3565,8.5743 19.1318,8.4118 18.8623,8.3047C18.5928,8.1976 18.273,8.1436 17.9033,8.1436ZM12.001,9.6162C12.3395,9.6163 12.6106,9.7563 12.8145,10.0361C13.0182,10.316 13.1201,10.7564 13.1201,11.3574V12.6377C13.1201,13.2493 13.0207,13.6942 12.8203,13.9707C12.6199,14.2471 12.3464,14.3847 12.001,14.3848C11.6623,14.3848 11.3904,14.2471 11.1865,13.9707C10.9827,13.6942 10.8809,13.2494 10.8809,12.6377V11.3574C10.8809,10.739 10.9813,10.2949 11.1816,10.0254C11.3821,9.7525 11.6554,9.6162 12.001,9.6162Z",
                fill = SolidColor(Color(0xFF000000)),
                fillAlpha = 1f,
                stroke = null,
                strokeAlpha = 1f,
                strokeLineWidth = 0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 4f,
                pathFillType = PathFillType.NonZero,
                trimPathStart = 0f,
                trimPathEnd = 1f,
                trimPathOffset = 0f,
            )
        }
    }

    val SosNight: ImageVector by lazy {
        iconVector("CouiIconVectors.SosNight", 24f.dp, 24f.dp, 24f, 24f, tintColor = CouiColor.Night.RED) {
            iconPath(
                pathData = "M16,4C20.4183,4 24,7.5817 24,12C24,16.4183 20.4183,20 16,20H8C3.5817,20 0,16.4183 0,12C0,7.5817 3.5817,4 8,4H16ZM6.208,8.1436C5.5551,8.1436 5.0331,8.3249 4.6426,8.6875C4.2522,9.0503 4.0568,9.5155 4.0566,10.082V10.1709C4.0567,10.6234 4.1729,11.0069 4.4043,11.3213C4.6393,11.6323 5.0474,11.9663 5.6279,12.3223C6.1529,12.6539 6.5006,12.904 6.6699,13.0732C6.8426,13.2425 6.9287,13.4525 6.9287,13.7012V13.8301C6.9287,14.0339 6.8627,14.195 6.7314,14.3125C6.6036,14.4265 6.4238,14.4834 6.1924,14.4834C6.0232,14.4834 5.8749,14.4503 5.7471,14.3848C5.6227,14.3157 5.5171,14.2191 5.4307,14.0947C5.3443,13.9669 5.2716,13.8201 5.2129,13.6543C5.1576,13.4851 5.1141,13.3952 5.083,13.3848C5.0554,13.3744 5.0261,13.3745 4.9951,13.3848L3.917,13.7939C3.886,13.8077 3.861,13.8303 3.8438,13.8613C3.8266,13.8926 3.8529,14.0396 3.9219,14.3018C3.9944,14.5644 4.1292,14.8205 4.3262,15.0693C4.5266,15.3181 4.7804,15.5122 5.0879,15.6504C5.3955,15.7886 5.7677,15.8574 6.2031,15.8574C6.9043,15.8574 7.4571,15.6603 7.8613,15.2666C8.2655,14.8693 8.4687,14.3524 8.4688,13.7168V13.5967C8.4687,13.1338 8.3435,12.7331 8.0947,12.3945C7.8459,12.0559 7.4364,11.7151 6.8662,11.373C6.341,11.0621 5.9974,10.8283 5.835,10.6729C5.6727,10.5174 5.5909,10.3347 5.5908,10.124V10.0508C5.591,9.8783 5.6464,9.7431 5.7568,9.6465C5.8674,9.5464 6.0181,9.4961 6.208,9.4961C6.3358,9.4961 6.4482,9.5224 6.5449,9.5742C6.6451,9.6226 6.7321,9.6991 6.8047,9.8027C6.877,9.9062 6.9371,10.0219 6.9854,10.1494C7.0372,10.2773 7.0754,10.3471 7.0996,10.3574C7.1272,10.3643 7.1565,10.3604 7.1875,10.3467L8.2354,9.958C8.2662,9.9476 8.289,9.9288 8.3027,9.9014C8.3198,9.8699 8.2921,9.7311 8.2197,9.4863C8.1472,9.2376 8.0204,9.0062 7.8408,8.792C7.6611,8.5743 7.4365,8.4118 7.167,8.3047C6.8975,8.1976 6.5778,8.1436 6.208,8.1436ZM12.0059,8.1436C11.1765,8.1436 10.5133,8.4327 10.0156,9.0098C9.5181,9.5868 9.2687,10.4434 9.2686,11.5801V12.4043C9.2686,13.5514 9.5158,14.4141 10.0098,14.9912C10.504,15.5684 11.1681,15.8574 12.001,15.8574C12.8199,15.8573 13.4804,15.563 13.9814,14.9756C14.4822,14.3881 14.7324,13.5306 14.7324,12.4043V11.5859C14.7324,10.4457 14.4853,9.5869 13.9912,9.0098C13.5005,8.4327 12.8387,8.1436 12.0059,8.1436ZM17.9033,8.1436C17.2504,8.1436 16.7284,8.3249 16.3379,8.6875C15.9475,9.0503 15.7521,9.5155 15.752,10.082V10.1709C15.752,10.6234 15.8682,11.0069 16.0996,11.3213C16.3346,11.6323 16.7418,11.9664 17.3223,12.3223C17.8472,12.6538 18.1948,12.9039 18.3643,13.0732C18.537,13.2425 18.624,13.4524 18.624,13.7012V13.8301C18.624,14.0339 18.558,14.195 18.4268,14.3125C18.2989,14.4265 18.1192,14.4834 17.8877,14.4834C17.7184,14.4834 17.5693,14.4504 17.4414,14.3848C17.3172,14.3157 17.2122,14.2189 17.126,14.0947C17.0396,13.9669 16.9669,13.8201 16.9082,13.6543C16.853,13.4852 16.8094,13.3953 16.7783,13.3848C16.7508,13.3744 16.7214,13.3745 16.6904,13.3848L15.6123,13.7939C15.5813,13.8077 15.5563,13.8303 15.5391,13.8613C15.5218,13.8924 15.5482,14.0394 15.6172,14.3018C15.6898,14.5644 15.8245,14.8205 16.0215,15.0693C16.2219,15.3181 16.4757,15.5122 16.7832,15.6504C17.0908,15.7886 17.463,15.8574 17.8984,15.8574C18.5996,15.8573 19.1524,15.6603 19.5566,15.2666C19.9608,14.8693 20.163,14.3524 20.1631,13.7168V13.5967C20.163,13.1338 20.0388,12.7331 19.79,12.3945C19.5412,12.0559 19.1317,11.7152 18.5615,11.373C18.0363,11.0621 17.6927,10.8284 17.5303,10.6729C17.368,10.5174 17.2862,10.3347 17.2861,10.124V10.0508C17.2863,9.8783 17.3417,9.7431 17.4521,9.6465C17.5627,9.5464 17.7134,9.4961 17.9033,9.4961C18.0311,9.4961 18.1435,9.5224 18.2402,9.5742C18.3404,9.6226 18.4265,9.6991 18.499,9.8027C18.5715,9.9063 18.6323,10.0217 18.6807,10.1494C18.7325,10.2773 18.7707,10.3471 18.7949,10.3574C18.8225,10.3643 18.8518,10.3604 18.8828,10.3467L19.9307,9.958C19.9614,9.9476 19.9843,9.9288 19.998,9.9014C20.0151,9.8699 19.9874,9.7311 19.915,9.4863C19.8425,9.2376 19.7157,9.0062 19.5361,8.792C19.3565,8.5743 19.1318,8.4118 18.8623,8.3047C18.5928,8.1976 18.273,8.1436 17.9033,8.1436ZM12.001,9.6162C12.3395,9.6163 12.6106,9.7563 12.8145,10.0361C13.0182,10.316 13.1201,10.7564 13.1201,11.3574V12.6377C13.1201,13.2493 13.0207,13.6942 12.8203,13.9707C12.6199,14.2471 12.3464,14.3847 12.001,14.3848C11.6623,14.3848 11.3904,14.2471 11.1865,13.9707C10.9827,13.6942 10.8809,13.2494 10.8809,12.6377V11.3574C10.8809,10.739 10.9813,10.2949 11.1816,10.0254C11.3821,9.7525 11.6554,9.6162 12.001,9.6162Z",
                fill = SolidColor(Color(0xFF000000)),
                fillAlpha = 1f,
                stroke = null,
                strokeAlpha = 1f,
                strokeLineWidth = 0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 4f,
                pathFillType = PathFillType.NonZero,
                trimPathStart = 0f,
                trimPathEnd = 1f,
                trimPathOffset = 0f,
            )
        }
    }

    val LocationDay: ImageVector by lazy {
        iconVector("CouiIconVectors.LocationDay", 24f.dp, 24f.dp, 24f, 24f, tintColor = CouiColor.Day.YELLOW) {
            iconPath(
                pathData = "M21.276,21.553L19.497,17.995C19.193,17.385 18.569,17 17.888,17H16.032C15.008,18.345 14.003,19.48 13.311,20.222C12.972,20.584 12.494,20.793 12,20.793C11.506,20.793 11.028,20.584 10.69,20.222C9.997,19.48 8.993,18.345 7.968,17H6.113C5.431,17 4.808,17.385 4.503,17.995L2.724,21.553C2.391,22.218 2.875,23 3.618,23H20.382C21.125,23 21.609,22.218 21.276,21.553Z",
                fill = SolidColor(Color(0xFF000000)),
                fillAlpha = 0.4f,
                stroke = null,
                strokeAlpha = 0.4f,
                strokeLineWidth = 0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 4f,
                pathFillType = PathFillType.NonZero,
                trimPathStart = 0f,
                trimPathEnd = 1f,
                trimPathOffset = 0f,
            )
            iconPath(
                pathData = "M12,1C8.134,1 5,4.134 5,8C5,12.177 9.885,17.749 11.493,19.471C11.77,19.767 12.23,19.767 12.507,19.471C14.115,17.749 19,12.177 19,8C19,4.134 15.866,1 12,1ZM12,10C10.895,10 10,9.105 10,8C10,6.895 10.895,6 12,6C13.105,6 14,6.895 14,8C14,9.105 13.105,10 12,10Z",
                fill = SolidColor(Color(0xFF000000)),
                fillAlpha = 1f,
                stroke = null,
                strokeAlpha = 1f,
                strokeLineWidth = 0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 4f,
                pathFillType = PathFillType.NonZero,
                trimPathStart = 0f,
                trimPathEnd = 1f,
                trimPathOffset = 0f,
            )
        }
    }

    val LocationNight: ImageVector by lazy {
        iconVector("CouiIconVectors.LocationNight", 24f.dp, 24f.dp, 24f, 24f, tintColor = CouiColor.Night.YELLOW) {
            iconPath(
                pathData = "M21.276,21.553L19.497,17.995C19.193,17.385 18.569,17 17.888,17H16.032C15.008,18.345 14.003,19.48 13.311,20.222C12.972,20.584 12.494,20.793 12,20.793C11.506,20.793 11.028,20.584 10.69,20.222C9.997,19.48 8.993,18.345 7.968,17H6.113C5.431,17 4.808,17.385 4.503,17.995L2.724,21.553C2.391,22.218 2.875,23 3.618,23H20.382C21.125,23 21.609,22.218 21.276,21.553Z",
                fill = SolidColor(Color(0xFF000000)),
                fillAlpha = 0.6f,
                stroke = null,
                strokeAlpha = 0.6f,
                strokeLineWidth = 0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 4f,
                pathFillType = PathFillType.NonZero,
                trimPathStart = 0f,
                trimPathEnd = 1f,
                trimPathOffset = 0f,
            )
            iconPath(
                pathData = "M12,1C8.134,1 5,4.134 5,8C5,12.177 9.885,17.749 11.493,19.471C11.77,19.767 12.23,19.767 12.507,19.471C14.115,17.749 19,12.177 19,8C19,4.134 15.866,1 12,1ZM12,10C10.895,10 10,9.105 10,8C10,6.895 10.895,6 12,6C13.105,6 14,6.895 14,8C14,9.105 13.105,10 12,10Z",
                fill = SolidColor(Color(0xFF000000)),
                fillAlpha = 1f,
                stroke = null,
                strokeAlpha = 1f,
                strokeLineWidth = 0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 4f,
                pathFillType = PathFillType.NonZero,
                trimPathStart = 0f,
                trimPathEnd = 1f,
                trimPathOffset = 0f,
            )
        }
    }

    val WalletDay: ImageVector by lazy {
        iconVector("CouiIconVectors.WalletDay", 24f.dp, 24f.dp, 24f, 24f, tintColor = CouiColor.Day.BLUE) {
            iconPath(
                pathData = "M5.483,8.087C5.483,7.38 6.056,6.807 6.763,6.807H17.203C17.91,6.807 18.483,7.38 18.483,8.087C18.483,8.263 18.34,8.407 18.163,8.407H5.803C5.627,8.407 5.483,8.263 5.483,8.087Z",
                fill = SolidColor(Color(0xFF000000)),
                fillAlpha = 0.4f,
                stroke = null,
                strokeAlpha = 0.4f,
                strokeLineWidth = 0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 4f,
                pathFillType = PathFillType.NonZero,
                trimPathStart = 0f,
                trimPathEnd = 1f,
                trimPathOffset = 0f,
            )
            iconPath(
                pathData = "M4.983,10.686C4.983,9.979 5.556,9.406 6.263,9.406H17.703C18.41,9.406 18.983,9.979 18.983,10.686C18.983,10.863 18.84,11.006 18.663,11.006H5.303C5.127,11.006 4.983,10.863 4.983,10.686Z",
                fill = SolidColor(Color(0xFF000000)),
                fillAlpha = 0.4f,
                stroke = null,
                strokeAlpha = 0.4f,
                strokeLineWidth = 0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 4f,
                pathFillType = PathFillType.NonZero,
                trimPathStart = 0f,
                trimPathEnd = 1f,
                trimPathOffset = 0f,
            )
            iconPath(
                pathData = "M18.272,3.507C20.316,3.507 21.973,5.164 21.973,7.207V16.793C21.973,18.836 20.316,20.493 18.272,20.493H5.7C3.657,20.493 2,18.836 2,16.793V7.207C2,5.164 3.657,3.507 5.7,3.507H18.272ZM5.7,4.907C4.43,4.907 3.4,5.937 3.4,7.207V12.325C3.652,12.273 3.994,12.271 4.479,12.271H8.43C8.776,12.271 8.949,12.271 9.093,12.323C9.237,12.375 9.716,12.773 10.674,13.566C11.03,13.861 11.487,14.038 11.985,14.038C12.484,14.038 12.941,13.861 13.297,13.566C14.255,12.773 14.734,12.375 14.878,12.323C15.022,12.271 15.195,12.271 15.541,12.271H19.488C19.977,12.271 20.321,12.273 20.573,12.326V7.207C20.573,5.937 19.543,4.907 18.272,4.907H5.7Z",
                fill = SolidColor(Color(0xFF000000)),
                fillAlpha = 1f,
                stroke = null,
                strokeAlpha = 1f,
                strokeLineWidth = 0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 4f,
                pathFillType = PathFillType.NonZero,
                trimPathStart = 0f,
                trimPathEnd = 1f,
                trimPathOffset = 0f,
            )
        }
    }

    val WalletNight: ImageVector by lazy {
        iconVector("CouiIconVectors.WalletNight", 24f.dp, 24f.dp, 24f, 24f, tintColor = CouiColor.Night.BLUE) {
            iconPath(
                pathData = "M5.483,8.087C5.483,7.38 6.056,6.807 6.763,6.807H17.203C17.91,6.807 18.483,7.38 18.483,8.087C18.483,8.263 18.34,8.407 18.163,8.407H5.803C5.627,8.407 5.483,8.263 5.483,8.087Z",
                fill = SolidColor(Color(0xFF000000)),
                fillAlpha = 0.6f,
                stroke = null,
                strokeAlpha = 0.6f,
                strokeLineWidth = 0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 4f,
                pathFillType = PathFillType.NonZero,
                trimPathStart = 0f,
                trimPathEnd = 1f,
                trimPathOffset = 0f,
            )
            iconPath(
                pathData = "M4.983,10.686C4.983,9.979 5.556,9.406 6.263,9.406H17.703C18.41,9.406 18.983,9.979 18.983,10.686C18.983,10.863 18.84,11.006 18.663,11.006H5.303C5.127,11.006 4.983,10.863 4.983,10.686Z",
                fill = SolidColor(Color(0xFF000000)),
                fillAlpha = 0.6f,
                stroke = null,
                strokeAlpha = 0.6f,
                strokeLineWidth = 0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 4f,
                pathFillType = PathFillType.NonZero,
                trimPathStart = 0f,
                trimPathEnd = 1f,
                trimPathOffset = 0f,
            )
            iconPath(
                pathData = "M18.272,3.507C20.316,3.507 21.973,5.164 21.973,7.207V16.793C21.973,18.836 20.316,20.493 18.272,20.493H5.7C3.657,20.493 2,18.836 2,16.793V7.207C2,5.164 3.657,3.507 5.7,3.507H18.272ZM5.7,4.907C4.43,4.907 3.4,5.937 3.4,7.207V12.325C3.652,12.273 3.994,12.271 4.479,12.271H8.43C8.776,12.271 8.949,12.271 9.093,12.323C9.237,12.375 9.716,12.773 10.674,13.566C11.03,13.861 11.487,14.038 11.985,14.038C12.484,14.038 12.941,13.861 13.297,13.566C14.255,12.773 14.734,12.375 14.878,12.323C15.022,12.271 15.195,12.271 15.541,12.271H19.488C19.977,12.271 20.321,12.273 20.573,12.326V7.207C20.573,5.937 19.543,4.907 18.272,4.907H5.7Z",
                fill = SolidColor(Color(0xFF000000)),
                fillAlpha = 1f,
                stroke = null,
                strokeAlpha = 1f,
                strokeLineWidth = 0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 4f,
                pathFillType = PathFillType.NonZero,
                trimPathStart = 0f,
                trimPathEnd = 1f,
                trimPathOffset = 0f,
            )
        }
    }

    val HealthDay: ImageVector by lazy {
        iconVector("CouiIconVectors.HealthDay", 24f.dp, 24f.dp, 24f, 24f, tintColor = CouiColor.Day.GREEN) {
            iconPath(
                pathData = "M12.02,5.146C13.197,5.146 14.121,6.103 14.121,7.5C14.121,8.897 13.197,9.853 12.02,9.853C10.843,9.853 9.919,8.897 9.919,7.5C9.919,6.102 10.844,5.146 12.02,5.146Z",
                fill = SolidColor(Color(0xFF000000)),
                fillAlpha = 1f,
                stroke = null,
                strokeAlpha = 1f,
                strokeLineWidth = 0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 4f,
                pathFillType = PathFillType.NonZero,
                trimPathStart = 0f,
                trimPathEnd = 1f,
                trimPathOffset = 0f,
            )
            iconPath(
                pathData = "M21.54,9.9C21.157,9.9 20.803,9.654 20.681,9.271C19.788,6.437 17.563,4.212 14.729,3.318C14.255,3.169 13.992,2.663 14.142,2.19C14.291,1.716 14.799,1.454 15.27,1.602C18.664,2.671 21.329,5.336 22.398,8.73C22.548,9.203 22.285,9.709 21.81,9.858C21.721,9.887 21.63,9.9 21.54,9.9Z",
                fill = SolidColor(Color(0xFF000000)),
                fillAlpha = 0.4f,
                stroke = null,
                strokeAlpha = 0.4f,
                strokeLineWidth = 0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 4f,
                pathFillType = PathFillType.NonZero,
                trimPathStart = 0f,
                trimPathEnd = 1f,
                trimPathOffset = 0f,
            )
            iconPath(
                pathData = "M12,22.9C5.989,22.9 1.1,18.011 1.1,12C1.1,5.989 5.989,1.1 12,1.1C12.497,1.1 12.9,1.503 12.9,2C12.9,2.497 12.497,2.9 12,2.9C6.982,2.9 2.9,6.982 2.9,12C2.9,17.018 6.982,21.1 12,21.1C17.018,21.1 21.1,17.018 21.1,12C21.1,11.503 21.503,11.1 22,11.1C22.497,11.1 22.9,11.503 22.9,12C22.9,18.011 18.011,22.9 12,22.9Z",
                fill = SolidColor(Color(0xFF000000)),
                fillAlpha = 1f,
                stroke = null,
                strokeAlpha = 1f,
                strokeLineWidth = 0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 4f,
                pathFillType = PathFillType.NonZero,
                trimPathStart = 0f,
                trimPathEnd = 1f,
                trimPathOffset = 0f,
            )
            iconPath(
                pathData = "M16.239,11.682C15.728,11.171 15.042,10.893 14.322,10.893C13.695,10.893 13.092,11.102 12.604,11.497L12,12.1L11.593,11.682C11.082,11.171 10.397,10.893 9.677,10.893C8.957,10.893 8.272,11.171 7.761,11.682C6.716,12.739 6.716,14.458 7.761,15.514L8.179,15.921L10.727,18.469C11.43,19.172 12.569,19.172 13.272,18.469L15.82,15.921L16.238,15.514C17.284,14.458 17.284,12.739 16.239,11.682Z",
                fill = SolidColor(Color(0xFF000000)),
                fillAlpha = 1f,
                stroke = null,
                strokeAlpha = 1f,
                strokeLineWidth = 0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 4f,
                pathFillType = PathFillType.NonZero,
                trimPathStart = 0f,
                trimPathEnd = 1f,
                trimPathOffset = 0f,
            )
        }
    }

    val HealthNight: ImageVector by lazy {
        iconVector("CouiIconVectors.HealthNight", 24f.dp, 24f.dp, 24f, 24f, tintColor = CouiColor.Night.GREEN) {
            iconPath(
                pathData = "M12.02,5.146C13.197,5.146 14.121,6.103 14.121,7.5C14.121,8.897 13.197,9.853 12.02,9.853C10.843,9.853 9.919,8.897 9.919,7.5C9.919,6.102 10.844,5.146 12.02,5.146Z",
                fill = SolidColor(Color(0xFF000000)),
                fillAlpha = 1f,
                stroke = null,
                strokeAlpha = 1f,
                strokeLineWidth = 0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 4f,
                pathFillType = PathFillType.NonZero,
                trimPathStart = 0f,
                trimPathEnd = 1f,
                trimPathOffset = 0f,
            )
            iconPath(
                pathData = "M21.54,9.9C21.157,9.9 20.803,9.654 20.681,9.271C19.788,6.437 17.563,4.212 14.729,3.318C14.255,3.169 13.992,2.663 14.142,2.19C14.291,1.716 14.799,1.454 15.27,1.602C18.664,2.671 21.329,5.336 22.398,8.73C22.548,9.203 22.285,9.709 21.81,9.858C21.721,9.887 21.63,9.9 21.54,9.9Z",
                fill = SolidColor(Color(0xFF000000)),
                fillAlpha = 0.6f,
                stroke = null,
                strokeAlpha = 0.6f,
                strokeLineWidth = 0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 4f,
                pathFillType = PathFillType.NonZero,
                trimPathStart = 0f,
                trimPathEnd = 1f,
                trimPathOffset = 0f,
            )
            iconPath(
                pathData = "M12,22.9C5.989,22.9 1.1,18.011 1.1,12C1.1,5.989 5.989,1.1 12,1.1C12.497,1.1 12.9,1.503 12.9,2C12.9,2.497 12.497,2.9 12,2.9C6.982,2.9 2.9,6.982 2.9,12C2.9,17.018 6.982,21.1 12,21.1C17.018,21.1 21.1,17.018 21.1,12C21.1,11.503 21.503,11.1 22,11.1C22.497,11.1 22.9,11.503 22.9,12C22.9,18.011 18.011,22.9 12,22.9Z",
                fill = SolidColor(Color(0xFF000000)),
                fillAlpha = 1f,
                stroke = null,
                strokeAlpha = 1f,
                strokeLineWidth = 0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 4f,
                pathFillType = PathFillType.NonZero,
                trimPathStart = 0f,
                trimPathEnd = 1f,
                trimPathOffset = 0f,
            )
            iconPath(
                pathData = "M16.239,11.682C15.728,11.171 15.042,10.893 14.322,10.893C13.695,10.893 13.092,11.102 12.604,11.497L12,12.1L11.593,11.682C11.082,11.171 10.397,10.893 9.677,10.893C8.957,10.893 8.272,11.171 7.761,11.682C6.716,12.739 6.716,14.458 7.761,15.514L8.179,15.921L10.727,18.469C11.43,19.172 12.569,19.172 13.272,18.469L15.82,15.921L16.238,15.514C17.284,14.458 17.284,12.739 16.239,11.682Z",
                fill = SolidColor(Color(0xFF000000)),
                fillAlpha = 1f,
                stroke = null,
                strokeAlpha = 1f,
                strokeLineWidth = 0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 4f,
                pathFillType = PathFillType.NonZero,
                trimPathStart = 0f,
                trimPathEnd = 1f,
                trimPathOffset = 0f,
            )
        }
    }

    val GridDay: ImageVector by lazy {
        iconVector("CouiIconVectors.GridDay", 24f.dp, 24f.dp, 24f, 24f, tintColor = CouiColor.Day.GREEN) {
            iconPath(
                pathData = "M14.8,21H19.2C20.194,21 21,20.194 21,19.2V14.8C21,13.806 20.194,13 19.2,13H14.8C13.806,13 13,13.806 13,14.8V19.2C13,20.194 13.806,21 14.8,21Z",
                fill = SolidColor(Color(0xFF000000)),
                fillAlpha = 1f,
                stroke = null,
                strokeAlpha = 1f,
                strokeLineWidth = 0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 4f,
                pathFillType = PathFillType.NonZero,
                trimPathStart = 0f,
                trimPathEnd = 1f,
                trimPathOffset = 0f,
            )
            iconPath(
                pathData = "M4.8,21H9.2C10.194,21 11,20.194 11,19.2V14.8C11,13.806 10.194,13 9.2,13H4.8C3.806,13 3,13.806 3,14.8L3,19.2C3,20.194 3.806,21 4.8,21Z",
                fill = SolidColor(Color(0xFF000000)),
                fillAlpha = 1f,
                stroke = null,
                strokeAlpha = 1f,
                strokeLineWidth = 0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 4f,
                pathFillType = PathFillType.NonZero,
                trimPathStart = 0f,
                trimPathEnd = 1f,
                trimPathOffset = 0f,
            )
            iconPath(
                pathData = "M14.8,11H20.2C21.194,11 22,10.194 22,9.2V3.8C22,2.806 21.194,2 20.2,2H14.8C13.806,2 13,2.806 13,3.8V9.2C13,10.194 13.806,11 14.8,11Z",
                fill = SolidColor(Color(0xFF000000)),
                fillAlpha = 0.4f,
                stroke = null,
                strokeAlpha = 0.4f,
                strokeLineWidth = 0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 4f,
                pathFillType = PathFillType.NonZero,
                trimPathStart = 0f,
                trimPathEnd = 1f,
                trimPathOffset = 0f,
            )
            iconPath(
                pathData = "M4.8,11H9.2C10.194,11 11,10.194 11,9.2V4.8C11,3.806 10.194,3 9.2,3H4.8C3.806,3 3,3.806 3,4.8L3,9.2C3,10.194 3.806,11 4.8,11Z",
                fill = SolidColor(Color(0xFF000000)),
                fillAlpha = 1f,
                stroke = null,
                strokeAlpha = 1f,
                strokeLineWidth = 0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 4f,
                pathFillType = PathFillType.NonZero,
                trimPathStart = 0f,
                trimPathEnd = 1f,
                trimPathOffset = 0f,
            )
        }
    }

    val GridNight: ImageVector by lazy {
        iconVector("CouiIconVectors.GridNight", 24f.dp, 24f.dp, 24f, 24f, tintColor = CouiColor.Night.GREEN) {
            iconPath(
                pathData = "M14.8,21H19.2C20.194,21 21,20.194 21,19.2V14.8C21,13.806 20.194,13 19.2,13H14.8C13.806,13 13,13.806 13,14.8V19.2C13,20.194 13.806,21 14.8,21Z",
                fill = SolidColor(Color(0xFF000000)),
                fillAlpha = 1f,
                stroke = null,
                strokeAlpha = 1f,
                strokeLineWidth = 0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 4f,
                pathFillType = PathFillType.NonZero,
                trimPathStart = 0f,
                trimPathEnd = 1f,
                trimPathOffset = 0f,
            )
            iconPath(
                pathData = "M4.8,21H9.2C10.194,21 11,20.194 11,19.2V14.8C11,13.806 10.194,13 9.2,13H4.8C3.806,13 3,13.806 3,14.8L3,19.2C3,20.194 3.806,21 4.8,21Z",
                fill = SolidColor(Color(0xFF000000)),
                fillAlpha = 1f,
                stroke = null,
                strokeAlpha = 1f,
                strokeLineWidth = 0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 4f,
                pathFillType = PathFillType.NonZero,
                trimPathStart = 0f,
                trimPathEnd = 1f,
                trimPathOffset = 0f,
            )
            iconPath(
                pathData = "M14.8,11H20.2C21.194,11 22,10.194 22,9.2V3.8C22,2.806 21.194,2 20.2,2H14.8C13.806,2 13,2.806 13,3.8V9.2C13,10.194 13.806,11 14.8,11Z",
                fill = SolidColor(Color(0xFF000000)),
                fillAlpha = 0.6f,
                stroke = null,
                strokeAlpha = 0.6f,
                strokeLineWidth = 0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 4f,
                pathFillType = PathFillType.NonZero,
                trimPathStart = 0f,
                trimPathEnd = 1f,
                trimPathOffset = 0f,
            )
            iconPath(
                pathData = "M4.8,11H9.2C10.194,11 11,10.194 11,9.2V4.8C11,3.806 10.194,3 9.2,3H4.8C3.806,3 3,3.806 3,4.8L3,9.2C3,10.194 3.806,11 4.8,11Z",
                fill = SolidColor(Color(0xFF000000)),
                fillAlpha = 1f,
                stroke = null,
                strokeAlpha = 1f,
                strokeLineWidth = 0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 4f,
                pathFillType = PathFillType.NonZero,
                trimPathStart = 0f,
                trimPathEnd = 1f,
                trimPathOffset = 0f,
            )
        }
    }

    private fun batteryVector(name: String, tintColor: Color): ImageVector =
        iconVector(name, 24f.dp, 24f.dp, 24f, 24f, tintColor = tintColor) {
            addGroup(clipPathData = PathParser().parsePathString("M0,0h24v24h-24z").toNodes())
                iconPath(
                    pathData = "M17,8C17.773,8 18.4,8.627 18.4,9.4V14.6C18.4,15.373 17.773,16 17,16H4.101C3.327,16 2.7,15.373 2.7,14.6V9.4C2.7,8.627 3.327,8 4.101,8H17Z",
                    fill = SolidColor(Color(0xFF000000)),
                    fillAlpha = 1f,
                    stroke = null,
                    strokeAlpha = 1f,
                    strokeLineWidth = 0f,
                    strokeLineCap = StrokeCap.Butt,
                    strokeLineJoin = StrokeJoin.Miter,
                    strokeLineMiter = 4f,
                    pathFillType = PathFillType.NonZero,
                    trimPathStart = 0f,
                    trimPathEnd = 1f,
                    trimPathOffset = 0f,
                )
                iconPath(
                    pathData = "M15.598,5.3C16.426,5.3 17.09,5.299 17.625,5.343C18.168,5.387 18.641,5.481 19.077,5.703C19.773,6.058 20.34,6.624 20.694,7.32C20.916,7.756 21.009,8.229 21.054,8.772C21.097,9.308 21.098,9.971 21.098,10.8V13.2C21.098,14.029 21.097,14.692 21.054,15.227C21.009,15.771 20.916,16.244 20.694,16.68C20.34,17.376 19.773,17.942 19.077,18.297C18.641,18.519 18.168,18.613 17.625,18.657C17.09,18.701 16.426,18.7 15.598,18.7H5.497C4.669,18.7 4.005,18.701 3.47,18.657C2.926,18.613 2.453,18.519 2.018,18.297C1.321,17.942 0.755,17.376 0.4,16.68C0.178,16.244 0.084,15.771 0.04,15.227C-0.004,14.692 -0.003,14.029 -0.003,13.2V10.8C-0.003,9.971 -0.004,9.308 0.04,8.772C0.084,8.229 0.178,7.756 0.4,7.32C0.755,6.624 1.321,6.058 2.018,5.703C2.453,5.481 2.926,5.387 3.47,5.343C4.005,5.299 4.669,5.3 5.497,5.3H15.598ZM5.497,6.7C4.646,6.7 4.049,6.7 3.584,6.738C3.127,6.776 2.859,6.846 2.653,6.951C2.221,7.172 1.869,7.523 1.648,7.956C1.544,8.162 1.473,8.43 1.436,8.887C1.398,9.352 1.397,9.948 1.397,10.8V13.2C1.397,14.052 1.398,14.648 1.436,15.113C1.473,15.57 1.544,15.838 1.648,16.044C1.869,16.477 2.221,16.828 2.653,17.049C2.859,17.153 3.127,17.224 3.584,17.262C4.049,17.3 4.646,17.3 5.497,17.3H15.598C16.449,17.3 17.046,17.3 17.511,17.262C17.967,17.224 18.236,17.154 18.441,17.049C18.874,16.828 19.226,16.476 19.446,16.044C19.551,15.838 19.622,15.57 19.659,15.113C19.697,14.648 19.697,14.052 19.697,13.2V10.8C19.697,9.948 19.697,9.352 19.659,8.887C19.622,8.43 19.551,8.162 19.446,7.956C19.226,7.523 18.874,7.172 18.441,6.951C18.236,6.846 17.967,6.776 17.511,6.738C17.046,6.7 16.449,6.7 15.598,6.7H5.497ZM23.303,9.2C23.689,9.2 24.003,9.514 24.003,9.9V14.101C24.003,14.487 23.689,14.8 23.303,14.8C22.916,14.8 22.604,14.487 22.604,14.101V9.9C22.604,9.514 22.916,9.2 23.303,9.2Z",
                    fill = SolidColor(Color(0xFF000000)),
                    fillAlpha = 1f,
                    stroke = null,
                    strokeAlpha = 1f,
                    strokeLineWidth = 0f,
                    strokeLineCap = StrokeCap.Butt,
                    strokeLineJoin = StrokeJoin.Miter,
                    strokeLineMiter = 4f,
                    pathFillType = PathFillType.NonZero,
                    trimPathStart = 0f,
                    trimPathEnd = 1f,
                    trimPathOffset = 0f,
                )
            clearGroup()
        }

    val BatteryDay: ImageVector by lazy {
        batteryVector("CouiIconVectors.BatteryDay", CouiColor.Day.GREEN)
    }
    val BatteryNight: ImageVector by lazy {
        batteryVector("CouiIconVectors.BatteryNight", CouiColor.Night.GREEN)
    }

    val PanelEnterRightDay: ImageVector by lazy {
        iconVector("CouiIconVectors.PanelEnterRightDay", 24f.dp, 24f.dp, 24f, 24f, tintColor = CouiColor.Day.BLUE) {
            iconPath(
                pathData = "M15.299,5.305H22V21.351C22,21.709 21.737,22 21.414,22H15.299V5.305Z",
                fill = SolidColor(Color(0xFF000000)),
                fillAlpha = 0.4f,
                stroke = null,
                strokeAlpha = 0.4f,
                strokeLineWidth = 0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 4f,
                pathFillType = PathFillType.NonZero,
                trimPathStart = 0f,
                trimPathEnd = 1f,
                trimPathOffset = 0f,
            )
            iconPath(
                pathData = "M14,22H12.7C12.313,22 12,21.687 12,21.3V4.8C12,3.253 13.253,2 14.8,2H21.3C21.687,2 22,2.313 22,2.7V4H14.8C14.358,4 14,4.358 14,4.8V22Z",
                fill = SolidColor(Color(0xFF000000)),
                fillAlpha = 1f,
                stroke = null,
                strokeAlpha = 1f,
                strokeLineWidth = 0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 4f,
                pathFillType = PathFillType.NonZero,
                trimPathStart = 0f,
                trimPathEnd = 1f,
                trimPathOffset = 0f,
            )
            iconPath(
                pathData = "M10,9V13",
                fill = SolidColor(Color(0x00000000)),
                fillAlpha = 1f,
                stroke = SolidColor(Color(0xFF000000)),
                strokeAlpha = 1f,
                strokeLineWidth = 1.8f,
                strokeLineCap = StrokeCap.Round,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 4f,
                pathFillType = PathFillType.NonZero,
                trimPathStart = 0f,
                trimPathEnd = 1f,
                trimPathOffset = 0f,
            )
            iconPath(
                pathData = "M7.689,10.283L3.818,7.041C3.427,6.713 2.903,7.133 3.015,7.689C3.153,8.372 3.266,9.162 3.316,10.042H0.957C0.428,10.042 0,10.47 0,10.999C0,11.527 0.428,11.956 0.957,11.956H3.313C3.275,12.632 3.194,13.42 3.017,14.302C2.905,14.857 3.427,15.284 3.817,14.957C5.108,13.877 6.398,12.796 7.689,11.715C8.104,11.368 8.104,10.63 7.689,10.283Z",
                fill = SolidColor(Color(0xFF000000)),
                fillAlpha = 1f,
                stroke = null,
                strokeAlpha = 1f,
                strokeLineWidth = 0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 4f,
                pathFillType = PathFillType.NonZero,
                trimPathStart = 0f,
                trimPathEnd = 1f,
                trimPathOffset = 0f,
            )
        }
    }

    val PanelEnterRightNight: ImageVector by lazy {
        iconVector("CouiIconVectors.PanelEnterRightNight", 24f.dp, 24f.dp, 24f, 24f, tintColor = CouiColor.Night.BLUE) {
            iconPath(
                pathData = "M15.299,5.305H22V21.351C22,21.709 21.737,22 21.414,22H15.299V5.305Z",
                fill = SolidColor(Color(0xFF000000)),
                fillAlpha = 0.6f,
                stroke = null,
                strokeAlpha = 0.6f,
                strokeLineWidth = 0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 4f,
                pathFillType = PathFillType.NonZero,
                trimPathStart = 0f,
                trimPathEnd = 1f,
                trimPathOffset = 0f,
            )
            iconPath(
                pathData = "M14,22H12.7C12.313,22 12,21.687 12,21.3V4.8C12,3.253 13.253,2 14.8,2H21.3C21.687,2 22,2.313 22,2.7V4H14.8C14.358,4 14,4.358 14,4.8V22Z",
                fill = SolidColor(Color(0xFF000000)),
                fillAlpha = 1f,
                stroke = null,
                strokeAlpha = 1f,
                strokeLineWidth = 0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 4f,
                pathFillType = PathFillType.NonZero,
                trimPathStart = 0f,
                trimPathEnd = 1f,
                trimPathOffset = 0f,
            )
            iconPath(
                pathData = "M10,9V13",
                fill = SolidColor(Color(0x00000000)),
                fillAlpha = 1f,
                stroke = SolidColor(Color(0xFF000000)),
                strokeAlpha = 1f,
                strokeLineWidth = 1.8f,
                strokeLineCap = StrokeCap.Round,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 4f,
                pathFillType = PathFillType.NonZero,
                trimPathStart = 0f,
                trimPathEnd = 1f,
                trimPathOffset = 0f,
            )
            iconPath(
                pathData = "M7.689,10.283L3.818,7.041C3.427,6.713 2.903,7.133 3.015,7.689C3.153,8.372 3.266,9.162 3.316,10.042H0.957C0.428,10.042 0,10.47 0,10.999C0,11.527 0.428,11.956 0.957,11.956H3.313C3.275,12.632 3.194,13.42 3.017,14.302C2.905,14.857 3.427,15.284 3.817,14.957C5.108,13.877 6.398,12.796 7.689,11.715C8.104,11.368 8.104,10.63 7.689,10.283Z",
                fill = SolidColor(Color(0xFF000000)),
                fillAlpha = 1f,
                stroke = null,
                strokeAlpha = 1f,
                strokeLineWidth = 0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 4f,
                pathFillType = PathFillType.NonZero,
                trimPathStart = 0f,
                trimPathEnd = 1f,
                trimPathOffset = 0f,
            )
        }
    }

    private fun magicCubeKeyRightVector(name: String, tintColor: Color): ImageVector =
        iconVector(name, 24f.dp, 24f.dp, 24f, 24f, tintColor = tintColor) {
            iconPath(
                pathData = "M8.701,5.305H2V21.351C2,21.709 2.263,22 2.586,22H8.701V5.305Z",
                fill = SolidColor(Color(0xFF000000)),
                fillAlpha = 0.4f,
                stroke = null,
                strokeAlpha = 0.4f,
                strokeLineWidth = 0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 4f,
                pathFillType = PathFillType.NonZero,
                trimPathStart = 0f,
                trimPathEnd = 1f,
                trimPathOffset = 0f,
            )
            iconPath(
                pathData = "M10,22H11.3C11.687,22 12,21.687 12,21.3V4.8C12,3.253 10.747,2 9.2,2H2.7C2.313,2 2,2.313 2,2.7V4H9.2C9.642,4 10,4.358 10,4.8V22Z",
                fill = SolidColor(Color(0xFF000000)),
                fillAlpha = 1f,
                stroke = null,
                strokeAlpha = 1f,
                strokeLineWidth = 0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 4f,
                pathFillType = PathFillType.NonZero,
                trimPathStart = 0f,
                trimPathEnd = 1f,
                trimPathOffset = 0f,
            )
            iconPath(
                pathData = "M14,9V13",
                fill = SolidColor(Color(0x00000000)),
                fillAlpha = 1f,
                stroke = SolidColor(Color(0xFF000000)),
                strokeAlpha = 1f,
                strokeLineWidth = 1.8f,
                strokeLineCap = StrokeCap.Round,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 4f,
                pathFillType = PathFillType.NonZero,
                trimPathStart = 0f,
                trimPathEnd = 1f,
                trimPathOffset = 0f,
            )
            iconPath(
                pathData = "M16.311,10.283L20.182,7.041C20.573,6.713 21.097,7.133 20.985,7.689C20.847,8.372 20.734,9.162 20.684,10.042H23.043C23.572,10.042 24,10.47 24,10.999C24,11.527 23.572,11.956 23.043,11.956H20.687C20.725,12.632 20.806,13.42 20.983,14.302C21.095,14.857 20.573,15.284 20.183,14.957C18.892,13.877 17.602,12.796 16.311,11.715C15.896,11.368 15.896,10.63 16.311,10.283Z",
                fill = SolidColor(Color(0xFF000000)),
                fillAlpha = 1f,
                stroke = null,
                strokeAlpha = 1f,
                strokeLineWidth = 0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 4f,
                pathFillType = PathFillType.NonZero,
                trimPathStart = 0f,
                trimPathEnd = 1f,
                trimPathOffset = 0f,
            )
        }

    val PanelEnterLeftDay: ImageVector by lazy {
        magicCubeKeyRightVector("CouiIconVectors.PanelEnterLeftDay", CouiColor.Day.BLUE)
    }
    val PanelEnterLeftNight: ImageVector by lazy {
        magicCubeKeyRightVector("CouiIconVectors.PanelEnterLeftNight", CouiColor.Night.BLUE)
    }

    private fun coolingFanVector(name: String, tintColor: Color): ImageVector =
        iconVector(name, 24f.dp, 24f.dp, 24f, 24f, tintColor = tintColor) {
            iconPath(
                pathData = "M9.435,12.351C9.797,12.437 9.904,12.974 10.117,14.047L11.42,20.601C11.506,21.034 11.242,21.461 10.815,21.577C10.641,21.625 10.457,21.632 10.28,21.597C8.679,21.272 7.752,20.811 6.326,19.813C5.884,19.504 5.571,19.045 5.43,18.524C5.234,17.807 5.387,17.04 5.842,16.451L8.069,13.565C8.738,12.699 9.073,12.266 9.435,12.351ZM11.791,14.747C11.968,14.42 12.514,14.458 13.605,14.534L20.272,14.999C20.713,15.03 21.055,15.396 21.056,15.838C21.056,16.018 21.014,16.198 20.934,16.359C20.2,17.819 19.513,18.593 18.176,19.707C17.761,20.052 17.236,20.233 16.696,20.233C15.953,20.233 15.252,19.884 14.804,19.291L12.604,16.384C11.944,15.511 11.614,15.074 11.791,14.747ZM3.92,7.76C4.534,7.341 5.309,7.234 6.014,7.472L9.469,8.635C10.506,8.984 11.024,9.158 11.062,9.528C11.1,9.898 10.628,10.174 9.683,10.726L3.915,14.099C3.533,14.323 3.044,14.212 2.795,13.847C2.693,13.697 2.624,13.526 2.6,13.348C2.384,11.729 2.517,10.702 2.994,9.028C3.142,8.51 3.474,8.064 3.92,7.76ZM17.485,5.34C17.648,4.929 18.101,4.714 18.522,4.848C18.694,4.902 18.853,4.995 18.982,5.121C20.151,6.262 20.679,7.154 21.335,8.766C21.538,9.265 21.551,9.821 21.388,10.335C21.162,11.043 20.618,11.605 19.917,11.852L16.479,13.066C15.447,13.431 14.931,13.613 14.673,13.345C14.415,13.077 14.617,12.568 15.02,11.551L17.485,5.34ZM11.932,2.386C12.469,2.348 13.002,2.508 13.439,2.823C14.043,3.258 14.408,3.95 14.426,4.693L14.512,8.338C14.537,9.432 14.55,9.979 14.216,10.141C13.881,10.302 13.46,9.952 12.618,9.253L7.479,4.981C7.14,4.699 7.076,4.201 7.334,3.843C7.439,3.696 7.577,3.575 7.737,3.491C9.185,2.735 10.196,2.509 11.932,2.386Z",
                fill = SolidColor(Color(0xFF000000)),
                fillAlpha = 1f,
                stroke = null,
                strokeAlpha = 1f,
                strokeLineWidth = 0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 4f,
                pathFillType = PathFillType.NonZero,
                trimPathStart = 0f,
                trimPathEnd = 1f,
                trimPathOffset = 0f,
            )
        }

    val FanDay: ImageVector by lazy {
        coolingFanVector("CouiIconVectors.FanDay", CouiColor.Day.BLUE)
    }
    val FanNight: ImageVector by lazy {
        coolingFanVector("CouiIconVectors.FanNight", CouiColor.Night.BLUE)
    }

    private fun atmosphereLightVector(name: String, tintColor: Color): ImageVector =
        iconVector(name, 24f.dp, 24f.dp, 24f, 24f, tintColor = tintColor) {
            iconPath(
                pathData = "M11.999,1.65C17.715,1.65 22.348,6.284 22.348,12C22.348,17.716 17.715,22.349 11.999,22.349C6.283,22.349 1.649,17.715 1.649,12C1.649,6.284 6.283,1.65 11.999,1.65ZM11.999,2.349C6.669,2.349 2.348,6.67 2.348,12C2.348,17.329 6.669,21.65 11.999,21.65C17.328,21.65 21.649,17.329 21.649,12C21.649,6.67 17.328,2.349 11.999,2.349ZM11.999,3.349C16.776,3.349 20.649,7.222 20.649,12C20.649,16.777 16.776,20.65 11.999,20.65C7.222,20.65 3.348,16.777 3.348,12C3.348,7.223 7.222,3.349 11.999,3.349ZM11.999,4.65C7.94,4.65 4.649,7.941 4.649,12C4.649,16.059 7.94,19.349 11.999,19.349C16.058,19.349 19.348,16.059 19.348,12C19.348,7.94 16.058,4.65 11.999,4.65ZM11.999,6C15.312,6 17.998,8.686 17.999,12C17.999,15.313 15.312,18 11.999,18C8.685,17.999 5.999,15.313 5.999,12C5.999,8.686 8.685,6 11.999,6Z",
                fill = SolidColor(Color(0xFF000000)),
                fillAlpha = 1f,
                stroke = null,
                strokeAlpha = 1f,
                strokeLineWidth = 0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 4f,
                pathFillType = PathFillType.NonZero,
                trimPathStart = 0f,
                trimPathEnd = 1f,
                trimPathOffset = 0f,
            )
        }

    val RingsDay: ImageVector by lazy {
        atmosphereLightVector("CouiIconVectors.RingsDay", CouiColor.Day.BLUE)
    }
    val RingsNight: ImageVector by lazy {
        atmosphereLightVector("CouiIconVectors.RingsNight", CouiColor.Night.BLUE)
    }

    val AccessibilityDay: ImageVector by lazy {
        iconVector("CouiIconVectors.AccessibilityDay", 24f.dp, 24f.dp, 24f, 24f, tintColor = CouiColor.Day.ORANGE) {
            iconPath(
                pathData = "M9.645,23C8.534,23 7.633,22.099 7.633,20.988V17.379C7.56,17.335 1.665,12.958 1.665,12.958C1.039,12.594 0.835,11.794 1.199,11.168C1.548,10.542 2.349,10.323 2.975,10.687L8.725,14.006C10.748,15.171 13.252,15.171 15.275,14.006L21.025,10.687C21.651,10.323 22.452,10.542 22.801,11.168C23.165,11.793 22.961,12.594 22.335,12.958C22.335,12.958 16.44,17.335 16.367,17.379V20.988C16.367,22.099 15.466,23 14.355,23H9.645ZM12,12.823C10.399,12.823 9.089,11.513 9.089,9.911C9.089,8.31 10.399,7 12,7C13.601,7 14.911,8.31 14.911,9.911C14.911,11.513 13.601,12.823 12,12.823Z",
                fill = SolidColor(Color(0xFF000000)),
                fillAlpha = 1f,
                stroke = null,
                strokeAlpha = 1f,
                strokeLineWidth = 0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 4f,
                pathFillType = PathFillType.NonZero,
                trimPathStart = 0f,
                trimPathEnd = 1f,
                trimPathOffset = 0f,
            )
            iconPath(
                pathData = "M21.2,8.99C20.851,8.99 20.518,8.784 20.372,8.441C18.942,5.075 15.656,2.9 12,2.9C8.344,2.9 5.058,5.075 3.628,8.441C3.434,8.899 2.901,9.111 2.448,8.918C1.99,8.724 1.777,8.195 1.972,7.738C3.684,3.706 7.62,1.1 12,1.1C16.38,1.1 20.316,3.706 22.028,7.738C22.222,8.195 22.01,8.724 21.552,8.918C21.437,8.967 21.318,8.99 21.2,8.99Z",
                fill = SolidColor(Color(0xFF000000)),
                fillAlpha = 0.4f,
                stroke = null,
                strokeAlpha = 0.4f,
                strokeLineWidth = 0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 4f,
                pathFillType = PathFillType.NonZero,
                trimPathStart = 0f,
                trimPathEnd = 1f,
                trimPathOffset = 0f,
            )
        }
    }

    val AccessibilityNight: ImageVector by lazy {
        iconVector("CouiIconVectors.AccessibilityNight", 24f.dp, 24f.dp, 24f, 24f, tintColor = CouiColor.Night.ORANGE) {
            iconPath(
                pathData = "M9.645,23C8.534,23 7.633,22.099 7.633,20.988V17.379C7.56,17.335 1.665,12.958 1.665,12.958C1.039,12.594 0.835,11.794 1.199,11.168C1.548,10.542 2.349,10.323 2.975,10.687L8.725,14.006C10.748,15.171 13.252,15.171 15.275,14.006L21.025,10.687C21.651,10.323 22.452,10.542 22.801,11.168C23.165,11.793 22.961,12.594 22.335,12.958C22.335,12.958 16.44,17.335 16.367,17.379V20.988C16.367,22.099 15.466,23 14.355,23H9.645ZM12,12.823C10.399,12.823 9.089,11.513 9.089,9.911C9.089,8.31 10.399,7 12,7C13.601,7 14.911,8.31 14.911,9.911C14.911,11.513 13.601,12.823 12,12.823Z",
                fill = SolidColor(Color(0xFF000000)),
                fillAlpha = 1f,
                stroke = null,
                strokeAlpha = 1f,
                strokeLineWidth = 0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 4f,
                pathFillType = PathFillType.NonZero,
                trimPathStart = 0f,
                trimPathEnd = 1f,
                trimPathOffset = 0f,
            )
            iconPath(
                pathData = "M21.2,8.99C20.851,8.99 20.518,8.784 20.372,8.441C18.942,5.075 15.656,2.9 12,2.9C8.344,2.9 5.058,5.075 3.628,8.441C3.434,8.899 2.901,9.111 2.448,8.918C1.99,8.724 1.777,8.195 1.972,7.738C3.684,3.706 7.62,1.1 12,1.1C16.38,1.1 20.316,3.706 22.028,7.738C22.222,8.195 22.01,8.724 21.552,8.918C21.437,8.967 21.318,8.99 21.2,8.99Z",
                fill = SolidColor(Color(0xFF000000)),
                fillAlpha = 0.6f,
                stroke = null,
                strokeAlpha = 0.6f,
                strokeLineWidth = 0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 4f,
                pathFillType = PathFillType.NonZero,
                trimPathStart = 0f,
                trimPathEnd = 1f,
                trimPathOffset = 0f,
            )
        }
    }

    val AIDay: ImageVector by lazy {
        iconVector("CouiIconVectors.AIDay", 24f.dp, 24f.dp, 24f, 24f, tintColor = Color.Unspecified) {
            iconPath(
                pathData = "M5.112,15.321C5.738,15.532 6.165,15.68 6.476,15.797C6.774,15.91 6.943,15.989 7.063,16.057L6.156,17.899L8.167,16.899C8.663,16.652 9.165,16.398 9.677,16.169C10.097,15.98 10.541,15.804 11.014,15.715C11.675,15.592 12.364,15.59 13.028,15.717C12.917,15.95 12.823,16.18 12.74,16.399C12.572,16.838 12.421,17.285 12.277,17.716C11.982,17.694 11.685,17.708 11.394,17.762C11.173,17.804 10.942,17.88 10.529,18.066C10.193,18.217 9.744,18.438 9.088,18.764L7.02,19.793C6.979,19.813 6.841,19.884 6.709,19.936C6.709,19.936 6.706,19.937 6.701,19.939C6.694,19.942 6.686,19.946 6.675,19.95C6.654,19.958 6.625,19.969 6.589,19.981C6.518,20.005 6.421,20.032 6.303,20.053C6.068,20.095 5.748,20.11 5.398,20.006C4.792,19.827 4.311,19.369 4.096,18.779L4.055,18.656L4.039,18.597L4.038,18.593C3.952,18.257 3.971,17.952 4.013,17.729C4.034,17.618 4.061,17.525 4.083,17.458C4.095,17.424 4.106,17.396 4.114,17.376C4.118,17.366 4.121,17.357 4.123,17.351C4.126,17.346 4.126,17.344 4.126,17.343C4.178,17.211 4.249,17.072 4.269,17.031L5.112,15.321ZM15.727,11.014C15.959,11.125 16.19,11.218 16.409,11.301C16.848,11.469 17.295,11.619 17.726,11.763C17.703,12.059 17.717,12.356 17.772,12.648C17.813,12.869 17.889,13.099 18.076,13.512C18.226,13.847 18.447,14.297 18.774,14.953L19.802,17.021C19.823,17.062 19.892,17.201 19.945,17.333H19.946C19.946,17.333 19.947,17.336 19.949,17.34C19.951,17.347 19.955,17.356 19.959,17.367C19.968,17.388 19.979,17.417 19.991,17.452C20.014,17.522 20.042,17.621 20.063,17.738C20.105,17.973 20.12,18.294 20.016,18.644C19.824,19.29 19.316,19.794 18.668,19.985L18.665,19.986L18.607,20.003L18.603,20.003C18.267,20.09 17.962,20.07 17.739,20.028C17.627,20.007 17.534,19.98 17.467,19.958C17.434,19.946 17.405,19.936 17.385,19.928C17.375,19.924 17.367,19.921 17.361,19.919C17.355,19.916 17.353,19.915 17.353,19.915C17.221,19.863 17.082,19.792 17.04,19.772L15.33,18.929C15.541,18.303 15.689,17.876 15.807,17.565C15.92,17.267 15.998,17.097 16.067,16.977L17.909,17.885L16.908,15.875C16.661,15.379 16.407,14.877 16.177,14.365C15.989,13.945 15.813,13.501 15.725,13.028C15.601,12.367 15.6,11.677 15.727,11.014ZM5.454,19.815C5.493,19.827 5.533,19.836 5.571,19.844C5.532,19.836 5.494,19.826 5.454,19.814C5.382,19.792 5.311,19.767 5.243,19.737L5.454,19.815ZM10.472,16.065C10.45,16.073 10.427,16.081 10.405,16.088C10.508,16.052 10.612,16.019 10.717,15.99L10.472,16.065ZM5.449,4.029C5.785,3.943 6.09,3.962 6.313,4.003C6.424,4.024 6.517,4.051 6.584,4.074C6.618,4.085 6.646,4.095 6.666,4.103C6.676,4.107 6.685,4.111 6.691,4.114C6.696,4.116 6.698,4.117 6.699,4.117C6.83,4.169 6.968,4.239 7.01,4.259L8.719,5.101C8.62,5.395 8.538,5.645 8.463,5.86L8.244,6.467C8.132,6.765 8.052,6.933 7.984,7.053L6.142,6.146L7.143,8.158C7.389,8.653 7.644,9.154 7.873,9.667C8.062,10.087 8.237,10.531 8.326,11.005C8.449,11.666 8.451,12.355 8.324,13.018C8.092,12.907 7.861,12.813 7.643,12.73C7.203,12.562 6.756,12.412 6.325,12.268C6.347,11.973 6.334,11.675 6.28,11.383C6.238,11.162 6.161,10.932 5.975,10.519C5.824,10.184 5.604,9.735 5.278,9.079L4.249,7.01C4.229,6.969 4.158,6.831 4.106,6.699C4.105,6.698 4.105,6.695 4.103,6.691C4.1,6.685 4.096,6.676 4.092,6.666C4.084,6.644 4.073,6.615 4.061,6.58C4.037,6.509 4.01,6.411 3.989,6.294C3.947,6.059 3.932,5.738 4.036,5.388C4.227,4.742 4.735,4.237 5.383,4.046L5.386,4.045L5.445,4.03L5.449,4.029ZM6.532,12.205L6.534,12.18C6.535,12.142 6.535,12.103 6.536,12.065C6.535,12.112 6.534,12.158 6.532,12.205ZM6.536,12.021C6.537,11.972 6.534,11.922 6.533,11.873C6.534,11.922 6.537,11.972 6.536,12.021ZM6.514,11.613C6.504,11.524 6.492,11.435 6.476,11.347C6.492,11.435 6.504,11.524 6.514,11.613ZM17.748,3.979C17.983,3.937 18.304,3.922 18.654,4.026C19.259,4.206 19.74,4.663 19.955,5.253L19.994,5.374L19.996,5.377L20.012,5.435L20.013,5.439C20.099,5.775 20.079,6.08 20.037,6.303C20.017,6.415 19.99,6.508 19.967,6.575C19.956,6.608 19.946,6.637 19.938,6.657C19.934,6.667 19.931,6.675 19.928,6.681C19.925,6.688 19.924,6.689 19.924,6.689C19.872,6.821 19.803,6.959 19.783,7.001L18.939,8.711C18.312,8.5 17.885,8.353 17.575,8.235C17.275,8.122 17.106,8.044 16.987,7.976L17.895,6.132L15.884,7.133C15.388,7.38 14.887,7.634 14.374,7.864C13.955,8.052 13.511,8.228 13.037,8.316C12.375,8.44 11.685,8.442 11.021,8.314C11.132,8.082 11.227,7.852 11.311,7.633C11.478,7.194 11.628,6.746 11.773,6.315C12.068,6.338 12.366,6.325 12.658,6.27C12.879,6.229 13.109,6.152 13.522,5.965C13.857,5.814 14.307,5.595 14.963,5.268L17.032,4.24C17.073,4.219 17.211,4.148 17.342,4.096C17.343,4.096 17.345,4.096 17.35,4.093C17.356,4.091 17.366,4.087 17.376,4.083C17.398,4.074 17.427,4.063 17.462,4.051C17.533,4.028 17.631,4 17.748,3.979ZM17.863,5.925L16.158,6.774L17.863,5.926C17.935,5.89 17.989,5.863 18.031,5.846C17.989,5.863 17.935,5.89 17.863,5.925ZM5.84,5.96V5.958C5.837,5.943 5.837,5.93 5.84,5.92C5.846,5.902 5.856,5.886 5.869,5.873C5.881,5.86 5.897,5.85 5.915,5.844C5.926,5.841 5.939,5.841 5.953,5.843H5.915C5.879,5.854 5.851,5.883 5.84,5.92C5.837,5.931 5.837,5.944 5.84,5.96ZM4.998,4.437C4.954,4.465 4.91,4.495 4.869,4.526C4.91,4.495 4.954,4.465 4.998,4.437ZM5.226,4.316C5.164,4.343 5.104,4.373 5.046,4.407C5.104,4.373 5.164,4.343 5.226,4.316ZM6.53,11.807C6.526,11.752 6.523,11.696 6.517,11.64C6.523,11.696 6.526,11.752 6.53,11.807ZM5.84,5.96C5.844,5.977 5.852,5.997 5.863,6.022C5.852,5.997 5.844,5.977 5.84,5.96Z",
                fill = Brush.linearGradient(colorStops = arrayOf(0.013f to Color(0xFFFFB300), 0.481f to Color(0xFFE24B70), 1f to Color(0xFFC851E6)), start = Offset(19.181f, 5.454f), end = Offset(4.55f, 20.085f), tileMode = TileMode.Clamp),
                fillAlpha = 1f,
                stroke = null,
                strokeAlpha = 1f,
                strokeLineWidth = 0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 4f,
                pathFillType = PathFillType.NonZero,
                trimPathStart = 0f,
                trimPathEnd = 1f,
                trimPathOffset = 0f,
            )
            iconPath(
                pathData = "M15.353,13.923C15.376,13.988 15.4,14.052 15.424,14.115C15.388,14.14 15.353,14.166 15.318,14.192C15.353,14.166 15.389,14.141 15.424,14.116C15.491,14.288 15.56,14.453 15.63,14.61C15.822,15.038 16.032,15.461 16.235,15.867C16.01,16.06 15.809,16.28 15.641,16.526C15.514,16.711 15.405,16.928 15.245,17.352C15.114,17.695 14.952,18.17 14.719,18.864L13.984,21.054C13.969,21.097 13.922,21.245 13.866,21.375C13.866,21.375 13.865,21.377 13.862,21.384C13.859,21.39 13.855,21.399 13.851,21.409C13.842,21.43 13.83,21.459 13.813,21.492C13.78,21.559 13.73,21.647 13.661,21.745C13.525,21.941 13.309,22.178 12.988,22.353C12.395,22.674 11.68,22.671 11.087,22.348L11.084,22.347L11.031,22.317L11.028,22.315C10.729,22.138 10.527,21.908 10.399,21.721C10.335,21.627 10.288,21.542 10.256,21.479C10.241,21.447 10.229,21.42 10.22,21.401C10.216,21.391 10.212,21.382 10.209,21.376C10.207,21.37 10.206,21.368 10.206,21.368C10.15,21.238 10.101,21.091 10.086,21.047L9.473,19.241C10.066,18.947 10.472,18.75 10.776,18.614C11.066,18.483 11.24,18.417 11.373,18.381L12.034,20.326L12.749,18.197C12.926,17.672 13.1,17.137 13.3,16.612C13.464,16.183 13.653,15.744 13.925,15.347C14.306,14.791 14.793,14.302 15.353,13.923ZM12.088,20.7L12.035,20.714C12.053,20.714 12.072,20.71 12.088,20.701C12.098,20.696 12.107,20.687 12.116,20.674C12.107,20.687 12.098,20.695 12.088,20.7ZM4.8,9.464C5.094,10.056 5.292,10.462 5.428,10.765C5.559,11.056 5.623,11.231 5.66,11.364L3.714,12.025L5.844,12.74C6.369,12.917 6.903,13.091 7.428,13.291C7.858,13.455 8.297,13.644 8.694,13.916C9.249,14.296 9.736,14.783 10.116,15.342C9.874,15.428 9.645,15.525 9.432,15.62C9.002,15.813 8.579,16.022 8.172,16.225C7.979,16 7.76,15.8 7.515,15.632C7.329,15.505 7.113,15.396 6.689,15.236C6.345,15.105 5.872,14.943 5.177,14.71L2.987,13.975C2.943,13.96 2.795,13.913 2.665,13.857C2.665,13.856 2.663,13.855 2.658,13.853C2.651,13.85 2.642,13.847 2.631,13.842C2.61,13.833 2.582,13.82 2.549,13.804C2.482,13.771 2.393,13.721 2.295,13.653C2.1,13.516 1.863,13.3 1.689,12.98C1.368,12.387 1.369,11.671 1.693,11.078L1.694,11.075L1.869,11.174L1.695,11.075L1.724,11.023L1.726,11.019C1.903,10.72 2.132,10.518 2.32,10.39C2.414,10.326 2.498,10.279 2.562,10.247C2.593,10.232 2.62,10.22 2.64,10.211C2.65,10.207 2.658,10.203 2.664,10.2C2.67,10.198 2.672,10.198 2.672,10.198C2.802,10.141 2.95,10.092 2.994,10.077L4.8,9.464ZM15.876,7.805C16.07,8.03 16.29,8.231 16.535,8.4C16.721,8.527 16.938,8.636 17.362,8.796C17.705,8.926 18.179,9.088 18.873,9.321L21.063,10.057C21.106,10.071 21.254,10.119 21.384,10.175C21.384,10.175 21.386,10.176 21.393,10.179C21.399,10.182 21.408,10.185 21.418,10.19C21.439,10.199 21.468,10.211 21.501,10.228C21.568,10.261 21.656,10.311 21.754,10.379C21.95,10.516 22.188,10.732 22.362,11.053C22.683,11.645 22.68,12.361 22.357,12.953L22.356,12.956L22.326,13.01L22.324,13.013C22.147,13.312 21.917,13.514 21.73,13.642C21.636,13.706 21.551,13.753 21.488,13.784C21.456,13.8 21.43,13.812 21.41,13.821C21.4,13.825 21.391,13.829 21.385,13.831C21.377,13.835 21.377,13.834 21.377,13.834C21.247,13.891 21.1,13.939 21.056,13.954L19.251,14.567C18.958,13.975 18.76,13.569 18.623,13.266C18.492,12.974 18.428,12.799 18.392,12.666L20.336,12.006L18.205,11.292C17.681,11.116 17.146,10.941 16.622,10.741C16.191,10.578 15.753,10.388 15.356,10.115C14.8,9.735 14.311,9.248 13.932,8.689C14.175,8.603 14.405,8.507 14.62,8.411C15.049,8.219 15.47,8.008 15.876,7.805ZM8.58,14.082C8.719,14.177 8.854,14.279 8.984,14.387C8.854,14.279 8.719,14.176 8.58,14.081V14.082ZM1.711,11.566C1.71,11.571 1.709,11.575 1.707,11.58C1.725,11.512 1.747,11.445 1.773,11.379L1.711,11.566ZM22.186,11.149C22.256,11.278 22.308,11.413 22.344,11.552C22.327,11.485 22.306,11.418 22.281,11.353L22.186,11.148C22.177,11.132 22.167,11.117 22.158,11.102C22.167,11.117 22.177,11.132 22.186,11.149ZM1.898,11.121L1.869,11.174L1.899,11.121C1.936,11.059 1.975,11.002 2.016,10.949C1.975,11.002 1.935,11.059 1.898,11.121ZM17.29,8.984C17.638,9.115 18.115,9.279 18.81,9.512L20.999,10.246C21.046,10.262 21.184,10.307 21.305,10.36V10.359C21.184,10.306 21.046,10.262 20.999,10.246L18.81,9.511C18.463,9.394 18.17,9.296 17.92,9.209L17.29,8.983V8.984ZM11.062,1.68C11.617,1.379 12.281,1.362 12.85,1.627L12.962,1.684L12.965,1.685V1.686L13.019,1.715L13.022,1.717C13.321,1.894 13.522,2.123 13.651,2.311C13.715,2.405 13.762,2.489 13.793,2.553C13.809,2.584 13.821,2.611 13.83,2.631C13.834,2.641 13.838,2.649 13.84,2.655C13.842,2.66 13.843,2.662 13.843,2.663C13.9,2.793 13.948,2.94 13.963,2.985L14.577,4.789C13.984,5.083 13.579,5.282 13.276,5.418C12.985,5.549 12.809,5.612 12.676,5.649L12.015,3.705L11.3,5.835C11.124,6.36 10.949,6.894 10.749,7.419C10.585,7.849 10.396,8.288 10.124,8.685C9.744,9.24 9.257,9.727 8.699,10.107C8.613,9.864 8.517,9.635 8.421,9.422C8.229,8.993 8.019,8.57 7.816,8.163C8.04,7.97 8.241,7.751 8.409,7.506C8.536,7.32 8.645,7.104 8.805,6.68C8.935,6.336 9.097,5.863 9.33,5.168L10.066,2.978C10.081,2.934 10.128,2.786 10.184,2.656C10.184,2.656 10.186,2.654 10.188,2.649C10.191,2.642 10.194,2.633 10.199,2.622C10.208,2.601 10.22,2.573 10.237,2.54C10.27,2.473 10.32,2.384 10.388,2.286C10.525,2.091 10.741,1.854 11.062,1.68ZM10.478,2.516C10.407,2.635 10.373,2.722 10.368,2.736C10.316,2.856 10.271,2.994 10.255,3.041V3.042C10.271,2.996 10.315,2.857 10.368,2.737C10.373,2.725 10.406,2.637 10.478,2.516ZM13.277,2.171L13.26,2.153C13.248,2.141 13.235,2.131 13.223,2.119C13.241,2.137 13.26,2.154 13.277,2.171ZM13.151,2.056C13.127,2.036 13.104,2.016 13.078,1.997C13.104,2.016 13.127,2.036 13.151,2.056ZM12.92,1.89C12.935,1.899 12.95,1.909 12.964,1.918C12.95,1.909 12.936,1.898 12.92,1.889L12.867,1.86L12.92,1.89Z",
                fill = Brush.linearGradient(colorStops = arrayOf(0f to Color(0xFF00A1FF), 0.916f to Color(0xFF4964FD)), start = Offset(19.427f, 4.084f), end = Offset(6.736f, 19.419f), tileMode = TileMode.Clamp),
                fillAlpha = 1f,
                stroke = null,
                strokeAlpha = 1f,
                strokeLineWidth = 0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 4f,
                pathFillType = PathFillType.NonZero,
                trimPathStart = 0f,
                trimPathEnd = 1f,
                trimPathOffset = 0f,
            )
        }
    }

    val AINight: ImageVector get() = AIDay

    val GamepadDay: ImageVector by lazy {
        iconVector("CouiIconVectors.GamepadDay", 24f.dp, 24f.dp, 24f, 24f, tintColor = CouiColor.Day.GREEN) {
            iconPath(
                pathData = "M20.222,6.745C19.572,5.818 18.701,5.086 17.581,5.086H16.029C16,5.154 15.978,5.224 15.938,5.287C15.076,6.668 13.605,7.494 12,7.498C10.395,7.494 8.923,6.668 8.062,5.287C8.022,5.224 8,5.154 7.971,5.086H6.419C5.299,5.086 4.428,5.818 3.778,6.745C1.875,9.456 -0.579,18.185 2.323,19.706C3.07,20.098 3.986,19.926 4.518,19.266L6.565,16.742C6.902,16.327 7.403,16.086 7.932,16.086H16.068C16.597,16.086 17.098,16.327 17.435,16.742L19.482,19.266C20.013,19.926 20.93,20.098 21.677,19.706C24.579,18.185 22.125,9.456 20.222,6.745ZM9.988,11.278C9.988,11.556 9.764,11.782 9.488,11.782H8.238V13.04C8.238,13.318 8.014,13.543 7.738,13.543H7.237C6.961,13.543 6.737,13.318 6.737,13.04V11.782H5.488C5.211,11.782 4.988,11.557 4.988,11.278V10.774C4.988,10.496 5.211,10.271 5.488,10.271H6.737V9.013C6.737,8.735 6.961,8.509 7.237,8.509H7.738C8.014,8.509 8.238,8.735 8.238,9.013V10.271H9.488C9.764,10.271 9.988,10.496 9.988,10.774V11.278ZM16.487,12.788C15.521,12.788 14.738,11.999 14.738,11.026C14.738,10.053 15.521,9.265 16.487,9.265C17.454,9.265 18.237,10.053 18.237,11.026C18.237,11.999 17.454,12.788 16.487,12.788Z",
                fill = SolidColor(Color(0xFF000000)),
                fillAlpha = 1f,
                stroke = null,
                strokeAlpha = 1f,
                strokeLineWidth = 0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 4f,
                pathFillType = PathFillType.NonZero,
                trimPathStart = 0f,
                trimPathEnd = 1f,
                trimPathOffset = 0f,
            )
            iconPath(
                pathData = "M9.272,4.087H14.747C15.058,4.087 15.257,4.433 15.092,4.697C14.452,5.719 13.314,6.4 12.013,6.4C10.712,6.4 9.575,5.719 8.928,4.699C8.76,4.435 8.959,4.087 9.272,4.087Z",
                fill = SolidColor(Color(0xFF000000)),
                fillAlpha = 0.4f,
                stroke = null,
                strokeAlpha = 0.4f,
                strokeLineWidth = 0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 4f,
                pathFillType = PathFillType.NonZero,
                trimPathStart = 0f,
                trimPathEnd = 1f,
                trimPathOffset = 0f,
            )
        }
    }

    val GamepadNight: ImageVector by lazy {
        iconVector("CouiIconVectors.GamepadNight", 24f.dp, 24f.dp, 24f, 24f, tintColor = CouiColor.Night.GREEN) {
            iconPath(
                pathData = "M20.222,6.745C19.572,5.818 18.701,5.086 17.581,5.086H16.029C16,5.154 15.978,5.224 15.938,5.287C15.076,6.668 13.605,7.494 12,7.498C10.395,7.494 8.923,6.668 8.062,5.287C8.022,5.224 8,5.154 7.971,5.086H6.419C5.299,5.086 4.428,5.818 3.778,6.745C1.875,9.456 -0.579,18.185 2.323,19.706C3.07,20.098 3.986,19.926 4.518,19.266L6.565,16.742C6.902,16.327 7.403,16.086 7.932,16.086H16.068C16.597,16.086 17.098,16.327 17.435,16.742L19.482,19.266C20.013,19.926 20.93,20.098 21.677,19.706C24.579,18.185 22.125,9.456 20.222,6.745ZM9.988,11.278C9.988,11.556 9.764,11.782 9.488,11.782H8.238V13.04C8.238,13.318 8.014,13.543 7.738,13.543H7.237C6.961,13.543 6.737,13.318 6.737,13.04V11.782H5.488C5.211,11.782 4.988,11.557 4.988,11.278V10.774C4.988,10.496 5.211,10.271 5.488,10.271H6.737V9.013C6.737,8.735 6.961,8.509 7.237,8.509H7.738C8.014,8.509 8.238,8.735 8.238,9.013V10.271H9.488C9.764,10.271 9.988,10.496 9.988,10.774V11.278ZM16.487,12.788C15.521,12.788 14.738,11.999 14.738,11.026C14.738,10.053 15.521,9.265 16.487,9.265C17.454,9.265 18.237,10.053 18.237,11.026C18.237,11.999 17.454,12.788 16.487,12.788Z",
                fill = SolidColor(Color(0xFF000000)),
                fillAlpha = 1f,
                stroke = null,
                strokeAlpha = 1f,
                strokeLineWidth = 0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 4f,
                pathFillType = PathFillType.NonZero,
                trimPathStart = 0f,
                trimPathEnd = 1f,
                trimPathOffset = 0f,
            )
            iconPath(
                pathData = "M9.272,4.087H14.747C15.058,4.087 15.257,4.433 15.092,4.697C14.452,5.719 13.314,6.4 12.013,6.4C10.712,6.4 9.575,5.719 8.928,4.699C8.76,4.435 8.959,4.087 9.272,4.087Z",
                fill = SolidColor(Color(0xFF000000)),
                fillAlpha = 0.6f,
                stroke = null,
                strokeAlpha = 0.6f,
                strokeLineWidth = 0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 4f,
                pathFillType = PathFillType.NonZero,
                trimPathStart = 0f,
                trimPathEnd = 1f,
                trimPathOffset = 0f,
            )
        }
    }

    private fun cloudServiceVector(name: String, tintColor: Color): ImageVector =
        iconVector(name, 24f.dp, 24f.dp, 24f, 24f, tintColor = tintColor) {
            iconPath(
                pathData = "M11.649,3.574C14.635,3.412 17.204,5.347 18.014,8.038C18.073,8.233 18.102,8.33 18.079,8.413C18.058,8.485 18.009,8.546 17.945,8.584C17.871,8.626 17.753,8.621 17.519,8.61C17.417,8.605 17.313,8.602 17.209,8.602C16.708,8.602 16.218,8.659 15.748,8.766C15.512,8.82 15.394,8.847 15.318,8.835C15.235,8.821 15.2,8.805 15.135,8.752C15.076,8.703 15.012,8.583 14.884,8.344C14.282,7.218 13.01,6.481 11.686,6.605C10.14,6.751 8.898,7.998 8.756,9.544C8.73,9.832 8.74,10.114 8.786,10.384C9.538,10.706 10.2,11.2 10.722,11.815C10.806,11.914 10.848,11.963 10.865,12.027C10.879,12.08 10.876,12.146 10.858,12.198C10.836,12.26 10.786,12.31 10.687,12.409L9.412,13.684C9.202,13.894 9.097,13.999 9.01,14.028C8.914,14.061 8.873,14.061 8.776,14.032C8.689,14.006 8.539,13.866 8.242,13.588C7.847,13.218 7.318,12.991 6.736,12.991C5.434,12.991 4.39,14.122 4.539,15.454C4.654,16.482 5.495,17.307 6.526,17.402C7.231,17.467 7.875,17.198 8.324,16.736V16.739L13.577,11.485L13.58,11.488C14.631,10.443 16.122,9.839 17.749,9.987C20.346,10.222 22.392,12.385 22.495,14.991H22.496C22.613,17.973 20.221,20.436 17.265,20.436C15.665,20.436 14.232,19.713 13.272,18.579C13.189,18.48 13.147,18.431 13.13,18.367C13.116,18.313 13.119,18.248 13.137,18.196C13.159,18.134 13.209,18.084 13.308,17.985L14.582,16.712C14.792,16.501 14.897,16.395 14.984,16.366C15.081,16.333 15.121,16.333 15.219,16.363C15.307,16.389 15.456,16.53 15.753,16.81C16.148,17.182 16.68,17.411 17.264,17.411C18.567,17.411 19.609,16.279 19.46,14.947C19.346,13.937 18.535,13.123 17.526,13.006C16.845,12.925 16.22,13.16 15.766,13.58L15.762,13.578L10.435,18.901L10.433,18.904L10.333,19.004L10.33,19.002C9.392,19.889 8.127,20.436 6.736,20.436C3.714,20.436 1.284,17.865 1.515,14.796C1.693,12.445 3.451,10.509 5.723,10.063C5.716,9.864 5.716,9.662 5.729,9.458C5.922,6.298 8.432,3.749 11.649,3.574Z",
                fill = SolidColor(Color(0xFF000000)),
                fillAlpha = 1f,
                stroke = null,
                strokeAlpha = 1f,
                strokeLineWidth = 0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 4f,
                pathFillType = PathFillType.NonZero,
                trimPathStart = 0f,
                trimPathEnd = 1f,
                trimPathOffset = 0f,
            )
        }

    val CloudLinkDay: ImageVector by lazy {
        cloudServiceVector("CouiIconVectors.CloudLinkDay", CouiColor.Day.BLUE)
    }
    val CloudLinkNight: ImageVector by lazy {
        cloudServiceVector("CouiIconVectors.CloudLinkNight", CouiColor.Night.BLUE)
    }

    val SettingDay: ImageVector by lazy {
        iconVector("CouiIconVectors.SettingDay", 24f.dp, 24f.dp, 24f, 24f, tintColor = Color.Unspecified) {
            iconPath(
                pathData = "M12.004,12.004m-4.934,0a4.934,4.934 0,1 1,9.867 0a4.934,4.934 0,1 1,-9.867 0",
                fill = SolidColor(Color(0x00000000)),
                fillAlpha = 1f,
                stroke = SolidColor(Color(0xFF000000)),
                strokeAlpha = 0.2f,
                strokeLineWidth = 1.4801f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 4f,
                pathFillType = PathFillType.NonZero,
                trimPathStart = 0f,
                trimPathEnd = 1f,
                trimPathOffset = 0f,
            )
            iconPath(
                pathData = "M11.28,1.466C11.56,0.844 12.443,0.844 12.724,1.466L13.319,2.785C13.461,3.099 13.874,3.177 14.12,2.934L15.148,1.922C15.634,1.443 16.456,1.761 16.494,2.442L16.575,3.893C16.595,4.238 16.952,4.459 17.269,4.322L18.593,3.749C19.218,3.479 19.87,4.071 19.661,4.719L19.212,6.108C19.107,6.436 19.359,6.767 19.702,6.756L21.15,6.703C21.83,6.678 22.223,7.466 21.795,7.995L20.875,9.129C20.659,9.396 20.773,9.799 21.098,9.912L22.472,10.388C23.114,10.612 23.195,11.486 22.605,11.824L21.34,12.547C21.042,12.719 21.003,13.134 21.265,13.357L22.377,14.302C22.895,14.743 22.654,15.59 21.982,15.691C21.982,15.691 21.982,15.691 21.981,15.691C21.981,15.691 21.98,15.691 21.98,15.691L20.546,15.904C20.545,15.904 20.545,15.904 20.546,15.905C20.546,15.905 20.546,15.905 20.545,15.905C20.206,15.957 20.019,16.33 20.183,16.632L20.879,17.915C20.879,17.916 20.879,17.916 20.879,17.916C20.879,17.917 20.879,17.917 20.879,17.917C21.203,18.515 20.672,19.218 20.008,19.068L18.599,18.749C18.263,18.673 17.952,18.954 17.995,19.296L18.183,20.74C18.27,21.416 17.519,21.88 16.953,21.499L15.758,20.692C15.472,20.5 15.08,20.652 14.998,20.987L14.655,22.396C14.492,23.059 13.624,23.22 13.235,22.66L12.41,21.474C12.213,21.191 11.793,21.191 11.595,21.474L10.771,22.659C10.771,22.66 10.771,22.66 10.77,22.66C10.77,22.66 10.77,22.66 10.769,22.66C10.38,23.219 9.512,23.058 9.35,22.396L9.006,20.987C8.924,20.652 8.532,20.499 8.245,20.692L7.05,21.499C6.484,21.88 5.734,21.416 5.822,20.74L6.008,19.296C6.052,18.954 5.742,18.672 5.406,18.748C5.406,18.748 5.406,18.748 5.406,18.748C5.406,18.748 5.406,18.749 5.406,18.749L3.998,19.068C3.997,19.068 3.997,19.068 3.997,19.068C3.996,19.068 3.996,19.068 3.996,19.068C3.332,19.217 2.802,18.515 3.125,17.917C3.125,17.917 3.125,17.917 3.125,17.916C3.125,17.916 3.125,17.916 3.125,17.915L3.822,16.632C3.985,16.33 3.798,15.956 3.458,15.905L2.024,15.691C1.351,15.59 1.109,14.743 1.627,14.302L2.739,13.357C3.001,13.134 2.963,12.718 2.664,12.547L1.399,11.824C0.809,11.486 0.89,10.612 1.531,10.388L2.905,9.912C3.231,9.799 3.346,9.396 3.129,9.129L2.208,7.995C1.78,7.466 2.174,6.679 2.854,6.703L4.301,6.757C4.645,6.769 4.897,6.436 4.791,6.108L4.343,4.719C4.134,4.071 4.786,3.479 5.411,3.749L6.736,4.322C7.052,4.459 7.41,4.238 7.429,3.893L7.509,2.442C7.547,1.761 8.37,1.443 8.856,1.922L9.883,2.934C10.129,3.177 10.542,3.1 10.685,2.785L11.28,1.466ZM7.148,12.868C6.038,12.836 5.483,12.821 5.17,13.288C4.857,13.755 5.045,14.18 5.423,15.03C6.545,17.554 9.065,19.313 11.994,19.313C12.234,19.313 12.47,19.301 12.704,19.278C13.628,19.186 14.09,19.14 14.341,18.635C14.592,18.13 14.302,17.656 13.721,16.708L12.846,15.281C11.969,13.851 10.431,12.96 8.759,12.913L7.148,12.868ZM14.392,9.484C13.467,11.162 13.463,13.2 14.382,14.885L15.003,16.024C15.527,16.985 15.789,17.466 16.343,17.507C16.896,17.548 17.173,17.182 17.727,16.451C18.644,15.238 19.189,13.727 19.189,12.087C19.189,10.5 18.679,9.033 17.815,7.841C17.271,7.091 16.999,6.715 16.44,6.75C15.88,6.784 15.613,7.269 15.079,8.238L14.392,9.484ZM11.996,11.134C11.483,11.133 11.067,11.552 11.068,12.067C11.068,12.582 11.485,13 11.998,13.001C12.512,13.001 12.929,12.583 12.928,12.068C12.927,11.553 12.51,11.134 11.996,11.134ZM11.994,4.86C9.012,4.86 6.455,6.684 5.365,9.281C5.01,10.127 4.833,10.55 5.147,11.008C5.461,11.466 6.008,11.451 7.103,11.42L8.747,11.374C10.426,11.326 11.964,10.429 12.834,8.991L13.759,7.462C14.329,6.518 14.615,6.045 14.365,5.544C14.116,5.042 13.657,4.994 12.74,4.899C12.495,4.873 12.246,4.86 11.994,4.86Z",
                fill = SolidColor(Color(0xFF404040)),
                fillAlpha = 1f,
                stroke = null,
                strokeAlpha = 1f,
                strokeLineWidth = 0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 4f,
                pathFillType = PathFillType.NonZero,
                trimPathStart = 0f,
                trimPathEnd = 1f,
                trimPathOffset = 0f,
            )
        }
    }

    val SettingNight: ImageVector by lazy {
        iconVector("CouiIconVectors.SettingNight", 24f.dp, 24f.dp, 24f, 24f, tintColor = Color.Unspecified) {
            iconPath(
                pathData = "M12.004,12.004m-4.934,0a4.934,4.934 0,1 1,9.867 0a4.934,4.934 0,1 1,-9.867 0",
                fill = SolidColor(Color(0x00000000)),
                fillAlpha = 1f,
                stroke = SolidColor(Color(0xFFFFFFFF)),
                strokeAlpha = 0.2f,
                strokeLineWidth = 1.4801f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 4f,
                pathFillType = PathFillType.NonZero,
                trimPathStart = 0f,
                trimPathEnd = 1f,
                trimPathOffset = 0f,
            )
            iconPath(
                pathData = "M11.28,1.466C11.561,0.844 12.443,0.844 12.724,1.466L13.319,2.785C13.461,3.1 13.874,3.177 14.121,2.935L15.148,1.922C15.634,1.443 16.457,1.762 16.495,2.443L16.576,3.894C16.595,4.238 16.952,4.459 17.269,4.323L18.593,3.749C19.219,3.479 19.871,4.072 19.662,4.72L19.212,6.109C19.107,6.436 19.359,6.768 19.703,6.756L21.15,6.703C21.83,6.678 22.224,7.467 21.795,7.995L20.876,9.129C20.659,9.397 20.773,9.8 21.098,9.912L22.472,10.389C23.114,10.613 23.195,11.487 22.605,11.825L21.34,12.548C21.042,12.719 21.004,13.135 21.265,13.358L22.378,14.303C22.895,14.744 22.654,15.59 21.983,15.692C21.982,15.692 21.982,15.692 21.982,15.692C21.981,15.692 21.981,15.692 21.981,15.692L20.546,15.905C20.546,15.905 20.546,15.905 20.546,15.905C20.546,15.905 20.546,15.906 20.546,15.906C20.206,15.957 20.02,16.331 20.183,16.633L20.879,17.916C20.879,17.916 20.879,17.917 20.879,17.917C20.879,17.917 20.88,17.917 20.88,17.918C21.203,18.516 20.672,19.218 20.008,19.069L18.599,18.749C18.263,18.673 17.952,18.955 17.996,19.296L18.183,20.741C18.27,21.416 17.519,21.88 16.954,21.499L15.758,20.693C15.472,20.5 15.081,20.652 14.999,20.988L14.655,22.397C14.492,23.059 13.625,23.22 13.235,22.66L12.411,21.475C12.213,21.191 11.793,21.191 11.595,21.475L10.771,22.66C10.771,22.66 10.771,22.66 10.771,22.66C10.77,22.66 10.77,22.661 10.77,22.661C10.38,23.22 9.513,23.059 9.35,22.397L9.006,20.988C8.925,20.652 8.532,20.5 8.246,20.693L7.05,21.499C6.485,21.88 5.735,21.416 5.822,20.741L6.008,19.296C6.052,18.955 5.743,18.673 5.407,18.748C5.406,18.748 5.406,18.749 5.406,18.749C5.407,18.749 5.406,18.749 5.406,18.75L3.998,19.069C3.998,19.069 3.997,19.069 3.997,19.069C3.997,19.069 3.997,19.069 3.996,19.069C3.333,19.218 2.802,18.516 3.125,17.918C3.125,17.917 3.126,17.917 3.126,17.917C3.126,17.917 3.126,17.916 3.126,17.916L3.822,16.633C3.986,16.331 3.799,15.957 3.459,15.906L2.024,15.692C1.351,15.591 1.11,14.744 1.628,14.303L2.74,13.358C3.001,13.135 2.963,12.719 2.665,12.548L1.399,11.825C0.809,11.487 0.89,10.613 1.532,10.389L2.906,9.912C3.231,9.799 3.346,9.396 3.129,9.129L2.209,7.995C1.78,7.467 2.175,6.679 2.854,6.703L4.301,6.757C4.645,6.77 4.897,6.436 4.792,6.109L4.343,4.72C4.134,4.072 4.786,3.479 5.412,3.749L6.736,4.323C7.053,4.459 7.41,4.238 7.429,3.894L7.509,2.443C7.547,1.762 8.37,1.444 8.856,1.922L9.883,2.935C10.13,3.177 10.543,3.1 10.685,2.785L11.28,1.466ZM7.148,12.868C6.038,12.836 5.483,12.82 5.169,13.288C4.856,13.755 5.045,14.18 5.423,15.03C6.545,17.554 9.065,19.313 11.995,19.313C12.234,19.313 12.47,19.301 12.704,19.278C13.628,19.186 14.091,19.14 14.342,18.635C14.592,18.129 14.302,17.656 13.721,16.708L12.846,15.282C11.969,13.851 10.432,12.96 8.759,12.913L7.148,12.868ZM14.392,9.485C13.467,11.163 13.463,13.201 14.381,14.886L15.002,16.024C15.526,16.985 15.788,17.466 16.342,17.507C16.896,17.548 17.172,17.182 17.726,16.451C18.643,15.238 19.188,13.727 19.188,12.086C19.188,10.5 18.679,9.033 17.816,7.842C17.271,7.091 17,6.715 16.44,6.75C15.88,6.784 15.613,7.269 15.079,8.238L14.392,9.485ZM11.997,11.134C11.483,11.134 11.067,11.551 11.068,12.067C11.069,12.582 11.485,13.001 11.999,13.001C12.512,13.002 12.929,12.584 12.928,12.069C12.928,11.553 12.51,11.135 11.997,11.134ZM11.995,4.86C9.012,4.86 6.454,6.684 5.364,9.282C5.009,10.127 4.832,10.55 5.146,11.009C5.46,11.467 6.007,11.451 7.102,11.421L8.747,11.374C10.425,11.327 11.964,10.429 12.835,8.991L13.759,7.461C14.33,6.517 14.615,6.045 14.366,5.543C14.116,5.042 13.657,4.994 12.741,4.898C12.495,4.873 12.247,4.86 11.995,4.86Z",
                fill = SolidColor(Color(0xFFAAAAAA)),
                fillAlpha = 1f,
                stroke = null,
                strokeAlpha = 1f,
                strokeLineWidth = 0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 4f,
                pathFillType = PathFillType.NonZero,
                trimPathStart = 0f,
                trimPathEnd = 1f,
                trimPathOffset = 0f,
            )
        }
    }

    val PhoneDay: ImageVector by lazy {
        iconVector("CouiIconVectors.PhoneDay", 24f.dp, 24f.dp, 24f, 24f, tintColor = Color.Unspecified) {
            iconPath(
                pathData = "M7.7902,2.3906H16.2122A1.3,1.3 0,0 1,17.5122,3.6906V20.3015A1.3,1.3 0,0 1,16.2122,21.6015H7.7902A1.3,1.3 0,0 1,6.4902,20.3015V3.6906A1.3,1.3 0,0 1,7.7902,2.3906Z",
                fill = SolidColor(Color(0xFF000000)),
                fillAlpha = 0.2f,
                stroke = null,
                strokeAlpha = 1f,
                strokeLineWidth = 0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 4f,
                pathFillType = PathFillType.NonZero,
                trimPathStart = 0f,
                trimPathEnd = 1f,
                trimPathOffset = 0f,
            )
            iconPath(
                pathData = "M8.4,2H15.6A2.4,2.4 0,0 1,18,4.4V19.6A2.4,2.4 0,0 1,15.6,22H8.4A2.4,2.4 0,0 1,6,19.6V4.4A2.4,2.4 0,0 1,8.4,2Z",
                fill = SolidColor(Color(0x00000000)),
                fillAlpha = 1f,
                stroke = SolidColor(Color(0xFF404040)),
                strokeAlpha = 1f,
                strokeLineWidth = 1.7f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Round,
                strokeLineMiter = 4f,
                pathFillType = PathFillType.NonZero,
                trimPathStart = 0f,
                trimPathEnd = 1f,
                trimPathOffset = 0f,
            )
            iconPath(
                pathData = "M10,19H14",
                fill = SolidColor(Color(0x00000000)),
                fillAlpha = 1f,
                stroke = SolidColor(Color(0xFF404040)),
                strokeAlpha = 1f,
                strokeLineWidth = 1.7f,
                strokeLineCap = StrokeCap.Round,
                strokeLineJoin = StrokeJoin.Round,
                strokeLineMiter = 4f,
                pathFillType = PathFillType.NonZero,
                trimPathStart = 0f,
                trimPathEnd = 1f,
                trimPathOffset = 0f,
            )
        }
    }

    val PhoneNight: ImageVector by lazy {
        iconVector("CouiIconVectors.PhoneNight", 24f.dp, 24f.dp, 24f, 24f, tintColor = Color.Unspecified) {
            iconPath(
                pathData = "M7.7902,2.3906H16.2122A1.3,1.3 0,0 1,17.5122,3.6906V20.3015A1.3,1.3 0,0 1,16.2122,21.6015H7.7902A1.3,1.3 0,0 1,6.4902,20.3015V3.6906A1.3,1.3 0,0 1,7.7902,2.3906Z",
                fill = SolidColor(Color(0xFFFFFFFF)),
                fillAlpha = 0.12f,
                stroke = null,
                strokeAlpha = 1f,
                strokeLineWidth = 0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 4f,
                pathFillType = PathFillType.NonZero,
                trimPathStart = 0f,
                trimPathEnd = 1f,
                trimPathOffset = 0f,
            )
            iconPath(
                pathData = "M8.4,2H15.6A2.4,2.4 0,0 1,18,4.4V19.6A2.4,2.4 0,0 1,15.6,22H8.4A2.4,2.4 0,0 1,6,19.6V4.4A2.4,2.4 0,0 1,8.4,2Z",
                fill = SolidColor(Color(0x00000000)),
                fillAlpha = 1f,
                stroke = SolidColor(Color(0xFFAAAAAA)),
                strokeAlpha = 1f,
                strokeLineWidth = 1.7f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Round,
                strokeLineMiter = 4f,
                pathFillType = PathFillType.NonZero,
                trimPathStart = 0f,
                trimPathEnd = 1f,
                trimPathOffset = 0f,
            )
            iconPath(
                pathData = "M10,19H14",
                fill = SolidColor(Color(0x00000000)),
                fillAlpha = 1f,
                stroke = SolidColor(Color(0xFFAAAAAA)),
                strokeAlpha = 1f,
                strokeLineWidth = 1.7f,
                strokeLineCap = StrokeCap.Round,
                strokeLineJoin = StrokeJoin.Round,
                strokeLineMiter = 4f,
                pathFillType = PathFillType.NonZero,
                trimPathStart = 0f,
                trimPathEnd = 1f,
                trimPathOffset = 0f,
            )
        }
    }

    private fun accountSyncVector(name: String, tintColor: Color): ImageVector =
        iconVector(name, 24f.dp, 24f.dp, 24f, 24f, tintColor = tintColor) {
            iconPath(
                pathData = "M22,12C22,14.782 20.864,17.3 19.036,19.109C17.227,20.9 14.745,22 12,22C9.255,22 6.773,20.9 4.964,19.109C3.136,17.3 2,14.782 2,12C2,6.473 6.473,2 12,2C17.527,2 22,6.473 22,12Z",
                fill = SolidColor(Color(0x00000000)),
                fillAlpha = 1f,
                stroke = SolidColor(Color(0xFF000000)),
                strokeAlpha = 1f,
                strokeLineWidth = 1.6364f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 4f,
                pathFillType = PathFillType.NonZero,
                trimPathStart = 0f,
                trimPathEnd = 1f,
                trimPathOffset = 0f,
            )
            iconPath(
                pathData = "M12.001,6.545C13.653,6.545 14.951,7.888 14.951,9.85C14.951,11.811 13.653,13.153 12.001,13.153C10.349,13.153 9.051,11.811 9.051,9.85C9.051,7.888 10.349,6.545 12.001,6.545ZM18.786,18.535C16.344,13.284 7.657,13.273 5.215,18.535C5.104,18.776 5.156,19.073 5.34,19.264C8.851,22.912 15.151,22.912 18.662,19.265C18.846,19.073 18.899,18.775 18.786,18.535Z",
                fill = SolidColor(Color(0xFF000000)),
                fillAlpha = 1f,
                stroke = null,
                strokeAlpha = 1f,
                strokeLineWidth = 0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 4f,
                pathFillType = PathFillType.NonZero,
                trimPathStart = 0f,
                trimPathEnd = 1f,
                trimPathOffset = 0f,
            )
        }

    val UserCircleDay: ImageVector by lazy {
        accountSyncVector("CouiIconVectors.UserCircleDay", CouiColor.Day.BLUE)
    }
    val UserCircleNight: ImageVector by lazy {
        accountSyncVector("CouiIconVectors.UserCircleNight", CouiColor.Night.BLUE)
    }

    val GoogleDay: ImageVector by lazy {
        iconVector("CouiIconVectors.GoogleDay", 24f.dp, 24f.dp, 24f, 24f, tintColor = Color.Unspecified) {
            iconPath(
                pathData = "M21.914,10.807C21.887,10.586 21.874,10.476 21.816,10.382C21.766,10.303 21.686,10.232 21.602,10.193C21.501,10.146 21.383,10.146 21.146,10.146H12.668C12.506,10.146 12.424,10.146 12.362,10.178C12.308,10.206 12.263,10.25 12.236,10.305C12.204,10.367 12.204,10.448 12.204,10.61V13.631C12.204,13.793 12.204,13.874 12.236,13.936C12.263,13.991 12.308,14.035 12.362,14.063C12.424,14.095 12.506,14.095 12.668,14.095H17.696C17.455,15.364 16.731,16.44 15.646,17.162C14.736,17.774 13.577,18.145 12.204,18.145C9.551,18.145 7.297,16.356 6.49,13.946H6.478L6.49,13.937C6.286,13.325 6.165,12.677 6.165,12C6.165,11.323 6.286,10.675 6.49,10.063C7.297,7.653 9.551,5.864 12.204,5.864C13.552,5.864 14.765,6.282 15.77,7.09C15.903,7.196 15.969,7.25 16.032,7.265C16.09,7.279 16.143,7.276 16.199,7.256C16.26,7.234 16.316,7.178 16.427,7.067L18.691,4.805C18.809,4.687 18.869,4.628 18.889,4.557C18.907,4.497 18.905,4.427 18.882,4.369C18.855,4.301 18.794,4.249 18.672,4.145C16.952,2.685 14.776,1.805 12.204,1.805C8.215,1.805 4.774,4.094 3.095,7.431C2.399,8.802 2,10.35 2,12C2,13.65 2.399,15.198 3.095,16.569V16.579C4.774,19.906 8.215,22.195 12.204,22.195C14.959,22.195 17.269,21.287 18.957,19.73C20.887,17.95 22,15.337 22,12.232C22,11.744 21.97,11.269 21.914,10.807Z",
                fill = SolidColor(Color(0xFFFC4C53)),
                fillAlpha = 1f,
                stroke = null,
                strokeAlpha = 1f,
                strokeLineWidth = 0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 4f,
                pathFillType = PathFillType.NonZero,
                trimPathStart = 0f,
                trimPathEnd = 1f,
                trimPathOffset = 0f,
            )
            iconPath(
                pathData = "M21.914,10.807C21.887,10.586 21.874,10.476 21.816,10.382C21.766,10.303 21.686,10.232 21.602,10.193C21.501,10.146 21.383,10.146 21.146,10.146H12.668C12.506,10.146 12.424,10.146 12.362,10.178C12.308,10.206 12.263,10.25 12.236,10.305C12.204,10.367 12.204,10.448 12.204,10.61V13.631C12.204,13.793 12.204,13.874 12.236,13.936C12.263,13.991 12.308,14.035 12.362,14.063C12.424,14.095 12.506,14.095 12.668,14.095H17.696C17.455,15.364 16.731,16.44 15.646,17.162C14.736,17.774 13.577,18.145 12.204,18.145C9.551,18.145 7.297,16.356 6.49,13.946H6.478L6.49,13.937C6.286,13.325 6.165,12.677 6.165,12C6.165,11.323 6.286,10.675 6.49,10.063C7.297,7.653 9.551,5.864 12.204,5.864C13.552,5.864 14.765,6.282 15.77,7.09C15.903,7.196 15.969,7.25 16.032,7.265C16.09,7.279 16.143,7.276 16.199,7.256C16.26,7.234 16.316,7.178 16.427,7.067L18.691,4.805C18.809,4.687 18.869,4.628 18.889,4.557C18.907,4.497 18.905,4.427 18.882,4.369C18.855,4.301 18.794,4.249 18.672,4.145C16.952,2.685 14.776,1.805 12.204,1.805C8.215,1.805 4.774,4.094 3.095,7.431C2.399,8.802 2,10.35 2,12C2,13.65 2.399,15.198 3.095,16.569V16.579C4.774,19.906 8.215,22.195 12.204,22.195C14.959,22.195 17.269,21.287 18.957,19.73C20.887,17.95 22,15.337 22,12.232C22,11.744 21.97,11.269 21.914,10.807Z",
                fill = Brush.linearGradient(colorStops = arrayOf(0f to Color(0xFF4285F4), 1f to Color(0x004285F4)), start = Offset(12.95f, 10.211f), end = Offset(12.95f, 7.571f), tileMode = TileMode.Clamp),
                fillAlpha = 1f,
                stroke = null,
                strokeAlpha = 1f,
                strokeLineWidth = 0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 4f,
                pathFillType = PathFillType.NonZero,
                trimPathStart = 0f,
                trimPathEnd = 1f,
                trimPathOffset = 0f,
            )
            iconPath(
                pathData = "M5.935,3.963C7.268,4.341 8.504,5.226 9.491,6.532C8.089,7.264 7.001,8.54 6.49,10.063C6.286,10.675 6.165,11.323 6.165,12C6.165,12.677 6.286,13.326 6.49,13.937L6.478,13.946H6.49C7.152,15.923 8.788,17.481 10.818,17.977C10.482,18.838 10.054,19.628 9.542,20.318C9.218,20.756 8.862,21.148 8.484,21.493C6.14,20.573 4.221,18.81 3.095,16.579V16.569C2.399,15.198 2,13.65 2,12C2,10.35 2.399,8.802 3.095,7.431C3.777,6.074 4.751,4.89 5.935,3.963Z",
                fill = Brush.radialGradient(colorStops = arrayOf(0.368f to Color(0xFFFFCF09), 0.718f to Color(0xB2FFCF09), 1f to Color(0x00FFCF09)), center = Offset(4.455f, 13.457f), radius = 7.194f, tileMode = TileMode.Clamp),
                fillAlpha = 1f,
                stroke = null,
                strokeAlpha = 1f,
                strokeLineWidth = 0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 4f,
                pathFillType = PathFillType.NonZero,
                trimPathStart = 0f,
                trimPathEnd = 1f,
                trimPathOffset = 0f,
            )
            iconPath(
                pathData = "M17.693,14.099C18.074,14.519 18.398,14.978 18.652,15.466C18.977,16.088 20.361,16.742 20.761,17.405C20.283,18.284 19.677,19.066 18.957,19.73C17.269,21.287 14.959,22.195 12.204,22.195C8.918,22.195 6.003,20.641 4.14,18.232C4.999,17.059 6.408,16.029 6.733,15.192C6.778,15.078 6.827,14.967 6.879,14.857C7.894,16.793 9.898,18.145 12.204,18.145C13.577,18.145 14.736,17.775 15.646,17.163C16.729,16.441 17.451,15.367 17.693,14.099ZM12.204,11.711C13.118,11.702 14.05,11.869 14.936,12.214C15.739,12.526 16.48,12.974 17.118,13.532C17.321,13.71 17.511,13.898 17.69,14.095H12.668C12.506,14.095 12.424,14.095 12.362,14.063C12.308,14.035 12.263,13.991 12.235,13.936C12.204,13.874 12.204,13.793 12.204,13.631V11.711Z",
                fill = Brush.radialGradient(colorStops = arrayOf(0.229f to Color(0xFF20BE46), 0.752f to Color(0x8C20BE46), 1f to Color(0x0020BE46)), center = Offset(11.543f, 20.938f), radius = 7.837f, tileMode = TileMode.Clamp),
                fillAlpha = 1f,
                stroke = null,
                strokeAlpha = 1f,
                strokeLineWidth = 0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 4f,
                pathFillType = PathFillType.NonZero,
                trimPathStart = 0f,
                trimPathEnd = 1f,
                trimPathOffset = 0f,
            )
        }
    }

    val GoogleNight: ImageVector get() = GoogleDay

    val CertificateDay: ImageVector by lazy {
        iconVector("CouiIconVectors.CertificateDay", 24f.dp, 24f.dp, 24f, 24f, tintColor = CouiColor.Day.ORANGE) {
            iconPath(
                pathData = "M21.325,5H19.975C19.745,8.24 19.105,13.71 17.315,18.92C17.085,19.62 16.385,20.1 15.635,20.1H5.405C5.295,20.46 5.175,20.82 5.045,21.17C4.895,21.57 5.205,22 5.675,22H18.635C18.925,22 19.195,21.82 19.275,21.57C21.565,14.91 21.935,7.77 21.995,5.63C22.005,5.28 21.695,5 21.325,5Z",
                fill = SolidColor(Color(0xFF000000)),
                fillAlpha = 0.4f,
                stroke = null,
                strokeAlpha = 0.4f,
                strokeLineWidth = 0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 4f,
                pathFillType = PathFillType.NonZero,
                trimPathStart = 0f,
                trimPathEnd = 1f,
                trimPathOffset = 0f,
            )
            iconPath(
                pathData = "M12.584,8.85L10.384,11.059L9.471,10.136C9.267,9.932 8.929,9.932 8.725,10.136C8.522,10.349 8.522,10.686 8.725,10.89L10.012,12.176C10.118,12.283 10.251,12.336 10.384,12.336C10.517,12.336 10.65,12.283 10.757,12.176L13.337,9.604C13.542,9.4 13.542,9.063 13.337,8.85C13.134,8.646 12.788,8.646 12.584,8.85Z",
                fill = SolidColor(Color(0xFF000000)),
                fillAlpha = 1f,
                stroke = null,
                strokeAlpha = 1f,
                strokeLineWidth = 0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 4f,
                pathFillType = PathFillType.NonZero,
                trimPathStart = 0f,
                trimPathEnd = 1f,
                trimPathOffset = 0f,
            )
            iconPath(
                pathData = "M18.323,2H5.266C4.902,2 4.604,2.264 4.594,2.604C4.546,4.758 4.21,12.2 2.041,18.169C1.897,18.575 2.204,19 2.674,19H15.635C15.923,19 16.191,18.821 16.278,18.566C18.563,11.907 18.937,4.767 18.995,2.633C19.004,2.283 18.697,2 18.323,2ZM15.706,10.952L14.863,11.848C14.748,11.963 14.686,12.105 14.686,12.274L14.641,13.507C14.632,13.852 14.358,14.127 14.012,14.136L12.779,14.181C12.619,14.181 12.468,14.243 12.353,14.358L11.457,15.2C11.2,15.44 10.81,15.44 10.553,15.2L9.657,14.358C9.542,14.243 9.391,14.181 9.231,14.181L7.998,14.136C7.652,14.127 7.378,13.852 7.369,13.507L7.324,12.274C7.324,12.105 7.262,11.963 7.147,11.848L6.304,10.952C6.065,10.695 6.065,10.305 6.304,10.048L7.147,9.152C7.262,9.036 7.324,8.886 7.324,8.726L7.369,7.493C7.378,7.147 7.652,6.872 7.998,6.864L9.231,6.819C9.391,6.819 9.542,6.757 9.657,6.642L10.553,5.799C10.81,5.56 11.2,5.56 11.457,5.799L12.353,6.642C12.468,6.757 12.619,6.819 12.779,6.819L14.012,6.864C14.358,6.872 14.632,7.147 14.641,7.493L14.686,8.726C14.686,8.886 14.748,9.036 14.863,9.152L15.706,10.048C15.945,10.305 15.945,10.695 15.706,10.952Z",
                fill = SolidColor(Color(0xFF000000)),
                fillAlpha = 1f,
                stroke = null,
                strokeAlpha = 1f,
                strokeLineWidth = 0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 4f,
                pathFillType = PathFillType.NonZero,
                trimPathStart = 0f,
                trimPathEnd = 1f,
                trimPathOffset = 0f,
            )
        }
    }

    val CertificateNight: ImageVector by lazy {
        iconVector("CouiIconVectors.CertificateNight", 24f.dp, 24f.dp, 24f, 24f, tintColor = CouiColor.Night.ORANGE) {
            iconPath(
                pathData = "M21.325,5H19.975C19.745,8.24 19.105,13.71 17.315,18.92C17.085,19.62 16.385,20.1 15.635,20.1H5.405C5.295,20.46 5.175,20.82 5.045,21.17C4.895,21.57 5.205,22 5.675,22H18.635C18.925,22 19.195,21.82 19.275,21.57C21.565,14.91 21.935,7.77 21.995,5.63C22.005,5.28 21.695,5 21.325,5Z",
                fill = SolidColor(Color(0xFF000000)),
                fillAlpha = 0.6f,
                stroke = null,
                strokeAlpha = 0.6f,
                strokeLineWidth = 0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 4f,
                pathFillType = PathFillType.NonZero,
                trimPathStart = 0f,
                trimPathEnd = 1f,
                trimPathOffset = 0f,
            )
            iconPath(
                pathData = "M12.584,8.85L10.384,11.059L9.471,10.136C9.267,9.932 8.929,9.932 8.725,10.136C8.522,10.349 8.522,10.686 8.725,10.89L10.012,12.176C10.118,12.283 10.251,12.336 10.384,12.336C10.517,12.336 10.65,12.283 10.757,12.176L13.337,9.604C13.542,9.4 13.542,9.063 13.337,8.85C13.134,8.646 12.788,8.646 12.584,8.85Z",
                fill = SolidColor(Color(0xFF000000)),
                fillAlpha = 1f,
                stroke = null,
                strokeAlpha = 1f,
                strokeLineWidth = 0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 4f,
                pathFillType = PathFillType.NonZero,
                trimPathStart = 0f,
                trimPathEnd = 1f,
                trimPathOffset = 0f,
            )
            iconPath(
                pathData = "M18.323,2H5.266C4.902,2 4.604,2.264 4.594,2.604C4.546,4.758 4.21,12.2 2.041,18.169C1.897,18.575 2.204,19 2.674,19H15.635C15.923,19 16.191,18.821 16.278,18.566C18.563,11.907 18.937,4.767 18.995,2.633C19.004,2.283 18.697,2 18.323,2ZM15.706,10.952L14.863,11.848C14.748,11.963 14.686,12.105 14.686,12.274L14.641,13.507C14.632,13.852 14.358,14.127 14.012,14.136L12.779,14.181C12.619,14.181 12.468,14.243 12.353,14.358L11.457,15.2C11.2,15.44 10.81,15.44 10.553,15.2L9.657,14.358C9.542,14.243 9.391,14.181 9.231,14.181L7.998,14.136C7.652,14.127 7.378,13.852 7.369,13.507L7.324,12.274C7.324,12.105 7.262,11.963 7.147,11.848L6.304,10.952C6.065,10.695 6.065,10.305 6.304,10.048L7.147,9.152C7.262,9.036 7.324,8.886 7.324,8.726L7.369,7.493C7.378,7.147 7.652,6.872 7.998,6.864L9.231,6.819C9.391,6.819 9.542,6.757 9.657,6.642L10.553,5.799C10.81,5.56 11.2,5.56 11.457,5.799L12.353,6.642C12.468,6.757 12.619,6.819 12.779,6.819L14.012,6.864C14.358,6.872 14.632,7.147 14.641,7.493L14.686,8.726C14.686,8.886 14.748,9.036 14.863,9.152L15.706,10.048C15.945,10.305 15.945,10.695 15.706,10.952Z",
                fill = SolidColor(Color(0xFF000000)),
                fillAlpha = 1f,
                stroke = null,
                strokeAlpha = 1f,
                strokeLineWidth = 0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 4f,
                pathFillType = PathFillType.NonZero,
                trimPathStart = 0f,
                trimPathEnd = 1f,
                trimPathOffset = 0f,
            )
        }
    }

    val LabDay: ImageVector by lazy {
        iconVector("CouiIconVectors.LabDay", 24f.dp, 24f.dp, 24f, 24f, tintColor = CouiColor.Day.BLUE) {
            iconPath(
                pathData = "M12.159,0.111L12.347,0.619C12.782,1.793 13.707,2.718 14.881,3.153L15.389,3.341C15.537,3.395 15.537,3.604 15.389,3.659L14.881,3.847C13.707,4.282 12.782,5.207 12.347,6.381L12.159,6.889C12.104,7.037 11.896,7.037 11.841,6.889L11.653,6.381C11.218,5.207 10.293,4.282 9.119,3.847L8.611,3.659C8.463,3.604 8.463,3.395 8.611,3.341L9.119,3.153C10.293,2.718 11.218,1.793 11.653,0.619L11.841,0.111C11.896,-0.037 12.104,-0.037 12.159,0.111Z",
                fill = SolidColor(Color(0xFF000000)),
                fillAlpha = 0.4f,
                stroke = null,
                strokeAlpha = 0.4f,
                strokeLineWidth = 0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 4f,
                pathFillType = PathFillType.NonZero,
                trimPathStart = 0f,
                trimPathEnd = 1f,
                trimPathOffset = 0f,
            )
            iconPath(
                pathData = "M21.263,19.989L15.552,13.531C15.199,13.192 15,12.724 15,12.234V10H16C16.552,10 17,9.552 17,9C17,8.448 16.552,8 16,8H8C7.448,8 7,8.448 7,9C7,9.552 7.448,10 8,10H9V12.234C9,12.724 8.801,13.192 8.448,13.531L2.737,19.989C1.686,21.145 2.506,23 4.069,23H19.931C21.493,23 22.314,21.145 21.263,19.989ZM17.276,21H6.723C6.187,21 5.902,20.288 6.257,19.836L8,17.62C10.222,15 12.204,20.37 15.653,17.18L17.742,19.836C18.098,20.288 17.813,21 17.276,21Z",
                fill = SolidColor(Color(0xFF000000)),
                fillAlpha = 1f,
                stroke = null,
                strokeAlpha = 1f,
                strokeLineWidth = 0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 4f,
                pathFillType = PathFillType.NonZero,
                trimPathStart = 0f,
                trimPathEnd = 1f,
                trimPathOffset = 0f,
            )
        }
    }

    val LabNight: ImageVector by lazy {
        iconVector("CouiIconVectors.LabNight", 24f.dp, 24f.dp, 24f, 24f, tintColor = CouiColor.Night.BLUE) {
            iconPath(
                pathData = "M12.159,0.111L12.347,0.619C12.782,1.793 13.707,2.718 14.881,3.153L15.389,3.341C15.537,3.395 15.537,3.604 15.389,3.659L14.881,3.847C13.707,4.282 12.782,5.207 12.347,6.381L12.159,6.889C12.104,7.037 11.896,7.037 11.841,6.889L11.653,6.381C11.218,5.207 10.293,4.282 9.119,3.847L8.611,3.659C8.463,3.604 8.463,3.395 8.611,3.341L9.119,3.153C10.293,2.718 11.218,1.793 11.653,0.619L11.841,0.111C11.896,-0.037 12.104,-0.037 12.159,0.111Z",
                fill = SolidColor(Color(0xFF000000)),
                fillAlpha = 0.6f,
                stroke = null,
                strokeAlpha = 0.6f,
                strokeLineWidth = 0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 4f,
                pathFillType = PathFillType.NonZero,
                trimPathStart = 0f,
                trimPathEnd = 1f,
                trimPathOffset = 0f,
            )
            iconPath(
                pathData = "M21.263,19.989L15.552,13.531C15.199,13.192 15,12.724 15,12.234V10H16C16.552,10 17,9.552 17,9C17,8.448 16.552,8 16,8H8C7.448,8 7,8.448 7,9C7,9.552 7.448,10 8,10H9V12.234C9,12.724 8.801,13.192 8.448,13.531L2.737,19.989C1.686,21.145 2.506,23 4.069,23H19.931C21.493,23 22.314,21.145 21.263,19.989ZM17.276,21H6.723C6.187,21 5.902,20.288 6.257,19.836L8,17.62C10.222,15 12.204,20.37 15.653,17.18L17.742,19.836C18.098,20.288 17.813,21 17.276,21Z",
                fill = SolidColor(Color(0xFF000000)),
                fillAlpha = 1f,
                stroke = null,
                strokeAlpha = 1f,
                strokeLineWidth = 0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 4f,
                pathFillType = PathFillType.NonZero,
                trimPathStart = 0f,
                trimPathEnd = 1f,
                trimPathOffset = 0f,
            )
        }
    }

    val BookOpenDay: ImageVector by lazy {
        iconVector("CouiIconVectors.BookOpenDay", 24f.dp, 24f.dp, 24f, 24f, tintColor = CouiColor.Day.ORANGE) {
            iconPath(
                pathData = "M2.003,7.9C2.003,7.473 2.379,7.149 2.802,7.206C4.041,7.371 5.463,7.649 7.003,8.111C8.757,8.638 10.248,9.28 11.453,9.891V22.891C10.248,22.28 8.757,21.638 7.003,21.111C5.386,20.625 3.9,20.344 2.618,20.182C2.266,20.137 2.003,19.843 2.003,19.488V7.9ZM21.997,7.9V19.488C21.997,19.843 21.734,20.137 21.382,20.182C20.1,20.344 18.614,20.625 16.997,21.111C15.243,21.638 13.752,22.28 12.547,22.891V9.891C13.752,9.28 15.243,8.638 16.997,8.111C18.537,7.649 19.959,7.371 21.198,7.206C21.621,7.149 21.997,7.473 21.997,7.9ZM20.341,14.647C20.29,14.346 19.997,14.141 19.707,14.198C18.726,14.366 17.736,14.6 16.766,14.893C16.158,15.073 15.515,15.294 14.803,15.57C14.52,15.679 14.38,15.998 14.49,16.282C14.574,16.5 14.782,16.632 15.002,16.632C15.069,16.632 15.136,16.621 15.202,16.595C15.884,16.331 16.498,16.119 17.081,15.947C18.011,15.667 18.957,15.443 19.892,15.282C20.192,15.23 20.393,14.946 20.341,14.647ZM20.341,11.308C20.29,11.008 19.997,10.802 19.707,10.859C18.726,11.027 17.736,11.262 16.765,11.554C16.148,11.738 15.506,11.959 14.803,12.231C14.52,12.341 14.38,12.659 14.49,12.943C14.574,13.161 14.782,13.294 15.002,13.294C15.069,13.294 15.136,13.282 15.202,13.257C15.875,12.996 16.489,12.784 17.081,12.608C18.011,12.328 18.957,12.104 19.892,11.943C20.192,11.891 20.393,11.607 20.341,11.308Z",
                fill = SolidColor(Color(0xFF000000)),
                fillAlpha = 1f,
                stroke = null,
                strokeAlpha = 1f,
                strokeLineWidth = 0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 4f,
                pathFillType = PathFillType.NonZero,
                trimPathStart = 0f,
                trimPathEnd = 1f,
                trimPathOffset = 0f,
            )
            iconPath(
                pathData = "M14.146,1.109H10.03C8.405,1.109 6.949,2.326 6.864,3.948C6.774,5.678 8.15,7.109 9.86,7.109H10.359L11.616,8.639C11.816,8.883 12.189,8.883 12.389,8.639L13.647,7.109H13.976C15.6,7.109 17.056,5.893 17.141,4.271C17.232,2.541 15.856,1.109 14.146,1.109Z",
                fill = SolidColor(Color(0xFF000000)),
                fillAlpha = 0.4f,
                stroke = null,
                strokeAlpha = 0.4f,
                strokeLineWidth = 0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 4f,
                pathFillType = PathFillType.NonZero,
                trimPathStart = 0f,
                trimPathEnd = 1f,
                trimPathOffset = 0f,
            )
        }
    }

    val BookOpenNight: ImageVector by lazy {
        iconVector("CouiIconVectors.BookOpenNight", 24f.dp, 24f.dp, 24f, 24f, tintColor = CouiColor.Night.ORANGE) {
            iconPath(
                pathData = "M2.003,7.9C2.003,7.473 2.379,7.149 2.802,7.206C4.041,7.371 5.463,7.649 7.003,8.111C8.757,8.638 10.248,9.28 11.453,9.891V22.891C10.248,22.28 8.757,21.638 7.003,21.111C5.386,20.625 3.9,20.344 2.618,20.182C2.266,20.137 2.003,19.843 2.003,19.488V7.9ZM21.997,7.9V19.488C21.997,19.843 21.734,20.137 21.382,20.182C20.1,20.344 18.614,20.625 16.997,21.111C15.243,21.638 13.752,22.28 12.547,22.891V9.891C13.752,9.28 15.243,8.638 16.997,8.111C18.537,7.649 19.959,7.371 21.198,7.206C21.621,7.149 21.997,7.473 21.997,7.9ZM20.341,14.647C20.29,14.346 19.997,14.141 19.707,14.198C18.726,14.366 17.736,14.6 16.766,14.893C16.158,15.073 15.515,15.294 14.803,15.57C14.52,15.679 14.38,15.998 14.49,16.282C14.574,16.5 14.782,16.632 15.002,16.632C15.069,16.632 15.136,16.621 15.202,16.595C15.884,16.331 16.498,16.119 17.081,15.947C18.011,15.667 18.957,15.443 19.892,15.282C20.192,15.23 20.393,14.946 20.341,14.647ZM20.341,11.308C20.29,11.008 19.997,10.802 19.707,10.859C18.726,11.027 17.736,11.262 16.765,11.554C16.148,11.738 15.506,11.959 14.803,12.231C14.52,12.341 14.38,12.659 14.49,12.943C14.574,13.161 14.782,13.294 15.002,13.294C15.069,13.294 15.136,13.282 15.202,13.257C15.875,12.996 16.489,12.784 17.081,12.608C18.011,12.328 18.957,12.104 19.892,11.943C20.192,11.891 20.393,11.607 20.341,11.308Z",
                fill = SolidColor(Color(0xFF000000)),
                fillAlpha = 1f,
                stroke = null,
                strokeAlpha = 1f,
                strokeLineWidth = 0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 4f,
                pathFillType = PathFillType.NonZero,
                trimPathStart = 0f,
                trimPathEnd = 1f,
                trimPathOffset = 0f,
            )
            iconPath(
                pathData = "M14.146,1.109H10.03C8.405,1.109 6.949,2.326 6.864,3.948C6.774,5.678 8.15,7.109 9.86,7.109H10.359L11.616,8.639C11.816,8.883 12.189,8.883 12.389,8.639L13.647,7.109H13.976C15.6,7.109 17.056,5.893 17.141,4.271C17.232,2.541 15.856,1.109 14.146,1.109Z",
                fill = SolidColor(Color(0xFF000000)),
                fillAlpha = 0.6f,
                stroke = null,
                strokeAlpha = 0.6f,
                strokeLineWidth = 0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 4f,
                pathFillType = PathFillType.NonZero,
                trimPathStart = 0f,
                trimPathEnd = 1f,
                trimPathOffset = 0f,
            )
        }
    }

    internal fun appUpdate(backgroundColor: Color): ImageVector {
        return iconVector("CouiIconVectors.AppUpdate", 32f.dp, 32f.dp, 32f, 32f, tintColor = Color.Unspecified) {
            iconPath(
                pathData = "M16,0a16,16 0,0 1 0,32a16,16 0,0 1 0,-32z",
                fill = SolidColor(backgroundColor),
                fillAlpha = 1f,
                stroke = null,
                strokeAlpha = 1f,
                strokeLineWidth = 0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 4f,
                pathFillType = PathFillType.NonZero,
                trimPathStart = 0f,
                trimPathEnd = 1f,
                trimPathOffset = 0f,
            )
            iconPath(
                pathData = "M16,16m10,-0a10,10 0,1 1,-20 -0a10,10 0,1 1,20 -0",
                fill = SolidColor(Color(0x00000000)),
                fillAlpha = 1f,
                stroke = SolidColor(Color(0xFFFFFFFF)),
                strokeAlpha = 1f,
                strokeLineWidth = 1.4f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Round,
                strokeLineMiter = 4f,
                pathFillType = PathFillType.NonZero,
                trimPathStart = 0f,
                trimPathEnd = 1f,
                trimPathOffset = 0f,
            )
            iconPath(
                pathData = "M16,6C10.477,6 6,10.477 6,16C6,21.523 10.477,26 16,26C21.523,26 26,21.523 26,16C26,10.477 21.523,6 16,6ZM20.707,14.293L16.707,10.293C16.317,9.902 15.683,9.902 15.293,10.293L11.293,14.293C10.902,14.683 10.902,15.317 11.293,15.707C11.683,16.098 12.317,16.098 12.707,15.707L15,13.414L15,21C15,21.552 15.448,22 16,22C16.552,22 17,21.552 17,21L17,13.414L19.293,15.707C19.683,16.098 20.317,16.098 20.707,15.707C21.098,15.317 21.098,14.683 20.707,14.293Z",
                fill = SolidColor(Color(0xFFFFFFFF)),
                fillAlpha = 1f,
                stroke = null,
                strokeAlpha = 1f,
                strokeLineWidth = 0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 4f,
                pathFillType = PathFillType.EvenOdd,
                trimPathStart = 0f,
                trimPathEnd = 1f,
                trimPathOffset = 0f,
            )
        }
    }


    val AppUpdateDay: ImageVector by lazy { appUpdate(CouiColor.Day.GREEN) }
    val AppUpdateNight: ImageVector by lazy { appUpdate(CouiColor.Night.GREEN) }
}
private fun iconVector(
    name: String,
    width: androidx.compose.ui.unit.Dp,
    height: androidx.compose.ui.unit.Dp,
    viewportWidth: Float,
    viewportHeight: Float,
    tintColor: Color,
    content: ImageVector.Builder.() -> Unit,
): ImageVector = ImageVector.Builder(
    name,
    width,
    height,
    viewportWidth,
    viewportHeight,
    tintColor = tintColor,
    autoMirror = false,
).apply(content).build()

private fun ImageVector.Builder.iconPath(
    pathData: String, fill: Brush?, fillAlpha: Float, stroke: Brush?, strokeAlpha: Float,
    strokeLineWidth: Float, strokeLineCap: StrokeCap, strokeLineJoin: StrokeJoin, strokeLineMiter: Float,
    pathFillType: PathFillType, trimPathStart: Float, trimPathEnd: Float, trimPathOffset: Float, name: String = "",
) {
    addPath(
        pathData = PathParser().parsePathString(pathData).toNodes(), pathFillType = pathFillType, name = name,
        fill = fill, fillAlpha = fillAlpha, stroke = stroke, strokeAlpha = strokeAlpha,
        strokeLineWidth = strokeLineWidth, strokeLineCap = strokeLineCap, strokeLineJoin = strokeLineJoin,
        strokeLineMiter = strokeLineMiter, trimPathStart = trimPathStart, trimPathEnd = trimPathEnd, trimPathOffset = trimPathOffset,
    )
}

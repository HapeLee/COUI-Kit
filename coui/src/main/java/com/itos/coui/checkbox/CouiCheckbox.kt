package com.itos.coui.checkbox

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathMeasure
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawOutline
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.vector.PathParser
import androidx.compose.ui.unit.dp

data class CouiCheckboxColors(
    val checked: Color,
    val unchecked: Color,
    val disabledChecked: Color,
    val disabledUnchecked: Color,
    val checkmark: Color,
    val disabledCheckmark: Color,
)

private val CheckboxEasing = CubicBezierEasing(0.3f, 0f, 0.1f, 1f)

/** 承接短信 coui_btn_check 的几何和切换动效; 点击和无障碍语义由宿主提供。 */
@Composable
fun CouiCheckbox(
    modifier: Modifier = Modifier,
    checked: Boolean,
    enabled: Boolean = true,
    shape: Shape,
    borderShape: Shape,
    colors: CouiCheckboxColors,
) {
    val checkedPath = remember {
        PathParser().parsePathString(
            "M10.831,14.318L17.033,8L18,8.985L11.315,15.796C11.047,16.068 10.614,16.068 " +
                "10.347,15.796L7,12.386L7.967,11.401L10.831,14.318Z",
        ).toPath()
    }
    val fill by animateFloatAsState(
        targetValue = if (checked) 1f else 0f,
        animationSpec = tween(
            durationMillis = if (enabled) 150 else 0,
            delayMillis = if (enabled && !checked) 150 else 0,
            easing = CheckboxEasing,
        ),
        label = "couiCheckboxFill",
    )
    val checkProgress by animateFloatAsState(
        targetValue = if (checked) 1f else 0f,
        animationSpec = tween(if (enabled) 300 else 0, easing = CheckboxEasing),
        label = "couiCheckboxCheck",
    )
    val pulse = remember { Animatable(1f) }
    var previousChecked by remember { mutableStateOf(checked) }
    LaunchedEffect(checked, enabled) {
        val changed = previousChecked != checked
        previousChecked = checked
        if (enabled && changed) {
            pulse.animateTo(1f, keyframes {
                durationMillis = 300
                pulse.value at 0 using CheckboxEasing
                1.08f at 150 using CheckboxEasing
                1f at 300
            })
        } else {
            pulse.snapTo(1f)
        }
    }
    Canvas(modifier.size(24.dp)) {
        val unit = size.minDimension / 24f
        val fillColor = if (enabled) colors.checked else colors.disabledChecked
        val borderColor = if (enabled) colors.unchecked else colors.disabledUnchecked
        val checkColor = if (enabled) colors.checkmark else colors.disabledCheckmark
        scale(pulse.value) {
            translate(3.8f * unit, 3.799f * unit) {
                drawOutline(
                    outline = borderShape.createOutline(Size(16.4f * unit, 16.4f * unit), layoutDirection, this),
                    color = borderColor,
                    style = Stroke((if (pulse.isRunning) 1.8f else 1.6f) * unit),
                )
            }
            translate(3f * unit, 3f * unit) {
                drawOutline(
                    outline = shape.createOutline(Size(18f * unit, 18f * unit), layoutDirection, this),
                    color = fillColor,
                    alpha = fill,
                )
            }
            val check = Path().apply {
                moveTo((12f - 14.12f / 3f) * unit, (12f - 0.236f / 3f) * unit)
                lineTo((12f - 3.44f / 3f) * unit, (12f + 10.454f / 3f) * unit)
                lineTo((12f + 16.97f / 3f) * unit, (12f - 10.146f / 3f) * unit)
            }
            val measure = PathMeasure().apply { setPath(check, false) }
            val segment = Path()
            measure.getSegment(0f, measure.length * checkProgress, segment)
            if (checkProgress == 1f && !pulse.isRunning) {
                scale(unit, unit, pivot = Offset.Zero) {
                    drawPath(checkedPath, color = checkColor)
                }
            } else {
                drawPath(
                    path = segment,
                    color = checkColor,
                    style = Stroke(width = 1.3f * unit, cap = StrokeCap.Butt, join = StrokeJoin.Round),
                )
            }
        }
    }
}

package com.itos.coui.demo

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.snap
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalRippleThemeConfiguration
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RippleThemeConfiguration
import androidx.compose.material3.SwitchColors
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/** 整行处理切换时传入 null, 避免重复点击节点。 */
@Composable
internal fun DemoSwitch(
    modifier: Modifier = Modifier,
    checked: Boolean,
    onCheckedChange: ((Boolean) -> Unit)?,
    thumbContent: (@Composable () -> Unit)? = null,
    enabled: Boolean = true,
    colors: SwitchColors = SwitchDefaults.colors(),
    interactionSource: MutableInteractionSource? = null,
) {
    val source = interactionSource ?: remember { MutableInteractionSource() }
    val pressed by source.collectIsPressedAsState()
    val targetSize = when {
        pressed -> 28.dp
        checked || thumbContent != null -> 24.dp
        else -> 16.dp
    }
    val motion = MaterialTheme.motionScheme.fastSpatialSpec<Dp>()
    val thumbSize by animateDpAsState(
        targetValue = targetSize,
        animationSpec = if (pressed) snap() else motion,
        label = "switchThumbSize",
    )
    val thumbOffset by animateDpAsState(
        targetValue = when {
            pressed && checked -> 22.dp
            pressed -> 2.dp
            checked -> 24.dp
            else -> (32.dp - targetSize) / 2
        },
        animationSpec = if (pressed) snap() else motion,
        label = "switchThumbOffset",
    )
    val thumbColor = when {
        enabled && checked -> colors.checkedThumbColor
        enabled -> colors.uncheckedThumbColor
        checked -> colors.disabledCheckedThumbColor
        else -> colors.disabledUncheckedThumbColor
    }
    val trackColor = when {
        enabled && checked -> colors.checkedTrackColor
        enabled -> colors.uncheckedTrackColor
        checked -> colors.disabledCheckedTrackColor
        else -> colors.disabledUncheckedTrackColor
    }
    val borderColor = when {
        enabled && checked -> colors.checkedBorderColor
        enabled -> colors.uncheckedBorderColor
        checked -> colors.disabledCheckedBorderColor
        else -> colors.disabledUncheckedBorderColor
    }
    val iconColor = when {
        enabled && checked -> colors.checkedIconColor
        enabled -> colors.uncheckedIconColor
        checked -> colors.disabledCheckedIconColor
        else -> colors.disabledUncheckedIconColor
    }
    val useInsetRing =
        LocalRippleThemeConfiguration.current.focus is RippleThemeConfiguration.Focus.InsetRing

    Box(
        modifier = modifier
            .then(
                if (onCheckedChange != null) {
                    Modifier.minimumInteractiveComponentSize().toggleable(
                        value = checked,
                        enabled = enabled,
                        role = Role.Switch,
                        interactionSource = source,
                        indication = null,
                        onValueChange = onCheckedChange,
                    )
                } else Modifier,
            )
            .wrapContentSize(Alignment.Center)
            .requiredSize(52.dp, 32.dp)
            .border(2.dp, borderColor, DemoShapes.capsule)
            .background(trackColor, DemoShapes.capsule)
            .then(
                if (useInsetRing) {
                    Modifier.indication(
                        source,
                        ripple(
                            focusRingShape = DemoShapes.capsule,
                            enablePressIndication = false,
                            enableFocusIndication = true,
                            enableDragIndication = false,
                            enableHoverIndication = false,
                        ),
                    )
                } else Modifier,
            ),
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .offset(x = thumbOffset)
                .size(thumbSize)
                .indication(
                    source,
                    ripple(bounded = false, radius = 20.dp, enableFocusIndication = !useInsetRing),
                )
                .background(thumbColor, DemoShapes.capsule),
            contentAlignment = Alignment.Center,
        ) {
            if (thumbContent != null) {
                CompositionLocalProvider(LocalContentColor provides iconColor, content = thumbContent)
            }
        }
    }
}

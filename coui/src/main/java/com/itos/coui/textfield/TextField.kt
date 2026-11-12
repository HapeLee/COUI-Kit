package com.itos.coui.textfield

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.foundation.text.input.KeyboardActionHandler
import androidx.compose.foundation.text.input.OutputTransformation
import androidx.compose.foundation.text.input.TextFieldDecorator
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import com.itos.coui.theme.CouiTheme

/**
 * 页面内的圆角输入框：卡片底色、无边框、占位提示与前后图标槽。
 * 值、校验和提交由宿主持有；组件只渲染当前值、光标与局部焦点，点击卡片任意位置都会聚焦，
 * 槽位内的控件保留自己的点击。配色取 [CouiTextFieldColors]，不创建局部主题作用域。
 *
 * 单行时 `minLines` 与 `maxLines` 不参与布局；多行时按两者在最小高度内增长并在 `maxLines` 处滚动。
 * `enabled = false` 停止输入并改用禁用文字色，`readOnly = true` 仍可聚焦、选择与复制。
 */
@Composable
fun TextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String? = null,
    leading: (@Composable () -> Unit)? = null,
    trailing: (@Composable () -> Unit)? = null,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    singleLine: Boolean = true,
    minLines: Int = 1,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    dark: Boolean = CouiTheme.isDark,
    colors: CouiTextFieldColors = CouiTextFieldDefaults.colors(dark),
    shape: Shape = CouiTextFieldDefaults.shape,
    contentPadding: PaddingValues = CouiTextFieldDefaults.contentPadding,
    textStyle: TextStyle = CouiTextFieldDefaults.textStyle(),
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    interactionSource: MutableInteractionSource? = null,
    cursorBrush: Brush = SolidColor(colors.cursor),
) {
    requireTextFieldLines(singleLine, minLines, maxLines)
    val placeholderColor = if (enabled) colors.placeholder else colors.disabled
    BasicTextField(value = value, onValueChange = onValueChange,
        modifier = modifier.couiTextFieldSurface(shape, colors.container),
        enabled = enabled, readOnly = readOnly,
        textStyle = textStyle.copy(color = if (enabled) colors.text else colors.disabled),
        keyboardOptions = keyboardOptions, keyboardActions = keyboardActions,
        singleLine = singleLine, minLines = if (singleLine) 1 else minLines,
        maxLines = if (singleLine) 1 else maxLines,
        visualTransformation = visualTransformation,
        interactionSource = interactionSource, cursorBrush = cursorBrush,
        decorationBox = { inner ->
            CouiTextFieldDecoration(placeholder = placeholder, textStyle = textStyle,
                placeholderColor = placeholderColor, leading = leading, trailing = trailing,
                contentPadding = contentPadding, singleLine = singleLine,
                showsPlaceholder = value.isEmpty(), inner = inner)
        })
}

/**
 * 同一外观的宿主状态重载：编辑事务、撤销与光标由 [state] 持有，组件不复制文本。
 * `inputTransformation` 与 `outputTransformation` 直接交给 Foundation，用于长度、格式和掩码限制。
 * 键盘动作沿用 Foundation 的状态分支：只有默认动作回调，拿不到当前 ImeAction。
 */
@Composable
fun TextField(
    modifier: Modifier = Modifier,
    state: TextFieldState,
    placeholder: String? = null,
    leading: (@Composable () -> Unit)? = null,
    trailing: (@Composable () -> Unit)? = null,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    singleLine: Boolean = true,
    minLines: Int = 1,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    dark: Boolean = CouiTheme.isDark,
    colors: CouiTextFieldColors = CouiTextFieldDefaults.colors(dark),
    shape: Shape = CouiTextFieldDefaults.shape,
    contentPadding: PaddingValues = CouiTextFieldDefaults.contentPadding,
    textStyle: TextStyle = CouiTextFieldDefaults.textStyle(),
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    onKeyboardAction: KeyboardActionHandler? = null,
    inputTransformation: InputTransformation? = null,
    outputTransformation: OutputTransformation? = null,
    interactionSource: MutableInteractionSource? = null,
    cursorBrush: Brush = SolidColor(colors.cursor),
) {
    requireTextFieldLines(singleLine, minLines, maxLines)
    val placeholderColor = if (enabled) colors.placeholder else colors.disabled
    BasicTextField(state = state,
        modifier = modifier.couiTextFieldSurface(shape, colors.container),
        enabled = enabled, readOnly = readOnly,
        inputTransformation = inputTransformation,
        textStyle = textStyle.copy(color = if (enabled) colors.text else colors.disabled),
        keyboardOptions = keyboardOptions, onKeyboardAction = onKeyboardAction,
        lineLimits = if (singleLine) TextFieldLineLimits.SingleLine
            else TextFieldLineLimits.MultiLine(minLines, maxLines),
        interactionSource = interactionSource, cursorBrush = cursorBrush,
        outputTransformation = outputTransformation,
        decorator = object : TextFieldDecorator {
            @Composable
            override fun Decoration(innerTextField: @Composable () -> Unit) {
                CouiTextFieldDecoration(placeholder = placeholder, textStyle = textStyle,
                    placeholderColor = placeholderColor, leading = leading, trailing = trailing,
                    contentPadding = contentPadding, singleLine = singleLine,
                    showsPlaceholder = state.text.isEmpty(), inner = innerTextField)
            }
        })
}

/** 卡片轮廓属于输入本体，命中区包含留白。 */
private fun Modifier.couiTextFieldSurface(shape: Shape, color: Color): Modifier =
    defaultMinSize(minHeight = CouiTextFieldDefaults.minHeight).clip(shape).background(color)

/**
 * 装饰也属于输入本体：图标槽与留白不拦截输入焦点，槽位内的可点击控件先行消费事件。
 * 占位提示与输入文字共用同一排版，只换颜色；多行时图标与提示对齐首行。
 */
@Composable
private fun CouiTextFieldDecoration(
    placeholder: String?,
    textStyle: TextStyle,
    placeholderColor: Color,
    leading: (@Composable () -> Unit)?,
    trailing: (@Composable () -> Unit)?,
    contentPadding: PaddingValues,
    singleLine: Boolean,
    showsPlaceholder: Boolean,
    inner: @Composable () -> Unit,
) {
    Row(modifier = Modifier.padding(contentPadding),
        horizontalArrangement = Arrangement.spacedBy(CouiTextFieldDefaults.slotSpacing),
        verticalAlignment = if (singleLine) Alignment.CenterVertically else Alignment.Top) {
        leading?.invoke()
        Box(Modifier.weight(1f)) {
            if (showsPlaceholder && placeholder != null) {
                BasicText(placeholder, style = textStyle.copy(color = placeholderColor),
                    maxLines = if (singleLine) 1 else Int.MAX_VALUE,
                    overflow = if (singleLine) TextOverflow.Ellipsis else TextOverflow.Clip)
            }
            inner()
        }
        trailing?.invoke()
    }
}

/** 行数只在多行时生效，单行由 [singleLine] 决定高度。 */
private fun requireTextFieldLines(singleLine: Boolean, minLines: Int, maxLines: Int) {
    if (singleLine) return
    require(minLines >= 1) { "minLines 必须大于零" }
    require(maxLines >= minLines) { "maxLines 不能小于 minLines" }
}

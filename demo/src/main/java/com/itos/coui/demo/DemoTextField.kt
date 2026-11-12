package com.itos.coui.demo

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.itos.coui.icons.CouiIcons
import com.itos.coui.layout.CouiPageDefaults
import com.itos.coui.textfield.CouiTextFieldDefaults
import com.itos.coui.textfield.TextField

/** 输入框只接当前值或宿主状态；焦点、光标和图标槽的点击都由组件自己装配。 */
@Composable
internal fun TextFieldSample(dark: Boolean) {
    val colors = CouiTextFieldDefaults.colors(dark)
    var location by rememberSaveable { mutableStateOf("") }
    var message by rememberSaveable { mutableStateOf("") }
    // 多行示例改用宿主状态重载：编辑事务与光标由 TextFieldState 持有。
    val note = rememberTextFieldState()
    Column(Modifier.fillMaxWidth().padding(top = CouiPageDefaults.groupSpacing),
        verticalArrangement = Arrangement.spacedBy(8.dp)) {
        // 尾部图标槽：图标颜色与尺寸由宿主决定，不跟随卡片文字色。
        TextField(modifier = Modifier.fillMaxWidth().padding(horizontal = CouiPageDefaults.horizontalMargin),
            value = location, onValueChange = { location = it },
            placeholder = stringResource(R.string.text_field_location),
            trailing = {
                Icon(CouiIcons.Filled.Location, null, Modifier.size(CouiPageDefaults.iconSize),
                    tint = colors.text)
            })
        // 头部图标槽 + 可点击的尾部清空按钮；槽位控件先消费点击，不会一并聚焦输入框。
        TextField(modifier = Modifier.fillMaxWidth().padding(horizontal = CouiPageDefaults.horizontalMargin),
            value = message, onValueChange = { message = it },
            placeholder = stringResource(R.string.text_field_message),
            leading = {
                Icon(CouiIcons.Filled.BookOpen, null, Modifier.size(CouiPageDefaults.iconSize),
                    tint = colors.text)
            },
            trailing = {
                if (message.isNotEmpty()) Box(Modifier.size(CouiPageDefaults.iconSize)
                    .clickable(role = Role.Button) { message = "" }, contentAlignment = Alignment.Center) {
                    Icon(CouiIcons.Filled.Close, stringResource(R.string.text_field_clear), Modifier.size(20.dp),
                        tint = colors.placeholder)
                }
            })
        // 多行：首行到 minLines 撑高，超过 maxLines 后内部滚动。
        TextField(modifier = Modifier.fillMaxWidth().padding(horizontal = CouiPageDefaults.horizontalMargin),
            state = note, placeholder = stringResource(R.string.text_field_note),
            singleLine = false, minLines = 3, maxLines = 5)
        // 只读仍可聚焦、选择与复制；禁用停止输入并改用禁用色。
        TextField(modifier = Modifier.fillMaxWidth().padding(horizontal = CouiPageDefaults.horizontalMargin),
            value = stringResource(R.string.text_field_sample_value), onValueChange = {},
            placeholder = stringResource(R.string.text_field_read_only), readOnly = true)
        TextField(modifier = Modifier.fillMaxWidth().padding(horizontal = CouiPageDefaults.horizontalMargin),
            value = stringResource(R.string.text_field_sample_value), onValueChange = {},
            placeholder = stringResource(R.string.text_field_read_only), enabled = false)
    }
}

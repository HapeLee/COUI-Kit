# 输入框

`TextField` 是页面内的圆角输入卡片：无边框、卡片底色、占位提示与前后图标槽，位于 `com.itos.coui.textfield`。它只负责把当前值、光标和焦点画出来，值、校验和提交仍由宿主持有。

| 用途 | 使用 |
| --- | --- |
| 单行或多行文本输入 | `TextField` |
| 纯展示的选择行（右侧当前值 + 箭头） | [Item](item.md) |
| 输入型弹窗 | [弹窗](dialog.md) |

## 基本用法

```kotlin
@Composable
fun LocationField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    colors: CouiTextFieldColors,
) {
    TextField(modifier = modifier.fillMaxWidth().padding(horizontal = CouiPageDefaults.horizontalMargin),
        value = value, onValueChange = onValueChange,
        placeholder = "地点",
        trailing = { Icon(CouiIcons.Filled.Location, null, Modifier.size(CouiPageDefaults.iconSize), tint = colors.text) })
}
```
`padding` 与 `colors` 由页面提供，`Icon` 来自宿主使用的图标库。输入框本身不加页面留白，与 [Group](group.md) 同页时按 `CouiPageDefaults.horizontalMargin` 对齐。

## 状态与事件

`value` 与 `onValueChange` 是受控入口：宿主持有文本，组件在每次编辑后回调完整新值，不缓存副本。需要编辑事务、撤销或异步校验时改用 `TextFieldState` 重载，由宿主创建并持有该状态，组件只读 `state.text` 判断占位提示是否显示。

`inputTransformation` 与 `outputTransformation` 只在状态重载上提供，直接交给 Foundation：前者限制或改写编辑事务，后者只改显示文本。两者都不参与宿主的业务校验。

两个重载的键盘动作入口与 Foundation 一致：`value`/`onValueChange` 版本使用 `KeyboardActions`，`TextFieldState` 版本使用 `onKeyboardAction`，后者只收到默认动作的调用，拿不到当前 `ImeAction`。需要在状态重载里区分搜索、发送等动作时，由宿主改用 `value` 重载或自行处理按键。

点击卡片任意位置都会聚焦并把光标落在点击处，包括文字两侧的留白；`leading` 和 `trailing` 里的可点击控件先消费事件，不会同时聚焦输入框。

`enabled = false` 停止输入与聚焦，文字和占位提示改用禁用色；`readOnly = true` 仍可聚焦、选择与复制，只是不能改写内容。

## 单行与多行

`singleLine = true` 是默认值，字段高度由 `CouiTextFieldDefaults.minHeight` 与字号决定。多行时传 `singleLine = false` 并设置 `minLines` / `maxLines`：字段在最小行数上继续增长，超过 `maxLines` 后内部滚动，图标与占位提示对齐首行。单行时这两个参数不参与布局。

`maxLines` 不能小于 `minLines`，两者都必须大于零；不满足时组件在组合期抛出 `IllegalArgumentException`。

## 外观与预设

| 入口 | 作用 |
| --- | --- |
| `colors: CouiTextFieldColors` | 卡片底色、正文、占位提示、禁用文字与光标颜色 |
| `shape` | 卡片轮廓，默认 `CouiTextFieldDefaults.shape` |
| `contentPadding` | 卡片内留白 |
| `textStyle` | 输入与占位提示共用的排版，默认 `CouiTextFieldDefaults.textStyle()` |
| `cursorBrush` | 光标画笔，默认取 `colors.cursor` |

`CouiTextFieldDefaults.colors(dark)` 从当前主题解析四个角色：`container` 是卡片底色（与同页分组卡片同源的 `card`）、`text` 是输入文字、`placeholder` 是占位提示、`cursor` 是光标（跟随强调色）、`disabled` 是禁用态文字。强调色与主题不一致时，在该结果上 `copy(cursor = accent)`，不要另建色板。默认排版为 16sp、系统无衬线常规字重，行距与列表主文字一致；需要对齐的前缀、计数器等文字可直接使用 `CouiTextFieldDefaults.textStyle()`。

组件不创建局部主题作用域，也不改写槽位内容的颜色：`leading` 与 `trailing` 里的图标需要显式 `tint`，尺寸由调用方决定，常用 24dp。

## 平台与装配

输入框只依赖 Foundation 与 COUI 核心，不限制最低 API 版本，也不需要背景采样源。它不安装滚动、不消费系统栏 Insets，页面负责键盘避让；页面装配见[宿主页面接入](../guide/secondary-pages.md)。

需要横向并排多个输入框时，用 `Modifier.width` 或 `weight` 限制各自宽度；字段默认占满可用宽度。输入框是独立卡片，不要放进 `Group` 的卡片内容中。

## 完整示例与 API

源码见 [TextField](../../coui/src/main/java/com/itos/coui/textfield/TextField.kt) 与 [Defaults](../../coui/src/main/java/com/itos/coui/textfield/CouiTextFieldDefaults.kt)。Demo 装配见 [DemoTextField](../../demo/src/main/java/com/itos/coui/demo/DemoTextField.kt)。

# 浮层：Menu、Dialog 与 BottomSheet

三类浮层均依赖根部[窗口背景源](backdrop-and-effects.md#窗口源)（`ProvideCouiMenuBackdrop`）且**保持调用常驻**。

| 组件 | 显隐参数 | API 33+ 有窗口源时的材质默认行为 |
| --- | --- | --- |
| `Menu` | `expanded: Boolean` | 自动启用模糊与光效（无源回退纯色） |
| `Dialog` / `InputDialog` / `LoadingDialog` | `visible: Boolean` | 默认 `material = null`，**须显式传 `material` + `backdrop`** |
| `BottomSheet` | `visible: MutableState<Boolean>` | 省略 `material` 即自动启用 |

## Menu

### 设置行菜单

行宽、按触点弹出：共享 `anchor`，行加 `.couiMenuAnchor(anchor)` 并在 `onClick` 调 `anchor.open`：

```kotlin
val anchor = rememberCouiMenuAnchor(); var expanded by remember { mutableStateOf(false) }
Box {
    Item(Modifier.couiMenuAnchor(anchor), onClick = { anchor.open { expanded = true } },
        trailing = { Icon(CouiIcons.Outlined.Popup, null) }, title = title)
    Menu(anchor = anchor, expanded = expanded, onDismissRequest = { expanded = false },
        items = items, onItemClick = { expanded = false; onSelect(it.value) })
}
```

展开期高亮触发行：在 `Item` 用 `couiButtonFeedback(..., list = true, hoverLocked = expanded)` + `clickable`，并**省略 `Item.onClick`**。

### 按钮菜单

用仅包单个按钮的 `Box` 并列放按钮与 `Menu`，**省略 `anchor`**；API 31–33 换宿主按钮：

```kotlin
var expanded by remember { mutableStateOf(false) }
Box {
    if (Build.VERSION.SDK_INT >= 34) TopAppBarButton(onClick = { expanded = true }) { moreIcon() }
    else androidx.compose.material3.IconButton(onClick = { expanded = true }) { moreIcon() }
    Menu(expanded = expanded, onDismissRequest = { expanded = false }, items = items,
        onItemClick = { expanded = false; onSelect(it.value) }, offset = DpOffset(0.dp, 4.dp))
}
```

### 避坑

- **位置或紧贴图标**：`Box` 误包整排按钮，或 `Menu` 放进按钮内容槽。确保 `Box` 仅包单个按钮。
- **变纯色**：检查根部 `ProvideCouiMenuBackdrop`，或是否被内层传 `null` 屏蔽。
- `CouiMenuItem` 限两级（`children` 不可再嵌 `children`）；单选高亮用 `selected`，多选勾选框用 `checked`（`null` 为普通项）。
- 自定义非 `CouiShape` 的 `CouiMenuStyle` 须传 `radius`。行尾 `CouiIcons.Outlined.Popup` 仅提示，不旋转、不单绑点击。

## Dialog 系列

```kotlin
var visible by rememberSaveable { mutableStateOf(false) }
val backdrop = rememberCouiWindowBackdrop()
val material = if (CouiDialogDefaults.isMaterialSupported() && backdrop != null) CouiDialogDefaults.material(CouiTheme.isDark) else null
Dialog(visible = visible, onDismissRequest = { visible = false }, title = title, message = message,
    confirmAction = CouiDialogAction(confirmText, { onConfirm(); visible = false }),
    dismissAction = CouiDialogAction(cancelText, { visible = false }), material = material, backdrop = backdrop)
```

- 多行操作用 `Dialog(actionRows = ...)`（1–4 行，每行非空）。
- **`InputDialog`**（复用 `material`/`backdrop`）：打开重置 `draft`；保存失败“重新编辑”直接设 `editing = true` 保留输入：

```kotlin
val draft = rememberTextFieldState() // 打开时: draft.edit { replace(0, length, name); selection = TextRange(name.length) }
InputDialog(state = draft, visible = editing, title = title, clearContentDescription = clearText,
    onDismissRequest = { editing = false }, confirmAction = CouiDialogAction(saveText, {
        onRename(recordId, draft.text.toString()); editing = false
    }, enabled = draft.text.isNotBlank()), dismissAction = CouiDialogAction(cancelText, { editing = false }),
    material = material, backdrop = backdrop)
```

- **`LoadingDialog`**：`LoadingDialog(visible = loading, message = loadingText, material = material, backdrop = backdrop)`。默认拦返回键；支持取消传 `onDismissRequest`（允许点外取消加 `dismissOnClickOutside = true`）。

### 避坑

- **只装窗口源仍是实色**：须显式传 `material` + `backdrop`；但 **API < 33 或无 `backdrop` 时传非空 `material` 直接崩溃**。
- **外包 `if (visible)`**：立即毁窗，丢退场动画。
- **`require` 崩溃**：`CouiDialogAction.text`、`LoadingDialog.message` 不可空白；`CouiDialogActionStyle.Primary` 必配 `colors = CouiDialogDefaults.primaryActionColors(accent)`。
- 自定义非 `CouiShape` 开材质须传 `cornerRadius`。宿主页 `imePadding()` 仅在 `LocalWindowInfo.current.isWindowFocused` 为真时启用，防背景页随输入弹窗抬起。

## BottomSheet

传 `MutableState<Boolean>` 本身并保持常驻。关闭前二次确认时，让 `onDismissRequest` 与取消按钮共用确认流程，确认后再改 `visible.value = false`。顶部按钮弹菜单放 `CouiSheetButton.popupContent`。

```kotlin
val visible = rememberSaveable { mutableStateOf(false) }
val draft = rememberSaveable { mutableIntStateOf(0) }
Item(title = timeTitle, onClick = { draft.intValue = savedValue; visible.value = true })
BottomSheet(visible = visible, titleContent = { Text(text = title, kind = CouiTextKind.SheetTitle) },
    leadingButton = CouiSheetButton(onClick = { visible.value = false }, label = cancelText, enabled = visible.value),
    trailingButton = CouiSheetButton(onClick = { onSave(draft.intValue); visible.value = false }, label = saveText, enabled = visible.value)) {
    /* 正文；含 LazyColumn 必传 scrollableContent = false */
}
```

### 避坑

- **与键盘间多空白**：`BottomSheet` 自带底栏与 IME 避让，正文勿加 `imePadding()` / `navigationBarsPadding()`。
- **正文 `LazyColumn` 滚不动或手势冲突**：传 `scrollableContent = false`。
- **卡片内控件勿传 `rememberCouiSheetSurfaceBackdrop()`**：它会透出卡片背后底色。

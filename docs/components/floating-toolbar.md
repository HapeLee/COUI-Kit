# FloatingToolbar 悬浮操作栏

编辑页面可以把常用操作放在底部胶囊里。调用方提供动作列表与点击回调，工具栏负责增删时的回弹，并在宽度不足时把剩余动作收进“更多”。组件位于 `com.itos.coui.floatingtoolbar`，需要 Android 14（API 34）及以上。

## 随列表增删图标

~~~kotlin
@RequiresApi(34)
@Composable
fun EditorActions(
    modifier: Modifier = Modifier,
    items: List<CouiFloatingToolbarItem<String>>,
    visible: Boolean,
    onAction: (String) -> Unit,
) {
    FloatingToolbar(
        modifier = modifier.navigationBarsPadding(),
        items = items,
        onItemClick = onAction,
        visible = visible,
    )
}
~~~

每个动作提供唯一且稳定的 `value`、无障碍与菜单共用的 `label`、`ImageVector` 图标。显示顺序跟随列表。使用业务 ID 作为 `value`，删除前面的动作后，后续动作仍能沿用自己的身份。普通按钮和“更多”菜单都通过 `onItemClick` 提交该值。`enabled = false` 时两个入口都禁用。

在 COUI `Scaffold` 的底栏槽中可直接使用此例：工具栏自身只保留 `76.dp` 工具区高度（收起整栏时仍保留该占位），需通过 `Modifier.navigationBarsPadding()` 处理底部系统栏安全区，编辑页键盘避让由宿主页容器统一处理。放在其他布局时，通过 `backdrop` 指定同页的不透明 SDR 背景源。“更多”菜单优先采样当前窗口，宿主没有提供窗口采样源时使用工具栏的 `backdrop`，此时采样范围应覆盖菜单弹出的区域。默认每个工具栏创建自己的绘制器，需要处理初始化失败时可显式传入 `CouiFloatingRenderer.standalone()`。[背景采样](../effects/backdrop.md)

## 收起整栏与增删动作

- 切换整栏的 `visible` 时让组件留在组合中。显示时整栏从 0.8 倍回弹到原尺寸；收起时从当前倍率缩小并淡出。按钮从收起开始停止响应，工具区保留布局高度。
- 修改动作列表时，移除项保留到这批退出动画完成。先用 200ms 改变间距，然后间距在 200ms 内恢复，图标同步按 response 0.5s、bounce 0.4 的弹簧缩放。连续修改按提交顺序播放。胶囊边缘跟随当帧首尾图标。
- 一次提交新的 `items` 列表，可以在任意位置同时增减多个图标。清空列表会让所有图标一起退场；再次添加时按同一套规则入场。切换整栏的 `visible` 会保留已选图标。

## 布局与配色

列表入口使用父容器的可用宽度。默认工具区高 76dp，44dp 高的胶囊在其中居中。每项 44×40dp、图标 24dp、项间 4dp，多项时胶囊左右各留 6dp。两项的胶囊为 104dp，四项为 200dp。可用宽度还取决于工具区的响应式侧边距。足够宽时七项能直接显示，八项会留出“更多”。导航栏、键盘避让和正文底部留白由页面管理。

默认使用当前主题的底栏材质。宿主选择纯色模式时，使用 `CouiFloatingToolbarDefaults.style(containerColor = color)` 替换玻璃底色。用 `iconTint` 覆盖单个动作，多色矢量图标传入 `Color.Unspecified` 可显示自带颜色。整栏按压、跟手光与投影各由一个胶囊承担，单项有自己的按压波纹、悬停与焦点反馈。

在可滚动页面中，轻点图标会执行操作。从胶囊上开始拖动时，操作栏跟手形变，页面保持原位；从胶囊外起手可正常滚动页面。

## 自定义内容

图标加文字、分隔或自定义控件可以放入原有内容槽。下面的操作在槽位中保留组合状态，切换 `visible` 时会收放其完整宽度。

~~~kotlin
FloatingToolbar(visible = toolbarVisible, backdrop = backdrop) {
    FloatingToolbarItem(
        modifier = Modifier.width(80.dp),
        onClick = onFormat,
        visible = showFormat,
    ) {
        Text("格式")
    }
}
~~~

自定义槽没有可用于菜单的业务标签，由宿主决定哪些动作放入槽内。直接移除自定义可组合项会结束其组合，需要保留退出内容时可切换单项 `visible` 或使用列表入口。

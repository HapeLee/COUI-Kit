# 图标

`CouiIcons` 提供可在 Composable 中使用的 `ImageVector` 图标，部分图标会根据 `CouiTheme` 切换日间与夜间配色。

使用图标时，在调用模块中添加依赖，并与核心库使用相同版本：

```kotlin
implementation("io.github.itoseo:coui-icons:$couiVersion")
```

以下示例中的 `contentDescription` 由调用方根据界面语义提供。

## 基本显示

在 Composable 中使用 `Image` 可直接显示图标内置颜色；使用 Material 3 `Icon` 时，传 `tint = Color.Unspecified` 以保留图标默认配色：

```kotlin
Image(
    imageVector = CouiIcons.Filled.Wifi,
    contentDescription = contentDescription,
)

Icon(
    imageVector = CouiIcons.Filled.Wifi,
    contentDescription = contentDescription,
    tint = Color.Unspecified,
)
```

图标按 `CouiIcons.Filled` 和 `CouiIcons.Outlined` 提供。`Back`、`Close`、`Add`、`DarkLight`、`ListFilter` 和 `More` 在两种样式中使用相同图形；`Search`、`Headset`、`SliderCustom` 和 `LayoutSpliced` 仅提供 `Outlined`。它们默认日间为黑色、夜间为白色，其中 `Back` 支持 RTL 自动镜像。

图形保存在 VectorDrawable 资源中，传统 View 可直接引用，例如 `@drawable/coui_wifi_filled`。Compose 入口根据 `CouiTheme` 加载日夜资源；传统 View 的资源则根据 Android 配置选择。

## 指定颜色与特殊图标

`CouiColor.Day` 和 `CouiColor.Night` 均提供 `GRAY`（`#474747`）和 `DARK_GRAY`（`#191919`）。

- **单色图标指定 `tint`**：可结合 `CouiTheme.isDark` 从 `CouiColor.Day` / `CouiColor.Night` 选择颜色并传给 `Icon`：

```kotlin
val iconTint = if (CouiTheme.isDark) CouiColor.Night.BLUE else CouiColor.Day.BLUE
Icon(
    imageVector = CouiIcons.Filled.Wifi,
    contentDescription = contentDescription,
    tint = iconTint,
)
```

- **多色图标**：`AI`、`Google`、`Setting`、`Phone` 内置多色或渐变路径，应使用 `Image` 或 `Icon(..., tint = Color.Unspecified)`，避免整体 `tint` 覆盖原有配色。
- **应用更新图标（`AppUpdate`）**：自定义圆形背景色时调用 `CouiIcons.Filled.AppUpdate(color = ...)`，并在 `Icon` 中传 `tint = Color.Unspecified` 以保留白色前景：

```kotlin
val badgeColor = if (CouiTheme.isDark) CouiColor.Night.GREEN else CouiColor.Day.GREEN
Icon(
    imageVector = CouiIcons.Filled.AppUpdate(color = badgeColor),
    contentDescription = contentDescription,
    tint = Color.Unspecified,
)
```

[CouiIcons.kt](../../coui-icons/src/main/java/com/itos/coui/icons/CouiIcons.kt) · [CouiColor.kt](../../coui/src/main/java/com/itos/coui/theme/CouiColor.kt)

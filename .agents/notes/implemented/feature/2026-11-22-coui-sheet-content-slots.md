# Agent Note: Sheet 工具栏的宿主内容槽

Status: implemented

## Problem

面板顶部两侧原本只接受 `CouiSheetButton`：一个 `onClick` 加 `label` 或 `icon` 二选一，菜单以整颗按钮为锚点。宿主的顶部操作却常常是自带点击、禁用态、选中态和下拉锚点的任意 Composable。把宿主内容塞进 `CouiSheetButton.icon` 会额外生成一个空动作的 `Role.Button` 点击区：按钮之外的边距变成点不动的空白，读屏还会报出没有可执行动作的按钮。整行塞进 `titleContent` 则会给包含按钮的容器加上 `heading()` 语义，并且拿不到工具栏槽位的按下守卫。

宿主的替代方案是自己按 `toolbarTopSpacing` + `toolbarHeight` 复刻工具栏：常量重复维护，且丢掉 `toolbarOwnsTouch` 守卫和 `LocalCouiToolbarBackdrop` 采样。

## Decision

[SheetToolbar](../../../../coui/src/main/java/com/itos/coui/sheet/SheetToolbar.kt) 新增 `leadingContent` / `trailingContent` 两个 Compose 内容槽，[BottomSheet](../../../../coui/src/main/java/com/itos/coui/sheet/BottomSheet.kt) 透传同名参数。内容槽复用既有的 `actionInput` 修饰符与 `CompositionLocalProvider(LocalCouiToolbarBackdrop provides backdrop)`：

- 首指落在槽位内即置 `toolbarOwnsTouch`，面板拖拽不接管这次触摸；槽位不消费事件，宿主控件继续处理点击与按压。
- 槽位内的菜单与浮动控件采样不含自身的面板背景源，与按钮槽一致。
- 标题测量不变：两侧仍是先测量再等量避让，标题宽度按实际内容宽度而非固定常量。
- 同一侧只允许按钮或内容之一，`require` 直接拒绝同时传入，避免静默丢内容。
- 三个顶部槽位全空时不占工具栏高度，与改动前一致。

## Alternatives considered

- `CouiSheetButton` 增加 `content` 槽：点击仍由库托管，宿主按钮会被再包一层 COUI 胶囊，形成外层空动作按钮加内层真按钮的双点击目标，无障碍问题原样保留。
- 宿主自行复刻 11dp / 50dp 几何：能看齐间距，但丢守卫与采样，并让两个仓库各自维护同一组常量。
- 整行 `headerContent` 替换：最灵活，但会丢弃居中避让和 `heading()` 语义，宿主还得在所有引擎之外再实现一遍标题测量。

## Existing-note audit

- [底部面板的运动与窗口采样归属](2026-11-07-modal-bottom-sheet.md)：本篇只扩展顶部槽位的可放置内容，窗口、运动、测量和采样归属不变。
- [警告弹窗](2026-10-24-coui-alert-dialog.md)：不涉及，弹窗操作按钮仍是库托管形态。

## Consequences

宿主可以在 COUI 工具栏里保留自己的按钮组合与菜单锚点，同时得到面板的防误触与采样。代价是工具栏多了一组公开参数，需要随上游 `SheetToolbar` 一起维护；库没有单元测试，验证依赖 demo 的「自定义操作槽」样本（含挂在槽位上的菜单）、编译与真机手势核对。

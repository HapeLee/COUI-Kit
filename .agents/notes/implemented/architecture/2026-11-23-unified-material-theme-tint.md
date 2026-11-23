# Agent Note: 统一模糊材质主题染色架构与弹窗接入

Status: implemented

## Problem

在 `0.0.1-alpha16` 中，COUI 的背景材质染色分散在主题、底层绘制与组件三层，存在三处不一致：`Dialog` / `InputDialog` / `LoadingDialog` 共用的 `CouiDialogWindow` 未暴露 `themeTint` 且未向 `rememberCouiMaterialEffect` 传参；`TopAppBarButton` / `TopAppBarSearch`、`SearchBar`、`MessageInputBar`、`SegmentedControl` 与 `ChipTab` 系列缺漏主题染色默认接线或组件覆写入口；`Menu` 在 API 33+ 依赖 `CouiEdge` 外部额外一遍 `drawRect(BlendMode.Color)` 染色；同时各组件直接把传入颜色的 `alpha` 当作染色强度，启用 `CouiMaterial3Style` 时默认 `colors.surface` 的 `alpha` 为 `1f`，迫使宿主在 `Menu`、`NavigationBar`、`BottomSheet`、顶栏与弹窗中各自乘强度或修改 `mix`/`blend` 混色。

## Decision

全库将所有中性透明模糊材质组件的背景染色收敛为单一正交架构：**外部只传染色基准色，库内统一持有固定染色强度与唯一保亮度染色算子**，仅作用于开启模糊材质的组件表面，绝不进入任何不透明背景或遮罩。

### 主题与优先级解析

1. **单组件显式参数最优先**：所有支持模糊材质的中性组件均支持 `themeTint` 传参，且均作为**基准色**处理，保留原有参数顺序：
   - 无独立 `Style` 的中性透明模糊组件（[Dialog](../../../../coui/src/main/java/com/itos/coui/dialog/Dialog.kt)、[InputDialog](../../../../coui/src/main/java/com/itos/coui/dialog/InputDialog.kt)、[LoadingDialog](../../../../coui/src/main/java/com/itos/coui/dialog/LoadingDialog.kt)、[BottomSheet](../../../../coui/src/main/java/com/itos/coui/sheet/BottomSheet.kt)、[NavigationBar](../../../../coui/src/main/java/com/itos/coui/navigation/NavigationBar.kt)、[SearchBar](../../../../coui/src/main/java/com/itos/coui/search/SearchBar.kt)、[SegmentedControl](../../../../coui/src/main/java/com/itos/coui/segmentbutton/SegmentedControl.kt)、[ChipTab / ChipTabButton](../../../../coui/src/main/java/com/itos/coui/chip/ChipTab.kt)、[ChipTabRow](../../../../coui/src/main/java/com/itos/coui/chip/ChipTabRow.kt)）在参数末尾（尾随 lambda 前）提供 `themeTint: Color = CouiTheme.materialTintFor(dark)`；底层窗口 [CouiDialogWindow](../../../../coui/src/main/java/com/itos/coui/dialog/CouiDialogWindow.kt) 默认为 `Color.Transparent`。
   - 接收完整材质配置的组件（[MessageInputBar](../../../../coui/src/main/java/com/itos/coui/messageinput/MessageInputBar.kt) 与 [Menu](../../../../coui/src/main/java/com/itos/coui/menu/Menu.kt)、[TopAppBarButton](../../../../coui/src/main/java/com/itos/coui/topappbar/TopAppBarButton.kt)、[TopAppBarSearch / TopAppBarSearchButton](../../../../coui/src/main/java/com/itos/coui/topappbar/TopAppBarSearch.kt)）：`MessageInputBar` 将 `CouiTheme.materialTintFor(dark)` 注入默认 `material`，组件 `themeTint` 默认继承 `material.themeTint`（保留已有自定义 `material` 的染色且支持显式覆盖）；`Menu` 由 [CouiMenuDefaults.style](../../../../coui/src/main/java/com/itos/coui/menu/CouiMenuStyle.kt) 注入主题默认，新增 `themeTint: Color = style.themeTint` 置于 `onSpotlightDiagnostic` 之前以保留尾随 lambda 调用方式；顶栏组件由 [CouiTopAppBarButtonDefaults.style](../../../../coui/src/main/java/com/itos/coui/topappbar/CouiTopAppBarButtonDefaults.kt) 注入主题默认，组件参数默认 `Color.Unspecified`，未指定时沿用调用方传入的自定义 `style`（保证 [SheetToolbar](../../../../coui/src/main/java/com/itos/coui/sheet/SheetToolbar.kt) 的 `Color.Transparent` 不被覆盖而二次染色），指定具体颜色时仅覆盖材质染色基准色。
2. **保持现有两级主题解析**：[CouiTheme.kt](../../../../coui/src/main/java/com/itos/coui/theme/CouiTheme.kt) 不引入额外的 `CompositionLocal`，继续由 `CouiTheme.materialTintFor(dark)` 按「局部 `CouiTheme` → 全局 `Coui.defaultStyle.themeDefaults(dark).materialTint`」顺序解析原始基准色；局部 `CouiTheme` 或全局默认策略与组件 `dark` 不一致时，染色基准色回退为 `Color.Transparent`。
3. **全局 M3 默认保持无参基准色**：[CouiMaterial3Style](../../../../coui-material3/src/main/java/com/itos/coui/material3/CouiMaterial3Style.kt) 保持无参构造，继续提供不透明 `colors.surface` 作为染色基准色，不改动 `CouiColorScheme` 任何背景或表面角色。

### 唯一固定强度与三条底层渲染管线统一

1. **内部唯一固定强度 `0.30f`**：[CouiMaterial.kt](../../../../coui/src/main/java/com/itos/coui/effect/CouiMaterial.kt) 定义 `internal const val CouiMaterialTintStrength = 0.30f` 与 `internal fun resolveCouiThemeTint(baseColor: Color)`，不对外公开调节入口；`Color.Transparent` 与 `Color.Unspecified` 归一化为 `Color.Transparent`，其余任意颜色忽略传入 `alpha` 并强制采用 `0.30f`。
2. **唯一保亮度 AGSL 算子**：在 `CouiMaterialShaderFunctions` 中提取 `applyCouiThemeTint(half3 rgb, half4 tint)`，先 `clamp(..., 0.0, 1.0)` 消除 `ColorDodge` 等混色造成的超白点溢值，再按 `mix(clamped, luminosity(clamped, tint.rgb), tint.a)` 仅迁移基准色相与饱和度并保持材质亮度。
   - **面板 / 弹窗材质**：`rememberCouiMaterialEffect` 公开与内部重载均暴露 `themeTint`，以归一化结果作为 `remember` 依赖键并在 `CouiMaterialShader` 末尾调用 `applyCouiThemeTint`。
   - **浮动表面**：[CouiFloatingSurfaces](../../../../coui/src/main/java/com/itos/coui/effect/CouiFloatingSurfaces.kt) 在上传 `u_themeTint` 前经 `resolveCouiThemeTint` 归一化，[CouiFloatingShader](../../../../coui/src/main/java/com/itos/coui/effect/CouiFloatingShader.kt) 在 `outputCol` 限幅之后、覆盖率之前调用 `applyCouiThemeTint`。
   - **菜单与通用描边**：[CouiEdge](../../../../coui/src/main/java/com/itos/coui/effect/CouiEdge.kt) 构造末尾新增 `themeTint: Color = Color.Transparent` 并由 `CouiMenuEdge` 将其纳入 `remember` 键；`CouiEdgeShader` 在轮廓光与内晕判断块之后统一判断 `THEME_TINT` 并对非零 alpha 像素先除后乘执行 `applyCouiThemeTint`，保证关闭轮廓光（`uContourGlowEnabled < 0.5`）时仍正确染色。[CouiMenuMaterial](../../../../coui/src/main/java/com/itos/coui/menu/CouiMenuMaterial.kt) 在 API 33+ 将染色并入 `CouiEdge`（取消外部重复遍数），仅在 API 31–32 降级路径保留 `drawRect(effectiveTint, BlendMode.Color)`。

### 与不透明背景、轮廓及特化控件的隔离边界

- `CouiMaterialTintStrength`（`0.30f`）仅通过 `resolveCouiThemeTint` 进入上述四处模糊材质染色入口，绝不修改页面 `background` / `surface` / `container` / `card`、`CouiDialogWindow` 不透明回退底色、`rememberCouiMenuBlur` 补底色、`colors.dimAmount` 窗口遮罩、`TopAppBarSurface` 渐进遮罩、`MessageInputBarSurface` 底部渐变遮罩，以及 `MessageInputBar` 关闭系统模糊时的不透明填充（显式传 `themeTint = Color.Transparent`）和 `SegmentedControl` 普通填色模式（`backdrop == null`）。
- `Dialog` 与 `BottomSheet` 的 `CouiContour` 无色相预乘白光轮廓保持叠在已染色底图之上，前景文字与按钮不染色；独立有色 `Fab` 与 `NavigationBar` 右侧有色按钮保持语义强调色，不默认读取 `CouiTheme.materialTintFor(dark)`。

## Existing-note audit

- [可选 Material 3 策略与消费处解析](2026-10-31-optional-material3-style.md)：部分重叠。本篇保留其消费处读取、局部 `CouiTheme` 与 `Fab` 排除契约，并记录全组件统一基准色与 `0.30f` 固定强度语义；已在原 Note 补充交叉链接。
- [原生主题、组件默认值与 Material 染色边界](2026-10-30-native-theme-and-component-defaults.md)：部分重叠。本篇扩展其「仅部分组件启用宿主适配染色」边界至全库中性模糊材质组件并统一强度语义，保留原生透明默认、有色 `FAB` 及右附件白色约束。
- [COUI 弹窗组件与原厂承载边界](../feature/2026-10-24-coui-alert-dialog.md)：部分重叠。保留弹窗复用 `rememberCouiMaterialEffect`、白光 `CouiContour` 与非限幅 `ColorDodge` 依据，在其后置染色阶段接入统一 `themeTint` 参数；对 [BottomSheet](../feature/2026-11-07-modal-bottom-sheet.md) 与 [Menu 合成](../bug-fix/2026-10-15-coui-submenu-input-dispatch.md) 同理保留既有渲染后端与几何约束，无过时 `proposed`/`rejected` 项需删除或归档。

## Alternatives considered

- **新增独立 `ProvideCouiMaterialTint` 与内部 `LocalCouiMaterialTint` 作用域**：可以在开启 `CouiMaterial3Style` 时单独整树关闭或改用非 `surface` 基准色并保留嵌套 `MaterialTheme` 解析。但当前 COUI、Demo 与实际宿主 Metric 均无此类局部开关需求且零调用方，直接复用 `CouiMaterial3Style`、`CouiTheme(materialTint = ...)` 与单组件 `themeTint` 更精简，后续确有真实需要时再补充也不会破坏兼容。
- **继续由外部通过 `themeTint.alpha` 控制强度**：各组件与宿主须在每处调用手动乘以 `0.30f`，且 M3 默认 `surface`（`alpha = 1f`）与宿主实际观感分裂，还容易出现重复乘透明度或漏传。改为基准色契约与库内唯一 `CouiMaterialTintStrength = 0.30f` 后，任意来源的同色传参视觉完全一致。
- **通过 `CouiDialogMaterial` 传入或修改 `mix`/`blend` 混色**：`CouiDialogDefaults.material(dark)` 为非 `@Composable` 工厂无法读 `CouiTheme.materialTintFor(dark)`，且对 `mix`/`blend` 染色会被 `Overlay` / `ColorDodge` 二次放大色偏。改由弹窗与 `BottomSheet` 对齐使用组件参数 `themeTint` 透传至后置 `applyCouiThemeTint`。
- **在顶栏组件中把 `themeTint` 默认值设为 `CouiTheme.materialTintFor(dark)`**：会覆盖调用方显式传入自定义 `style` 中的 `material.themeTint`（如 `SheetToolbar` 为避免对已染色面板底图二次染色而显式关闭）。改用 `Color.Unspecified` 默认值，未显式覆盖时保留 `style` 配置。
- **对外开放 `CouiMaterialTintStrength` 或强度调节参数**：违反单一外观强度原则并造成同应用内不同组件浓淡割裂，因此仅保留基准色覆盖能力。

## Consequences

所有中性模糊材质组件（包括 `Dialog` 系列）只需传同一个基准色，或在开启 `CouiMaterial3Style` / `CouiTheme(materialTint = ...)` 时省略参数，即可得到与 Metric 一致的克制染色观感；`Menu` 在 API 33+ 减少了一遍整面 Canvas `BlendMode.Color` 绘制；Demo 移除了 `withDemoThemeTint()` 临时补丁并以纯基准色示例对齐新契约。代价是外部调用方无法再通过修改 `themeTint` 自身的 `alpha`（非零值）调整染色深浅，原依靠非透明 `alpha` 控制浓淡的接入方需改用不同基准色或接受统一 `0.30f` 强度。

本轮变更完成 Note 树/格式/归档脚本校验、`git diff --check`、渲染入口与不透明背景隔离的 `git grep` 核查，以及 AGSL 片段、公开参数位置与 Markdown 链接静态核查；按仓库交付约束未执行 Android Gradle 构建与真机 API 31–32 / 33 / 34+ 渲染及颜色观感验收，视觉效果与渲染器编译仍待宿主设备实际验证。

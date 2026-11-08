# Agent Note: DynamicAnimation 1.1.0 对齐与 API 26–30 能力隔离

Status: implemented

## Problem

核心库把 `androidx.dynamicanimation:dynamicanimation` 严格锁在 1.0.0，而宿主使用的 Material 3 1.14.0 请求 1.1.0，严格约束使依赖图无法收敛到同一版本。两个库模块的 `minSdk` 为 31，外部宿主保持 26 时无法通过 manifest 合并；本仓 Demo 仍为 31，要用它在 API 26 验收需另行降低。降低门槛还要处理 API 31 的 `RenderEffect`、窗口背景模糊和 API 29 的 `android.graphics.BlendMode`：部分调用无条件执行，部分在预设初始化时早于任何版本判断。

## Decision

版本目录改为普通 `version = "1.1.0"`，与 Material 3 1.14.0 对齐；[发行检查](../../../../.github/scripts/verify-maven-artifacts.cjs)核对核心 `.module` 的依赖与依赖约束中至少一处请求 `1.1.0`、且没有 `strictly`。不使用 force、exclude 或 overrideLibrary。脚本只校验声明，宿主最终解析版本仍由依赖图验收。

`coui` 与 `coui-material3` 的 `minSdk` 降为 26。运行路径按能力隔离，旧系统保留布局、滚动、输入和动画，只使用不含模糊的明确填色，不新增静默降级：

| 入口 | 门槛与旧系统行为 |
| --- | --- |
| `TopAppBarSurface` | API 33+ 渐进模糊；31–32 三层平台模糊；26–30 沿用遮罩色作无模糊实底，Transparent 时不铺底 |
| 常驻 `SearchBar` | 取消文字的平台模糊从 API 31 起；26–30 沿用普通填色且不模糊文字；34+ 浮动材质不变 |
| `CouiDialogWindow` | `setBackgroundBlurRadius(0)` 从 API 31 起；26–30 只清标记并保留普通遮罩 |
| 菜单材质 | 26–30 不透明普通菜单；31–32 Gaussian；33+ Fast Kawase |
| 跟手光 | 预设改用库内 `SpotlightBlend`，平台 `BlendMode` 只在 API 33+ 绘制链构造；33 以下无 Shader 回调 |
| 浮动底栏、FAB、顶栏按钮、ChipTab | 继续按 API 34 门槛调用，低版本由宿主选用现有 M3 控件 |

平台 `BlendMode` 仍出现在 `rememberCouiSpotlight` 与 `CouiFloatingMaterial` 的公开类型里。含静态字段访问的预设工厂与数据类加 `@RequiresApi(29)`，公开组件入口和 `rememberCouiSpotlight` 不加，避免把 API 29 要求扩散到所有调用方；运行时保护由 `SDK_INT` 分支承担，`@RequiresApi` 只表达 Lint 契约。公开签名与参数语义不变，样式工厂的 Lint 契约收紧，兼容文档按函数列明。

## 依据

AndroidX 发行说明记录 1.1.0-alpha02 修复「取消待处理位置后仍对其进行处理」，1.1.0 稳定版于 2025-04-09 发布；Material Components Android 1.14.0 的版本目录请求 `androidXDynamicAnimation = "1.1.0"`。1.1.0 在运行中换目标时仍保留半帧积分，但 `cancel()` 清除待定目标，并在 API 33+ 读取系统动画时长比例。CouiSpring 的普通停止与硬重置保持分开：硬重置仍重建实例以清除速度，源码注释按 1.1.0 语义修正。

## Existing-note audit

- [普通弹簧持久通道](2026-10-29-persistent-motion-channels.md)：部分重叠。其逐帧验证基于 1.0.0 二进制，`cancel` 不清 pending 的结论已就地修正并互链；普通停止与硬重置的规则保留。
- [公开 API 收口](2026-10-23-public-api-boundary.md)、[GitHub Packages 预览](../process/2026-10-25-github-packages-preview.md)、[开关](../feature/2026-10-27-coui-switch.md)：部分重叠，严格约束相关事实就地更新并互链，其余决定保留。
- [搜索页面复核](../bug-fix/2026-11-02-secondary-page-review-fixes.md)：部分重叠，普通填色的平台区间就地更新。
- 没有同范围的 proposed/rejected 记录需要删除或归档。

## Alternatives considered

- **保留 `strictly = "1.0.0"`，由宿主排除或覆盖 M3 请求。** 严格约束与 1.1.0 无法同时满足，项目规范也禁止 force/exclude，不能作为长期方案。
- **把公开 API 的平台 `BlendMode` 全部换成库内枚举。** 能彻底消除 Lint 面上的 API 29 引用，但要改 `CouiFloatingMaterial` 等公开类型和全部预设文档，超出本轮范围；先在高版本绘制链与预设工厂划定门槛。
- **库保持 `minSdk 31`，宿主用 `overrideLibrary`。** 绕过 manifest 合并检查，规范明确禁止，且旧系统仍会在无条件调用处崩溃。
- **只加 `SDK_INT` 判断，不补 `@RequiresApi`。** 运行时安全，但 Lint 无法证明高版本调用受保护，CI 的 `NewApi` 检查会失去依据。

## Consequences

宿主可在 API 26 接入两个库模块，但 26–30 的顶栏、搜索、菜单和弹窗没有模糊效果，这是明确的视觉降级；34+ 的浮动组件仍由宿主按门槛选用替代控件。`SpotlightPreset` 不再持有平台对象，`rememberCouiSpotlight` 的可选 `blendMode` 覆盖仍要求调用方自行保证 API 29+，默认路径不受影响。预设工厂新增 `@RequiresApi(29)`，调用方 Lint 契约收紧，实际组件门槛仍是 34。

1.1.0 的 `cancel()` 会清 pending，且 API 33+ 读取系统动画时长比例；`CouiSpring.cancelComplete()`、`CouiCollapsingAppBarState`、`CouiFabExpansion`、`CouiSearchMotion`、`SpotlightState`、`ChipTab` 的中断与重启序列尚未在设备上对照，不能凭静态核对声明等价。

## Verification

核对版本目录、发行检查脚本、两库构建配置、全部 `SDK_INT` 分支与平台 API 调用点、AndroidX 发行说明和 Material 1.14.0 版本目录；源码与文档的 `strictly`、API 31 表述已同步。未执行 Android 构建、Gradle 依赖解析、Lint、AAR manifest 检查、设备行为或视觉回归。待宿主验证：`.module` 与两 AAR manifest 的实际产物、最终解析到的动画版本，以及 API 26/31/33/34 的旧系统入口、平台模糊、Shader 和浮动组件；本仓 Demo 仍是 31，API 26 验收需外部宿主或另降 Demo 配置；动画重点序列为「运行中反向 → 下一帧前取消 → 立即开始新动画」的重启前几帧与回调。

# COUI Compose

此工程包含独立的 COUI 组件库和 Demo App。库源码来自 Metric 的公共 COUI 模块，包名保留为 `com.itos.coui`。这是基于公开 Android / Compose API 的实现，不是厂商 SDK。

- `coui`：组件、效果、背景采样、几何、输入、动画及预设。
- `demo`：通过 `implementation(project(":coui"))` 使用公开 API 的 Demo。主题、页面状态和示例交互均归 App。

工程不引用 Metric 目录、约定插件、主题、资源或业务模块。Metric 仍使用原模块；当前不配置 Maven 发布、不切换 Metric 依赖、不执行公开发行。

## 本地使用

在 Android Studio 中打开本目录，完成 Gradle Sync，再选择 `demo` 运行。SDK、Java 和依赖要求以 [版本目录](gradle/libs.versions.toml)、[库配置](coui/build.gradle.kts)、[Demo 配置](demo/build.gradle.kts) 和 [Gradle JVM 配置](gradle/gradle-daemon-jvm.properties) 为准。工程使用 AGP 内置 Kotlin，不再应用 `org.jetbrains.kotlin.android`。

`demo` 的 debug 与 release 构建均使用 Android Gradle Plugin 默认的 debug 测试签名。构建时由插件使用或创建本机默认 debug keystore，不需要在工程内填写签名密码。此配置只用于 Demo 测试；本地 keystore、IDE 状态及构建产物由 `.gitignore` 排除，Gradle Wrapper 保留在版本控制中。

Demo 的 release 启用 R8 代码压缩、优化、混淆及资源裁剪，使用 AGP 默认优化规则和依赖自带的 consumer rules；debug 保留默认调试配置。COUI 库由最终 App 统一优化，不单独预混淆。定位 release 崩溃时保留对应构建的 mapping 文件。

Demo 包含三个页面：

| 页面 | 示例 |
| --- | --- |
| 组件 | 连续滑条、可见分段、隐式步进与禁用态；复选框各状态；带图标、选中项、独立勾选、分隔线、禁用项及子菜单的菜单；长菜单与滚动条 |
| 效果 | 胶囊阴影及便捷 / 底层入口；双主体融合、内容遮罩和跟随回弹；背景采样与模糊；材质混色 |
| 预设 | 全部 FAB 固定配色、轮廓光、左右附件独立收放及顶栏按钮禁用态 |

顶栏演示渐进模糊、图标按钮、文字按钮和搜索，搜索时同时展示退场动作与常驻动作的布局。搜索仅匹配当前页的分组标题。底栏切换示例页；独立 FAB 与右侧附件打开外观预设页；顶栏“重置”恢复默认 FAB 配色。深浅色通过顶栏或左侧附件切换。全部数值滑条使用 CouiSlider，切页和筛选保留受控值。融合示例使用三条独立的示例记录，展示侧滑、跟随回弹、内容遮罩、删除确认和离场动画；删除只改变 Demo 内存状态，清空后可恢复。侧滑装配只在 Demo，库继续提供算法。滑条占满分组的可用宽度；长菜单限制高度并保留四组三项。

M3 装配参考 Metric：页面使用 `surfaceContainer` 层级，示例卡片使用 `surfaceBright`，前景按配套语义颜色绘制。Demo 独立使用系统动态色；G2 形状和开关装配留在 App，`capsule` 依赖也只进入 App。设置使用整行开关，预设使用单选组，复选框只用于复选框示例；子控件不重复创建点击节点。M3 导航在竖屏使用底栏、横屏使用侧栏，选中项保留可用状态。COUI 始终保留底栏。

不支持相应效果的平台会显示版本要求；浮动渲染器创建失败会显示失败信息。旧平台的普通页面导航只用于访问仍可运行的示例，不代表 COUI 效果已通过验证。

## 验证边界

当前只执行静态检查，没有运行 Android 构建、Gradle Sync、Preview、安装或设备验证。请在 Android Studio 中完成 Sync 和运行，检查深浅色、搜索与键盘、菜单与子菜单、底栏切页及附件收放。另检查大字体、TalkBack、键盘焦点、鼠标悬停、横竖屏和系统导航模式下的遮挡、模糊与阴影。

公共接口与装配限制见 [库说明](coui/README.md)。模块规则见 [AGENTS.md](AGENTS.md)，迁出决定见 [架构记录](.agents/notes/implemented/architecture/2026-10-23-standalone-coui-demo.md)。

M3 装配见 [Demo 展示记录](.agents/notes/implemented/feature/2026-10-23-demo-material-and-gallery.md)，当前展示范围的取舍见 [Demo 精简记录](.agents/notes/implemented/feature/2026-10-24-demo-focused-showcase.md)。

## 开发与接入

- [接入指南](docs/INTEGRATION.md)：源码模块依赖、最小阴影示例、背景源、组件装配与底层算法。
- [消费者兼容要求](docs/COMPATIBILITY.md)：工具链边界、依赖对齐、效果平台门槛及待验证范围。
- [Developer 指南](DEVELOPER.md)：官方 1:1 对齐、代码风格、公开 API、核心 Note 和 PR 流程。
- [实现分析与复刻指南](docs/coui-replication-guide.md)：从原厂页面追踪到实际算法、典型移植链路、排错方法及证据交接。

重要变更通过功能分支提交 PR，禁止直接推送主分支。Note Skill 与静态校验随仓库提供；CI 只检查 Note，不执行 Android 构建或发布。仓库当前没有远端配置，分支保护需在后续配置远端时启用。

# 运行与操作 Demo

## 运行

在 Android Studio 中打开仓库根目录，完成 Gradle Sync，再选择 `demo` 运行。SDK、Java 和依赖要求以 [版本目录](../../gradle/libs.versions.toml)、[库配置](../../coui/build.gradle.kts)、[Demo 配置](../../demo/build.gradle.kts) 和 [Gradle JVM 配置](../../gradle/gradle-daemon-jvm.properties) 为准。工程使用 AGP 内置 Kotlin，不再应用 `org.jetbrains.kotlin.android`。

`demo` 的 debug 与 release 构建均使用 Android Gradle Plugin 默认的 debug 测试签名。构建时由插件使用或创建本机默认 debug keystore，不需要在工程内填写签名密码。此配置只用于 Demo 测试；本地 keystore、IDE 状态及构建产物由 `.gitignore` 排除，Gradle Wrapper 保留在版本控制中。

Demo 的 release 启用 R8 代码压缩、优化、混淆及资源裁剪，使用 AGP 默认优化规则和依赖自带的 consumer rules；debug 保留默认调试配置。COUI 库由最终 App 统一优化，不单独预混淆。定位 release 崩溃时保留对应构建的 mapping 文件。

Demo 包含三个页面：

| 页面 | 示例                                                                                                  |
|----|-----------------------------------------------------------------------------------------------------|
| 组件 | 连续滑条、可见分段、隐式步进与禁用态；复选框各状态；带图标、选中项、独立勾选、分隔线、禁用项及子菜单的菜单；长菜单与滚动条；ChipTab 的单选、分类菜单、可选图标按钮与纯色背景、禁用态及横向滚动 |
| 效果 | 胶囊阴影及便捷 / 底层入口；双主体融合、内容遮罩和跟随回弹；背景采样与模糊；材质混色                                                         |
| 预设 | 全部 FAB 固定配色、轮廓光、左右附件独立收放及顶栏按钮禁用态                                                                    |

顶栏演示渐进模糊、图标按钮、文字按钮和搜索，搜索时同时展示退场动作与常驻动作的布局。搜索仅匹配当前页的分组标题。底栏切换示例页；独立 FAB 与右侧附件打开外观预设页；顶栏“重置”恢复默认 FAB 配色。深浅色通过顶栏或左侧附件切换。全部数值滑条使用 CouiSlider，切页和筛选保留受控值。融合示例使用三条独立的示例记录，展示侧滑、跟随回弹、内容遮罩、删除确认和离场动画；删除只改变 Demo 内存状态，清空后可恢复。侧滑装配只在 Demo，库继续提供算法。滑条占满分组的可用宽度；长菜单限制高度并保留四组三项。

Demo 的 M3 页面使用 `surfaceContainer` 层级，示例卡片使用 `surfaceBright`，前景按配套语义颜色绘制。Demo 使用系统动态色；G2 形状和开关装配留在 App，`capsule` 依赖也只进入 App。设置使用整行开关，预设使用单选组，复选框只用于复选框示例；子控件不重复创建点击节点。M3 导航在竖屏使用底栏、横屏使用侧栏，选中项保留可用状态。COUI 始终保留底栏。

不支持相应效果的平台会显示版本要求；浮动渲染器创建失败会显示失败信息。旧平台的普通页面导航只用于访问仍可运行的示例，不代表 COUI 效果已通过验证。

## 源码导航

- [DemoApp](../../demo/src/main/java/com/itos/coui/demo/DemoApp.kt)：页面、采样源、顶栏、搜索、底栏和 FAB。
- [DemoSamples](../../demo/src/main/java/com/itos/coui/demo/DemoSamples.kt)：滑条、复选框与菜单。
- [DemoChipSamples](../../demo/src/main/java/com/itos/coui/demo/DemoChipSamples.kt)：ChipTab 与分类菜单。
- [DemoVisualEffects](../../demo/src/main/java/com/itos/coui/demo/DemoVisualEffects.kt)：采样模糊与材质混色。
- [DemoShadowSamples](../../demo/src/main/java/com/itos/coui/demo/DemoShadowSamples.kt)：便捷和底层阴影。
- [DemoRecordSamples](../../demo/src/main/java/com/itos/coui/demo/DemoRecordSamples.kt)：侧滑驱动融合、确认和示例删除。

宿主接入从[组件](../components/README.md)与[效果](../effects/README.md)页面开始。Demo 的主题及私有装配函数留在示例模块。

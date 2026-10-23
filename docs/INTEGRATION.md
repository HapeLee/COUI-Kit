# COUI 接入指南

先检查 [消费者兼容要求](COMPATIBILITY.md)。当前支持在源码工程中通过 Android Library module 接入，Maven 坐标与发行产物尚未配置。

## 添加模块

独立宿主工程可复制本仓库的 `coui/` 到自己的根目录，并合并 [版本目录](../gradle/libs.versions.toml) 中该模块引用的库和插件别名。保留库资源、既有许可及构建配置，不要只复制 Kotlin 文件。插件版本由宿主统一声明，避免同一插件出现不同版本。先按当前开发基线对齐，不能省略 Compose 插件或随意放宽 DynamicAnimation 约束。

宿主 `settings.gradle.kts`：

```kotlin
include(":coui")
```

消费者模块 `build.gradle.kts`：

```kotlin
android {
    buildFeatures { compose = true }
}

dependencies {
    implementation(project(":coui"))
}
```

以上是增量配置，消费者仍需自己的 Android/Compose 插件、SDK 和主题配置。此工程的 [demo/build.gradle.kts](../demo/build.gradle.kts) 是完整示例，不要求普通宿主复制 Demo 签名、应用标识或 Capsule 依赖。当前源码接入方式不代表独立消费者已完成验证。

## 最小普通阴影

下面使用便捷入口，不需要背景采样。示例没有应用文案，可直接放入支持 API 34 的 Compose 内容。宿主在进入此函数前检查版本；父级需要为阴影保留绘制空间，不能沿主体边界裁剪。

```kotlin
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.itos.coui.effect.CouiShadowDefaults
import com.itos.coui.effect.couiShadow
import com.itos.coui.shape.CouiShape

@RequiresApi(34)
@Composable
fun ShadowSample(dark: Boolean) {
    val radius = 24.dp
    Box(
        Modifier.padding(32.dp).size(160.dp, 72.dp)
            .couiShadow(style = CouiShadowDefaults.menu(dark), radius = radius)
            .background(
                color = if (dark) Color.DarkGray else Color.White,
                shape = CouiShape(radius, cornerWeight = 2f),
            ),
    )
}
```

便捷阴影不改变布局，长度使用 dp；窗口坐标、节点 alpha、轴向缩放和多主体请直接使用 `CouiShadowRenderer`。普通 RoundRect 不支持 inverse clipping，不能开启对应参数。完整低层示例见 [DemoShadowSamples](../demo/src/main/java/com/itos/coui/demo/DemoShadowSamples.kt)。

## 背景源和浮动组件

在所属窗口创建 `rememberCouiBackdrop()`，将 `Modifier.couiBackdrop(source)` 只挂到一个内容节点。该节点应包含不透明页面及滚动内容。顶栏、底栏、FAB 和菜单作为源子树外的消费者；不得采样自身，也不要把一个源 Modifier 挂到多个节点。

自定义 `drawSource` 时要绘制真实内容，不能只录制补底。源按自身尺寸栅格化，消费者负责本地坐标映射。跨窗口通过真实屏幕坐标转换；透明系统窗口不是屏幕截图源。Dialog/BottomSheet 需要自己的所属窗口源。菜单使用 `ProvideCouiMenuBackdrop` 的回调 Modifier 包住该窗口的页面内容，Popup 不进入该采样子树。

| 组件 | 宿主必须提供的装配 |
| --- | --- |
| 顶栏 | `CouiTopAppBar` 接页面源。按钮组、按钮、搜索放在其 content 内。完整槽位用 `CouiTopAppBarButtonGroup`，不要逐按钮提升后丢失祖先淡化。顶栏不替宿主处理全部滚动与 inset。 |
| 按钮 | 提供 `CouiToolbarStyle`、回调和内容。普通图标按钮可省略 enabled、text 和 renderer；默认 renderer 由该调用点 remember，创建异常向上传播。需要自行捕获创建失败时，先创建并显式传入 renderer。 |
| 搜索 | 按钮和搜索框共用一个 `rememberCouiTopAppBarSearchState`；可使用自带 renderer，或显式传入单主体实例。查询、展开目标、焦点请求、IME 与返回由宿主维护。常驻动作使用 `CouiTopAppBarSearchAction` 的 visibleWhileSearching。 |
| 底栏 | 提供三主体 renderer、导航项、受控选择、附件可见性和回调。底栏自己消费底部安全区；内容留白使用实测总高度，不能再加一次系统 inset。 |
| FAB | 提供单主体 renderer、Shape、材质和阴影。同一最终底色同时传入 `CouiFabDefaults.material` 和 `shadow`，不分别再取主题色。 |
| 菜单 | 提供 items、expanded、关闭/提交回调和 `CouiMenuStyle`。触点定位使用 rememberCouiMenuAnchor/couiMenuAnchor；键盘触发保留默认锚点。父项只展开，子项提交真实 payload。 |
| 滑条 | 提供受控值、范围、steps、配色、Shape 与回调。showTickMarks 只控制点位与相应交互，不删除逻辑步进。保存或确认仍归宿主。 |
| 复选框 | 只绘制状态。宿主添加点击及无障碍语义；整行可点击时不建立第二个点击节点。 |

每个独立绘制节点持有自己的可变 renderer。单主体使用 `CouiFloatingRenderer.standalone()`，底栏使用 `CouiFloatingRenderer()`；不能为了减少创建跨节点共享。Shader 创建失败应记录原因并由宿主显示错误或明确的替代 UI。Compose content 不能包在普通 try/catch 中；需要处理创建异常时，在 remember 的工厂中捕获创建结果，再依据结果决定是否调用组件。协程取消不能转成创建失败。

深浅色、字体和主题色由宿主显式提供。固定效果参数直接调用 Defaults；轮廓光通过 `CompositionLocalProvider(LocalCouiContourGlowEnabled provides enabled)` 控制，普通阴影不会因此一并关闭。高级 `CouiToolbarStyle` 的光效开关也需与宿主当前值保持一致。

顶栏和菜单的完整可运行装配见 [DemoApp](../demo/src/main/java/com/itos/coui/demo/DemoApp.kt)、[DemoControls](../demo/src/main/java/com/itos/coui/demo/DemoControls.kt) 与 [DemoSamples](../demo/src/main/java/com/itos/coui/demo/DemoSamples.kt)。

## 双主体融合算法

`CouiMetaballBody` 使用绘制节点本地像素，传入主体中心、尺寸和圆角。`CouiMetaballRenderer` 处理横向同色双主体 CONIC 场；背景和内容遮罩使用相同几何及融合参数，各自持有 renderer。遮罩先将内容录制到独立离屏层，再用 `Color.White` 与 `BlendMode.DstIn` 绘制。不要把背景 renderer 与遮罩共享为同一可变实例。

`rememberCouiMetaballFollowingTranslation` 提供跟随回弹，参数预设在 `CouiSwipeRevealDefaults`。RTL、布局、输入、业务确认和提交属于宿主；库没有侧滑容器。交互示例见 [DemoSamples](../demo/src/main/java/com/itos/coui/demo/DemoSamples.kt)。

## 自定义与内部边界

普通使用从组件和 Defaults 进入；自定义绘制保留 Backdrop、阴影、材质/轮廓/跟手光、浮动表面、运动与融合底层接口，清单见 [库说明](../coui/README.md)。每个接口仍需遵守自己的平台与坐标要求。

滚动条、菜单运动帧/定位、菜单项遮罩、搜索运动实现和底栏附件内部状态不供宿主调用。菜单模糊从 `rememberCouiMenuBlur` 获取；其返回值在旧平台可为空。诊断接口只用于开发取证，按主线程和明确启停的要求使用，不作为业务状态来源，也不默认启用截图或逐帧日志。

本轮接入说明仅经过源码与静态检查；实际构建、独立依赖消费、R8 和设备验收暂缓。发现依赖冲突时保留完整错误，不用排除依赖或静默替代掩盖问题。

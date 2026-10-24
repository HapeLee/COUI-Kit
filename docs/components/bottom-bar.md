# 底栏

CouiBottomBar 展示页面选项与左右附件，内部使用三主体浮动表面。宿主持有 selectedIndex 并执行真实导航，组件只负责选择交互与显示。

## 装配导航

下面片段位于 API 34 及以上的 Compose 内容中。backdrop 为页面源；items 为非空 CouiNavigationItem 列表；selectedIndex 和 onSelection 接入宿主页面状态。图标、无障碍文案及左右点击回调由宿主提供。

~~~kotlin
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import com.itos.coui.effect.CouiFloatingRenderer
import com.itos.coui.navigation.CouiBottomBar

val renderer = remember { CouiFloatingRenderer() }
CouiBottomBar(
    backdrop = backdrop,
    renderer = renderer,
    items = items,
    selectedIndex = selectedIndex,
    leftIcon = leftIcon,
    leftDescription = leftDescription,
    rightIcon = rightIcon,
    rightDescription = rightDescription,
    leftVisible = leftVisible,
    rightVisible = rightVisible,
    onLeftClick = onLeftClick,
    onRightClick = onRightClick,
    rightContainerColor = rightContainerColor,
    dark = dark,
    lightColor = Color.White,
    themeTint = themeTint,
    circleShape = circleShape,
    capsuleShape = capsuleShape,
    onSelection = onSelection,
)
~~~

rightContainerColor 是右附件最终底色，themeTint 和两种 Shape 来自宿主[外观配置](../guide/appearance.md)。示例的 lightColor 沿用 Demo 装配。

## 状态、布局与平台

items 必须非空，selectedIndex 属于列表；每项包含 title、icon 和 selectedIcon。左右附件显隐独立，收放期间的形变限制不代表关闭其全部交互。附件不用于挂接另一套外部 FAB 状态。

组件自己处理底部系统安全区。宿主测量底栏总高度，为滚动内容留白，不再重复叠加底部系统 inset。页面导航、方向适配与业务状态属于宿主。

要求 API 34。renderer 使用三主体构造方式，不能换成 standalone 或与其他节点共享。源应覆盖不透明页面，底栏放在源子树外，见[背景采样](../effects/backdrop.md)。

## 入口与示例

[CouiBottomBar / CouiNavigationItem](../../coui/src/main/java/com/itos/coui/navigation/CouiBottomBar.kt)；高度测量、左右附件和受控导航见 [DemoApp](../../demo/src/main/java/com/itos/coui/demo/DemoApp.kt)。

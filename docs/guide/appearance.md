# 外观与预设

把 COUI 放入现有 App 时，由宿主提供深浅色、强调色、内容色、字体和 Shape。库不要求使用独立的应用主题。

## 选择预设

| 场景      | 公开入口                                         |
|---------|----------------------------------------------|
| 滑条      | CouiSliderDefaults.colors                    |
| 复选框     | CouiCheckboxDefaults.colors                  |
| 顶栏按钮与搜索 | CouiTopAppBarButtonDefaults、CouiToolbarStyle |
| FAB     | CouiFabColor、CouiFabDefaults                 |
| ChipTab | CouiChipTabDefaults                          |
| 普通阴影    | CouiShadowDefaults                           |
| 融合跟随    | CouiSwipeRevealDefaults                      |

固定参数直接引用所属 Defaults。宿主提供的主题色与 Shape 是外观装配，不要把它们称为原厂默认。顶栏强度、搜索收起行为和菜单组合光效含已有项目适配，具体用法见各组件页。

FAB 的最终底色同时用于 material 与 shadow；分别再次取色会造成材质和阴影不一致，示例见 [FAB](../components/fab.md)。

## Shape 与样式对象

[CouiShape](../../coui/src/main/java/com/itos/coui/shape/CouiShape.kt)接受半径与角权重，[CouiCapsuleShape](../../coui/src/main/java/com/itos/coui/shape/CouiCapsuleShape.kt)提供胶囊。宿主可传入自己的 Shape；材质距离场、内容 Path 和投影 Outline 各有几何要求，不能认为同名圆角在所有绘制链中相同。

顶栏使用 [CouiToolbarStyle](../../coui/src/main/java/com/itos/coui/topappbar/CouiToolbarStyle.kt)描述文字、光效、形状、材质和阴影。菜单使用 [CouiMenuStyle](../../coui/src/main/java/com/itos/coui/menu/CouiMenuStyle.kt)描述表面、项目、复选框及文字样式。它们只描述外观，不读取 App 偏好或持有业务状态。

## 控制轮廓光

在 Compose 内容中，用同一个宿主状态控制对应组件的轮廓和内晕：

~~~kotlin
import androidx.compose.runtime.CompositionLocalProvider
import com.itos.coui.effect.LocalCouiContourGlowEnabled

CompositionLocalProvider(LocalCouiContourGlowEnabled provides contourEnabled) {
    content()
}
~~~

contourEnabled 是宿主 Boolean，content 是要展示的 Compose 内容。显式构造 CouiToolbarStyle 时，它的 contourGlowEnabled 也使用同一值。普通阴影、模糊、材质和跟手光分别由各自入口控制。

# ChipTab 分类栏

CouiChipTabRow 显示可横向滚动的单选分类，并可在分类组外放置一个独立动作。它负责选中项露出、惯性和回弹，宿主负责分类数据与选中值。

## 受控选择

下面函数接收页面背景源、项目列表及受控选择，使用组件自身的 renderer 工厂：

~~~kotlin
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.itos.coui.backdrop.CouiBackdrop
import com.itos.coui.chip.CouiChipTabItem
import com.itos.coui.chip.CouiChipTabRow

@RequiresApi(34)
@Composable
fun Categories(
    modifier: Modifier = Modifier,
    items: List<CouiChipTabItem>,
    selectedKey: Any?,
    onSelectedKeyChange: (Any) -> Unit,
    backdrop: CouiBackdrop,
    dark: Boolean,
) {
    CouiChipTabRow(
        modifier = modifier.fillMaxWidth(),
        items = items,
        selectedKey = selectedKey,
        onSelectedKeyChange = onSelectedKeyChange,
        backdrop = backdrop,
        dark = dark,
    )
}
~~~

## 项目与交互

- key 在同一栏中唯一，并在重排和重组期间保持稳定。非空列表的 selectedKey 必须属于列表，空列表使用 null。
- 重复点击不取消选择。点击、键盘焦点和选中值变化会露出对应项目；可用宽度改变时重新露出选中项，首次布局保留初始或恢复的滚动位置。
- 长项目按完整可用宽度测量，文字超出后省略。横向裁剪同时限制点击，纵向保留阴影空间。
- leadingAction 位于单选组外，null 表示只显示分类。需要退出过渡时保留槽位内容，仅改变 leadingActionVisible，让动作宽度、透明度和留白同步过渡。
- itemSpacing 和 animationLevel 控制间距与动画等级；参数含义见公开声明，不读取厂商私有系统属性。

## 样式与平台

要求 API 34，源必须是不透明兄弟节点。宿主提供文字样式、内容色与图标；图标不被组件重新染色。默认样式从 CouiChipTabDefaults 获取。

分类项与独立图标按钮各持有单主体 renderer。显式传 renderers 时，与 items 按顺序对应且实例唯一。需要处理创建失败时，在调用前创建并处理结果，见[资源规则](../effects/backdrop.md#资源与失败处理)。

## 独立入口与示例

[CouiChipTabRow / CouiChipTabItem](../../coui/src/main/java/com/itos/coui/chip/CouiChipTabRow.kt)、[CouiChipTab / CouiChipTabButton](../../coui/src/main/java/com/itos/coui/chip/CouiChipTab.kt)、[Defaults](../../coui/src/main/java/com/itos/coui/chip/CouiChipTabDefaults.kt)。单独使用分类项或按钮时，宿主负责组装选中值、背景源、语义与独立 renderer。分类菜单、图标动作和纯色背景见 [DemoChipSamples](../../demo/src/main/java/com/itos/coui/demo/DemoChipSamples.kt)。

# 顶栏与按钮

CouiTopAppBar 将页面背景处理为渐进模糊，并为按钮、按钮组与搜索提供共享的材质源和转场上下文。

## 标题与操作

先在所属页面创建[背景源](../effects/backdrop.md)。下面的函数接收该源，以及宿主构造的 CouiToolbarStyle；按钮文案和点击动作由宿主传入。

~~~kotlin
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.itos.coui.backdrop.CouiBackdrop
import com.itos.coui.topappbar.CouiToolbarStyle
import com.itos.coui.topappbar.CouiTopAppBar
import com.itos.coui.topappbar.CouiTopAppBarButton
import com.itos.coui.topappbar.CouiTopAppBarButtonGroup

@RequiresApi(34)
@Composable
fun Toolbar(
    modifier: Modifier = Modifier,
    backdrop: CouiBackdrop,
    style: CouiToolbarStyle,
    title: String,
    actionLabel: String,
    onAction: () -> Unit,
) {
    CouiTopAppBar(modifier = modifier, backdrop = backdrop) {
        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Text(title, Modifier.weight(1f))
            CouiTopAppBarButtonGroup {
                CouiTopAppBarButton(onClick = onAction, text = true, style = style) {
                    Text(actionLabel)
                }
            }
        }
    }
}
~~~

## 样式与布局

CouiToolbarStyle 的 circleShape、capsuleShape、contentColor、textStyle 与 cursorColor 由宿主提供；material 和 shadow 工厂从 CouiTopAppBarButtonDefaults 取得预设，并使用同一轮廓光状态。构造示例见 [DemoStyles](../../demo/src/main/java/com/itos/coui/demo/DemoStyles.kt)，接入时使用宿主自己的字体、颜色和 Shape。

按钮组包住完整操作槽位，放在槽位进出动画之外，保留祖先淡化、原位输入与锚点。需要显隐过渡时使用 CouiTopAppBarActionVisibility。按钮和组都放在 CouiTopAppBar 的 content 内。

普通按钮默认在当前调用点 remember 单主体 renderer；需要处理创建失败时，先创建并显式传入，见[资源和失败处理](../effects/backdrop.md#资源与失败处理)。text 选择文字按钮，enabled 控制交互。图标内容由宿主提供可访问说明。

渐进背景可向下延伸，但不增加顶栏布局高度或命中范围。宿主负责滚动安全区与顶部系统 inset，避免与父级重复 padding。当前强度和部分转场包含已批准适配。

## 平台与入口

容器内部保留旧平台路径；浮动按钮要求 API 34。完整平台表见[兼容要求](../guide/compatibility.md)。

[顶栏](../../coui/src/main/java/com/itos/coui/topappbar/CouiTopAppBar.kt)、[按钮](../../coui/src/main/java/com/itos/coui/topappbar/CouiTopAppBarButton.kt)、[按钮组](../../coui/src/main/java/com/itos/coui/topappbar/CouiTopAppBarButtonGroup.kt)、[预设](../../coui/src/main/java/com/itos/coui/topappbar/CouiTopAppBarButtonDefaults.kt)。搜索见[独立使用页](search.md)，完整页面见 [DemoApp](../../demo/src/main/java/com/itos/coui/demo/DemoApp.kt)。

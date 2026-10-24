# 菜单

CouiMenu 提供选中项、独立勾选、分隔、禁用项与一级子菜单。宿主持有展开状态和选择结果，菜单管理 Popup、输入及退场资源。

## 打开并提交选择

下面是一个页面容器。style 是宿主构造的 CouiMenuStyle；items 是带真实业务值的项目，buttonLabel 与页面内容来自宿主。

~~~kotlin
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.itos.coui.menu.CouiMenu
import com.itos.coui.menu.CouiMenuItem
import com.itos.coui.menu.CouiMenuStyle
import com.itos.coui.menu.ProvideCouiMenuBackdrop
import com.itos.coui.menu.couiMenuAnchor
import com.itos.coui.menu.rememberCouiMenuAnchor

@Composable
fun MenuPage(
    modifier: Modifier = Modifier,
    items: List<CouiMenuItem<Int>>,
    style: CouiMenuStyle,
    background: Color,
    buttonLabel: String,
    onSelect: (Int) -> Unit,
    content: @Composable () -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }
    val anchor = rememberCouiMenuAnchor()
    ProvideCouiMenuBackdrop { source ->
        Box(modifier.fillMaxSize()) {
            Column(Modifier.fillMaxSize().then(source).background(background)) {
                Button(
                    modifier = Modifier.couiMenuAnchor(anchor),
                    onClick = { anchor.open { expanded = true } },
                ) { Text(buttonLabel) }
                content()
            }
            CouiMenu(
                modifier = Modifier.heightIn(max = 320.dp),
                expanded = expanded,
                onDismissRequest = { expanded = false },
                items = items,
                onItemClick = { item -> onSelect(item.value); expanded = false },
                style = style,
                anchor = anchor,
            )
        }
    }
}
~~~

页面背景须不透明。菜单自身位于采样子树外；Dialog 等窗口需在所属窗口重新提供源，见[背景采样](../effects/backdrop.md)。

## 项目与外观

| 字段 | 语义 |
| --- | --- |
| value | 提交给宿主的业务值 |
| selected | 当前选择的视觉状态 |
| checked | 独立开关状态，与 selected 分开；null 表示普通项目 |
| children | 一级子菜单；父项只展开，子项提交业务值 |
| dividerBefore | 项目前的分组分隔，不参与选择 |
| enabled | 是否接受点击或滑动提交 |

子项不能继续嵌套。触点定位使用 anchor.open 与 couiMenuAnchor；键盘触发保留默认锚点。modifier 可限制可用高度，超出内容通过菜单自己的滚动区访问。

CouiMenuStyle 接收颜色、菜单与项目 Shape、复选框样式、文字和 themeTint。菜单使用已批准的组合光效与主题染色，不是未经适配的整套原厂菜单默认。

## 入口与示例

[CouiMenu / CouiMenuItem](../../coui/src/main/java/com/itos/coui/menu/CouiMenu.kt)、[样式](../../coui/src/main/java/com/itos/coui/menu/CouiMenuStyle.kt)、[触点锚点](../../coui/src/main/java/com/itos/coui/menu/CouiMenuAnchor.kt)；选中、勾选、子菜单及限高分组示例见 [DemoSamples](../../demo/src/main/java/com/itos/coui/demo/DemoSamples.kt)。平台模糊分支见[兼容要求](../guide/compatibility.md)。

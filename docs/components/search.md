# 顶栏搜索

CouiTopAppBarSearchButton 与 CouiTopAppBarSearch 使用同一个搜索状态，将按钮转场为输入框。宿主持有查询、展开目标、焦点请求、IME 和返回操作。

## 展开与关闭

下面的顶栏示例在 API 34 及以上使用。backdrop 来自页面背景源，style 与 searchStyle 是宿主的普通按钮、搜索外观；所有文案从宿主资源传入。

~~~kotlin
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import com.itos.coui.backdrop.CouiBackdrop
import com.itos.coui.topappbar.CouiToolbarStyle
import com.itos.coui.topappbar.CouiTopAppBar
import com.itos.coui.topappbar.CouiTopAppBarButtonGroup
import com.itos.coui.topappbar.CouiTopAppBarSearch
import com.itos.coui.topappbar.CouiTopAppBarSearchButton
import com.itos.coui.topappbar.rememberCouiTopAppBarSearchState

@RequiresApi(34)
@Composable
fun SearchToolbar(
    modifier: Modifier = Modifier,
    backdrop: CouiBackdrop,
    style: CouiToolbarStyle,
    searchStyle: CouiToolbarStyle,
    title: String,
    placeholder: String,
    searchDescription: String,
    closeDescription: String,
    query: String,
    onQueryChange: (String) -> Unit,
    onSearch: () -> Unit,
) {
    var expanded by rememberSaveable { mutableStateOf(false) }
    var focusPending by remember { mutableStateOf(false) }
    val state = rememberCouiTopAppBarSearchState(expanded)
    val keyboard = LocalSoftwareKeyboardController.current
    val close = { expanded = false; focusPending = false; keyboard?.hide(); Unit }
    BackHandler(enabled = expanded, onBack = close)
    CouiTopAppBar(modifier = modifier, backdrop = backdrop) {
        CouiTopAppBarSearch(
            state = state,
            style = searchStyle,
            query = query,
            onQueryChange = onQueryChange,
            onSearch = onSearch,
            placeholder = placeholder,
            focusPending = focusPending,
            onFocused = { focusPending = false },
            trailingIcon = {
                IconButton(onClick = close) {
                    Icon(Icons.Default.Close, contentDescription = closeDescription)
                }
            },
        ) {
            Row(Modifier.fillMaxWidth()) {
                Text(title, Modifier.weight(1f))
                CouiTopAppBarButtonGroup {
                    CouiTopAppBarSearchButton(
                        state = state,
                        style = style,
                        onClick = { expanded = true; focusPending = true },
                        contentDescription = searchDescription,
                    )
                }
            }
        }
    }
}
~~~

示例使用 Activity Compose 的 BackHandler 与 Material3 图标按钮；宿主需直接声明这些依赖。

## 状态与转场

按钮和输入框必须在 CouiTopAppBar 内，共享同一 state。组件不持久化查询，不替宿主执行搜索业务。焦点请求完成后清除 focusPending，避免重复请求。

CouiTopAppBarSearchAction 的 visibleWhileSearching 选择常驻动作；普通动作按搜索状态退场。UI 的 visible、展开目标和查询分别维护：仅隐藏顶栏时保留展开目标，业务结束搜索时再关闭、处理查询和键盘。

搜索状态工厂默认持有独立单主体 renderer；需要展示构造错误时，可自行创建并显式传入。常驻动作移动、收起避让及部分速度包含已有适配。固定顶栏的可见范围与父级滚动协调，不通过延迟焦点或跳页掩盖布局问题。

## 入口与示例

[搜索与动作](../../coui/src/main/java/com/itos/coui/topappbar/CouiTopAppBarSearch.kt)、[状态工厂](../../coui/src/main/java/com/itos/coui/topappbar/CouiTopAppBarSearchState.kt)。常驻/退场操作、返回和页面搜索见 [DemoApp](../../demo/src/main/java/com/itos/coui/demo/DemoApp.kt)；公共准备见[顶栏](top-app-bar.md)与[背景采样](../effects/backdrop.md)。

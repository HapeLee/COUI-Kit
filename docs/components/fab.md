# FAB

CouiFab 是独立浮动操作按钮。它使用单主体材质与投影，不依赖底栏，也不创建假附件。

## 使用配色预设

下面函数接收页面背景源、配色预设、点击动作和图标内容。宿主在调用前检查 API 34，并为图标提供无障碍说明。

~~~kotlin
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.itos.coui.backdrop.CouiBackdrop
import com.itos.coui.effect.CouiFloatingRenderer
import com.itos.coui.effect.LocalCouiContourGlowEnabled
import com.itos.coui.floatingactionbutton.CouiFab
import com.itos.coui.floatingactionbutton.CouiFabColor
import com.itos.coui.floatingactionbutton.CouiFabDefaults
import com.itos.coui.shape.CouiShape

@RequiresApi(34)
@Composable
fun PresetFab(
    modifier: Modifier = Modifier,
    backdrop: CouiBackdrop,
    dark: Boolean,
    preset: CouiFabColor,
    onClick: () -> Unit,
    content: @Composable () -> Unit,
) {
    val renderer = remember { CouiFloatingRenderer.standalone() }
    val color = CouiFabDefaults.containerColor(preset, dark)
    val density = LocalDensity.current.density
    val contour = LocalCouiContourGlowEnabled.current
    CouiFab(
        modifier = modifier,
        backdrop = backdrop,
        renderer = renderer,
        onClick = onClick,
        dark = dark,
        shape = CouiShape(999.dp, cornerWeight = 2f),
        material = CouiFabDefaults.material(color, dark),
        shadow = CouiFabDefaults.shadow(color, density, contour),
        content = content,
    )
}
~~~

## 颜色与资源

使用同一个最终 color 构造材质和阴影。自定义底色时也遵守这一条件，不在两处独立取色。

固定配色由 CouiFabColor 与 CouiFabDefaults 持有。普通投影、有色 caustic 和轮廓属于不同绘制部分；关闭轮廓时使用该状态生成对应 shadow 预设，不能仅隐藏一层前景。

每个独立 FAB 持有自己的 standalone renderer；构造失败与[公共资源规则](../effects/backdrop.md#资源与失败处理)一致。FAB 放在不透明采样源外，父级为投影保留绘制空间。

## 入口与示例

[CouiFab](../../coui/src/main/java/com/itos/coui/floatingactionbutton/CouiFab.kt)、[配色](../../coui/src/main/java/com/itos/coui/floatingactionbutton/CouiFabColor.kt)、[Defaults](../../coui/src/main/java/com/itos/coui/floatingactionbutton/CouiFabDefaults.kt)。完整示例见 [DemoApp](../../demo/src/main/java/com/itos/coui/demo/DemoApp.kt)。

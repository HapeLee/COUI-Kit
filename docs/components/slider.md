# 滑动选择条

CouiSlider 用于调节音量、强度等数值，支持连续选择、可见分段和隐藏点位的逻辑步进。完整连续示例见[快速开始](../guide/getting-started.md)。

## 分段选择

下面的函数放入 Compose 页面，由宿主持有 value，并提供深浅色与强调色。steps 表示内部等分点数，端点仍属于可选值：

~~~kotlin
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.itos.coui.shape.CouiCapsuleShape
import com.itos.coui.slider.CouiSlider
import com.itos.coui.slider.CouiSliderDefaults

@Composable
fun SteppedSlider(
    modifier: Modifier = Modifier,
    value: Float,
    onValueChange: (Float) -> Unit,
    dark: Boolean,
    accent: Color,
    onFinished: () -> Unit,
) {
    CouiSlider(
        modifier = modifier.fillMaxWidth(),
        value = value,
        onValueChange = onValueChange,
        onValueChangeFinished = onFinished,
        valueRange = 0f..4f,
        steps = 3,
        showTickMarks = true,
        colors = CouiSliderDefaults.colors(accent, dark),
        dark = dark,
        shape = CouiCapsuleShape,
    )
}
~~~

## 状态与外观

- onValueChange 更新当前逻辑值，保存或确认仍由宿主完成；onValueChangeFinished 用于结束本次数值调整。
- steps 保留逻辑步进；showTickMarks 决定是否展示 Section 点位及对应吸附。关闭点位不删除内部步进。
- enabled 控制交互。输入值、范围端点须为有限数，范围不能反向，steps 必须有效。
- Modifier.fillMaxWidth 让轨道适应可用宽度；组件本身有最小尺寸，不自动占满父级。
- 配色从 CouiSliderDefaults.colors 获取，轨道 Shape 由宿主选择。G2 外观属于宿主适配；专用马达波形及完整厂商物理表现仍需设备确认。

## 入口与示例

[CouiSlider](../../coui/src/main/java/com/itos/coui/slider/CouiSlider.kt)、[Defaults](../../coui/src/main/java/com/itos/coui/slider/CouiSliderDefaults.kt)；完整受控与禁用示例见 [DemoSamples](../../demo/src/main/java/com/itos/coui/demo/DemoSamples.kt)。平台要求见[兼容要求](../guide/compatibility.md)。

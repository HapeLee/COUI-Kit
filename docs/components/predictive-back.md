# Nav3 页面转场

在 `ComponentActivity` 中，用主模块的 `PredictiveBackNavDisplay` 显示已有的 Nav3 条目，选择页面前进、返回与手势预览动画。自定义 Compose 宿主需提供 `LocalNavigationEventDispatcherOwner`。

```kotlin
import com.itos.coui.navigation3.CouiBackPreset
import com.itos.coui.navigation3.PredictiveBackNavDisplay

PredictiveBackNavDisplay(
    entries = entries,
    preset = CouiBackPreset.CHATGPT,
    onBack = { backStack.removeLastOrNull() },
)
```

| 预设 | 页面表现 |
| --- | --- |
| `CHATGPT` | 前后页沿水平方向错开并淡入淡出，Nav3 根据系统进度推进或回退。 |
| `AOSP` | 当前页随手势缩成圆角卡片，露出上一页。提交从最后绘制的一帧续播，取消时卡片回到原位。 |

宿主持有非空的条目列表与返回栈。每次 `onBack` 移除栈顶一个条目；自定义 Scene 返回需要移除多个条目时，容器按数量回调。根页面将系统返回交给上层，取消手势保留当前栈。设备或返回方式未提供手势进度时使用普通返回转场。

## 多标签与分栏

多个标签页常驻组合时，对每个容器传入 `backHandlerEnabled = page == currentPage`。页面内的弹窗和输入法继续使用宿主的系统返回分发器。

将分栏策略传入 `sceneStrategies`，Nav3 在策略均未命中时使用单栏。预设负责整页手势预览及收尾。详情面板有自己的关闭动画时，让 `usePredictiveTransition` 对这一对 Scene 返回 `false`。松手提交后，宿主移除条目，Nav3 使用该场景配置的普通返回动画。

```kotlin
PredictiveBackNavDisplay(
    entries = entries,
    preset = CouiBackPreset.AOSP,
    backHandlerEnabled = page == currentPage,
    sceneStrategies = listOf(listDetailStrategy),
    usePredictiveTransition = { from, to -> !isPaneTransition(from, to) },
    onBack = { backStack.removeLastOrNull() },
)
```

`backgroundColor` 设置容器背景，并与 `contentColor` 的亮度一起决定 AOSP 遮罩深浅。系统提供屏幕圆角半径时，`deviceCornerShape` 将该半径（Dp）转换为卡片形状，默认使用 `CouiShape`；缺少半径时使用 `fallbackShape`。与外部栏位同步动画时，可读取 `preset.backReleaseDurationMillis`，按毫秒估计提交等待窗口。

见[组件源码](../../coui/src/main/java/com/itos/coui/navigation3/PredictiveBackNavDisplay.kt)与[预设定义](../../coui/src/main/java/com/itos/coui/navigation3/CouiBackPreset.kt)。

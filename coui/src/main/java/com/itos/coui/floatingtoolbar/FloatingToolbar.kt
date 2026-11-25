package com.itos.coui.floatingtoolbar

import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.isSpecified
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.rememberGraphicsLayer
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalViewConfiguration
import androidx.compose.ui.platform.ViewConfiguration
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.constrainWidth
import androidx.compose.ui.unit.constrainHeight
import kotlin.math.roundToInt
import com.itos.coui.backdrop.CouiBackdrop
import com.itos.coui.backdrop.LocalCouiBackdrop
import com.itos.coui.effect.CouiFloatingRenderer
import com.itos.coui.effect.CouiFloatingSurface
import com.itos.coui.effect.SpotlightPreset
import com.itos.coui.effect.couiFloatingSurface
import com.itos.coui.effect.couiToolbarFeedback
import com.itos.coui.effect.rememberCouiSpotlight
import com.itos.coui.motion.couiPressMotion
import com.itos.coui.motion.couiTouchMotion
import com.itos.coui.motion.rememberCouiPressMotion
import com.itos.coui.motion.rememberCouiScaleVisibility
import com.itos.coui.motion.rememberCouiTouchMotion
import com.itos.coui.theme.CouiTheme
import com.itos.coui.theme.ProvideCouiContent
import com.itos.coui.shape.CouiCapsuleShape
import com.itos.coui.shape.CouiShape

private val LocalToolbarSingle = compositionLocalOf { false }
private val LocalToolbarInteractive = compositionLocalOf { true }
private val LocalToolbarAnimation = compositionLocalOf<ToolbarAnimation?> { null }

/** 自定义内容槽。需要按动作 ID 增删、自动收纳时使用接收 items 的重载。 */
@RequiresApi(34)
@Composable
fun FloatingToolbar(
    modifier: Modifier = Modifier,
    backdrop: CouiBackdrop = checkNotNull(LocalCouiBackdrop.current) { "悬浮操作栏需要页面背景源" },
    dark: Boolean = CouiTheme.isDark,
    style: CouiFloatingToolbarStyle = CouiFloatingToolbarDefaults.style(dark),
    renderer: CouiFloatingRenderer = remember { CouiFloatingRenderer.standalone() },
    content: @Composable RowScope.() -> Unit,
) {
    FloatingToolbar(modifier, visible = true, backdrop = backdrop, renderer = renderer, style = style, content = content)
}

/** 保持组合并切换 visible，整栏使用与导航底栏相同的缩放显隐效果。 */
@RequiresApi(34)
@Composable
fun FloatingToolbar(
    modifier: Modifier = Modifier,
    visible: Boolean,
    backdrop: CouiBackdrop = checkNotNull(LocalCouiBackdrop.current) { "悬浮操作栏需要页面背景源" },
    renderer: CouiFloatingRenderer = remember { CouiFloatingRenderer.standalone() },
    style: CouiFloatingToolbarStyle = CouiFloatingToolbarDefaults.style(),
    content: @Composable RowScope.() -> Unit,
) {
    ToolbarSurface(modifier, visible, backdrop, renderer, style, rememberToolbarAnimation(), null, content)
}

@RequiresApi(34)
@Composable
internal fun ToolbarSurface(
    modifier: Modifier,
    visible: Boolean,
    backdrop: CouiBackdrop,
    renderer: CouiFloatingRenderer,
    style: CouiFloatingToolbarStyle,
    animation: ToolbarAnimation,
    listBatch: ToolbarItems?,
    content: @Composable RowScope.() -> Unit,
) {
    var width by remember { mutableFloatStateOf(0f) }
    var height by remember { mutableIntStateOf(0) }
    var measuredCount by remember { mutableIntStateOf(0) }
    val single = (listBatch?.target?.size ?: measuredCount) == 1
    val shape = if (single && style.shape === CouiCapsuleShape) {
        CouiShape(CouiFloatingToolbarDefaults.height / 2, 2f)
    } else style.shape
    val cornerRadius = with(LocalDensity.current) { (shape as? CouiShape)?.radius?.toPx() }
    val visibility = rememberCouiScaleVisibility(visible)
    val motion = rememberCouiTouchMotion(visible)
    val press = rememberCouiPressMotion()
    val (lightInput, drawLight) = rememberCouiSpotlight(interactive = visible,
        preset = SpotlightPreset.OpaqueSmall2, dark = style.dark, color = style.lightColor)
    val contentLayer = rememberGraphicsLayer()
    val viewConfiguration = LocalViewConfiguration.current
    val buttonConfiguration = remember(viewConfiguration) {
        object : ViewConfiguration by viewConfiguration {
            override val minimumTouchTargetSize = DpSize.Zero
        }
    }
    // 编辑器负责导航栏和 IME 的避让。此处只保留工具区自身的高度。
    Layout(modifier = modifier.height(76.dp), content = {
        Box(Modifier.couiFloatingSurface(backdrop, renderer, style.material, style.shadow, shape,
            contentLayer, style.contourGlowEnabled) {
            val x = motion.translationX.value
            val y = motion.translationY.value
            CouiFloatingSurface(
                bounds = Rect(x, y, x + width, y + height),
                scaleX = (motion.scaleX.value + press.scale.value - 1f) * visibility.scale.value,
                scaleY = (motion.scaleY.value + press.scale.value - 1f) * visibility.scale.value,
                cornerWeight = (shape as? CouiShape)?.cornerWeight ?: if (measuredCount == 1) 2f else 1f,
                cornerRadius = cornerRadius,
                alpha = visibility.alpha.value.coerceIn(0f, 1f),
                visible = visibility.isPresent && width > 0f,
            )
        }) {
            CompositionLocalProvider(
                LocalCouiFloatingToolbarStyle provides style,
                LocalToolbarSingle provides single,
                LocalToolbarInteractive provides visible,
                LocalToolbarAnimation provides animation,
                LocalViewConfiguration provides buttonConfiguration,
            ) {
                ToolbarLayout(
                    modifier = Modifier.graphicsLayer {
                        if (size.width > 0f) transformOrigin = TransformOrigin(width / (2f * size.width), 0.5f)
                        translationX = motion.translationX.value
                        translationY = motion.translationY.value
                        scaleX = (motion.scaleX.value + press.scale.value - 1f) * visibility.scale.value
                        scaleY = (motion.scaleY.value + press.scale.value - 1f) * visibility.scale.value
                    }.couiTouchMotion(motion, visible, cancelOnConsumedMove = false, consumeDrag = true)
                        .couiPressMotion(press, visible)
                        .then(lightInput)
                        .drawWithContent {
                            contentLayer.pivotOffset = Offset(width / 2f, height / 2f)
                            contentLayer.record { this@drawWithContent.drawContent() }
                        }
                        .clip(shape).drawBehind { drawLight?.invoke(this) },
                    animation = animation,
                    listBatch = listBatch,
                    present = visibility.isPresent,
                    onGeometry = { w, h, n ->
                        width = w
                        height = h
                        measuredCount = n
                    },
                    content = content,
                )
            }
        }
    }) { measurables, constraints ->
        val capsule = measurables.single().measure(constraints.copy(minWidth = 0, minHeight = 0))
        val hostWidth = constraints.constrainWidth(capsule.width)
        val hostHeight = constraints.constrainHeight(capsule.height)
        layout(hostWidth, hostHeight) {
            // 原厂居中对齐的是当帧背景的浮点边界，整数布局坐标只在最后一次摆放时量化。
            val left = (hostWidth - width) / 2f
            val integerLeft = left.roundToInt()
            capsule.placeWithLayer(integerLeft, (hostHeight - capsule.height) / 2) {
                translationX = left - integerLeft
            }
        }
    }
}

/** 操作项保留在槽中时，切换 visible 可播放入场与退出动画。 */
@Composable
fun FloatingToolbarItem(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    enabled: Boolean = true,
    visible: Boolean = true,
    content: @Composable () -> Unit,
) {
    val id = remember { Any() }
    val batch = LocalToolbarAnimation.current?.batch
    val exiting = batch != null && id in batch.old && id !in batch.target
    ToolbarButton(modifier.toolbarItem(id, visible), onClick, enabled, visible && !exiting, content)
}

@Composable
internal fun ToolbarButton(
    modifier: Modifier,
    onClick: () -> Unit,
    enabled: Boolean,
    interactive: Boolean,
    content: @Composable () -> Unit,
) {
    val style = LocalCouiFloatingToolbarStyle.current
    val source = remember { MutableInteractionSource() }
    val active = interactive && LocalToolbarInteractive.current
    val feedback = if (LocalToolbarSingle.current) CouiFloatingToolbarDefaults.singleItemFeedbackInset
        else -CouiFloatingToolbarDefaults.itemSpacing
    val input = if (active) Modifier.couiToolbarFeedback(style.dark, source, enabled, pressFeedback = true, maskInset = feedback)
        .clickable(source, indication = null, enabled = enabled, role = Role.Button, onClick = onClick)
        else Modifier.clearAndSetSemantics {}
    Box(modifier.height(CouiFloatingToolbarDefaults.itemHeight).widthIn(min = CouiFloatingToolbarDefaults.itemSize)
        .then(input), contentAlignment = Alignment.Center) {
        val color = if (enabled) style.contentColor else Color(if (style.dark) 0x4DFFFFFF else 0x42000000)
        if (color.isSpecified) ProvideCouiContent(color = color) { content() }
        else content()
    }
}

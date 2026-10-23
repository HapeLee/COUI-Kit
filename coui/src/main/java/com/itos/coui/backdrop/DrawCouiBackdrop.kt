package com.itos.coui.backdrop

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.neverEqualPolicy
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlurEffect
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.RenderEffect
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.layer.drawLayer
import androidx.compose.ui.graphics.rememberGraphicsLayer
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import kotlin.math.ceil

/** 配置当前背景的离屏效果, size 不包含 padding, 单位为设备像素。 */
class CouiBackdropEffectScope internal constructor(private val scale: Density) : Density by scale {
    var size: Size = Size.Zero
        internal set
    var padding: Float = 0f
    var renderEffect: RenderEffect? = null

    fun blur(radius: Float, edgeTreatment: TileMode = TileMode.Clamp) {
        if (radius <= 0f) return
        padding = maxOf(padding, ceil(radius))
        renderEffect = BlurEffect(renderEffect, radius, radius, edgeTreatment)
    }
}

/** 仅采样层应用效果; surface 与子内容保持清晰。 */
@Composable
fun Modifier.drawCouiBackdrop(
    backdrop: CouiBackdrop,
    shape: () -> Shape,
    effects: CouiBackdropEffectScope.() -> Unit,
    onDrawSurface: DrawScope.() -> Unit = {},
    onDrawBackdrop: (DrawScope.(DrawScope.() -> Unit) -> Unit)? = null,
): Modifier {
    val layer = rememberGraphicsLayer()
    val density = LocalDensity.current
    val effectScope = remember(density) { CouiBackdropEffectScope(density) }
    var coordinates: LayoutCoordinates? by remember { mutableStateOf(null, neverEqualPolicy()) }
    return this
        .onGloballyPositioned { coordinates = it }
        .graphicsLayer {
            this.shape = shape()
            clip = true
            compositingStrategy = CompositingStrategy.Offscreen
        }
        .drawWithContent {
            val target = coordinates
            if (target != null && target.isAttached && size.width > 0f && size.height > 0f) {
                effectScope.size = size
                effectScope.padding = 0f
                effectScope.renderEffect = null
                effectScope.effects()
                val padding = ceil(effectScope.padding).toInt()
                layer.renderEffect = effectScope.renderEffect
                layer.topLeft = IntOffset(-padding, -padding)
                layer.record(size = IntSize(size.width.toInt() + 2 * padding, size.height.toInt() + 2 * padding)) {
                    translate(padding.toFloat(), padding.toFloat()) {
                        val drawSource: DrawScope.() -> Unit = { backdrop.draw(this, target) }
                        if (onDrawBackdrop != null) onDrawBackdrop(drawSource) else drawSource()
                    }
                }
                drawLayer(layer)
            }
            onDrawSurface()
            drawContent()
        }
}

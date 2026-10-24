package com.itos.coui.demo

import android.graphics.RenderEffect
import android.graphics.Shader
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.graphics.asComposeRenderEffect
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.itos.coui.backdrop.CouiBackdrop
import com.itos.coui.backdrop.couiBackdrop
import com.itos.coui.backdrop.drawCouiBackdrop
import com.itos.coui.backdrop.rememberCouiBackdrop
import com.itos.coui.effect.CouiContour
import com.itos.coui.effect.CouiEdge
import com.itos.coui.effect.CouiEdgeStyle
import com.itos.coui.effect.LocalCouiContourGlowEnabled
import com.itos.coui.effect.SpotlightPreset
import com.itos.coui.effect.rememberCouiMaterialEffect
import com.itos.coui.effect.rememberCouiSpotlight
import com.itos.coui.topappbar.CouiTopAppBarButtonDefaults
import kotlin.math.ceil

@Composable
internal fun Modifier.demoLabel(label: Int): Modifier {
    val text = stringResource(label)
    return semantics { contentDescription = text }
}

@Composable
internal fun FoundationEffectsDemo(dark: Boolean, query: String) {
    DemoSection(R.string.backdrop_title, query) { BackdropSample(BackdropMode.Blur, dark) }
    DemoSection(R.string.material_title, query) {
        if (Build.VERSION.SDK_INT >= 33) BackdropSample(BackdropMode.Material, dark)
        else Text(stringResource(R.string.requires_api_33))
    }
    DemoSection(R.string.contour_title, query) {
        if (Build.VERSION.SDK_INT >= 33) ContourSample() else Text(stringResource(R.string.requires_api_33))
    }
    DemoSection(R.string.edge_title, query) {
        if (Build.VERSION.SDK_INT >= 33) BackdropSample(BackdropMode.Edge, dark)
        else Text(stringResource(R.string.requires_api_33))
    }
    DemoSection(R.string.spotlight_title, query) {
        if (Build.VERSION.SDK_INT >= 33) SpotlightSample(dark) else Text(stringResource(R.string.requires_api_33))
    }
    DemoSection(R.string.shape_title, query) { ShapeSample() }
    DemoSection(R.string.motion_title, query) { MotionSample() }
    DemoSection(R.string.feedback_title, query) { FeedbackSample(dark) }
}

private enum class BackdropMode { Blur, Material, Edge }

@Composable
private fun BackdropStage(phase: Float = 0f, content: @Composable BoxScope.(CouiBackdrop) -> Unit) {
    val backdrop = rememberCouiBackdrop()
    val colors = MaterialTheme.colorScheme
    Box(Modifier.fillMaxWidth().height(200.dp).clip(DemoShapes.item)) {
        // 独立采样源只包含图案, 其上的效果面板不能录入该源。
        Canvas(Modifier.fillMaxSize().couiBackdrop(backdrop)) {
            drawRect(colors.surfaceBright)
            val palette = listOf(colors.primaryContainer, colors.secondaryContainer, colors.tertiaryContainer)
            repeat(9) { index ->
                drawRect(palette[index % palette.size], Offset((index - 1 + phase) * size.width / 7f, 0f),
                    Size(size.width / 14f, size.height))
            }
            drawCircle(colors.primary, radius = size.height * 0.22f,
                center = Offset(size.width * (0.2f + phase * 0.5f), size.height * 0.45f))
        }
        content(backdrop)
    }
}

@Composable
private fun BackdropSample(mode: BackdropMode, dark: Boolean) {
    var radius by rememberSaveable { mutableFloatStateOf(16f) }
    var phase by rememberSaveable { mutableFloatStateOf(0f) }
    val density = LocalDensity.current
    val contour = LocalCouiContourGlowEnabled.current
    val material = CouiTopAppBarButtonDefaults.material(dark, capsule = true)
    val optical = if (Build.VERSION.SDK_INT >= 33 && mode == BackdropMode.Material) {
        rememberCouiMaterialEffect(material.firstColor, material.secondColor)
    } else null
    val edgeResult = if (Build.VERSION.SDK_INT >= 33 && mode == BackdropMode.Edge) {
        remember(dark, density.density, contour) {
            runCatching { CouiEdge(if (dark) CouiEdgeStyle.Dark else CouiEdgeStyle.Light,
                with(density) { DemoShapes.effect.radius.toPx() }, DemoShapes.effect.cornerWeight,
                material.firstColor, material.secondColor, material.innerGlowAlpha, contour) }
        }
    } else null
    val failure = edgeResult?.exceptionOrNull()
    if (failure != null) {
        Text(stringResource(R.string.renderer_error, failure.message ?: failure.javaClass.simpleName),
            color = MaterialTheme.colorScheme.error)
        return
    }
    Text(stringResource(R.string.blur_radius_value, radius.toInt()), style = MaterialTheme.typography.labelLarge)
    Slider(modifier = Modifier.demoLabel(R.string.blur_radius), value = radius, onValueChange = { radius = it }, valueRange = 0f..32f)
    Text(stringResource(R.string.backdrop_position), style = MaterialTheme.typography.labelLarge)
    Slider(modifier = Modifier.demoLabel(R.string.backdrop_position), value = phase, onValueChange = { phase = it })
    BackdropStage(phase) { backdrop ->
        Box(Modifier.align(Alignment.Center).padding(24.dp).fillMaxWidth().height(112.dp)
            .drawCouiBackdrop(backdrop, shape = { DemoShapes.effect }, effects = {
                val blurPx = with(density) { radius.dp.toPx() }
                val effect = if (Build.VERSION.SDK_INT >= 33) edgeResult?.getOrNull()?.renderEffect(size, density.density) ?: optical
                    else null
                if (effect == null) blur(blurPx) else {
                    padding = ceil(blurPx)
                    renderEffect = if (blurPx == 0f) effect.asComposeRenderEffect() else RenderEffect.createChainEffect(effect,
                        RenderEffect.createBlurEffect(blurPx, blurPx, Shader.TileMode.CLAMP)).asComposeRenderEffect()
                }
            }), contentAlignment = Alignment.Center) {
            Text(stringResource(R.string.backdrop_foreground), color = MaterialTheme.colorScheme.onSurface)
        }
    }
}

@RequiresApi(33)
@Composable
private fun ContourSample() {
    var floating by rememberSaveable { mutableStateOf(false) }
    var opacity by rememberSaveable { mutableFloatStateOf(0.7f) }
    val rendering = remember(floating) { runCatching { CouiContour(floating) } }
    val renderer = rendering.getOrNull()
    if (renderer == null) {
        val failure = checkNotNull(rendering.exceptionOrNull())
        Text(stringResource(R.string.renderer_error, failure.message ?: failure.javaClass.simpleName), color = MaterialTheme.colorScheme.error)
        return
    }
    val surface = MaterialTheme.colorScheme.primaryContainer.copy(alpha = opacity)
    val contour = LocalCouiContourGlowEnabled.current
    DemoSwitchRow(stringResource(R.string.floating_contour), floating, { floating = it })
    Text(stringResource(R.string.surface_opacity), style = MaterialTheme.typography.labelLarge)
    Slider(modifier = Modifier.demoLabel(R.string.surface_opacity), value = opacity, onValueChange = { opacity = it })
    BackdropStage {
        Canvas(Modifier.align(Alignment.Center).padding(24.dp).fillMaxWidth().height(112.dp)
            .clip(DemoShapes.effect).demoLabel(R.string.contour_title)) {
            drawRect(ShaderBrush(renderer.shader(size, density, DemoShapes.effect.radius.toPx(), DemoShapes.effect.cornerWeight,
                surfaceColor = surface, innerGlowAlpha = 0.2f, lineAlpha = 0.5f, effectAlpha = if (contour) 1f else 0f)))
        }
    }
}

@RequiresApi(33)
@Composable
private fun SpotlightSample(dark: Boolean) {
    var preset by rememberSaveable { mutableStateOf(SpotlightPreset.TranslucentLarge1) }
    var enabled by rememberSaveable { mutableStateOf(true) }
    DemoSwitchRow(stringResource(R.string.enable_spotlight), enabled, { enabled = it })
    Column(Modifier.selectableGroup()) {
        SpotlightPreset.entries.forEach { option ->
            val label = stringResource(when (option) {
                SpotlightPreset.TranslucentLarge1 -> R.string.spotlight_translucent
                SpotlightPreset.OpaqueSmall1 -> R.string.spotlight_small_1
                SpotlightPreset.OpaqueMedium1 -> R.string.spotlight_medium_1
                SpotlightPreset.OpaqueSmall2 -> R.string.spotlight_small_2
                SpotlightPreset.OpaqueMedium2 -> R.string.spotlight_medium_2
                SpotlightPreset.ChipTab -> R.string.spotlight_chip_tab
            })
            DemoChoiceRow(label, preset == option, { preset = option })
        }
    }
    key(preset) {
        var clicks by rememberSaveable { mutableIntStateOf(0) }
        val (input, drawLight) = rememberCouiSpotlight(enabled, preset, dark)
        val source = remember { MutableInteractionSource() }
        Box(Modifier.fillMaxWidth().height(144.dp).clip(DemoShapes.effect)
            .background(MaterialTheme.colorScheme.surfaceContainerHighest)) {
            Box(Modifier.fillMaxSize().graphicsLayer {
                compositingStrategy = if (drawLight != null) CompositingStrategy.Offscreen else CompositingStrategy.Auto
            }.then(input).drawWithContent { drawLight?.invoke(this); drawContent() }
                .clickable(source, indication = null, enabled = enabled, role = Role.Button) { clicks++ },
                contentAlignment = Alignment.Center) {
                Text(stringResource(R.string.spotlight_target, clicks))
            }
        }
    }
    Text(stringResource(R.string.spotlight_hint), style = MaterialTheme.typography.bodySmall)
}

@Composable
private fun ShapeSample() {
    val shapes = listOf(DemoShapes.roundRect, DemoShapes.effect, DemoShapes.couiCapsule)
    val labels = listOf(stringResource(R.string.shape_round), stringResource(R.string.shape_g2), stringResource(R.string.shape_capsule))
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        shapes.forEachIndexed { index, shape ->
            Column(Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Box(Modifier.fillMaxWidth().height(64.dp).background(MaterialTheme.colorScheme.primaryContainer, shape))
                Text(labels[index], style = MaterialTheme.typography.labelMedium)
            }
        }
    }
}

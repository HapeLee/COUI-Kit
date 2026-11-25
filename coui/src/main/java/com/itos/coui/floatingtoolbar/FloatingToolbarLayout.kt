package com.itos.coui.floatingtoolbar

import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.AlignmentLine
import androidx.compose.ui.layout.FirstBaseline
import androidx.compose.ui.layout.HorizontalAlignmentLine
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Measured
import androidx.compose.ui.layout.ParentDataModifier
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.constrainHeight
import androidx.compose.ui.unit.constrainWidth
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

private data class ToolbarSlot(
    val id: Any? = null,
    val visible: Boolean = true,
    val weight: Float = 0f,
    val fill: Boolean = true,
    val align: Alignment.Vertical = Alignment.CenterVertically,
    val line: ((Measured) -> Int)? = null,
)

private fun Modifier.slot(update: ToolbarSlot.() -> ToolbarSlot): Modifier =
    then(object : ParentDataModifier {
        override fun Density.modifyParentData(parentData: Any?): Any = (parentData as? ToolbarSlot ?: ToolbarSlot()).update()
    })

internal fun Modifier.toolbarItem(id: Any, visible: Boolean) = slot { copy(id = id, visible = visible) }

/** 自定义逐项摆放仍接受普通 Row 的权重、垂直对齐和基线。 */
private object ToolbarRowScope : RowScope {
    override fun Modifier.weight(weight: Float, fill: Boolean): Modifier {
        require(weight > 0f) { "操作栏槽位权重必须大于零" }
        return slot { copy(weight = weight.coerceAtMost(Float.MAX_VALUE), fill = fill) }
    }
    override fun Modifier.align(alignment: Alignment.Vertical) = slot { copy(align = alignment, line = null) }
    override fun Modifier.alignBy(alignmentLine: HorizontalAlignmentLine) = alignBy { it[alignmentLine] }
    override fun Modifier.alignByBaseline() = alignBy(FirstBaseline)
    override fun Modifier.alignBy(alignmentLineBlock: (Measured) -> Int) = slot { copy(line = alignmentLineBlock) }
}

@Composable
internal fun ToolbarLayout(
    modifier: Modifier,
    animation: ToolbarAnimation,
    listBatch: ToolbarItems?,
    present: Boolean,
    onGeometry: (Float, Int, Int) -> Unit,
    content: @Composable RowScope.() -> Unit,
) {
    // 直接移走的自定义槽位可能已经离开组合，保留其上次测得的宽度直到本批结算。
    val widths = remember { mutableMapOf<Any, Int>() }
    Layout(modifier = modifier, content = { ToolbarRowScope.content() }) { measurables, constraints ->
        val slots = measurables.map { it.parentData as? ToolbarSlot ?: ToolbarSlot() }
        val ids = measurables.mapIndexed { index, measurable -> slots[index].id ?: measurable }
        val target = ids.filterIndexed { index, _ -> slots[index].visible }
        val itemCount = slots.count { it.visible && it.id != null }
        if (listBatch == null) animation.submit(ToolbarGroup(target, itemCount > 1))
        val batch = listBatch ?: animation.batch
        val defaults = CouiFloatingToolbarDefaults
        val spacing = defaults.itemSpacing.roundToPx()
        val inset = defaults.edgeInset.roundToPx()
        val height = constraints.constrainHeight(defaults.height.roundToPx())
        val childConstraints = Constraints(maxWidth = constraints.maxWidth, maxHeight = height)
        val children = arrayOfNulls<Placeable>(ids.size)
        val hasButtons = slots.any { it.id != null }
        val rowSpacing = if (hasButtons) spacing else 0
        val rowInset = if (hasButtons) inset else 0
        var remaining = (constraints.maxWidth - (itemCount - 1).coerceAtLeast(0) * rowSpacing -
            (if (itemCount > 1) 2 * rowInset else 0)).coerceAtLeast(0)
        var weight = 0f
        for ((i, measurable) in measurables.withIndex()) {
            if (slots[i].weight > 0f && constraints.hasBoundedWidth && ids[i] in target) {
                weight += slots[i].weight
            } else {
                val maxWidth = if (listBatch == null && slots[i].visible && constraints.hasBoundedWidth) remaining
                    else childConstraints.maxWidth
                val child = measurable.measure(childConstraints.copy(maxWidth = maxWidth))
                children[i] = child
                if (slots[i].visible) remaining = (remaining - child.width).coerceAtLeast(0)
            }
        }
        for (i in ids.indices) {
            if (children[i] == null) {
                val share = (remaining * (slots[i].weight / weight)).roundToInt().coerceIn(0, remaining)
                remaining -= share
                weight -= slots[i].weight
                children[i] = measurables[i].measure(childConstraints.copy(
                    minWidth = if (slots[i].fill) share else 0, maxWidth = share,
                ))
            }
            widths[ids[i]] = checkNotNull(children[i]).width
        }
        // 排队中的自定义槽位可能先离开组合，待整条队列结算后再释放其测量值。
        if (batch.from == batch.to) widths.keys.retainAll(ids.toSet())
        val plain = measurables.indices.filter { slots[it].id == null }.mapTo(mutableSetOf()) { ids[it] }
        val buttons = if (plain.isEmpty()) null else batch.order.toSet() - plain
        val frame = batch.frame(widths, rowSpacing, rowInset, animation.fraction(batch), animation.gapFraction(batch),
            layoutDirection == LayoutDirection.Rtl, buttons, 16.dp.roundToPx())
        val width = constraints.constrainWidth(frame.width.roundToInt())
        onGeometry(frame.width, height, batch.target.size)
        val lines = slots.mapIndexed { i, slot -> slot.line?.invoke(checkNotNull(children[i])) }
        val baseline = lines.filterNotNull().filter { it != AlignmentLine.Unspecified }.maxOrNull() ?: 0
        layout(width, height) {
            if (!present) return@layout
            for ((i, id) in ids.withIndex()) {
                val position = frame.items[id] ?: continue
                if (position.scale <= 0f) continue
                val child = checkNotNull(children[i])
                val x = position.x
                val line = lines[i]
                val y = if (line != null && line != AlignmentLine.Unspecified) baseline - line
                    else slots[i].align.align(child.height, height)
                val left = x.roundToInt()
                child.placeWithLayer(left, y) {
                    translationX = x - left
                    scaleX = position.scale
                    scaleY = position.scale
                    alpha = position.scale.coerceAtMost(1f)
                }
            }
        }
    }
}

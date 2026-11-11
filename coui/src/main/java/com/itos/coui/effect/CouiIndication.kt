package com.itos.coui.effect

import android.view.View
import android.view.ViewTreeObserver
import androidx.compose.foundation.IndicationNodeFactory
import androidx.compose.foundation.interaction.FocusInteraction
import androidx.compose.foundation.interaction.HoverInteraction
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.node.CompositionLocalConsumerModifierNode
import androidx.compose.ui.node.DelegatableNode
import androidx.compose.ui.node.DrawModifierNode
import androidx.compose.ui.node.currentValueOf
import androidx.compose.ui.platform.LocalView
import kotlinx.coroutines.launch

/**
 * 普通卡片和列表的默认蒙层，不缩放内容，也不创建点击或触摸 owner。
 * 复用列表最低按压进度；开始与取消由 clickable 的 InteractionSource 决定，
 * 不像显式 list 反馈在原始 DOWN 上提前点亮，避免父卡片响应子按钮的按压。
 * 蒙层绘制在内容上方，调用方负责按真实 Shape 裁剪。顶栏、FAB 等专用反馈不受它替换。
 */
@Immutable
data class CouiIndication(val dark: Boolean) : IndicationNodeFactory {
    override fun create(interactionSource: InteractionSource): DelegatableNode = CouiIndicationNode(interactionSource, dark)
}

private class CouiIndicationNode(private val source: InteractionSource, private val dark: Boolean) :
    Modifier.Node(), DrawModifierNode, CompositionLocalConsumerModifierNode {
    private val presses = mutableSetOf<PressInteraction.Press>()
    private val hovers = mutableSetOf<HoverInteraction.Enter>()
    private val focuses = mutableSetOf<FocusInteraction.Focus>()
    private lateinit var press: CouiListPressMask
    private lateinit var hover: CouiStateProgress
    private lateinit var focus: CouiStateProgress
    private var view: View? = null
    private val windowFocus = ViewTreeObserver.OnWindowFocusChangeListener { focused ->
        if (!focused) {
            presses.clear(); hovers.clear(); focuses.clear()
            press.release()
            hover.setActive(false)
            focus.setActive(false)
        }
    }

    override fun onAttach() {
        press = CouiListPressMask()
        hover = CouiStateProgress()
        focus = CouiStateProgress()
        view = currentValueOf(LocalView).also { it.viewTreeObserver.addOnWindowFocusChangeListener(windowFocus) }
        coroutineScope.launch {
            source.interactions.collect { interaction ->
                when (interaction) {
                    is PressInteraction.Press -> presses.add(interaction)
                    is PressInteraction.Release -> presses.remove(interaction.press)
                    is PressInteraction.Cancel -> presses.remove(interaction.press)
                    is HoverInteraction.Enter -> hovers.add(interaction)
                    is HoverInteraction.Exit -> hovers.remove(interaction.enter)
                    is FocusInteraction.Focus -> focuses.add(interaction)
                    is FocusInteraction.Unfocus -> focuses.remove(interaction.focus)
                }
                press.updateState(true, presses.isNotEmpty())
                hover.setActive(hovers.isNotEmpty())
                focus.setActive(focuses.isNotEmpty())
            }
        }
    }

    override fun ContentDrawScope.draw() {
        drawContent()
        drawRect(maskColor(hover.value, if (dark) 0x26FFFFFF else 0x14000000))
        drawRect(maskColor(focus.value, if (dark) 0x331A8CFF else 0x1F0080FF))
        drawRect(maskColor(press.progress, if (dark) 0x33FFFFFF else 0x1F000000))
    }

    override fun onDetach() {
        view?.viewTreeObserver?.let { if (it.isAlive) it.removeOnWindowFocusChangeListener(windowFocus) }
        view = null
        presses.clear(); hovers.clear(); focuses.clear()
        press.dispose(); hover.dispose(); focus.dispose()
    }
}

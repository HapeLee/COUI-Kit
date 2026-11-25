package com.itos.coui.demo

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.itos.coui.dialog.CouiDialogAction
import com.itos.coui.dialog.Dialog
import com.itos.coui.effect.CouiFloatingRenderer
import com.itos.coui.floatingtoolbar.CouiFloatingToolbarItem
import com.itos.coui.floatingtoolbar.FloatingToolbar
import com.itos.coui.icons.CouiIcons
import com.itos.coui.layout.Divider
import com.itos.coui.layout.Group

/** 操作栏只关心「一排同级操作」；数量、宽度和图标配色都由调用方决定。 */
private class ToolbarAction(val icon: ImageVector, val label: String, val tint: Color? = null)

@Composable
internal fun FloatingToolbarSample(dark: Boolean) {
    if (Build.VERSION.SDK_INT >= 34) FloatingToolbarContent(dark)
    else Text(stringResource(R.string.requires_api_34), color = MaterialTheme.colorScheme.onSurfaceVariant)
}

@RequiresApi(34)
@Composable
private fun FloatingToolbarContent(dark: Boolean) {
    var toolbarVisible by rememberSaveable { mutableStateOf(true) }
    var enabled by rememberSaveable { mutableStateOf(true) }
    var lastAction by rememberSaveable { mutableStateOf("") }
    val actions = listOf(
        // 多色图标按图标库通用约定自带颜色，其余图标使用操作栏的内容色。
        ToolbarAction(CouiIcons.Filled.AI, stringResource(R.string.floating_toolbar_edit), Color.Unspecified),
        ToolbarAction(CouiIcons.Filled.ShieldCheck, stringResource(R.string.floating_toolbar_check)),
        ToolbarAction(CouiIcons.Filled.Microphone, stringResource(R.string.floating_toolbar_voice)),
        ToolbarAction(CouiIcons.Filled.Add, stringResource(R.string.floating_toolbar_add)),
        ToolbarAction(CouiIcons.Filled.Photo, stringResource(R.string.floating_toolbar_photo)),
        ToolbarAction(CouiIcons.Filled.Setting, stringResource(R.string.floating_toolbar_settings)),
        ToolbarAction(CouiIcons.Filled.Favorite, stringResource(R.string.floating_toolbar_favorite)),
        ToolbarAction(CouiIcons.Filled.Share, stringResource(R.string.floating_toolbar_share)),
    )
    var visibleItems by rememberSaveable { mutableStateOf(BooleanArray(actions.size) { it < 4 }) }
    var editingIcons by rememberSaveable { mutableStateOf(false) }
    var draft by rememberSaveable { mutableStateOf(visibleItems) }
    val visibleCount = visibleItems.count { it }
    Group(title = stringResource(R.string.floating_toolbar_actions_count, visibleCount)) {
        ToolbarPreview(actions, visibleItems, dark, enabled, toolbarVisible) { lastAction = actions[it].label }
    }
    Group {
        DemoPreference(
            title = stringResource(R.string.floating_toolbar_visible_icons),
            value = stringResource(R.string.floating_toolbar_selected_count, visibleCount, actions.size),
            onClick = { draft = visibleItems.copyOf(); editingIcons = true },
        )
        Divider()
        DemoPreference(
            title = stringResource(R.string.floating_toolbar_last_action),
            summary = stringResource(R.string.floating_toolbar_hint),
            value = if (lastAction.isEmpty()) stringResource(R.string.floating_toolbar_none) else lastAction,
        )
        Divider()
        DemoSwitchPreference(
            title = stringResource(R.string.floating_toolbar_visible),
            checked = toolbarVisible,
            onCheckedChange = { toolbarVisible = it },
        )
        Divider()
        DemoSwitchPreference(
            title = stringResource(R.string.floating_toolbar_enabled),
            checked = enabled,
            onCheckedChange = { enabled = it },
        )
    }
    Dialog(
        visible = editingIcons,
        title = stringResource(R.string.floating_toolbar_visible_icons),
        onDismissRequest = { editingIcons = false },
        confirmAction = CouiDialogAction(stringResource(R.string.dialog_confirm), {
            if (editingIcons) visibleItems = draft
            editingIcons = false
        }),
        dismissAction = CouiDialogAction(stringResource(R.string.dialog_cancel), { editingIcons = false }),
        material = demoDialogMaterial(),
    ) {
        actions.forEachIndexed { index, action ->
            DemoCheckboxPreference(
                title = action.label,
                checked = draft[index],
                icon = { Icon(action.icon, null, Modifier.size(24.dp),
                    tint = action.tint ?: MaterialTheme.colorScheme.onSurface) },
                onCheckedChange = { checked -> draft = draft.copyOf().also { it[index] = checked } },
            )
        }
    }
}

@RequiresApi(34)
@Composable
private fun ToolbarPreview(
    actions: List<ToolbarAction>,
    visibleFlags: BooleanArray,
    dark: Boolean,
    enabled: Boolean,
    toolbarVisible: Boolean,
    onAction: (Int) -> Unit,
) {
    val rendering = remember { runCatching { CouiFloatingRenderer.standalone() } }
    val renderer = rendering.getOrNull()
    ChromePreview(
        modifier = Modifier.height(120.dp),
        background = {
            ChromePreviewGradient(dark)
            // 栏后保留一段正文，才能看出胶囊的背景模糊：栏内文字被模糊，栏外保持清晰。
            Text(
                stringResource(R.string.floating_toolbar_preview_text),
                Modifier.align(Alignment.Center).fillMaxWidth().padding(horizontal = 12.dp),
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
                maxLines = 2,
            )
        },
    ) { backdrop ->
        if (renderer != null) {
            FloatingToolbar(
                modifier = Modifier.align(Alignment.Center),
                items = actions.mapIndexedNotNull { index, action ->
                    if (visibleFlags[index]) CouiFloatingToolbarItem(index, action.label, action.icon, enabled, action.tint)
                    else null
                },
                onItemClick = onAction,
                visible = toolbarVisible,
                backdrop = backdrop,
                renderer = renderer,
            )
        } else {
            val failure = checkNotNull(rendering.exceptionOrNull())
            Text(stringResource(R.string.renderer_error, failure.message ?: failure.javaClass.simpleName),
                Modifier.align(Alignment.Center), color = MaterialTheme.colorScheme.error)
        }
    }
}

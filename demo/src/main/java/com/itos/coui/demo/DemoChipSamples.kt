package com.itos.coui.demo

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.itos.coui.backdrop.couiBackdrop
import com.itos.coui.backdrop.rememberCouiBackdrop
import com.itos.coui.chip.CouiChipTabButton
import com.itos.coui.chip.CouiChipTabItem
import com.itos.coui.chip.CouiChipTabRow
import com.itos.coui.effect.CouiFloatingRenderer

@Composable
internal fun ChipTabSample(dark: Boolean) {
    if (Build.VERSION.SDK_INT >= 34) ChipTabMaterialSample(dark)
    else Text(stringResource(R.string.requires_api_34), color = MaterialTheme.colorScheme.onSurfaceVariant)
}

@RequiresApi(34)
@Composable
private fun ChipTabMaterialSample(dark: Boolean) {
    var selected by rememberSaveable { mutableIntStateOf(0) }
    var enabled by rememberSaveable { mutableStateOf(true) }
    var longLabel by rememberSaveable { mutableStateOf(false) }
    var showIconButton by rememberSaveable { mutableStateOf(false) }
    var categoriesOpen by rememberSaveable { mutableStateOf(false) }
    val labels = (1..7).map { index ->
        if (longLabel && index == 6) stringResource(R.string.chip_long_label)
        else stringResource(R.string.chip_category_number, index)
    }
    val rendering = remember(labels.size) { runCatching { List(labels.size) { CouiFloatingRenderer.standalone() } } }
    val buttonRendering = remember { lazy(LazyThreadSafetyMode.NONE) { runCatching { CouiFloatingRenderer.standalone() } } }
    val buttonResult = if (showIconButton || buttonRendering.isInitialized()) buttonRendering.value else null
    val renderers = rendering.getOrNull()
    val buttonRenderer = buttonResult?.getOrNull()
    val backdrop = rememberCouiBackdrop()
    val backgroundColor = if (dark) Color.Black else MaterialTheme.colorScheme.surfaceBright
    DemoSwitchRow(stringResource(R.string.chip_enable), enabled, { enabled = it })
    DemoSwitchRow(stringResource(R.string.chip_show_icon_button), showIconButton, { showIconButton = it })
    DemoSwitchRow(stringResource(R.string.chip_use_long_label), longLabel, { longLabel = it })
    if (renderers == null || (showIconButton && buttonRenderer == null)) {
        val failure = checkNotNull(rendering.exceptionOrNull() ?: buttonResult?.exceptionOrNull())
        Text(stringResource(R.string.renderer_error, failure.message ?: failure.javaClass.simpleName),
            color = MaterialTheme.colorScheme.error)
        return
    }
    Box(Modifier.fillMaxWidth()) {
        Box(Modifier.matchParentSize().couiBackdrop(backdrop).background(backgroundColor))
        CouiChipTabRow(
            modifier = Modifier.fillMaxWidth(),
            items = labels.mapIndexed { index, label ->
                CouiChipTabItem(index, label, icon = { Icon(Icons.Default.Tune, null) })
            },
            selectedKey = selected, onSelectedKeyChange = { selected = it as Int }, backdrop = backdrop, dark = dark,
            enabled = enabled, itemSpacing = 8.dp, renderers = renderers,
            leadingActionVisible = showIconButton,
            leadingAction = if (buttonRenderer != null) {
                {
                    CouiChipTabButton(onClick = { categoriesOpen = true }, contentDescription = stringResource(R.string.chip_categories),
                        backdrop = backdrop, dark = dark, enabled = enabled && showIconButton, renderer = buttonRenderer) {
                        Icon(Icons.Default.Tune, null)
                    }
                }
            } else null,
        )
    }
    Text(stringResource(R.string.chip_selected, labels[selected]))
    if (categoriesOpen) {
        AlertDialog(onDismissRequest = { categoriesOpen = false }, title = { Text(stringResource(R.string.chip_categories)) },
            text = {
                Column(Modifier.heightIn(max = 360.dp).verticalScroll(rememberScrollState()).selectableGroup(),
                    verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    labels.forEachIndexed { index, label ->
                        DemoChoiceRow(label, selected == index, { selected = index; categoriesOpen = false })
                    }
                }
            },
            confirmButton = { TextButton(onClick = { categoriesOpen = false }) { Text(stringResource(R.string.chip_close)) } })
    }
}

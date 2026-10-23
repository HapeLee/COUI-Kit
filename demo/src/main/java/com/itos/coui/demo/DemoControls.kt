package com.itos.coui.demo

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp

@Composable
internal fun DemoSwitchRow(label: String, checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    val source = remember { MutableInteractionSource() }
    Row(Modifier.fillMaxWidth().clip(DemoShapes.item).heightIn(min = 56.dp)
        .toggleable(checked, interactionSource = source, indication = ripple(), role = Role.Switch,
            onValueChange = onCheckedChange).padding(horizontal = 8.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically) {
        Text(label, Modifier.weight(1f).padding(end = 16.dp))
        DemoSwitch(checked = checked, onCheckedChange = null, interactionSource = source,
            thumbContent = if (checked) { { Icon(Icons.Default.Check, null, Modifier.size(16.dp)) } } else null)
    }
}

@Composable
internal fun DemoChoiceRow(label: String, selected: Boolean, onSelect: () -> Unit,
    leading: (@Composable () -> Unit)? = null) {
    Row(Modifier.fillMaxWidth().clip(DemoShapes.item).heightIn(min = 56.dp)
        .selectable(selected, role = Role.RadioButton, onClick = onSelect)
        .background(if (selected) MaterialTheme.colorScheme.secondaryContainer else Color.Transparent)
        .padding(horizontal = 8.dp, vertical = 8.dp), verticalAlignment = Alignment.CenterVertically) {
        leading?.invoke()
        Text(label, Modifier.weight(1f).padding(horizontal = 12.dp), color = if (selected)
            MaterialTheme.colorScheme.onSecondaryContainer else MaterialTheme.colorScheme.onSurface)
        RadioButton(selected = selected, onClick = null)
    }
}

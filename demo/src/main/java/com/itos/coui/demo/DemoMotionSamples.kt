package com.itos.coui.demo

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.itos.coui.effect.couiFabFeedback
import com.itos.coui.effect.couiNavigationFeedback
import com.itos.coui.effect.couiToolbarFeedback
import com.itos.coui.motion.CouiPressMotion
import com.itos.coui.motion.couiPressMotion
import com.itos.coui.motion.couiTouchMotion
import com.itos.coui.motion.rememberCouiTouchMotion

@Composable
internal fun MotionSample() {
    var enabled by rememberSaveable { mutableStateOf(true) }
    var clicks by rememberSaveable { mutableIntStateOf(0) }
    val scope = rememberCoroutineScope()
    val density = LocalDensity.current.density
    val motion = rememberCouiTouchMotion(enabled)
    val press = remember(scope, density) { CouiPressMotion(scope, density) }
    val source = remember { MutableInteractionSource() }
    DemoSwitchRow(stringResource(R.string.enable_motion), enabled, { enabled = it })
    Box(Modifier.fillMaxWidth().padding(20.dp), contentAlignment = Alignment.Center) {
        Box(Modifier.fillMaxWidth().heightIn(min = 96.dp).couiPressMotion(press, enabled).couiTouchMotion(motion, enabled)
            .graphicsLayer {
                translationX = motion.translationX.value
                translationY = motion.translationY.value
                scaleX = motion.scaleX.value * press.scale.value
                scaleY = motion.scaleY.value * press.scale.value
            }.clip(DemoShapes.couiCapsule).background(MaterialTheme.colorScheme.primaryContainer)
            .clickable(source, indication = null, enabled = enabled, role = Role.Button) { clicks++ }.padding(16.dp),
            contentAlignment = Alignment.Center) {
            Text(stringResource(R.string.motion_target, clicks), color = MaterialTheme.colorScheme.onPrimaryContainer)
        }
    }
    Text(stringResource(R.string.motion_hint), style = MaterialTheme.typography.bodySmall)
}

@Composable
internal fun FeedbackSample(dark: Boolean) {
    var enabled by rememberSaveable { mutableStateOf(true) }
    var clicks by rememberSaveable { mutableIntStateOf(0) }
    DemoSwitchRow(stringResource(R.string.enable_feedback), enabled, { enabled = it })
    Text(stringResource(R.string.feedback_hint), style = MaterialTheme.typography.bodySmall)
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        listOf(R.string.navigation_feedback, R.string.fab_feedback, R.string.toolbar_feedback).forEachIndexed { index, label ->
            val source = remember { MutableInteractionSource() }
            val feedback = if (!enabled) Modifier else when (index) {
                0 -> Modifier.couiNavigationFeedback(dark, source)
                1 -> Modifier.couiFabFeedback(dark, source)
                else -> Modifier.couiToolbarFeedback(dark, source, enabled)
            }
            Box(Modifier.fillMaxWidth().clip(DemoShapes.capsule).background(MaterialTheme.colorScheme.surfaceContainerHighest)
                .then(feedback).clickable(source, indication = null, enabled = enabled, role = Role.Button) { clicks++ }
                .padding(16.dp), contentAlignment = Alignment.Center) { Text(stringResource(label)) }
        }
    }
    Text(stringResource(R.string.feedback_count, clicks), style = MaterialTheme.typography.labelLarge)
}

package com.rapmix.app.widgets

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun NeonTopBar(
    title: String,
    right1: (@Composable () -> Unit)? = null,
    right2: (@Composable () -> Unit)? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(title, style = MaterialTheme.typography.headlineSmall, color = MaterialTheme.colorScheme.primary)
        Spacer(Modifier.weight(1f))
        right1?.invoke()
        if (right1 != null) Spacer(Modifier.width(8.dp))
        right2?.invoke()
    }
}

@Composable fun IconSettings(onClick: () -> Unit) {
    IconButton(onClick) { Icon(Icons.Default.Settings, contentDescription = "settings") }
}
@Composable fun IconBell(onClick: () -> Unit) {
    IconButton(onClick) { Icon(Icons.Default.Notifications, contentDescription = "bell") }
}
@Composable fun IconSearch(onClick: () -> Unit) {
    IconButton(onClick) { Icon(Icons.Default.Search, contentDescription = "search") }
}

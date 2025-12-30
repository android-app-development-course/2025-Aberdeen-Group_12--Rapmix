package com.example.rapmix.widgets

import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.rapmix.nav.Routes

@Composable
fun BottomBar(
    currentRoute: String,
    onNavigate: (String) -> Unit
) {
    val items = listOf(
        BottomItem("创作", Routes.CREATE, Icons.Filled.Home),
        BottomItem("社区", Routes.COMMUNITY, Icons.Filled.Settings),
        BottomItem("草稿", Routes.DRAFTS, Icons.Filled.Settings),
        BottomItem("我的", Routes.PROFILE, Icons.Filled.Person)
    )

    NavigationBar(
        modifier = Modifier.height(70.dp),
        containerColor = Color(0xFF1A1D2B),
        contentColor = Color.White
    ) {
        items.forEach { item ->
            val selected = currentRoute == item.route
            NavigationBarItem(
                selected = selected,
                onClick = { onNavigate(item.route) },
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.label
                    )
                },
                label = { Text(item.label) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color(0xFFFF2BD6),
                    selectedTextColor = Color(0xFFFF2BD6),
                    unselectedIconColor = Color.White.copy(alpha = 0.55f),
                    unselectedTextColor = Color.White.copy(alpha = 0.55f),
                    indicatorColor = Color(0x33FF2BD6)
                )
            )
        }
    }
}

private data class BottomItem(
    val label: String,
    val route: String,
    val icon: ImageVector
)

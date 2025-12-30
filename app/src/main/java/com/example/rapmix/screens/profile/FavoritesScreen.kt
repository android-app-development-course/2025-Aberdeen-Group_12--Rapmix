package com.example.rapmix.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rapmix.ui.NeonPurple

@Composable
fun FavoritesScreen(onBack: () -> Unit) {
    val tabs = listOf("全部", "作品", "挑战", "用户", "歌单")
    var selectedTab by remember { mutableStateOf("全部") }

    // 选中模式
    val selectedIds = remember { mutableStateListOf<String>() }

    // 假数据（你后面可以换成真实数据）
    val allItems = remember {
        listOf(
            FavItem("1", "Freestyle Battle", "嘻哈制造局", "1.2K"),
            FavItem("2", "24小时创作挑战", "极限制作", "2.4K"),
            FavItem("3", "MC HotDog", "说唱教父", "18.2K"),
            FavItem("4", "地下之声", "独立音乐人", "824"),
            FavItem("5", "嘻哈音乐节2025", "年度盛典", "5.7K"),
            FavItem("6", "都市节奏", "原创MV", "3.1K"),
        )
    }

    val shownItems = remember(selectedTab) {
        when (selectedTab) {
            "作品" -> allItems.filter { it.id in setOf("1", "3", "6") }
            "挑战" -> allItems.filter { it.id in setOf("2", "5") }
            "用户" -> allItems.filter { it.id in setOf("4") }
            "歌单" -> emptyList()
            else -> allItems
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        TopBarFavorites(
            title = "我的收藏",
            onBack = onBack,
            onMenu = { /* TODO: menu */ }
        )

        Spacer(Modifier.height(10.dp))

        // tabs（横向滚动：用 Row + verticalScroll/horizontalScroll 会引入更多 import）
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            tabs.forEach { t ->
                val isSel = t == selectedTab
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(26.dp))
                        .background(if (isSel) NeonPurple else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.35f))
                        .clickable { selectedTab = t }
                        .padding(horizontal = 16.dp, vertical = 10.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        if (t == "全部") "全\n部" else t,
                        fontSize = 12.sp,
                        color = if (isSel) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.75f)
                    )
                }
            }
        }

        Spacer(Modifier.height(12.dp))

        // 网格
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(horizontal = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(bottom = 80.dp) // 给底部选择栏留空间
        ) {
            items(shownItems) { item ->
                FavCard(
                    item = item,
                    selected = selectedIds.contains(item.id),
                    onToggle = {
                        if (selectedIds.contains(item.id)) selectedIds.remove(item.id)
                        else selectedIds.add(item.id)
                    }
                )
            }
        }

        // 底部选择栏（只有选中时显示）
        if (selectedIds.isNotEmpty()) {
            BottomSelectionBar(
                count = selectedIds.size,
                onDelete = { selectedIds.clear() }
            )
        }
    }
}

@Composable
private fun TopBarFavorites(
    title: String,
    onBack: () -> Unit,
    onMenu: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.18f))
            .padding(horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            "←",
            fontSize = 22.sp,
            modifier = Modifier
                .padding(end = 10.dp)
                .clickable { onBack() }
        )

        Text(
            title,
            modifier = Modifier.weight(1f),
            fontSize = 18.sp
        )

        Text(
            "≡",
            fontSize = 20.sp,
            modifier = Modifier
                .padding(start = 10.dp)
                .clickable { onMenu() }
        )
    }
}

@Composable
private fun FavCard(
    item: FavItem,
    selected: Boolean,
    onToggle: () -> Unit
) {
    val shape = RoundedCornerShape(18.dp)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(190.dp)
            .clip(shape)
            .clickable { onToggle() },
        shape = shape,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.25f)
        )
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // 图像区域占位（你后续可替换成 AsyncImage）
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(108.dp)
                    .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.35f)),
                contentAlignment = Alignment.Center
            ) {
                Text("封面", color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f))
            }

            Column(modifier = Modifier.padding(12.dp)) {
                Text(item.title, fontSize = 16.sp)
                Spacer(Modifier.height(4.dp))
                Text(item.subtitle, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f))
                Spacer(Modifier.height(8.dp))
                Text("❤ ${item.likes}", fontSize = 12.sp, color = NeonPurple)
            }
        }
    }

    if (selected) {
        // 选中效果：外边框
        Box(
            modifier = Modifier
                .offset(x = 0.dp, y = (-190).dp)
                .fillMaxWidth()
                .height(190.dp)
                .clip(shape)
                .background(NeonPurple.copy(alpha = 0.08f))
        )
    }
}

@Composable
private fun BottomSelectionBar(
    count: Int,
    onDelete: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.22f))
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("☑", color = NeonPurple, fontSize = 16.sp)
        Spacer(Modifier.width(10.dp))
        Text("已选择 $count 个项目", fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.85f))
        Spacer(Modifier.weight(1f))
        Text(
            "🗑 删除",
            color = MaterialTheme.colorScheme.error,
            modifier = Modifier.clickable { onDelete() }
        )
    }
}

private data class FavItem(
    val id: String,
    val title: String,
    val subtitle: String,
    val likes: String
)

package com.example.rapmix.screens.drafts

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rapmix.ui.NeonPurple

@Composable
fun DraftsScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0B1020))
            .padding(horizontal = 16.dp)
    ) {
        Spacer(Modifier.height(16.dp))

        // 右上角 + 新建（不再显示“我的草稿”标题，避免重复）
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            Box(
                modifier = Modifier
                    .background(NeonPurple, RoundedCornerShape(18.dp))
                    .padding(horizontal = 16.dp, vertical = 10.dp)
            ) {
                Text(
                    text = "+ 新建",
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }

        Spacer(Modifier.height(16.dp))

        // 分类标签
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            FilterChip("全部", selected = true)
            FilterChip("旋律")
            FilterChip("Beat")
            FilterChip("Remix")
            FilterChip("创作中")
        }

        Spacer(Modifier.height(20.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(14.dp),
            contentPadding = PaddingValues(bottom = 90.dp)
        ) {
            items(fakeDrafts) { draft ->
                DraftCard(draft)
            }
        }
    }
}

@Composable
private fun FilterChip(text: String, selected: Boolean = false) {
    Box(
        modifier = Modifier
            .background(
                if (selected) NeonPurple else Color(0xFF1B2236),
                RoundedCornerShape(18.dp)
            )
            .padding(horizontal = 14.dp, vertical = 8.dp)
    ) {
        Text(
            text = text,
            fontSize = 13.sp,
            color = if (selected) Color.White else Color(0xFFB8C0D8)
        )
    }
}

@Composable
private fun DraftCard(draft: DraftItem) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF141C2F))
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 🎵 音乐方块（不用 Icon，彻底避免 unresolved）
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(NeonPurple, RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "♪",
                    color = Color.White,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(Modifier.width(14.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = draft.title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = draft.desc,
                    fontSize = 13.sp,
                    color = Color(0xFF9FA8C7)
                )
                Spacer(Modifier.height(6.dp))
                StatusTag(draft.status)
            }

            Text(
                text = draft.time,
                fontSize = 12.sp,
                color = Color(0xFF9FA8C7)
            )
        }
    }
}

@Composable
private fun StatusTag(text: String) {
    Box(
        modifier = Modifier
            .background(
                if (text == "已发布") NeonPurple else Color(0xFF2A3148),
                RoundedCornerShape(12.dp)
            )
            .padding(horizontal = 10.dp, vertical = 4.dp)
    ) {
        Text(
            text = text,
            fontSize = 11.sp,
            color = Color.White
        )
    }
}

private data class DraftItem(
    val title: String,
    val desc: String,
    val status: String,
    val time: String
)

private val fakeDrafts = listOf(
    DraftItem("夜行列车", "Trap节奏 · 3条音轨", "未完成", "昨天"),
    DraftItem("霓虹都市", "Synthwave · 5条音轨", "已发布", "3天前"),
    DraftItem("雨中漫步", "Lo-fi Chill · 4条音轨", "未完成", "2025-11-05")
)

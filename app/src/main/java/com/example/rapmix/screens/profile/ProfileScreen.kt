package com.example.rapmix.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rapmix.data.ProfileStore
import com.example.rapmix.ui.NeonPurple

@Composable
fun ProfileScreen(
    onEdit: () -> Unit,
    onOpenFavorites: () -> Unit,
    onOpenHistory: () -> Unit,
    onOpenWallet: () -> Unit
) {
    val cardShape = RoundedCornerShape(18.dp)
    val softCardColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.35f)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp)
            .padding(top = 14.dp, bottom = 18.dp)
    ) {
        // 顶部栏：左标题 + 右设置
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "个人空间",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = NeonPurple
            )
            Spacer(modifier = Modifier.weight(1f))
            IconButton(onClick = { /* TODO settings */ }) {
                Icon(
                    imageVector = Icons.Filled.Settings,
                    contentDescription = "settings",
                    tint = NeonPurple
                )
            }
        }

        Spacer(Modifier.height(12.dp))

        // 用户信息卡片
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = cardShape,
            colors = CardDefaults.cardColors(containerColor = softCardColor)
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // 头像占位
                Box(
                    modifier = Modifier
                        .size(58.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.55f)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "IMG",
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.75f)
                    )
                }

                Spacer(Modifier.width(14.dp))

                Column(modifier = Modifier.weight(1f)) {
                    val name = ProfileStore.username.ifBlank { "RapMaster" }
                    val nick = ProfileStore.nickname.trim()
                    val bio = ProfileStore.bio.trim()

                    Text(
                        text = name,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    val sub = buildString {
                        append("Lv.7 混音达人")
                        if (nick.isNotEmpty()) append(" · ").append(nick)
                    }
                    Text(
                        text = sub,
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )

                    if (bio.isNotEmpty()) {
                        Spacer(Modifier.height(6.dp))
                        Text(
                            text = bio,
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.75f),
                            maxLines = 2
                        )
                    }

                    Spacer(Modifier.height(8.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        TagChip(text = ProfileStore.skill.ifBlank { "说唱" })
                        TagChip(text = ProfileStore.musicStyle.ifBlank { "Beat制作" })
                    }
                }

                // 画笔按钮
                IconButton(onClick = onEdit) {
                    Icon(
                        imageVector = Icons.Filled.Edit,
                        contentDescription = "edit",
                        tint = NeonPurple
                    )
                }
            }
        }

        Spacer(Modifier.height(14.dp))

        // 三个统计卡片
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            StatCard(title = "作品", value = "24", modifier = Modifier.weight(1f))
            StatCard(title = "粉丝", value = "3.8K", modifier = Modifier.weight(1f))
            StatCard(title = "收藏", value = "128", modifier = Modifier.weight(1f))
        }

        Spacer(Modifier.height(16.dp))

        // 我的成就
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "我的成就",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = NeonPurple
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "查看全部",
                fontSize = 12.sp,
                color = NeonPurple.copy(alpha = 0.85f),
                modifier = Modifier.clickable { /* TODO */ }
            )
        }

        Spacer(Modifier.height(10.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            AchievementCard(title = "首秀达人", emoji = "🏅", modifier = Modifier.weight(1f))
            AchievementCard(title = "创作先锋", emoji = "📈", modifier = Modifier.weight(1f))
            AchievementCard(title = "社区明星", emoji = "🔒", badge = "18/50", modifier = Modifier.weight(1f))
        }

        Spacer(Modifier.height(14.dp))

        // 菜单列表（可点击）
        MenuRow(title = "我的收藏", emoji = "⭐") { onOpenFavorites() }
        Spacer(Modifier.height(12.dp))
        MenuRow(title = "播放历史", emoji = "🕘") { onOpenHistory() }
        Spacer(Modifier.height(12.dp))
        MenuRow(title = "钱包中心", emoji = "👛") { onOpenWallet() }
    }
}

@Composable
private fun TagChip(text: String) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(999.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.45f))
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        Text(
            text = text,
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
        )
    }
}

@Composable
private fun StatCard(title: String, value: String, modifier: Modifier = Modifier) {
    val cardShape = RoundedCornerShape(16.dp)
    Card(
        modifier = modifier.height(74.dp),
        shape = cardShape,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.30f))
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = value, fontSize = 22.sp, fontWeight = FontWeight.Bold, color = NeonPurple)
            Spacer(Modifier.height(2.dp))
            Text(text = title, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.75f))
        }
    }
}

@Composable
private fun AchievementCard(
    title: String,
    emoji: String,
    badge: String? = null,
    modifier: Modifier = Modifier
) {
    val cardShape = RoundedCornerShape(16.dp)
    Card(
        modifier = modifier.height(92.dp),
        shape = cardShape,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.30f))
    ) {
        Box(Modifier.fillMaxSize()) {
            if (badge != null) {
                Text(
                    text = badge,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.85f),
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.55f))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = emoji, fontSize = 26.sp)
                Spacer(Modifier.height(6.dp))
                Text(
                    text = title,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}

@Composable
private fun MenuRow(
    title: String,
    emoji: String,
    onClick: () -> Unit
) {
    val cardShape = RoundedCornerShape(16.dp)
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(74.dp)
            .clickable { onClick() },
        shape = cardShape,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.30f))
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(42.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.55f)),
                contentAlignment = Alignment.Center
            ) {
                Text(text = emoji, fontSize = 18.sp)
            }

            Spacer(Modifier.width(12.dp))

            Text(
                text = title,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = "›",
                fontSize = 24.sp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )
        }
    }
}

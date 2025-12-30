package com.example.rapmix.screens.community

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items

private val Bg = Color(0xFF0B1220)
private val Card = Color(0xFF1B2434)
private val Card2 = Color(0xFF1A2230)
private val DividerLine = Color(0xFF2B3550)
private val NeonPurple = Color(0xFFB65CFF)
private val NeonPink = Color(0xFFFF4FD8)
private val TextMain = Color(0xFFEAF0FF)
private val TextSub = Color(0xFF9AA8C7)

data class ChallengeItem(val title: String, val participants: String)
data class ProductItem(
    val name: String,
    val price: String,
    val gradientA: Color,
    val gradientB: Color
)
data class PostItem(
    val user: String,
    val time: String,
    val content: String,
    val likes: Int,
    val comments: Int,
    val hasAudio: Boolean = false,
    val audioTitle: String = "城市脉搏",
    val audioHint: String = "点击试听"
)

@Composable
fun CommunityScreen() {
    val challenges = listOf(
        ChallengeItem("电子融合挑战", "1.2K人参与"),
        ChallengeItem("Old School主题", "842人参与")
    )

    val products = listOf(
        ProductItem("RapMix T恤", "159元", Color(0xFFDA1E28), Color(0xFF8B1DFF)),
        ProductItem("限定咖啡杯", "89元", Color(0xFFB86B00), Color(0xFFFF4D4D)),
        ProductItem("棒球帽", "129元", Color(0xFF0E8F4A), Color(0xFF00C2FF)),
        ProductItem("功能背包", "299元", Color(0xFF5B3BFF), Color(0xFF9C27B0)),
        ProductItem("智能手环", "499元", Color(0xFF1E6BFF), Color(0xFF00B0FF)),
        ProductItem("贴纸套装", "39元", Color(0xFFFF5A00), Color(0xFFFF1744))
    )

    val posts = listOf(
        PostItem(
            user = "MC Shadow",
            time = "5分钟前",
            content = "刚刚完成了新作品《城市夜曲》，融合了trap和古筝元素",
            likes = 128,
            comments = 24,
            hasAudio = false
        ),
        PostItem(
            user = "BeatMaster",
            time = "2小时前",
            content = "新挑战#城市节奏#正在进行中，快来参与赢取设备奖励",
            likes = 89,
            comments = 15,
            hasAudio = true
        )
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Bg)
            .padding(horizontal = 18.dp),
        contentPadding = PaddingValues(top = 14.dp, bottom = 18.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // ===== 顶部右侧（铃铛 + 搜索）——用文字徽章代替图标 =====
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(Modifier.weight(1f))
                MiniIconBadge(text = "🔔")
                Spacer(Modifier.width(10.dp))
                MiniIconBadge(text = "🔍")
            }
        }

        // ===== 本周热门挑战 =====
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "本周热门挑战",
                    color = NeonPurple,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.ExtraBold
                )
                Spacer(Modifier.weight(1f))
                Text(
                    text = "查看全部",
                    color = NeonPurple,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }

        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                challenges.forEachIndexed { idx, c ->
                    ChallengeCard(
                        title = c.title,
                        participants = c.participants,
                        coverBrush =
                            if (idx == 0)
                                Brush.linearGradient(listOf(Color(0xFF3A0C59), Color(0xFF111827)))
                            else
                                Brush.linearGradient(listOf(Color(0xFF1D2A5A), Color(0xFF111827)))
                    )
                }
            }
        }

        item { SectionDivider() }

        // ===== RapMix商城 =====
        item {
            Text(
                text = "RapMix商城",
                color = NeonPurple,
                fontSize = 18.sp,
                fontWeight = FontWeight.ExtraBold
            )
        }

        items(products.chunked(2)) { rowItems ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                ProductCard(item = rowItems[0], modifier = Modifier.weight(1f))
                if (rowItems.size > 1) {
                    ProductCard(item = rowItems[1], modifier = Modifier.weight(1f))
                } else {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }

        item { SectionDivider() }

        // ===== 最新动态 =====
        item {
            Text(
                text = "最新动态",
                color = NeonPurple,
                fontSize = 18.sp,
                fontWeight = FontWeight.ExtraBold
            )
        }

        items(posts) { p ->
            PostCard(post = p)
        }
    }
}

@Composable
private fun MiniIconBadge(text: String) {
    Box(
        modifier = Modifier
            .size(34.dp)
            .clip(RoundedCornerShape(999.dp))
            .background(Color(0xFF222C3E))
            .border(1.dp, Color(0x332B3550), RoundedCornerShape(999.dp)),
        contentAlignment = Alignment.Center
    ) {
        Text(text = text, fontSize = 14.sp)
    }
}

@Composable
private fun ChallengeCard(
    title: String,
    participants: String,
    coverBrush: Brush
) {
    Column(
        modifier = Modifier
            .width(178.dp)
            .clip(RoundedCornerShape(18.dp))
            .background(Card)
            .border(1.dp, Color(0x332B3550), RoundedCornerShape(18.dp))
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(108.dp)
                .background(coverBrush)
        ) {
            // 🔥 圆形徽章（代替火焰图标）
            Box(
                modifier = Modifier
                    .padding(10.dp)
                    .size(34.dp)
                    .align(Alignment.TopEnd)
                    .clip(RoundedCornerShape(999.dp))
                    .background(NeonPurple)
                    .border(1.dp, Color(0x66FFFFFF), RoundedCornerShape(999.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text("🔥", fontSize = 14.sp)
            }
        }

        Column(modifier = Modifier.padding(12.dp)) {
            Text(
                text = title,
                color = TextMain,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(Modifier.height(6.dp))
            Text(text = participants, color = TextSub, fontSize = 13.sp)
        }
    }
}

@Composable
private fun ProductCard(item: ProductItem, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(18.dp))
            .background(Card)
            .border(1.dp, Color(0x332B3550), RoundedCornerShape(18.dp))
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .background(Brush.linearGradient(listOf(item.gradientA, item.gradientB))),
            contentAlignment = Alignment.Center
        ) {
            // 🛒 代替购物车图标
            Text("🛍️", fontSize = 26.sp)
        }

        Column(modifier = Modifier.padding(12.dp)) {
            Text(
                text = item.name,
                color = TextMain,
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(Modifier.height(4.dp))
            Text(text = item.price, color = TextSub, fontSize = 13.sp)
            Spacer(Modifier.height(10.dp))

            Button(
                onClick = { },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(38.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = NeonPurple)
            ) {
                Text("购买", color = Color.White, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
private fun PostCard(post: PostItem) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .background(Card2)
            .border(1.dp, Color(0x332B3550), RoundedCornerShape(18.dp))
            .padding(14.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            // 头像占位
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(RoundedCornerShape(999.dp))
                    .background(Color(0xFF3A465E)),
                contentAlignment = Alignment.Center
            ) {
                Text("PHOTO", color = Color(0xFFCFD7EE), fontSize = 10.sp, fontWeight = FontWeight.Bold)
            }

            Spacer(Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(post.user, color = TextMain, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                Text(post.time, color = TextSub, fontSize = 12.sp)
            }
        }

        Spacer(Modifier.height(10.dp))

        Text(
            text = post.content,
            color = TextMain,
            fontSize = 15.sp,
            fontWeight = FontWeight.SemiBold,
            lineHeight = 20.sp
        )

        if (post.hasAudio) {
            Spacer(Modifier.height(12.dp))
            AudioMiniPlayer(title = post.audioTitle, hint = post.audioHint)
        }

        Spacer(Modifier.height(12.dp))

        // 底部点赞/评论 —— 用文字代替图标
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("♡", color = TextSub, fontSize = 18.sp)
            Spacer(Modifier.width(6.dp))
            Text("${post.likes}", color = TextSub, fontSize = 13.sp)

            Spacer(Modifier.width(18.dp))

            Text("💬", fontSize = 16.sp)
            Spacer(Modifier.width(6.dp))
            Text("${post.comments}", color = TextSub, fontSize = 13.sp)
        }
    }
}

@Composable
private fun AudioMiniPlayer(title: String, hint: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(Color(0xFF202B3C))
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(54.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Brush.linearGradient(listOf(NeonPurple, NeonPink))),
            contentAlignment = Alignment.Center
        ) {
            Text("▶", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Black)
        }

        Spacer(Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(title, color = TextMain, fontSize = 14.sp, fontWeight = FontWeight.Bold)
            Text(hint, color = TextSub, fontSize = 12.sp)
        }

        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(999.dp))
                .background(NeonPurple),
            contentAlignment = Alignment.Center
        ) {
            Text("▶", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Black)
        }
    }
}

@Composable
private fun SectionDivider() {
    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(DividerLine)
    )
}

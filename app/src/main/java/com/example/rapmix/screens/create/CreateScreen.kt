package com.example.rapmix.screens.create

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rapmix.ui.NeonPurple

@Composable
fun CreateScreen(
    onOpenAddTrack: () -> Unit = {}
) {
    val bg = MaterialTheme.colorScheme.background
    val cardBg = MaterialTheme.colorScheme.surface.copy(alpha = 0.55f)

    val vScroll = rememberScrollState()

    Surface(modifier = Modifier.fillMaxSize(), color = bg) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(vScroll)
                .padding(bottom = 90.dp) // 给底部导航栏留空间
        ) {
            TopHeader()

            Spacer(Modifier.height(12.dp))

            // 多轨混音
            SectionTitle("多轨混音")
            AddTrackHeader(onOpenAddTrack)

            Spacer(Modifier.height(10.dp))
            TrackCard(title = "主旋律", progress = 0.78f, cardBg = cardBg)
            Spacer(Modifier.height(12.dp))
            TrackCard(title = "Beat节奏", progress = 0.42f, cardBg = cardBg)

            Spacer(Modifier.height(18.dp))

            // 脉冲净化
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = cardBg)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "脉冲净化",
                            color = NeonPurple,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.ExtraBold
                        )
                        Spacer(Modifier.weight(1f))
                        Text(
                            text = "消除背景噪音",
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.65f),
                            fontSize = 12.sp
                        )
                    }

                    Spacer(Modifier.height(12.dp))

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(70.dp)
                            .clip(RoundedCornerShape(18.dp))
                            .background(Color(0xFF6C2DB5).copy(alpha = 0.75f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "⚡  启动净化",
                            color = Color(0xFFB8B8B8),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(Modifier.height(20.dp))

            // 智能风格库（横向滚动）
            SectionTitle("智能风格库")
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp)
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                StyleTile("Trap Beat", cardBg)
                StyleTile("Old School", cardBg)
                StyleTile("Urban", cardBg)
                StyleTile("Synth", cardBg)
            }

            Spacer(Modifier.height(20.dp))

            // 创作大挑战
            SectionTitle("创作大挑战")
            ChallengeCard(cardBg)

            Spacer(Modifier.height(22.dp))

            // 优秀作品展示
            SectionTitle("优秀作品展示")
            WorksRow(title = "城市韵律", author = "DJ Echo", liked = true, cardBg = cardBg)
            Spacer(Modifier.height(12.dp))
            WorksRow(title = "电子古韵", author = "BeatMaster", liked = false, cardBg = cardBg)

            Spacer(Modifier.height(28.dp))
        }
    }
}

@Composable
private fun TopHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 14.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "创作中心",
            color = NeonPurple,
            fontSize = 22.sp,
            fontWeight = FontWeight.ExtraBold
        )
        Spacer(Modifier.weight(1f))
        IconButton(onClick = { }) {
            Icon(imageVector = Icons.Filled.ExitToApp, contentDescription = "exit", tint = NeonPurple)
        }
        IconButton(onClick = { }) {
            Icon(imageVector = Icons.Filled.Settings, contentDescription = "settings", tint = NeonPurple)
        }
    }

    Divider(color = NeonPurple.copy(alpha = 0.25f), thickness = 1.dp)
}

@Composable
private fun SectionTitle(text: String) {
    Text(
        text = text,
        modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp),
        color = NeonPurple,
        fontSize = 18.sp,
        fontWeight = FontWeight.ExtraBold
    )
}

@Composable
private fun AddTrackHeader(onOpenAddTrack: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(Modifier.weight(1f))
        Button(
            onClick = onOpenAddTrack,
            shape = RoundedCornerShape(22.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF2BD6)),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 10.dp)
        ) {
            Text("添加音轨", color = Color.Black, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
private fun TrackCard(title: String, progress: Float, cardBg: Color) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 14.dp),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = cardBg)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(42.dp)
                    .clip(RoundedCornerShape(21.dp))
                    .background(NeonPurple.copy(alpha = 0.9f)),
                contentAlignment = Alignment.Center
            ) {
                // 用 Person 当占位图标（你项目里肯定有）
                Icon(
                    imageVector = Icons.Filled.Person,
                    contentDescription = "track",
                    tint = Color.Black
                )
            }

            Spacer(Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(title, color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(8.dp))
                LinearProgressIndicator(
                    progress = { progress },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                        .clip(RoundedCornerShape(6.dp)),
                    color = Color(0xFFFF2BD6),
                    trackColor = Color.White.copy(alpha = 0.15f)
                )
            }

            Spacer(Modifier.width(10.dp))
            Text("🔊", fontSize = 18.sp)
        }
    }
}

@Composable
private fun StyleTile(name: String, cardBg: Color) {
    Box(
        modifier = Modifier
            .width(120.dp)
            .height(86.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(cardBg)
            .padding(14.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(Icons.Filled.Person, contentDescription = name, tint = Color(0xFFFF2BD6))
            Spacer(Modifier.height(10.dp))
            Text(name, color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
        }
    }
}

@Composable
private fun ChallengeCard(cardBg: Color) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 14.dp),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = cardBg)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(modifier = Modifier.weight(1f)) {
                    Text("本周挑战主题", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    Spacer(Modifier.height(4.dp))
                    Text("未来都市融合", color = NeonPurple, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                    Spacer(Modifier.height(6.dp))
                    Text(
                        "参与规则：融合传统乐器与电子元素，时长3-5分钟",
                        color = Color.White.copy(alpha = 0.6f),
                        fontSize = 12.sp
                    )
                }
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(18.dp))
                        .background(NeonPurple)
                        .padding(horizontal = 14.dp, vertical = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text("进行中", color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                }
            }

            Spacer(Modifier.height(14.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                MiniStatCard(label = "参与人数", value = "456", modifier = Modifier.weight(1f))
                MiniStatCard(label = "剩余时间", value = "3天", modifier = Modifier.weight(1f))
            }
        }
    }
}

@Composable
private fun MiniStatCard(label: String, value: String, modifier: Modifier) {
    val tileBg = MaterialTheme.colorScheme.surface.copy(alpha = 0.45f)

    Card(
        modifier = modifier.height(64.dp),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = tileBg)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(label, color = Color.White.copy(alpha = 0.55f), fontSize = 12.sp)
            Spacer(Modifier.height(6.dp))
            Text(value, color = NeonPurple, fontSize = 16.sp, fontWeight = FontWeight.ExtraBold)
        }
    }
}

@Composable
private fun WorksRow(title: String, author: String, liked: Boolean, cardBg: Color) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 14.dp),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = cardBg)
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(46.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(NeonPurple.copy(alpha = 0.9f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.Person,
                    contentDescription = "work",
                    tint = Color.Black
                )
            }

            Spacer(Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(title, color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                Text("by $author", color = Color.White.copy(alpha = 0.6f), fontSize = 13.sp)
            }

            Text(if (liked) "❤️" else "🤍", fontSize = 18.sp)
        }
    }
}

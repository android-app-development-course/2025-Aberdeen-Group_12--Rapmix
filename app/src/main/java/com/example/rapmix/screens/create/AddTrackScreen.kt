package com.example.rapmix.screens.create

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rapmix.ui.NeonPurple

@Composable
fun AddTrackScreen(
    onBack: () -> Unit,
    onSave: () -> Unit,
    onCancel: () -> Unit,
    onAddTrack: (audioUriString: String) -> Unit,

    // ✅ 新增：点录音按钮时跳去录音页
    onOpenRecord: () -> Unit,

    // ✅ 从录音页/外部回传的已选音频（可为空）
    pickedAudioUriString: String? = null,
    onClearPickedAudio: () -> Unit = {}
) {
    // 当前选择的音频（导入/录音都写进这里）
    var selectedAudio by remember { mutableStateOf<String?>(null) }

    // 如果从录音页返回了 uri，就写入
    LaunchedEffect(pickedAudioUriString) {
        if (!pickedAudioUriString.isNullOrBlank()) {
            selectedAudio = pickedAudioUriString
            onClearPickedAudio()
        }
    }

    // 导入文件：系统选择器
    val openAudioLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.OpenDocument()
    ) { uri ->
        if (uri != null) {
            selectedAudio = uri.toString()
        }
    }

    val bg = MaterialTheme.colorScheme.background
    val cardBg = MaterialTheme.colorScheme.surface.copy(alpha = 0.55f)

    Surface(modifier = Modifier.fillMaxSize(), color = bg) {
        Column(modifier = Modifier.fillMaxSize()) {

            // 顶部栏
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBack) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = "back", tint = Color.White)
                }
                Text(
                    text = "添加音轨",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.ExtraBold
                )
                Spacer(Modifier.weight(1f))
                TextButton(onClick = onSave) {
                    Text("保存", color = Color(0xFFFF2BD6), fontWeight = FontWeight.Bold)
                }
            }

            Spacer(Modifier.height(10.dp))

            // 音源库 3 个按钮
            Text(
                text = "音源库",
                modifier = Modifier.padding(horizontal = 14.dp),
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.ExtraBold
            )

            Spacer(Modifier.height(12.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                SourceTile(
                    title = "采样库",
                    icon = Icons.Filled.Person,
                    cardBg = cardBg,
                    onClick = { /* TODO: 你之后再做采样库 */ }
                )
                SourceTile(
                    title = "录音",
                    icon = Icons.Filled.Settings,
                    cardBg = cardBg,
                    onClick = { onOpenRecord() } // ✅ 跳转录音页
                )
                SourceTile(
                    title = "导入文件",
                    icon = Icons.Filled.Person,
                    cardBg = cardBg,
                    onClick = {
                        // ✅ 打开系统文件选择器（语音备忘录的音频也可选）
                        openAudioLauncher.launch(arrayOf("audio/*"))
                    }
                )
            }

            Spacer(Modifier.height(18.dp))

            // 已选择音频展示
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = cardBg)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "当前已选择音频",
                        color = NeonPurple,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                    Spacer(Modifier.height(10.dp))
                    Text(
                        text = selectedAudio ?: "未选择（请录音或导入文件）",
                        color = Color.White.copy(alpha = 0.75f),
                        fontSize = 12.sp
                    )
                }
            }

            Spacer(Modifier.height(18.dp))

            // 底部按钮（取消 / 添加音轨）
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedButton(
                    onClick = onCancel,
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(18.dp)
                ) {
                    Text("取消")
                }
                Button(
                    onClick = {
                        val audio = selectedAudio
                        if (!audio.isNullOrBlank()) onAddTrack(audio)
                    },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(18.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF2BD6))
                ) {
                    Text("添加音轨", color = Color.Black, fontWeight = FontWeight.ExtraBold)
                }
            }

            Spacer(Modifier.height(10.dp))
        }
    }
}

@Composable
private fun RowScope.SourceTile(
    title: String,
    icon: ImageVector,
    cardBg: Color,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .weight(1f) // ✅ 现在在 RowScope 中，合法
            .height(88.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(cardBg)
            .clickable { onClick() }
            .padding(14.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(icon, contentDescription = title, tint = Color(0xFFFF2BD6))
            Spacer(Modifier.height(10.dp))
            Text(title, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 14.sp)
        }
    }
}

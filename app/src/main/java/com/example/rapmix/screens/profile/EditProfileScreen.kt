package com.example.rapmix.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rapmix.data.ProfileStore
import com.example.rapmix.ui.NeonPurple

@Composable
fun EditProfileScreen(
    onBack: () -> Unit,
    onSaveDone: () -> Unit
) {
    var username by rememberSaveable { mutableStateOf(ProfileStore.username) }
    var nickname by rememberSaveable { mutableStateOf(ProfileStore.nickname) }
    var bio by rememberSaveable { mutableStateOf(ProfileStore.bio) }
    var instagram by rememberSaveable { mutableStateOf(ProfileStore.instagram) }
    var twitter by rememberSaveable { mutableStateOf(ProfileStore.twitter) }

    val musicStyles = listOf("嘻哈", "R&B", "电子", "摇滚")
    val skills = listOf("作词", "作曲", "Beat制作")

    // ✅ 关键：不要写 mutableStateOf(value = ...)
    var selectedStyle by rememberSaveable {
        mutableStateOf(ProfileStore.musicStyle.ifBlank { "电子" })
    }
    var selectedSkill by rememberSaveable {
        mutableStateOf(ProfileStore.skill.ifBlank { "作词" })
    }

    val cardShape = RoundedCornerShape(18.dp)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        TopBarSimple(
            title = "编辑个人信息",
            onBack = onBack,
            onSave = {
                ProfileStore.username = username.trim()
                ProfileStore.nickname = nickname.trim()
                ProfileStore.bio = bio.trim()
                ProfileStore.instagram = instagram.trim()
                ProfileStore.twitter = twitter.trim()
                ProfileStore.musicStyle = selectedStyle
                ProfileStore.skill = selectedSkill
                onSaveDone()
            }
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 14.dp)
        ) {
            // 头像区（灰底 + PHOTO + 📷）
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                Box(
                    modifier = Modifier
                        .size(96.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.55f)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "PHOTO",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.75f)
                    )
                }

                Box(
                    modifier = Modifier
                        .offset(x = 34.dp, y = 34.dp)
                        .size(34.dp)
                        .clip(CircleShape)
                        .background(NeonPurple),
                    contentAlignment = Alignment.Center
                ) {
                    Text("📷", fontSize = 16.sp, color = MaterialTheme.colorScheme.onPrimary)
                }
            }

            Spacer(Modifier.height(14.dp))

            // 基本信息
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = cardShape,
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.35f)
                )
            ) {
                Column(Modifier.padding(16.dp)) {
                    Text("基本信息", color = NeonPurple, fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
                    Spacer(Modifier.height(10.dp))

                    LabeledField("用户名") {
                        OutlinedTextField(
                            value = username,
                            onValueChange = { username = it },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true
                        )
                    }

                    Spacer(Modifier.height(10.dp))

                    LabeledField("昵称") {
                        OutlinedTextField(
                            value = nickname,
                            onValueChange = { nickname = it },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true
                        )
                    }

                    Spacer(Modifier.height(10.dp))

                    LabeledField("个人简介") {
                        OutlinedTextField(
                            value = bio,
                            onValueChange = { bio = it },
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(min = 110.dp),
                            minLines = 3
                        )
                    }
                }
            }

            Spacer(Modifier.height(14.dp))

            // 偏好设置
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = cardShape,
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.35f)
                )
            ) {
                Column(Modifier.padding(16.dp)) {
                    Text("偏好设置", color = NeonPurple, fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
                    Spacer(Modifier.height(10.dp))

                    Text("音乐风格", color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f))
                    Spacer(Modifier.height(8.dp))
                    ChipRowSimple(
                        options = musicStyles,
                        selected = selectedStyle,
                        onSelect = { selectedStyle = it }
                    )

                    Spacer(Modifier.height(12.dp))

                    Text("擅长领域", color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f))
                    Spacer(Modifier.height(8.dp))
                    ChipRowSimple(
                        options = skills,
                        selected = selectedSkill,
                        onSelect = { selectedSkill = it }
                    )
                }
            }

            Spacer(Modifier.height(14.dp))

            // 社交账号
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = cardShape,
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.35f)
                )
            ) {
                Column(Modifier.padding(16.dp)) {
                    Text("社交账号", color = NeonPurple, fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
                    Spacer(Modifier.height(10.dp))

                    LabeledField("Instagram") {
                        OutlinedTextField(
                            value = instagram,
                            onValueChange = { instagram = it },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true,
                            placeholder = { Text("@rap_master") }
                        )
                    }

                    Spacer(Modifier.height(10.dp))

                    LabeledField("Twitter") {
                        OutlinedTextField(
                            value = twitter,
                            onValueChange = { twitter = it },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true,
                            placeholder = { Text("@rap_master") }
                        )
                    }
                }
            }

            Spacer(Modifier.height(22.dp))
        }
    }
}

@Composable
private fun TopBarSimple(
    title: String,
    onBack: () -> Unit,
    onSave: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.25f))
            .padding(horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            "←",
            fontSize = 22.sp,
            modifier = Modifier
                .padding(end = 10.dp)
                .clickable { onBack() },
            color = MaterialTheme.colorScheme.onSurface
        )

        Text(
            title,
            modifier = Modifier.weight(1f),
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurface
        )

        Text(
            "保存",
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .clickable { onSave() }
                .padding(horizontal = 10.dp, vertical = 6.dp),
            color = NeonPurple,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
private fun LabeledField(label: String, content: @Composable () -> Unit) {
    Text(label, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.65f))
    Spacer(Modifier.height(6.dp))
    content()
}

@Composable
private fun ChipRowSimple(
    options: List<String>,
    selected: String,
    onSelect: (String) -> Unit
) {
    Row(
        modifier = Modifier.horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        options.forEach { text ->
            val isSelected = text == selected
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(18.dp))
                    .background(
                        if (isSelected) NeonPurple
                        else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.45f)
                    )
                    .clickable { onSelect(text) }
                    .padding(horizontal = 14.dp, vertical = 8.dp)
            ) {
                Text(
                    text,
                    color = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

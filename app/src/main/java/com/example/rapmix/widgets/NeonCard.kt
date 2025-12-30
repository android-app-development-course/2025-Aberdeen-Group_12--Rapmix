package com.example.rapmix.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.rapmix.ui.CardBg
import com.example.rapmix.ui.NeonPurple

/**
 * 音乐软件常见“柔和漂浮卡片”：
 * - 背后加一层轻微发光/阴影（blur）
 * - 表层再放真正内容
 */
@Composable
fun NeonCard(
    modifier: Modifier = Modifier,
    corner: Dp = 18.dp,
    contentPadding: PaddingValues = PaddingValues(16.dp),
    content: @Composable () -> Unit
) {
    val shape = RoundedCornerShape(corner)

    Box(modifier = modifier) {
        // Glow layer
        Box(
            modifier = Modifier
                .matchParentSize()
                .clip(shape)
                .background(NeonPurple.copy(alpha = 0.10f))
                .blur(18.dp)
        )
        // Card layer
        Box(
            modifier = Modifier
                .matchParentSize()
                .clip(shape)
                .background(CardBg)
        )
        // Content
        Box(modifier = Modifier.padding(contentPadding)) {
            content()
        }
    }
}

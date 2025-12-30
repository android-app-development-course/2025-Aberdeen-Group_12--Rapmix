package com.example.rapmix.widgets

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import com.example.rapmix.ui.NeonPink
import com.example.rapmix.ui.NeonPurple
import com.example.rapmix.ui.TextTitle



@Composable
fun NeonTitle(
    text: String,
    modifier: Modifier = Modifier,
    style: TextStyle = MaterialTheme.typography.headlineLarge,
    glow1: Color = NeonPurple,
    glow2: Color = NeonPink,
    glowAlpha: Float = 0.55f
) {
    Text(
        text = text,
        style = style.copy(color = TextTitle),
        modifier = modifier.drawBehind {
            // 简单稳定的 glow：画几层轻微偏移的透明文字轮廓
            // 注意：这里不直接绘文字（复杂），而是用 shadow 方案更重；
            // 该版本通过 drawBehind 画发光“光斑”更轻量。
            val center = Offset(size.width * 0.08f, size.height * 0.62f)
            drawCircle(color = glow1.copy(alpha = glowAlpha), radius = size.minDimension * 0.38f, center = center)
            drawCircle(color = glow2.copy(alpha = glowAlpha * 0.75f), radius = size.minDimension * 0.28f, center = center)
        }
    )
}

package com.example.rapmix

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.rapmix.nav.AppNav
import com.example.rapmix.ui.RapMixTheme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            RapMixTheme {
                var showSplash by remember { mutableStateOf(true) }

                if (showSplash) {
                    RapMixSplash(
                        onFinish = { showSplash = false }
                    )
                } else {
                    AppNav()
                }
            }
        }
    }
}

@Composable
private fun RapMixSplash(
    onFinish: () -> Unit
) {
    val alpha = remember { Animatable(0f) }
    val scale = remember { Animatable(0.85f) }

    LaunchedEffect(Unit) {
        // Fade + Scale in
        alpha.animateTo(
            targetValue = 1f,
            animationSpec = tween(600)
        )
        scale.animateTo(
            targetValue = 1.1f,
            animationSpec = tween(
                durationMillis = 800,
                easing = FastOutSlowInEasing
            )
        )


        delay(2000)


        alpha.animateTo(
            targetValue = 0f,
            animationSpec = tween(400)
        )

        onFinish()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            // ✅ 背景色 = 你 App 的深色背景（可调）
            .background(Color(0xFF0E0F1A)),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.rapmix_logo),
            contentDescription = "RapMix Logo",
            modifier = Modifier
                // ✅ 这里控制图标大小（你要更大就改这里）
                .size(440.dp)
                .scale(scale.value)
                .alpha(alpha.value)
        )
    }
}

package com.example.rapmix.screens.create

import android.Manifest
import android.content.Context
import android.media.MediaRecorder
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun RecordTrackScreen(
    onBack: () -> Unit,
    onRecorded: (uriString: String) -> Unit
) {
    val context = LocalContext.current

    var isRecording by remember { mutableStateOf(false) }
    var recorder by remember { mutableStateOf<MediaRecorder?>(null) }
    var lastSavedFile by remember { mutableStateOf<File?>(null) }
    var errorText by remember { mutableStateOf<String?>(null) }
    var hasPermission by remember { mutableStateOf(false) }

    // 申请录音权限
    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        hasPermission = granted
        if (!granted) errorText = "你拒绝了录音权限，无法开始录音"
    }

    fun startNow() {
        runCatching {
            val (r, file) = startRecording(context)
            recorder = r
            lastSavedFile = file
            isRecording = true
            errorText = null
        }.onFailure {
            errorText = "录音启动失败：${it.message}"
            recorder?.runCatching { release() }
            recorder = null
            lastSavedFile = null
            isRecording = false
        }
    }

    /**
     * stopAndMaybeReturn = true  -> 正常结束录音，回传 uri
     * stopAndMaybeReturn = false -> 仅停止并释放（比如按返回键），不回传
     */
    fun stopNow(stopAndMaybeReturn: Boolean) {
        val r = recorder
        val file = lastSavedFile

        runCatching {
            try {
                r?.stop()
            } catch (_: Exception) {
                // 录太短可能 stop() 抛异常，忽略
            } finally {
                r?.runCatching { release() }
            }

            recorder = null
            isRecording = false

            if (stopAndMaybeReturn) {
                if (file != null && file.exists() && file.length() > 0) {
                    errorText = null
                    // ✅ 标准 file uri
                    val uriString = file.toURI().toString()
                    onRecorded(uriString)
                } else {
                    errorText = "录音文件无效，请重试"
                }
            }
        }.onFailure {
            recorder?.runCatching { release() }
            recorder = null
            isRecording = false
            errorText = "停止录音失败：${it.message}"
        }
    }

    // 首次进入：检查权限（这里直接请求一次最省事）
    LaunchedEffect(Unit) {
        permissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
    }

    // 页面销毁时释放录音器
    DisposableEffect(Unit) {
        onDispose {
            recorder?.runCatching { release() }
            recorder = null
        }
    }

    val bg = MaterialTheme.colorScheme.background
    val cardBg = MaterialTheme.colorScheme.surface.copy(alpha = 0.55f)

    Surface(modifier = Modifier.fillMaxSize(), color = bg) {
        Column(modifier = Modifier.fillMaxSize()) {

            // TopBar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = {
                    // ✅ 录音中按返回：只停止，不回传，避免误添加
                    if (isRecording) stopNow(stopAndMaybeReturn = false)
                    onBack()
                }) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = "back", tint = Color.White)
                }

                Text(
                    text = "录音",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.ExtraBold
                )

                Spacer(Modifier.weight(1f))

                Text(
                    text = if (isRecording) "录音中..." else "准备就绪",
                    color = if (isRecording) Color(0xFFFF2BD6) else Color.White.copy(alpha = 0.7f),
                    fontSize = 13.sp
                )
            }

            Spacer(Modifier.height(10.dp))

            // 主卡片
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = cardBg)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(18.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "点击按钮开始录音，再次点击结束录音并生成文件",
                        color = Color.White.copy(alpha = 0.75f),
                        fontSize = 13.sp
                    )

                    Spacer(Modifier.height(18.dp))

                    val btnBg = if (isRecording) Color(0xFFFF2BD6) else Color(0xFF7C3AED)
                    val btnText = if (isRecording) "停止录音" else "开始录音"

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(70.dp)
                            .clip(RoundedCornerShape(18.dp))
                            .background(btnBg),
                        contentAlignment = Alignment.Center
                    ) {
                        TextButton(
                            onClick = {
                                if (!isRecording) {
                                    if (!hasPermission) {
                                        permissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
                                    } else {
                                        startNow()
                                    }
                                } else {
                                    // ✅ 正常结束：回传 uri
                                    stopNow(stopAndMaybeReturn = true)
                                }
                            }
                        ) {
                            Text(
                                text = btnText,
                                color = Color.Black,
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 18.sp
                            )
                        }
                    }

                    Spacer(Modifier.height(14.dp))

                    lastSavedFile?.let {
                        Text(
                            text = "录音文件：${it.name}",
                            color = Color.White.copy(alpha = 0.75f),
                            fontSize = 12.sp
                        )
                    }

                    if (errorText != null) {
                        Spacer(Modifier.height(10.dp))
                        Text(
                            text = errorText!!,
                            color = Color(0xFFFF5A5A),
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }
    }
}

/** 开始录音：保存到 app 的 cacheDir 下（m4a） */
private fun startRecording(context: Context): Pair<MediaRecorder, File> {
    val ts = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())
    val outFile = File(context.cacheDir, "rec_$ts.m4a")
    if (outFile.exists()) outFile.delete()

    val recorder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        MediaRecorder(context)
    } else {
        @Suppress("DEPRECATION")
        MediaRecorder()
    }

    recorder.apply {
        setAudioSource(MediaRecorder.AudioSource.MIC)
        setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
        setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
        setAudioSamplingRate(44100)
        setAudioEncodingBitRate(128_000)
        setOutputFile(outFile.absolutePath)
        prepare()
        start()
    }

    return recorder to outFile
}

package com.example.rapmix.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rapmix.ui.NeonPurple
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun WalletScreen(
    onBack: () -> Unit
) {
    // 你后面可接真实数据/Room，这里先做 UI & 假数据
    var balance by remember { mutableStateOf(128.50) }

    val rechargeOptions = listOf(30, 60, 120, 180, 300, 500)
    var selectedAmount by remember { mutableStateOf(60) }

    val txs = remember {
        listOf(
            TxItem("作品购买", "11月17日 09:30", -15.00),
            TxItem("充值", "11月16日 15:22", +100.00),
            TxItem("会员订阅", "11月15日 19:45", -30.00)
        )
    }

    val bg = MaterialTheme.colorScheme.background
    val cardShape = RoundedCornerShape(22.dp)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(bg)
    ) {
        TopBarWallet(
            title = "钱包中心",
            rightText = "交易记录",
            onBack = onBack,
            onRight = { /* TODO: 交易记录页面 later */ }
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp)
                .padding(top = 14.dp, bottom = 18.dp)
        ) {
            // ✅ 余额大卡片
            BalanceCard(
                balance = balance,
                onRecharge = {
                    // 假逻辑：点击立即充值，把选择金额加到余额（你后面换成支付流程）
                    balance += selectedAmount.toDouble()
                }
            )

            Spacer(Modifier.height(18.dp))

            // ✅ 充值金额标题
            Text(
                text = "充值金额",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(Modifier.height(12.dp))

            // ✅ 2 x 3 充值金额卡片
            RechargeGrid(
                options = rechargeOptions,
                selected = selectedAmount,
                onSelect = { selectedAmount = it }
            )

            Spacer(Modifier.height(22.dp))

            // ✅ 最近交易
            Text(
                text = "最近交易",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(Modifier.height(12.dp))

            txs.forEachIndexed { idx, tx ->
                TxRow(tx)
                if (idx != txs.lastIndex) Spacer(Modifier.height(18.dp))
            }

            Spacer(Modifier.height(40.dp))
        }
    }
}

@Composable
private fun TopBarWallet(
    title: String,
    rightText: String,
    onBack: () -> Unit,
    onRight: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.18f))
            .padding(horizontal = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onBack) {
            Icon(
                Icons.Filled.ArrowBack,
                contentDescription = "back",
                tint = MaterialTheme.colorScheme.onSurface
            )
        }

        Text(
            text = title,
            modifier = Modifier.weight(1f),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )

        Text(
            text = rightText,
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .clickable { onRight() }
                .padding(horizontal = 10.dp, vertical = 6.dp),
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.75f)
        )
    }
}

@Composable
private fun BalanceCard(
    balance: Double,
    onRecharge: () -> Unit
) {
    val shape = RoundedCornerShape(26.dp)

    // 外层紫色 glow
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(210.dp)
            .clip(shape)
            .background(NeonPurple.copy(alpha = 0.18f))
            .padding(2.dp)
    ) {
        Card(
            modifier = Modifier.fillMaxSize(),
            shape = shape,
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.22f)
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(18.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "当前余额",
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.75f)
                )

                Spacer(Modifier.height(12.dp))

                Row(verticalAlignment = Alignment.Bottom) {
                    Text(
                        text = String.format(Locale.US, "%.2f", balance),
                        fontSize = 46.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(
                        text = "RMB",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.85f),
                        modifier = Modifier.padding(bottom = 10.dp)
                    )
                }

                Spacer(Modifier.height(16.dp))

                RechargeButton(text = "立即充值", onClick = onRecharge)
            }
        }
    }
}

@Composable
private fun RechargeButton(
    text: String,
    onClick: () -> Unit
) {
    // 用 Surface 做“渐变按钮效果”的替代：紫色 + 轻微 glow（不依赖渐变 API，最稳）
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(18.dp))
            .background(NeonPurple.copy(alpha = 0.20f))
            .padding(2.dp)
    ) {
        Surface(
            modifier = Modifier
                .clip(RoundedCornerShape(18.dp))
                .clickable { onClick() },
            color = NeonPurple
        ) {
            Box(
                modifier = Modifier
                    .padding(horizontal = 36.dp, vertical = 12.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = text,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }
}

@Composable
private fun RechargeGrid(
    options: List<Int>,
    selected: Int,
    onSelect: (Int) -> Unit
) {
    val shape = RoundedCornerShape(18.dp)

    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        for (row in 0 until 2) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                for (col in 0 until 3) {
                    val idx = row * 3 + col
                    val amount = options[idx]
                    val isSelected = amount == selected

                    // 紫色 glow 边框
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .aspectRatio(1.08f)
                            .clip(shape)
                            .background(
                                if (isSelected) NeonPurple.copy(alpha = 0.20f)
                                else NeonPurple.copy(alpha = 0.10f)
                            )
                            .padding(2.dp)
                    ) {
                        Card(
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(shape)
                                .clickable { onSelect(amount) },
                            shape = shape,
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.18f)
                            )
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(12.dp),
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = "${amount}元",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = if (isSelected) NeonPurple
                                    else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.55f)
                                )
                                Spacer(Modifier.height(8.dp))
                                Text(
                                    text = "¥${String.format(Locale.US, "%.2f", amount.toDouble())}",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

private data class TxItem(
    val title: String,
    val timeText: String,
    val amount: Double
)

@Composable
private fun TxRow(tx: TxItem) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = tx.title,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(Modifier.height(6.dp))
            Text(
                text = tx.timeText,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.55f)
            )
        }

        val isPositive = tx.amount > 0
        Text(
            text = (if (isPositive) "+¥" else "-¥") + String.format(Locale.US, "%.2f", kotlin.math.abs(tx.amount)),
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = if (isPositive) androidx.compose.ui.graphics.Color(0xFF35D07F)
            else androidx.compose.ui.graphics.Color(0xFFFF5A5A)
        )
    }
}

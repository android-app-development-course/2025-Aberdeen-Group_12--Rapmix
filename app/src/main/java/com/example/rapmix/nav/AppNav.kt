package com.example.rapmix.nav

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.rapmix.screens.community.CommunityScreen
import com.example.rapmix.screens.create.AddTrackScreen
import com.example.rapmix.screens.create.CreateScreen
import com.example.rapmix.screens.create.RecordTrackScreen
import com.example.rapmix.screens.drafts.DraftsScreen
import com.example.rapmix.screens.profile.ProfileScreen

// ✅ 这四个你项目里已经有（从你截图文件列表看到了）
import com.example.rapmix.screens.profile.EditProfileScreen
import com.example.rapmix.screens.profile.FavoritesScreen
import com.example.rapmix.screens.profile.HistoryScreen
import com.example.rapmix.screens.profile.WalletScreen

import com.example.rapmix.widgets.BottomBar

@Composable
fun AppNav() {
    val navController = rememberNavController()

    // 录音页回传的 uri（RecordTrackScreen -> AddTrackScreen）
    var recordedUriString by remember { mutableStateOf<String?>(null) }

    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            // ✅ 只在主 tab 页面显示底栏
            val showBottomBar = currentRoute in setOf(
                Routes.CREATE,
                Routes.COMMUNITY,
                Routes.DRAFTS,
                Routes.PROFILE
            )
            if (showBottomBar) {
                BottomBar(
                    currentRoute = currentRoute ?: Routes.CREATE,
                    onNavigate = { route ->
                        navController.navigate(route) {
                            launchSingleTop = true
                            restoreState = true
                            popUpTo(Routes.CREATE) { saveState = true }
                        }
                    }
                )
            }
        }
    ) { innerPadding ->

        NavHost(
            navController = navController,
            startDestination = Routes.CREATE,
            modifier = Modifier.padding(innerPadding)
        ) {

            // ---------- 主 Tab ----------
            composable(Routes.CREATE) {
                CreateScreen(
                    onOpenAddTrack = { navController.navigate(Routes.ADD_TRACK) }
                )
            }

            composable(Routes.COMMUNITY) { CommunityScreen() }

            composable(Routes.DRAFTS) { DraftsScreen() }

            // ✅ 关键修复：把 ProfileScreen 的点击回调接到导航
            composable(Routes.PROFILE) {
                ProfileScreen(
                    onEdit = { navController.navigate(Routes.EDIT_PROFILE) },
                    onOpenFavorites = { navController.navigate(Routes.FAVORITES) },
                    onOpenHistory = { navController.navigate(Routes.HISTORY) },
                    onOpenWallet = { navController.navigate(Routes.WALLET) }
                )
            }

            // ---------- 添加音轨 ----------
            composable(Routes.ADD_TRACK) {
                AddTrackScreen(
                    onBack = { navController.popBackStack() },
                    onSave = { /* TODO */ },
                    onCancel = { navController.popBackStack() },
                    onAddTrack = { navController.popBackStack() },
                    onOpenRecord = { navController.navigate(Routes.RECORD_TRACK) },
                    pickedAudioUriString = recordedUriString,
                    onClearPickedAudio = { recordedUriString = null }
                )
            }

            // ---------- 录音 ----------
            composable(Routes.RECORD_TRACK) {
                RecordTrackScreen(
                    onBack = { navController.popBackStack() },
                    onRecorded = { uriString ->
                        recordedUriString = uriString
                        navController.popBackStack()
                    }
                )
            }

            // ---------- Profile 子页面 ----------
            composable(Routes.EDIT_PROFILE) {
                EditProfileScreen(
                    onBack = { navController.popBackStack() },
                    onSaveDone = { navController.popBackStack() }
                )

            }
            composable(Routes.FAVORITES) {
                FavoritesScreen(onBack = { navController.popBackStack() })
            }
            composable(Routes.HISTORY) {
                HistoryScreen(onBack = { navController.popBackStack() })
            }
            composable(Routes.WALLET) {
                WalletScreen(onBack = { navController.popBackStack() })
            }
        }
    }
}

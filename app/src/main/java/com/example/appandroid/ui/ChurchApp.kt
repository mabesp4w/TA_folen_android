package com.example.appandroid.ui

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.appandroid.ui.navigation.Routes
import com.example.appandroid.ui.navigation.TopLevelDestination
import com.example.appandroid.ui.screen.DashboardScreen
import com.example.appandroid.ui.screen.FeatureScreen
import com.example.appandroid.ui.screen.GaleriDetailScreen
import com.example.appandroid.ui.screen.GaleriScreen
import com.example.appandroid.ui.screen.JadwalScreen
import com.example.appandroid.ui.screen.JemaatScreen
import com.example.appandroid.ui.screen.KehadiranScreen
import com.example.appandroid.ui.screen.KeuanganScreen
import com.example.appandroid.ui.screen.LainnyaScreen
import com.example.appandroid.ui.screen.MajelisScreen
import com.example.appandroid.ui.screen.PendetaScreen
import com.example.appandroid.ui.screen.PengaturanScreen
import com.example.appandroid.ui.screen.PengurusScreen
import com.example.appandroid.ui.screen.RayonScreen
import com.example.appandroid.ui.screen.RenunganScreen
import com.example.appandroid.ui.screen.TentangScreen
import com.example.appandroid.ui.screen.UnsurScreen
import com.example.appandroid.ui.screen.WartaScreen

private const val FEATURE_ROUTE = "fitur"

@Composable
fun ChurchApp() {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = backStackEntry?.destination
    val topLevel = TopLevelDestination.entries

    // Sebagian menu punya layar khusus; sisanya memakai layar placeholder generik.
    val openMenu: (String) -> Unit = { id ->
        when (id) {
            Routes.RAYON -> navController.navigate(Routes.RAYON)
            Routes.UNSUR -> navController.navigate(Routes.UNSUR)
            Routes.JEMAAT -> navController.navigate(Routes.JEMAAT)
            Routes.PENDETA -> navController.navigate(Routes.PENDETA)
            Routes.MAJELIS -> navController.navigate(Routes.MAJELIS)
            Routes.PENGURUS -> navController.navigate(Routes.PENGURUS)
            Routes.KEHADIRAN -> navController.navigate(Routes.KEHADIRAN)
            Routes.KEUANGAN -> navController.navigate(Routes.KEUANGAN)
            Routes.PENGATURAN -> navController.navigate(Routes.PENGATURAN)
            Routes.TENTANG -> navController.navigate(Routes.TENTANG)
            Routes.GALERI -> navController.navigate(Routes.GALERI)
            else -> navController.navigateToFeature(id)
        }
    }

    // Bottom bar hanya tampil pada rute utama, tidak pada halaman detail fitur.
    val showBottomBar = currentDestination?.hierarchy?.any { dest ->
        topLevel.any { it.route == dest.route }
    } ?: true

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        bottomBar = {
            if (showBottomBar) {
                NavigationBar(
                    containerColor = MaterialTheme.colorScheme.surface,
                    tonalElevation = 3.dp,
                ) {
                    topLevel.forEach { destination ->
                        val selected = currentDestination?.hierarchy?.any {
                            it.route == destination.route
                        } == true
                        NavigationBarItem(
                            selected = selected,
                            onClick = {
                                navController.navigate(destination.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            icon = {
                                Icon(
                                    imageVector = if (selected) destination.selectedIcon
                                    else destination.unselectedIcon,
                                    contentDescription = destination.label,
                                )
                            },
                            label = { Text(destination.label) },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = MaterialTheme.colorScheme.onPrimaryContainer,
                                selectedTextColor = MaterialTheme.colorScheme.primary,
                                indicatorColor = MaterialTheme.colorScheme.primaryContainer,
                                unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            ),
                        )
                    }
                }
            }
        },
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Routes.BERANDA,
            modifier = Modifier.fillMaxSize(),
            enterTransition = { fadeIn(tween(220)) },
            exitTransition = { fadeOut(tween(180)) },
        ) {
            composable(Routes.BERANDA) {
                DashboardScreen(
                    onMenuClick = openMenu,
                    onLihatJadwal = { navController.navigate(Routes.JADWAL) },
                    onLihatRenungan = { navController.navigate(Routes.RENUNGAN) },
                    onLihatWarta = { navController.navigate(Routes.WARTA) },
                    onLihatGaleri = { navController.navigate(Routes.GALERI) },
                    contentPadding = innerPadding,
                )
            }
            composable(Routes.JADWAL) { JadwalScreen(contentPadding = innerPadding) }
            composable(Routes.RENUNGAN) { RenunganScreen(contentPadding = innerPadding) }
            composable(Routes.WARTA) { WartaScreen(contentPadding = innerPadding) }
            composable(Routes.LAINNYA) {
                LainnyaScreen(
                    onItemClick = openMenu,
                    contentPadding = innerPadding,
                )
            }
            composable(Routes.RAYON) {
                RayonScreen(
                    onBack = { navController.popBackStack() },
                    contentPadding = innerPadding,
                )
            }
            composable(Routes.UNSUR) {
                UnsurScreen(
                    onBack = { navController.popBackStack() },
                    contentPadding = innerPadding,
                )
            }
            composable(Routes.JEMAAT) {
                JemaatScreen(
                    onBack = { navController.popBackStack() },
                    contentPadding = innerPadding,
                )
            }
            composable(Routes.PENDETA) {
                PendetaScreen(
                    onBack = { navController.popBackStack() },
                    contentPadding = innerPadding,
                )
            }
            composable(Routes.MAJELIS) {
                MajelisScreen(
                    onBack = { navController.popBackStack() },
                    contentPadding = innerPadding,
                )
            }
            composable(Routes.PENGURUS) {
                PengurusScreen(
                    onBack = { navController.popBackStack() },
                    contentPadding = innerPadding,
                )
            }
            composable(Routes.KEHADIRAN) {
                KehadiranScreen(
                    onBack = { navController.popBackStack() },
                    contentPadding = innerPadding,
                )
            }
            composable(Routes.KEUANGAN) {
                KeuanganScreen(
                    onBack = { navController.popBackStack() },
                    contentPadding = innerPadding,
                )
            }
            composable(Routes.PENGATURAN) {
                PengaturanScreen(
                    onBack = { navController.popBackStack() },
                    onOpenTentang = { navController.navigate(Routes.TENTANG) },
                    contentPadding = innerPadding,
                )
            }
            composable(Routes.TENTANG) {
                TentangScreen(
                    onBack = { navController.popBackStack() },
                    contentPadding = innerPadding,
                )
            }
            composable(Routes.GALERI) {
                GaleriScreen(
                    onBack = { navController.popBackStack() },
                    onOpenAlbum = { id -> navController.navigate(Routes.galeriDetail(id)) },
                    contentPadding = innerPadding,
                )
            }
            composable(Routes.GALERI_DETAIL) {
                GaleriDetailScreen(
                    onBack = { navController.popBackStack() },
                    contentPadding = innerPadding,
                )
            }
            composable("$FEATURE_ROUTE/{title}") { entry ->
                val title = entry.arguments?.getString("title").orEmpty()
                FeatureScreen(title = title.replaceFirstChar { it.uppercase() }, contentPadding = innerPadding)
            }
        }
    }
}

private fun androidx.navigation.NavController.navigateToFeature(id: String) {
    navigate("$FEATURE_ROUTE/$id")
}

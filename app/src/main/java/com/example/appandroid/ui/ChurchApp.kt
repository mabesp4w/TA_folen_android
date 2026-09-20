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
import com.example.appandroid.ui.screen.JadwalScreen
import com.example.appandroid.ui.screen.LainnyaScreen
import com.example.appandroid.ui.screen.RenunganScreen
import com.example.appandroid.ui.screen.WartaScreen

private const val FEATURE_ROUTE = "fitur"

@Composable
fun ChurchApp() {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = backStackEntry?.destination
    val topLevel = TopLevelDestination.entries

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
                    onMenuClick = { navController.navigateToFeature(it) },
                    onLihatJadwal = { navController.navigate(Routes.JADWAL) },
                    onLihatRenungan = { navController.navigate(Routes.RENUNGAN) },
                    onLihatWarta = { navController.navigate(Routes.WARTA) },
                    onLihatGaleri = { navController.navigateToFeature("galeri") },
                    contentPadding = innerPadding,
                )
            }
            composable(Routes.JADWAL) { JadwalScreen(contentPadding = innerPadding) }
            composable(Routes.RENUNGAN) { RenunganScreen(contentPadding = innerPadding) }
            composable(Routes.WARTA) { WartaScreen(contentPadding = innerPadding) }
            composable(Routes.LAINNYA) {
                LainnyaScreen(
                    onItemClick = { navController.navigateToFeature(it) },
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

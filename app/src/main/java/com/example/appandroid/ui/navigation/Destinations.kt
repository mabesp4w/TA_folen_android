package com.example.appandroid.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.ui.graphics.vector.ImageVector

// Rute-rute utama aplikasi.
object Routes {
    const val BERANDA = "beranda"
    const val JADWAL = "jadwal"
    const val RENUNGAN = "renungan"
    const val WARTA = "warta"
    const val LAINNYA = "lainnya"
}

// Item yang tampil pada bottom navigation bar.
enum class TopLevelDestination(
    val route: String,
    val label: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
) {
    BERANDA(Routes.BERANDA, "Beranda", Icons.Filled.Home, Icons.Outlined.Home),
    JADWAL(Routes.JADWAL, "Jadwal", Icons.Filled.DateRange, Icons.Outlined.DateRange),
    RENUNGAN(Routes.RENUNGAN, "Renungan", Icons.Filled.Favorite, Icons.Outlined.FavoriteBorder),
    WARTA(Routes.WARTA, "Warta", Icons.Filled.Notifications, Icons.Outlined.Notifications),
    LAINNYA(Routes.LAINNYA, "Lainnya", Icons.Filled.Menu, Icons.Outlined.Menu),
}

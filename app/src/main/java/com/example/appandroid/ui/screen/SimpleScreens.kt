package com.example.appandroid.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.appandroid.ui.component.Eyebrow
import com.example.appandroid.ui.component.EmptyState
import com.example.appandroid.ui.component.IconBadge
import com.example.appandroid.ui.component.PaperCard
import com.example.appandroid.ui.icon.AppIcons
import com.example.appandroid.ui.sample.MenuItem
import com.example.appandroid.ui.theme.Brand

// Header bergradien yang dipakai ulang pada layar selain dashboard.
@Composable
fun ScreenHeader(title: String, subtitle: String) {
    val statusTop = WindowInsets.statusBars.asPaddingValues().calculateTopPadding()
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Brush.linearGradient(Brand.HeaderGradient))
            .padding(top = statusTop)
            .padding(start = 20.dp, end = 20.dp, top = 22.dp, bottom = 22.dp),
    ) {
        Column {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineSmall,
                color = Color.White,
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White.copy(alpha = 0.82f),
            )
        }
    }
}

// Layar sederhana dengan header + empty state (untuk tab yang datanya menyusul).
@Composable
private fun ComingSoonScreen(
    title: String,
    subtitle: String,
    icon: ImageVector,
    emptyTitle: String,
    emptyMessage: String,
    contentPadding: PaddingValues,
) {
    Column(Modifier.fillMaxSize()) {
        ScreenHeader(title, subtitle)
        Box(
            modifier = Modifier.fillMaxSize().padding(bottom = contentPadding.calculateBottomPadding()),
            contentAlignment = Alignment.Center,
        ) {
            EmptyState(
                icon = icon,
                title = emptyTitle,
                message = emptyMessage,
                modifier = Modifier.padding(horizontal = 32.dp),
            )
        }
    }
}

@Composable
fun JadwalScreen(contentPadding: PaddingValues) = ComingSoonScreen(
    title = "Jadwal Ibadah",
    subtitle = "Jadwal ibadah minggu ini dan mendatang",
    icon = Icons.Filled.DateRange,
    emptyTitle = "Jadwal segera hadir",
    emptyMessage = "Daftar lengkap jadwal ibadah akan tampil di sini setelah terhubung ke data.",
    contentPadding = contentPadding,
)

@Composable
fun RenunganScreen(contentPadding: PaddingValues) = ComingSoonScreen(
    title = "Renungan Harian",
    subtitle = "Santapan rohani setiap hari",
    icon = Icons.Filled.Favorite,
    emptyTitle = "Renungan segera hadir",
    emptyMessage = "Arsip renungan harian akan tampil di sini setelah terhubung ke data.",
    contentPadding = contentPadding,
)

@Composable
fun WartaScreen(contentPadding: PaddingValues) = ComingSoonScreen(
    title = "Warta Jemaat",
    subtitle = "Pengumuman dan informasi gereja",
    icon = Icons.Filled.Notifications,
    emptyTitle = "Warta segera hadir",
    emptyMessage = "Warta dan pengumuman jemaat akan tampil di sini setelah terhubung ke data.",
    contentPadding = contentPadding,
)

// Menu lengkap pada tab "Lainnya".
private data class LainnyaGroup(val title: String, val items: List<MenuItem>)

private val lainnyaGroups = listOf(
    LainnyaGroup(
        "Data Jemaat",
        listOf(
            MenuItem("jemaat", "Jemaat", Icons.Filled.Person),
            MenuItem("rayon", "Rayon", Icons.Filled.LocationOn),
            MenuItem("unsur", "Unsur", Icons.AutoMirrored.Filled.List),
        ),
    ),
    LainnyaGroup(
        "Pelayanan",
        listOf(
            MenuItem("pendeta", "Pendeta", Icons.Filled.AccountCircle),
            MenuItem("majelis", "Majelis", Icons.Filled.Face),
            MenuItem("pengurus", "Badan Pengurus", Icons.Filled.AccountBox),
        ),
    ),
    LainnyaGroup(
        "Media & Statistik",
        listOf(
            MenuItem("galeri", "Galeri", AppIcons.Gallery),
            MenuItem("kehadiran", "Kehadiran", Icons.Filled.CheckCircle),
            MenuItem("keuangan", "Keuangan", Icons.Filled.ShoppingCart),
        ),
    ),
    LainnyaGroup(
        "Aplikasi",
        listOf(
            MenuItem("pengaturan", "Pengaturan", Icons.Filled.Settings),
            MenuItem("tentang", "Tentang", Icons.Filled.Info),
        ),
    ),
)

@Composable
fun LainnyaScreen(
    onItemClick: (String) -> Unit,
    contentPadding: PaddingValues,
) {
    Column(Modifier.fillMaxSize()) {
        ScreenHeader("Lainnya", "Semua data dan pengaturan aplikasi")
        LazyColumn(
            contentPadding = PaddingValues(
                start = 16.dp, end = 16.dp, top = 20.dp,
                bottom = contentPadding.calculateBottomPadding() + 24.dp,
            ),
            verticalArrangement = Arrangement.spacedBy(22.dp),
        ) {
            items(lainnyaGroups) { group ->
                Column {
                    Eyebrow(group.title, color = Brand.Pacific)
                    Spacer(Modifier.height(10.dp))
                    PaperCard(Modifier.fillMaxWidth()) {
                        Column {
                            group.items.forEachIndexed { index, item ->
                                LainnyaRow(item = item, onClick = { onItemClick(item.id) })
                                if (index != group.items.lastIndex) {
                                    Box(
                                        Modifier
                                            .fillMaxWidth()
                                            .padding(start = 68.dp)
                                            .height(1.dp)
                                            .background(MaterialTheme.colorScheme.outlineVariant),
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun LainnyaRow(item: MenuItem, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 14.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        IconBadge(
            icon = item.icon,
            contentDescription = null,
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
            size = 40,
        )
        Spacer(Modifier.width(14.dp))
        Text(
            text = item.label,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.weight(1f),
        )
        Icon(
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.size(20.dp),
        )
    }
}

// Layar detail generik saat sebuah menu ditekan (placeholder sebelum data tersambung).
@Composable
fun FeatureScreen(title: String, contentPadding: PaddingValues) {
    Column(Modifier.fillMaxSize()) {
        ScreenHeader(title, "Halaman ini sedang disiapkan")
        Box(
            modifier = Modifier.fillMaxSize().padding(bottom = contentPadding.calculateBottomPadding()),
            contentAlignment = Alignment.Center,
        ) {
            EmptyState(
                icon = Icons.AutoMirrored.Filled.List,
                title = "Segera hadir",
                message = "Data \"$title\" akan tampil di sini setelah terhubung ke API.",
                modifier = Modifier.padding(horizontal = 32.dp),
            )
        }
    }
}

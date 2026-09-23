package com.example.appandroid.ui.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.example.appandroid.data.local.entity.AlbumEntity
import com.example.appandroid.data.local.entity.JadwalEntity
import com.example.appandroid.data.local.entity.PengumumanEntity
import com.example.appandroid.data.local.entity.RenunganEntity
import com.example.appandroid.ui.component.Eyebrow
import com.example.appandroid.ui.component.IconBadge
import com.example.appandroid.ui.component.PaperCard
import com.example.appandroid.ui.component.SectionHeader
import com.example.appandroid.ui.component.StatusChip
import com.example.appandroid.ui.icon.AppIcons
import com.example.appandroid.ui.sample.MenuItem
import com.example.appandroid.ui.theme.Brand
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

private val quickMenu = listOf(
    MenuItem("jemaat", "Jemaat", Icons.Filled.Person),
    MenuItem("rayon", "Rayon", Icons.Filled.LocationOn),
    MenuItem("galeri", "Galeri", AppIcons.Gallery),
    MenuItem("pendeta", "Pendeta", Icons.Filled.AccountCircle),
    MenuItem("majelis", "Majelis", Icons.Filled.Face),
    MenuItem("pengurus", "Pengurus", Icons.Filled.AccountBox),
    MenuItem("kehadiran", "Kehadiran", Icons.Filled.CheckCircle),
    MenuItem("keuangan", "Keuangan", Icons.Filled.ShoppingCart),
)

private val idLocale = Locale.of("id")
private val dateDisplayFormatter = DateTimeFormatter.ofPattern("d MMM", idLocale)
private val dayFormatter = DateTimeFormatter.ofPattern("EEEE", idLocale)

private fun formatTanggal(tgl: String?): Pair<String, String> {
    if (tgl == null) return "--" to ""
    return try {
        val date = LocalDate.parse(tgl)
        date.format(dateDisplayFormatter).split(" ", limit = 2).let {
            (it.getOrElse(0) { "--" }) to (it.getOrElse(1) { "" })
        }
    } catch (_: Exception) {
        "--" to ""
    }
}

private fun formatHari(tgl: String?): String {
    if (tgl == null) return ""
    return try {
        LocalDate.parse(tgl).format(dayFormatter)
    } catch (_: Exception) {
        ""
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    onMenuClick: (String) -> Unit,
    onLihatJadwal: () -> Unit,
    onLihatRenungan: () -> Unit,
    onLihatWarta: () -> Unit,
    onLihatGaleri: () -> Unit,
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier,
    viewModel: DashboardViewModel = hiltViewModel(),
) {
    val renungan by viewModel.renungan.collectAsState()
    val jadwal by viewModel.jadwal.collectAsState()
    val warta by viewModel.warta.collectAsState()
    val albums by viewModel.albums.collectAsState()
    val rayonCount by viewModel.rayonCount.collectAsState()
    val unsurCount by viewModel.unsurCount.collectAsState()
    val isRefreshing by viewModel.isRefreshing.collectAsState()
    val error by viewModel.error.collectAsState()

    Box(modifier = modifier.fillMaxSize()) {
        PullToRefreshBox(
            isRefreshing = isRefreshing,
            onRefresh = { viewModel.refresh() },
            modifier = Modifier.fillMaxSize(),
        ) {
            LazyColumn(
                contentPadding = PaddingValues(
                    bottom = contentPadding.calculateBottomPadding() + 24.dp,
                ),
                verticalArrangement = Arrangement.spacedBy(24.dp),
            ) {
                item {
                    GreetingHeader(
                        rayonCount = rayonCount,
                        unsurCount = unsurCount,
                        albumCount = albums.size,
                    )
                }

                if (renungan != null) {
                    item {
                        FeaturedRenungan(
                            renungan = renungan!!,
                            onClick = onLihatRenungan,
                            modifier = Modifier.padding(horizontal = 16.dp),
                        )
                    }
                }

                item {
                    QuickAccessGrid(
                        items = quickMenu,
                        onItemClick = onMenuClick,
                        modifier = Modifier.padding(horizontal = 16.dp),
                    )
                }

                if (jadwal.isNotEmpty()) {
                    item {
                        Column {
                            SectionHeader(
                                title = "Jadwal Mendatang",
                                actionLabel = "Semua",
                                onAction = onLihatJadwal,
                                modifier = Modifier.padding(horizontal = 16.dp),
                            )
                            Spacer(Modifier.height(12.dp))
                            Column(
                                verticalArrangement = Arrangement.spacedBy(10.dp),
                                modifier = Modifier.padding(horizontal = 16.dp),
                            ) {
                                jadwal.forEach { JadwalCard(it) }
                            }
                        }
                    }
                }

                if (albums.isNotEmpty()) {
                    item {
                        Column {
                            SectionHeader(
                                title = "Galeri Kegiatan",
                                actionLabel = "Semua",
                                onAction = onLihatGaleri,
                                modifier = Modifier.padding(horizontal = 16.dp),
                            )
                            Spacer(Modifier.height(12.dp))
                            LazyRow(
                                contentPadding = PaddingValues(horizontal = 16.dp),
                                horizontalArrangement = Arrangement.spacedBy(12.dp),
                            ) {
                                items(albums, key = { it.id }) { AlbumCard(it) }
                            }
                        }
                    }
                }

                if (warta.isNotEmpty()) {
                    item {
                        Column {
                            SectionHeader(
                                title = "Warta Terbaru",
                                actionLabel = "Semua",
                                onAction = onLihatWarta,
                                modifier = Modifier.padding(horizontal = 16.dp),
                            )
                            Spacer(Modifier.height(12.dp))
                            Column(
                                verticalArrangement = Arrangement.spacedBy(10.dp),
                                modifier = Modifier.padding(horizontal = 16.dp),
                            ) {
                                warta.forEach { WartaCard(it) }
                            }
                        }
                    }
                }
            }
        }

        AnimatedVisibility(
            visible = isRefreshing,
            enter = fadeIn(),
            exit = fadeOut(),
            modifier = Modifier.align(Alignment.TopCenter),
        ) {
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
        }

        if (error != null) {
            Snackbar(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(16.dp)
                    .padding(bottom = contentPadding.calculateBottomPadding()),
                action = {},
                dismissAction = {
                    Text(
                        "Tutup",
                        modifier = Modifier.clickable { viewModel.clearError() },
                        color = MaterialTheme.colorScheme.inversePrimary,
                    )
                },
            ) {
                Text("Gagal memuat data. Periksa koneksi.")
            }
        }
    }
}

@Composable
private fun GreetingHeader(
    rayonCount: Int,
    unsurCount: Int,
    albumCount: Int,
) {
    val statusTop = WindowInsets.statusBars.asPaddingValues().calculateTopPadding()
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Brush.linearGradient(Brand.HeaderGradient))
            .padding(top = statusTop)
            .padding(start = 20.dp, end = 20.dp, top = 20.dp, bottom = 26.dp),
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column(Modifier.weight(1f)) {
                    Eyebrow("GKI Syaloom Pasifik Indah", color = Brand.GoldSoft)
                    Spacer(Modifier.height(6.dp))
                    Text(
                        text = "Selamat datang",
                        style = MaterialTheme.typography.headlineSmall,
                        color = Color.White,
                    )
                    Text(
                        text = "Damai sejahtera menyertai Anda",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White.copy(alpha = 0.82f),
                    )
                }
                IconBadge(
                    icon = Icons.Filled.Notifications,
                    contentDescription = "Notifikasi",
                    containerColor = Color.White.copy(alpha = 0.16f),
                    contentColor = Color.White,
                    size = 46,
                )
            }
            Spacer(Modifier.height(18.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                HeaderStat(rayonCount.toString(), "Rayon", Modifier.weight(1f))
                HeaderStat(unsurCount.toString(), "Unsur", Modifier.weight(1f))
                HeaderStat(albumCount.toString(), "Album", Modifier.weight(1f))
            }
        }
    }
}

@Composable
private fun HeaderStat(value: String, label: String, modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        color = Color.White.copy(alpha = 0.13f),
    ) {
        Column(Modifier.padding(vertical = 12.dp, horizontal = 14.dp)) {
            Text(
                text = value,
                style = MaterialTheme.typography.titleLarge,
                color = Color.White,
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = label,
                style = MaterialTheme.typography.bodySmall,
                color = Color.White.copy(alpha = 0.8f),
            )
        }
    }
}

@Composable
private fun FeaturedRenungan(
    renungan: RenunganEntity,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    PaperCard(modifier = modifier.fillMaxWidth().clickable(onClick = onClick)) {
        Column(Modifier.padding(18.dp)) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Eyebrow("Renungan Hari Ini")
                StatusChip(
                    text = renungan.tglPublish ?: "",
                    dotColor = Brand.Gold,
                )
            }
            Spacer(Modifier.height(10.dp))
            Text(
                text = renungan.judul,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface,
            )
            if (!renungan.bacaan.isNullOrBlank()) {
                Spacer(Modifier.height(6.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Filled.Favorite,
                        contentDescription = null,
                        tint = Brand.Terracotta,
                        modifier = Modifier.size(15.dp),
                    )
                    Spacer(Modifier.width(6.dp))
                    Text(
                        text = renungan.bacaan,
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.secondary,
                    )
                }
            }
            if (!renungan.kutipan.isNullOrBlank()) {
                Spacer(Modifier.height(10.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(14.dp))
                        .background(MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f))
                        .padding(14.dp),
                ) {
                    Text(
                        text = "“${renungan.kutipan}”",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                }
            }
        }
    }
}

@Composable
private fun QuickAccessGrid(
    items: List<MenuItem>,
    onItemClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(16.dp)) {
        items.chunked(4).forEach { row ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                row.forEach { item ->
                    QuickAccessTile(
                        item = item,
                        onClick = { onItemClick(item.id) },
                        modifier = Modifier.weight(1f),
                    )
                }
                repeat(4 - row.size) { Spacer(Modifier.weight(1f)) }
            }
        }
    }
}

@Composable
private fun QuickAccessTile(
    item: MenuItem,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.clip(RoundedCornerShape(16.dp)).clickable(onClick = onClick),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        IconBadge(
            icon = item.icon,
            contentDescription = item.label,
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
            size = 52,
        )
        Spacer(Modifier.height(6.dp))
        Text(
            text = item.label,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onBackground,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

@Composable
private fun JadwalCard(item: JadwalEntity) {
    val (tglNum, tglBulan) = formatTanggal(item.tgl)
    val hari = formatHari(item.tgl)
    PaperCard(Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(
                modifier = Modifier
                    .clip(RoundedCornerShape(14.dp))
                    .background(MaterialTheme.colorScheme.primary)
                    .padding(vertical = 10.dp, horizontal = 14.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = tglNum,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontWeight = FontWeight.Bold,
                )
                Text(
                    text = tglBulan,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.85f),
                )
            }
            Spacer(Modifier.width(14.dp))
            Column(Modifier.weight(1f)) {
                Text(
                    text = item.nmIbadah,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Spacer(Modifier.height(3.dp))
                Text(
                    text = "$hari • ${item.waktu ?: ""} WIT",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.secondary,
                    fontWeight = FontWeight.SemiBold,
                )
                Text(
                    text = if (item.rayon != null) "${item.tempat ?: ""} • Rayon ${item.rayon}" else item.tempat ?: "",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
    }
}

@Composable
private fun AlbumCard(item: AlbumEntity) {
    PaperCard(Modifier.width(160.dp)) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(96.dp)
                    .background(Brush.linearGradient(listOf(Brand.Pacific, Brand.PacificDeep))),
                contentAlignment = Alignment.Center,
            ) {
                if (!item.sampul.isNullOrBlank()) {
                    AsyncImage(
                        model = item.sampul,
                        contentDescription = item.judul,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop,
                    )
                } else {
                    Icon(
                        imageVector = AppIcons.Gallery,
                        contentDescription = null,
                        tint = Color.White.copy(alpha = 0.9f),
                        modifier = Modifier.size(30.dp),
                    )
                }
            }
            Column(Modifier.padding(12.dp)) {
                Text(
                    text = item.judul,
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Spacer(Modifier.height(2.dp))
                Text(
                    text = "${item.jumlahFoto} foto${if (!item.kategori.isNullOrBlank()) " • ${item.kategori}" else ""}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
    }
}

@Composable
private fun WartaCard(item: PengumumanEntity) {
    PaperCard(Modifier.fillMaxWidth()) {
        Row(modifier = Modifier.padding(14.dp)) {
            IconBadge(
                icon = Icons.Filled.Notifications,
                contentDescription = null,
                containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                contentColor = MaterialTheme.colorScheme.onTertiaryContainer,
            )
            Spacer(Modifier.width(12.dp))
            Column(Modifier.weight(1f)) {
                Text(
                    text = item.judul,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                if (!item.isi.isNullOrBlank()) {
                    Spacer(Modifier.height(3.dp))
                    Text(
                        text = item.isi,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
                Spacer(Modifier.height(6.dp))
                Text(
                    text = item.tglPublish ?: "",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.secondary,
                )
            }
        }
    }
}

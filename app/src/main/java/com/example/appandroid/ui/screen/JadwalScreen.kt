package com.example.appandroid.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.appandroid.data.local.entity.JadwalEntity
import com.example.appandroid.ui.component.EmptyState
import com.example.appandroid.ui.component.IconBadge
import com.example.appandroid.ui.component.PaperCard
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

private val jadwalIdLocale = Locale.of("id")
private val jadwalDateFormatter = DateTimeFormatter.ofPattern("d MMM", jadwalIdLocale)
private val jadwalDayFormatter = DateTimeFormatter.ofPattern("EEEE", jadwalIdLocale)

private fun formatJadwalTanggal(tgl: String?): Pair<String, String> {
    if (tgl == null) return "--" to ""
    return try {
        val date = LocalDate.parse(tgl)
        date.format(jadwalDateFormatter).split(" ", limit = 2).let {
            (it.getOrElse(0) { "--" }) to (it.getOrElse(1) { "" })
        }
    } catch (_: Exception) {
        "--" to ""
    }
}

private fun formatJadwalHari(tgl: String?): String {
    if (tgl == null) return ""
    return try {
        LocalDate.parse(tgl).format(jadwalDayFormatter)
    } catch (_: Exception) {
        ""
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JadwalScreen(
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier,
    viewModel: JadwalViewModel = hiltViewModel(),
) {
    val jadwalList by viewModel.jadwalList.collectAsState()
    val isRefreshing by viewModel.isRefreshing.collectAsState()
    val error by viewModel.error.collectAsState()

    Box(modifier.fillMaxSize()) {
        Column(Modifier.fillMaxSize()) {
            ScreenHeader(
                title = "Jadwal Ibadah",
                subtitle = "Jadwal ibadah minggu ini dan mendatang",
            )
            PullToRefreshBox(
                isRefreshing = isRefreshing,
                onRefresh = { viewModel.refresh() },
                modifier = Modifier.fillMaxSize(),
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(
                        start = 16.dp,
                        end = 16.dp,
                        top = 18.dp,
                        bottom = contentPadding.calculateBottomPadding() + 24.dp,
                    ),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    if (jadwalList.isNotEmpty()) {
                        item { JadwalSummary(count = jadwalList.size) }
                        items(jadwalList, key = { it.id }) { jadwal ->
                            JadwalCard(jadwal = jadwal)
                        }
                    } else {
                        item {
                            EmptyState(
                                icon = Icons.Filled.DateRange,
                                title = "Belum ada jadwal",
                                message = "Jadwal ibadah akan tampil setelah terhubung ke server. " +
                                    "Tarik ke bawah untuk memuat ulang.",
                                modifier = Modifier.padding(horizontal = 24.dp, vertical = 48.dp),
                            )
                        }
                    }
                }
            }
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
private fun JadwalSummary(count: Int) {
    PaperCard(Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            IconBadge(
                icon = Icons.Filled.DateRange,
                contentDescription = null,
                size = 44,
            )
            Spacer(Modifier.width(14.dp))
            Column {
                Text(
                    text = count.toString(),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Bold,
                )
                Text(
                    text = "Jadwal ibadah mendatang",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
    }
}

@Composable
private fun JadwalCard(jadwal: JadwalEntity) {
    val (tglNum, tglBulan) = formatJadwalTanggal(jadwal.tgl)
    val hari = formatJadwalHari(jadwal.tgl)
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
                    text = jadwal.nmIbadah,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
                Spacer(Modifier.height(3.dp))
                Text(
                    text = listOfNotNull(hari.ifBlank { null }, jadwal.waktu?.let { "$it WIT" })
                        .joinToString(" • "),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.secondary,
                    fontWeight = FontWeight.SemiBold,
                )
                val tempatText = if (jadwal.rayon != null) {
                    listOfNotNull(jadwal.tempat, "Rayon ${jadwal.rayon}").joinToString(" • ")
                } else {
                    jadwal.tempat.orEmpty()
                }
                if (tempatText.isNotBlank()) {
                    Text(
                        text = tempatText,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
                if (!jadwal.pelayanFirman.isNullOrBlank()) {
                    Spacer(Modifier.height(4.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Filled.Person,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(14.dp),
                        )
                        Spacer(Modifier.width(4.dp))
                        Text(
                            text = jadwal.pelayanFirman,
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )
                    }
                }
            }
        }
    }
}

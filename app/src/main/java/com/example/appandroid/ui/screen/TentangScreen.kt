package com.example.appandroid.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.appandroid.BuildConfig
import com.example.appandroid.ui.component.Eyebrow
import com.example.appandroid.ui.component.IconBadge
import com.example.appandroid.ui.component.PaperCard
import com.example.appandroid.ui.icon.AppIcons
import com.example.appandroid.ui.theme.Brand
// __BODY__

private data class Fitur(val icon: ImageVector, val judul: String, val deskripsi: String)

private val daftarFitur = listOf(
    Fitur(Icons.Filled.DateRange, "Jadwal Ibadah", "Jadwal pelayanan dan ibadah mendatang"),
    Fitur(Icons.Filled.Favorite, "Renungan", "Renungan untuk bertumbuh setiap hari"),
    Fitur(Icons.Filled.Notifications, "Warta Jemaat", "Pengumuman dan warta terbaru"),
    Fitur(AppIcons.Gallery, "Galeri", "Dokumentasi kegiatan jemaat"),
    Fitur(Icons.Filled.Person, "Data & Pelayanan", "Jemaat, rayon, pendeta, majelis, dan pengurus"),
    Fitur(Icons.Filled.CheckCircle, "Statistik", "Rekap kehadiran dan keuangan jemaat"),
)

@Composable
fun TentangScreen(
    onBack: () -> Unit,
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier,
) {
    Column(modifier.fillMaxSize()) {
        ScreenHeader(
            title = "Tentang",
            subtitle = "Informasi mengenai aplikasi ini",
            onBack = onBack,
        )
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(
                start = 16.dp,
                end = 16.dp,
                top = 24.dp,
                bottom = contentPadding.calculateBottomPadding() + 24.dp,
            ),
            verticalArrangement = Arrangement.spacedBy(20.dp),
        ) {
            item { AppIdentity() }
            item {
                PaperCard(Modifier.fillMaxWidth()) {
                    Text(
                        text = "GKI Syaloom adalah aplikasi profil dan informasi resmi jemaat " +
                            "GKI Syaloom Pasifik Indah. Aplikasi menyajikan jadwal ibadah, renungan, " +
                            "warta, galeri, serta data pelayanan dan statistik jemaat. Seluruh data " +
                            "dapat dilihat tanpa perlu masuk akun dan tetap tersedia secara luring " +
                            "(offline) setelah dimuat.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(16.dp),
                    )
                }
            }
            item {
                Column {
                    Eyebrow("Fitur", color = Brand.Pacific)
                    Spacer(Modifier.height(10.dp))
                    PaperCard(Modifier.fillMaxWidth()) {
                        Column(Modifier.padding(vertical = 6.dp)) {
                            daftarFitur.forEach { fitur -> FiturRow(fitur) }
                        }
                    }
                }
            }
            item { AppFooter() }
        }
    }
}
// __PARTS__

@Composable
private fun AppIdentity() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier
                .size(96.dp)
                .clip(RoundedCornerShape(28.dp))
                .background(Brush.linearGradient(Brand.HeaderGradient)),
            contentAlignment = Alignment.Center,
        ) {
            // Salib sederhana sebagai lambang — tidak memakai motif budaya tertentu.
            Box(contentAlignment = Alignment.Center) {
                Box(
                    Modifier
                        .width(11.dp)
                        .height(50.dp)
                        .clip(RoundedCornerShape(3.dp))
                        .background(Color.White),
                )
                Box(
                    Modifier
                        .offset(y = (-9).dp)
                        .width(34.dp)
                        .height(11.dp)
                        .clip(RoundedCornerShape(3.dp))
                        .background(Color.White),
                )
            }
        }
        Spacer(Modifier.height(16.dp))
        Text(
            text = "GKI Syaloom Pasifik Indah",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
        )
        Spacer(Modifier.height(4.dp))
        Text(
            text = "Versi ${BuildConfig.VERSION_NAME} (${BuildConfig.VERSION_CODE})",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}

@Composable
private fun FiturRow(fitur: Fitur) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        IconBadge(
            icon = fitur.icon,
            contentDescription = null,
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
            size = 40,
        )
        Spacer(Modifier.width(14.dp))
        Column {
            Text(
                text = fitur.judul,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface,
            )
            Text(
                text = fitur.deskripsi,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}

@Composable
private fun AppFooter() {
    Column(
        modifier = Modifier.fillMaxWidth().padding(top = 4.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "© 2026 GKI Syaloom Pasifik Indah",
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
        )
        Spacer(Modifier.height(2.dp))
        Text(
            text = "Dibuat untuk mendukung pelayanan jemaat",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
        )
    }
}


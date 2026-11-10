package com.example.appandroid.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.appandroid.data.local.entity.RenunganEntity
import com.example.appandroid.ui.component.EmptyState
import com.example.appandroid.ui.component.HtmlText
import com.example.appandroid.ui.component.PaperCard
import com.example.appandroid.ui.component.StatusChip
import com.example.appandroid.ui.theme.Brand

@Composable
fun RenunganDetailScreen(
    onBack: () -> Unit,
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier,
    viewModel: RenunganDetailViewModel = hiltViewModel(),
) {
    val renungan by viewModel.renungan.collectAsState()

    Column(modifier.fillMaxSize()) {
        ScreenHeader(
            title = renungan?.judul ?: "Renungan",
            subtitle = renungan?.tglPublish
                ?.let { formatTanggalPublish(it) }
                ?.takeIf { it.isNotBlank() } ?: "Renungan harian",
            onBack = onBack,
        )

        when (val data = renungan) {
            null -> {
                // Data belum tersedia di cache (mis. proses sinkronisasi belum selesai).
                EmptyState(
                    icon = Icons.Filled.Favorite,
                    title = "Renungan tidak ditemukan",
                    message = "Kembali ke daftar renungan lalu buka kembali.",
                    modifier = Modifier.padding(horizontal = 24.dp, vertical = 48.dp),
                )
            }
            else -> RenunganDetailContent(
                renungan = data,
                bottomPadding = contentPadding.calculateBottomPadding(),
            )
        }
    }
}

@Composable
private fun RenunganDetailContent(
    renungan: RenunganEntity,
    bottomPadding: Dp,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(start = 16.dp, end = 16.dp, top = 18.dp)
            .padding(bottom = bottomPadding + 24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        // Baris meta: tanggal & sumber.
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            if (!renungan.tglPublish.isNullOrBlank()) {
                StatusChip(
                    text = formatTanggalPublish(renungan.tglPublish),
                    dotColor = Brand.Gold,
                )
            }
            if (!renungan.sumber.isNullOrBlank()) {
                Text(
                    text = renungan.sumber,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }

        // Bacaan alkitab.
        if (!renungan.bacaan.isNullOrBlank()) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 2.dp),
            ) {
                Icon(
                    imageVector = Icons.Filled.Favorite,
                    contentDescription = null,
                    tint = Brand.Terracotta,
                    modifier = Modifier.size(16.dp),
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    text = renungan.bacaan,
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.secondary,
                )
            }
        }

        // Kutipan tampil sebagai blok aksen (blockquote).
        if (!renungan.kutipan.isNullOrBlank()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min),
            ) {
                Box(
                    modifier = Modifier
                        .width(4.dp)
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(2.dp))
                        .background(Brand.Gold),
                )
                Spacer(Modifier.width(12.dp))
                Text(
                    text = renungan.kutipan,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontStyle = FontStyle.Italic,
                )
            }
        }

        // Isi renungan lengkap dengan format HTML.
        if (!renungan.isi.isNullOrBlank()) {
            PaperCard(Modifier.fillMaxWidth()) {
                HtmlText(
                    html = renungan.isi,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                )
            }
        }
    }
}

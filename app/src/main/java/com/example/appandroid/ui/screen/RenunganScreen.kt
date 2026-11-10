package com.example.appandroid.ui.screen

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.appandroid.data.local.entity.RenunganEntity
import com.example.appandroid.ui.component.EmptyState
import com.example.appandroid.ui.component.HtmlText
import com.example.appandroid.ui.component.PaperCard
import com.example.appandroid.ui.component.StatusChip
import com.example.appandroid.ui.theme.Brand

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RenunganScreen(
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier,
    onOpenDetail: (String) -> Unit = {},
    viewModel: RenunganViewModel = hiltViewModel(),
) {
    val renunganList by viewModel.renunganList.collectAsState()
    val isRefreshing by viewModel.isRefreshing.collectAsState()
    val error by viewModel.error.collectAsState()

    Box(modifier.fillMaxSize()) {
        Column(Modifier.fillMaxSize()) {
            ScreenHeader(
                title = "Renungan Harian",
                subtitle = "Santapan rohani setiap hari",
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
                    if (renunganList.isNotEmpty()) {
                        items(renunganList, key = { it.id }) { renungan ->
                            RenunganCard(
                                renungan = renungan,
                                onClick = { onOpenDetail(renungan.id) },
                            )
                        }
                    } else {
                        item {
                            EmptyState(
                                icon = Icons.Filled.Favorite,
                                title = "Belum ada renungan",
                                message = "Renungan harian akan tampil setelah terhubung ke server. " +
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
private fun RenunganCard(
    renungan: RenunganEntity,
    onClick: () -> Unit,
) {
    PaperCard(Modifier.fillMaxWidth().clickable(onClick = onClick)) {
        Column(Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top,
            ) {
                Text(
                    text = renungan.judul,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f),
                )
                if (!renungan.tglPublish.isNullOrBlank()) {
                    Spacer(Modifier.width(10.dp))
                    StatusChip(
                        text = formatTanggalPublish(renungan.tglPublish),
                        dotColor = Brand.Gold,
                    )
                }
            }
            if (!renungan.bacaan.isNullOrBlank()) {
                Spacer(Modifier.height(8.dp))
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
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
            }
            val preview = renungan.kutipan?.takeIf { it.isNotBlank() }
                ?: renungan.isi
            if (!preview.isNullOrBlank()) {
                Spacer(Modifier.height(8.dp))
                HtmlText(
                    html = preview,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
    }
}

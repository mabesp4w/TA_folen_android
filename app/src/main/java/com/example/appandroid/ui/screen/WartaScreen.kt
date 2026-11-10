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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.ExperimentalMaterial3Api
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
import com.example.appandroid.data.local.entity.PengumumanEntity
import com.example.appandroid.ui.component.EmptyState
import com.example.appandroid.ui.component.HtmlText
import com.example.appandroid.ui.component.IconBadge
import com.example.appandroid.ui.component.PaperCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WartaScreen(
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier,
    viewModel: WartaViewModel = hiltViewModel(),
) {
    val wartaList by viewModel.wartaList.collectAsState()
    val isRefreshing by viewModel.isRefreshing.collectAsState()
    val error by viewModel.error.collectAsState()

    Box(modifier.fillMaxSize()) {
        Column(Modifier.fillMaxSize()) {
            ScreenHeader(
                title = "Warta Jemaat",
                subtitle = "Pengumuman dan informasi gereja",
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
                    if (wartaList.isNotEmpty()) {
                        items(wartaList, key = { it.id }) { warta ->
                            WartaCard(pengumuman = warta)
                        }
                    } else {
                        item {
                            EmptyState(
                                icon = Icons.Filled.Notifications,
                                title = "Belum ada warta",
                                message = "Warta dan pengumuman jemaat akan tampil setelah terhubung ke server. " +
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
private fun WartaCard(pengumuman: PengumumanEntity) {
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
                    text = pengumuman.judul,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
                val isi = pengumuman.isi
                if (!isi.isNullOrBlank()) {
                    Spacer(Modifier.height(4.dp))
                    HtmlText(
                        html = isi,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
                if (!pengumuman.tglPublish.isNullOrBlank()) {
                    Spacer(Modifier.height(6.dp))
                    Text(
                        text = formatTanggalPublish(pengumuman.tglPublish),
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.secondary,
                    )
                }
            }
        }
    }
}

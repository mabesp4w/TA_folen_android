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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.appandroid.data.local.entity.PengurusEntity
import com.example.appandroid.ui.component.EmptyState
import com.example.appandroid.ui.component.IconBadge
import com.example.appandroid.ui.component.PaperCard
import com.example.appandroid.ui.component.StatusChip
import com.example.appandroid.ui.theme.Brand

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PengurusScreen(
    onBack: () -> Unit,
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier,
    viewModel: PengurusViewModel = hiltViewModel(),
) {
    val pengurusList by viewModel.pengurusList.collectAsState()
    val isRefreshing by viewModel.isRefreshing.collectAsState()
    val error by viewModel.error.collectAsState()

    Column(modifier.fillMaxSize()) {
        ScreenHeader(
            title = "Badan Pengurus",
            subtitle = "Kepengurusan unsur kategorial",
            onBack = onBack,
        )
        Box(Modifier.fillMaxSize()) {
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
                    if (pengurusList.isNotEmpty()) {
                        items(pengurusList, key = { it.id }) { pengurus ->
                            PengurusCard(pengurus = pengurus)
                        }
                    } else {
                        item {
                            EmptyState(
                                icon = Icons.Filled.AccountBox,
                                title = "Belum ada data pengurus",
                                message = "Data badan pengurus akan tampil setelah terhubung ke server. " +
                                    "Tarik ke bawah untuk memuat ulang.",
                                modifier = Modifier.padding(horizontal = 24.dp, vertical = 48.dp),
                            )
                        }
                    }
                }
            }
// __BODY2__

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
}

@Composable
private fun PengurusCard(pengurus: PengurusEntity) {
    val meta = listOfNotNull(
        pengurus.jabatan,
        pengurus.sektor?.let { "Sektor $it" },
        pengurus.rayon?.let { "Rayon $it" },
    ).joinToString(" • ")
    val unsur = pengurus.unsur
    PaperCard(Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            IconBadge(
                icon = Icons.Filled.AccountBox,
                contentDescription = null,
            )
            Spacer(Modifier.width(14.dp))
            Column(Modifier.weight(1f)) {
                Text(
                    text = pengurus.nama,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
                if (meta.isNotEmpty()) {
                    Text(
                        text = meta,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
            }
            if (!unsur.isNullOrBlank()) {
                Spacer(Modifier.width(10.dp))
                StatusChip(text = unsur, dotColor = Brand.Terracotta)
            }
        }
    }
}

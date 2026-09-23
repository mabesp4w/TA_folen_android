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
import androidx.compose.material.icons.filled.Person
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
import com.example.appandroid.data.local.entity.JemaatEntity
import com.example.appandroid.ui.component.EmptyState
import com.example.appandroid.ui.component.IconBadge
import com.example.appandroid.ui.component.PaperCard
import com.example.appandroid.ui.component.StatusChip
import com.example.appandroid.ui.theme.Brand

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JemaatScreen(
    onBack: () -> Unit,
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier,
    viewModel: JemaatViewModel = hiltViewModel(),
) {
    val jemaatList by viewModel.jemaatList.collectAsState()
    val isRefreshing by viewModel.isRefreshing.collectAsState()
    val error by viewModel.error.collectAsState()

    Column(modifier.fillMaxSize()) {
        ScreenHeader(
            title = "Jemaat",
            subtitle = "Direktori warga jemaat",
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
                    if (jemaatList.isNotEmpty()) {
                        item { JemaatSummary(jemaatCount = jemaatList.size) }
                        items(jemaatList, key = { it.id }) { jemaat ->
                            JemaatCard(jemaat = jemaat)
                        }
                    } else {
                        item {
                            EmptyState(
                                icon = Icons.Filled.Person,
                                title = "Belum ada data jemaat",
                                message = "Data jemaat akan tampil setelah terhubung ke server. " +
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
private fun JemaatSummary(
    jemaatCount: Int,
    modifier: Modifier = Modifier,
) {
    PaperCard(modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            IconBadge(
                icon = Icons.Filled.Person,
                contentDescription = null,
                size = 44,
            )
            Spacer(Modifier.width(14.dp))
            Column {
                Text(
                    text = jemaatCount.toString(),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Bold,
                )
                Text(
                    text = "Warga jemaat terdaftar",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
    }
}
// __BODY3__

@Composable
private fun JemaatCard(jemaat: JemaatEntity) {
    val meta = listOfNotNull(
        jemaat.rayon?.let { "Rayon $it" },
        jemaat.alamatBlok,
    ).joinToString(" • ")
    val status = jemaat.status
    PaperCard(Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            IconBadge(
                icon = Icons.Filled.Person,
                contentDescription = null,
                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
            )
            Spacer(Modifier.width(14.dp))
            Column(Modifier.weight(1f)) {
                Text(
                    text = jemaat.nmLengkap,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                if (meta.isNotEmpty()) {
                    Text(
                        text = meta,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
            }
            if (!status.isNullOrBlank()) {
                Spacer(Modifier.width(10.dp))
                StatusChip(text = status, dotColor = Brand.Pacific)
            }
        }
    }
}

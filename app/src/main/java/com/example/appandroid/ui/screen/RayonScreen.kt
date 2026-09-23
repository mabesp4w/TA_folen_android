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
import androidx.compose.material.icons.filled.LocationOn
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.appandroid.data.local.entity.RayonEntity
import com.example.appandroid.ui.component.EmptyState
import com.example.appandroid.ui.component.IconBadge
import com.example.appandroid.ui.component.PaperCard
import com.example.appandroid.ui.component.StatusChip
import com.example.appandroid.ui.theme.Brand

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RayonScreen(
    onBack: () -> Unit,
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier,
    viewModel: RayonViewModel = hiltViewModel(),
) {
    val rayonList by viewModel.rayonList.collectAsState()
    val isRefreshing by viewModel.isRefreshing.collectAsState()
    val error by viewModel.error.collectAsState()

    Column(modifier.fillMaxSize()) {
        ScreenHeader(
            title = "Rayon",
            subtitle = "Pembagian wilayah pelayanan jemaat",
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
                    if (rayonList.isNotEmpty()) {
                        item {
                            RayonSummary(
                                rayonCount = rayonList.size,
                                jemaatCount = rayonList.sumOf { it.jumlahJemaat },
                            )
                        }
                        items(rayonList, key = { it.id }) { rayon ->
                            RayonCard(rayon = rayon)
                        }
                    } else {
                        item {
                            EmptyState(
                                icon = Icons.Filled.LocationOn,
                                title = "Belum ada data rayon",
                                message = "Data rayon akan tampil setelah terhubung ke server. " +
                                    "Tarik ke bawah untuk memuat ulang.",
                                modifier = Modifier.padding(horizontal = 24.dp, vertical = 48.dp),
                            )
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
}

@Composable
private fun RayonSummary(
    rayonCount: Int,
    jemaatCount: Int,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        SummaryTile(
            value = rayonCount.toString(),
            label = "Rayon",
            icon = Icons.Filled.LocationOn,
            modifier = Modifier.weight(1f),
        )
        SummaryTile(
            value = jemaatCount.toString(),
            label = "Jemaat",
            icon = Icons.Filled.Person,
            modifier = Modifier.weight(1f),
        )
    }
}

@Composable
private fun SummaryTile(
    value: String,
    label: String,
    icon: ImageVector,
    modifier: Modifier = Modifier,
) {
    PaperCard(modifier) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            IconBadge(
                icon = icon,
                contentDescription = null,
                size = 40,
            )
            Spacer(Modifier.width(12.dp))
            Column {
                Text(
                    text = value,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Bold,
                )
                Text(
                    text = label,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
    }
}

@Composable
private fun RayonCard(rayon: RayonEntity) {
    PaperCard(Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            IconBadge(
                icon = Icons.Filled.LocationOn,
                contentDescription = null,
                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
            )
            Spacer(Modifier.width(14.dp))
            Text(
                text = rayon.nmRayon,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(1f),
            )
            Spacer(Modifier.width(10.dp))
            StatusChip(
                text = "${rayon.jumlahJemaat} jemaat",
                dotColor = Brand.Pacific,
            )
        }
    }
}

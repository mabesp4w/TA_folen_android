package com.example.appandroid.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.example.appandroid.data.remote.dto.FotoDto
import com.example.appandroid.ui.component.EmptyState
import com.example.appandroid.ui.component.PaperCard
import com.example.appandroid.ui.icon.AppIcons
import com.example.appandroid.ui.theme.Brand

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GaleriDetailScreen(
    onBack: () -> Unit,
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier,
    viewModel: GaleriDetailViewModel = hiltViewModel(),
) {
    val album by viewModel.album.collectAsState()
    val isRefreshing by viewModel.isRefreshing.collectAsState()
    val error by viewModel.error.collectAsState()

    val fotos = album?.foto.orEmpty()
    val subtitle = album?.let { a ->
        listOfNotNull(
            a.kategori?.takeIf { it.isNotBlank() },
            a.tgl?.takeIf { it.isNotBlank() },
        ).joinToString(" • ").ifBlank { "${a.foto.size} foto" }
    } ?: "Memuat album…"

    Column(modifier.fillMaxSize()) {
        ScreenHeader(
            title = album?.judul ?: "Album",
            subtitle = subtitle,
            onBack = onBack,
        )
        Box(Modifier.fillMaxSize()) {
            PullToRefreshBox(
                isRefreshing = isRefreshing,
                onRefresh = { viewModel.refresh() },
                modifier = Modifier.fillMaxSize(),
            ) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(
                        start = 16.dp,
                        end = 16.dp,
                        top = 18.dp,
                        bottom = contentPadding.calculateBottomPadding() + 24.dp,
                    ),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    album?.keterangan?.takeIf { it.isNotBlank() }?.let { keterangan ->
                        item(span = { GridItemSpan(maxLineSpan) }) {
                            DetailIntro(keterangan = keterangan, fotoCount = fotos.size)
                        }
                    }
                    if (fotos.isNotEmpty()) {
                        items(fotos, key = { it.id }) { foto ->
                            FotoCard(foto = foto)
                        }
                    } else if (album != null) {
                        item(span = { GridItemSpan(maxLineSpan) }) {
                            EmptyState(
                                icon = AppIcons.Gallery,
                                title = "Belum ada foto",
                                message = "Album ini belum memiliki foto.",
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
                    Text("Gagal memuat album. Periksa koneksi.")
                }
            }
        }
    }
}
@Composable
private fun DetailIntro(keterangan: String, fotoCount: Int) {
    PaperCard(Modifier.fillMaxWidth()) {
        Column(Modifier.padding(16.dp)) {
            Text(
                text = keterangan,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
            )
            Spacer(Modifier.height(6.dp))
            Text(
                text = "$fotoCount foto",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.secondary,
            )
        }
    }
}
@Composable
private fun FotoCard(foto: FotoDto) {
    PaperCard(Modifier.fillMaxWidth()) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .background(Brush.linearGradient(listOf(Brand.Pacific, Brand.PacificDeep))),
                contentAlignment = Alignment.Center,
            ) {
                if (!foto.url.isNullOrBlank()) {
                    AsyncImage(
                        model = foto.url,
                        contentDescription = foto.caption,
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
            if (!foto.caption.isNullOrBlank()) {
                Text(
                    text = foto.caption,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(10.dp),
                )
            }
        }
    }
}


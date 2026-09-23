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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.BrightnessAuto
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.appandroid.BuildConfig
import com.example.appandroid.data.settings.ThemeMode
import com.example.appandroid.ui.component.Eyebrow
import com.example.appandroid.ui.component.IconBadge
import com.example.appandroid.ui.component.PaperCard
import com.example.appandroid.ui.theme.Brand
// __BODY__

private data class ThemeOption(val mode: ThemeMode, val label: String, val icon: ImageVector)

private val themeOptions = listOf(
    ThemeOption(ThemeMode.SYSTEM, "Ikuti sistem", Icons.Filled.BrightnessAuto),
    ThemeOption(ThemeMode.LIGHT, "Terang", Icons.Filled.LightMode),
    ThemeOption(ThemeMode.DARK, "Gelap", Icons.Filled.DarkMode),
)

@Composable
fun PengaturanScreen(
    onBack: () -> Unit,
    onOpenTentang: () -> Unit,
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier,
    viewModel: SettingsViewModel = hiltViewModel(),
) {
    val themeMode by viewModel.themeMode.collectAsState()

    Column(modifier.fillMaxSize()) {
        ScreenHeader(
            title = "Pengaturan",
            subtitle = "Sesuaikan tampilan dan lihat info aplikasi",
            onBack = onBack,
        )
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(
                start = 16.dp,
                end = 16.dp,
                top = 20.dp,
                bottom = contentPadding.calculateBottomPadding() + 24.dp,
            ),
            verticalArrangement = Arrangement.spacedBy(22.dp),
        ) {
            item {
                Column {
                    Eyebrow("Tampilan", color = Brand.Pacific)
                    Spacer(Modifier.height(10.dp))
                    PaperCard(Modifier.fillMaxWidth()) {
                        Column {
                            themeOptions.forEachIndexed { index, option ->
                                ThemeRow(
                                    option = option,
                                    selected = option.mode == themeMode,
                                    onClick = { viewModel.setThemeMode(option.mode) },
                                )
                                if (index != themeOptions.lastIndex) RowDivider()
                            }
                        }
                    }
                }
            }
            item {
                Column {
                    Eyebrow("Aplikasi", color = Brand.Pacific)
                    Spacer(Modifier.height(10.dp))
                    PaperCard(Modifier.fillMaxWidth()) {
                        Column {
                            InfoRow(
                                label = "Versi aplikasi",
                                value = "${BuildConfig.VERSION_NAME} (${BuildConfig.VERSION_CODE})",
                            )
                            RowDivider()
                            InfoRow(label = "Server", value = BuildConfig.BASE_URL)
                            RowDivider()
                            NavRow(
                                icon = Icons.Filled.Info,
                                label = "Tentang aplikasi",
                                onClick = onOpenTentang,
                            )
                        }
                    }
                }
            }
        }
    }
}
// __ROWS__

@Composable
private fun ThemeRow(
    option: ThemeOption,
    selected: Boolean,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 14.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        IconBadge(
            icon = option.icon,
            contentDescription = null,
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
            size = 40,
        )
        Spacer(Modifier.width(14.dp))
        Text(
            text = option.label,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.weight(1f),
        )
        RadioButton(selected = selected, onClick = onClick)
    }
}

@Composable
private fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface,
        )
        Spacer(Modifier.width(16.dp))
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.End,
            modifier = Modifier.weight(1f),
        )
    }
}

@Composable
private fun NavRow(icon: ImageVector, label: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.size(22.dp),
        )
        Spacer(Modifier.width(12.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.weight(1f),
        )
        Icon(
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.size(20.dp),
        )
    }
}

@Composable
private fun RowDivider() {
    Box(
        Modifier
            .fillMaxWidth()
            .padding(start = 16.dp)
            .height(1.dp)
            .background(MaterialTheme.colorScheme.outlineVariant),
    )
}


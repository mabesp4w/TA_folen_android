package com.example.appandroid.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appandroid.data.local.entity.AlbumEntity
import com.example.appandroid.data.local.entity.JadwalEntity
import com.example.appandroid.data.local.entity.PengumumanEntity
import com.example.appandroid.data.local.entity.RenunganEntity
import com.example.appandroid.data.repository.ChurchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val repository: ChurchRepository,
) : ViewModel() {

    val renungan: StateFlow<RenunganEntity?> = repository.getRenunganTerbaru()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), null)

    val jadwal: StateFlow<List<JadwalEntity>> = repository.getJadwalMendatang()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    val warta: StateFlow<List<PengumumanEntity>> = repository.getPengumumanTerbaru()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    val albums: StateFlow<List<AlbumEntity>> = repository.getAlbums()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    val rayonCount: StateFlow<Int> = repository.getRayonCount()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), 0)

    val unsurCount: StateFlow<Int> = repository.getUnsurCount()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), 0)

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    init {
        refresh()
    }

    fun refresh() {
        viewModelScope.launch {
            _isRefreshing.value = true
            _error.value = null
            val result = repository.refreshAll()
            if (result.isFailure) {
                _error.value = result.exceptionOrNull()?.message
            }
            _isRefreshing.value = false
        }
    }

    fun clearError() {
        _error.value = null
    }
}

package com.example.appandroid.ui.screen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appandroid.data.remote.dto.AlbumDetailDto
import com.example.appandroid.data.repository.ChurchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GaleriDetailViewModel @Inject constructor(
    private val repository: ChurchRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val albumId: String = checkNotNull(savedStateHandle["id"])

    private val _album = MutableStateFlow<AlbumDetailDto?>(null)
    val album: StateFlow<AlbumDetailDto?> = _album.asStateFlow()

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
            val result = repository.getAlbumDetail(albumId)
            result.onSuccess { _album.value = it }
                .onFailure { _error.value = it.message }
            _isRefreshing.value = false
        }
    }

    fun clearError() {
        _error.value = null
    }
}

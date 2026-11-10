package com.example.appandroid.ui.screen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appandroid.data.local.entity.RenunganEntity
import com.example.appandroid.data.repository.ChurchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class RenunganDetailViewModel @Inject constructor(
    repository: ChurchRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val renunganId: String = checkNotNull(savedStateHandle["id"])

    // Data dibaca dari cache Room yang sudah diisi oleh layar daftar.
    val renungan: StateFlow<RenunganEntity?> = repository.getRenunganById(renunganId)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), null)
}

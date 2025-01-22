package com.example.propertiapp.ui.viewmodel.pemilik

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.propertiapp.model.Pemilik
import com.example.propertiapp.repository.PemilikRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class DetailPemilikUiState {
    object Loading : DetailPemilikUiState()
    data class Success(val pemilik: Pemilik) : DetailPemilikUiState()
    object Error : DetailPemilikUiState()
}

class DetailPemilikViewModel(private val repository: PemilikRepository) : ViewModel() {

    private val _uiState = MutableStateFlow<DetailPemilikUiState>(DetailPemilikUiState.Loading)
    val uiState: StateFlow<DetailPemilikUiState> = _uiState.asStateFlow()

    fun getPemilikById(idPemilik: String) {
        viewModelScope.launch {
            _uiState.value = DetailPemilikUiState.Loading
            try {
                val pemilik = repository.getPemilikById(idPemilik)
                // Log untuk debugging
                Log.d("DetailPemilikViewModel", "Pemilik: $pemilik")
                _uiState.value = DetailPemilikUiState.Success(pemilik)
            } catch (e: Exception) {
                // Log error
                Log.e("DetailPemilikViewModel", "Error: ${e.message}")
                _uiState.value = DetailPemilikUiState.Error
            }
        }
    }

    fun updatePemilik(idPemilik: String, pemilik: Pemilik) {
        viewModelScope.launch {
            _uiState.value = DetailPemilikUiState.Loading
            try {
                repository.updatePemilik(idPemilik, pemilik)
                _uiState.value = DetailPemilikUiState.Success(pemilik)
            } catch (e: Exception) {
                // Log error
                Log.e("DetailPemilikViewModel", "Error updating: ${e.message}")
                _uiState.value = DetailPemilikUiState.Error
            }
        }
    }
}
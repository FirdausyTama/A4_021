package com.example.propertiapp.ui.viewmodel.jenisproperti

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.propertiapp.model.Jenis
import com.example.propertiapp.repository.JenisRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class DetailJenisUiState {
    object Loading : DetailJenisUiState()
    data class Success(val jenis: Jenis) : DetailJenisUiState()
    object Error : DetailJenisUiState()
}

class DetailJenisViewModel(private val repository: JenisRepository) : ViewModel() {

    private val _uiState = MutableStateFlow<DetailJenisUiState>(DetailJenisUiState.Loading)
    val uiState: StateFlow<DetailJenisUiState> = _uiState.asStateFlow()

    fun getJenisById(idJenis: Int) {
        viewModelScope.launch {
            _uiState.value = DetailJenisUiState.Loading
            try {
                val jenis = repository.getJenisById(idJenis)
                // Log untuk debugging
                Log.d("DetailJenisViewModel", "Jenis: $jenis")
                _uiState.value = DetailJenisUiState.Success(jenis)
            } catch (e: Exception) {
                // Log error
                Log.e("DetailJenisViewModel", "Error: ${e.message}")
                _uiState.value = DetailJenisUiState.Error
            }
        }
    }

    fun updateJenis(idJenis: Int, jenis: Jenis) {
        viewModelScope.launch {
            _uiState.value = DetailJenisUiState.Loading
            try {
                repository.updateJenis(idJenis, jenis)
                _uiState.value = DetailJenisUiState.Success(jenis)
            } catch (e: Exception) {
                // Log error
                Log.e("DetailJenisViewModel", "Error updating: ${e.message}")
                _uiState.value = DetailJenisUiState.Error
            }
        }
    }
}
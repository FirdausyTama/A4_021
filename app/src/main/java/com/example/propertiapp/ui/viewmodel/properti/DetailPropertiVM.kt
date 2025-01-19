package com.example.propertiapp.ui.viewmodel.properti

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.propertiapp.model.Properti
import com.example.propertiapp.repository.PropertiRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class DetailPropertiUiState {
    object Loading : DetailPropertiUiState()
    data class Success(val properti: Properti) : DetailPropertiUiState()
    object Error : DetailPropertiUiState()
}

class DetailPropertiViewModel(private val repository: PropertiRepository) : ViewModel() {

    private val _uiState = MutableStateFlow<DetailPropertiUiState>(DetailPropertiUiState.Loading)
    val uiState: StateFlow<DetailPropertiUiState> = _uiState.asStateFlow()

    fun getPropertiById(idProperti: String) {
        viewModelScope.launch {
            _uiState.value = DetailPropertiUiState.Loading
            try {
                val properti = repository.getPropertiById(idProperti)
                // Tambahkan log untuk debugging
                Log.d("DetailPropertiViewModel", "Properti: $properti")
                _uiState.value = DetailPropertiUiState.Success(properti)
            } catch (e: Exception) {
                // Tambahkan log error
                Log.e("DetailPropertiViewModel", "Error: ${e.message}")
                _uiState.value = DetailPropertiUiState.Error
            }
        }
    }

    fun updateProperti(idProperti: String, properti: Properti) {
        viewModelScope.launch {
            _uiState.value = DetailPropertiUiState.Loading
            try {
                repository.updateProperti(idProperti, properti)
                _uiState.value = DetailPropertiUiState.Success(properti)
            } catch (e: Exception) {
                _uiState.value = DetailPropertiUiState.Error
            }
        }
    }
}
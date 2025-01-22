package com.example.propertiapp.ui.viewmodel.manajer

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.propertiapp.model.Manajer
import com.example.propertiapp.repository.ManajerRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class DetailManajerUiState {
    object Loading : DetailManajerUiState()
    data class Success(val manajer: Manajer) : DetailManajerUiState()
    object Error : DetailManajerUiState()
}

class DetailManajerVM(private val repository: ManajerRepository) : ViewModel() {

    private val _uiState = MutableStateFlow<DetailManajerUiState>(DetailManajerUiState.Loading)
    val uiState: StateFlow<DetailManajerUiState> = _uiState.asStateFlow()

    fun getManajerById(idManajer: String) {
        viewModelScope.launch {
            _uiState.value = DetailManajerUiState.Loading
            try {
                val manajer = repository.getManajerById(idManajer)
                // Log untuk debugging
                Log.d("DetailManajerViewModel", "Manajer: $manajer")
                _uiState.value = DetailManajerUiState.Success(manajer)
            } catch (e: Exception) {
                // Log error
                Log.e("DetailManajerViewModel", "Error: ${e.message}")
                _uiState.value = DetailManajerUiState.Error
            }
        }
    }

    fun updateManajer(idManajer: String, manajer: Manajer) {
        viewModelScope.launch {
            _uiState.value = DetailManajerUiState.Loading
            try {
                repository.updateManajer(idManajer, manajer)
                _uiState.value = DetailManajerUiState.Success(manajer)
            } catch (e: Exception) {
                // Log error
                Log.e("DetailManajerViewModel", "Error updating: ${e.message}")
                _uiState.value = DetailManajerUiState.Error
            }
        }
    }
}
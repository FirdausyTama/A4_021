package com.example.propertiapp.ui.viewmodel.pemilik

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.propertiapp.model.Pemilik
import com.example.propertiapp.repository.PemilikRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class UpdateUiEvent(
    val idPemilik: Int = 0,
    val namaPemilik: String,
    val kontakPemilik: String,
)

sealed class UpdateUiState {
    object Idle : UpdateUiState()
    object Loading : UpdateUiState()
    data class Success(val pemilik: Pemilik) : UpdateUiState()
    data class Error(val message: String) : UpdateUiState()
}

class UpdateViewModelPemilik(
    private val pemilikRepository: PemilikRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<UpdateUiState>(UpdateUiState.Idle)
    val uiState: StateFlow<UpdateUiState> = _uiState

    private var currentFormData: UpdateUiEvent? = null

    fun loadPemilikData(idPemilik: Int) {
        _uiState.value = UpdateUiState.Loading
        viewModelScope.launch {
            try {
                val pemilik = pemilikRepository.getPemilikById(idPemilik)
                currentFormData = UpdateUiEvent(
                    idPemilik = pemilik.idPemilik,
                    namaPemilik = pemilik.namaPemilik,
                    kontakPemilik = pemilik.kontakPemilik
                )
                _uiState.value = UpdateUiState.Success(pemilik)
            } catch (e: Exception) {
                _uiState.value = UpdateUiState.Error("Failed to load pemilik data: ${e.message}")
            }
        }
    }

    fun updateUiState(event: UpdateUiEvent) {
        currentFormData = event
    }

    fun updatePemilik() {
        val formData = currentFormData
        if (formData == null) {
            _uiState.value = UpdateUiState.Error("Form data is not initialized")
            return
        }

        _uiState.value = UpdateUiState.Loading
        viewModelScope.launch {
            try {
                val pemilik = Pemilik(
                    idPemilik = formData.idPemilik,
                    namaPemilik = formData.namaPemilik,
                    kontakPemilik = formData.kontakPemilik
                )
                pemilikRepository.updatePemilik(formData.idPemilik, pemilik)
                _uiState.value = UpdateUiState.Success(pemilik)
            } catch (e: Exception) {
                _uiState.value = UpdateUiState.Error("Failed to update pemilik: ${e.message}")
            }
        }
    }
}
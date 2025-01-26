package com.example.propertiapp.ui.viewmodel.jenisproperti

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.propertiapp.model.Jenis
import com.example.propertiapp.repository.JenisRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class UpdateUiEvent(
    val idJenis: Int,
    val namaJenis: String,
    val deskripsiJenis: String
)

sealed class UpdateUiState {
    object Idle : UpdateUiState()
    object Loading : UpdateUiState()
    data class Success(val jenis: Jenis) : UpdateUiState()
    data class Error(val message: String) : UpdateUiState()
}

class UpdateJenisVM(
    private val jenisRepository: JenisRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<UpdateUiState>(UpdateUiState.Idle)
    val uiState: StateFlow<UpdateUiState> = _uiState

    private var currentFormData: UpdateUiEvent? = null

    fun loadJenisData(idJenis: Int) {
        _uiState.value = UpdateUiState.Loading
        viewModelScope.launch {
            try {
                val jenis = jenisRepository.getJenisById(idJenis)
                currentFormData = UpdateUiEvent(
                    idJenis = jenis.idJenis,
                    namaJenis = jenis.namaJenis,
                    deskripsiJenis = jenis.deskripsiJenis
                )
                _uiState.value = UpdateUiState.Success(jenis)
            } catch (e: Exception) {
                _uiState.value = UpdateUiState.Error("Failed to load jenis data: ${e.message}")
            }
        }
    }

    fun updateUiState(event: UpdateUiEvent) {
        currentFormData = event
    }

    fun updateJenis() {
        val formData = currentFormData
        if (formData == null) {
            _uiState.value = UpdateUiState.Error("Form data is not initialized")
            return
        }

        _uiState.value = UpdateUiState.Loading
        viewModelScope.launch {
            try {
                val jenis = Jenis(
                    idJenis = formData.idJenis,
                    namaJenis = formData.namaJenis,
                    deskripsiJenis = formData.deskripsiJenis
                )
                jenisRepository.updateJenis(formData.idJenis, jenis)
                _uiState.value = UpdateUiState.Success(jenis)
            } catch (e: Exception) {
                _uiState.value = UpdateUiState.Error("Failed to update jenis: ${e.message}")
            }
        }
    }
}
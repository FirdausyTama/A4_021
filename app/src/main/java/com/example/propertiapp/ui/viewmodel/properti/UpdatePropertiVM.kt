package com.example.propertiapp.ui.viewmodel.properti

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.propertiapp.model.Properti
import com.example.propertiapp.repository.PropertiRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class UpdateUiEvent(
    val idProperti: Int = 0,
    val namaProperti: String,
    val deskripsiProperti: String,
    val lokasi: String,
    val harga: String,
    val statusProperti: String,
    val idPemilik: String,
    val idManajer: String,
    val idJenis: String
)

sealed class UpdateUiState {
    object Idle : UpdateUiState()
    object Loading : UpdateUiState()
    data class Success(val properti: Properti) : UpdateUiState()
    data class Error(val message: String) : UpdateUiState()
}

class UpdatePropertiVM(
    private val propertiRepository: PropertiRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<UpdateUiState>(UpdateUiState.Idle)
    val uiState: StateFlow<UpdateUiState> = _uiState

    private var currentFormData: UpdateUiEvent? = null

    fun loadPropertiData(idProperti: Int) {
        _uiState.value = UpdateUiState.Loading
        viewModelScope.launch {
            try {
                val properti = propertiRepository.getPropertiById(idProperti)
                currentFormData = UpdateUiEvent(
                    idProperti = properti.idProperti,
                    namaProperti = properti.namaProperti,
                    deskripsiProperti = properti.deskripsiProperti,
                    lokasi = properti.lokasi,
                    harga = properti.harga,
                    statusProperti = properti.statusProperti,
                    idJenis = properti.idJenis,
                    idPemilik = properti.idPemilik,
                    idManajer = properti.idManajer
                )
                _uiState.value = UpdateUiState.Success(properti)
            } catch (e: Exception) {
                _uiState.value = UpdateUiState.Error("Failed to load properti data: ${e.message}")
            }
        }
    }

    fun updateUiState(event: UpdateUiEvent) {
        currentFormData = event
    }

    fun updateProperti() {
        val formData = currentFormData
        if (formData == null) {
            _uiState.value = UpdateUiState.Error("Form data is not initialized")
            return
        }

        _uiState.value = UpdateUiState.Loading
        viewModelScope.launch {
            try {
                val properti = Properti(
                    idProperti = formData.idProperti,
                    namaProperti = formData.namaProperti,
                    deskripsiProperti = formData.deskripsiProperti,
                    lokasi = formData.lokasi,
                    harga = formData.harga,
                    statusProperti = formData.statusProperti,
                    idJenis = formData.idJenis,
                    idPemilik = formData.idPemilik,
                    idManajer = formData.idManajer
                )
                propertiRepository.updateProperti(formData.idProperti, properti)
                _uiState.value = UpdateUiState.Success(properti)
            } catch (e: Exception) {
                _uiState.value = UpdateUiState.Error("Failed to update properti: ${e.message}")
            }
        }
    }
}
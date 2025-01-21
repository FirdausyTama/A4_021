package com.example.propertiapp.ui.viewmodel.properti

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.propertiapp.model.Properti
import com.example.propertiapp.repository.PropertiRepository
import kotlinx.coroutines.launch

class UpdatePropertiViewModel(private val propertiRepository: PropertiRepository) : ViewModel() {
    var uiState by mutableStateOf(UpdatePropertiUiState())
        private set

    fun updateUiState(updateUiEvent: UpdatePropertiUiEvent) {
        uiState = UpdatePropertiUiState(updateUiEvent = updateUiEvent)
    }

    fun updateProperti(idProperti: String) {
        viewModelScope.launch {
            try {
                propertiRepository.updateProperti(idProperti, uiState.updateUiEvent.toProperti())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun getPropertiById(idProperti: String) {
        viewModelScope.launch {
            try {
                val result = propertiRepository.getPropertiById(idProperti)
                uiState = result.toUpdatePropertiUiState()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

data class UpdatePropertiUiState(
    val updateUiEvent: UpdatePropertiUiEvent = UpdatePropertiUiEvent()
)

data class UpdatePropertiUiEvent(
    val idProperti: String = "",
    val namaProperti: String = "",
    val deskripsiProperti: String = "",
    val lokasi: String = "",
    val harga: String = "",
    val statusProperti: String = "",
    val idJenis: String = "",
    val idPemilik: String = "",
    val idManajer: String = ""
)

fun UpdatePropertiUiEvent.toProperti(): Properti = Properti(
    idProperti = idProperti,
    namaProperti = namaProperti,
    deskripsiProperti = deskripsiProperti,
    lokasi = lokasi,
    harga = harga,
    statusProperti = statusProperti,
    idJenis = idJenis,
    idPemilik = idPemilik,
    idManajer = idManajer
)

fun Properti.toUpdatePropertiUiState(): UpdatePropertiUiState = UpdatePropertiUiState(
    updateUiEvent = toUpdatePropertiUiEvent()
)

fun Properti.toUpdatePropertiUiEvent(): UpdatePropertiUiEvent = UpdatePropertiUiEvent(
    idProperti = idProperti,
    namaProperti = namaProperti,
    deskripsiProperti = deskripsiProperti,
    lokasi = lokasi,
    harga = harga,
    statusProperti = statusProperti,
    idJenis = idJenis,
    idPemilik = idPemilik,
    idManajer = idManajer
)

package com.example.propertiapp.ui.viewmodel.properti

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.propertiapp.model.Properti
import com.example.propertiapp.repository.PropertiRepository
import kotlinx.coroutines.launch

class InsertViewModel(private val propertiRepository: PropertiRepository) : ViewModel() {
    var uiState by mutableStateOf(InsertUiState())
        private set

    fun updateInsertPropertiState(insertUiEvent: InsertUiEvent) {
        uiState = InsertUiState(insertUiEvent = insertUiEvent)
    }

    suspend fun insertProperti() {
        viewModelScope.launch {
            try {
                propertiRepository.insertProperti(uiState.insertUiEvent.toProperti())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

data class InsertUiState(
    val insertUiEvent: InsertUiEvent = InsertUiEvent()
)

data class InsertUiEvent(
    val idProperti: Int = 0,
    val namaProperti: String = "",
    val deskripsiProperti: String = "",
    val lokasi: String = "",
    val harga: String = "",
    val statusProperti: String = "",
    val idPemilik: String = "",
    val idManajer: String = "",
    val idJenis: String = ""
)

fun InsertUiEvent.toProperti(): Properti = Properti(
    idProperti = idProperti,
    namaProperti = namaProperti,
    deskripsiProperti = deskripsiProperti,
    lokasi = lokasi,
    harga = harga,
    statusProperti = statusProperti,
    idPemilik = idPemilik,
    idManajer = idManajer,
    idJenis = idJenis
)

fun Properti.toUiStateProperti(): InsertUiState = InsertUiState(
    insertUiEvent = toInsertUiEvent()
)

fun Properti.toInsertUiEvent(): InsertUiEvent = InsertUiEvent(
    idProperti = idProperti,
    namaProperti = namaProperti,
    deskripsiProperti = deskripsiProperti,
    lokasi = lokasi,
    harga = harga,
    statusProperti = statusProperti,
    idPemilik = idPemilik,
    idManajer = idManajer,
    idJenis = idJenis
)
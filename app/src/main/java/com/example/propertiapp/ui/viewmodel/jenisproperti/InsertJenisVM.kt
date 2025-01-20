package com.example.propertiapp.ui.viewmodel.jenisproperti

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.propertiapp.model.Jenis
import com.example.propertiapp.repository.JenisRepository
import kotlinx.coroutines.launch

class InsertJenisViewModel(private val jenisRepository: JenisRepository) : ViewModel() {
    var uiState by mutableStateOf(InsertJenisUiState())
        private set

    fun updateInsertJenisState(insertUiEvent: InsertJenisUiEvent) {
        uiState = InsertJenisUiState(insertUiEvent = insertUiEvent)
    }

    suspend fun insertJenis() {
        viewModelScope.launch {
            try {
                jenisRepository.insertJenis(uiState.insertUiEvent.toJenis())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

data class InsertJenisUiState(
    val insertUiEvent: InsertJenisUiEvent = InsertJenisUiEvent()
)

data class InsertJenisUiEvent(
    val idJenis: String = "",
    val namaJenis: String = "",
    val deskripsiJenis: String = ""
)

fun InsertJenisUiEvent.toJenis(): Jenis = Jenis(
    idJenis = idJenis,
    namaJenis = namaJenis,
    deskripsiJenis = deskripsiJenis
)

fun Jenis.toUiStateJenis(): InsertJenisUiState = InsertJenisUiState(
    insertUiEvent = toInsertJenisUiEvent()
)

fun Jenis.toInsertJenisUiEvent(): InsertJenisUiEvent = InsertJenisUiEvent(
    idJenis = idJenis,
    namaJenis = namaJenis,
    deskripsiJenis = deskripsiJenis
)
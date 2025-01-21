package com.example.propertiapp.ui.viewmodel.pemilik

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.propertiapp.model.Pemilik
import com.example.propertiapp.repository.PemilikRepository
import kotlinx.coroutines.launch

class InsertPemilikViewModel(private val pemilikRepository: PemilikRepository) : ViewModel() {
    var uiState by mutableStateOf(InsertPemilikUiState())
        private set

    fun updateInsertPemilikState(insertUiEvent: InsertPemilikUiEvent) {
        uiState = InsertPemilikUiState(insertUiEvent = insertUiEvent)
    }

    suspend fun insertPemilik() {
        viewModelScope.launch {
            try {
                pemilikRepository.insertPemilik(uiState.insertUiEvent.toPemilik())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

data class InsertPemilikUiState(
    val insertUiEvent: InsertPemilikUiEvent = InsertPemilikUiEvent()
)

data class InsertPemilikUiEvent(
    val idPemilik: String = "",
    val namaPemilik: String = "",
    val kontakPemilik: String = ""
)

fun InsertPemilikUiEvent.toPemilik(): Pemilik = Pemilik(
    idPemilik = idPemilik,
    namaPemilik = namaPemilik,
    kontakPemilik = kontakPemilik
)

fun Pemilik.toUiStatePemilik(): InsertPemilikUiState = InsertPemilikUiState(
    insertUiEvent = toInsertPemilikUiEvent()
)

fun Pemilik.toInsertPemilikUiEvent(): InsertPemilikUiEvent = InsertPemilikUiEvent(
    idPemilik = idPemilik,
    namaPemilik = namaPemilik,
    kontakPemilik = kontakPemilik
)
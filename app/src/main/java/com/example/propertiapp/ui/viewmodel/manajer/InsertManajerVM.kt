package com.example.propertiapp.ui.viewmodel.manajer

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.propertiapp.model.Manajer
import com.example.propertiapp.repository.ManajerRepository
import kotlinx.coroutines.launch

class InsertManajerVM(private val manajerRepository: ManajerRepository) : ViewModel() {
    var uiState by mutableStateOf(InsertManajerUiState())
        private set

    fun updateInsertManajerState(insertUiEvent: InsertManajerUiEvent) {
        uiState = InsertManajerUiState(insertUiEvent = insertUiEvent)
    }

    suspend fun insertManajer() {
        viewModelScope.launch {
            try {
                manajerRepository.insertManajer(uiState.insertUiEvent.toManajer())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

data class InsertManajerUiState(
    val insertUiEvent: InsertManajerUiEvent = InsertManajerUiEvent()
)

data class InsertManajerUiEvent(
    val idManajer: String = "",
    val namaManajer: String = "",
    val kontakManajer: String = ""
)

fun InsertManajerUiEvent.toManajer(): Manajer = Manajer(
    idManajer = idManajer,
    namaManajer = namaManajer,
    kontakManajer = kontakManajer
)

fun Manajer.toUiStateManajer(): InsertManajerUiState = InsertManajerUiState(
    insertUiEvent = toInsertManajerUiEvent()
)

fun Manajer.toInsertManajerUiEvent(): InsertManajerUiEvent = InsertManajerUiEvent(
    idManajer = idManajer,
    namaManajer = namaManajer,
    kontakManajer = kontakManajer
)
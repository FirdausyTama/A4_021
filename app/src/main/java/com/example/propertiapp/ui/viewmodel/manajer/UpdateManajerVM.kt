package com.example.propertiapp.ui.viewmodel.manajer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.propertiapp.model.Manajer
import com.example.propertiapp.repository.ManajerRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class UpdateUiEvent(
    val idManajer: Int = 0,
    val namaManajer: String = "",
    val kontakManajer: String = ""
)

sealed class UpdateUiState {
    object Idle : UpdateUiState()
    object Loading : UpdateUiState()
    data class Success(val manajer: Manajer) : UpdateUiState()
    data class Error(val message: String) : UpdateUiState()
}

class UpdateManajerVM(
    private val manajerRepository: ManajerRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<UpdateUiState>(UpdateUiState.Idle)
    val uiState: StateFlow<UpdateUiState> = _uiState

    private var currentFormData: UpdateUiEvent? = null

    fun loadManajerData(idManajer: Int) {
        _uiState.value = UpdateUiState.Loading
        viewModelScope.launch {
            try {
                val manajer = manajerRepository.getManajerById(idManajer)
                currentFormData = UpdateUiEvent(
                    idManajer = manajer.idManajer,
                    namaManajer = manajer.namaManajer,
                    kontakManajer = manajer.kontakManajer
                )
                _uiState.value = UpdateUiState.Success(manajer)
            } catch (e: Exception) {
                _uiState.value = UpdateUiState.Error("Failed to load manajer data: ${e.message}")
            }
        }
    }

    fun updateUiState(event: UpdateUiEvent) {
        currentFormData = event
    }

    fun updateManajer() {
        val formData = currentFormData
        if (formData == null) {
            _uiState.value = UpdateUiState.Error("Form data is not initialized")
            return
        }

        _uiState.value = UpdateUiState.Loading
        viewModelScope.launch {
            try {
                val manajer = Manajer(
                    idManajer = formData.idManajer,
                    namaManajer = formData.namaManajer,
                    kontakManajer = formData.kontakManajer
                )
                manajerRepository.updateManajer(formData.idManajer, manajer)
                _uiState.value = UpdateUiState.Success(manajer)
            } catch (e: Exception) {
                _uiState.value = UpdateUiState.Error("Failed to update manajer: ${e.message}")
            }
        }
    }
}

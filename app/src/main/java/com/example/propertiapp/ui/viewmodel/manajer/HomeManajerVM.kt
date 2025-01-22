package com.example.propertiapp.ui.viewmodel.manajer

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.propertiapp.model.Manajer
import com.example.propertiapp.repository.ManajerRepository
import kotlinx.coroutines.launch
import okio.IOException

sealed class ManajerUiState {
    data class Success(val manajer: List<Manajer>): ManajerUiState()
    object Error: ManajerUiState()
    object Loading: ManajerUiState()
}

class HomeManajerVM(private val manajerRepo: ManajerRepository): ViewModel() {
    var manajerUiState: ManajerUiState by mutableStateOf(ManajerUiState.Loading)
        private set

    init {
        getManajer()
    }

    fun getManajer() {
        viewModelScope.launch {
            manajerUiState = ManajerUiState.Loading
            manajerUiState = try {
                ManajerUiState.Success(manajerRepo.getManajer())
            } catch (e: IOException) {
                ManajerUiState.Error
            } catch (e: HttpException) {
                ManajerUiState.Error
            }
        }
    }

    fun deleteManajer(idManajer: String) {
        viewModelScope.launch {
            try {
                manajerRepo.deleteManajer(idManajer)
            } catch (e: IOException) {
                ManajerUiState.Error
            } catch (e: HttpException) {
                ManajerUiState.Error
            }
        }
    }
}
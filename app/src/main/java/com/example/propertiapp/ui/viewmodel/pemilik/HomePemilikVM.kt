package com.example.propertiapp.ui.viewmodel.pemilik

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.propertiapp.model.Pemilik
import com.example.propertiapp.repository.PemilikRepository
import kotlinx.coroutines.launch
import okio.IOException

sealed class PemilikUiState {
    data class Success(val pemilik: List<Pemilik>): PemilikUiState()
    object Error: PemilikUiState()
    object Loading: PemilikUiState()
}

class HomePemilikVM(private val pemilikRepo: PemilikRepository): ViewModel() {
    var pemilikUiState: PemilikUiState by mutableStateOf(PemilikUiState.Loading)
        private set

    init {
        getPemilik()
    }

    fun getPemilik() {
        viewModelScope.launch {
            pemilikUiState = PemilikUiState.Loading
            pemilikUiState = try {
                PemilikUiState.Success(pemilikRepo.getPemilik())
            } catch (e: IOException) {
                PemilikUiState.Error
            } catch (e: HttpException) {
                PemilikUiState.Error
            }
        }
    }

    fun deletePemilik(idPemilik: String) {
        viewModelScope.launch {
            try {
                pemilikRepo.deletePemilik(idPemilik)
            } catch (e: IOException) {
                PemilikUiState.Error
            } catch (e: HttpException) {
                PemilikUiState.Error
            }
        }
    }
}
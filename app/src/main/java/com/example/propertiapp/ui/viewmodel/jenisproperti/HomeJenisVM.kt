package com.example.propertiapp.ui.viewmodel.jenisproperti

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.propertiapp.model.Jenis
import com.example.propertiapp.repository.JenisRepository
import kotlinx.coroutines.launch
import okio.IOException

sealed class JenisUiState {
    data class Success(val jenis: List<Jenis>): JenisUiState()
    object Error: JenisUiState()
    object Loading: JenisUiState()
}

class JenisViewModel(private val jenisRepo: JenisRepository): ViewModel() {
    var jenisUiState: JenisUiState by mutableStateOf(JenisUiState.Loading)
        private set

    init {
        getJenis()
    }

    fun getJenis() {
        viewModelScope.launch {
            jenisUiState = JenisUiState.Loading
            jenisUiState = try {
                JenisUiState.Success(jenisRepo.getJenis())
            } catch (e: IOException) {
                JenisUiState.Error
            } catch (e: HttpException) {
                JenisUiState.Error
            }
        }
    }

    fun deleteJenis(idJenis: String) {
        viewModelScope.launch {
            try {
                jenisRepo.deleteJenis(idJenis)
            } catch (e: IOException) {
                JenisUiState.Error
            } catch (e: HttpException) {
                JenisUiState.Error
            }
        }
    }
}
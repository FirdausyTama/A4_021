package com.example.propertiapp.ui.viewmodel.properti

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.propertiapp.model.Properti
import com.example.propertiapp.repository.PropertiRepository
import kotlinx.coroutines.launch
import okio.IOException

sealed class HomeUiState {
    data class Success(val properti: List<Properti>): HomeUiState()
    object Error: HomeUiState()
    object Loading: HomeUiState()
}

class HomeViewModel(private val propertiRepo: PropertiRepository): ViewModel() {
    var propertiUiState: HomeUiState by mutableStateOf(HomeUiState.Loading)
        private set

    init {
        getProperti()
    }

    fun getProperti() {
        viewModelScope.launch {
            propertiUiState = HomeUiState.Loading
            propertiUiState = try {
                HomeUiState.Success(propertiRepo.getProperti())
            } catch (e: IOException) {
                HomeUiState.Error
            } catch (e: HttpException) {
                HomeUiState.Error
            }
        }
    }

    fun deleteProperti(idProperti: Int) {
        viewModelScope.launch {
            try {
                propertiRepo.deleteProperti(idProperti)
            } catch (e: IOException) {
                HomeUiState.Error
            } catch (e: HttpException) {
                HomeUiState.Error
            }
        }
    }
}
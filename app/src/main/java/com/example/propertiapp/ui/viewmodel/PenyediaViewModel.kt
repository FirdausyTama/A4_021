package com.example.propertiapp.ui.viewmodel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.propertiapp.PropertiApplication
import com.example.propertiapp.ui.viewmodel.properti.HomeViewModel

object PenyediaViewModel {
    val Factory = viewModelFactory {
        initializer { HomeViewModel(aplikasiKontak().container.propertiRepository) }
    }
}

fun CreationExtras.aplikasiKontak():PropertiApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]as PropertiApplication)
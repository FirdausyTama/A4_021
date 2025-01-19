package com.example.propertiapp.ui.viewmodel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.propertiapp.PropertiApplication
import com.example.propertiapp.ui.viewmodel.properti.HomeViewModel
import com.example.propertiapp.ui.viewmodel.properti.InsertViewModel

object PenyediaViewModel {
    val Factory = viewModelFactory {
        initializer { HomeViewModel(aplikasiProperti().container.propertiRepository) }
        initializer { InsertViewModel(aplikasiProperti().container.propertiRepository) }
    }
}

fun CreationExtras.aplikasiProperti():PropertiApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]as PropertiApplication)
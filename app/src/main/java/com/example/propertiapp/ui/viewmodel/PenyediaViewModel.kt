package com.example.propertiapp.ui.viewmodel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.propertiapp.PropertiApplication
import com.example.propertiapp.ui.viewmodel.jenisproperti.DetailJenisViewModel
import com.example.propertiapp.ui.viewmodel.jenisproperti.InsertJenisViewModel
import com.example.propertiapp.ui.viewmodel.jenisproperti.JenisViewModel
import com.example.propertiapp.ui.viewmodel.properti.DetailPropertiViewModel
import com.example.propertiapp.ui.viewmodel.properti.HomeViewModel
import com.example.propertiapp.ui.viewmodel.properti.InsertViewModel

object PenyediaViewModel {
    val Factory = viewModelFactory {
        initializer { HomeViewModel(aplikasiProperti().container.propertiRepository) }
        initializer { InsertViewModel(aplikasiProperti().container.propertiRepository) }
        initializer { DetailPropertiViewModel(aplikasiProperti().container.propertiRepository) }
        initializer { JenisViewModel(aplikasiProperti().container.jenisRepository) }
        initializer { InsertJenisViewModel(aplikasiProperti().container.jenisRepository) }
        initializer { DetailJenisViewModel(aplikasiProperti().container.jenisRepository) }
    }
}

fun CreationExtras.aplikasiProperti():PropertiApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]as PropertiApplication)
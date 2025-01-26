package com.example.propertiapp.ui.viewmodel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.propertiapp.PropertiApplication
import com.example.propertiapp.ui.viewmodel.jenisproperti.DetailJenisViewModel
import com.example.propertiapp.ui.viewmodel.jenisproperti.InsertJenisViewModel
import com.example.propertiapp.ui.viewmodel.jenisproperti.JenisViewModel
import com.example.propertiapp.ui.viewmodel.jenisproperti.UpdateJenisVM
import com.example.propertiapp.ui.viewmodel.manajer.DetailManajerVM
import com.example.propertiapp.ui.viewmodel.manajer.HomeManajerVM
import com.example.propertiapp.ui.viewmodel.manajer.InsertManajerVM
import com.example.propertiapp.ui.viewmodel.manajer.UpdateManajerVM
import com.example.propertiapp.ui.viewmodel.pemilik.DetailPemilikViewModel
import com.example.propertiapp.ui.viewmodel.pemilik.HomePemilikVM
import com.example.propertiapp.ui.viewmodel.pemilik.InsertPemilikViewModel
import com.example.propertiapp.ui.viewmodel.pemilik.UpdateViewModelPemilik
import com.example.propertiapp.ui.viewmodel.properti.DetailPropertiViewModel
import com.example.propertiapp.ui.viewmodel.properti.HomeViewModel
import com.example.propertiapp.ui.viewmodel.properti.InsertViewModel
import com.example.propertiapp.ui.viewmodel.properti.UpdatePropertiVM

object PenyediaViewModel {
    val Factory = viewModelFactory {
        initializer { HomeViewModel(aplikasiProperti().container.propertiRepository) }
        initializer { InsertViewModel(aplikasiProperti().container.propertiRepository) }
        initializer { DetailPropertiViewModel(aplikasiProperti().container.propertiRepository) }
        initializer { UpdatePropertiVM(aplikasiProperti().container.propertiRepository) }

        //jenis
        initializer { JenisViewModel(aplikasiProperti().container.jenisRepository) }
        initializer { InsertJenisViewModel(aplikasiProperti().container.jenisRepository) }
        initializer { DetailJenisViewModel(aplikasiProperti().container.jenisRepository) }
        initializer { UpdateJenisVM(aplikasiProperti().container.jenisRepository) }

        //Pemilik
        initializer { HomePemilikVM(aplikasiProperti().container.pemilikRepository) }
        initializer { InsertPemilikViewModel(aplikasiProperti().container.pemilikRepository) }
        initializer { DetailPemilikViewModel(aplikasiProperti().container.pemilikRepository) }
        initializer { UpdateViewModelPemilik(aplikasiProperti().container.pemilikRepository) }

        //Manajer
        initializer { HomeManajerVM(aplikasiProperti().container.manajerRepository) }
        initializer { InsertManajerVM(aplikasiProperti().container.manajerRepository) }
        initializer { DetailManajerVM(aplikasiProperti().container.manajerRepository) }
        initializer { UpdateManajerVM(aplikasiProperti().container.manajerRepository) }
    }
}

fun CreationExtras.aplikasiProperti():PropertiApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]as PropertiApplication)
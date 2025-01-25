package com.example.propertiapp.model

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.propertiapp.ui.viewmodel.PenyediaViewModel
import com.example.propertiapp.ui.viewmodel.jenisproperti.JenisUiState
import com.example.propertiapp.ui.viewmodel.jenisproperti.JenisViewModel
import com.example.propertiapp.ui.viewmodel.manajer.HomeManajerVM
import com.example.propertiapp.ui.viewmodel.manajer.ManajerUiState
import com.example.propertiapp.ui.viewmodel.pemilik.HomePemilikVM
import com.example.propertiapp.ui.viewmodel.pemilik.PemilikUiState

object JenisMenu {
    @Composable
    fun jenisOptions(
        viewModel: JenisViewModel = viewModel(factory = PenyediaViewModel.Factory)
    ): List<String> {
        val jenisState = viewModel.jenisUiState
        return when (jenisState) {
            is JenisUiState.Success -> jenisState.jenis.map { it.namaJenis }
            else -> emptyList()
        }
    }

    @Composable
    fun pemilikOptions(
        viewModel: HomePemilikVM = viewModel(factory = PenyediaViewModel.Factory)
    ): List<String> {
        val pemilikState = viewModel.pemilikUiState
        return when (pemilikState) {
            is PemilikUiState.Success -> pemilikState.pemilik.map { it.namaPemilik }
            else -> emptyList()
        }
    }

    @Composable
    fun manajerOptions(
        viewModel: HomeManajerVM = viewModel(factory = PenyediaViewModel.Factory)
    ): List<String> {
        val manajerState = viewModel.manajerUiState
        return when (manajerState) {
            is ManajerUiState.Success -> manajerState.manajer.map { it.namaManajer }
            else -> emptyList()
        }
    }
}
package com.example.propertiapp.ui.view.jenisproperti

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.propertiapp.navigasi.DestinasiNavigasi
import com.example.propertiapp.ui.costumewidget.CostumeTopAppBar
import com.example.propertiapp.ui.viewmodel.PenyediaViewModel
import com.example.propertiapp.ui.viewmodel.jenisproperti.InsertJenisUiEvent
import com.example.propertiapp.ui.viewmodel.jenisproperti.InsertJenisUiState
import com.example.propertiapp.ui.viewmodel.jenisproperti.InsertJenisViewModel
import kotlinx.coroutines.launch

object DestinasiEntryJenis : DestinasiNavigasi {
    override val route = "jenis_entry"
    override val titleRes = "Entry Jenis"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryJenisScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertJenisViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val uiState = viewModel.uiState
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiEntryJenis.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ) { innerPadding ->
        EntryJenisBody(
            insertUiState = uiState,
            onJenisValueChange = viewModel::updateInsertJenisState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.insertJenis()
                    navigateBack()
                }
            },
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )
    }
}

@Composable
fun EntryJenisBody(
    insertUiState: InsertJenisUiState,
    onJenisValueChange: (InsertJenisUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ) {
        FormInputJenis(
            insertUiEvent = insertUiState.insertUiEvent,
            onValueChange = onJenisValueChange,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = onSaveClick,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Simpan")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormInputJenis(
    insertUiEvent: InsertJenisUiEvent,
    modifier: Modifier = Modifier,
    onValueChange: (InsertJenisUiEvent) -> Unit = {},
    enabled: Boolean = true
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = insertUiEvent.namaJenis,
            onValueChange = { onValueChange(insertUiEvent.copy(namaJenis = it)) },
            label = { Text("Nama Jenis") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            placeholder = { Text("Masukkan Nama Jenis") },
            singleLine = true
        )

        OutlinedTextField(
            value = insertUiEvent.deskripsiJenis,
            onValueChange = { onValueChange(insertUiEvent.copy(deskripsiJenis = it)) },
            label = { Text("Deskripsi Jenis") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            placeholder = { Text("Masukkan Deskripsi Jenis") },
            singleLine = false
        )
    }
}
package com.example.propertiapp.ui.view.properti

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.propertiapp.navigasi.DestinasiNavigasi
import com.example.propertiapp.ui.costumewidget.CostumeTopAppBar
import com.example.propertiapp.ui.viewmodel.properti.InsertUiEvent
import com.example.propertiapp.ui.viewmodel.properti.InsertViewModel
import com.example.propertiapp.ui.viewmodel.PenyediaViewModel
import com.example.propertiapp.ui.viewmodel.properti.InsertUiState
import kotlinx.coroutines.launch

object DestinasiEntry : DestinasiNavigasi {
    override val route = "properti_entry"
    override val titleRes = "Entry Properti"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryPropertiScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val uiState = viewModel.uiState
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiEntry.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ) { innerPadding ->
        EntryBody(
            insertUiState = uiState,
            onPropertiValueChange = viewModel::updateInsertPropertiState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.insertProperti()
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
fun EntryBody(
    insertUiState: InsertUiState,
    onPropertiValueChange: (InsertUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ) {
        FormInput(
            insertUiEvent = insertUiState.insertUiEvent,
            onValueChange = onPropertiValueChange,
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
fun FormInput(
    insertUiEvent: InsertUiEvent,
    modifier: Modifier = Modifier,
    onValueChange: (InsertUiEvent) -> Unit = {},
    enabled: Boolean = true
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // ID Properti field
        OutlinedTextField(
            value = insertUiEvent.idProperti,
            onValueChange = { onValueChange(insertUiEvent.copy(idProperti = it)) },
            label = { Text("ID Properti") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            placeholder = { Text("Masukkan ID Properti") },
            singleLine = true
        )

        // Existing fields remain the same
        OutlinedTextField(
            value = insertUiEvent.namaProperti,
            onValueChange = { onValueChange(insertUiEvent.copy(namaProperti = it)) },
            label = { Text("Nama Properti") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            placeholder = { Text("Masukkan Nama Properti") },
            singleLine = true
        )

        OutlinedTextField(
            value = insertUiEvent.deskripsiProperti,
            onValueChange = { onValueChange(insertUiEvent.copy(deskripsiProperti = it)) },
            label = { Text("Deskripsi Properti") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            placeholder = { Text("Masukkan Deskripsi Properti") },
            singleLine = false,
        )

        OutlinedTextField(
            value = insertUiEvent.lokasi,
            onValueChange = { onValueChange(insertUiEvent.copy(lokasi = it)) },
            label = { Text("Lokasi") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            placeholder = { Text("Masukkan Lokasi") },
            singleLine = true
        )

        OutlinedTextField(
            value = insertUiEvent.harga,
            onValueChange = { onValueChange(insertUiEvent.copy(harga = it)) },
            label = { Text("Harga") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            placeholder = { Text("Masukkan Harga") },
            singleLine = true
        )

        OutlinedTextField(
            value = insertUiEvent.statusProperti,
            onValueChange = { onValueChange(insertUiEvent.copy(statusProperti = it)) },
            label = { Text("Status Properti") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            placeholder = { Text("Masukkan Status Properti") },
            singleLine = true
        )

        OutlinedTextField(
            value = insertUiEvent.idJenis,
            onValueChange = { onValueChange(insertUiEvent.copy(idJenis = it)) },
            label = { Text("ID Jenis") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            placeholder = { Text("Masukkan ID Jenis") },
            singleLine = true
        )

        OutlinedTextField(
            value = insertUiEvent.idPemilik,
            onValueChange = { onValueChange(insertUiEvent.copy(idPemilik = it)) },
            label = { Text("ID Pemilik") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            placeholder = { Text("Masukkan ID Pemilik") },
            singleLine = true
        )

        OutlinedTextField(
            value = insertUiEvent.idManajer,
            onValueChange = { onValueChange(insertUiEvent.copy(idManajer = it)) },
            label = { Text("ID Manajer") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            placeholder = { Text("Masukkan ID Manajer") },
            singleLine = true
        )
    }
}

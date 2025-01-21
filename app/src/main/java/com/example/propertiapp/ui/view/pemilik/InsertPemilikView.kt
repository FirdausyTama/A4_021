package com.example.propertiapp.ui.view.pemilik

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
import com.example.propertiapp.ui.viewmodel.pemilik.InsertPemilikUiEvent
import com.example.propertiapp.ui.viewmodel.pemilik.InsertPemilikUiState
import com.example.propertiapp.ui.viewmodel.pemilik.InsertPemilikViewModel
import kotlinx.coroutines.launch

object DestinasiEntryPemilik : DestinasiNavigasi {
    override val route = "pemilik_entry"
    override val titleRes = "Entry Pemilik"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InsertPemilikView(
    navigateBack: () -> Unit,
    onNavigateBack: () -> Unit, // Add this parameter for specific navigation
    modifier: Modifier = Modifier,
    viewModel: InsertPemilikViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val uiState = viewModel.uiState
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiEntryPemilik.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = onNavigateBack // Use specific navigation handler
            )
        }
    ) { innerPadding ->
        EntryPemilikBody(
            insertUiState = uiState,
            onPemilikValueChange = viewModel::updateInsertPemilikState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.insertPemilik()
                    onNavigateBack() // Use specific navigation handler
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
fun EntryPemilikBody(
    insertUiState: InsertPemilikUiState,
    onPemilikValueChange: (InsertPemilikUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ) {
        FormInputPemilik(
            insertUiEvent = insertUiState.insertUiEvent,
            onValueChange = onPemilikValueChange,
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
fun FormInputPemilik(
    insertUiEvent: InsertPemilikUiEvent,
    modifier: Modifier = Modifier,
    onValueChange: (InsertPemilikUiEvent) -> Unit = {},
    enabled: Boolean = true
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
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
            value = insertUiEvent.namaPemilik,
            onValueChange = { onValueChange(insertUiEvent.copy(namaPemilik = it)) },
            label = { Text("Nama Pemilik") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            placeholder = { Text("Masukkan Nama Pemilik") },
            singleLine = true
        )

        OutlinedTextField(
            value = insertUiEvent.kontakPemilik,
            onValueChange = { onValueChange(insertUiEvent.copy(kontakPemilik = it)) },
            label = { Text("Kontak Pemilik") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            placeholder = { Text("Masukkan Nomor Telepon/Email") },
            singleLine = true
        )
    }
}
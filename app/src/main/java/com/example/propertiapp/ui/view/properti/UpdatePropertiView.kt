package com.example.propertiapp.ui.view.properti

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.propertiapp.navigasi.DestinasiNavigasi
import com.example.propertiapp.ui.costumewidget.CostumeTopAppBar
import com.example.propertiapp.ui.viewmodel.PenyediaViewModel
import com.example.propertiapp.ui.viewmodel.properti.UpdatePropertiUiEvent
import com.example.propertiapp.ui.viewmodel.properti.UpdatePropertiUiState
import com.example.propertiapp.ui.viewmodel.properti.UpdatePropertiViewModel
import kotlinx.coroutines.launch

object DestinasiEditProperti : DestinasiNavigasi {
    override val route = "properti_edit"
    override val titleRes = "Edit Properti"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditPropertiScreen(
    navigateBack: () -> Unit,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: UpdatePropertiViewModel = viewModel(factory = PenyediaViewModel.Factory),
    idProperti: String
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    LaunchedEffect(key1 = true) {
        coroutineScope.launch {
            viewModel.getPropertiById(idProperti)
        }
    }

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiEditProperti.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ) { innerPadding ->
        EditPropertiBody(
            updateUiState = viewModel.uiState,
            onPropertiValueChange = viewModel::updateUiState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.updateProperti(idProperti)
                    onNavigateBack()
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
fun EditPropertiBody(
    updateUiState: UpdatePropertiUiState,
    onPropertiValueChange: (UpdatePropertiUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ) {
        FormInputPropertiEdit(
            updateUiEvent = updateUiState.updateUiEvent,
            onValueChange = onPropertiValueChange,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = onSaveClick,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Simpan Perubahan")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormInputPropertiEdit(
    updateUiEvent: UpdatePropertiUiEvent,
    modifier: Modifier = Modifier,
    onValueChange: (UpdatePropertiUiEvent) -> Unit = {},
    enabled: Boolean = true
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = updateUiEvent.namaProperti,
            onValueChange = { onValueChange(updateUiEvent.copy(namaProperti = it)) },
            label = { Text("Nama Properti") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = updateUiEvent.deskripsiProperti,
            onValueChange = { onValueChange(updateUiEvent.copy(deskripsiProperti = it)) },
            label = { Text("Deskripsi Properti") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = updateUiEvent.lokasi,
            onValueChange = { onValueChange(updateUiEvent.copy(lokasi = it)) },
            label = { Text("Lokasi") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = updateUiEvent.harga,
            onValueChange = { onValueChange(updateUiEvent.copy(harga = it)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            label = { Text("Harga") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = updateUiEvent.statusProperti,
            onValueChange = { onValueChange(updateUiEvent.copy(statusProperti = it)) },
            label = { Text("Status Properti") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(
                value = updateUiEvent.idJenis,
                onValueChange = { onValueChange(updateUiEvent.copy(idJenis = it)) },
                label = { Text("ID Jenis") },
                modifier = Modifier.fillMaxWidth().weight(1f),
                enabled = enabled,
                singleLine = true
            )
            OutlinedTextField(
                value = updateUiEvent.idPemilik,
                onValueChange = { onValueChange(updateUiEvent.copy(idPemilik = it)) },
                label = { Text("ID Pemilik") },
                modifier = Modifier.fillMaxWidth().weight(1f),
                enabled = enabled,
                singleLine = true
            )
            OutlinedTextField(
                value = updateUiEvent.idManajer,
                onValueChange = { onValueChange(updateUiEvent.copy(idManajer = it)) },
                label = { Text("ID Manajer") },
                modifier = Modifier.fillMaxWidth().weight(1f),
                enabled = enabled,
                singleLine = true
            )
        }
    }
}

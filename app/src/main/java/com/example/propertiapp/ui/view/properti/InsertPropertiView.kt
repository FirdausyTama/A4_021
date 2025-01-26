package com.example.propertiapp.ui.view.properti

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.propertiapp.model.JenisMenu
import com.example.propertiapp.model.StatusPropertiMenu
import com.example.propertiapp.navigasi.DestinasiNavigasi
import com.example.propertiapp.ui.costumewidget.CostumeTopAppBar
import com.example.propertiapp.ui.costumewidget.DynamicSelectedTextField
import com.example.propertiapp.ui.viewmodel.properti.InsertUiEvent
import com.example.propertiapp.ui.viewmodel.properti.InsertViewModel
import com.example.propertiapp.ui.viewmodel.PenyediaViewModel
import com.example.propertiapp.ui.viewmodel.jenisproperti.JenisUiState
import com.example.propertiapp.ui.viewmodel.jenisproperti.JenisViewModel
import com.example.propertiapp.ui.viewmodel.manajer.HomeManajerVM
import com.example.propertiapp.ui.viewmodel.manajer.ManajerUiState
import com.example.propertiapp.ui.viewmodel.pemilik.HomePemilikVM
import com.example.propertiapp.ui.viewmodel.pemilik.PemilikUiState
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

    var chosenDropdown by remember {
        mutableStateOf(
            ""
        )
    }
    var checked by remember { mutableStateOf(false) }
    var listData: MutableList<String> = mutableListOf(chosenDropdown)


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
    enabled: Boolean = true,
){
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
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

        DynamicSelectedTextField(
            selectedValue = insertUiEvent.statusProperti,
            options = StatusPropertiMenu.options,
            label = "Status",
            onValueChangedEvent = { spesialis ->
                onValueChange(insertUiEvent.copy(statusProperti = spesialis))
            },
            modifier = Modifier.fillMaxWidth()
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            DynamicSelectedTextField(
                selectedValue = insertUiEvent.idJenis, // Changed from namaDokter to namaJenis
                options = JenisMenu.jenisOptions(), // Changed from NamaDokter.options() to JenisMenu.jenisOptions()
                label = "Jenis", // Updated label to match
                onValueChangedEvent = { selectedJenis -> // Changed variable name
                    onValueChange(insertUiEvent.copy(idJenis = selectedJenis)) // Updated property name
                },
                modifier = Modifier.weight(1f)
            )
            DynamicSelectedTextField(
                selectedValue = insertUiEvent.idPemilik, // Changed from namaDokter to namaJenis
                options = JenisMenu.pemilikOptions(), // Changed from NamaDokter.options() to JenisMenu.jenisOptions()
                label = "Milik", // Updated label to match
                onValueChangedEvent = { selectedJenis -> // Changed variable name
                    onValueChange(insertUiEvent.copy(idPemilik = selectedJenis)) // Updated property name
                },
                modifier = Modifier.weight(1f)
            )
        }
        DynamicSelectedTextField(
            selectedValue = insertUiEvent.idManajer, // Changed from namaDokter to namaJenis
            options = JenisMenu.manajerOptions(), // Changed from NamaDokter.options() to JenisMenu.jenisOptions()
            label = "Manajer", // Updated label to match
            onValueChangedEvent = { selectedJenis -> // Changed variable name
                onValueChange(insertUiEvent.copy(idManajer = selectedJenis)) // Updated property name
            },
        )
    }
}

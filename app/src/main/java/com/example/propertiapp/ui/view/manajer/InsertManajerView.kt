package com.example.propertiapp.ui.view.manajer

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
import com.example.propertiapp.ui.viewmodel.manajer.InsertManajerUiEvent
import com.example.propertiapp.ui.viewmodel.manajer.InsertManajerUiState
import com.example.propertiapp.ui.viewmodel.manajer.InsertManajerVM
import kotlinx.coroutines.launch

object DestinasiEntryManajer : DestinasiNavigasi {
    override val route = "manajer_entry"
    override val titleRes = "Entry Manajer"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InsertManajerView(
    navigateBack: () -> Unit,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertManajerVM = viewModel(factory = PenyediaViewModel.Factory)
) {
    val uiState = viewModel.uiState
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiEntryManajer.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = onNavigateBack
            )
        }
    ) { innerPadding ->
        EntryManajerBody(
            insertUiState = uiState,
            onManajerValueChange = viewModel::updateInsertManajerState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.insertManajer()
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
fun EntryManajerBody(
    insertUiState: InsertManajerUiState,
    onManajerValueChange: (InsertManajerUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ) {
        FormInputManajer(
            insertUiEvent = insertUiState.insertUiEvent,
            onValueChange = onManajerValueChange,
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
fun FormInputManajer(
    insertUiEvent: InsertManajerUiEvent,
    modifier: Modifier = Modifier,
    onValueChange: (InsertManajerUiEvent) -> Unit = {},
    enabled: Boolean = true
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = insertUiEvent.namaManajer,
            onValueChange = { onValueChange(insertUiEvent.copy(namaManajer = it)) },
            label = { Text("Nama Manajer") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            placeholder = { Text("Masukkan Nama Manajer") },
            singleLine = true
        )

        OutlinedTextField(
            value = insertUiEvent.kontakManajer,
            onValueChange = { onValueChange(insertUiEvent.copy(kontakManajer = it)) },
            label = { Text("Kontak Manajer") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            placeholder = { Text("Masukkan Nomor Telepon/Email") },
            singleLine = true
        )
    }
}
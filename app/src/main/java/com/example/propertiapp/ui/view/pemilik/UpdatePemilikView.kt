package com.example.propertiapp.ui.view.pemilik

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.propertiapp.navigasi.DestinasiNavigasi
import com.example.propertiapp.ui.costumewidget.CostumeTopAppBar
import com.example.propertiapp.ui.viewmodel.PenyediaViewModel
import com.example.propertiapp.ui.viewmodel.pemilik.UpdateUiEvent
import com.example.propertiapp.ui.viewmodel.pemilik.UpdateUiState
import com.example.propertiapp.ui.viewmodel.pemilik.UpdateViewModelPemilik

object DestinasiUpdatePemilik : DestinasiNavigasi {
    override val route = "update_pemilik"
    override val titleRes = "Update Pemilik"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateScreenPemilik(
    idPemilik: String,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: UpdateViewModelPemilik = viewModel(factory = PenyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(idPemilik) {
        viewModel.loadPemilikData(idPemilik)
    }

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiUpdatePemilik.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = onNavigateBack
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            when (uiState) {
                is UpdateUiState.Loading -> CircularProgressIndicator()
                is UpdateUiState.Success -> {
                    val pemilik = (uiState as UpdateUiState.Success).pemilik
                    UpdateForm(
                        idPemilik = pemilik.idPemilik,
                        namaPemilik = pemilik.namaPemilik,
                        kontakPemilik = pemilik.kontakPemilik,
                        onUpdateClick = {
                            viewModel.updateUiState(it)
                            viewModel.updatePemilik()
                            onNavigateBack()
                        }
                    )
                }
                is UpdateUiState.Error -> {
                    Text("Error: ${(uiState as UpdateUiState.Error).message}")
                }
                else -> {}
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateForm(
    idPemilik: String,
    namaPemilik: String,
    kontakPemilik: String,
    onUpdateClick: (UpdateUiEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    var idPemilik by remember { mutableStateOf(idPemilik) }
    var namaPemilik by remember { mutableStateOf(namaPemilik) }
    var kontakPemilik by remember { mutableStateOf(kontakPemilik) }

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedTextField(
            value = idPemilik,
            onValueChange = { idPemilik = it },
            label = { Text("ID Pemilik") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        OutlinedTextField(
            value = namaPemilik,
            onValueChange = { namaPemilik = it },
            label = { Text("Nama Pemilik") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        OutlinedTextField(
            value = kontakPemilik,
            onValueChange = { kontakPemilik = it },
            label = { Text("Kontak Pemilik") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Button(
            onClick = {
                onUpdateClick(
                    UpdateUiEvent(
                        idPemilik = idPemilik,
                        namaPemilik = namaPemilik,
                        kontakPemilik = kontakPemilik
                    )
                )
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Update")
        }
    }
}
package com.example.propertiapp.ui.view.jenisproperti

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
import com.example.propertiapp.ui.viewmodel.jenisproperti.UpdateJenisVM
import com.example.propertiapp.ui.viewmodel.jenisproperti.UpdateUiEvent
import com.example.propertiapp.ui.viewmodel.jenisproperti.UpdateUiState

object DestinasiUpdateJenis : DestinasiNavigasi {
    override val route = "update_jenis"
    override val titleRes = "Update Jenis"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateScreenJenis(
    idJenis: Int,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: UpdateJenisVM = viewModel(factory = PenyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(idJenis) {
        viewModel.loadJenisData(idJenis)
    }

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiUpdateJenis.titleRes,
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
                    val jenis = (uiState as UpdateUiState.Success).jenis
                    UpdateForm(
                        idJenis = jenis.idJenis,
                        namaJenis = jenis.namaJenis,
                        deskripsiJenis = jenis.deskripsiJenis,
                        onUpdateClick = {
                            viewModel.updateUiState(it)
                            viewModel.updateJenis()
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
    idJenis: Int,
    namaJenis: String,
    deskripsiJenis: String,
    onUpdateClick: (UpdateUiEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    var idJenis by remember { mutableStateOf(idJenis) }
    var namaJenis by remember { mutableStateOf(namaJenis) }
    var deskripsiJenis by remember { mutableStateOf(deskripsiJenis) }

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedTextField(
            value = namaJenis,
            onValueChange = { namaJenis = it },
            label = { Text("Nama Jenis") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        OutlinedTextField(
            value = deskripsiJenis,
            onValueChange = { deskripsiJenis = it },
            label = { Text("Deskripsi Jenis") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Button(
            onClick = {
                onUpdateClick(
                    UpdateUiEvent(
                        idJenis = idJenis,
                        namaJenis = namaJenis,
                        deskripsiJenis = deskripsiJenis
                    )
                )
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Update")
        }
    }
}
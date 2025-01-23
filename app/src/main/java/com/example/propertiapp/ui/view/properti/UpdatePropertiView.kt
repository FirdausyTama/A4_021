package com.example.propertiapp.ui.view.properti

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.propertiapp.navigasi.DestinasiNavigasi
import com.example.propertiapp.ui.costumewidget.CostumeTopAppBar
import com.example.propertiapp.ui.viewmodel.PenyediaViewModel
import com.example.propertiapp.ui.viewmodel.properti.*

object DestinasiEditProperti : DestinasiNavigasi {
    override val route = "properti_update"
    override val titleRes = "Update Properti"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditPropertiScreen(
    idProperti: String,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: UpdatePropertiVM = viewModel(factory = PenyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(idProperti) {
        viewModel.loadPropertiData(idProperti)
    }

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiEditProperti.titleRes,
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
                    val properti = (uiState as UpdateUiState.Success).properti
                    UpdateForm(
                        idProperti = properti.idProperti,
                        namaProperti = properti.namaProperti,
                        deskripsiProperti = properti.deskripsiProperti,
                        lokasi = properti.lokasi,
                        harga = properti.harga,
                        statusProperti = properti.statusProperti,
                        idJenis = properti.idJenis,
                        idPemilik = properti.idPemilik,
                        idManajer = properti.idManajer,
                        onUpdateClick = {
                            viewModel.updateUiState(it)
                            viewModel.updateProperti()
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
    idProperti: String,
    namaProperti: String,
    deskripsiProperti: String,
    lokasi: String,
    harga: String,
    statusProperti: String,
    idJenis: String,
    idPemilik: String,
    idManajer: String,
    onUpdateClick: (UpdateUiEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    var idProperti by remember { mutableStateOf(idProperti) }
    var namaProperti by remember { mutableStateOf(namaProperti) }
    var deskripsiProperti by remember { mutableStateOf(deskripsiProperti) }
    var lokasi by remember { mutableStateOf(lokasi) }
    var harga by remember { mutableStateOf(harga) }
    var statusProperti by remember { mutableStateOf(statusProperti) }
    var idJenis by remember { mutableStateOf(idJenis) }
    var idPemilik by remember { mutableStateOf(idPemilik) }
    var idManajer by remember { mutableStateOf(idManajer) }

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedTextField(
            value = namaProperti,
            onValueChange = { namaProperti = it },
            label = { Text("Nama Properti") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        OutlinedTextField(
            value = deskripsiProperti,
            onValueChange = { deskripsiProperti = it },
            label = { Text("Deskripsi Properti") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        OutlinedTextField(
            value = lokasi,
            onValueChange = { lokasi = it },
            label = { Text("Lokasi") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        OutlinedTextField(
            value = harga,
            onValueChange = { harga = it },
            label = { Text("Harga") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
        )

        OutlinedTextField(
            value = statusProperti,
            onValueChange = { statusProperti = it },
            label = { Text("Status Properti") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(
                value = idJenis,
                onValueChange = { idJenis = it },
                label = { Text("ID Jenis") },
                modifier = Modifier.weight(1f),
                singleLine = true
            )

            OutlinedTextField(
                value = idPemilik,
                onValueChange = { idPemilik = it },
                label = { Text("ID Pemilik") },
                modifier = Modifier.weight(1f),
                singleLine = true
            )

            OutlinedTextField(
                value = idManajer,
                onValueChange = { idManajer = it },
                label = { Text("ID Manajer") },
                modifier = Modifier.weight(1f),
                singleLine = true
            )
        }

        Button(
            onClick = {
                onUpdateClick(
                    UpdateUiEvent(
                        idProperti = idProperti,
                        namaProperti = namaProperti,
                        deskripsiProperti = deskripsiProperti,
                        lokasi = lokasi,
                        harga = harga,
                        statusProperti = statusProperti,
                        idJenis = idJenis,
                        idPemilik = idPemilik,
                        idManajer = idManajer
                    )
                )
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Update")
        }
    }
}
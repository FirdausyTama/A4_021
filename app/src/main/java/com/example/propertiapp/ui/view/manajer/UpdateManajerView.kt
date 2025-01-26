package com.example.propertiapp.ui.view.manajer


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
import com.example.propertiapp.ui.viewmodel.manajer.UpdateManajerVM
import com.example.propertiapp.ui.viewmodel.manajer.UpdateUiEvent
import com.example.propertiapp.ui.viewmodel.manajer.UpdateUiState

object DestinasiUpdateManajer : DestinasiNavigasi {
    override val route = "update_manajer"
    override val titleRes = "Update Manajer"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateScreenManajer(
    idManajer: Int,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: UpdateManajerVM = viewModel(factory = PenyediaViewModel.Factory)
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(idManajer) {
        viewModel.loadManajerData(idManajer)
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            CostumeTopAppBar(
                title = DestinasiUpdateManajer.titleRes,
                canNavigateBack = true,
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
                    val manajer = (uiState as UpdateUiState.Success).manajer
                    UpdateForm(
                        idManajer = manajer.idManajer,
                        namaManajer = manajer.namaManajer,
                        kontakManajer = manajer.kontakManajer,
                        onUpdateClick = {
                            viewModel.updateUiState(it)
                            viewModel.updateManajer()
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
    idManajer: Int,
    namaManajer: String,
    kontakManajer: String,
    onUpdateClick: (UpdateUiEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    var idManajer by remember { mutableStateOf(idManajer) }
    var namaManajer by remember { mutableStateOf(namaManajer) }
    var kontakManajer by remember { mutableStateOf(kontakManajer) }

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedTextField(
            value = namaManajer,
            onValueChange = { namaManajer = it },
            label = { Text("Nama Manajer") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        OutlinedTextField(
            value = kontakManajer,
            onValueChange = { kontakManajer = it },
            label = { Text("Kontak Manajer") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Button(
            onClick = {
                onUpdateClick(
                    UpdateUiEvent(
                        idManajer = idManajer,
                        namaManajer = namaManajer,
                        kontakManajer = kontakManajer
                    )
                )
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Update")
        }
    }
}

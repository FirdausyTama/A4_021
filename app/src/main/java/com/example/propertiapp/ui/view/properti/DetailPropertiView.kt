package com.example.propertiapp.ui.view.properti

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.propertiapp.model.Properti
import com.example.propertiapp.navigasi.DestinasiNavigasi
import com.example.propertiapp.ui.costumewidget.CostumeTopAppBar
import com.example.propertiapp.ui.viewmodel.PenyediaViewModel
import com.example.propertiapp.ui.viewmodel.properti.DetailPropertiUiState
import com.example.propertiapp.ui.viewmodel.properti.DetailPropertiViewModel

object DestinasiDetailProperti : DestinasiNavigasi {
    override val route = "detail_properti"
    override val titleRes = "Detail Properti"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailPropertiScreen(
    idProperti: String,
    onNavigateBack: () -> Unit,
    onEditClick: (String) -> Unit, // Tambahkan parameter idProperti
    navigateToJenis: () -> Unit,
    viewModel: DetailPropertiViewModel = viewModel(factory = PenyediaViewModel.Factory),
    modifier: Modifier = Modifier
) {
    val uiState = viewModel.uiState.collectAsState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    LaunchedEffect(idProperti) {
        Log.d("DetailPropertiScreen", "Loading properti with ID: $idProperti")
        viewModel.getPropertiById(idProperti)
    }

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiDetailProperti.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = onNavigateBack
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onEditClick(idProperti) }, // Navigasi ke Update Properti
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Properti"
                )
            }
        },
    ) { innerPadding ->
        when (val state = uiState.value) {
            is DetailPropertiUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
            is DetailPropertiUiState.Success -> {
                Column(
                    modifier = modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    DetailPropertiCard(
                        properti = state.properti,
                        onJenisClick = navigateToJenis
                    )
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
            is DetailPropertiUiState.Error -> ErrorScreen(
                retryAction = { viewModel.getPropertiById(idProperti) }
            )
        }
    }
}


@Composable
fun DetailPropertiCard(
    properti: Properti,
    onJenisClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = MaterialTheme.shapes.medium,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Header Section
            Text(
                text = properti.namaProperti,
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary
            )

            // Price Section
            Text(
                text = "${properti.harga}",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary
            )

            // Location Section
            Text(
                text = properti.lokasi,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            // Description Section
            Text(
                text = properti.deskripsiProperti,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Property Details Section
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Status
                Column {
                    Text(
                        text = "Status",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = properti.statusProperti,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
                Column {
                    Text(
                        text = "Pemilik",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = properti.idPemilik,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
                Column {
                    Text(
                        text = "Manajer",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = properti.idManajer,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
                Column {
                    Text(
                        text = "Jenis",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = properti.idJenis,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onJenisClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = MaterialTheme.shapes.medium
            ) {
                Text("Jenis Lainya")
            }
        }
    }
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun ErrorScreen(
    retryAction: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(text = "Terjadi Kesalahan")
        Button(
            onClick = retryAction,
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text(text = "Coba Lagi")
        }
    }
}
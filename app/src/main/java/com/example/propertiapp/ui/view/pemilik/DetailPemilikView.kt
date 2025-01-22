package com.example.propertiapp.ui.view.pemilik

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import com.example.propertiapp.model.Pemilik
import com.example.propertiapp.navigasi.DestinasiNavigasi
import com.example.propertiapp.ui.costumewidget.CostumeTopAppBar
import com.example.propertiapp.ui.viewmodel.PenyediaViewModel
import com.example.propertiapp.ui.viewmodel.pemilik.DetailPemilikUiState
import com.example.propertiapp.ui.viewmodel.pemilik.DetailPemilikViewModel

object DestinasiDetailPemilik : DestinasiNavigasi {
    override val route = "detail_pemilik"
    override val titleRes = "Detail Pemilik"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailPemilikScreen(
    idPemilik: String,
    onNavigateBack: () -> Unit,
    onEditClick: () -> Unit,
    viewModel: DetailPemilikViewModel = viewModel(factory = PenyediaViewModel.Factory),
    modifier: Modifier = Modifier
) {
    val uiState = viewModel.uiState.collectAsState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    LaunchedEffect(idPemilik) {
        Log.d("DetailPemilikScreen", "Loading pemilik with ID: $idPemilik")
        viewModel.getPemilikById(idPemilik)
    }

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiDetailPemilik.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = onNavigateBack
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onEditClick,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Pemilik"
                )
            }
        },
    ) { innerPadding ->
        when (val state = uiState.value) {
            is DetailPemilikUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
            is DetailPemilikUiState.Success -> {
                Column(
                    modifier = modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    DetailPemilikCard(pemilik = state.pemilik)
                }
            }
            is DetailPemilikUiState.Error -> ErrorScreen(
                retryAction = { viewModel.getPemilikById(idPemilik) }
            )
        }
    }
}

@Composable
fun DetailPemilikCard(pemilik: Pemilik) {
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
            // Nama Pemilik
            Text(
                text = "Nama: ${pemilik.namaPemilik}",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            // ID Pemilik
            Text(
                text = "ID: ${pemilik.idPemilik}",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            // Nomor Telepon
            Text(
                text = "No. Telepon: ${pemilik.kontakPemilik}",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun LoadingScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun ErrorScreen(
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
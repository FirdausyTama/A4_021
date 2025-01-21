package com.example.propertiapp.ui.view.jenis

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.propertiapp.R
import com.example.propertiapp.model.Jenis
import com.example.propertiapp.navigasi.DestinasiNavigasi
import com.example.propertiapp.ui.viewmodel.PenyediaViewModel
import com.example.propertiapp.ui.viewmodel.jenisproperti.JenisUiState
import com.example.propertiapp.ui.viewmodel.jenisproperti.JenisViewModel

object DestinasiJenis : DestinasiNavigasi {
    override val route = "jenis"
    override val titleRes = "Jenis Properti"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JenisScreen(
    navigateToItemEntry: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (String) -> Unit = {},
    navigateBack: () -> Unit, // Add this parameter
    viewModel: JenisViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = DestinasiJenis.titleRes,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                },
                navigationIcon = {
                    IconButton(onClick = navigateBack) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Kembali"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                ),
                scrollBehavior = scrollBehavior,
                actions = {
                    IconButton(onClick = { viewModel.getJenis() }) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "Refresh",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        JenisContent(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            onAddClick = navigateToItemEntry,
            jenisUiState = viewModel.jenisUiState,
            retryAction = { viewModel.getJenis() },
            onDetailClick = onDetailClick,
            onDeleteClick = {
                viewModel.deleteJenis(it.idJenis)
                viewModel.getJenis()
            }
        )
    }
}

@Composable
fun JenisContent(
    modifier: Modifier = Modifier,
    onAddClick: () -> Unit = {},
    jenisUiState: JenisUiState,
    retryAction: () -> Unit,
    onDetailClick: (String) -> Unit,
    onDeleteClick: (Jenis) -> Unit
) {
    Box(modifier = modifier) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            item {
                // Header Card
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    )
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                painter = painterResource(id = R.drawable.logo),
                                contentDescription = "",
                                modifier = Modifier.size(100.dp),
                                tint = Color(0xFF3700B3)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Column {
                                Text(
                                    text = "Daftar Jenis Properti",
                                    style = MaterialTheme.typography.titleLarge
                                )
                                Text(
                                    text = "Kelola jenis-jenis properti",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        }
                    }
                }
            }

            item {
                // Action Buttons
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Button(
                        onClick = onAddClick,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Tambah Jenis")
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
            }

            // Content Area
            when (jenisUiState) {
                is JenisUiState.Loading -> {
                    item { OnLoading(modifier = Modifier.fillParentMaxSize()) }
                }
                is JenisUiState.Success -> {
                    items(jenisUiState.jenis) { jenis ->
                        JenisCard(
                            jenis = jenis,
                            onClick = { onDetailClick(jenis.idJenis) },
                            onDeleteClick = { onDeleteClick(jenis) },
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
                is JenisUiState.Error -> {
                    item { OnError(retryAction, modifier = Modifier.fillParentMaxSize()) }
                }
            }
        }
    }
}

@Composable
fun OnLoading(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = Modifier.size(200.dp),
            painter = painterResource(R.drawable.loading),
            contentDescription = stringResource(R.string.loading)
        )
    }
}

@Composable
fun OnError(retryAction: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.error),
            contentDescription = ""
        )
        Text(text = stringResource(R.string.loading_failed), modifier = Modifier.padding(16.dp))
        Button(onClick = retryAction) {
            Text(stringResource(R.string.retry))
        }
    }
}

@Composable
fun JenisCard(
    jenis: Jenis,
    onClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = jenis.namaJenis,
                    style = MaterialTheme.typography.titleLarge,
                )
                Spacer(Modifier.weight(1f))
                IconButton(onClick = {}) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = "Info"
                    )
                }
                IconButton(onClick = onDeleteClick) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete"
                    )
                }
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "Description",
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = jenis.deskripsiJenis,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}
package com.example.propertiapp.ui.view.pemilik

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
import com.example.propertiapp.model.Pemilik
import com.example.propertiapp.navigasi.DestinasiNavigasi
import com.example.propertiapp.ui.viewmodel.PenyediaViewModel
import com.example.propertiapp.ui.viewmodel.pemilik.HomePemilikVM
import com.example.propertiapp.ui.viewmodel.pemilik.PemilikUiState

object DestinasiPemilik : DestinasiNavigasi {
    override val route = "pemilik"
    override val titleRes = "Pemilik Properti"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PemilikScreen(
    navigateToItemEntry: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (Int) -> Unit = {},
    navigateBack: () -> Unit,
    viewModel: HomePemilikVM = viewModel(factory = PenyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = DestinasiPemilik.titleRes,
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
                    IconButton(onClick = { viewModel.getPemilik() }) {
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
        PemilikContent(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            onAddClick = navigateToItemEntry,
            pemilikUiState = viewModel.pemilikUiState,
            retryAction = { viewModel.getPemilik() },
            onDetailClick = onDetailClick,
            onDeleteClick = {
                viewModel.deletePemilik(it.idPemilik)
                viewModel.getPemilik()
            }
        )
    }
}

@Composable
fun PemilikContent(
    modifier: Modifier = Modifier,
    onAddClick: () -> Unit = {},
    pemilikUiState: PemilikUiState,
    retryAction: () -> Unit,
    onDetailClick: (Int) -> Unit,
    onDeleteClick: (Pemilik) -> Unit
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
                                    text = "Daftar Pemilik Properti",
                                    style = MaterialTheme.typography.titleLarge
                                )
                                Text(
                                    text = "Kelola data pemilik properti",
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
                        Text("+ Tambah Pemilik")
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
            }

            // Content Area
            when (pemilikUiState) {
                is PemilikUiState.Loading -> {
                    item { OnLoading(modifier = Modifier.fillParentMaxSize()) }
                }
                is PemilikUiState.Success -> {
                    items(pemilikUiState.pemilik) { pemilik ->
                        PemilikCard(
                            pemilik = pemilik,
                            onClick = { onDetailClick(pemilik.idPemilik) },
                            onDeleteClick = { onDeleteClick(pemilik) },
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
                is PemilikUiState.Error -> {
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
fun PemilikCard(
    pemilik: Pemilik,
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
                    text = pemilik.namaPemilik,
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
                    imageVector = Icons.Default.Phone,
                    contentDescription = "Contact",
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = pemilik.kontakPemilik,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}
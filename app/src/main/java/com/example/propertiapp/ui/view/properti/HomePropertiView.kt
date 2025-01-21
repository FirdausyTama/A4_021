package com.example.propertiapp.ui.view.properti

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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.propertiapp.R
import com.example.propertiapp.model.Properti
import com.example.propertiapp.navigasi.DestinasiNavigasi
import com.example.propertiapp.ui.viewmodel.PenyediaViewModel
import com.example.propertiapp.ui.viewmodel.properti.HomeUiState
import com.example.propertiapp.ui.viewmodel.properti.HomeViewModel

object DestinasiHome : DestinasiNavigasi {
    override val route = "home"
    override val titleRes = "Home"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navigateToItemEntry: () -> Unit,
    navigateToJenis: () -> Unit,
    navigateToPemilik: () -> Unit, // Navigasi ke pengaturan
    navigateToManajer: () -> Unit, // Navigasi ke tentang
    modifier: Modifier = Modifier,
    onDetailClick: (String) -> Unit = {},
    viewModel: HomeViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = { Text(DestinasiHome.titleRes) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                ),
                scrollBehavior = scrollBehavior,
                actions = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {

                        // Tambah Properti
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.clickable { /*navigateToItemEntry()*/ }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Home,
                                contentDescription = "Home Properti",
                                tint = MaterialTheme.colorScheme.onPrimary
                            )
                            Text(
                                text = "Home",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                        // Jenis Properti
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.clickable { navigateToJenis() }
                        ) {
                            Icon(
                                imageVector = Icons.Default.AddCircle,
                                contentDescription = "Jenis Properti",
                                tint = MaterialTheme.colorScheme.onPrimary
                            )
                            Text(
                                text = "Jenis",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                        // Pengaturan
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.clickable { navigateToPemilik() }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Face,
                                contentDescription = "Halaman Pemilik",
                                tint = MaterialTheme.colorScheme.onPrimary
                            )
                            Text(
                                text = "Pemilik",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                        // Tentang
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.clickable { navigateToManajer() }
                        ) {
                            Icon(
                                imageVector = Icons.Default.AccountBox,
                                contentDescription = "Halaman Manajer",
                                tint = MaterialTheme.colorScheme.onPrimary
                            )
                            Text(
                                text = "Manajer",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                        // Refresh
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.clickable { viewModel.getProperti() }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Refresh,
                                contentDescription = "Refresh",
                                tint = MaterialTheme.colorScheme.onPrimary
                            )
                            Text(
                                text = "Refresh",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                    }
                }
            )
        }
    ) { innerPadding ->
        HomeContent(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            onAddClick = navigateToItemEntry,
            onJenisClick = navigateToJenis,
            homeUiState = viewModel.propertiUiState,
            retryAction = { viewModel.getProperti() },
            onDetailClick = onDetailClick,
            onDeleteClick = {
                viewModel.deleteProperti(it.idProperti)
                viewModel.getProperti()
            }
        )
    }
}

@Composable
fun HomeContent(
    modifier: Modifier = Modifier,
    onAddClick: () -> Unit = {},
    onJenisClick: () -> Unit = {}, // Add this parameter
    homeUiState: HomeUiState,
    retryAction: () -> Unit,
    onDetailClick: (String) -> Unit,
    onDeleteClick: (Properti) -> Unit
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
                                    text = "Manajemen Properti",
                                    style = MaterialTheme.typography.titleLarge
                                )
                                Text(
                                    text = "Cari properti yang sesuai dengan anda",
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
                        Text("+ Tambah Properti")
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
            }


            // Content Area
            when (homeUiState) {
                is HomeUiState.Loading -> {
                    item { OnLoading(modifier = Modifier.fillParentMaxSize()) }
                }
                is HomeUiState.Success -> {
                    items(homeUiState.properti) { properti ->
                        PropertiCard(
                            properti = properti,
                            onClick = { onDetailClick(properti.idProperti) },
                            onDeleteClick = { onDeleteClick(properti) },
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
                is HomeUiState.Error -> {
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
fun PropertiList(
    propertiList: List<Properti>,
    onDetailClick: (Properti) -> Unit,
    onDeleteClick: (Properti) -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(propertiList) { properti ->
            PropertiCard(
                properti = properti,
                onClick = { onDetailClick(properti) },
                onDeleteClick = { onDeleteClick(properti) }
            )
        }
    }
}

@Composable
fun PropertiCard(
    properti: Properti,
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
                    text = properti.namaProperti,
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
                    imageVector = Icons.Default.Info,
                    contentDescription = "Description",
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = properti.deskripsiProperti,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = "Location",
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = properti.lokasi,
                    style = MaterialTheme.typography.bodySmall
                )
                Spacer(modifier = Modifier.width(16.dp))
                Icon(
                    imageVector = Icons.Default.ShoppingCart,
                    contentDescription = "Price",
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "Rp ${properti.harga}",
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Warning,
                    contentDescription = "Status",
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = properti.statusProperti,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}
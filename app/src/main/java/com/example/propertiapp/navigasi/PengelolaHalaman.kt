package com.example.propertiapp.navigasi

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.propertiapp.ui.view.DestinasiHomeStart
import com.example.propertiapp.ui.view.HomeStartScreen
import com.example.propertiapp.ui.view.properti.*
import com.example.propertiapp.ui.view.jenis.*
import com.example.propertiapp.ui.view.jenisproperti.DestinasiDetailJenis
import com.example.propertiapp.ui.view.jenisproperti.DestinasiEntryJenis
import com.example.propertiapp.ui.view.jenisproperti.DetailJenisScreen
import com.example.propertiapp.ui.view.jenisproperti.EntryJenisScreen
import com.example.propertiapp.ui.view.pemilik.DestinasiEntryPemilik
import com.example.propertiapp.ui.view.pemilik.DestinasiPemilik
import com.example.propertiapp.ui.view.pemilik.InsertPemilikView
import com.example.propertiapp.ui.view.pemilik.PemilikScreen

@Composable
fun PengelolaHalaman(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = DestinasiHomeStart.route,  // Ubah start destination
        modifier = Modifier,
    ) {
        // Home Start Screen
        composable(DestinasiHomeStart.route) {
            HomeStartScreen(
                navigateToHome = {
                    navController.navigate(DestinasiHome.route) {
                        popUpTo(DestinasiHomeStart.route) { inclusive = true }
                    }
                }
            )
        }
        // Properti Home
        composable(DestinasiHome.route) {
            HomeScreen(
                navigateToItemEntry = { navController.navigate(DestinasiEntry.route) },
                navigateToJenis = { navController.navigate(DestinasiJenis.route) },
                navigateToPemilik = { navController.navigate(DestinasiPemilik.route) },
                navigateToManajer = {},
                onDetailClick = { idProperti ->
                    navController.navigate("${DestinasiDetailProperti.route}/$idProperti")
                }
            )
        }

        // Pemilik Screen
        composable(DestinasiPemilik.route) {
            PemilikScreen(
                navigateToItemEntry = { navController.navigate(DestinasiEntryPemilik.route) },
                navigateBack = { navController.navigateUp() },
                onDetailClick = { /*idPemilik ->
                    navController.navigate("${DestinasiDetailPemilik.route}/$idPemilik")*/
                }
            )
        }

        // Entry Pemilik Screen
        composable(DestinasiEntryPemilik.route) {
            InsertPemilikView(
                navigateBack = { navController.navigateUp() },
                onNavigateBack = {
                    navController.navigate(DestinasiPemilik.route) {
                        popUpTo(DestinasiPemilik.route) { inclusive = true }
                    }
                }
            )
        }

        // Properti Entry
        composable(DestinasiEntry.route) {
            EntryPropertiScreen(
                navigateBack = { navController.navigate(DestinasiHome.route) }
            )
        }

        // Detail properti
        composable(
            route = "${DestinasiDetailProperti.route}/{idProperti}",
            arguments = listOf(navArgument("idProperti") { type = NavType.StringType })
        ) { backStackEntry ->
            val idProperti = backStackEntry.arguments?.getString("idProperti") ?: ""
            DetailPropertiScreen(
                idProperti = idProperti,
                onNavigateBack = { navController.navigateUp() },
                onEditClick = {
                    navController.navigate("${DestinasiEditProperti.route}/$idProperti")
                },
                navigateToJenis = { navController.navigate(DestinasiJenis.route) }
            )
        }

        // Edit Properti
        composable(
            route = "${DestinasiEditProperti.route}/{idProperti}",
            arguments = listOf(navArgument("idProperti") { type = NavType.StringType })
        ) { backStackEntry ->
            val idProperti = backStackEntry.arguments?.getString("idProperti") ?: ""
            EditPropertiScreen(
                idProperti = idProperti,
                navigateBack = { navController.navigateUp() },
                onNavigateBack = { navController.navigateUp() }
            )
        }

        // Jenis Screens
        composable(DestinasiJenis.route) {
            JenisScreen(
                navigateToItemEntry = { navController.navigate(DestinasiEntryJenis.route) },
                onDetailClick = { idJenis ->
                    navController.navigate("${DestinasiDetailJenis.route}/$idJenis")
                },
                navigateBack = { navController.navigate(DestinasiHome.route) }
            )
        }

        composable(DestinasiEntryJenis.route) {
            EntryJenisScreen(
                navigateBack = { navController.navigate(DestinasiJenis.route) }
            )
        }

        composable(
            route = "${DestinasiDetailJenis.route}/{idJenis}",
            arguments = listOf(navArgument("idJenis") { type = NavType.StringType })
        ) { backStackEntry ->
            val idJenis = backStackEntry.arguments?.getString("idJenis") ?: ""
            DetailJenisScreen(
                idJenis = idJenis,
                onNavigateBack = { navController.navigateUp() },
                onEditClick = {/*
                    navController.navigate("${DestinasiUpdateProperti.route}/$idJenis")*/
                }
            )
        }
    }
}

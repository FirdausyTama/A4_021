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
import com.example.propertiapp.ui.view.jenisproperti.DestinasiUpdateJenis
import com.example.propertiapp.ui.view.jenisproperti.DetailJenisScreen
import com.example.propertiapp.ui.view.jenisproperti.EntryJenisScreen
import com.example.propertiapp.ui.view.jenisproperti.UpdateScreenJenis
import com.example.propertiapp.ui.view.manajer.DestinasiDetailManajer
import com.example.propertiapp.ui.view.manajer.DestinasiEntryManajer
import com.example.propertiapp.ui.view.manajer.DestinasiManajer
import com.example.propertiapp.ui.view.manajer.DestinasiUpdateManajer
import com.example.propertiapp.ui.view.manajer.DetailManajerScreen
import com.example.propertiapp.ui.view.manajer.EntryManajerBody
import com.example.propertiapp.ui.view.manajer.InsertManajerView
import com.example.propertiapp.ui.view.manajer.ManajerScreen
import com.example.propertiapp.ui.view.manajer.UpdateScreenManajer
import com.example.propertiapp.ui.view.pemilik.DestinasiDetailPemilik
import com.example.propertiapp.ui.view.pemilik.DestinasiEntryPemilik
import com.example.propertiapp.ui.view.pemilik.DestinasiPemilik
import com.example.propertiapp.ui.view.pemilik.DestinasiUpdatePemilik
import com.example.propertiapp.ui.view.pemilik.DetailPemilikScreen
import com.example.propertiapp.ui.view.pemilik.InsertPemilikView
import com.example.propertiapp.ui.view.pemilik.PemilikScreen
import com.example.propertiapp.ui.view.pemilik.UpdateScreenPemilik

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
                navigateToManajer = { navController.navigate(DestinasiManajer.route) },
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
                onDetailClick = { idPemilik ->
                    navController.navigate("${DestinasiDetailPemilik.route}/$idPemilik")
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
            arguments = listOf(navArgument("idProperti") { type = NavType.IntType })
        ) { backStackEntry ->
            val idProperti = backStackEntry.arguments?.getInt("idProperti") ?: 0
            DetailPropertiScreen(
                idProperti = idProperti,
                onNavigateBack = { navController.navigateUp() },
                onEditClick = { id ->
                    // sekarang menerima parameter id
                    navController.navigate("${DestinasiEditProperti.route}/$id")
                },
                navigateToJenis = {
                    navController.navigate(DestinasiJenis.route)
                }
            )
        }

        // Edit Properti
        composable(
            route = "${DestinasiEditProperti.route}/{idProperti}",
            arguments = listOf(navArgument("idProperti") { type = NavType.IntType })
        ) { backStackEntry ->
            val idProperti = backStackEntry.arguments?.getInt("idProperti") ?: 0
            EditPropertiScreen(
                idProperti = idProperti,
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
            arguments = listOf(navArgument("idJenis") { type = NavType.IntType })
        ) { backStackEntry ->
            val idJenis = backStackEntry.arguments?.getInt("idJenis") ?: 0
            DetailJenisScreen(
                idJenis = idJenis,
                onNavigateBack = { navController.navigateUp() },
                onEditClick = {
                    navController.navigate("${DestinasiUpdateJenis.route}/$idJenis")
                }
            )
        }

        // Edit Jenis
        composable(
            route = "${DestinasiUpdateJenis.route}/{idJenis}",
            arguments = listOf(navArgument("idJenis") { type = NavType.IntType })
        ) { backStackEntry ->
            val idJenis = backStackEntry.arguments?.getInt("idJenis") ?: 0
            UpdateScreenJenis(
                idJenis = idJenis,
                onNavigateBack = { navController.navigateUp() }
            )
        }

        //Detail Pemilik
        composable(
            route = "${DestinasiDetailPemilik.route}/{idPemilik}",
            arguments = listOf(navArgument("idPemilik") { type = NavType.IntType })
        ) { backStackEntry ->
            val idPemilik = backStackEntry.arguments?.getInt("idPemilik") ?: 0
            DetailPemilikScreen(
                idPemilik = idPemilik,
                onNavigateBack = { navController.navigateUp() },
                onEditClick = {
                    navController.navigate("${DestinasiUpdatePemilik.route}/$idPemilik")
                }
            )
        }

        composable(DestinasiManajer.route) {
            ManajerScreen(
                navigateToItemEntry = { navController.navigate(DestinasiEntryManajer.route) },
                navigateBack = { navController.navigate(DestinasiHome.route) },
                onDetailClick = { idManajer ->  // Changed from idPemilik
                    navController.navigate("${DestinasiDetailManajer.route}/$idManajer") // Changed route
                }
            )
        }

        // Edit Manajer
        composable(
            route = "${DestinasiUpdateManajer.route}/{idManajer}",
            arguments = listOf(navArgument("idManajer") { type = NavType.IntType })
        ) { backStackEntry ->
            val idManajer = backStackEntry.arguments?.getInt("idManajer") ?: 0
            UpdateScreenManajer(
                idManajer = idManajer,
                onNavigateBack = { navController.navigateUp() }
            )
        }

        composable(DestinasiEntryManajer.route) {
            InsertManajerView(
                navigateBack = { navController.navigate(DestinasiManajer.route) },
                onNavigateBack = { navController.navigate(DestinasiManajer.route) }
            )
        }

        composable(
            route = "${DestinasiDetailManajer.route}/{idManajer}",
            arguments = listOf(navArgument("idManajer") { type = NavType.IntType })
        ) { backStackEntry ->
            val idManajer = backStackEntry.arguments?.getInt("idManajer") ?:0
            DetailManajerScreen(
                idManajer = idManajer,
                onNavigateBack = { navController.navigateUp() },
                onEditClick = {
                    navController.navigate("${DestinasiUpdateManajer.route}/$idManajer")
                }
            )
        }

        // Edit Pemilik
        composable(
            route = "${DestinasiUpdatePemilik.route}/{idPemilik}",
            arguments = listOf(navArgument("idPemilik") { type = NavType.IntType })
        ) { backStackEntry ->
            val idPemilik = backStackEntry.arguments?.getInt("idPemilik") ?: 0
            UpdateScreenPemilik(
                idPemilik = idPemilik,
                onNavigateBack = { navController.navigateUp() }
            )
        }
    }
}
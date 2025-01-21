package com.example.propertiapp.navigasi

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.propertiapp.ui.view.properti.*
import com.example.propertiapp.ui.view.jenis.*
import com.example.propertiapp.ui.view.jenisproperti.DestinasiDetailJenis
import com.example.propertiapp.ui.view.jenisproperti.DestinasiEntryJenis
import com.example.propertiapp.ui.view.jenisproperti.DetailJenisScreen
import com.example.propertiapp.ui.view.jenisproperti.EntryJenisScreen

@Composable
fun PengelolaHalaman(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = DestinasiHome.route,
        modifier = Modifier,
    ) {
        // Properti Screens
        composable(DestinasiHome.route) {
            HomeScreen(
                navigateToItemEntry = { navController.navigate(DestinasiEntry.route) },
                navigateToJenis = { navController.navigate(DestinasiJenis.route) }, // Add this
                onDetailClick = { idProperti ->
                    navController.navigate("${DestinasiDetailProperti.route}/$idProperti")
                }
            )
        }

        composable(DestinasiEntry.route) {
            EntryPropertiScreen(
                navigateBack = { navController.navigate(DestinasiHome.route) }
            )
        }

        composable(
            route = "${DestinasiDetailProperti.route}/{idProperti}",
            arguments = listOf(navArgument("idProperti") { type = NavType.StringType })
        ) { backStackEntry ->
            val idProperti = backStackEntry.arguments?.getString("idProperti") ?: ""
            DetailPropertiScreen(
                idProperti = idProperti,
                onNavigateBack = { navController.navigateUp() },
                onEditClick = {
                    navController.navigate("${DestinasiEntry.route}?idProperti=$idProperti")
                },
                navigateToJenis = { navController.navigate(DestinasiJenis.route) }
            )
        }

        // Jenis Screens
        composable(DestinasiJenis.route) {
            JenisScreen(
                navigateToItemEntry = { navController.navigate(DestinasiEntryJenis.route)},
                onDetailClick = { idJenis ->
                    navController.navigate("${DestinasiDetailJenis.route}/$idJenis")
                },
                navigateBack = {navController.navigate(DestinasiHome.route)}

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
                onEditClick = {
                    navController.navigate("${DestinasiEntryJenis.route}?idJenis=$idJenis")
                }
            )
        }
    }
}
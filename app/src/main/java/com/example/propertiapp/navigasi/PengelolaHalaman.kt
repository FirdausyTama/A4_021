package com.example.propertiapp.navigasi

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.propertiapp.ui.view.properti.DestinasiHome
import com.example.propertiapp.ui.view.properti.HomeScreen
import com.example.propertiapp.ui.view.properti.DestinasiEntry
import com.example.propertiapp.ui.view.properti.EntryPropertiScreen
import com.example.propertiapp.ui.view.properti.DestinasiDetailProperti
import com.example.propertiapp.ui.view.properti.DetailPropertiScreen

@Composable
fun PengelolaHalaman(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = DestinasiHome.route,
        modifier = Modifier,
    ) {
        // Home Screen
        composable(DestinasiHome.route) {
            HomeScreen(
                navigateToItemEntry = { navController.navigate(DestinasiEntry.route)},
                onDetailClick = { idProperti ->
                    navController.navigate("${DestinasiDetailProperti.route}/$idProperti")
                }
            )
        }

        // Entry Screen
        composable(DestinasiEntry.route) {
            EntryPropertiScreen(
                navigateBack = { navController.navigate(DestinasiHome.route) }
            )
        }

        // Detail Screen
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
                }
            )
        }
    }
}
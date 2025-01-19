package com.example.propertiapp.navigasi

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.propertiapp.ui.view.properti.DestinasiHome
import com.example.propertiapp.ui.view.properti.HomeScreen
import com.example.propertiapp.ui.view.properti.DestinasiEntry
import com.example.propertiapp.ui.view.properti.EntryPropertiScreen

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
                onDetailClick = { /*idProperti ->
                    navController.navigate("${DestinasiDetail.route}/$idProperti")*/
                }
            )
        }

        // Entry Screen
        composable(DestinasiEntry.route) {
            EntryPropertiScreen(
                navigateBack = { navController.navigate(DestinasiHome.route) }
            )
        }
    }
}
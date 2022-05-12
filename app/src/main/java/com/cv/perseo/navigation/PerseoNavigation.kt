package com.cv.perseo.navigation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.cv.perseo.screens.SplashScreen
import com.cv.perseo.screens.dashboard.DashboardScreen
import com.cv.perseo.screens.enterpriseselector.EnterpriseSelectorScreen
import com.cv.perseo.screens.inventory.InventoryScreen
import com.cv.perseo.screens.login.LoginScreen

@ExperimentalComposeUiApi
@ExperimentalFoundationApi
@Composable
fun PerseoNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = PerseoScreens.SplashScreen.name) {

        composable(PerseoScreens.SplashScreen.name) {
            SplashScreen(navController = navController)
        }

        composable(PerseoScreens.LoginScreen.name) {
            LoginScreen(navController = navController)
        }

        composable(PerseoScreens.EnterpriseSelector.name) {
            EnterpriseSelectorScreen(navController = navController)
        }

        composable(PerseoScreens.Dashboard.name) {
            DashboardScreen(navController = navController)
        }

        composable(PerseoScreens.Inventory.name) {
            InventoryScreen(navController = navController)
        }
    }
}
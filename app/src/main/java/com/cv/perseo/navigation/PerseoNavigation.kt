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
import com.cv.perseo.screens.osdetails.OSDetails
import com.cv.perseo.screens.serviceorders.ServiceOrders
import com.cv.perseo.screens.subscribers.Subscribers

@ExperimentalComposeUiApi
@ExperimentalFoundationApi
@Composable
fun PerseoNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = PerseoScreens.SplashScreen.route) {

        composable(PerseoScreens.SplashScreen.route) {
            SplashScreen(navController = navController)
        }

        composable(PerseoScreens.LoginScreen.route) {
            LoginScreen(navController = navController)
        }

        composable(PerseoScreens.EnterpriseSelector.route) {
            EnterpriseSelectorScreen(navController = navController)
        }

        composable(PerseoScreens.Dashboard.route) {
            DashboardScreen(navController = navController)
        }

        composable(PerseoScreens.Inventory.route) {
            InventoryScreen(navController = navController)
        }

        composable(PerseoScreens.OSDetails.route) {
            OSDetails(navController = navController)
        }

        composable(PerseoScreens.ServiceOrders.route) {
            ServiceOrders(navController = navController)
        }
        
        composable(PerseoScreens.Subscribers.route) {
            Subscribers(navController = navController)
        }
    }
}
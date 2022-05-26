package com.cv.perseo.navigation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.cv.perseo.screens.SplashScreen
import com.cv.perseo.screens.compliance.ComplianceScreen
import com.cv.perseo.screens.dashboard.DashboardScreen
import com.cv.perseo.screens.enterpriseselector.EnterpriseSelectorScreen
import com.cv.perseo.screens.equipment.EquipmentScreen
import com.cv.perseo.screens.inventory.InventoryScreen
import com.cv.perseo.screens.login.LoginScreen
import com.cv.perseo.screens.materials.MaterialsScreen
import com.cv.perseo.screens.myserviceorders.MyServiceOrdersScreen
import com.cv.perseo.screens.osdetails.OSDetailsScreen
import com.cv.perseo.screens.rubro.RubroScreen
import com.cv.perseo.screens.servicecords.ServiceCordsScreen
import com.cv.perseo.screens.serviceorders.ServiceOrdersScreen
import com.cv.perseo.screens.subscribers.SubscribersScreen
import com.cv.perseo.screens.zone.ZoneScreen

@ExperimentalComposeUiApi
@ExperimentalFoundationApi
@Composable
fun PerseoNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = PerseoScreens.Splash.route) {

        composable(PerseoScreens.Compliance.route) {
            ComplianceScreen(navController = navController)
        }

        composable(PerseoScreens.Dashboard.route) {
            DashboardScreen(navController = navController)
        }

        composable(PerseoScreens.EnterpriseSelector.route) {
            EnterpriseSelectorScreen(navController = navController)
        }

        composable(PerseoScreens.Equipment.route) {
            EquipmentScreen(navController = navController)
        }

        composable(PerseoScreens.Inventory.route) {
            InventoryScreen(navController = navController)
        }

        composable(PerseoScreens.Login.route) {
            LoginScreen(navController = navController)
        }

        composable(PerseoScreens.Materials.route) {
            MaterialsScreen(navController = navController)
        }

        composable(PerseoScreens.MyServiceOrders.route) {
            MyServiceOrdersScreen(navController = navController)
        }

        composable(PerseoScreens.OSDetails.route) {
            OSDetailsScreen(navController = navController)
        }

        composable(PerseoScreens.Rubro.route) {
            RubroScreen(navController = navController)
        }

        composable(PerseoScreens.ServicesCords.route) {
            ServiceCordsScreen(navController = navController)
        }

        composable(PerseoScreens.ServiceOrders.route) {
            ServiceOrdersScreen(navController = navController)
        }

        composable(PerseoScreens.Subscribers.route) {
            SubscribersScreen(navController = navController)
        }

        composable(PerseoScreens.Zone.route) {
            ZoneScreen(navController = navController)
        }

        composable(PerseoScreens.Splash.route) {
            SplashScreen(navController = navController)
        }
    }
}
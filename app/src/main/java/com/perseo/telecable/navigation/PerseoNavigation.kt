package com.perseo.telecable.navigation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.perseo.telecable.screens.completedordersummary.CompletedOrderSummaryScreen
import com.perseo.telecable.screens.compliance.ComplianceScreen
import com.perseo.telecable.screens.dashboard.DashboardScreen
import com.perseo.telecable.screens.dashboard.DashboardScreenViewModel
import com.perseo.telecable.screens.enterpriseselector.EnterpriseSelectorScreen
import com.perseo.telecable.screens.equipment.EquipmentScreen
import com.perseo.telecable.screens.inventory.InventoryScreen
import com.perseo.telecable.screens.login.LoginScreen
import com.perseo.telecable.screens.login.LoginScreenViewModel
import com.perseo.telecable.screens.materials.MaterialsScreen
import com.perseo.telecable.screens.myserviceorders.MyServiceOrdersScreen
import com.perseo.telecable.screens.ordersoptions.OrdersOptionsScreen
import com.perseo.telecable.screens.osdetails.OSDetailsScreen
import com.perseo.telecable.screens.rubro.RubroScreen
import com.perseo.telecable.screens.scheduleorders.ScheduleOrdersScreen
import com.perseo.telecable.screens.servicecords.ServiceCordsScreen
import com.perseo.telecable.screens.serviceorders.ServiceOrdersScreen
import com.perseo.telecable.screens.signature.SignatureScreen
import com.perseo.telecable.screens.splash.SplashScreen
import com.perseo.telecable.screens.splash.SplashScreenViewModel
import com.perseo.telecable.screens.subscribers.SubscribersScreen
import com.perseo.telecable.screens.zone.ZoneScreen

@ExperimentalPermissionsApi
@ExperimentalComposeUiApi
@ExperimentalFoundationApi
@Composable
fun PerseoNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = PerseoScreens.Splash.route) {

        composable(PerseoScreens.Compliance.route) {
            ComplianceScreen(navController = navController)
        }

        composable(PerseoScreens.CompletedOrderSummary.route) {
            CompletedOrderSummaryScreen(navController = navController)
        }

        composable(PerseoScreens.Signature.route) {
            SignatureScreen(navController = navController) {
                navController.previousBackStackEntry
                    ?.savedStateHandle
                    ?.set("FIRMA", it)
                navController.popBackStack()
            }
        }

        composable(PerseoScreens.Dashboard.route) {
            val viewModel = hiltViewModel<DashboardScreenViewModel>()
            DashboardScreen(navController = navController, viewModel = viewModel)
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
            val viewModel = hiltViewModel<LoginScreenViewModel>()
            LoginScreen(navController = navController, viewModel = viewModel)
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
            val viewModel = hiltViewModel<SplashScreenViewModel>()
            SplashScreen(navController = navController, viewModel = viewModel)
        }

        composable(PerseoScreens.ScheduleOrders.route) {
            ScheduleOrdersScreen(navController = navController)
        }

        composable(PerseoScreens.OrderOptions.route) {
            OrdersOptionsScreen(navController = navController)
        }
    }
}
package com.cv.perseo.screens.serviceorders

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.cv.perseo.components.ButtonsList
import com.cv.perseo.components.PerseoBottomBar
import com.cv.perseo.components.PerseoTopBar
import com.cv.perseo.navigation.PerseoScreens
import com.cv.perseo.ui.theme.Background
import com.cv.perseo.utils.Constants

@ExperimentalFoundationApi
@Composable
fun ServiceOrdersScreen(navController: NavController) {
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            PerseoTopBar(
                title = "Ordenes de Servicio",
                inDashboard = false
            ) {
                navController.navigate(PerseoScreens.Dashboard.route)
            }
        },
        bottomBar = {
            PerseoBottomBar()
        },
        backgroundColor = Background,
    ) {
        ButtonsList(
            navController,
            listOf(Constants.MY_SERVICE_ORDERS, Constants.COMPLIANCE, Constants.SERVICES_CORDS)
        )
    }
}
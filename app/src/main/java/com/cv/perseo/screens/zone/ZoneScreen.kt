package com.cv.perseo.screens.zone

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
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
fun ZoneScreen(navController: NavController) {
    val scaffoldState = rememberScaffoldState()
    val rubroList = listOf(Constants.RUBRO1, Constants.RUBRO2, Constants.RUBRO3, Constants.RUBRO4)

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            PerseoTopBar(
                title = "(Zona)*",
                inDashboard = false
            ) {
                navController.navigate(PerseoScreens.MyServiceOrders.route) {
                    popUpTo(PerseoScreens.MyServiceOrders.route)
                }
            }
        },
        bottomBar = {
            PerseoBottomBar()
        },
        backgroundColor = Background,
    ) {
        ButtonsList(navController = navController, Items = rubroList, true)
    }
}
package com.cv.perseo.screens.serviceorders

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.cv.perseo.components.ButtonsList
import com.cv.perseo.components.PerseoBottomBar
import com.cv.perseo.components.PerseoTopBar
import com.cv.perseo.navigation.PerseoScreens
import com.cv.perseo.screens.dashboard.DashboardScreenViewModel
import com.cv.perseo.ui.theme.Background
import com.cv.perseo.utils.Constants

@ExperimentalFoundationApi
@Composable
fun ServiceOrdersScreen(
    navController: NavController,
    viewModel: ServiceOrdersScreenViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()
    viewModel.getGeneralData()
    val generalData by viewModel.data.observeAsState()

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
            if (generalData != null){
                PerseoBottomBar(
                    enterprise = generalData?.municipality!!,
                    enterpriseIcon = generalData?.logo!!
                )
            }
        },
        backgroundColor = Background,
    ) {
        ButtonsList(
            navController,
            viewModel.permissions.collectAsState().value
        )
    }
}
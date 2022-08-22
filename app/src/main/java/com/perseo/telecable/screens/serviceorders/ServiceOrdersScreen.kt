package com.perseo.telecable.screens.serviceorders

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.perseo.telecable.components.ButtonsList
import com.perseo.telecable.components.PerseoBottomBar
import com.perseo.telecable.components.PerseoTopBar
import com.perseo.telecable.navigation.PerseoScreens
import com.perseo.telecable.ui.theme.Background

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
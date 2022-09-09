package com.perseo.telecable.screens.myserviceorders

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.perseo.telecable.R
import com.perseo.telecable.components.PerseoBottomBar
import com.perseo.telecable.components.PerseoTopBar
import com.perseo.telecable.components.ZonesButtons
import com.perseo.telecable.navigation.PerseoScreens
import com.perseo.telecable.ui.theme.Background

@ExperimentalFoundationApi
@Composable
fun MyServiceOrdersScreen(
    navController: NavController,
    viewModel: MyServiceOrdersScreenViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()
    val zones by viewModel.serviceOrdersZones.collectAsState()
    viewModel.getGeneralData()
    val generalData by viewModel.data.observeAsState()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            PerseoTopBar(
                title = stringResource(id = R.string.zone),
                inDashboard = false
            ) {
                navController.navigate(PerseoScreens.OrderOptions.route) {
                    popUpTo(PerseoScreens.OrderOptions.route)
                }
            }
        },
        bottomBar = {
            if (generalData != null) {
                PerseoBottomBar(
                    enterprise = generalData?.municipality!!,
                    enterpriseIcon = generalData?.logo!!
                )
            }
        },
        backgroundColor = Background,
    ) {
        ZonesButtons(items = zones) { zone ->
            viewModel.saveZone(zone)
            navController.navigate(PerseoScreens.Zone.route)
        }
    }
}
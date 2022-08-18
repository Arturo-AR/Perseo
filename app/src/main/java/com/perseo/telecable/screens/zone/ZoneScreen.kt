package com.perseo.telecable.screens.zone

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
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
fun ZoneScreen(
    navController: NavController,
    viewModel: ZoneScreenViewModel = hiltViewModel()
) {
    viewModel.getGeneralData()
    val generalData by viewModel.data.observeAsState()
    val scaffoldState = rememberScaffoldState()
    val currentZone by viewModel.currentZone.observeAsState()
    val rubro = viewModel.rubro.collectAsState().value
    val rubroList = mutableListOf<String>()
    if (rubro.isNotEmpty()) {
        for (x in rubro) {
            rubroList.add(x.rubro_icon)
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            PerseoTopBar(
                title = currentZone ?: "",
                inDashboard = false
            ) {
                navController.navigate(PerseoScreens.MyServiceOrders.route) {
                    popUpTo(PerseoScreens.MyServiceOrders.route)
                }
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
        if (rubroList.isNotEmpty()) {
            ButtonsList(navController = navController, Items = rubroList, onRubro = true) { index ->
                viewModel.saveRubro(rubro[index].rubro)
                navController.navigate(PerseoScreens.Rubro.route)
            }
        }
    }
}
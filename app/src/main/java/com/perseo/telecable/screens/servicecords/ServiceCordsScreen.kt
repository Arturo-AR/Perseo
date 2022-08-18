package com.perseo.telecable.screens.servicecords

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.perseo.telecable.components.CordsServicesFilters
import com.perseo.telecable.components.CordsServicesItem
import com.perseo.telecable.components.PerseoBottomBar
import com.perseo.telecable.components.PerseoTopBar
import com.perseo.telecable.navigation.PerseoScreens
import com.perseo.telecable.ui.theme.Background

@Composable
fun ServiceCordsScreen(
    navController: NavController,
    viewModel: ServiceCordsScreenViewModel = hiltViewModel()

) {
    val scaffoldState = rememberScaffoldState()
    val cords by viewModel.cordsOrder.observeAsState()
    viewModel.getGeneralData()
    val generalData by viewModel.data.observeAsState()
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            PerseoTopBar(
                title = "Cortes de Servicio",
                inDashboard = false
            ) {
                navController.navigate(PerseoScreens.ServiceOrders.route) {
                    popUpTo(PerseoScreens.ServiceOrders.route)
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 50.dp)
                .background(Background)
        ) {
            CordsServicesFilters()
            if (!cords.isNullOrEmpty()) {
                LazyColumn(contentPadding = PaddingValues(8.dp)) {
                    items(cords!!) { cord ->
                        CordsServicesItem(cord)
                    }
                }
            }

        }
    }
}
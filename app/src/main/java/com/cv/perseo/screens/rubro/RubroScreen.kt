package com.cv.perseo.screens.rubro

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.cv.perseo.components.PerseoBottomBar
import com.cv.perseo.components.PerseoTopBar
import com.cv.perseo.components.ServiceOrderCard
import com.cv.perseo.navigation.PerseoScreens
import com.cv.perseo.ui.theme.Background
import com.cv.perseo.utils.toast

@ExperimentalFoundationApi
@Composable
fun RubroScreen(
    navController: NavController,
    viewModel: RubroScreenViewModel = hiltViewModel()
) {
    viewModel.getGeneralData()
    val generalData by viewModel.data.observeAsState()
    val scaffoldState = rememberScaffoldState()
    val orders = viewModel.serviceOrders.collectAsState().value
    val currentRubro by viewModel.currentRubro.observeAsState()
    val context = LocalContext.current
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            PerseoTopBar(
                title = currentRubro ?: "",
                inDashboard = false
            ) {
                navController.navigate(PerseoScreens.Zone.route) {
                    popUpTo(PerseoScreens.Zone.route)
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

        LazyVerticalGrid(
            modifier = Modifier.padding(bottom = 50.dp),
            cells = GridCells.Fixed(2),
            contentPadding = PaddingValues(8.dp)
        ) {
            items(orders) { order ->
                ServiceOrderCard(order) {
                    if (order.preCumDate == "") {
                        viewModel.saveOsId(order.osId)
                        navController.navigate(PerseoScreens.OSDetails.route)
                    } else {
                        context.toast("Orden Pre-Cumplida")
                    }
                }
            }
        }
    }
}
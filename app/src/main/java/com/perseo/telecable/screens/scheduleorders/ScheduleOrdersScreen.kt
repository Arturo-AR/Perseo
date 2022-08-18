package com.perseo.telecable.screens.scheduleorders

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.perseo.telecable.components.*
import com.perseo.telecable.navigation.PerseoScreens
import com.perseo.telecable.ui.theme.Background

@Composable
fun ScheduleOrdersScreen(
    navController: NavController,
    viewModel: ScheduleOrdersScreenViewModel = hiltViewModel()
) {
    viewModel.getGeneralData()
    val generalData by viewModel.data.observeAsState()
    val scaffoldState = rememberScaffoldState()
    val schedule = viewModel.scheduleOrders.collectAsState().value
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            PerseoTopBar(
                title = "Agenda",
                inDashboard = false
            ) {
                navController.navigate(PerseoScreens.OrderOptions.route) {
                    popUpTo(PerseoScreens.OrderOptions.route)
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
        if (schedule.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier
                    .padding(bottom = 50.dp),
                contentPadding = PaddingValues(8.dp)
            ) {
                items(schedule) { order ->
                    ScheduleItem(order) {
                        if (order.preCumDate == "") {
                            viewModel.saveOsId(order.osId)
                            navController.navigate(PerseoScreens.OSDetails.route)
                        } else {
                            Log.d("pre completed", "Order Ready")
                        }
                    }
                }
            }
        }
    }
}
package com.perseo.telecable.screens.scheduleorders

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.perseo.telecable.components.*
import com.perseo.telecable.navigation.PerseoScreens
import com.perseo.telecable.ui.theme.Background
import com.perseo.telecable.utils.toast

@Composable
fun ScheduleOrdersScreen(
    navController: NavController,
    viewModel: ScheduleOrdersScreenViewModel = hiltViewModel()
) {
    viewModel.getGeneralData()
    val generalData by viewModel.data.observeAsState()
    val scaffoldState = rememberScaffoldState()
    val schedule = viewModel.scheduleOrders.collectAsState().value
    val context = LocalContext.current
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
            if (generalData != null) {
                PerseoBottomBar(
                    enterprise = generalData?.municipality!!,
                    enterpriseIcon = generalData?.logo!!
                )
            }
        },
        backgroundColor = Background,
    ) {
        Column {
            if (schedule.isNotEmpty()) {
                Text(
                    modifier = Modifier.padding(16.dp),
                    text = "Ordenes agendadas para el dia ${schedule[0].scheduleDate}",
                    color = Color.White,
                    textAlign = TextAlign.Start,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                LazyColumn(
                    modifier = Modifier
                        .padding(bottom = 50.dp)
                ) {
                    items(schedule) { order ->
                        ScheduleItem(order) {
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
    }
}
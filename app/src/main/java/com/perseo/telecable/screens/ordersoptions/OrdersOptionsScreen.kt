package com.perseo.telecable.screens.ordersoptions

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.perseo.telecable.R
import com.perseo.telecable.components.DefaultButtonWithImage
import com.perseo.telecable.components.PerseoBottomBar
import com.perseo.telecable.components.PerseoTopBar
import com.perseo.telecable.navigation.PerseoScreens
import com.perseo.telecable.ui.theme.Background

@Composable
fun OrdersOptionsScreen(
    navController: NavController,
    viewModel: OrdersOptionsScreenViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()
    viewModel.getGeneralData()
    val generalData by viewModel.data.observeAsState()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            PerseoTopBar(
                title = stringResource(id = R.string.order_by),
                inDashboard = false
            ) {
                navController.navigate(PerseoScreens.ServiceOrders.route) {
                    popUpTo(PerseoScreens.ServiceOrders.route)
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
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            DefaultButtonWithImage(
                title = stringResource(id = R.string.zone),
                onClick = {
                    navController.navigate(PerseoScreens.MyServiceOrders.route)
                })
            DefaultButtonWithImage(
                title = stringResource(id = R.string.schedule),
                onClick = {
                    navController.navigate(PerseoScreens.ScheduleOrders.route)
                })
        }
    }
}
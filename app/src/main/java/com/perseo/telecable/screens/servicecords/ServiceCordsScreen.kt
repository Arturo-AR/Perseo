package com.perseo.telecable.screens.servicecords

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.perseo.telecable.components.*
import com.perseo.telecable.navigation.PerseoScreens
import com.perseo.telecable.ui.theme.Background
import com.perseo.telecable.utils.toJsonString
import com.perseo.telecable.utils.toast

@Composable
fun ServiceCordsScreen(
    navController: NavController,
    viewModel: ServiceCordsScreenViewModel = hiltViewModel()

) {
    val scaffoldState = rememberScaffoldState()
    val cords by viewModel.cordsOrder.observeAsState()
    viewModel.getGeneralData()
    val generalData by viewModel.data.observeAsState()
    val context = LocalContext.current
    val osList by viewModel.osList.observeAsState()
    val openConfirmDialog = remember { mutableStateOf(false) }
    val options by viewModel.filterOptions.observeAsState()
    if (openConfirmDialog.value) {
        ShowAlertDialog(
            title = "Alerta",
            message = {
                Text(
                    text = "Desea cumplir las ordenes:\n${osList?.toJsonString()}",
                    color = Color.White,
                    fontSize = 18.sp
                )
            },
            positiveButtonText = "Cumplir",
            openDialog = openConfirmDialog
        ) {
            viewModel.completeOrders {
                navController.navigate(PerseoScreens.ServiceOrders.route) {
                    popUpTo(PerseoScreens.ServiceOrders.route)
                }
            }
        }
    }
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
            if (generalData != null) {
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
            Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.Center) {

                CordsServicesFilters(options ?: emptyList(), onChangeFilter = {
                    viewModel.cleanOsList()
                    viewModel.updateFilter(it)
                },
                    onChangeOption = { option, filter ->
                        viewModel.cleanOsList()
                        viewModel.updateList(option, filter)
                    })
            }
            Column(modifier = Modifier.weight(10f)) {
                if (!cords.isNullOrEmpty()) {
                    LazyColumn(contentPadding = PaddingValues(8.dp)) {
                        items(cords!!) { cord ->
                            CordsServicesItem(cord) { os, checked ->
                                if (checked) {
                                    viewModel.addOs(os.osId)
                                } else {
                                    viewModel.deleteOs(cord.osId)
                                }
                            }
                        }
                    }
                }
            }
            Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.Center) {
                CordsServicesFooter {
                    if (osList.isNullOrEmpty()) {
                        context.toast("No has seleccionado ninguna orden")
                    } else {
                        openConfirmDialog.value = true
                    }
                }
            }
        }
    }
}
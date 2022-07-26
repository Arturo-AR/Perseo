package com.cv.perseo.screens.osdetails

import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.cv.perseo.components.DetailContainer
import com.cv.perseo.components.PerseoBottomBar
import com.cv.perseo.components.PerseoTopBar
import com.cv.perseo.model.ItemOSDetail
import com.cv.perseo.navigation.PerseoScreens
import com.cv.perseo.ui.theme.Accent
import com.cv.perseo.ui.theme.Background
import com.cv.perseo.ui.theme.Yellow6
import com.cv.perseo.utils.Constants

@Composable
fun OSDetailsScreen(
    navController: NavController,
    viewModel: OSDetailsScreenViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()
    val scrollState = rememberScrollState()
    val doing by viewModel.doing.observeAsState()
    val onWay by viewModel.onWay.observeAsState()
    val os by viewModel.currentOs.observeAsState()
    val generalData = viewModel.generalData.collectAsState().value
    val motivos by viewModel.motivos.observeAsState()
    val context = LocalContext.current
    if (os != null && generalData.isNotEmpty()) {
        viewModel.getMotivos(os?.motivoId!!, generalData[0].idMunicipality)
    }
    if (!motivos.isNullOrEmpty()) {
        Log.d("motivos", motivos.toString())
    }

    val parameters = listOf(
        ItemOSDetail("Contrato No: ", os?.noContract.toString()),
        ItemOSDetail("Estatus: ", os?.status ?: ""),
        ItemOSDetail("Paquete: ", os?.packageName ?: ""),
        ItemOSDetail("Celular: ", os?.cellPhone ?: "-"),
        ItemOSDetail("Telefono: ", os?.phone ?: "-"),
    )
    val parameters2 = listOf(
        ItemOSDetail("Colonia: ", os?.colony ?: ""),
        ItemOSDetail("Detalle: ", os?.observations ?: ""),
    )
    var equipmentString = ""
    if (!os?.equipment.isNullOrEmpty()) {
        for (equipment in os?.equipment!!) {
            equipmentString += "${equipment.equipmentDesc}: ${equipment.equipmentId}\n"
        }
    }
    val parameters3 = listOf(
        ItemOSDetail("Equipos: ", equipmentString),
        ItemOSDetail("Detalle 1: ", os?.osDetail1 ?: ""),
        ItemOSDetail("Detalle 2: ", os?.osDetail2 ?: ""),
    )

    BackHandler(enabled = true) {
        if (doing == true || onWay == true) {
            return@BackHandler
        } else {
            navController.navigate(PerseoScreens.OrderOptions.route) {
                popUpTo(PerseoScreens.OrderOptions.route)
            }
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            PerseoTopBar(
                title = "Orden no. ${if (os != null) os!!.osId else ""}",
                inDashboard = false
            ) {
                if (doing != true && onWay != true) {
                    navController.navigate(PerseoScreens.OrderOptions.route) {
                        popUpTo(PerseoScreens.OrderOptions.route)
                    }
                }
            }
        },
        bottomBar = {
            PerseoBottomBar()
        },
        backgroundColor = Background,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 50.dp, top = if (doing == false) 20.dp else 0.dp)
                .background(Background)
        ) {
            if (doing == true) {
                Row(
                    modifier = Modifier
                        .weight(1.5f)
                        .fillMaxSize()
                        .padding(horizontal = 32.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        modifier = Modifier
                            .height(65.dp)
                            .padding(vertical = 12.dp)
                            .fillMaxHeight()
                            .clickable {
                                navController.navigate(PerseoScreens.Materials.route)
                            },
                        painter = rememberAsyncImagePainter(Constants.BUTTON_MATERIAL), //TODO: Change painter per bitmap
                        contentDescription = null,
                        contentScale = ContentScale.Crop
                    )
                    Image(
                        modifier = Modifier
                            .height(65.dp)
                            .padding(vertical = 12.dp)
                            .fillMaxHeight()
                            .clickable {
                                if (!motivos.isNullOrEmpty()) {
                                    navController.navigate(PerseoScreens.Equipment.route)
                                } else {
                                    Toast
                                        .makeText(
                                            context,
                                            "No se requieren equipos",
                                            Toast.LENGTH_SHORT
                                        )
                                        .show()
                                }
                            },
                        painter = rememberAsyncImagePainter(Constants.BUTTON_EQUIPMENT), //TODO: Change painter per bitmap
                        contentDescription = null,
                        contentScale = ContentScale.Crop
                    )
                }
            }

            Column(
                modifier = Modifier
                    .weight(9.5f)
                    .fillMaxSize()
                    .verticalScroll(scrollState),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                DetailContainer("${os?.name} ${os?.lastName}", parameters)
                DetailContainer("${os?.street ?: ""} #${os?.outdoorNumber ?: ""}", parameters2)
                DetailContainer("Datos de Orden", parameters3)
                if (generalData.isNotEmpty()) {
                    if (onWay == false && doing == false) {
                        Button(
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = Accent,
                                contentColor = Color.White
                            ),
                            onClick = {
                                viewModel.startRoute()
                            }) {
                            Text(
                                modifier = Modifier.padding(6.dp),
                                text = "INICIAR RUTA",
                                fontWeight = FontWeight.Bold
                            )
                        }

                    } else {
                        Image(
                            modifier = Modifier
                                .height(60.dp)
                                .padding(vertical = 6.dp)
                                .fillMaxHeight()
                                .clickable {
                                    if (onWay!! && !doing!!) {
                                        viewModel.finishRoute()
                                        viewModel.startDoing()
                                    } else {
                                        viewModel.finishDoing()
                                        navController.navigate(PerseoScreens.Dashboard.route)
                                    }
                                },
                            painter = rememberAsyncImagePainter(
                                if (doing == true) Constants.BUTTON_FINISH else Constants.BUTTON_START
                            ),
                            contentDescription = null,
                            contentScale = ContentScale.Crop
                        )
                        Image(
                            modifier = Modifier
                                .height(50.dp)
                                .padding(vertical = 6.dp)
                                .fillMaxHeight()
                                .clickable {
                                    viewModel.finishOrder()
                                },
                            painter = rememberAsyncImagePainter(Constants.BUTTON_CANCEL), //TODO: Change painter per bitmap
                            contentDescription = null,
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            }
            Row(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .weight(1f)
                    .fillMaxSize()
                    .background(Background),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Row {
                    Text(text = "Sector: ", color = Color.White)
                    Text(text = os?.sector ?: "", color = Yellow6)
                }
                Row {
                    Text(text = "CT: ", color = Color.White)
                    Text(text = os?.ct ?: "", color = Yellow6)
                }
            }
        }
    }
}
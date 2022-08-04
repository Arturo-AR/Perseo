package com.cv.perseo.screens.equipment

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.cv.perseo.components.EquipmentItem
import com.cv.perseo.components.PerseoBottomBar
import com.cv.perseo.components.PerseoTopBar
import com.cv.perseo.navigation.PerseoScreens
import com.cv.perseo.ui.theme.Background
import com.cv.perseo.ui.theme.Yellow4
import com.cv.perseo.utils.toBitmap

@ExperimentalComposeUiApi
@ExperimentalFoundationApi
@Composable
fun EquipmentScreen(
    navController: NavController,
    viewModel: EquipmentScreenViewModel = hiltViewModel()
) {
    viewModel.initEquipment()

    val scaffoldState = rememberScaffoldState()
    val os by viewModel.currentOs.observeAsState()
    val generalData = viewModel.generalData.collectAsState().value
    val motivos by viewModel.motivos.observeAsState()
    val keyboardController = LocalSoftwareKeyboardController.current
    val currentEquipment by viewModel.equipmentTmp.observeAsState()
    val routers by viewModel.routers.observeAsState()
    val boxes by viewModel.terminalBox.observeAsState()

    if (generalData.isNotEmpty()) {
        viewModel.getRouterBoxes()
    }

    if (os != null && generalData.isNotEmpty()) {
        viewModel.getMotivos(os?.motivoId!!, generalData[0].idMunicipality)
    }
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            PerseoTopBar(
                title = "Equipos",
                inDashboard = false
            ) {
                navController.navigate(PerseoScreens.OSDetails.route) {
                    popUpTo(PerseoScreens.OSDetails.route)
                }
            }
        },
        bottomBar = {
            if (generalData.isNotEmpty()){
                PerseoBottomBar(
                    enterprise = generalData[0].municipality,
                    enterpriseIcon = generalData[0].logo
                )
            }
        },
        backgroundColor = Background,
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (!motivos.isNullOrEmpty() && !boxes.isNullOrEmpty() && !routers.isNullOrEmpty()) {
                LazyVerticalGrid(
                    cells = GridCells.Fixed(2),
                    contentPadding = PaddingValues(16.dp)
                ) {
                    items(motivos!!.size) { index ->
                        EquipmentItem(
                            motivo = motivos!![index],
                            boxes = boxes!!,
                            routers = routers!!,
                            oldBitmap = currentEquipment?.find { it.equipment == motivos!![index] }?.image?.toBitmap(),
                            idMotivo = currentEquipment?.find { it.equipment == motivos!![index] }?.idEquipment
                                ?: "",
                            onAction = {
                                viewModel.saveTmp(
                                    equipment = viewModel.getEquipmentType(motivos!![index]),
                                    idEquipment = viewModel.getIdEquipment(it, motivos!![index]),
                                    image = null
                                )
                                keyboardController?.hide()
                            }) {
                            viewModel.saveTmp(
                                equipment = viewModel.getEquipmentType(motivos!![index]),
                                idEquipment = null,
                                image = it
                            )
                        }
                    }
                }
            }
            Button(colors = ButtonDefaults.buttonColors(
                backgroundColor = Yellow4,
                contentColor = Color.Black
            ), onClick = {
                viewModel.saveEquipmentInDatabase()
            }) {
                Text(text = "Agregar")
            }
            Button(colors = ButtonDefaults.buttonColors(
                backgroundColor = Yellow4,
                contentColor = Color.Black
            ), onClick = {
                viewModel.validateEquipment()
            }) {
                Text(text = "Validar")
            }
        }
    }
}
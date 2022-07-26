package com.cv.perseo.screens.equipment

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.cv.perseo.components.EquipmentItem
import com.cv.perseo.components.PerseoBottomBar
import com.cv.perseo.components.PerseoTopBar
import com.cv.perseo.components.TextFieldWithDropdownUsage
import com.cv.perseo.model.EquipmentTmp
import com.cv.perseo.navigation.PerseoScreens
import com.cv.perseo.ui.theme.Background
import com.cv.perseo.ui.theme.Yellow4

@ExperimentalComposeUiApi
@ExperimentalFoundationApi
@Composable
fun EquipmentScreen(
    navController: NavController,
    viewModel: EquipmentScreenViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()
    val os by viewModel.currentOs.observeAsState()
    val generalData = viewModel.generalData.collectAsState().value
    val motivos by viewModel.motivos.observeAsState()
    val keyboardController = LocalSoftwareKeyboardController.current

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
            PerseoBottomBar()
        },
        backgroundColor = Background,
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (!motivos.isNullOrEmpty()) {
                LazyVerticalGrid(
                    cells = GridCells.Fixed(2),
                    contentPadding = PaddingValues(16.dp)
                ) {
                    items(motivos!!) { motivo ->
                        EquipmentItem(
                            motivo = motivo,
                            onAction = {
                                viewModel.saveTmp(
                                    equipment = motivo,
                                    idEquipment = it,
                                    image = null
                                )
                                keyboardController?.hide()
                            }) {
                            viewModel.saveTmp(
                                equipment = motivo,
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
            ), onClick = { Log.d("Equipos", viewModel.equipmentTmp.value.toString()) }) {
                Text(text = "Agregar")
            }
        }

    }
}
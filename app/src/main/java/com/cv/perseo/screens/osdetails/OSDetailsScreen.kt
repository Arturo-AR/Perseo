package com.cv.perseo.screens.osdetails

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.cv.perseo.components.DetailContainer
import com.cv.perseo.components.PerseoBottomBar
import com.cv.perseo.components.PerseoTopBar
import com.cv.perseo.model.ItemOSDetail
import com.cv.perseo.navigation.PerseoScreens
import com.cv.perseo.ui.theme.Background
import com.cv.perseo.ui.theme.Yellow3
import com.cv.perseo.utils.Constants

@Composable
fun OSDetailsScreen(
    navController: NavController,
    viewModel: OSDetailsScreenViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()
    val scrollState = rememberScrollState()
    val idOs by viewModel.currentOs.observeAsState()

    val parameters = listOf(
        ItemOSDetail("Contrato No: ", "34434"),
        ItemOSDetail("Estatus: ", "POR INSTALAR"),
        ItemOSDetail("Celular: ", "4423736927"),
        ItemOSDetail("Telefono: ", "3237327"),
    )
    val parameters2 = listOf(
        ItemOSDetail("Colonia: ", "Estrella"),
        ItemOSDetail("Detalle: ", "FRENTE A UN PARQUE"),
    )
    val parameters3 = listOf(
        ItemOSDetail("Equipos: ", "Sin equipos"),
        ItemOSDetail("Detalle 1: ", "Sin detalle"),
    )

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            PerseoTopBar(
                title = "Orden no.${if (idOs != null) idOs else ""}",
                inDashboard = false
            ) {
                navController.popBackStack()
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
                .padding(bottom = 50.dp)
                .background(Background)
        ) {
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
                            navController.navigate(PerseoScreens.Equipment.route)
                        },
                    painter = rememberAsyncImagePainter(Constants.BUTTON_EQUIPMENT), //TODO: Change painter per bitmap
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
            }
            Column(
                modifier = Modifier
                    .weight(9.5f)
                    .fillMaxSize()
                    .verticalScroll(scrollState),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                DetailContainer("Juan Perez", parameters)
                DetailContainer("JUAN DE SILVA #324", parameters2)
                DetailContainer("Datos de Orden", parameters3)
                Image(
                    modifier = Modifier
                        .height(60.dp)
                        .padding(vertical = 6.dp)
                        .fillMaxHeight()
                        .clickable {

                        },
                    painter = rememberAsyncImagePainter(Constants.BUTTON_FINISH), //TODO: Change painter per bitmap
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
                Image(
                    modifier = Modifier
                        .height(50.dp)
                        .padding(vertical = 6.dp)
                        .fillMaxHeight()
                        .clickable {

                        },
                    painter = rememberAsyncImagePainter(Constants.BUTTON_CANCEL), //TODO: Change painter per bitmap
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
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
                    Text(text = "A58TRG5", color = Yellow3)
                }
                Row {
                    Text(text = "CT: ", color = Color.White)
                    Text(text = "23423535", color = Yellow3)
                }
            }
        }
    }
}
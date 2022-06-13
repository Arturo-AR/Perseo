package com.cv.perseo.screens.osdetails

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.cv.perseo.components.DetailContainer
import com.cv.perseo.model.ItemOSDetail

@Composable
fun OSDetailsScreen(navController: NavController) {

    val scrollState= rememberScrollState()

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

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        DetailContainer("Juan Perez", parameters)
        DetailContainer("JUAN DE SILVA #324", parameters2)
        DetailContainer("Datos de Orden", parameters3)

    }
}
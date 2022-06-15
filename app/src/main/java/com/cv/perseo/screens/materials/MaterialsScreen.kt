package com.cv.perseo.screens.materials

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.cv.perseo.components.MaterialsAddItem
import com.cv.perseo.components.PerseoBottomBar
import com.cv.perseo.components.PerseoTopBar
import com.cv.perseo.navigation.PerseoScreens
import com.cv.perseo.ui.theme.Background
import com.cv.perseo.ui.theme.Yellow4

@Composable
fun MaterialsScreen(navController: NavController) {
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            PerseoTopBar(
                title = "Materiales",
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
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier.padding(bottom = 8.dp),
                text = "Agregar Material",
                color = Yellow4,
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp
            )
            MaterialsAddItem()
        }
    }
}
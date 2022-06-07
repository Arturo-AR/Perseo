package com.cv.perseo.screens.rubro

import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.cv.perseo.components.PerseoBottomBar
import com.cv.perseo.components.PerseoTopBar
import com.cv.perseo.navigation.PerseoScreens
import com.cv.perseo.ui.theme.Background

@Composable
fun RubroScreen(navController: NavController) {
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            PerseoTopBar(
                title = "Rubros",
                inDashboard = false
            ) {
                navController.navigate(PerseoScreens.Zone.route) {
                    popUpTo(PerseoScreens.Zone.route)
                }
            }
        },
        bottomBar = {
            PerseoBottomBar()
        },
        backgroundColor = Background,
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
/*            StandardBackground(
                modifier = Modifier
                    .height(70.dp)
                    .width(120.dp),
                colorBackground = Yellow5,
                colorBorder = Color.DarkGray
            )*/
        }
    }
}
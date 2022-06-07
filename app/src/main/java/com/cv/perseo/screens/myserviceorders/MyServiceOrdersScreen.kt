package com.cv.perseo.screens.myserviceorders

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.cv.perseo.components.PerseoBottomBar
import com.cv.perseo.components.PerseoTopBar
import com.cv.perseo.components.ZonesButtons
import com.cv.perseo.navigation.PerseoScreens
import com.cv.perseo.ui.theme.Background

@ExperimentalFoundationApi
@Composable
fun MyServiceOrdersScreen(navController: NavController) {
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            PerseoTopBar(
                title = "Mis Ordenes",
                inDashboard = false
            ) {
                navController.navigate(PerseoScreens.ServiceOrders.route) {
                    popUpTo(PerseoScreens.ServiceOrders.route)
                }
            }
        },
        bottomBar = {
            PerseoBottomBar()
        },
        backgroundColor = Background,
    ) {
        ZonesButtons(navController = navController)
/*        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clickable {},
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = rememberAsyncImagePainter("http://servermrl.no-ip.org/perseo/webservices/aplicacion/images/fondos/cuadro.png"), //TODO: Change painter per bitmap
                    contentDescription = null
                )
                Row {
                    Text(
                        text = "Prados verdes jeje ",
                        color = Color.Black,
                        style = MaterialTheme.typography.subtitle1
                    )
                    Icon(imageVector = Icons.Default.LocationOn, contentDescription = null)
                }

            }
        }*/
    }
}
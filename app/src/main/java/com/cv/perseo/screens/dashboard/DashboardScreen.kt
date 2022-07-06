package com.cv.perseo.screens.dashboard

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.cv.perseo.components.*
import com.cv.perseo.navigation.PerseoScreens
import com.cv.perseo.ui.theme.Accent
import com.cv.perseo.ui.theme.Background
import com.cv.perseo.ui.theme.ButtonText
import com.cv.perseo.ui.theme.TextColor
import com.cv.perseo.utils.Constants
import kotlinx.coroutines.launch

@ExperimentalFoundationApi
@Composable
fun DashboardScreen(
    navController: NavController,
    viewModel: DashboardScreenViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val openDialog = remember {
        mutableStateOf(false)
    }

    BackHandler {
        openDialog.value = !openDialog.value
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            PerseoTopBar(
                title = "Test",
                inDashboard = true
            ) {
                scope.launch {
                    scaffoldState.drawerState.open()
                }
            }
        },
        drawerContent = { DrawerView(navController, viewModel) },
        bottomBar = {
            PerseoBottomBar()
        },
        backgroundColor = Background,
    ) {
        if (openDialog.value) {
            ShowAlertDialog(
                title = "Alerta",
                message = "Desea salir de la aplicacion ?",
                openDialog = openDialog
            ) {
                //TODO: Close app when user tap accept
            }
        }
        ButtonsList(
            navController,
            listOf(Constants.SERVICE_ORDERS, Constants.INVENTORY, Constants.SUBSCRIBER)
        )
    }
}

@Composable
fun DrawerView(navController: NavController, viewModel: DashboardScreenViewModel) {

    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
            .padding(vertical = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Card(
            modifier = Modifier
                .width(90.dp)
                .height(90.dp)
                .clickable { },
            elevation = 8.dp,
            shape = RoundedCornerShape(8.dp),
            border = BorderStroke(1.dp, Color.LightGray)
        ) {
            LogoPerseo(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Perseo App", color = ButtonText, style = MaterialTheme.typography.h6)
        Spacer(modifier = Modifier.height(32.dp))
        AddDrawerHeader(title = "Cambiar de ciudad", icon = Icons.Default.Edit) {
            Toast.makeText(context, "Cambiar ciudad", Toast.LENGTH_SHORT).show()
        }
        Divider(color = Accent)
        AddDrawerHeader(title = "Cerrar Sesion", icon = Icons.Default.Close) {
            viewModel.signOut()
            navController.navigate(PerseoScreens.Login.route)
        }
    }
}

@Composable
fun AddDrawerHeader(
    title: String,
    icon: ImageVector,
    titleColor: Color = TextColor,
    onPress: () -> Unit
) {
    Card(
        elevation = 0.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clickable {
                onPress.invoke()
            },
        backgroundColor = Background
    ) {
        Row(modifier = Modifier.padding(vertical = 16.dp)) {
            Icon(imageVector = icon, contentDescription = null, tint = titleColor)
            Spacer(modifier = Modifier.width(32.dp))
            Text(
                text = title,
                style = TextStyle(
                    fontSize = 14.sp,
                    color = titleColor
                )
            )
        }
    }
}
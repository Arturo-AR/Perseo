package com.cv.perseo.screens.dashboard

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.cv.perseo.components.DefaultButton
import com.cv.perseo.components.LogoPerseo
import com.cv.perseo.components.PerseoBottomBar
import com.cv.perseo.components.PerseoTopBar
import com.cv.perseo.navigation.PerseoScreens
import com.cv.perseo.ui.theme.Accent
import com.cv.perseo.ui.theme.Background
import com.cv.perseo.ui.theme.ButtonText
import com.cv.perseo.ui.theme.TextColor
import kotlinx.coroutines.launch

@ExperimentalFoundationApi
@Composable
fun DashboardScreen(navController: NavController) {

    val context = LocalContext.current
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

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
        drawerContent = { DrawerView() },
        bottomBar = {
            PerseoBottomBar()
        },
        backgroundColor = Background,
    ) {

        val list = listOf("Mis Ordenes", "Cumplimiento OS", "Inventario", "Ordenes de servicio")
        LazyVerticalGrid(
            cells = GridCells.Fixed(2),
            contentPadding = PaddingValues(8.dp)
        ) {
            items(list.size) { index ->
                DefaultButton(text = list[index]) {
                    when (list[index]) {
                        "Inventario" -> navController.navigate(PerseoScreens.Inventory.name)
                        else -> Toast.makeText(context, list[index], Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        //Text(text = "En dashboard")// TODO add dashboard content
    }
}

@Composable
fun DrawerView() {

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
            Toast.makeText(context, "Cerrar sesion", Toast.LENGTH_SHORT).show()
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
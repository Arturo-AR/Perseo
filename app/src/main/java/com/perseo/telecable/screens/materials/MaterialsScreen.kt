package com.perseo.telecable.screens.materials

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.perseo.telecable.components.*
import com.perseo.telecable.navigation.PerseoScreens
import com.perseo.telecable.ui.theme.Background
import com.perseo.telecable.ui.theme.Yellow4
import com.perseo.telecable.utils.toast

@ExperimentalComposeUiApi
@Composable
fun MaterialsScreen(
    navController: NavController,
    viewModel: MaterialsScreenViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()
    val keyboardController = LocalSoftwareKeyboardController.current
    val materialsSaved by viewModel.materialSaved.collectAsState()
    val inventory by viewModel.inventory.observeAsState()
    val materialTmp by viewModel.materialTmp.observeAsState()
    val material by viewModel.material.observeAsState()
    val context = LocalContext.current
    viewModel.getMaterialsSaved()
    viewModel.getInventory()
    viewModel.getMaterial()
    val generalData by viewModel.data.observeAsState()
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
            if (generalData != null) {
                PerseoBottomBar(
                    enterprise = generalData?.municipality!!,
                    enterpriseIcon = generalData?.logo!!
                )
            }
        },
        backgroundColor = Background,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 16.dp, top = 0.dp, end = 16.dp, bottom = 55.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.Center) {
                Text(
                    modifier = Modifier.padding(bottom = 8.dp),
                    text = "Agregar Material",
                    color = Yellow4,
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp
                )
            }
            Column(modifier = Modifier.weight(7f), verticalArrangement = Arrangement.Center) {
                if (!material.isNullOrEmpty() && materialsSaved != null) {
                    MaterialsList(material!!, materialsSaved) { amount, material ->
                        try {
                            if ((inventory?.find { it.materialDesc == material.materialDesc }?.amount
                                    ?: 0) >= amount.toDouble()
                            ) {
                                viewModel.saveTmp(
                                    materialId = material.materialId,
                                    materialDesc = material.materialDesc,
                                    amount.toDouble()
                                )
                                context.toast("Registrado")
                            } else {
                                context.toast("materiales insuficientes")
                            }
                        } catch (ex: Exception) {
                            ex.printStackTrace()
                            context.toast("cantidad no valida")
                        }
                        keyboardController?.hide()
                    }
                } else {
                    CircularProgressIndicator(
                        modifier = Modifier.size(80.dp),
                        color = Yellow4,
                        strokeWidth = 8.dp
                    )
                }
            }
            Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.Center) {
                Button(
                    onClick = {
                        viewModel.saveMaterialsInDatabase()
                        context.toast("Materiales registrados correctamente")
                        Thread.sleep(1500)
                        navController.navigate(PerseoScreens.OSDetails.route)
                    },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Yellow4
                    )
                ) {
                    Text(text = "Agregar")
                }
            }
        }
    }
}
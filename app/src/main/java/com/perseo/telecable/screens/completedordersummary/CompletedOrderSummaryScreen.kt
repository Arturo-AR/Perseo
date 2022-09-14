package com.perseo.telecable.screens.completedordersummary

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.perseo.telecable.R
import com.perseo.telecable.components.*
import com.perseo.telecable.model.SummaryItems
import com.perseo.telecable.navigation.PerseoScreens
import com.perseo.telecable.ui.theme.Background
import com.perseo.telecable.ui.theme.Yellow4
import com.perseo.telecable.ui.theme.Yellow6Hint
import com.perseo.telecable.utils.Constants
import com.perseo.telecable.utils.toEquipmentFormat
import com.perseo.telecable.utils.toast

@Composable
fun CompletedOrderSummaryScreen(
    navController: NavController,
    viewModel: CompletedOrderSummaryScreenViewModel = hiltViewModel()
) {
    viewModel.getMaterials()
    viewModel.getEquipment()
    val scaffoldState = rememberScaffoldState()
    val generalData by viewModel.data.observeAsState()
    val openDialogFinish = remember { mutableStateOf(false) }
    val material by viewModel.material.observeAsState()
    val equipment by viewModel.equipment.observeAsState()
    val loading = remember { mutableStateOf(false) }
    var titular by rememberSaveable {
        mutableStateOf(false)
    }
    val firma = navController.currentBackStackEntry
        ?.savedStateHandle
        ?.getLiveData<Bitmap>("FIRMA")?.observeAsState()

    val context = LocalContext.current
    if (openDialogFinish.value) {
        ShowAlertDialog(
            title = "Alerta",
            message = {
                Text(
                    text = "Desea finalizar la orden de serivcio?",
                    color = White,
                    fontSize = 18.sp
                )
            },
            openDialog = openDialogFinish,
            positiveButtonText = "Finalizar"
        ) {
            openDialogFinish.value = false
/*            loading.value = true
            try {
                viewModel.finishOrder {
                    loading.value = false
                    navController.navigate(PerseoScreens.OrderOptions.route) {
                        popUpTo(PerseoScreens.OrderOptions.route)
                    }
                }
            } catch (ex: Exception) {
                ex.printStackTrace()
            }*/
        }
    }
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            PerseoTopBar(
                title = stringResource(id = R.string.summary),
                inDashboard = false,
                textColor = White
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
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (!material.isNullOrEmpty()) {
                SummaryBox(
                    title = "Materiales",
                    list = material!!.map {
                        SummaryItems(
                            itemDesc = it.desc_material ?: "",
                            value = it.cantidad.toString()
                        )
                    })
            }
            if (!equipment.isNullOrEmpty()) {
                SummaryBox(
                    title = "Equipos",
                    list = equipment!!.map {
                        SummaryItems(
                            itemDesc = it.id_tipo_equipo.toEquipmentFormat(),
                            value = it.id_equipo
                        )
                    })
            }
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    modifier = Modifier.weight(1f),
                    onClick = {
                        navController.navigate(PerseoScreens.Signature.route)
                        //context.toast("PROXIMAMENTE")
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Yellow4)
                ) {
                    Text(text = "Firmar")
                }
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Titular ?",
                        color = White
                    )
                    Checkbox(
                        enabled = true,
                        checked = titular, onCheckedChange = { titular = !titular },
                        colors = CheckboxDefaults.colors(
                            checkedColor = Yellow4,
                            checkmarkColor = Black,
                            uncheckedColor = Yellow6Hint
                        )
                    )
                }
                Box(
                    modifier = Modifier
                        .weight(2f)
                ) {
                    firma?.value?.let { sign ->
                        Image(
                            modifier = Modifier
                                .padding(8.dp)
                                .clip(
                                    RoundedCornerShape(
                                        topStart = 10.dp,
                                        topEnd = 10.dp,
                                        bottomStart = 10.dp,
                                        bottomEnd = 10.dp
                                    )
                                ),
                            bitmap = sign.asImageBitmap(),
                            contentDescription = null
                        )
                    }
                }

            }
            Button(
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(start = 16.dp),
                onClick = { context.toast("PROXIMAMENTE") },
                colors = ButtonDefaults.buttonColors(backgroundColor = Yellow4)
            ) {
                Text(text = "Encuesta")
            }
            Image(
                modifier = Modifier
                    .height(60.dp)
                    .padding(vertical = 6.dp)
                    .fillMaxHeight()
                    .clickable {
                        openDialogFinish.value = true
                    },
                painter = rememberAsyncImagePainter(
                    Constants.PERSEO_BASE_URL + Constants.BUTTON_FINISH
                ),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
        }
    }
}
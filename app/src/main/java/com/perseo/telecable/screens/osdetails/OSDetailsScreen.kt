package com.perseo.telecable.screens.osdetails

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.os.Build
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.perseo.telecable.components.*
import com.perseo.telecable.model.ItemOSDetail
import com.perseo.telecable.navigation.PerseoScreens
import com.perseo.telecable.ui.theme.*
import com.perseo.telecable.utils.Constants
import com.perseo.telecable.utils.toBase64String
import com.perseo.telecable.utils.toast

@ExperimentalComposeUiApi
@ExperimentalFoundationApi
@Composable
fun OSDetailsScreen(
    navController: NavController,
    viewModel: OSDetailsScreenViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()
    val scrollState = rememberScrollState()
    val openDialogRoute = remember { mutableStateOf(false) }
    val openDialogStart = remember { mutableStateOf(false) }
    val openDialogFinish = remember { mutableStateOf(false) }
    val openDialogCancel = remember { mutableStateOf(false) }
    val loading = remember { mutableStateOf(false) }
    var cancelReason by remember { mutableStateOf("") }
    val cancelImages by viewModel.cancelImages.observeAsState()
    val doing by viewModel.doing.observeAsState()
    val onWay by viewModel.onWay.observeAsState()
    val os by viewModel.currentOs.observeAsState()
    val generalData = viewModel.generalData.collectAsState().value
    val motivos by viewModel.motivos.observeAsState()
    val subscriberImages by viewModel.subscriberImages.observeAsState()
    val context = LocalContext.current
    val currentLocation by viewModel.getLocationLiveData().observeAsState()
    viewModel.startLocationUpdates()
    viewModel.updateImages()
    viewModel.updateMaterials()
    viewModel.updateInfo()
    val imagesList by viewModel.images.observeAsState()
    if (os != null && generalData.isNotEmpty()) {
        viewModel.updateEquipment()
        viewModel.updateAllEquipment()
        viewModel.getMotivos(os?.motivoId!!, generalData[0].idMunicipality)
        viewModel.getSubscriberImages()
    }

    val parameters = listOf(
        ItemOSDetail("Contrato No: ", os?.noContract.toString()),
        ItemOSDetail("Estatus: ", os?.status ?: ""),
        ItemOSDetail("Paquete: ", os?.packageName ?: ""),
        ItemOSDetail("Celular: ", os?.cellPhone ?: "-"),
        ItemOSDetail("Telefono: ", os?.phone ?: "-"),
    )
    val parameters2 = listOf(
        ItemOSDetail("Colonia: ", os?.colony ?: ""),
        ItemOSDetail("Detalle: ", os?.observations ?: ""),
    )
    var equipmentString = ""
    if (!os?.equipment.isNullOrEmpty()) {
        for (equipment in os?.equipment!!) {
            equipmentString += "${equipment.equipmentDesc}: ${equipment.equipmentId}\n"
        }
    }
    val parameters3 = listOf(
        ItemOSDetail("Equipos: ", equipmentString),
        ItemOSDetail("Detalle 1: ", os?.osDetail1 ?: ""),
        ItemOSDetail("Detalle 2: ", os?.osDetail2 ?: ""),
    )

    BackHandler(enabled = true) {
        if (doing == true || onWay == true) {
            return@BackHandler
        } else {
            navController.navigate(PerseoScreens.OrderOptions.route) {
                popUpTo(PerseoScreens.OrderOptions.route)
            }
        }
    }

    if (openDialogRoute.value) {
        ShowAlertDialog(
            title = "Alerta",
            message = {
                Text(
                    text = "Desea iniciar la ruta hacia esta orden?",
                    color = Color.White,
                    fontSize = 18.sp
                )
            },
            openDialog = openDialogRoute
        ) {
            viewModel.startRoute()
            openDialogRoute.value = false
        }
    }

    if (openDialogStart.value) {
        ShowAlertDialog(
            title = "Alerta",
            message = {
                Text(
                    text = "Desea iniciar la orden de servicio?",
                    color = Color.White,
                    fontSize = 18.sp
                )
            },
            openDialog = openDialogStart
        ) {
            viewModel.finishRoute()
            viewModel.startDoing()
            viewModel.startCompliance()
            openDialogStart.value = false
        }
    }

    if (openDialogFinish.value) {
        ShowAlertDialog(
            title = "Alerta",
            message = {
                Text(
                    text = "Desea finalizar la orden de serivcio?",
                    color = Color.White,
                    fontSize = 18.sp
                )
            },
            openDialog = openDialogFinish,
            positiveButtonText = "Finalizar"
        ) {
            openDialogFinish.value = false
            loading.value = true
            try {
                viewModel.finishOrder {
                    loading.value = false
                    navController.navigate(PerseoScreens.OrderOptions.route) {
                        popUpTo(PerseoScreens.OrderOptions.route)
                    }
                }
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }
    }

    if (openDialogCancel.value) {
        ShowAlertDialog(
            title = "Cancelar Orden",
            message = {
                Column(
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Ingrese el motivo de cancelacion:",
                        color = Color.White,
                        fontSize = 18.sp
                    )
                    TextField(
                        value = cancelReason,
                        placeholder = {
                            Text(
                                text = "Motivo",
                                color = ButtonText
                            )
                        },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Done
                        ),
                        onValueChange = {
                            cancelReason = it
                        }, colors = TextFieldDefaults.textFieldColors(
                            textColor = Color.White,
                            cursorColor = Yellow4,
                            focusedIndicatorColor = Yellow4
                        )
                    )
                    RequestContentPermissionCancelList(
                        bitmapList = cancelImages,
                        returnUri = {
                            var bitmap: Bitmap? = null
                            if (Build.VERSION.SDK_INT < 28) {
                                bitmap = MediaStore.Images
                                    .Media.getBitmap(context.contentResolver, it)

                            } else {
                                val source = it?.let { it1 ->
                                    ImageDecoder
                                        .createSource(context.contentResolver, it1)
                                }
                                source?.let { it1 -> ImageDecoder.decodeBitmap(it1) }
                                    ?.let { it2 -> bitmap = it2 }
                            }

                            viewModel.addCancelImage(bitmap)
                            //imagesList.add(bitmap)
                        },
                        onReturn = { bitmap ->
                            viewModel.deleteCancelImage(bitmap)
                        }
                    )
                }
            },
            positiveButtonText = "Cancelar",
            negativeButtonText = "Regresar",
            openDialog = openDialogCancel
        ) {
            if (cancelReason != "" && cancelImages?.isNotEmpty()!!) {
                loading.value = true
                openDialogCancel.value = false
                viewModel.cancelOrder(
                    reason = cancelReason,
                    images = cancelImages?.map { it.toBase64String() }!!
                ) {
                    navController.navigate(PerseoScreens.OrderOptions.route) {
                        popUpTo(PerseoScreens.OrderOptions.route)
                    }
                    loading.value = false
                }
            } else if (cancelReason == "") {
                context.toast("Ingrese el motivo")
            } else {
                context.toast("Agregue al menos una imagen")
            }
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            PerseoTopBar(
                title = "Orden no. ${if (os != null) os!!.osId else ""}"
            ) {
                if (doing != true && onWay != true) {
                    navController.navigate(PerseoScreens.OrderOptions.route) {
                        popUpTo(PerseoScreens.OrderOptions.route)
                    }
                }
            }
        },
        bottomBar = {
            if (generalData.isNotEmpty()) {
                PerseoBottomBar(
                    enterprise = generalData[0].municipality,
                    enterpriseIcon = generalData[0].logo
                )
            }
        },
        backgroundColor = Background,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 50.dp, top = if (doing == false) 20.dp else 0.dp)
                .background(Background)
        ) {
            if (doing == true) {
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
                        painter = rememberAsyncImagePainter(Constants.PERSEO_BASE_URL + Constants.BUTTON_MATERIAL),
                        contentDescription = null,
                        contentScale = ContentScale.Crop
                    )
                    Image(
                        modifier = Modifier
                            .height(65.dp)
                            .padding(vertical = 12.dp)
                            .fillMaxHeight()
                            .clickable {
                                if (!motivos.isNullOrEmpty()) {
                                    navController.navigate(PerseoScreens.Equipment.route)
                                } else {
                                    Toast
                                        .makeText(
                                            context,
                                            "No se requieren equipos",
                                            Toast.LENGTH_SHORT
                                        )
                                        .show()
                                }
                            },
                        painter = rememberAsyncImagePainter(Constants.PERSEO_BASE_URL + Constants.BUTTON_EQUIPMENT),
                        contentDescription = null,
                        contentScale = ContentScale.Crop
                    )
                }
            }

            Column(
                modifier = Modifier
                    .weight(9.5f)
                    .fillMaxSize()
                    .verticalScroll(scrollState),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                DetailContainer("${os?.name} ${os?.lastName}", parameters)
                DetailContainer("${os?.street ?: ""} #${os?.outdoorNumber ?: ""}", parameters2)
                DetailContainer("Datos de Orden", parameters3)
                if (!subscriberImages.isNullOrEmpty()) {
                    DetailContainerImages("Imagenes del Abonado", subscriberImages!!)
                }
                if (doing == true) {
                    RequestContentPermissionList(bitmapList = imagesList!!, returnUri = {
                        var bitmap: Bitmap? = null
                        if (Build.VERSION.SDK_INT < 28) {
                            bitmap = MediaStore.Images
                                .Media.getBitmap(context.contentResolver, it)

                        } else {
                            val source = it?.let { it1 ->
                                ImageDecoder
                                    .createSource(context.contentResolver, it1)
                            }
                            source?.let { it1 -> ImageDecoder.decodeBitmap(it1) }
                                ?.let { it2 -> bitmap = it2 }
                        }
                        try {
                            viewModel.saveImages("EXTRA", bitmap?.toBase64String()!!)
                        } catch (ex: Exception) {
                            ex.printStackTrace()
                        }
                    }) {
                        try {
                            viewModel.deleteImage(it)
                        } catch (ex: Exception) {
                            ex.printStackTrace()
                        }
                    }
                }


                if (generalData.isNotEmpty()) {
                    if (onWay == false && doing == false) {
                        Button(
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = Accent,
                                contentColor = Color.White
                            ),
                            onClick = {
                                openDialogRoute.value = true
                            }) {
                            Text(
                                modifier = Modifier.padding(6.dp),
                                text = "INICIAR RUTA",
                                fontWeight = FontWeight.Bold
                            )
                        }

                    } else {
                        Image(
                            modifier = Modifier
                                .height(60.dp)
                                .padding(vertical = 6.dp)
                                .fillMaxHeight()
                                .clickable {
                                    if (onWay!! && !doing!!) {
                                        openDialogStart.value = true
                                    } else {
                                        openDialogFinish.value = true
                                        //viewModel.finishDoing()
                                        //navController.navigate(PerseoScreens.Dashboard.route)
                                    }
                                },
                            painter = rememberAsyncImagePainter(
                                Constants.PERSEO_BASE_URL +
                                        if (doing == true) Constants.BUTTON_FINISH else Constants.BUTTON_START
                            ),
                            contentDescription = null,
                            contentScale = ContentScale.Crop
                        )
                        Image(
                            modifier = Modifier
                                .height(50.dp)
                                .padding(vertical = 6.dp)
                                .fillMaxHeight()
                                .clickable {
                                    openDialogCancel.value = true
                                },
                            painter = rememberAsyncImagePainter(Constants.PERSEO_BASE_URL + Constants.BUTTON_CANCEL),
                            contentDescription = null,
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            }
            Column {
                Text(text = "Column perrona")
                currentLocation.let {
                    Text(text = it?.latitude.toString(), color = Color.White)
                    Text(text = it?.longitude.toString(), color = Color.White)
                }
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
                    Text(text = os?.sector ?: "", color = Yellow6)
                }
                Row {
                    Text(text = "CT: ", color = Color.White)
                    Text(text = os?.ct ?: "", color = Yellow6)
                }
            }
        }
        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
            if (loading.value) {
                CircularProgressIndicator(
                    modifier = Modifier.size(80.dp),
                    color = Yellow4,
                    strokeWidth = 8.dp
                )
            }
        }
    }
}
package com.cv.perseo.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.Color.Companion.Yellow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.cv.perseo.data.Data
import com.cv.perseo.model.ItemOSDetail
import com.cv.perseo.model.Material
import com.cv.perseo.model.ServiceOrder
import com.cv.perseo.navigation.PerseoScreens
import com.cv.perseo.ui.theme.*
import com.cv.perseo.utils.Constants

@Composable
fun LogoPerseo(modifier: Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = rememberAsyncImagePainter(Constants.LOGO_PERSEO),
            contentDescription = "Logo Perseo"
        )
    }
}

@Composable
fun PasswordInput(
    modifier: Modifier,
    passwordState: MutableState<String>,
    labelId: String,
    enable: Boolean,
    passwordVisibility: MutableState<Boolean>,
    imeAction: ImeAction = ImeAction.Done,
    onAction: KeyboardActions = KeyboardActions.Default,
) {
    val visualTransformation = if (passwordVisibility.value) VisualTransformation.None else
        PasswordVisualTransformation()

    TextField(
        value = passwordState.value, onValueChange = {
            passwordState.value = it
        },
        label = { Text(text = labelId) },
        singleLine = true,
        textStyle = TextStyle(fontSize = 18.sp, color = Color.White),
        modifier = modifier
            .padding(bottom = 10.dp, start = 10.dp, end = 10.dp)
            .fillMaxWidth(),
        enabled = enable,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = imeAction
        ),
        visualTransformation = visualTransformation,
        trailingIcon = {
            PasswordVisibility(passwordVisibility = passwordVisibility)
        },
        keyboardActions = onAction,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color.Black,
            focusedLabelColor = Yellow3,
            unfocusedBorderColor = Color.Black,
            unfocusedLabelColor = Color.LightGray,
            backgroundColor = Accent
        )
    )
}

@Composable
fun EmailInput(
    modifier: Modifier = Modifier,
    emailState: MutableState<String>,
    labelId: String = "Email",
    enable: Boolean = true,
    imeAction: ImeAction = ImeAction.Next,
    onAction: KeyboardActions = KeyboardActions.Default
) {
    InputField(
        modifier = modifier,
        valueState = emailState,
        labelId = labelId,
        enable = enable,
        keyboardType = KeyboardType.Email,
        imeAction = imeAction,
        onAction = onAction
    )
}

@Composable
fun InputField(
    modifier: Modifier = Modifier,
    valueState: MutableState<String>,
    labelId: String,
    enable: Boolean,
    isSingleLine: Boolean = true,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
    onAction: KeyboardActions = KeyboardActions.Default
) {
    TextField(
        value = valueState.value, onValueChange = { valueState.value = it },
        label = { Text(text = labelId) },
        singleLine = isSingleLine,
        textStyle = TextStyle(fontSize = 18.sp, color = Color.White),
        modifier = modifier
            .padding(bottom = 10.dp, start = 10.dp, end = 10.dp)
            .fillMaxWidth(),
        enabled = enable,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = imeAction),
        keyboardActions = onAction,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color.Black,
            focusedLabelColor = Yellow3,
            unfocusedBorderColor = Color.Black,
            unfocusedLabelColor = Color.LightGray,
            backgroundColor = Accent
        )
    )
}

@Composable
fun PasswordVisibility(passwordVisibility: MutableState<Boolean>) {
    val visible = passwordVisibility.value
    IconButton(onClick = { passwordVisibility.value = !visible }) {
        Icon(
            imageVector = Icons.Default.Edit,
            contentDescription = "Icon to show password",
            tint = Color.Black
        )
    }
}

@Composable
fun PerseoTopBar(
    title: String,
    inDashboard: Boolean = false,
    onBackArrowClicked: () -> Unit = {}
) {
    TopAppBar(
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                if (inDashboard) {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.clickable {
                            onBackArrowClicked.invoke()
                        }
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Arrow Back",
                        tint = Color.White,
                        modifier = Modifier.clickable {
                            onBackArrowClicked.invoke()
                        }
                    )
                }

                Text(
                    text = title,
                    color = Yellow4,
                    style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 24.sp)
                )
                LogoPerseo(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(vertical = 8.dp)
                )
            }
        },
        modifier = Modifier.height(70.dp),
        backgroundColor = Accent,
        elevation = 0.dp
    )
}

@Composable
fun PerseoBottomBar(
    enterprise: String = "TEST",
    enterpriseIcon: String = Constants.LOGO_TELECABLE
) {
    BottomAppBar(
        modifier = Modifier
            .height(50.dp)
            .clip(
                RoundedCornerShape(
                    topStart = 10.dp,
                    topEnd = 10.dp,
                    bottomStart = 0.dp,
                    bottomEnd = 0.dp
                )
            ),
        backgroundColor = Accent,
        content = {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = enterprise,
                    color = Yellow3,
                    style = MaterialTheme.typography.h6,
                    fontWeight = FontWeight.Bold
                )
                Image(
                    painter = rememberAsyncImagePainter(enterpriseIcon), //TODO: Change painter per bitmap
                    contentDescription = null
                )
            }

        })
}

@Composable
fun ImageButton(
    urlImage: String,
    modifier: Modifier,
    action: () -> Unit = {}
) {
    Image(
        modifier = modifier
            .width(150.dp)
            .height(60.dp)
            .clickable {
                action.invoke()
            },
        painter = rememberAsyncImagePainter(model = urlImage),
        contentDescription = null
    )
}

@ExperimentalFoundationApi
@Composable
fun ButtonsList(
    navController: NavController,
    Items: List<String>,
    onRubro: Boolean = false
) {
    LazyVerticalGrid(
        cells = GridCells.Fixed(2),
        contentPadding = PaddingValues(32.dp),
    ) {
        items(Items.size) { index ->
            ImageButton(
                urlImage = Items[index],
                modifier = Modifier.padding(horizontal = 8.dp)
            ) {
                if (!onRubro) {
                    when (Items[index]) {
                        Constants.INVENTORY -> {
                            navController.navigate(PerseoScreens.Inventory.route)
                        }
                        Constants.SERVICE_ORDERS -> {
                            navController.navigate(PerseoScreens.ServiceOrders.route)
                        }
                        Constants.SUBSCRIBER -> {
                            navController.navigate(PerseoScreens.Subscribers.route)
                        }
                        Constants.MY_SERVICE_ORDERS -> {
                            navController.navigate(PerseoScreens.MyServiceOrders.route)
                        }
                        Constants.COMPLIANCE -> {
                            navController.navigate(PerseoScreens.Compliance.route)
                        }
                        Constants.SERVICES_CORDS -> {
                            navController.navigate(PerseoScreens.ServicesCords.route)
                        }
                    }
                } else {
                    navController.navigate(PerseoScreens.Rubro.route)
                }

            }
        }
    }
}

@Composable
fun ShowAlertDialog(
    title: String,
    message: String,
    positiveButtonText: String = "Aceptar",
    negativeButtonText: String = "Cancelar",
    openDialog: MutableState<Boolean>,
    onYesPress: () -> Unit
) {
    if (openDialog.value) {
        AlertDialog(onDismissRequest = { openDialog.value = false },
            title = { Text(text = title, color = Yellow4) },
            text = { Text(text = message, color = Color.White) },
            backgroundColor = Background,
            buttons = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(all = 8.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = { openDialog.value = false }) {
                        Text(text = negativeButtonText, color = Yellow4)
                    }
                    TextButton(onClick = { onYesPress.invoke() }) {
                        Text(text = positiveButtonText, color = Yellow4)
                    }
                }
            })
    }
}

@ExperimentalFoundationApi
@Composable
fun ZonesButtons(
    Items: List<String> = listOf(
        "VILLAS DEL PEDREGAL",
        "HUB CUAUTEPEC",
        "NUEVO HIDALGO",
        "COLEGIO Y BONFIL"
    ),
    navController: NavController
) {
    LazyVerticalGrid(
        cells = GridCells.Fixed(2),
        contentPadding = PaddingValues(32.dp),
        content = {
            items(Items.size) { index ->
                Box(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .height(50.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        modifier = Modifier
                            .fillMaxSize()
                            .clickable {
                                navController.navigate(PerseoScreens.Zone.route)
                            },
                        painter = rememberAsyncImagePainter(Constants.BUTTON_BACKGROUND), //TODO: Change painter per bitmap
                        contentDescription = null,
                        contentScale = ContentScale.FillBounds
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            modifier = Modifier.width(100.dp),
                            text = Items[index],
                            fontSize = 12.sp,
                            maxLines = 2
                        )
                        Icon(imageVector = Icons.Default.LocationOn, contentDescription = null)
                    }
                }
            }
        })
}

@Composable
fun ServiceOrderCard(
    os: ServiceOrder,
    onClick: () -> Unit
) {
    Card(modifier = Modifier
        .width(200.dp)
        .height(130.dp)
        .padding(10.dp)
        .clickable {
            onClick.invoke()
        }) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Image(
                modifier = Modifier
                    .fillMaxSize(),
                painter = rememberAsyncImagePainter(Constants.OS_ACTIVE_BACKGROUND), //TODO: Change painter per bitmap
                contentDescription = null,
                contentScale = ContentScale.FillBounds
            )
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = os.motivoDesc,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "${os.vialidad} ${os.noExterior}",
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    fontSize = 12.sp
                )
                Text(
                    text = "Sector: ${os.sector}",
                    textAlign = TextAlign.Center,
                    fontSize = 12.sp
                )
            }
        }
    }
}

@Composable
fun DetailContainer(
    title: String,
    parameters: List<ItemOSDetail>
) {
    var expanded by remember { mutableStateOf(false) }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, bottom = 24.dp),
        backgroundColor = Accent
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    modifier = Modifier
                        .padding(8.dp),
                    text = title,
                    style = MaterialTheme.typography.h6,
                    color = Yellow6
                )
                Icon(
                    modifier = Modifier
                        .padding(8.dp)
                        .clickable {
                            expanded = !expanded
                        },
                    tint = Color.White,
                    imageVector = if (expanded) Icons.Default.ArrowDropDown else Icons.Default.ArrowForward,
                    contentDescription = null
                )
            }
            AnimatedVisibility(visible = expanded) {
                Column(Modifier.padding(8.dp)) {
                    for (item in parameters) {
                        DetailItem(row = item.row, value = item.value)
                    }
                }
            }
        }
    }
}

@Composable
fun DetailItem(
    row: String,
    value: String
) {
    Column(Modifier.padding(8.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(imageVector = Icons.Filled.Info, contentDescription = null)
            Text(
                modifier = Modifier.padding(start = 8.dp),
                text = row,
                style = MaterialTheme.typography.body2,
                color = Color.White
            )
            Text(
                text = value,
                style = MaterialTheme.typography.body2,
                color = Yellow6
            )
        }
    }
}

@Preview
@Composable
fun MaterialsAddItem() {

    val materiales = Data.Material
    // State variables
    var countryName: Material by remember { mutableStateOf(materiales[0]) }
    var expanded by remember { mutableStateOf(false) }

    Card {
        Row(
            modifier = Modifier
                .background(Accent)
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(9f)
                    .padding(end = 32.dp)
            ) {
                Text(text = "Material:", color = Color.White)
                Row(
                    Modifier
                        .background(Background)
                        .clickable {
                            expanded = !expanded
                        }
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = countryName.materialDesc,
                        fontSize = 18.sp,
                        modifier = Modifier.padding(end = 32.dp),
                        color = White,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Icon(
                        imageVector = Icons.Filled.ArrowDropDown,
                        contentDescription = "",
                        tint = White
                    )
                    DropdownMenu(expanded = expanded, onDismissRequest = {
                        expanded = false
                    }) {
                        materiales.forEach { country ->
                            DropdownMenuItem(onClick = {
                                expanded = false
                                countryName = country
                            }) {
                                Text(text = country.materialDesc)
                            }
                        }
                    }
                }
                var text by remember { mutableStateOf("") }

                OutlinedTextField(
                    value = text,
                    onValueChange = { text = it },
                    label = { Text("Cantidad") },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        textColor = White,
                        cursorColor = White,
                        focusedBorderColor = White,
                        unfocusedBorderColor = Yellow3,
                        focusedLabelColor = White,
                        unfocusedLabelColor = Yellow3,
                    )
                )
            }
            IconButton(
                modifier = Modifier
                    .clip(
                        RoundedCornerShape(
                            topStart = 10.dp,
                            topEnd = 10.dp,
                            bottomStart = 10.dp,
                            bottomEnd = 10.dp
                        )
                    )
                    .background(Yellow3),
                onClick = {}
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null, tint = White)
            }
        }
    }
}
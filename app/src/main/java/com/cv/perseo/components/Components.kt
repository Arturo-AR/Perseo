package com.cv.perseo.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.cv.perseo.data.Data
import com.cv.perseo.model.ItemOSDetail
import com.cv.perseo.model.database.ServiceOrder
import com.cv.perseo.model.perseoresponse.CordsOrderBody
import com.cv.perseo.model.perseoresponse.Inventory
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
        textStyle = TextStyle(fontSize = 18.sp, color = White),
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
            focusedBorderColor = Black,
            focusedLabelColor = Yellow3,
            unfocusedBorderColor = Black,
            unfocusedLabelColor = Color.LightGray,
            backgroundColor = Accent
        )
    )
}

@Composable
fun EmailInput(
    modifier: Modifier = Modifier,
    emailState: MutableState<String>,
    labelId: String = "Usuario",
    enable: Boolean = true,
    imeAction: ImeAction = ImeAction.Next,
    onAction: KeyboardActions = KeyboardActions.Default
) {
    InputField(
        modifier = modifier,
        valueState = emailState,
        labelId = labelId,
        enable = enable,
        keyboardType = KeyboardType.Text,
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
        textStyle = TextStyle(fontSize = 18.sp, color = White),
        modifier = modifier
            .padding(bottom = 10.dp, start = 10.dp, end = 10.dp)
            .fillMaxWidth(),
        enabled = enable,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = imeAction),
        keyboardActions = onAction,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Black,
            focusedLabelColor = Yellow3,
            unfocusedBorderColor = Black,
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
            tint = Black
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
                        tint = White,
                        modifier = Modifier.clickable {
                            onBackArrowClicked.invoke()
                        }
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Arrow Back",
                        tint = White,
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
        contentPadding = PaddingValues(16.dp),
    ) {
        items(Items.size) { index ->
            ImageButton(
                urlImage = Constants.PERSEO_BASE_URL+Items[index],
                modifier = Modifier.padding(8.dp)
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
                            navController.navigate(PerseoScreens.OrderOptions.route)
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
            text = { Text(text = message, color = White) },
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
        contentPadding = PaddingValues(16.dp),
        content = {
            items(Items.size) { index ->
                Box(
                    modifier = Modifier
                        .padding(4.dp)
                        .height(55.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        modifier = Modifier
                            .padding(4.dp)
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
                            .padding(horizontal = 10.dp),
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
    os: ServiceOrder = ServiceOrder(
        idOs = 234,
        rubroDesc = "efr",
        motivoDesc = "INTALAR SERVICIO TV (PAQ)",
        vialidad = "MARIANO DE JESUS TORRES",
        noExterior = "2345",
        zona = "wwekdfjn",
        paquete = "wwekdfjn",
        idRubro = "wwekdfjn",
        fechaPreCumplimiento = "wwekdfjn",
        iconoRubro = "wwekdfjn",
        fecha_agenda = "",
        hora_hasta = "",
        hora_de = "",
        detalle_agenda = ""
    ),
    onClick: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .height(145.dp)
            .padding(4.dp)
            .clickable {
                onClick.invoke()
            }
    ) {
        Image(
            modifier = Modifier
                .padding(4.dp)
                .fillMaxSize(),
            painter = rememberAsyncImagePainter(Constants.OS_ACTIVE_BACKGROUND), //TODO: Change painter per bitmap
            contentDescription = null,
            contentScale = ContentScale.FillBounds
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                modifier = Modifier.padding(horizontal = 4.dp),
                text = os.motivoDesc,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            Text(
                text = "${os.vialidad} #${os.noExterior}",
                color = White,
                textAlign = TextAlign.Center,
                fontSize = 12.sp
            )
            Text(
                text = "Sector perron",
                textAlign = TextAlign.Center,
                fontSize = 12.sp
            )
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
                    tint = White,
                    imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
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
                color = White
            )
            Text(
                text = value,
                style = MaterialTheme.typography.body2,
                color = Yellow6
            )
        }
    }
}

@Composable
fun MaterialsAddItem() {

    val materiales = Data.Material
    // State variables
    var countryName: Inventory by remember { mutableStateOf(materiales[0]) }
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
                Text(text = "Material:", color = White)
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
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done,
                    ),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        textColor = Yellow3,
                        cursorColor = White,
                        focusedBorderColor = White,
                        unfocusedBorderColor = Yellow3,
                        focusedLabelColor = Yellow3,
                        unfocusedLabelColor = White,
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

@Composable
fun EquipmentItem() {
    var text by remember { mutableStateOf("") }
    Card(
        modifier = Modifier
            .padding(8.dp)
            .clip(
                RoundedCornerShape(
                    topStart = 10.dp,
                    topEnd = 10.dp,
                    bottomStart = 10.dp,
                    bottomEnd = 10.dp
                )
            ), elevation = 3.dp
    ) {
        Column(
            modifier = Modifier
                .background(Accent)
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Text(text = "Etiqueta", color = White, fontSize = 20.sp)

            TextField(
                modifier = Modifier.padding(4.dp),
                value = text,
                onValueChange = { text = it },
                label = { Text("Equipo") },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    textColor = Yellow3,
                    cursorColor = White,
                    focusedBorderColor = White,
                    unfocusedBorderColor = Yellow3,
                    focusedLabelColor = Yellow3,
                    unfocusedLabelColor = White,
                )
            )

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
                    .background(Yellow6)
                    .padding(horizontal = 16.dp),
                onClick = {}
            ) {
                Row(
                    modifier = Modifier
                        .padding(horizontal = 4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Icon(imageVector = Icons.Default.Edit, contentDescription = null)
                    Text(text = "FOTO", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
fun CordsServicesItem(
    cord: CordsOrderBody,
) {
    val checked = rememberSaveable { mutableStateOf(false) }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier
                .background(
                    if (cord.orderType == "COFAPA") cordsRed else cordsBlue
                )
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                modifier = Modifier.weight(1.5f),
                text = "${cord.highway} #${cord.outdoorNumber}",
                color = White
            )
            Column(modifier = Modifier.weight(1.5f)) {
                Text(text = "Etiquita", color = Yellow4)
                Text(text = cord.label, color = White)
                Text(text = "Caja terminal", color = Yellow4)
                Text(text = cord.terminalBox, color = White)
            }
            Checkbox(
                modifier = Modifier.weight(1f),
                checked = checked.value,
                onCheckedChange = { checked.value = !checked.value })
        }
    }
}

@Composable
fun CordsServicesFilters() {
    val colonias = Data.colonias
    val spinners = listOf("Colonia", "Sector")
    val currentSelection = remember { mutableStateOf(spinners.first()) }

    // State variables
    var colonia: String by remember { mutableStateOf(colonias.first()) }
    var expanded by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier.padding(4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            Modifier
                .background(Background)
                .clickable {
                    expanded = !expanded
                }
                .padding(8.dp)
                .weight(1f),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = colonia,
                fontSize = 18.sp,
                color = White,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            DropdownMenu(expanded = expanded, onDismissRequest = {
                expanded = false
            }) {
                colonias.forEach { coloniaNew ->
                    DropdownMenuItem(onClick = {
                        expanded = false
                        colonia = coloniaNew
                    }) {
                        Text(text = coloniaNew)
                    }
                }
            }
            Icon(
                imageVector = Icons.Filled.ArrowDropDown,
                contentDescription = "",
                tint = White
            )

        }
        RadioGroupWithSelectable(
            modifier = Modifier,
            items = spinners,
            selection = currentSelection.value
        ) { clickedItem ->
            currentSelection.value = clickedItem
        }
    }
}

@Composable
fun LabelledRadioButton(
    modifier: Modifier = Modifier,
    label: String,
    selected: Boolean,
    onClick: (() -> Unit)?,
    enabled: Boolean = true,
    colors: RadioButtonColors = RadioButtonDefaults.colors()
) {

    Row(
        modifier = modifier.padding(horizontal = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End
    ) {
        RadioButton(
            selected = selected,
            onClick = onClick,
            enabled = enabled,
            colors = colors
        )

        Text(
            text = label,
            style = MaterialTheme.typography.body1.merge(),
            color = White
        )
    }
}

@Composable
fun RadioGroupWithSelectable(
    modifier: Modifier,
    items: List<String>,
    selection: String,
    onItemClick: ((String) -> Unit)
) {
    Row(modifier = modifier.selectableGroup()) {
        items.forEach { item ->
            LabelledRadioButton(
                modifier = Modifier
                    .selectable(
                        selected = item == selection,
                        onClick = { onItemClick(item) },
                        role = Role.RadioButton
                    ),
                label = item,
                selected = item == selection,
                onClick = null
            )
        }
    }
}

@Composable
fun DefaultButtonWithImage(
    title: String,
    onClick: () -> Unit
) {
    Box {
        ImageButton(urlImage = Constants.BUTTON_BACKGROUND, modifier = Modifier) {
            onClick()
        }
        Column(
            modifier = Modifier.matchParentSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = title, fontWeight = FontWeight.Bold)
        }
    }
}

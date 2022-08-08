package com.cv.perseo.components

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.PopupProperties
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.cv.perseo.data.Data
import com.cv.perseo.model.ItemOSDetail
import com.cv.perseo.model.database.Equipment
import com.cv.perseo.model.database.Materials
import com.cv.perseo.model.database.ServiceOrder
import com.cv.perseo.model.perseoresponse.*
import com.cv.perseo.navigation.PerseoScreens
import com.cv.perseo.ui.theme.*
import com.cv.perseo.utils.Constants
import com.cv.perseo.utils.toBitmap
import com.cv.perseo.utils.toHourFormat
import java.util.*

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
    inDashboard: Boolean? = false,
    onBackArrowClicked: () -> Unit = {}
) {
    TopAppBar(
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                if (inDashboard != null) {
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
    enterprise: String,
    enterpriseIcon: String = ""
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
                    modifier = Modifier.padding(8.dp),
                    bitmap = enterpriseIcon.toBitmap().asImageBitmap(),
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
    onRubro: Boolean = false,
    onClick: (Int) -> Unit = { }
) {
    LazyVerticalGrid(
        cells = GridCells.Fixed(2),
        contentPadding = PaddingValues(16.dp),
    ) {
        items(Items.size) { index ->
            ImageButton(
                urlImage = Constants.PERSEO_BASE_URL + (if (onRubro) Constants.RUBROS_BUTTONS_PATH else "") + Items[index],
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
                    onClick(index)
                }

            }
        }
    }
}

@Composable
fun ShowAlertDialog(
    title: String,
    message: @Composable () -> Unit,
    positiveButtonText: String = "Aceptar",
    negativeButtonText: String = "Cancelar",
    openDialog: MutableState<Boolean>,
    onYesPress: () -> Unit
) {
    if (openDialog.value) {
        AlertDialog(onDismissRequest = { openDialog.value = false },
            title = {
                Text(
                    text = title,
                    color = Yellow4,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            },
            text = message,
            backgroundColor = Background,
            shape = MaterialTheme.shapes.small,
            buttons = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(all = 8.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = { openDialog.value = false }) {
                        Text(text = negativeButtonText, color = Yellow4, fontSize = 16.sp)
                    }
                    TextButton(onClick = { onYesPress.invoke() }) {
                        Text(text = positiveButtonText, color = Yellow4, fontSize = 16.sp)
                    }
                }
            })
    }
}

@ExperimentalFoundationApi
@Composable
fun ZonesButtons(
    items: List<String>,
    onPress: (String) -> Unit
) {
    LazyVerticalGrid(
        cells = GridCells.Fixed(2),
        contentPadding = PaddingValues(16.dp),
        content = {
            items(items.size) { index ->
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
                            .clickable { onPress(items[index]) },
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
                            text = items[index],
                            fontSize = 12.sp,
                            maxLines = 2,
                            fontWeight = FontWeight.Bold
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
            painter = rememberAsyncImagePainter(if (os.preCumDate == "") Constants.OS_ACTIVE_BACKGROUND else Constants.OS_INACTIVE_BACKGROUND), //TODO: Change painter per bitmap
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
                text = os.motivo,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            Text(
                text = "${os.street} #${os.outdoorNumber}",
                color = White,
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
fun MaterialsAddItem(
    materiales: List<Inventory>,
    onClick: (Double, Inventory) -> Unit
) {
    // State variables
    var materialSelected: Inventory by remember { mutableStateOf(materiales[0]) }
    var expanded by remember { mutableStateOf(false) }
    var text by remember { mutableStateOf("") }
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
                        text = materialSelected.materialDesc,
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
                        materiales.forEach { material ->
                            DropdownMenuItem(onClick = {
                                expanded = false
                                materialSelected = material
                            }) {
                                Text(text = material.materialDesc)
                            }
                        }
                    }
                }

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
                onClick = {
                    if (text != ""){
                        onClick(text.toDouble(), materialSelected)
                    } else {
                        onClick(-1.0, materialSelected)
                    }
                }
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null, tint = White)
            }
        }
    }
}

@Composable
fun EquipmentItem(
    motivo: String,
    oldBitmap: Bitmap?,
    idMotivo: String?,
    boxes: List<TerminalBox>,
    routers: List<RouterCentral>,
    onAction: (String) -> Unit,
    getImage: (Bitmap) -> Unit
) {
    var text by remember { mutableStateOf(idMotivo ?: "") }
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
            Text(text = motivo, color = White, fontSize = 20.sp)
            if (motivo == "CAJA TERMINAL" || motivo == "ROUTER CENTRAL") {
                TextFieldWithDropdownUsage(
                    dataIn = if (motivo == "CAJA TERMINAL") boxes.map { it.terminalBoxDesc } else routers.map { it.routerCentralDesc },
                    label = "Equipo", onAction = onAction,
                    idMotivo = if (idMotivo == "") "" else if (motivo == "CAJA TERMINAL") boxes.filter { it.terminalBoxId == idMotivo?.toInt()!! }[0].terminalBoxDesc else routers.filter { it.routerCentralId == idMotivo?.toInt()!! }[0].routerCentralDesc
                )
            } else {
                TextField(
                    modifier = Modifier.padding(4.dp),
                    value = text,
                    onValueChange = {
                        text = it
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions {
                        onAction(text)
                    },
                    singleLine = true,
                    label = { Text("Equipo") },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        textColor = Yellow3,

                        cursorColor = White,
                        focusedBorderColor = Yellow4,
                        unfocusedBorderColor = White,
                        focusedLabelColor = Yellow3,
                        unfocusedLabelColor = White,
                    )
                )
            }
            RequestContentPermission(oldBitmap = oldBitmap) {
                getImage(it)
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

@Composable
fun ScheduleItem(
    order: ServiceOrder,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier
                .background(if (order.preCumDate == "") Yellow3 else Accent)
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(4f)) {
                Text(text = order.motivo, color = Black, fontWeight = FontWeight.Bold)
                Text(
                    text = "${order.street} #${order.outdoorNumber}",
                    color = White,
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp
                )
                if (!order.sector.isNullOrEmpty())
                    Text(
                        text = "sector: ${order.sector}",
                        fontWeight = FontWeight.Light,
                        fontSize = 13.sp
                    )

            }
            Column(
                modifier = Modifier
                    .weight(1.5f)
                    .height(IntrinsicSize.Max),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.End
            ) {
                Text(text = "De: ${order.hourFrom?.toHourFormat()}", color = White)
                Text(text = "De: ${order.hourUntil?.toHourFormat()}", color = White)
            }
        }
    }
}

@Composable
fun MaterialsFinalItem(
    material: Materials,
    onClick: (UUID) -> Unit
) {
    Card {
        Row(
            modifier = Modifier
                .background(Accent)
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(modifier = Modifier.weight(9f)) {
                Text(text = "${material.desc_material} ", color = Yellow4)
                Text(text = "${material.cantidad}", color = White)
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
                    .background(Gray)
                    .weight(2f),
                onClick = {
                    onClick(material.id)
                }
            ) {
                Icon(imageVector = Icons.Default.Close, contentDescription = null, tint = White)
            }
        }
    }
}

@Composable
fun MaterialsFinalList(
    materialList: List<Materials>,
    onDelete: (UUID) -> Unit
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(materialList) { material ->
            MaterialsFinalItem(material) {
                onDelete(it)
            }
        }
    }
}

@Composable
fun RequestContentPermission(
    oldBitmap: Bitmap?,
    onReturn: (Bitmap) -> Unit
) {
    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }
    val context = LocalContext.current
    val bitmap = remember {
        mutableStateOf(oldBitmap)
    }
    val launcher = rememberLauncherForActivityResult(
        contract =
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
    }
    Column {
        Button(colors = ButtonDefaults.buttonColors(
            backgroundColor = Yellow4
        ), onClick = {
            launcher.launch("image/*")
        }) {
            Text(text = "Imagen")
        }
        Spacer(modifier = Modifier.height(12.dp))

        imageUri?.let {
            if (Build.VERSION.SDK_INT < 28) {
                bitmap.value = MediaStore.Images
                    .Media.getBitmap(context.contentResolver, it)
            } else {
                val source = ImageDecoder
                    .createSource(context.contentResolver, it)
                bitmap.value = ImageDecoder.decodeBitmap(source)
            }
        }
        bitmap.value?.let { btm ->
            onReturn(btm)
            Image(
                bitmap = btm.asImageBitmap(),
                contentDescription = null,
                modifier = Modifier.size(100.dp)
            )
        }
    }
}

@ExperimentalFoundationApi
@Composable
fun RequestContentPermissionCancelList(
    bitmapList: List<Bitmap>?,
    returnUri: (Uri?) -> Unit,
    onReturn: (Bitmap) -> Unit

) {
    val launcher = rememberLauncherForActivityResult(
        contract =
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        returnUri(uri)
    }
    Card(
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Background),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Button(colors = ButtonDefaults.buttonColors(
                backgroundColor = Yellow4
            ), onClick = {
                //launcher.launch("image/*")
                if (bitmapList != null) {
                    if (bitmapList.size < 3) {
                        launcher.launch("image/*")
                    } else {
                        Log.d("Imagenes", "Limit of images")
                    }
                }
            }) {
                Text(text = "Agregar Imagen")
            }

            Row(Modifier.padding(8.dp)) {
                bitmapList?.map { btm ->
                    Image(
                        bitmap = btm.asImageBitmap(),
                        contentDescription = null,
                        modifier = Modifier
                            .size(80.dp)
                            .combinedClickable(
                                onClick = { },
                                onLongClick = {
                                    onReturn(btm)
                                },
                            )
                    )
                }
            }
        }
    }
}

@ExperimentalFoundationApi
@Composable
fun RequestContentPermissionList(
    bitmapList: List<Equipment>,
    returnUri: (Uri?) -> Unit,
    onReturn: (Equipment) -> Unit

) {
    var expanded by remember { mutableStateOf(false) }

    val launcher = rememberLauncherForActivityResult(
        contract =
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        returnUri(uri)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, bottom = 24.dp),
        backgroundColor = Accent
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Spacer(modifier = Modifier.width(16.dp))
                Button(colors = ButtonDefaults.buttonColors(
                    backgroundColor = Yellow4
                ), onClick = {
                    if (bitmapList.size < 3) {
                        launcher.launch("image/*")
                    } else {
                        Log.d("Imagenes", "Limit of images")
                    }
                }) {
                    Text(text = "Imagenes Adicionales")
                }
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
                Row(Modifier.padding(8.dp)) {
                    bitmapList.map { equipment ->
                        Image(
                            bitmap = equipment.url_image?.toBitmap()?.asImageBitmap()!!,
                            contentDescription = null,
                            modifier = Modifier
                                .size(100.dp)
                                .combinedClickable(
                                    onClick = { },
                                    onLongClick = {
                                        onReturn(equipment)
                                    })
                                .padding(4.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun TextFieldWithDropdownUsage(
    dataIn: List<String>,
    label: String,
    idMotivo: String?,
    onAction: (String) -> Unit,
) {
    val dropDownOptions = remember { mutableStateOf(listOf<String>()) }
    val textFieldValue = remember { mutableStateOf(idMotivo ?: "") }
    val dropDownExpanded = remember { mutableStateOf(false) }

    fun onDropdownDismissRequest() {
        dropDownExpanded.value = false
    }

    fun onValueChanged(value: String) {
        dropDownExpanded.value = true
        textFieldValue.value = value
        dropDownOptions.value = dataIn.filter {
            it.startsWith(value)
        }
    }

    TextFieldWithDropdown(
        modifier = Modifier.fillMaxWidth(),
        value = textFieldValue.value,
        setValue = ::onValueChanged,
        onDismissRequest = ::onDropdownDismissRequest,
        dropDownExpanded = dropDownExpanded.value,
        list = dropDownOptions.value,
        label = label,
        onAction = onAction
    )
}

@Composable
fun TextFieldWithDropdown(
    modifier: Modifier = Modifier,
    value: String,
    setValue: (String) -> Unit,
    onDismissRequest: () -> Unit,
    dropDownExpanded: Boolean,
    list: List<String>,
    label: String,
    onAction: (String) -> Unit,
) {
    Box(modifier) {
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged { focusState ->
                    if (!focusState.isFocused)
                        onDismissRequest()
                },
            value = value,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),

            onValueChange = setValue,
            label = { Text(text = label) },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                textColor = Yellow3,
                cursorColor = White,
                focusedBorderColor = Yellow3,
                unfocusedBorderColor = White,
                focusedLabelColor = Yellow3,
                unfocusedLabelColor = White,
            ),
            keyboardActions = KeyboardActions {
                onAction(value)
            },
            singleLine = true
        )
        DropdownMenu(
            expanded = dropDownExpanded,
            properties = PopupProperties(
                focusable = false,
                dismissOnBackPress = true,
                dismissOnClickOutside = true
            ),
            onDismissRequest = onDismissRequest
        ) {
            list.forEach { text ->
                DropdownMenuItem(onClick = {
                    setValue(
                        text
                    )
                }) {
                    Text(text = text)
                }
            }

        }
    }
}

@Composable
fun EnterpriseOption(
    enterprise: EnterpriseBody,
    onSelection: (EnterpriseBody) -> Unit
) {
    Box(contentAlignment = Alignment.Center) {
        Image(
            modifier = Modifier
                .height(80.dp)
                .fillMaxHeight()
                .clickable {
                    onSelection(enterprise)
                },
            painter = rememberAsyncImagePainter(
                Constants.PERSEO_BASE_URL + Constants.ITEM_BACKGROUND_CIUDAD_PATH
            ),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier.matchParentSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                modifier = Modifier.height(35.dp),
                contentScale = ContentScale.Crop,
                bitmap = enterprise.logoIcon.toBitmap().asImageBitmap(),
                contentDescription = null
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = enterprise.municipality, color = White, fontWeight = FontWeight.Bold,
                fontSize = if (enterprise.municipality.length < 9) 16.sp else 14.sp
            )
        }
    }
}

@ExperimentalFoundationApi
@Composable
fun EnterpriseList(
    list: List<EnterpriseBody>,
    onSelection: (EnterpriseBody) -> Unit
) {
    LazyVerticalGrid(
        cells = GridCells.Fixed(2),
        contentPadding = PaddingValues(16.dp),
        content = {
            items(list) { item ->
                EnterpriseOption(enterprise = item) { enterprise ->
                    onSelection(enterprise)
                }
            }
        })
}

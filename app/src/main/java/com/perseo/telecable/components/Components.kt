package com.perseo.telecable.components

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.DrawableRes
import androidx.compose.animation.*
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.*
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.PopupProperties
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.perseo.telecable.R
import com.perseo.telecable.model.ItemOSDetail
import com.perseo.telecable.model.SummaryItems
import com.perseo.telecable.model.database.Equipment
import com.perseo.telecable.model.database.Materials
import com.perseo.telecable.model.database.ServiceOrder
import com.perseo.telecable.model.perseoresponse.*
import com.perseo.telecable.navigation.PerseoScreens
import com.perseo.telecable.screens.dashboard.DashboardScreenViewModel
import com.perseo.telecable.ui.theme.*
import com.perseo.telecable.utils.*

@Composable
fun LogoPerseo(modifier: Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = rememberAsyncImagePainter(Constants.PERSEO_BASE_URL + Constants.LOGO_PERSEO),
            contentDescription = "Logo Perseo"
        )
    }
}

@Composable
fun DrawerView(navController: NavController, viewModel: DashboardScreenViewModel) {
    val openDialog = remember {
        mutableStateOf(false)
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
            .padding(vertical = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        if (openDialog.value) {
            ShowAlertDialog(
                title = stringResource(id = R.string.alert_title),
                message = {
                    Text(
                        text = stringResource(id = R.string.close_account_question),
                        color = White,
                        fontSize = 18.sp
                    )
                },
                positiveButtonText = stringResource(id = R.string.close),
                openDialog = openDialog
            ) {
                viewModel.signOut()
                navController.navigate(PerseoScreens.Login.route)
            }
        }

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
        Text(
            text = stringResource(id = R.string.app_name_perseo),
            color = ButtonText,
            style = MaterialTheme.typography.h6
        )
        Spacer(modifier = Modifier.height(32.dp))
        AddDrawerHeader(
            title = stringResource(id = R.string.change_city),
            icon = Icons.Default.Edit
        ) {
            navController.navigate(PerseoScreens.EnterpriseSelector.route)
        }
        Divider(color = Accent)
        AddDrawerHeader(
            title = stringResource(id = R.string.close_account),
            icon = Icons.Default.Close
        ) {
            openDialog.value = true
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

@ExperimentalComposeUiApi
@Composable
fun UserForm(
    loading: Boolean = false,
    onDone: (String, String) -> Unit = { _, _ -> }
) {
    val userId = rememberSaveable { mutableStateOf("") }
    val password = rememberSaveable { mutableStateOf("") }
    val passwordVisibility = rememberSaveable { mutableStateOf(false) }
    val passwordFocusRequest = FocusRequester.Default
    val keyboardController = LocalSoftwareKeyboardController.current
    val valid = remember(userId.value, password.value) {
        userId.value.trim().isNotEmpty() && password.value.trim().isNotEmpty()
    }
    val modifier = Modifier
        .height(250.dp)
        .background(Background)
        .verticalScroll(rememberScrollState())

    Column(modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        EmailInput(
            emailState = userId,
            enable = !loading,
            onAction = KeyboardActions {
                passwordFocusRequest.requestFocus()
            })
        PasswordInput(
            modifier = Modifier.focusRequester(passwordFocusRequest),
            passwordState = password,
            labelId = "Contraseña:",
            enable = !loading,
            passwordVisibility = passwordVisibility,
            onAction = KeyboardActions {
                if (!valid) return@KeyboardActions
                //onDone(email.value.trim(), password.value.trim())
                keyboardController?.hide()
            })
        SubmitButton(
            textId = "Ingresar",
            loading = loading,
            validInputs = valid
        ) {
            onDone(userId.value.trim(), password.value.trim())
        }
    }
}

@Composable
fun SubmitButton(
    textId: String,
    loading: Boolean,
    validInputs: Boolean,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth(),
        enabled = !loading && validInputs,
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Yellow4,
            contentColor = Black,
            disabledBackgroundColor = Accent
        )
    ) {
        if (loading) CircularProgressIndicator(modifier = Modifier.size(25.dp))
        else Text(text = textId, modifier = Modifier.padding(5.dp))

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
    textColor: Color = Yellow4,
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
                    color = textColor,
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
                        painter = rememberAsyncImagePainter(Constants.PERSEO_BASE_URL + Constants.BUTTON_BACKGROUND),
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
            painter = rememberAsyncImagePainter(Constants.PERSEO_BASE_URL + if (os.preCumDate == "") Constants.OS_ACTIVE_BACKGROUND else Constants.OS_INACTIVE_BACKGROUND),
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
fun DetailContainerImages(
    title: String,
    images: List<SubscriberImage?>
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
                LazyRow(Modifier.padding(8.dp)) {
                    items(images) { image ->
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                            modifier = Modifier.padding(4.dp)
                        ) {
                            Image(
                                painter = rememberAsyncImagePainter(image?.urlImage),
                                contentDescription = null,
                                modifier = Modifier.size(100.dp)
                            )
                            Text(text = image?.descriptionImage ?: "", color = White)
                        }
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

@ExperimentalFoundationApi
@Composable
fun MaterialsList(
    materials: List<Material>,
    materialsOld: List<Materials>,
    onAction: (String, Material) -> Unit,
    onLongClick: (String) -> Unit
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(materials) { material ->
            MaterialsItem(
                material = material,
                materialsOld = materialsOld,
                onAction = { amount, materialDesc ->
                    onAction(amount, materialDesc)
                },
                onLongClick = {
                    onLongClick(it)
                }
            )
        }
    }
}

@ExperimentalFoundationApi
@Composable
fun MaterialsItem(
    material: Material,
    materialsOld: List<Materials>,
    onAction: (String, Material) -> Unit,
    onLongClick: (String) -> Unit
) {
    val oldValue =
        materialsOld.find { it.id_material == material.materialId }?.cantidad?.toInt().toString()
    var text by rememberSaveable { mutableStateOf(if (oldValue == "null") "" else oldValue) }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(
                RoundedCornerShape(
                    topStart = 10.dp,
                    topEnd = 10.dp,
                    bottomStart = 10.dp,
                    bottomEnd = 10.dp
                )
            )
            .background(Accent)
            .padding(horizontal = 8.dp, vertical = 2.dp)
            .combinedClickable(
                onClick = {},
                onLongClick = {
                    text = ""
                    onLongClick(material.materialId)
                }
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.weight(5f)) {
            Text(text = material.materialDesc, modifier = Modifier.fillMaxWidth(), color = White)
        }
        Column(modifier = Modifier.weight(2f)) {
            BasicTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .background(
                        MaterialTheme.colors.surface,
                        MaterialTheme.shapes.small,
                    )
                    .fillMaxWidth(),
                value = text,
                onValueChange = {
                    text = it
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions {
                    onAction(text, material)
                },
                singleLine = true,
                cursorBrush = SolidColor(MaterialTheme.colors.primary),
                textStyle = LocalTextStyle.current.copy(
                    color = MaterialTheme.colors.onSurface,
                    fontSize = 12.sp
                ),
                decorationBox = { innerTextField ->
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(Modifier.weight(1f)) {
                            if (text.isEmpty()) Text(
                                "cantidad",
                                style = LocalTextStyle.current.copy(
                                    color = MaterialTheme.colors.onSurface.copy(alpha = 0.3f),
                                    fontSize = 12.sp
                                )
                            )
                            innerTextField()
                        }
                    }
                }
            )
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
    antennas: List<AntennaSectorial>,
    onAction: (String) -> Unit,
    getImage: (Bitmap?) -> Unit
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
            if (motivo == "CAJA TERMINAL" || motivo == "ROUTER CENTRAL" || motivo == "ANTENA SECTORIAL") {
                TextFieldWithDropdownUsage(
                    dataIn = if (motivo == "CAJA TERMINAL") boxes.map { it.terminalBoxDesc } else if (motivo == "ROUTER CENTRAL") routers.map { it.routerCentralDesc } else antennas.map { it.antennaSectorialDesc },
                    label = "Equipo", onAction = onAction,
                    idMotivo = if (idMotivo == "") "" else if (motivo == "CAJA TERMINAL") boxes.filter { it.terminalBoxId == idMotivo?.toInt()!! }[0].terminalBoxDesc else if (motivo == "ROUTER CENTRAL") routers.filter { it.routerCentralId == idMotivo?.toInt()!! }[0].routerCentralDesc else antennas.filter { it.antennaSectorialId == idMotivo?.toInt()!! }[0].antennaSectorialDesc
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
            RequestContentPermission(oldBitmap = oldBitmap, onReturn = {
                getImage(it)
            })
        }
    }
}

@Composable
fun CordsServicesItem(
    cord: CordsOrderBody,
    onChecked: (CordsOrderBody, Boolean) -> Unit
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
                Text(text = "Etiqueta", color = Yellow4)
                Text(text = cord.label, color = White)
                Text(text = "Caja terminal", color = Yellow4)
                Text(text = cord.terminalBox, color = White)
            }
            Checkbox(
                modifier = Modifier.weight(1f),
                checked = checked.value,
                onCheckedChange = {
                    checked.value = !checked.value
                    onChecked(cord, checked.value)

                })
        }
    }
}

@Composable
fun CordsServicesFilters(
    filters: List<String>,
    onChangeFilter: (String) -> Unit,
    onChangeOption: (String, String) -> Unit
) {
    val spinners = listOf("Colonia", "Sector")
    val currentSelection = remember { mutableStateOf("") }

    // State variables
    var currentFilter: String by remember { mutableStateOf("") }
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
                text = currentFilter,
                fontSize = 18.sp,
                color = White,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            DropdownMenu(expanded = expanded, onDismissRequest = {
                expanded = false
            }) {
                filters.forEach { newFilter ->
                    DropdownMenuItem(onClick = {
                        expanded = false
                        currentFilter = newFilter
                        onChangeOption(currentFilter, currentSelection.value)
                    }) {
                        Text(text = newFilter)
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
            onChangeFilter(clickedItem)
            currentFilter = ""
            //onChangeOption(currentFilter)
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
        ImageButton(
            urlImage = Constants.PERSEO_BASE_URL + Constants.BUTTON_BACKGROUND,
            modifier = Modifier
        ) {
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
fun RequestContentPermission(
    oldBitmap: Bitmap?,
    onReturn: (Bitmap?) -> Unit
) {
    val context = LocalContext.current
    val bitmap = remember {
        mutableStateOf(oldBitmap)
    }
    val launcher = rememberLauncherForActivityResult(
        contract =
        ActivityResultContracts.GetContent()
    ) {
        var currentBitmap: Bitmap? = null
        if (Build.VERSION.SDK_INT < 28) {
            currentBitmap = MediaStore.Images
                .Media.getBitmap(context.contentResolver, it)
        } else {
            val source = it?.let { it1 ->
                ImageDecoder
                    .createSource(context.contentResolver, it1)
            }
            source?.let { it1 -> ImageDecoder.decodeBitmap(it1) }
                ?.let { it2 -> currentBitmap = it2 }
        }
        bitmap.value = currentBitmap
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
                modifier = Modifier.height(40.dp),
                contentScale = ContentScale.Crop,
                bitmap = enterprise.logoIcon.toBitmap().asImageBitmap(),
                contentDescription = null
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = enterprise.tradeName, color = White, fontWeight = FontWeight.Bold,
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


@Composable
fun CordsServicesFooter(
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 8.dp)
        ) {

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(
                        RoundedCornerShape(
                            topStart = 0.dp,
                            topEnd = 90.dp,
                            bottomStart = 10.dp,
                            bottomEnd = 0.dp
                        )
                    )
                    .background(cordsRed)
                    .padding(start = 4.dp),
                text = "Cortes",
                color = White
            )

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(
                        RoundedCornerShape(
                            topStart = 0.dp,
                            topEnd = 90.dp,
                            bottomStart = 10.dp,
                            bottomEnd = 0.dp
                        )
                    )
                    .background(cordsBlue)
                    .padding(start = 4.dp),
                text = "Reconexiones",
                color = White
            )
        }
        Row(modifier = Modifier.weight(2f)) {
            Button(
                onClick = { onClick() }, colors = ButtonDefaults.buttonColors(
                    backgroundColor = Yellow4,
                    contentColor = Black
                )
            ) {
                Text(text = "Cumplir")
            }
        }
    }
}

@Composable
fun RowScope.MenuItems(
    @DrawableRes resId: Int,
    desc: String,
    colorTint: Color,
    border: Boolean = false,
    onClick: () -> Unit
) {
    val modifier = Modifier.size(24.dp)
    IconButton(
        onClick = onClick, modifier = Modifier.weight(1f, true)
    ) {
        Icon(
            painterResource(id = resId),
            contentDescription = desc,
            tint = colorTint,
            modifier = if (border) modifier.border(
                0.5.dp,
                White,
                shape = CircleShape
            ) else modifier
        )
    }
}

@Composable
fun ControlsBar(
    drawController: DrawController,
    onDownloadClick: () -> Unit,
    undoVisibility: MutableState<Boolean>,
    redoVisibility: MutableState<Boolean>
) {
    Row(modifier = Modifier.padding(12.dp), horizontalArrangement = Arrangement.SpaceAround) {
        MenuItems(
            R.drawable.ic_download,
            "download",
            if (undoVisibility.value) MaterialTheme.colors.primary else MaterialTheme.colors.primaryVariant
        ) {
            if (undoVisibility.value) onDownloadClick()
        }
        MenuItems(
            R.drawable.ic_undo,
            "undo",
            if (undoVisibility.value) MaterialTheme.colors.primary else MaterialTheme.colors.primaryVariant
        ) {
            if (undoVisibility.value) drawController.unDo()
        }
        MenuItems(
            R.drawable.ic_redo,
            "redo",
            if (redoVisibility.value) MaterialTheme.colors.primary else MaterialTheme.colors.primaryVariant
        ) {
            if (redoVisibility.value) drawController.reDo()
        }
        MenuItems(
            R.drawable.ic_refresh,
            "reset",
            if (redoVisibility.value || undoVisibility.value) MaterialTheme.colors.primary else MaterialTheme.colors.primaryVariant
        ) {
            drawController.reset()
        }
    }
}

@Composable
internal fun RowScope.ColorDots(
    color: Color,
    selected: Boolean,
    unSelectedSize: Dp = 26.dp,
    selectedSize: Dp = 36.dp,
    clickedColor: (Color) -> Unit
) {
    val dbAnimateAsState: Dp by animateDpAsState(
        targetValue = if (selected) selectedSize else unSelectedSize
    )
    IconButton(
        onClick = {
            clickedColor(color)
        }, modifier = Modifier
            .weight(1f, true)
    ) {
        Icon(
            painterResource(id = R.drawable.ic_color),
            contentDescription = null,
            tint = color,
            modifier = Modifier.size(dbAnimateAsState)
        )
    }
}

@Composable
fun DrawBox(
    drawController: DrawController,
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colors.background,
    bitmapCallback: (ImageBitmap?, Throwable?) -> Unit,
    trackHistory: (undoCount: Int, redoCount: Int) -> Unit = { _, _ -> }
) = AndroidView(
    factory = {
        ComposeView(it).apply {
            setContent {
                LaunchedEffect(drawController) {
                    drawController.changeBgColor(backgroundColor)
                    drawController.trackBitmaps(this@apply, this, bitmapCallback)
                    drawController.trackHistory(this, trackHistory)
                }
                Canvas(modifier = modifier
                    .background(drawController.bgColor)
                    .pointerInput(Unit) {
                        detectDragGestures(
                            onDragStart = { offset ->
                                drawController.insertNewPath(offset)
                            }
                        ) { change, _ ->
                            val newPoint = change.position
                            drawController.updateLatestPath(newPoint)
                        }
                    }) {

                    drawController.pathList.forEach { pw ->
                        drawPath(
                            createPath(pw.points),
                            color = pw.strokeColor,
                            alpha = pw.alpha,
                            style = Stroke(
                                width = pw.strokeWidth,
                                cap = StrokeCap.Round,
                                join = StrokeJoin.Round
                            )
                        )
                    }
                }
            }
        }
    },
    modifier = modifier
)

@Composable
fun SummaryBox(
    title: String,
    list: List<SummaryItems>?
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        backgroundColor = Background,
        border = BorderStroke(2.dp, Yellow4)
    ) {
        Column(Modifier.padding(8.dp)) {
            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = title,
                color = Yellow4,
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp
            )
            list?.map {
                SummaryItem(it)
            }
        }
    }
}

@Composable
fun SummaryItem(item: SummaryItems) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 2.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            modifier = Modifier.weight(4f).align(Alignment.CenterVertically),
            text = item.itemDesc,
            color = White,
        )
        Text(
            modifier = Modifier.weight(1f).align(Alignment.CenterVertically),
            text = item.value,
            color = White,
            textAlign = TextAlign.End
        )
    }
}
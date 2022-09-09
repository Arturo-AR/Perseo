package com.perseo.telecable.screens.dashboard

import android.os.Process
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.perseo.telecable.R
import com.perseo.telecable.components.*
import com.perseo.telecable.ui.theme.Background
import kotlinx.coroutines.launch

@ExperimentalFoundationApi
@Composable
fun DashboardScreen(
    navController: NavController,
    viewModel: DashboardScreenViewModel = hiltViewModel()
) {
    viewModel.getGeneralData()
    val generalData by viewModel.data.observeAsState()
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
                title = generalData?.tradeName ?: "",
                inDashboard = true
            ) {
                scope.launch {
                    scaffoldState.drawerState.open()
                }
            }
        },
        drawerContent = { DrawerView(navController, viewModel) },
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
        if (openDialog.value) {
            ShowAlertDialog(
                title = stringResource(id = R.string.alert_title),
                message = {
                    Text(
                        text = stringResource(id = R.string.close_app_question),
                        color = Color.White,
                        fontSize = 18.sp
                    )
                },
                openDialog = openDialog
            ) {
                Process.sendSignal(Process.myPid(), Process.SIGNAL_KILL)
            }
        }
        ButtonsList(
            navController,
            viewModel.permissions.collectAsState().value
        )
    }
}
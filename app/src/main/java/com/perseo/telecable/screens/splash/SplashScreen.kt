package com.perseo.telecable.screens.splash

import android.util.Log
import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.perseo.telecable.BuildConfig
import com.perseo.telecable.components.LogoPerseo
import com.perseo.telecable.components.ShowAlertDialog
import com.perseo.telecable.navigation.PerseoScreens
import com.perseo.telecable.ui.theme.Accent
import com.perseo.telecable.ui.theme.Yellow2
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    navController: NavController,
    viewModel: SplashScreenViewModel = hiltViewModel()
) {

    val doing by viewModel.doing.observeAsState()
    val onWay by viewModel.onWay.observeAsState()
    val showDialog = remember { mutableStateOf(false) }
    val versionOk by viewModel.versionOk.observeAsState()
    viewModel.verifyVersion(BuildConfig.VERSION_NAME)
    val scale = remember {
        Animatable(0f)
    }

    LaunchedEffect(key1 = true) {
        scale.animateTo(
            targetValue = 0.9f,
            animationSpec = tween(durationMillis = 800,
                easing = {
                    OvershootInterpolator(8f)
                        .getInterpolation(it)
                })
        )
        delay(1500L) //On screen total time
        if (versionOk != null)
            if (versionOk!!) {
                if (viewModel.generalData.value.isEmpty()) {
                    navController.navigate(PerseoScreens.Login.route)
                } else {
                    if (viewModel.generalData.value[0].idMunicipality != 0) {
                        if (doing == true || onWay == true) {
                            navController.navigate(PerseoScreens.OSDetails.route)
                        } else {
                            navController.navigate(PerseoScreens.Dashboard.route)
                        }
                    } else {
                        navController.navigate(PerseoScreens.EnterpriseSelector.route)
                    }
                }
            } else {
                showDialog.value = true
                Log.d("version", "error")
            }
    }

    if (showDialog.value) {
        ShowAlertDialog(
            title = "Alerta",
            message = {
                Text(
                    text = "La version de la app con la que cuentas esta deprecada, por favor actualiza",
                    color = Color.White,
                    fontSize = 18.sp
                )
            },
            openDialog = showDialog
        ) {
            //todo: open play store to update
        }
    }
    Surface(
        modifier = Modifier
            .padding(15.dp)
            .size(270.dp)
            .scale(scale.value),
        shape = CircleShape,
        color = Accent,
        border = BorderStroke(width = 3.dp, color = Yellow2)
    ) {
        val modifier = Modifier.padding(bottom = 35.dp, top = 10.dp)
        LogoPerseo(modifier)
    }
}
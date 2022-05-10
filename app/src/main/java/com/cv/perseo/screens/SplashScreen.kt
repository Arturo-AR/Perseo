package com.cv.perseo.screens

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.cv.perseo.components.LogoPerseo
import com.cv.perseo.navigation.PerseoScreens
import com.cv.perseo.ui.theme.Accent
import com.cv.perseo.ui.theme.Yellow2
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) {

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
        navController.navigate(PerseoScreens.LoginScreen.name)
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
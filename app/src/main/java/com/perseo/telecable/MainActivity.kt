package com.perseo.telecable

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.perseo.telecable.navigation.PerseoNavigation
import com.perseo.telecable.ui.theme.Background
import com.perseo.telecable.ui.theme.PerseoTheme
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalPermissionsApi
@ExperimentalComposeUiApi
@ExperimentalFoundationApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PerseoTheme {
                PerseoApp()
            }
        }
    }
}

@ExperimentalPermissionsApi
@ExperimentalComposeUiApi
@ExperimentalFoundationApi
@Composable
fun PerseoApp() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Background
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            PerseoNavigation()
        }
    }
}
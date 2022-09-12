package com.perseo.telecable.screens.signature

import android.graphics.Bitmap
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.navigation.NavController
import com.perseo.telecable.components.ControlsBar
import com.perseo.telecable.components.DrawBox
import com.perseo.telecable.utils.rememberDrawController

@Composable
fun SignatureScreen(
    navController: NavController,
    save: (Bitmap) -> Unit
) {
    val undoVisibility = remember { mutableStateOf(false) }
    val redoVisibility = remember { mutableStateOf(false) }
    val colorBarVisibility = remember { mutableStateOf(false) }
    val sizeBarVisibility = remember { mutableStateOf(false) }
    val bg = MaterialTheme.colors.background
    val currentBgColor = remember { mutableStateOf(bg) }
    val drawController = rememberDrawController()

    Box(Modifier.background(Color.White)) {
        Column {
            DrawBox(
                drawController = drawController,
                backgroundColor = currentBgColor.value,
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f, fill = false),
                bitmapCallback = { imageBitmap, error ->
                    imageBitmap?.let {
                        save(it.asAndroidBitmap())
                    }
                }
            ) { undoCount, redoCount ->
                sizeBarVisibility.value = false
                colorBarVisibility.value = false
                undoVisibility.value = undoCount != 0
                redoVisibility.value = redoCount != 0
            }

            ControlsBar(
                drawController = drawController,
                {
                    drawController.saveBitmap()
                },
                undoVisibility = undoVisibility,
                redoVisibility = redoVisibility,
            )
        }
    }
}
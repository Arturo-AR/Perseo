package com.cv.perseo.screens.osdetails

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.cv.perseo.R
import com.cv.perseo.ui.theme.Accent
import com.cv.perseo.ui.theme.Yellow6

@Composable
fun OSDetails(navController: NavController) {
    var expanded1 by remember { mutableStateOf(false) }
    var expanded2 by remember { mutableStateOf(false) }
    var expanded3 by remember { mutableStateOf(false) }

    Column {
        Card(backgroundColor = Accent) {
            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Juan Perez", style = MaterialTheme.typography.h6, color = Yellow6)
                    Icon(
                        modifier = Modifier.clickable {
                            if (expanded2 || expanded3) {
                                expanded2 = false
                                expanded3 = false
                            }
                            expanded1 = !expanded1
                        },
                        tint = Color.White,
                        imageVector = if (expanded1) Icons.Default.ArrowDropDown else Icons.Default.ArrowForward,
                        contentDescription = null
                    )
                }
                AnimatedVisibility(visible = expanded1) {
                    Column(Modifier.padding(8.dp)) {
                        Row {
                            Image(imageVector = Icons.Default.Done, contentDescription = null)
                            Text(
                                text = "Contrato No: ",
                                style = MaterialTheme.typography.body2,
                                color = Color.White
                            )
                            Text(
                                text = "2345",
                                style = MaterialTheme.typography.body2,
                                color = Yellow6
                            )
                        }
                    }
                }
            }
        }
        Card(backgroundColor = Accent) {
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painterResource(R.drawable.ic_launcher_foreground),
                        contentDescription = null
                    )
                    Icon(
                        modifier = Modifier.clickable {
                            if (expanded1 || expanded3) {
                                expanded1 = false
                                expanded3 = false
                            }
                            expanded2 = !expanded2
                        },
                        tint = Color.White,
                        imageVector = if (expanded2) Icons.Default.ArrowDropDown else Icons.Default.ArrowForward,
                        contentDescription = null
                    )
                }
                AnimatedVisibility(visible = expanded2) {
                    Column(Modifier.padding(8.dp)) {
                        Text(text = "Material Compose", style = MaterialTheme.typography.body2)
                        Text(text = "Material Compose", style = MaterialTheme.typography.body2)
                        Text(text = "Material Compose", style = MaterialTheme.typography.body2)
                    }
                }
            }
        }
        Card(backgroundColor = Accent) {
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painterResource(R.drawable.ic_launcher_foreground),
                        contentDescription = null
                    )
                    Icon(
                        modifier = Modifier.clickable {
                            if (expanded1 || expanded2) {
                                expanded1 = false
                                expanded2 = false
                            }
                            expanded3 = !expanded3
                        },
                        tint = Color.White,
                        imageVector = if (expanded3) Icons.Default.ArrowDropDown else Icons.Default.ArrowForward,
                        contentDescription = null
                    )
                }
                AnimatedVisibility(visible = expanded3) {
                    Column(Modifier.padding(8.dp)) {
                        Text(text = "Material Compose", style = MaterialTheme.typography.body2)
                        Text(text = "Material Compose", style = MaterialTheme.typography.body2)
                        Text(text = "Material Compose", style = MaterialTheme.typography.body2)
                    }
                }
            }
        }
    }
}
package com.perseo.telecable.screens.inventory

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.perseo.telecable.R
import com.perseo.telecable.components.PerseoBottomBar
import com.perseo.telecable.components.PerseoTopBar
import com.perseo.telecable.navigation.PerseoScreens
import com.perseo.telecable.ui.theme.Background
import com.perseo.telecable.ui.theme.Yellow3

@ExperimentalFoundationApi
@Composable
fun InventoryScreen(
    navController: NavController,
    viewModel: InventoryScreenViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()
    val inventory by viewModel.inventory.observeAsState()
    viewModel.getGeneralData()
    val generalData by viewModel.data.observeAsState()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            PerseoTopBar(
                title = stringResource(id = R.string.inventory),
                inDashboard = false
            ) {
                navController.navigate(PerseoScreens.Dashboard.route) {
                    popUpTo(PerseoScreens.Dashboard.route)
                }
            }
        },
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
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            LazyVerticalGrid(
                cells = GridCells.Fixed(3),
                contentPadding = PaddingValues(
                    start = 12.dp,
                    top = 16.dp,
                    end = 12.dp,
                    bottom = 58.dp
                ),
                content = {
                    if (!inventory.isNullOrEmpty()) {
                        items(inventory!!.size) { item ->
                            Card(
                                backgroundColor = Yellow3,
                                modifier = Modifier
                                    .padding(4.dp)
                                    .fillMaxSize()
                                    .clip(
                                        RoundedCornerShape(
                                            topStart = 15.dp,
                                            topEnd = 15.dp,
                                            bottomEnd = 15.dp,
                                            bottomStart = 15.dp,
                                        )
                                    )
                                    .clickable { },
                                elevation = 8.dp,
                            ) {
                                Column(
                                    modifier = Modifier
                                        .padding(8.dp)
                                        .height(90.dp),
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                ) {
                                    Text(
                                        text = inventory!![item].materialDesc,
                                        fontSize = 15.sp,
                                        color = Color.Black,
                                        textAlign = TextAlign.Center
                                    )
                                    Text(
                                        text = inventory!![item].amount.toString(),
                                        fontSize = 15.sp,
                                        color = Color.White,
                                        textAlign = TextAlign.Center
                                    )
                                }
                            }
                        }
                    }
                }
            )
        }
    }
}
package com.cv.perseo.screens.enterpriseselector

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.cv.perseo.components.EnterpriseList
import com.cv.perseo.components.PerseoTopBar
import com.cv.perseo.navigation.PerseoScreens
import com.cv.perseo.ui.theme.Background

@ExperimentalFoundationApi
@Composable
fun EnterpriseSelectorScreen(
    navController: NavController,
    viewModel: EnterpriseSelectorScreenViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()
    val options by viewModel.options.observeAsState()
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            PerseoTopBar(
                title = "Elige tu Ciudad",
                inDashboard = null
            ) {
                navController.navigate(PerseoScreens.OrderOptions.route) {
                    popUpTo(PerseoScreens.OrderOptions.route)
                }
            }
        },
        backgroundColor = Background,
    ) {
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (!options.isNullOrEmpty())
                EnterpriseList(options!!){
                    viewModel.updateData(it)
                    navController.navigate(PerseoScreens.Dashboard.route)
                }
        }
    }
}
package com.perseo.telecable.screens.enterpriseselector

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.perseo.telecable.R
import com.perseo.telecable.components.EnterpriseList
import com.perseo.telecable.components.PerseoTopBar
import com.perseo.telecable.navigation.PerseoScreens
import com.perseo.telecable.ui.theme.Background

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
                title = stringResource(id = R.string.choose_your_city),
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
                EnterpriseList(options!!) {
                    viewModel.updateData(it)
                    navController.navigate(PerseoScreens.Dashboard.route)
                }
        }
    }
}
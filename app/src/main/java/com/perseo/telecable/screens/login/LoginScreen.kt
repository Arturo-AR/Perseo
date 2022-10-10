package com.perseo.telecable.screens.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.perseo.telecable.R
import com.perseo.telecable.components.LogoPerseo
import com.perseo.telecable.components.UserForm
import com.perseo.telecable.navigation.PerseoScreens
import com.perseo.telecable.ui.theme.Background
import com.perseo.telecable.ui.theme.Yellow2
import com.perseo.telecable.utils.toMD5Hash
import com.perseo.telecable.utils.toast

@ExperimentalComposeUiApi
@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginScreenViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    Surface(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .background(Background)
                .padding(top = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
        ) {
            LogoPerseo(
                modifier = Modifier
                    .height(150.dp)
                    .width(150.dp)
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = stringResource(id = R.string.enter_account),
                color = Yellow2,
                style = MaterialTheme.typography.h5
            )
            Spacer(modifier = Modifier.height(40.dp))
            UserForm(loading = false) { userId, password ->
                viewModel.login(
                    userId = userId,
                    password = password.toMD5Hash(),
                    success = {
                        navController.navigate(PerseoScreens.Dashboard.route)
                    },
                    fail = { failMessage ->
                        context.toast(failMessage ?: "")
                    },
                    multipleEnterprise = {
                        navController.navigate(PerseoScreens.EnterpriseSelector.route)
                    })
            }
        }
    }
}
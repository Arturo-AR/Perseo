package com.perseo.telecable.screens.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.perseo.telecable.components.EmailInput
import com.perseo.telecable.components.LogoPerseo
import com.perseo.telecable.components.PasswordInput
import com.perseo.telecable.navigation.PerseoScreens
import com.perseo.telecable.ui.theme.Accent
import com.perseo.telecable.ui.theme.Background
import com.perseo.telecable.ui.theme.Yellow2
import com.perseo.telecable.ui.theme.Yellow4
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
                text = "Ingresa a tu cuenta",
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
                    fail = {
                        context.toast("Credenciales Inválidas")
                    },
                    multipleEnterprise = {
                        navController.navigate(PerseoScreens.EnterpriseSelector.route)
                    })
            }
        }
    }
}

@ExperimentalComposeUiApi
@Composable
fun UserForm(
    loading: Boolean = false,
    onDone: (String, String) -> Unit = { _, _ -> }
) {
    val userId = rememberSaveable { mutableStateOf("") }
    val password = rememberSaveable { mutableStateOf("") }
    val passwordVisibility = rememberSaveable { mutableStateOf(false) }
    val passwordFocusRequest = FocusRequester.Default
    val keyboardController = LocalSoftwareKeyboardController.current
    val valid = remember(userId.value, password.value) {
        userId.value.trim().isNotEmpty() && password.value.trim().isNotEmpty()
    }
    val modifier = Modifier
        .height(250.dp)
        .background(Background)
        .verticalScroll(rememberScrollState())

    Column(modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        EmailInput(
            emailState = userId,
            enable = !loading,
            onAction = KeyboardActions {
                passwordFocusRequest.requestFocus()
            })
        PasswordInput(
            modifier = Modifier.focusRequester(passwordFocusRequest),
            passwordState = password,
            labelId = "Contraseña:",
            enable = !loading,
            passwordVisibility = passwordVisibility,
            onAction = KeyboardActions {
                if (!valid) return@KeyboardActions
                //onDone(email.value.trim(), password.value.trim())
                keyboardController?.hide()
            })
        SubmitButton(
            textId = "Ingresar",
            loading = loading,
            validInputs = valid
        ) {
            onDone(userId.value.trim(), password.value.trim())
        }
    }
}

@Composable
fun SubmitButton(
    textId: String,
    loading: Boolean,
    validInputs: Boolean,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth(),
        enabled = !loading && validInputs,
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Yellow4,
            contentColor = Color.Black,
            disabledBackgroundColor = Accent
        )
    ) {
        if (loading) CircularProgressIndicator(modifier = Modifier.size(25.dp))
        else Text(text = textId, modifier = Modifier.padding(5.dp))

    }
}
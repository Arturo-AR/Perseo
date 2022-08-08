package com.cv.perseo.screens.materials

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.navigation.NavController
import com.cv.perseo.components.*
import com.cv.perseo.navigation.PerseoScreens
import com.cv.perseo.ui.theme.Background
import com.cv.perseo.ui.theme.Yellow4
import com.cv.perseo.utils.toast
import kotlinx.coroutines.launch

@Composable
fun MaterialsScreen(
    navController: NavController,
    viewModel: MaterialsScreenViewModel = hiltViewModel()
) {
    viewModel.getMaterials()
    val scaffoldState = rememberScaffoldState()
    val materials by viewModel.material.collectAsState()
    val inventory by viewModel.inventory.observeAsState()
    val context = LocalContext.current
    viewModel.getGeneralData()
    val generalData by viewModel.data.observeAsState()
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            PerseoTopBar(
                title = "Materiales",
                inDashboard = false
            ) {
                navController.navigate(PerseoScreens.OSDetails.route) {
                    popUpTo(PerseoScreens.OSDetails.route)
                }
            }
        },
        bottomBar = {
            if (generalData != null){
                PerseoBottomBar(
                    enterprise = generalData?.municipality!!,
                    enterpriseIcon = generalData?.logo!!
                )
            }
        },
        backgroundColor = Background,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier.padding(bottom = 8.dp),
                text = "Agregar Material",
                color = Yellow4,
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp
            )
            if (!inventory.isNullOrEmpty()) {
                MaterialsAddItem(inventory!!) { amount, material ->
                    if (amount != -1.0){
                        if (amount <= material.amount) {
                            viewModel.addMaterial(material, amount)
                            Log.d("cant3idad", "Correcto")
                        } else {
                            context.toast("Materiales Insuficientes")
                        }
                    } else {
                        context.toast("Ingrese cantidad")
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            MaterialsFinalList(materials) {
                viewModel.deleteMaterialById(it)
            }
        }
    }
}
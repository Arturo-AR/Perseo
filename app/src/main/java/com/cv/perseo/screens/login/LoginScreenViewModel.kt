package com.cv.perseo.screens.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cv.perseo.model.database.GeneralData
import com.cv.perseo.model.database.Permissions
import com.cv.perseo.model.perseoresponse.EnterpriseBody
import com.cv.perseo.model.perseoresponse.PermissionsBody
import com.cv.perseo.repository.DatabaseRepository
import com.cv.perseo.repository.PerseoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginScreenViewModel @Inject constructor(
    private val repository: PerseoRepository,
    private val dbRepository: DatabaseRepository
) :
    ViewModel() {

    fun login(userId: String, password: String, success: () -> Unit, fail: () -> Unit) {
        viewModelScope.launch {
            try {
                val data = repository.login(userId = userId, password = password)
                if (data.body()?.responseCode == 200
                ) {
                    if (data.body()?.responseBody?.enterprises?.size == 1) {
                        saveData(
                            userId,
                            data.body()?.responseBody?.enterprises!![0],
                            data.body()?.responseBody?.permissions!!
                        )
                    }
                    success()
                } else {
                    fail()
                }
            } catch (ex: Exception) {
                Log.d("Login", "Error at login: ${ex.message}")
            }
        }
    }


    private suspend fun saveData(
        idUser: String,
        enterprise: EnterpriseBody,
        permissions: List<PermissionsBody>
    ) {
        val data = GeneralData(
            idUser = idUser,
            //doing = false,
            //onWay = false,
            municipality = enterprise.municipality,
            logo = enterprise.logo,
            logoIcon = enterprise.logoIcon,
            idMunicipality = enterprise.idMunicipality
        )
        for (permission in permissions) {
            val permissionAct = Permissions(
                idActivitySon = permission.idActivitySon,
                idActivityFather = permission.idActivityFather,
                icon = permission.icon
            )
            dbRepository.insertPermissions(permissionAct)
        }
        dbRepository.insertGeneralData(data)
    }
}
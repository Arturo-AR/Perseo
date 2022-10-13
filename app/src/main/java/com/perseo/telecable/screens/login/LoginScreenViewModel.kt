package com.perseo.telecable.screens.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.perseo.telecable.model.database.GeneralData
import com.perseo.telecable.model.database.Permissions
import com.perseo.telecable.model.perseoresponse.EnterpriseBody
import com.perseo.telecable.model.perseoresponse.PermissionsBody
import com.perseo.telecable.repository.DatabaseRepository
import com.perseo.telecable.repository.PerseoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginScreenViewModel @Inject constructor(
    private val repository: PerseoRepository,
    private val dbRepository: DatabaseRepository
) :
    ViewModel() {

    fun login(
        userId: String,
        password: String,
        success: () -> Unit,
        fail: (String?) -> Unit,
        multipleEnterprise: () -> Unit
    ) {
        viewModelScope.launch {
            try {
                val data = repository.login(userId = userId, password = password)
                if (data.body()?.responseCode == 200) {
                    if (data.body()?.responseBody?.enterprises?.size!! <= 1) {
                        saveData(
                            userId,
                            password,
                            data.body()?.responseBody?.enterprises!![0],
                            data.body()?.responseBody?.permissions!!
                        )
                        success()
                    } else {
                        saveData(
                            userId,
                            password,
                            null,
                            data.body()?.responseBody?.permissions!!
                        )
                        multipleEnterprise()
                    }
                } else {
                    fail(data.body()?.responseMessage)
                }
            } catch (ex: Exception) {
                Log.d("Login", "Error at login: ${ex.message}")
            }
        }
    }


    private suspend fun saveData(
        idUser: String,
        password: String,
        enterprise: EnterpriseBody?,
        permissions: List<PermissionsBody>
    ) {
        viewModelScope.launch {
            val data = GeneralData(
                idUser = idUser,
                password = password,
                tradeName = enterprise?.tradeName ?: "",
                municipality = enterprise?.municipality ?: "",
                logo = enterprise?.logo ?: "",
                logoIcon = enterprise?.logoIcon ?: "",
                idMunicipality = enterprise?.idMunicipality ?: 0
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
        }.join()
    }
}
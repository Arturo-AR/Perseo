package com.cv.perseo.screens.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cv.perseo.model.database.GeneralData
import com.cv.perseo.navigation.PerseoScreens
import com.cv.perseo.repository.DatabaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardScreenViewModel @Inject constructor(
    private val dbRepository: DatabaseRepository
) :
    ViewModel() {

    private val _permissions = MutableStateFlow<List<String>>(emptyList())
    val permissions = _permissions.asStateFlow()

    private val _data: MutableLiveData<GeneralData> = MutableLiveData()
    val data: LiveData<GeneralData> = _data

    init {
        viewModelScope.launch {
            dbRepository.getPermissions().distinctUntilChanged()
                .collect { permissions ->
                    if (permissions.isNotEmpty()) {
                        val finalPermissions = mutableListOf<String>()
                        for (permission in permissions) {
                            if (permission.idActivityFather == PerseoScreens.Dashboard.id) {
                                finalPermissions.add(permission.icon)
                            }
                        }
                        _permissions.value = finalPermissions
                    }
                }
        }
    }

    fun signOut() {
        viewModelScope.launch {
            dbRepository.deleteGeneralData()
            dbRepository.deletePermissions()
            dbRepository.deleteServiceOrders()
        }
    }

    fun getGeneralData() {
        viewModelScope.launch {
            dbRepository.getGeneralData().distinctUntilChanged()
                .collect { data ->
                    if (data.isNotEmpty()) {
                        _data.value = data[0]
                    }
                }
        }
    }
}
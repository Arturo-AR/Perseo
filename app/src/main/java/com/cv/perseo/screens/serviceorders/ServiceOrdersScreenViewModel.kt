package com.cv.perseo.screens.serviceorders

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cv.perseo.repository.DatabaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ServiceOrdersScreenViewModel @Inject constructor(
    private val dbRepository: DatabaseRepository
) :
    ViewModel() {

    private val _permissions = MutableStateFlow<List<String>>(emptyList())
    val permissions = _permissions.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            dbRepository.getPermissions().distinctUntilChanged()
                .collect { permissions ->
                    if (permissions.isNotEmpty()) {
                        val finalPermissions = mutableListOf<String>()
                        for (permission in permissions) {
                            if (permission.idActivityFather == 3) {
                                finalPermissions.add(permission.icon)
                            }
                        }
                        _permissions.value = finalPermissions
                    }
                }
        }
    }
}
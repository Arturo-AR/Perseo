package com.cv.perseo.screens.myserviceorders

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cv.perseo.model.database.ServiceOrder
import com.cv.perseo.repository.DatabaseRepository
import com.cv.perseo.repository.SharedRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyServiceOrdersScreenViewModel @Inject constructor(
    private val dbRepository: DatabaseRepository,
    private val prefs: SharedRepository
) :
    ViewModel() {
    private val _serviceOrdersZones = MutableStateFlow<List<String>>(emptyList())
    val serviceOrdersZones = _serviceOrdersZones.asStateFlow()

    private val _serviceOrders = MutableStateFlow<List<ServiceOrder>>(emptyList())
    val serviceOrders = _serviceOrders.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            dbRepository.getZones().distinctUntilChanged()
                .collect { zones ->
                    if (zones.isNotEmpty()) {
                        _serviceOrdersZones.value = zones
                    }
                }
        }
    }

    fun saveZone(zone: String) {
        prefs.saveZone(zone)
    }
}
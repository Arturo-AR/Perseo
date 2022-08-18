package com.perseo.telecable.screens.myserviceorders

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.perseo.telecable.model.database.GeneralData
import com.perseo.telecable.model.database.ServiceOrder
import com.perseo.telecable.repository.DatabaseRepository
import com.perseo.telecable.repository.SharedRepository
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

    private val _data : MutableLiveData<GeneralData> = MutableLiveData()
    val data : LiveData<GeneralData> = _data

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

    fun getGeneralData() {
        viewModelScope.launch {
            dbRepository.getGeneralData().distinctUntilChanged()
                .collect { data ->
                    _data.value = data[0]
                }
        }
    }
}
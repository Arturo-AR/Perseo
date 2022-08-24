package com.perseo.telecable.screens.rubro

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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RubroScreenViewModel @Inject constructor(
    private val prefs: SharedRepository,
    private val dbRepository: DatabaseRepository
) :
    ViewModel() {
    private val _serviceOrders = MutableStateFlow<List<ServiceOrder>>(emptyList())
    val serviceOrders = _serviceOrders.asStateFlow()

    private val _data :  MutableLiveData<GeneralData> =MutableLiveData()
    val data : LiveData<GeneralData> = _data

    private val _currentRubro: MutableLiveData<String> = MutableLiveData()
    val currentRubro: LiveData<String> = _currentRubro

    init {
        val rubro = prefs.getRubro()
        _currentRubro.value = rubro
        viewModelScope.launch(Dispatchers.IO) {
            dbRepository.getServiceOrders(zone = prefs.getZone(), rubro = rubro)
                .distinctUntilChanged()
                .collect { orders ->
                    if (orders.isNotEmpty()) {
                        _serviceOrders.value = orders
                    }
                }
        }
    }


    fun saveOsId(id: Int) {
        prefs.saveOsId(id)
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
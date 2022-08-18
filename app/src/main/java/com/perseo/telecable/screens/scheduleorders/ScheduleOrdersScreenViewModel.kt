package com.perseo.telecable.screens.scheduleorders

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.perseo.telecable.model.database.GeneralData
import com.perseo.telecable.model.database.ServiceOrder
import com.perseo.telecable.repository.DatabaseRepository
import com.perseo.telecable.repository.SharedRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScheduleOrdersScreenViewModel @Inject constructor(
    private val dbRepository: DatabaseRepository,
    private val prefs: SharedRepository
) :
    ViewModel() {
    private val _scheduleOrders = MutableStateFlow<List<ServiceOrder>>(emptyList())
    val scheduleOrders = _scheduleOrders.asStateFlow()

    private val _data : MutableLiveData<GeneralData> = MutableLiveData()
    val data : LiveData<GeneralData> = _data

    init {
        viewModelScope.launch(Dispatchers.IO) {
            dbRepository.getScheduleOrders().distinctUntilChanged()
                .collect { schedule ->
                    if (schedule.isNotEmpty()) {
                        _scheduleOrders.value = schedule
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
package com.cv.perseo.screens.scheduleorders

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cv.perseo.model.database.ServiceOrder
import com.cv.perseo.repository.DatabaseRepository
import com.cv.perseo.repository.SharedRepository
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

}
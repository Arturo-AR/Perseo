package com.cv.perseo.screens.zone

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cv.perseo.model.Rubro
import com.cv.perseo.repository.DatabaseRepository
import com.cv.perseo.repository.SharedRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ZoneScreenViewModel @Inject constructor(
    private val prefs: SharedRepository,
    private val dbRepository: DatabaseRepository
) :
    ViewModel() {
    private val _currentZone: MutableLiveData<String> = MutableLiveData()
    val currentZone: LiveData<String> = _currentZone

    private val _rubro = MutableStateFlow<List<Rubro>>(emptyList())
    val rubro = _rubro.asStateFlow()

    init {
        val zone = prefs.getZone()
        _currentZone.value = zone
        viewModelScope.launch(Dispatchers.IO) {
            dbRepository.getRubro(zone).distinctUntilChanged()
                .collect { rubro ->
                    if (rubro.isNotEmpty()) {
                        _rubro.value = rubro
                    }
                }
        }
    }

    fun saveRubro(rubro: String){
        prefs.saveRubro(rubro)
    }
}
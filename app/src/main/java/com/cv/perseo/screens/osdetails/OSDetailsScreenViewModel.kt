package com.cv.perseo.screens.osdetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cv.perseo.model.database.GeneralData
import com.cv.perseo.model.perseoresponse.ServiceOrderItem
import com.cv.perseo.repository.DatabaseRepository
import com.cv.perseo.repository.PerseoRepository
import com.cv.perseo.repository.SharedRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OSDetailsScreenViewModel @Inject constructor(
    private val dbRepository: DatabaseRepository,
    private val repository: PerseoRepository,
    private val prefs: SharedRepository
) :
    ViewModel() {

    private val _currentOs: MutableLiveData<ServiceOrderItem> = MutableLiveData()
    val currentOs: LiveData<ServiceOrderItem> = _currentOs

    private val _generalData = MutableStateFlow<List<GeneralData>>(emptyList())
    val generalData = _generalData.asStateFlow()

    private val _doing: MutableLiveData<Boolean> = MutableLiveData()
    val doing: LiveData<Boolean> = _doing

    private val _onWay: MutableLiveData<Boolean> = MutableLiveData()
    val onWay: LiveData<Boolean> = _onWay

    private val _motivos: MutableLiveData<List<String>> = MutableLiveData()
    val motivos: LiveData<List<String>> = _motivos

    init {
        viewModelScope.launch {
            _doing.value = prefs.getDoing()
            _onWay.value = prefs.getOnWay()
            dbRepository.deleteServiceOrders()
            dbRepository.getGeneralData()
                .collect { generalData ->

                    _generalData.value = generalData
                    val response = repository.getServiceOrders(
                        userId = generalData[0].idUser,
                        enterpriseId = generalData[0].idMunicipality,
                        osId = prefs.getId()
                    )
                    if (response.isSuccessful) {
                        if (response.body()?.responseCode == 200) {
                            _currentOs.value = response.body()?.responseBody!![0]
                        }
                    }
                }
        }
    }

    fun getMotivos(motivoId: String, enterpriseId: Int) {
        viewModelScope.launch {
            val response = repository.motivoOrders(motivoId = motivoId, enterpriseId = enterpriseId)
            if (response.isSuccessful) {
                if (response.body()?.responseCode == 200) {
                    _motivos.value = response.body()!!.responseBody
                }
            }
        }
    }

    fun startRoute() {
        prefs.saveOnWay(true)
        _onWay.value = prefs.getOnWay()
    }

    fun startDoing() {
        prefs.saveDoing(true)
        _doing.value = prefs.getDoing()
    }

    fun finishRoute() {
        prefs.saveOnWay(false)
        _onWay.value = prefs.getOnWay()
    }

    fun finishDoing() {
        prefs.saveDoing(false)
        _doing.value = prefs.getDoing()
    }

    fun cancelOrder() {
        finishDoing()
        finishRoute()
        viewModelScope.launch {
            dbRepository.deleteMaterials()
            dbRepository.deleteEquipment()
        }
    }
}
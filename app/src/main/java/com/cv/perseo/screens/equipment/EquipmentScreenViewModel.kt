package com.cv.perseo.screens.equipment

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cv.perseo.model.EquipmentTmp
import com.cv.perseo.model.database.GeneralData
import com.cv.perseo.model.perseoresponse.ServiceOrderItem
import com.cv.perseo.repository.DatabaseRepository
import com.cv.perseo.repository.PerseoRepository
import com.cv.perseo.repository.SharedRepository
import com.cv.perseo.utils.toBase64String
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EquipmentScreenViewModel @Inject constructor(
    private val dbRepository: DatabaseRepository,
    private val repository: PerseoRepository,
    private val prefs: SharedRepository
) :
    ViewModel() {

    private val _equipmentTmp: MutableLiveData<MutableList<EquipmentTmp>> = MutableLiveData(
        mutableListOf()
    )
    val equipmentTmp: LiveData<MutableList<EquipmentTmp>> = _equipmentTmp

    private val _currentOs: MutableLiveData<ServiceOrderItem> = MutableLiveData()
    val currentOs: LiveData<ServiceOrderItem> = _currentOs

    private val _generalData = MutableStateFlow<List<GeneralData>>(emptyList())
    val generalData = _generalData.asStateFlow()

    private val _motivos: MutableLiveData<List<String>> = MutableLiveData()
    val motivos: LiveData<List<String>> = _motivos

    init {
        viewModelScope.launch {
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
                    val motivosResponse = response.body()!!.responseBody
                    val motivos = mutableListOf<String>()
                    motivosResponse?.map { motivo ->
                        val motivoArray = motivo.split("_")
                        if (motivoArray[0] == "AGREGAR" || motivoArray[0] == "QUITAR") {
                            if (motivoArray.size > 2) {
                                motivos.add("${motivoArray[1]} ${motivoArray[2]}")
                            } else {
                                motivos.add(motivoArray[1])
                            }
                        }
                    }
                    _motivos.value = motivos
                }
            }
        }
    }

    fun saveTmp(equipment: String?, idEquipment: String?, image: Bitmap?) {

        val current = _equipmentTmp.value?.find { it.equipment == equipment }
        if (current == null) {
            _equipmentTmp.value?.add(EquipmentTmp(equipment, idEquipment, image?.toBase64String()))
        } else {
            if (idEquipment == null) {
                current.imageBitmap = image?.toBase64String()
            } else {
                current.idEquipment = idEquipment
            }
        }
    }
}
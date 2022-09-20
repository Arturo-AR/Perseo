package com.perseo.telecable.screens.completedordersummary

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.perseo.telecable.ApplicationViewModel
import com.perseo.telecable.model.database.ComplianceInfo
import com.perseo.telecable.model.database.Equipment
import com.perseo.telecable.model.database.GeneralData
import com.perseo.telecable.model.database.Materials
import com.perseo.telecable.model.perseorequest.EquipmentRequest
import com.perseo.telecable.model.perseorequest.ImageRequest
import com.perseo.telecable.model.perseoresponse.AntennaSectorial
import com.perseo.telecable.model.perseoresponse.RouterCentral
import com.perseo.telecable.model.perseoresponse.ServiceOrderItem
import com.perseo.telecable.model.perseoresponse.TerminalBox
import com.perseo.telecable.repository.DatabaseRepository
import com.perseo.telecable.repository.ImgurRepository
import com.perseo.telecable.repository.PerseoRepository
import com.perseo.telecable.repository.SharedRepository
import com.perseo.telecable.utils.Constants
import com.perseo.telecable.utils.toDate
import com.perseo.telecable.utils.toHour
import com.perseo.telecable.utils.toJsonString
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class CompletedOrderSummaryScreenViewModel @Inject constructor(
    application: Application,
    private val dbRepository: DatabaseRepository,
    private val prefs: SharedRepository,
    private val repository: PerseoRepository,
    private val imgurRepository: ImgurRepository
) :
    ApplicationViewModel(application) {

    private val _materials: MutableLiveData<List<Materials>> = MutableLiveData()
    val material: LiveData<List<Materials>> = _materials

    private val _equipment: MutableLiveData<List<Equipment>> = MutableLiveData()
    val equipment: LiveData<List<Equipment>> = _equipment

    private val _equipmentRequest: MutableLiveData<List<EquipmentRequest>> = MutableLiveData()

    private val _data: MutableLiveData<GeneralData> = MutableLiveData()
    val data: LiveData<GeneralData> = _data

    private val _complianceInfo: MutableLiveData<ComplianceInfo> = MutableLiveData()

    private val _finalImages: MutableLiveData<MutableList<ImageRequest>> = MutableLiveData(mutableListOf())

    private val _currentOs: MutableLiveData<ServiceOrderItem> = MutableLiveData()
    val currentOs: LiveData<ServiceOrderItem> = _currentOs

    private val _doing: MutableLiveData<Boolean> = MutableLiveData()

    private val _onWay: MutableLiveData<Boolean> = MutableLiveData()


    init {
        viewModelScope.launch {
            dbRepository.getGeneralData().distinctUntilChanged()
                .collect { data ->
                    _data.value = data[0]
                    val response = repository.getServiceOrders(
                        userId = data[0].idUser,
                        enterpriseId = data[0].idMunicipality,
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

    fun getMaterials() {
        viewModelScope.launch {
            dbRepository.getAllMaterials().distinctUntilChanged()
                .collect { material ->
                    _materials.value = material

                }
        }
    }

    fun getEquipment() {
        viewModelScope.launch {
            dbRepository.getAllEquipment().distinctUntilChanged()
                .collect { equipment ->
                    _equipment.value = equipment
                }
        }
    }

    private fun finishRoute() {
        prefs.saveOnWay(false)
        _onWay.value = prefs.getOnWay()
    }

    private fun finishDoing() {
        prefs.saveDoing(false)
        _doing.value = prefs.getDoing()
    }

    fun finishOrder(onClick: () -> Unit) {
        viewModelScope.launch {
            endCompliance(
                getLocationLiveData().value?.latitude ?: "",
                getLocationLiveData().value?.longitude ?: ""
            )
            val ok = uploadImages()
            if (ok) {

                try {
                    val response = repository.finalizarOrdenServicio(
                        empresa_id = _data.value?.idMunicipality ?: 0,
                        ordenes_info_cumplimiento = _complianceInfo.value?.toJsonString()!!,
                        fotos = _finalImages.value?.toJsonString()!!,
                        equipos = _equipmentRequest.value?.toJsonString()!!,
                        orden = currentOs.value?.osId!!,
                        fecha = Date().toDate(),
                        materiales = material.value?.toJsonString()!!,
                        parametros = "[]",
                        info_cumplimiento = "{}"
                    )
                    if (response.isSuccessful) {
                        if (response.body() == "Committed") {
                            finishDoing()
                            finishRoute()
                            viewModelScope.launch {
                                dbRepository.deleteMaterials()
                                dbRepository.deleteEquipment()
                                dbRepository.deleteComplianceInfo()
                            }
                            onClick()
                        }
                    }
                } catch (ex: Exception) {
                    ex.printStackTrace()
                }
            }
        }
    }

    private suspend fun endCompliance(lat: String, lon: String) {
        viewModelScope.launch {
            val myDate = Date()
            _complianceInfo.value?.fecha_fin = myDate.toDate()
            _complianceInfo.value?.hora_fin = myDate.toHour()
            _complianceInfo.value?.ubicacion_fin = "$lat, $lon"
            dbRepository.updateComplianceInfo(_complianceInfo.value!!)
            updateInfo()
        }.join()
    }

    private suspend fun uploadImages(): Boolean {
        viewModelScope.launch {
            equipment.value?.map {
                if (!it.url_image.isNullOrEmpty()) {
                    val title =
                        if (it.id_tipo_equipo == "") it.nombre_imagen_adicional else it.id_tipo_equipo
                    val response = imgurRepository.uploadImage(
                        image = it.url_image!!,
                        album = getAlbum(),
                        title = "$title||${currentOs.value?.requestNumber}"
                    )
                    if (response.isSuccessful) {
                        _finalImages.value?.add(
                            ImageRequest(
                                description = response.body()?.upload?.title!!,
                                equipmentId = it.id_equipo,
                                osId = currentOs.value?.osId!!,
                                equipmentTypeId = it.id_tipo_equipo,
                                requestNumber = currentOs.value?.requestNumber!!,
                                link = response.body()?.upload?.link!!,
                            )
                        )
                    }
                }
            }
        }.join()
        return true
    }

    fun updateInfo() {
        viewModelScope.launch {
            dbRepository.getCompliance().distinctUntilChanged()
                .collect { compliance ->
                    _complianceInfo.value = compliance
                }
        }
    }

    private fun getAlbum(): String {
        return when (_data.value?.municipality) {
            "PACHUCA" -> Constants.ID_PACHUCA_ALBUM
            "MORELIA" -> Constants.ID_MORELIA_ALBUM
            "TULANCINGO" -> Constants.ID_TULANCINGO_ALBUM
            "TEST" -> Constants.ID_TEST_ALBUM
            else -> ""
        }
    }

    fun updateEquipment() {
        viewModelScope.launch {
            var currentEquipment: List<EquipmentRequest>
            dbRepository.getAllEquipment().distinctUntilChanged()
                .collect { equipment ->
                    currentEquipment = (equipment.filter {
                        it.id_tipo_equipo != ""
                    }).map {
                        EquipmentRequest(
                            osId = currentOs.value?.osId ?: 0,
                            equipmentId = it.id_equipo,
                            equipmentTypeId = it.id_tipo_equipo
                        )
                    }
                    _equipmentRequest.value = currentEquipment
                }
        }
    }
}
package com.cv.perseo.screens.osdetails

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cv.perseo.model.database.ComplianceInfo
import com.cv.perseo.model.database.Equipment
import com.cv.perseo.model.database.GeneralData
import com.cv.perseo.model.database.Materials
import com.cv.perseo.model.perseorequest.EquipmentRequest
import com.cv.perseo.model.perseorequest.ImageRequest
import com.cv.perseo.model.perseoresponse.ServiceOrderItem
import com.cv.perseo.repository.DatabaseRepository
import com.cv.perseo.repository.ImgurRepository
import com.cv.perseo.repository.PerseoRepository
import com.cv.perseo.repository.SharedRepository
import com.cv.perseo.utils.toDate
import com.cv.perseo.utils.toHour
import com.cv.perseo.utils.toJsonString
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject

@HiltViewModel
class OSDetailsScreenViewModel @Inject constructor(
    private val dbRepository: DatabaseRepository,
    private val repository: PerseoRepository,
    private val prefs: SharedRepository,
    private val imgurRepository: ImgurRepository
) :
    ViewModel() {

    private val _images: MutableLiveData<List<Equipment>> = MutableLiveData(mutableListOf())
    val images: LiveData<List<Equipment>> = _images

    private val _equipment: MutableLiveData<List<EquipmentRequest>> =
        MutableLiveData(mutableListOf())
    val equipment: LiveData<List<EquipmentRequest>> = _equipment

    private val _materials: MutableLiveData<List<Materials>> = MutableLiveData()
    val material: LiveData<List<Materials>> = _materials

    private val _complianceInfo: MutableLiveData<ComplianceInfo> = MutableLiveData()
    val complianceInfo: LiveData<ComplianceInfo> = _complianceInfo

    private val _finalImages: MutableLiveData<MutableList<ImageRequest>> =
        MutableLiveData(mutableListOf())
    val finalImages: LiveData<MutableList<ImageRequest>> = _finalImages

    private val allEquipment: MutableLiveData<List<Equipment>> = MutableLiveData()

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

    fun finishOrder() {
        viewModelScope.launch {
            val ok = uploadImages()
            if (ok) {
                Log.d("id_empresa", generalData.value[0].idMunicipality.toString())
                Log.d("info_pre", complianceInfo.value?.toJsonString()!!)
                Log.d("imagenes", finalImages.value?.toJsonString()!!)
                Log.d("equipos", equipment.value?.toJsonString()!!)
                Log.d("materiales", complianceInfo.value?.toJsonString()!!)
                Log.d("parametros_ro", "[]")
                Log.d("id_os", currentOs.value?.osId.toString())
                Log.d("fecha", Date().toDate())
                Log.d("info_cum", "{}")
/*                val response = repository.finalizarOrdenServicio(
                    generalData.value[0].idMunicipality,
                    complianceInfo.value?.toJsonString()!!,
                    finalImages.value?.toJsonString()!!,
                    equipment.value?.toJsonString()!!,
                    currentOs.value?.osId!!,
                    Date().toDate(),
                    material.value?.toJsonString()!!,
                    "[]",
                    "{}"
                )

                if (response.isSuccessful) {
                    Log.d("Success", "Cumplimiento correcto")
                }*/

            }
        }
    }

    private suspend fun uploadImages(): Boolean {
        viewModelScope.launch {
            allEquipment.value?.map {
                if (!it.url_image.isNullOrEmpty()) {
                    val title =
                        if (it.id_tipo_equipo == "") it.nombre_imagen_adicional else it.id_equipo
                    val response = imgurRepository.uploadImage(
                        image = it.url_image!!,
                        album = "9K03yxW",
                        title = "$title||${currentOs.value?.osId}"
                    )
                    if (response.isSuccessful) {
                        _finalImages.value?.add(
                            ImageRequest(
                                description = response.body()?.upload?.title!!,
                                equipmentId = it.id_equipo,
                                osId = currentOs.value?.osId!!,
                                equipmentTypeId = it.id_tipo_equipo,
                                requestNumber = currentOs.value?.noContract!!,
                                link = response.body()?.upload?.link!!,
                            )
                        )
                    }
                    Log.d("Link", response.body()?.upload?.link!!)
                }
            }
        }.join()
        return true
    }

    fun saveImages(name: String, image: String) {
        viewModelScope.launch {
            dbRepository.insertEquipment(
                Equipment(
                    nombre_imagen_adicional = name,
                    id_tipo_equipo = "",
                    id_equipo = "",
                    url_image = image
                )
            )
            updateImages()
        }
    }

    fun updateImages() {
        viewModelScope.launch {
            var extraImages: List<Equipment>
            dbRepository.getAllEquipment().distinctUntilChanged()
                .collect { equipment ->
                    extraImages = (equipment.filter {
                        it.id_tipo_equipo == ""
                    })
                    _images.value = extraImages
                }

        }
    }

    fun updateMaterials() {
        viewModelScope.launch {
            dbRepository.getAllMaterials().distinctUntilChanged()
                .collect { material ->
                    _materials.value = material

                }
        }
    }

    fun updateInfo() {
        viewModelScope.launch {
            dbRepository.getCompliance().distinctUntilChanged()
                .collect { compliance ->
                    _complianceInfo.value = compliance
                }
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
                            osId = currentOs.value?.osId!!,
                            equipmentId = it.id_equipo,
                            equipmentTypeId = it.id_tipo_equipo
                        )
                    }
                    _equipment.value = currentEquipment

                }
        }
    }

    fun updateAllEquipment() {
        viewModelScope.launch {
            dbRepository.getAllEquipment().distinctUntilChanged()
                .collect { equipment ->

                    allEquipment.value = equipment

                }
        }
    }

    fun startCompliance() {
        viewModelScope.launch {
            val myDate = Date()
            dbRepository.insertCompliance(
                ComplianceInfo(
                    id_empresa = generalData.value[0].idMunicipality,
                    id_os = currentOs.value?.osId!!,
                    fecha_fin = myDate.toDate(),
                    fecha_inicio = myDate.toDate(),
                    hora_fin = myDate.toHour(),
                    hora_inicio = myDate.toHour(),
                    ubicacion_fin = "",
                    ubicacion_inicio = "",
                    respuesta1 = "",
                    respuesta2 = "",
                    respuesta3 = ""
                )
            )
            updateInfo()
        }
    }

    fun printImages() {
        Log.d("images", finalImages.value?.toJsonString()!!)
    }
}
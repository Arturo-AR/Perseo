package com.perseo.telecable.screens.osdetails

import android.app.Application
import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.perseo.telecable.ApplicationViewModel
import com.perseo.telecable.model.database.ComplianceInfo
import com.perseo.telecable.model.database.Equipment
import com.perseo.telecable.model.database.GeneralData
import com.perseo.telecable.model.database.Materials
import com.perseo.telecable.model.perseorequest.*
import com.perseo.telecable.model.perseoresponse.ServiceOrderItem
import com.perseo.telecable.model.perseoresponse.SubscriberImage
import com.perseo.telecable.repository.DatabaseRepository
import com.perseo.telecable.repository.ImgurRepository
import com.perseo.telecable.repository.PerseoRepository
import com.perseo.telecable.repository.SharedRepository
import com.perseo.telecable.utils.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class OSDetailsScreenViewModel @Inject constructor(
    application: Application,
    private val dbRepository: DatabaseRepository,
    private val repository: PerseoRepository,
    private val prefs: SharedRepository,
    private val imgurRepository: ImgurRepository
) :
    ApplicationViewModel(application) {

    private val _cancelImages: MutableLiveData<List<Bitmap>> = MutableLiveData(mutableListOf())
    val cancelImages: MutableLiveData<List<Bitmap>> = _cancelImages

    private val _images: MutableLiveData<List<Equipment>> = MutableLiveData(mutableListOf())
    val images: LiveData<List<Equipment>> = _images

    private val _equipment: MutableLiveData<List<EquipmentRequest>> =
        MutableLiveData(mutableListOf())
    val equipment: LiveData<List<EquipmentRequest>> = _equipment

    private val _materials: MutableLiveData<List<Materials>> = MutableLiveData()
    val material: LiveData<List<Materials>> = _materials

    private val _complianceInfo: MutableLiveData<ComplianceInfo> = MutableLiveData()
    //val complianceInfo: LiveData<ComplianceInfo> = _complianceInfo

/*    private val _finalImages: MutableLiveData<MutableList<ImageRequest>> =
        MutableLiveData(mutableListOf())
    val finalImages: LiveData<MutableList<ImageRequest>> = _finalImages*/

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

    private val _subscriberImages: MutableLiveData<List<SubscriberImage?>> = MutableLiveData()
    val subscriberImages: MutableLiveData<List<SubscriberImage?>> = _subscriberImages

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
                    val motivosOriginal = response.body()!!.responseBody
                    val motivos = mutableListOf<String>()
                    motivosOriginal?.map { motivo ->
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

    fun cancelOrder(reason: String, images: List<String>, onSuccess: (String) -> Unit) {
        viewModelScope.launch {
            try {
                val links = uploadCancelImages(images)
                val reasons = CancelOrderRequest(
                    osId = currentOs.value?.osId ?: 0,
                    reason = reason,
                    imageUrl1 = links[0],
                    imageUrl2 = links[1],
                    imageUrl3 = links[2],
                    location = "${getLocationLiveData().value?.latitude ?: ""},${getLocationLiveData().value?.longitude ?: "" }"
                )
                val response =
                    repository.cancelOrder(
                        generalData.value[0].idMunicipality,
                        reasons.toJsonString()
                    )
                if (response.isSuccessful) {
                    when (response.body()?.responseCode) {
                        200, 404 -> {
                            finishDoing()
                            finishRoute()
                            viewModelScope.launch {
                                dbRepository.deleteMaterials()
                                dbRepository.deleteEquipment()
                                dbRepository.deleteComplianceInfo()
                            }
                            onSuccess("Orden Cancelada Correctamente")
                        }
                        204 -> {
                            finishDoing()
                            finishRoute()
                            viewModelScope.launch {
                                dbRepository.deleteMaterials()
                                dbRepository.deleteEquipment()
                                dbRepository.deleteComplianceInfo()
                            }
                            onSuccess("Algo salió mal")
                        }
                    }
                }
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }
    }

    private suspend fun uploadCancelImages(images: List<String>): List<String> {
        val linkList: MutableList<String> = mutableListOf()
        viewModelScope.launch {
            images.mapIndexed { index, it ->
                val title =
                    "can${index + 1}||${currentOs.value?.osId}||${Date().toDate()}||${currentOs.value?.requestNumber}"
                val album = getAlbum()
                val response = imgurRepository.uploadImage(
                    image = it,
                    album = album,
                    title = title
                )
                if (response.isSuccessful) {
                    linkList.add(response.body()?.upload?.link!!)
                }
            }
            for (x in linkList.size until 3) {
                linkList.add("")
            }
        }.join()
        return linkList
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

    fun startCompliance(lat: String, lon: String) {
        viewModelScope.launch {
            val myDate = Date()
            dbRepository.insertCompliance(
                ComplianceInfo(
                    id_empresa = generalData.value[0].idMunicipality,
                    id_os = currentOs.value?.osId!!,
                    fecha_fin = "",
                    fecha_inicio = myDate.toDate(),
                    hora_fin = "",
                    hora_inicio = myDate.toHour(),
                    ubicacion_fin = "",
                    ubicacion_inicio = "$lat, $lon",
                    respuesta1 = "",
                    respuesta2 = "",
                    respuesta3 = ""
                )
            )
            updateInfo()
        }
    }

    fun addCancelImage(bitmap: Bitmap?) {
        if (bitmap != null) {
            _cancelImages.value = _cancelImages.value?.plus(bitmap) ?: emptyList()
        }
    }

    fun deleteImage(equipment: Equipment) {
        viewModelScope.launch {
            dbRepository.deleteEquipmentByUnit(equipment)
        }
    }

    fun deleteCancelImage(bitmap: Bitmap?) {
        if (bitmap != null) {
            _cancelImages.value = _cancelImages.value?.minus(bitmap)
        }
    }

    fun getSubscriberImages() {
        viewModelScope.launch {
            val response = repository.getSubscriberImages(
                generalData.value[0].idMunicipality,
                currentOs.value?.requestNumber!!
            )
            if (response.isSuccessful) {
                _subscriberImages.value = response.body()?.responseBody
            }
        }
    }

    private fun getAlbum(): String {
        return when (generalData.value[0].municipality) {
            "PACHUCA" -> Constants.ID_PACHUCA_ALBUM
            "MORELIA" -> Constants.ID_MORELIA_ALBUM
            "TULANCINGO" -> Constants.ID_TULANCINGO_ALBUM
            "TEST" -> Constants.ID_TEST_ALBUM
            else -> ""
        }
    }
}
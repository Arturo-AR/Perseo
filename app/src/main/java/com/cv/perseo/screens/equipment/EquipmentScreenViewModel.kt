package com.cv.perseo.screens.equipment

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cv.perseo.model.EquipmentTmp
import com.cv.perseo.model.database.Equipment
import com.cv.perseo.model.database.GeneralData
import com.cv.perseo.model.perseorequest.ValidateEquipmentRequest
import com.cv.perseo.model.perseoresponse.RouterCentral
import com.cv.perseo.model.perseoresponse.ServiceOrderItem
import com.cv.perseo.model.perseoresponse.TerminalBox
import com.cv.perseo.repository.DatabaseRepository
import com.cv.perseo.repository.PerseoRepository
import com.cv.perseo.repository.SharedRepository
import com.cv.perseo.utils.toBase64String
import com.cv.perseo.utils.toJsonString
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
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

    private val motivosOriginal: MutableLiveData<List<String>> = MutableLiveData()

    private val _routers: MutableLiveData<List<RouterCentral>> = MutableLiveData()
    val routers: LiveData<List<RouterCentral>> = _routers

    private val _terminalBox: MutableLiveData<List<TerminalBox>> = MutableLiveData()
    val terminalBox: LiveData<List<TerminalBox>> = _terminalBox

    init {
        viewModelScope.launch {
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

    fun getRouterBoxes() {
        viewModelScope.launch {
            val response = repository.getRoutersCT(generalData.value[0].idMunicipality)
            if (response.isSuccessful) {
                val RC = response.body()
                if (RC?.responseCode == 200) {
                    _terminalBox.value = RC.responseBody.terminalBox
                    _routers.value = RC.responseBody.routers
                }
            }
        }
    }

    fun getMotivos(motivoId: String, enterpriseId: Int) {
        viewModelScope.launch {
            val response = repository.motivoOrders(motivoId = motivoId, enterpriseId = enterpriseId)
            if (response.isSuccessful) {
                if (response.body()?.responseCode == 200) {
                    motivosOriginal.value = response.body()!!.responseBody
                    val motivos = mutableListOf<String>()
                    motivosOriginal.value?.map { motivo ->
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
            _equipmentTmp.value?.add(
                EquipmentTmp(
                    null,
                    equipment,
                    idEquipment,
                    image?.toBase64String()
                )
            )
        } else {
            if (idEquipment == null) {
                current.image = image?.toBase64String()
            } else {
                current.idEquipment = idEquipment
            }
        }
    }

    fun saveEquipmentInDatabase() {
        viewModelScope.launch {
            dbRepository.deleteEquipment()
            for (equipment in _equipmentTmp.value!!) {
                dbRepository.insertEquipment(
                    Equipment(
                        nombre_imagen_adicional = "",
                        id_tipo_equipo = equipment.equipment!!,
                        id_equipo = equipment.idEquipment!!,
                        url_image = equipment.image
                    )
                )
            }
        }
    }

    fun initEquipment() {
        viewModelScope.launch {
            dbRepository.getAllEquipment().distinctUntilChanged()
                .collect { equipment ->
                    _equipmentTmp.value = equipment.map {
                        EquipmentTmp(
                            imageName = it.nombre_imagen_adicional,
                            equipment = it.id_tipo_equipo,
                            idEquipment = it.id_equipo,
                            image = it.url_image
                        )
                    } as MutableList
                }
        }
    }

    fun validateEquipment() {
        viewModelScope.launch {
            val equipment = ValidateEquipmentRequest(noContract = currentOs.value?.noContract!!)
            motivosOriginal.value?.map {
                when (it) {
                    "AGREGAR_CABLEMODEM" -> equipment.addCM = 1
                    "QUITAR_CABLEMODEM" -> equipment.removeCM = 1
                    "AGREGAR_DECO" -> equipment.addDeco = 1
                    "QUITAR_DECO" -> equipment.removeDeco = 1
                    "AGREGAR_ETIQUETA" -> equipment.addEtiq = 1
                    "QUITAR_ETIQUETA" -> equipment.removeEtiq = 1
                    "AGREGAR_CAJA_DIGITAL" -> equipment.addCD = 1
                    "QUITAR_CAJA_DIGITAL" -> equipment.removeCD = 1
                    "AGREGAR_CAJA_TERMINAL" -> equipment.addCT = 1
                    "QUITAR_CAJA_TERMINAL" -> equipment.removeCT = 1
                    "AGREGAR_ROUTER_CENTRAL" -> equipment.addRC = 1
                    "QUITAR_ROUTER_CENTRAL" -> equipment.removeRC = 1
                    "AGREGAR_LINEA" -> equipment.addLine = 1
                    "QUITAR_LINEA" -> equipment.removeLine = 1
                    "AGREGAR_ROUTER" -> equipment.addRouter = 1
                    "QUITAR_ROUTER" -> equipment.removeRouter = 1
                    "AGREGAR_IP_PUBLICA" -> equipment.addIp = 1
                    "QUITAR_IP_PUBLICA" -> equipment.removeIp = 1
                    "AGREGAR_ANTENA" -> equipment.addAntenna = 1
                    "QUITAR_ANTENA" -> equipment.removeAntenna = 1
                }
            }
            equipmentTmp.value?.map {
                when (it.equipment) {
                    "CABLEMODEM" -> equipment.cmId = listOf(it.idEquipment!!)
                    "DECO" -> equipment.decoId = listOf(it.idEquipment!!)
                    "ETIQUETA" -> equipment.etiqId = listOf(it.idEquipment!!)
                    "CAJA DIGITAL" -> equipment.cdId = listOf(it.idEquipment!!)
                    "CAJA TERMINAL" -> equipment.ctId = listOf(it.idEquipment!!)
                    "ROUTER CENTRAL" -> equipment.rcId = listOf(it.idEquipment!!)
                    "LINEA" -> equipment.lineId = listOf(it.idEquipment!!)
                    "ROUTER" -> equipment.routerId = listOf(it.idEquipment!!)
                    "IP PUBLICA" -> equipment.ipId = listOf(it.idEquipment!!)
                    "ANTENA" -> equipment.antennaId = listOf(it.idEquipment!!)
                }
            }
            val response = repository.validateEquipment(
                generalData.value[0].idMunicipality,
                equipment.toJsonString()
            )
            Log.d(
                "parametros",
                "${generalData.value[0].idMunicipality} ${equipment.toJsonString()}"
            )
            if (response.isSuccessful) {
                Log.d("Exito", "Llamada exitosa")
                val validate = response.body()
                if (validate?.responseBody?.code == "1") {
                    //Esta dado de alta correctamente
                    Log.d("Validate", validate.responseBody.message)
                } else {
                    //No esta dado de alta o lo tiene otro contrato
                    Log.d("Validate", validate?.responseBody?.message!!)
                }
            }
        }
    }

    fun getIdEquipment(id: String, equipmentType: String): String {
        return if (equipmentType == "ROUTER CENTRAL" || equipmentType == "CAJA TERMINAL") {
            if (equipmentType == "ROUTER CENTRAL") {
                val routerId = routers.value?.filter { it.routerCentralDesc == id }
                routerId?.get(0)?.routerCentralId.toString()
            } else {
                val boxId = terminalBox.value?.filter { it.terminalBoxDesc == id }
                boxId?.get(0)?.terminalBoxId.toString()
            }
        } else {
            id
        }
    }

    fun getEquipmentType(reason: String): String {
        return when (reason) {
            "CABLEMODEM" -> "CM"
            "DECO" -> "DECO"
            "ETIQUETA" -> "ETIQ"
            "CAJA DIGITAL" -> "CAJADIG"
            "CAJA TERMINAL" -> "CAJATER"
            "ROUTER CENTRAL" -> "ROUTERCEN"
            "LINEA" -> "LINEA"
            "ROUTER" -> "ROUTER"
            "IP PUBLICA" -> "IP"
            "ANTENA" -> "ANTE"
            else -> ""
        }
    }
}
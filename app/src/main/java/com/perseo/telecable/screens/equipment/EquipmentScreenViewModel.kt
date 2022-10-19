package com.perseo.telecable.screens.equipment

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.perseo.telecable.model.EquipmentTmp
import com.perseo.telecable.model.database.Equipment
import com.perseo.telecable.model.database.GeneralData
import com.perseo.telecable.model.perseorequest.ValidateEquipmentRequest
import com.perseo.telecable.model.perseoresponse.AntennaSectorial
import com.perseo.telecable.model.perseoresponse.RouterCentral
import com.perseo.telecable.model.perseoresponse.ServiceOrderItem
import com.perseo.telecable.model.perseoresponse.TerminalBox
import com.perseo.telecable.repository.DatabaseRepository
import com.perseo.telecable.repository.PerseoRepository
import com.perseo.telecable.repository.SharedRepository
import com.perseo.telecable.utils.toBase64String
import com.perseo.telecable.utils.toJsonString
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

    private val _antennaSectorial: MutableLiveData<List<AntennaSectorial>> = MutableLiveData()
    val antennaSectorial: LiveData<List<AntennaSectorial>> = _antennaSectorial

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
                val rCA = response.body()
                if (rCA?.responseCode == 200) {
                    _terminalBox.value = rCA.responseBody.terminalBox
                    _routers.value = rCA.responseBody.routers
                    _antennaSectorial.value = rCA.responseBody.antennasSectorial
                }
            }
        }
    }

    fun getMotivos(motivoId: String, enterpriseId: Int) {
        viewModelScope.launch {
            try {
                val response =
                    repository.motivoOrders(motivoId = motivoId, enterpriseId = enterpriseId)
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

            } catch (ex: Exception) {
                ex.printStackTrace()
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
                _equipmentTmp.value?.find { it.equipment == equipment }?.image = image?.toBase64String()
            } else {
                _equipmentTmp.value?.find { it.equipment == equipment }?.idEquipment = idEquipment
            }
        }
    }

    private fun saveEquipmentInDatabase() {
        viewModelScope.launch {
            dbRepository.deleteEquipment()
            try {
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
            } catch (ex: Exception) {
                ex.printStackTrace()
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

    fun validateEquipment(
        validateAction: (String) -> Unit
    ) {
        viewModelScope.launch {
            val equipment = ValidateEquipmentRequest(noContract = currentOs.value?.noContract ?: 0)
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
                    "AGREGAR_MINI_NODO" -> equipment.addMiniNodo = 1
                    "QUITAR_MINI_NODO" -> equipment.removeMiniNodo = 1
                    "AGREGAR_ANTENA_SECTORIAL" -> equipment.addAntennaSectorial = 1
                    "QUITAR_ANTENA_SECTORIAL" -> equipment.removeAntennaSectorial = 1
                }
            }
            try {
                equipmentTmp.value?.map {
                    when (it.equipment) {
                        "CM" -> equipment.cmId = listOf(it.idEquipment!!)
                        "DECO" -> equipment.decoId = listOf(it.idEquipment!!)
                        "ETIQ" -> equipment.etiqId = listOf(it.idEquipment!!)
                        "CAJADIG" -> equipment.cdId = listOf(it.idEquipment!!)
                        "CAJATER" -> equipment.ctId = listOf(it.idEquipment!!)
                        "ROUTERCEN" -> equipment.rcId = listOf(it.idEquipment!!)
                        "LINEA" -> equipment.lineId = listOf(it.idEquipment!!)
                        "ROUTER" -> equipment.routerId = listOf(it.idEquipment!!)
                        "IP" -> equipment.ipId = listOf(it.idEquipment!!)
                        "ANTE" -> equipment.antennaId = listOf(it.idEquipment!!)
                        "ANTESEC" -> equipment.antennaSectorialId = listOf(it.idEquipment!!)
                        "MINI" -> equipment.miniNodoId = listOf(it.idEquipment!!)
                    }
                }
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
            val response = repository.validateEquipment(
                generalData.value[0].idMunicipality,
                equipment.toJsonString()
            )
            if (response.isSuccessful) {
                val validate = response.body()
                if (validate?.responseBody?.code == "1") {
                    saveEquipmentInDatabase()
                    validateAction("Equipos registrados correctamente!")
                } else {
                    validateAction(validate?.responseBody?.message!!)
                }
            } else {
                validateAction("Error del servidor")
            }
        }
    }

    fun getIdEquipment(id: String, equipmentType: String): String {
        return when (equipmentType) {
            "ROUTER CENTRAL" -> {
                val routerId = routers.value?.filter { it.routerCentralDesc == id }
                routerId?.get(0)?.routerCentralId.toString()
            }
            "CAJA TERMINAL" -> {
                val boxId = terminalBox.value?.filter { it.terminalBoxDesc == id }
                boxId?.get(0)?.terminalBoxId.toString()
            }
            "ANTENA SECTORIAL" -> {
                val antennaId = antennaSectorial.value?.filter { it.antennaSectorialDesc == id }
                antennaId?.get(0)?.antennaSectorialId.toString()
            }
            else -> id
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
            "ANTENA SECTORIAL" -> "ANTESEC"
            "MINI NODO" -> "MINI"
            else -> ""
        }
    }
}
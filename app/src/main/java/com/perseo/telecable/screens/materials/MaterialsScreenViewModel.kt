package com.perseo.telecable.screens.materials

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.perseo.telecable.model.MaterialTmp
import com.perseo.telecable.model.database.GeneralData
import com.perseo.telecable.model.database.Materials
import com.perseo.telecable.model.perseoresponse.Inventory
import com.perseo.telecable.model.perseoresponse.Material
import com.perseo.telecable.repository.DatabaseRepository
import com.perseo.telecable.repository.PerseoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MaterialsScreenViewModel @Inject constructor(
    private val repository: PerseoRepository,
    private val dbRepository: DatabaseRepository
) :
    ViewModel() {
    private val _materialTmp: MutableLiveData<MutableList<MaterialTmp>> =
        MutableLiveData(mutableListOf())
    val materialTmp: LiveData<MutableList<MaterialTmp>> = _materialTmp

    private val _inventory: MutableLiveData<List<Inventory>> = MutableLiveData()
    val inventory: LiveData<List<Inventory>> = _inventory

    private val _materialSaved = MutableStateFlow<List<Materials>>(emptyList())
    val materialSaved = _materialSaved.asStateFlow()

    private val _data: MutableLiveData<GeneralData> = MutableLiveData()
    val data: LiveData<GeneralData> = _data

    private val _material: MutableLiveData<List<Material>> = MutableLiveData()
    val material: LiveData<List<Material>> = _material

    init {
        viewModelScope.launch(Dispatchers.IO) {
            dbRepository.getGeneralData().distinctUntilChanged()
                .collect { data ->
                    withContext(Dispatchers.Main) {
                        _data.value = data[0]
                    }
                }
        }
    }

    fun getInventory() {
        viewModelScope.launch {
            if (_data.value != null) {
                val inventoryResponse =
                    repository.getInventory(_data.value?.idMunicipality!!, _data.value?.idUser!!)
                if (inventoryResponse.isSuccessful) {
                    if (inventoryResponse.body()?.responseCode == 200) {
                        withContext(Dispatchers.Main) {
                            _inventory.value =
                                inventoryResponse.body()?.responseBody?.inventory
                        }
                    }
                }
            }
        }
    }

    fun getMaterial() {
        viewModelScope.launch {
            if (_data.value != null) {
                val materialResponse =
                    repository.getAllMaterials(_data.value?.idMunicipality!!)
                if (materialResponse.isSuccessful) {
                    if (materialResponse.body()?.responseCode == 200) {
                        withContext(Dispatchers.Main) {
                            _material.value =
                                materialResponse.body()?.responseBody
                        }
                    }
                }
            }
        }
    }

    fun getMaterialsSaved() {
        viewModelScope.launch {
            dbRepository.getAllMaterials().distinctUntilChanged()
                .collect { materials ->
                    _materialSaved.value = materials
                }
        }
    }

    fun saveTmp(materialId: String, materialDesc: String, amount: Double) {
        val current = _materialTmp.value?.find { it.materialId == materialId }
        if (current == null) {
            _materialTmp.value?.add(
                MaterialTmp(
                    materialId = materialId,
                    materialDesc = materialDesc,
                    amount = amount
                )
            )
        } else {
            _materialTmp.value?.find { it.materialId == materialId }?.amount = amount
        }
    }

    fun saveMaterialsInDatabase() {
        viewModelScope.launch {
            dbRepository.deleteMaterials()
            for (material in _materialTmp.value!!) {
                dbRepository.insertMaterial(
                    Materials(
                        id_material = material.materialId,
                        desc_material = material.materialDesc,
                        cantidad = material.amount,
                    )
                )
            }
        }
    }
}
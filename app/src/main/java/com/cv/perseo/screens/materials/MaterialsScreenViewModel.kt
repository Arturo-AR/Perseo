package com.cv.perseo.screens.materials

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cv.perseo.model.database.GeneralData
import com.cv.perseo.model.database.Materials
import com.cv.perseo.model.perseoresponse.Inventory
import com.cv.perseo.repository.DatabaseRepository
import com.cv.perseo.repository.PerseoRepository
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
class MaterialsScreenViewModel @Inject constructor(
    private val repository: PerseoRepository,
    private val dbRepository: DatabaseRepository
) :
    ViewModel() {
    private val _inventory: MutableLiveData<List<Inventory>> = MutableLiveData()
    val inventory: LiveData<List<Inventory>> = _inventory

    private val _material = MutableStateFlow<List<Materials>>(emptyList())
    val material = _material.asStateFlow()

    private val _data: MutableLiveData<GeneralData> = MutableLiveData()
    val data: LiveData<GeneralData> = _data

    init {
        viewModelScope.launch(Dispatchers.IO) {
            dbRepository.getGeneralData().distinctUntilChanged()
                .collect { data ->
                    if (data.isNotEmpty()) {
                        val inventoryResponse =
                            repository.getInventory(data[0].idMunicipality, data[0].idUser)
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
    }

    fun getMaterials() {
        viewModelScope.launch {
            dbRepository.getAllMaterials().distinctUntilChanged()
                .collect { materials ->
                    _material.value = materials
                }
        }
    }

    fun addMaterial(material: Inventory, amount: Double) {
        viewModelScope.launch {
            if (_material.value.find { it.id_material == material.materialId }?.id_material != material.materialId) {
                dbRepository.insertMaterial(
                    Materials(
                        id_material = material.materialId,
                        desc_material = material.materialDesc,
                        cantidad = amount
                    )
                )

            }
        }
    }

    fun deleteMaterialById(id: UUID) {
        viewModelScope.launch {
            dbRepository.deleteMaterialById(id)
        }
    }

    fun getGeneralData() {
        viewModelScope.launch {
            dbRepository.getGeneralData().distinctUntilChanged()
                .collect { data ->
                    _data.value = data[0]
                }
        }
    }
}
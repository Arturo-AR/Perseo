package com.cv.perseo.screens.inventory

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cv.perseo.model.perseoresponse.Inventory
import com.cv.perseo.repository.DatabaseRepository
import com.cv.perseo.repository.PerseoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class InventoryScreenViewModel @Inject constructor(
    private val repository: PerseoRepository,
    private val dbRepository: DatabaseRepository
) :
    ViewModel() {
    private val _inventory: MutableLiveData<List<Inventory>> = MutableLiveData()
    val inventory: LiveData<List<Inventory>> = _inventory

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
}
package com.cv.perseo.screens.servicecords

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cv.perseo.model.database.GeneralData
import com.cv.perseo.model.perseoresponse.CordsOrderBody
import com.cv.perseo.repository.DatabaseRepository
import com.cv.perseo.repository.PerseoRepository
import com.cv.perseo.utils.toDate
import com.cv.perseo.utils.toJsonString
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject

@HiltViewModel
class ServiceCordsScreenViewModel @Inject constructor(
    private val repository: PerseoRepository,
    private val dbRepository: DatabaseRepository
) :
    ViewModel() {

    private val _data: MutableLiveData<GeneralData> = MutableLiveData()
    val data: LiveData<GeneralData> = _data

    private val _cordsOrders: MutableLiveData<List<CordsOrderBody>> = MutableLiveData()
    val cordsOrder: LiveData<List<CordsOrderBody>> = _cordsOrders

    private val _osList: MutableLiveData<List<Int>> = MutableLiveData(mutableListOf())
    val osList: MutableLiveData<List<Int>> = _osList

    private val _filterOptions: MutableLiveData<List<String>> = MutableLiveData(mutableListOf())
    val filterOptions: MutableLiveData<List<String>> = _filterOptions

    init {
        viewModelScope.launch(Dispatchers.IO) {
            dbRepository.getGeneralData().distinctUntilChanged()
                .collect { data ->
                    if (data.isNotEmpty()) {
                        val cords =
                            repository.getCordsOrders(data[0].idUser, data[0].idMunicipality)
                        withContext(Dispatchers.Main) {
                            _cordsOrders.value = cords.body()?.responseBody
                        }
                    }
                }
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

    fun addOs(os: Int?) {
        if (os != null) {
            _osList.value = _osList.value?.plus(os) ?: emptyList()
        }
    }

    fun cleanOsList() {
        _osList.value = emptyList()
    }

    fun deleteOs(os: Int?) {
        if (os != null) {
            _osList.value = _osList.value?.minus(os)
        }
    }

    fun completeOrders(onSuccess: () -> Unit) {
        viewModelScope.launch {
            val response = repository.oSBlockCompliance(
                enterpriseId = _data.value?.idMunicipality!!,
                date = Date().toDate(),
                osIdsArray = _osList.value?.toJsonString()!!
            )
            if (response.isSuccessful) {
                if (response.body() == "Committed") {
                    onSuccess()
                } else {
                    Log.d("error", "Algo salio mal")
                }
            }
        }
    }

    fun updateOptions(filter: String) {
        if (!_cordsOrders.value.isNullOrEmpty()) {
            _filterOptions.value = if (filter == "Colonia") {
                _cordsOrders.value!!.map {
                    it.settlement
                }.distinct()
            } else {
                _cordsOrders.value!!.map {
                    it.sector
                }.distinct()
            }
        }
    }
}
package com.cv.perseo.screens.servicecords

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cv.perseo.model.perseoresponse.CordsOrderBody
import com.cv.perseo.repository.DatabaseRepository
import com.cv.perseo.repository.PerseoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ServiceCordsScreenViewModel @Inject constructor(
    private val repository: PerseoRepository,
    private val dbRepository: DatabaseRepository
) :
    ViewModel() {

    private val _cordsOrders: MutableLiveData<List<CordsOrderBody>> = MutableLiveData()
    val cordsOrder: LiveData<List<CordsOrderBody>> = _cordsOrders

    init {
        viewModelScope.launch(Dispatchers.IO) {
            dbRepository.getGeneralData().distinctUntilChanged()
                .collect { data ->
                    if (data.isNotEmpty()) {
                        val cords =
                            repository.getCordsOrders(data[0].idUser, data[0].idMunicipality)
                        Log.d("Cortes", cords.body()?.responseBody.toString())
                        withContext(Dispatchers.Main){
                            _cordsOrders.value = cords.body()?.responseBody
                        }
                    }
                }
        }
    }
}
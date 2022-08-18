package com.perseo.telecable.screens.enterpriseselector

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.perseo.telecable.model.database.GeneralData
import com.perseo.telecable.model.perseoresponse.EnterpriseBody
import com.perseo.telecable.repository.DatabaseRepository
import com.perseo.telecable.repository.PerseoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EnterpriseSelectorScreenViewModel @Inject constructor(
    private val repository: PerseoRepository,
    private val dbRepository: DatabaseRepository
) :
    ViewModel() {

    private val actualData: MutableLiveData<GeneralData> = MutableLiveData()

    private val _options: MutableLiveData<List<EnterpriseBody>> = MutableLiveData()
    val options: LiveData<List<EnterpriseBody>> = _options

    init {
        viewModelScope.launch {
            dbRepository.getGeneralData().distinctUntilChanged()
                .collect { data ->
                    if (data.isNotEmpty()) {
                        actualData.value = data[0]
                        val response =
                            repository.login(userId = data[0].idUser, password = data[0].password)
                        if (response.body()?.responseCode == 200) {
                            _options.value = response.body()!!.responseBody.enterprises
                        }
                    }
                }
        }
    }

    fun updateData(
        enterprise: EnterpriseBody
    ) {
        viewModelScope.launch {
            actualData.value?.municipality = enterprise.municipality
            actualData.value?.logo = enterprise.logo
            actualData.value?.tradeName = enterprise.tradeName
            actualData.value?.logoIcon = enterprise.logoIcon
            actualData.value?.idMunicipality = enterprise.idMunicipality
            dbRepository.updateGeneralData(actualData.value!!)
        }
    }
}
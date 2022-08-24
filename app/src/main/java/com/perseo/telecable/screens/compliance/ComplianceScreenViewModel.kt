package com.perseo.telecable.screens.compliance

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.perseo.telecable.model.database.GeneralData
import com.perseo.telecable.repository.DatabaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ComplianceScreenViewModel  @Inject constructor(
    private val dbRepository: DatabaseRepository
) :
    ViewModel() {

    private val _data : MutableLiveData<GeneralData> = MutableLiveData()
    val data : LiveData<GeneralData> = _data

    init {
        viewModelScope.launch {
            dbRepository.getGeneralData().distinctUntilChanged()
                .collect { data ->
                    _data.value = data[0]
                }
        }
    }
}
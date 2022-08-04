package com.cv.perseo.screens.subscribers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cv.perseo.model.database.GeneralData
import com.cv.perseo.repository.DatabaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SubscribersScreenViewModel  @Inject constructor(
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
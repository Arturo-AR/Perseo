package com.perseo.telecable.screens.splash

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.perseo.telecable.model.database.GeneralData
import com.perseo.telecable.repository.DatabaseRepository
import com.perseo.telecable.repository.PerseoRepository
import com.perseo.telecable.repository.SharedRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel @Inject constructor(
    private val dbRepository: DatabaseRepository,
    private val perseoRepository: PerseoRepository,
    private val prefs: SharedRepository
) :
    ViewModel() {
    private val _generalData = MutableStateFlow<List<GeneralData>>(emptyList())
    val generalData = _generalData.asStateFlow()

    private val _doing: MutableLiveData<Boolean> = MutableLiveData()
    val doing: LiveData<Boolean> = _doing

    private val _onWay: MutableLiveData<Boolean> = MutableLiveData()
    val onWay: LiveData<Boolean> = _onWay

    private val _versionOk: MutableLiveData<Boolean> = MutableLiveData(false)
    val versionOk: LiveData<Boolean> = _versionOk

    init {
        _doing.value = prefs.getDoing()
        _onWay.value = prefs.getOnWay()

        viewModelScope.launch(Dispatchers.IO) {
            dbRepository.getGeneralData().distinctUntilChanged()
                .collect { data ->
                    if (data.isEmpty()) {
                        Log.d("Splashscreen", "No General Data")
                    } else {
                        _generalData.value = data
                    }
                }
        }
    }

    fun verifyVersion(version:String) {
        viewModelScope.launch {
            val response = perseoRepository.verifyVersion(version)
            if (response.isSuccessful){
                if (response.body()?.responseCode == 200){
                    _versionOk.value = true
                }
            }
        }
    }
}
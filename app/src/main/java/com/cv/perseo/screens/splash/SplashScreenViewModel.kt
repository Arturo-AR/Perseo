package com.cv.perseo.screens.splash

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cv.perseo.model.database.GeneralData
import com.cv.perseo.repository.DatabaseRepository
import com.cv.perseo.repository.SharedRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel @Inject constructor(
    private val repository: DatabaseRepository,
    private val prefs: SharedRepository
) :
    ViewModel() {
    private val _generalData = MutableStateFlow<List<GeneralData>>(emptyList())
    val generalData = _generalData.asStateFlow()

    private val _doing: MutableLiveData<Boolean> = MutableLiveData()
    val doing: LiveData<Boolean> = _doing

    private val _onWay: MutableLiveData<Boolean> = MutableLiveData()
    val onWay: LiveData<Boolean> = _onWay

    init {

        _doing.value = prefs.getDoing()
        _onWay.value = prefs.getOnWay()

        viewModelScope.launch(Dispatchers.IO) {
            repository.getGeneralData().distinctUntilChanged()
                .collect { data ->
                    if (data.isEmpty()) {
                        Log.d("Splashscreen", "No General Data")
                    } else {
                        _generalData.value = data
                    }
                }
        }
    }
}
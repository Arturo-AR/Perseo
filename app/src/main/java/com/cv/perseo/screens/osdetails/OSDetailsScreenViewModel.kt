package com.cv.perseo.screens.osdetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cv.perseo.repository.SharedRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OSDetailsScreenViewModel @Inject constructor(
    private val prefs: SharedRepository
) :
    ViewModel() {
    private val _currentOs: MutableLiveData<Int> = MutableLiveData()
    val currentOs: LiveData<Int> = _currentOs

    init {
        _currentOs.value = prefs.getId()
    }
}
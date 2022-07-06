package com.cv.perseo.screens.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cv.perseo.repository.DatabaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardScreenViewModel @Inject constructor(
    private val dbRepository: DatabaseRepository
) :
    ViewModel() {

    fun signOut() {
        viewModelScope.launch {
            dbRepository.deleteGeneralData()
        }
    }
}
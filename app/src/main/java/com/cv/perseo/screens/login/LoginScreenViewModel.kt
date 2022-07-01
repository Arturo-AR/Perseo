package com.cv.perseo.screens.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cv.perseo.repository.PerseoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginScreenViewModel @Inject constructor(private val repository: PerseoRepository) :
    ViewModel() {

    fun login(userId: String, password: String, success: () -> Unit, fail: () -> Unit) =
        viewModelScope.launch {
            try {
                if (repository.login(userId = userId, password = password)
                        .body()?.responseCode == 200
                )
                    success()
                else {
                    fail()
                }
            } catch (ex: Exception) {
                Log.d("Login", "Error at login: ${ex.message}")
            }
        }
}
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

    fun login(userId: String, password: String, home: () -> Unit) =
        viewModelScope.launch {
            try {
                if (repository.logIn(username = userId, password = password)
                        .responseCode == 200
                )
                    home()
                else {
                    Log.d("Error", "Credenciales no validas")
                }
            } catch (ex: Exception) {
                Log.d("Login", "Error at login: ${ex.message}")
            }
        }
}
package com.cv.perseo.repository

import com.cv.perseo.model.LoginResponse
import com.cv.perseo.network.PerseoApi
import retrofit2.Response
import javax.inject.Inject

class PerseoRepository @Inject constructor(private val api: PerseoApi) {
    suspend fun logIn(username: String, password: String): Response<LoginResponse> {
        return api.login(0, username, password)
    }
}
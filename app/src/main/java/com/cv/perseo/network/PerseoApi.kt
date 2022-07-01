package com.cv.perseo.network

import com.cv.perseo.model.perseoresponse.LoginResponse
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import retrofit2.http.Query

interface PerseoApi {

    @FormUrlEncoded
    @POST("ws.php")
    suspend fun login(
        @Query("opc") opc: Int,
        @Field("USUARIO") username: String,
        @Field("CONTRASEÑA") password: String,
    ): Response<LoginResponse>
}
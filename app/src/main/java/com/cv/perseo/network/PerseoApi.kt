package com.cv.perseo.network

import com.cv.perseo.model.perseoresponse.*
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

    @FormUrlEncoded
    @POST("ws.php")
    suspend fun serviceOrders(
        @Query("opc") opc: Int,
        @Field("ID_USUARIO") userId: String,
        @Field("ID_EMPRESA") enterpriseId: Int,
        @Field("ID_OS") osId: Int
    ): Response<ServiceOrdersResponse>

    @FormUrlEncoded
    @POST("ws.php")
    suspend fun cordsOrders(
        @Query("opc") opc: Int,
        @Field("ID_USUARIO") userId: String,
        @Field("ID_EMPRESA") enterpriseId: Int
    ): Response<CordsOrdersResponse>

    @FormUrlEncoded
    @POST("ws.php")
    suspend fun inventory(
        @Query("opc") opc: Int,
        @Field("ID_EMPRESA") enterpriseId: Int,
        @Field("ID_USUARIO") userId: String
    ): Response<InventoryResponse>

    @FormUrlEncoded
    @POST("ws.php")
    suspend fun motivoOrders(
        @Query("opc") opc: Int,
        @Field("ID_MOTIVO_ORDEN") motivoId: String,
        @Field("ID_EMPRESA") enterpriseId: Int
    ): Response<MotivosResponse>

    @FormUrlEncoded
    @POST("ws.php")
    suspend fun validateEquipment(
        @Query("opc") opc: Int,
        @Field("ID_EMPRESA") enterpriseId: Int,
        @Field("PARAMETROS") parameters: String
    ): Response<ValidateEquipmentResponse>

    @FormUrlEncoded
    @POST("ws.php")
    suspend fun getRoutersCT(
        @Query("opc") opc: Int,
        @Field("ID_EMPRESA") enterpriseId: Int
    ): Response<RoutersCTResponse>
}
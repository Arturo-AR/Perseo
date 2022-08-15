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

    @FormUrlEncoded
    @POST("ws.php")
    suspend fun finalizarOrdenServicio(
        @Query("opc") opc: Int,
        @Field("ID_EMPRESA") empresa_id: Int,
        @Field("ORDENES_INFO_PRE_CUMPLIMIENTO") ordenes_info_cumplimiento: String,
        @Field("IMAGENES") fotos: String,
        @Field("EQUIPOS") equipos: String,
        @Field("ID_OS") orden: Int,
        @Field("FECHA") fecha: String,
        @Field("MATERIALES") materiales: String,
        @Field("PARAMETROS_ROUTER") parametros: String,
        @Field("INFO_CUMPLIMIENTO") info_cumplimiento: String,
    ): Response<String>

    @FormUrlEncoded
    @POST("ws.php")
    suspend fun cancelOrder(
        @Query("opc") opc: Int,
        @Field("ID_EMPRESA") enterpriseId: Int,
        @Field("MOTIVO_CANCELACION") cancelReason: String
    ): Response<String>

    @FormUrlEncoded
    @POST("ws.php")
    suspend fun getSubscriberImages(
        @Query("opc") opc: Int,
        @Field("ID_EMPRESA") enterpriseId: Int,
        @Field("NRO_SOLICITUD") requestNumber: Int
    ): Response<SubscriberImagesResponse>

    @FormUrlEncoded
    @POST("ws.php")
    suspend fun oSBlockCompliance(
        @Query("opc") opc: Int,
        @Field("ID_EMPRESA") enterpriseId: Int,
        @Field("FECHA") date: String,
        @Field("ID_OS_ARRAY") osIdsArray: String,
    ): Response<String>
}
package com.cv.perseo.model

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("RESPONSE_CODE") val responseCode: Int,
    @SerializedName("RESPONSE_MESSAGE") val responseMessage: String,
    @SerializedName("RESPONSE_BODY") val responseBody: List<CiudadesResponse>,
)

data class CiudadesResponse(
    @SerializedName("NOMBRE_COMERCIAL") val empresa: String,
    @SerializedName("CIUDAD") val ciudad: String,
    @SerializedName("LOGOTIPO") val logo: String,
    @SerializedName("LOGOTIPO_ICO") val logoIcono: String,
    @SerializedName("ID_USUARIO") val idUsuario: String,
    @SerializedName("ID_EMPRESA") val idEmpresa: Int,
)

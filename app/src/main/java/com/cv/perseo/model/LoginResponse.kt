package com.cv.perseo.model

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("RESPONSE_CODE") val responseCode: Int,
    @SerializedName("RESPONSE_MESSAGE") val responseMessage: String,
    @SerializedName("RESPONSE_BODY") val responseBody: LoginBody,
)

data class LoginBody(
    @SerializedName("PERMISOS") val permissions: List<PermissionsBody>,
    @SerializedName("CIUDADES") val enterprises: List<EnterpriseBody>
)

data class PermissionsBody(
    @SerializedName("ID_ACTIVITY") val idActivityFather: Int,
    @SerializedName("ID_ACTIVITY_RELACIONADA")val idActivitySon: Int,
    @SerializedName("ICONO")val icon: String
)

data class EnterpriseBody(
    @SerializedName("NOMBRE_COMERCIAL") val empresa: String,
    @SerializedName("CIUDAD") val ciudad: String,
    @SerializedName("LOGOTIPO") val logo: String,
    @SerializedName("LOGOTIPO_ICO") val logoIcono: String,
    @SerializedName("ID_EMPRESA") val idEmpresa: Int,
)

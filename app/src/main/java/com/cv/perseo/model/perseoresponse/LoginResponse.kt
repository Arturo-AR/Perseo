package com.cv.perseo.model.perseoresponse

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("RESPONSE_CODE") val responseCode: Int,
    @SerializedName("RESPONSE_MESSAGE") val responseMessage: String,
    @SerializedName("RESPONSE_BODY") val responseBody: LoginBody,
)

data class LoginBody(
    @SerializedName("EMPRESAS") val enterprises: List<EnterpriseBody>,
    @SerializedName("PERMISOS") val permissions: List<PermissionsBody>
)

data class PermissionsBody(
    @SerializedName("ID_ACTIVITY") val idActivityFather: Int,
    @SerializedName("ID_ACTIVITY_RELACIONADA") val idActivitySon: Int,
    @SerializedName("ICONO") val icon: String
)

data class EnterpriseBody(
    @SerializedName("CIUDAD") val municipality: String,
    @SerializedName("LOGOTIPO") val logo: String,
    @SerializedName("NOMBRE_COMERCIAL") val tradeName: String,
    @SerializedName("LOGOTIPO_ICO") val logoIcon: String,
    @SerializedName("ID_EMPRESA") val idMunicipality: Int,
)

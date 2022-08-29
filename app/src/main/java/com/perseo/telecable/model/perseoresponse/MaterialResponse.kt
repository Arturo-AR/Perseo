package com.perseo.telecable.model.perseoresponse

import com.google.gson.annotations.SerializedName

data class MaterialResponse(
    @SerializedName("RESPONSE_CODE") val responseCode: Int,
    @SerializedName("RESPONSE_MESSAGE") val responseMessage: String,
    @SerializedName("RESPONSE_BODY") val responseBody: List<Material>,
)

data class Material(
    @SerializedName("ID_MATERIAL") val materialId: String,
    @SerializedName("DESC_MATERIAL") val materialDesc: String,
)
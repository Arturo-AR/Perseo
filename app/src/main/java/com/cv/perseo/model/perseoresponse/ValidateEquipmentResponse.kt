package com.cv.perseo.model.perseoresponse

import com.google.gson.annotations.SerializedName

data class ValidateEquipmentResponse(
    @SerializedName("RESPONSE_CODE") val responseCode: Int,
    @SerializedName("RESPONSE_MESSAGE") val responseMessage: String,
    @SerializedName("RESPONSE_BODY") val responseBody: ValidateEquipment,
)

data class ValidateEquipment(
    @SerializedName("CODE") val code: String,
    @SerializedName("MESSAGE") val message: String,
)

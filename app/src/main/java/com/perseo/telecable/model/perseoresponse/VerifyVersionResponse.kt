package com.perseo.telecable.model.perseoresponse

import com.google.gson.annotations.SerializedName

data class VerifyVersionResponse(
    @SerializedName("RESPONSE_CODE") val responseCode: Int,
    @SerializedName("RESPONSE_MESSAGE") val responseMessage: String,
    @SerializedName("RESPONSE_BODY") val responseBody: String,
)

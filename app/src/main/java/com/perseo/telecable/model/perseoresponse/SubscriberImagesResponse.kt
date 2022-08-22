package com.perseo.telecable.model.perseoresponse

import com.google.gson.annotations.SerializedName

data class SubscriberImagesResponse(
    @SerializedName("RESPONSE_CODE") val responseCode: Int,
    @SerializedName("RESPONSE_MESSAGE") val responseMessage: String,
    @SerializedName("RESPONSE_BODY") val responseBody: List<SubscriberImage?>,
)

data class SubscriberImage(
    @SerializedName("URL_IMAGEN") val urlImage: String,
    @SerializedName("DESCRIPCION_IMAGEN") val descriptionImage: String
)

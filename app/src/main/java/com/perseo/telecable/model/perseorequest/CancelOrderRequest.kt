package com.perseo.telecable.model.perseorequest

import com.google.gson.annotations.SerializedName

data class CancelOrderRequest(
    @SerializedName("id_os") val osId: Int,
    @SerializedName("motivo") val reason: String,
    @SerializedName("url_image_1") val imageUrl1: String,
    @SerializedName("url_image_2") val imageUrl2: String,
    @SerializedName("url_image_3") val imageUrl3: String,
    @SerializedName("ubicacion") val location: String
)

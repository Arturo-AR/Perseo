package com.perseo.telecable.model.perseorequest

import com.google.gson.annotations.SerializedName

data class ImageRequest(
    @SerializedName("description") val description: String,
    @SerializedName("id_equipo") val equipmentId: String,
    @SerializedName("id_os") val osId: Int,
    @SerializedName("id_tipo_equipo") val equipmentTypeId: String,
    @SerializedName("nro_solicitud") val requestNumber: Int,
    @SerializedName("url_image") val link: String,
)

package com.perseo.telecable.model.perseorequest

import com.google.gson.annotations.SerializedName

data class EquipmentRequest(
    @SerializedName("id_os") val osId: Int,
    @SerializedName("id_tipo_equipo") val equipmentTypeId: String,
    @SerializedName("id_equipo") val equipmentId: String,
)

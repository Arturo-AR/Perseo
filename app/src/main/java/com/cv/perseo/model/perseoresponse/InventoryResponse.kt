package com.cv.perseo.model.perseoresponse

import com.google.gson.annotations.SerializedName

data class InventoryResponse(
    @SerializedName("RESPONSE_CODE") val responseCode: Int,
    @SerializedName("RESPONSE_MESSAGE") val responseMessage: String,
    @SerializedName("RESPONSE_BODY") val responseBody: InventoryBody?,
)

data class InventoryBody(
    @SerializedName("USUARIO") val user: String,
    @SerializedName("NOMBRE_USUARIO") val userName: String,
    @SerializedName("MATERIAL") val inventory: List<Inventory>
)

data class Inventory(
    @SerializedName("ID_MATERIAL") val materialId: String,
    @SerializedName("DESC_MATERIAL") val materialDesc: String,
    @SerializedName("CANTIDAD") val amount: Int
)

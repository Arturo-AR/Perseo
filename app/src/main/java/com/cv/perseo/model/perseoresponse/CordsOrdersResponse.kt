package com.cv.perseo.model.perseoresponse

import com.google.gson.annotations.SerializedName

data class CordsOrdersResponse(
    @SerializedName("RESPONSE_CODE") val responseCode: Int,
    @SerializedName("RESPONSE_MESSAGE") val responseMessage: String,
    @SerializedName("RESPONSE_BODY") val responseBody: List<CordsOrder>?,
)

data class CordsOrder(
    @SerializedName("ID_OS") val osId: Int,
    @SerializedName("ASENTAMIENTO") val settlement: String,
    @SerializedName("VIALIDAD") val highway: String,
    @SerializedName("EXTERIOR") val outdoorNumber: String,
    @SerializedName("INTERIOR") val indoorNumber: String,
    @SerializedName("ID_TIPO_ORDEN") val orderType: String,
    @SerializedName("SECTOR") val sector: String,
    @SerializedName("CAJA_TERMINAL") val terminalBox: String,
    @SerializedName("ETIQUETA") val label: String
)

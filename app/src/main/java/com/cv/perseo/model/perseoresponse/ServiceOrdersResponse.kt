package com.cv.perseo.model.perseoresponse

import com.google.gson.annotations.SerializedName

data class ServiceOrdersResponse(
    @SerializedName("RESPONSE_CODE") val responseCode: Int,
    @SerializedName("RESPONSE_MESSAGE") val responseMessage: String,
    @SerializedName("RESPONSE_BODY") val responseBody: List<ServiceOrderItem>?
)

data class ServiceOrderItem(
    @SerializedName("id_os") val osId: Int,
    @SerializedName("zona") val zone: String,
    @SerializedName("icono_rubro") val rubroIcon: String,
    @SerializedName("rubro") val rubro: String,
    @SerializedName("motivo") val motivo: String,
    @SerializedName("sector") val sector: String,
    @SerializedName("vialidad") val street: String,
    @SerializedName("no_exterior") val outdoorNumber: String,
    @SerializedName("no_interior") val indoorNumber: String,
    @SerializedName("fecha_pre_cumplimiento") val preCumDate:String,
    @SerializedName("fecha_agenda") val scheduleDate: String?,
    @SerializedName("hora_de") val hourFrom: String?,
    @SerializedName("hora_hasta") val hourUntil: String?,
    @SerializedName("detalle_agenda") val scheduleDetail: String?
)

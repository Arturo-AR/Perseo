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
    @SerializedName("caja_terminal") val ct: String,
    @SerializedName("vialidad") val street: String,
    @SerializedName("no_exterior") val outdoorNumber: String,
    @SerializedName("no_interior") val indoorNumber: String,
    @SerializedName("fecha_pre_cumplimiento") val preCumDate: String,
    @SerializedName("fecha_agenda") val scheduleDate: String?,
    @SerializedName("hora_de") val hourFrom: String?,
    @SerializedName("hora_hasta") val hourUntil: String?,
    @SerializedName("detalle_agenda") val scheduleDetail: String?,
    @SerializedName("celular") val cellPhone: String,
    @SerializedName("telefono") val phone: String,
    @SerializedName("paquete") val packageName: String,
    @SerializedName("detalle_pedido1") val osDetail1: String,
    @SerializedName("detalle_pedido2") val osDetail2: String,
    @SerializedName("nombres") val name: String,
    @SerializedName("apellidos") val lastName: String,
    @SerializedName("tvs") val tvs:Int,
    @SerializedName("contrato") val noContract: Int,
    @SerializedName("observaciones") val observations:String,
    @SerializedName("estado") val status:String,
    @SerializedName("asentamiento") val colony:String,
    @SerializedName("equipos") val equipment: List<EquipmentResponse>
)

data class EquipmentResponse(
    @SerializedName("ID_EQUIPO") val equipmentId:String,
    @SerializedName("DESC_TIPO_EQUIPO") val equipmentDesc:String,
)

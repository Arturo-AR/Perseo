package com.cv.perseo.model.perseoresponse

import com.google.gson.annotations.SerializedName

data class RoutersCTResponse(
    @SerializedName("RESPONSE_CODE") val responseCode: Int,
    @SerializedName("RESPONSE_MESSAGE") val responseMessage: String,
    @SerializedName("RESPONSE_BODY") val responseBody: RoutersCajas,
)

data class RoutersCajas(
    @SerializedName("ROUTERS") val routers: List<RouterCentral>,
    @SerializedName("CAJAS") val terminalBox: List<TerminalBox>
)

data class RouterCentral(
    @SerializedName("ID_ROUTER_CENTRAL") val routerCentralId: Int,
    @SerializedName("DESCRIPCION_ROUTER_CENTRAL") val routerCentralDesc: String,
    @SerializedName("IP_ROUTER_CENTRAL") val routerCentralIp: String,
    @SerializedName("TIPO_CONEXION_ROUTER_CENTRAL") val routerCentralConnectionType: String,
)

data class TerminalBox(
    @SerializedName("ID_CAJA_TERMINAL") val terminalBoxId: Int,
    @SerializedName("DESC_CAJA_TERMINAL") val terminalBoxDesc: String
)
package com.cv.perseo.model.perseorequest

import com.google.gson.annotations.SerializedName

data class ValidateEquipmentRequest(
    @SerializedName("AGREGAR_CABLEMODEM") var addCM: Int = 0,
    @SerializedName("QUITAR_CABLEMODEM") var removeCM: Int = 0,
    @SerializedName("AGREGAR_DECO") var addDeco: Int = 0,
    @SerializedName("QUITAR_DECO") var removeDeco: Int = 0,
    @SerializedName("AGREGAR_ETIQUETA") var addEtiq: Int = 0,
    @SerializedName("QUITAR_ETIQUETA") var removeEtiq: Int = 0,
    @SerializedName("AGREGAR_CAJA_DIGITAL") var addCD: Int = 0,
    @SerializedName("QUITAR_CAJA_DIGITAL") var removeCD: Int = 0,
    @SerializedName("AGREGAR_CAJA_TERMINAL") var addCT: Int = 0,
    @SerializedName("QUITAR_CAJA_TERMINAL") var removeCT: Int = 0,
    @SerializedName("AGREGAR_ROUTER_CENTRAL") var addRC: Int = 0,
    @SerializedName("QUITAR_ROUTER_CENTRAL") var removeRC: Int = 0,
    @SerializedName("AGREGAR_LINEA") var addLine: Int = 0,
    @SerializedName("QUITAR_LINEA") var removeLine: Int = 0,
    @SerializedName("AGREGAR_ROUTER") var addRouter: Int = 0,
    @SerializedName("QUITAR_ROUTER") var removeRouter: Int = 0,
    @SerializedName("AGREGAR_IP_PUBLICA") var addIp: Int = 0,
    @SerializedName("QUITAR_IP_PUBLICA") var removeIp: Int = 0,
    @SerializedName("AGREGAR_ANTENA") var addAntenna: Int = 0,
    @SerializedName("QUITAR_ANTENA") var removeAntenna: Int = 0,
    @SerializedName("id_equipo_antena") var antennaId: List<String> = listOf(),
    @SerializedName("id_equipo_caja_dig") var cdId: List<String> = listOf(),
    @SerializedName("id_equipo_caja_terminal") var ctId: List<String> = listOf(),
    @SerializedName("id_equipo_cm") var cmId: List<String> = listOf(),
    @SerializedName("id_equipo_deco") var decoId: List<String> = listOf(),
    @SerializedName("id_equipo_etiq") var etiqId: List<String> = listOf(),
    @SerializedName("id_equipo_ip_publica") var ipId: List<String> = listOf(),
    @SerializedName("id_equipo_linea") var lineId: List<String> = listOf(),
    @SerializedName("id_equipo_router") var routerId: List<String> = listOf(),
    @SerializedName("id_equipo_router_central") var rcId: List<String> = listOf(),
    @SerializedName("no_contrato") var noContract: Int,

)

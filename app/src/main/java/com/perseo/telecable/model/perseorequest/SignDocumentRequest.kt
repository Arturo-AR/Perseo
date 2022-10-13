package com.perseo.telecable.model.perseorequest

import com.google.gson.annotations.SerializedName

data class SignDocumentRequest(
    @SerializedName("id_empresa") val enterpriseId: Int,
    @SerializedName("contrato") val contract: Int,
    @SerializedName("nro_solicitud") val requestNumber: Int,
    @SerializedName("elementos") val elements: List<SignElements>,
    @SerializedName("id_documento") val documentId: Int,
    @SerializedName("documento") val document: String,
    @SerializedName("archivo") val file: String,
    @SerializedName("titular") val owner: Boolean,
)

data class SignElements(
    @SerializedName("nombre_elemento") val elementName: String,
    @SerializedName("elemento") val element: String
)
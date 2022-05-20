package com.cv.perseo.model

data class GeneralData(
    val userId: String,
    val onWay: Boolean,
    val doing: Boolean,
    val municipality: String,
    val logo: String,
    val idmMunicipality: Int
)

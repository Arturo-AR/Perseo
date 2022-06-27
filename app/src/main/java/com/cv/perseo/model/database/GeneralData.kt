package com.cv.perseo.model.database

data class GeneralData(
    val idUser: String,
    val onWay: Boolean,
    val doing: Boolean,
    val municipality: String,
    val logo: String,
    val logoIcon: String,
    val idMunicipality: Int
)

package com.cv.perseo.model.database

data class ServiceOrder(
    val idOs: Int,
    val rubroDesc: String,
    val motivoDesc: String,
    val vialidad: String,
    val noExterior: String,
    val zona: String,
    val paquete: String,
    val idRubro: String,
    val fechaPreCumplimiento: String,
    val iconoRubro: String
)

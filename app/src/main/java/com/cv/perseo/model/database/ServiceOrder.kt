package com.cv.perseo.model.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "service_orders")
data class ServiceOrder(
    @PrimaryKey val id: UUID = UUID.randomUUID(),
    val idOs: Int,
    val rubroDesc: String,
    val motivoDesc: String,
    val vialidad: String,
    val noExterior: String,
    val zona: String,
    val paquete: String,
    val idRubro: String,
    val fechaPreCumplimiento: String,
    val iconoRubro: String,
    val fecha_agenda: String?,
    val hora_de: String?,
    val hora_hasta: String?,
    val detalle_agenda: String?
)

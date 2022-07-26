package com.cv.perseo.model.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "compliance_info")
data class ComplianceInfo(
    @PrimaryKey val id: UUID = UUID.randomUUID(),
    val id_empresa: Int,
    val id_os: Int,
    val fecha_inicio: String,
    val hora_inicio: String,
    val ubicacion_inicio: String,
    var fecha_fin: String?,
    var hora_fin: String?,
    var ubicacion_fin: String?,
    var respuesta1: String?,
    var respuesta2: String?,
    var respuesta3: String?
)

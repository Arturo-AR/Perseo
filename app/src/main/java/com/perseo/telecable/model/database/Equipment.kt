package com.perseo.telecable.model.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "equipment")
data class Equipment(
    @PrimaryKey val id: UUID = UUID.randomUUID(),
    val nombre_imagen_adicional: String,
    val id_tipo_equipo: String,
    val id_equipo: String,
    var url_image: String?
)

package com.cv.perseo.model.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "equipment")
data class Equipment(
    @PrimaryKey val id: UUID = UUID.randomUUID(),
    val id_tipo_equipo: String,
    val id_equipo: String,
    val url_image: String?
)

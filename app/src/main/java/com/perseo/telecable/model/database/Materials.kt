package com.perseo.telecable.model.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "materials")
data class Materials(
    @PrimaryKey val id: UUID = UUID.randomUUID(),
    var id_material: String?,
    var desc_material: String?,
    var cantidad: Double
)

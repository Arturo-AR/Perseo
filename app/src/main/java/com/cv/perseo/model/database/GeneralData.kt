package com.cv.perseo.model.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "general_data")
data class GeneralData(
    @PrimaryKey val id: UUID = UUID.randomUUID(),
    val idUser: String,
    var onWay: Boolean,
    var doing: Boolean,
    val municipality: String,
    val logo: String,
    val logoIcon: String,
    val idMunicipality: Int
)

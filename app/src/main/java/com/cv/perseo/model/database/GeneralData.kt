package com.cv.perseo.model.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "general_data")
data class GeneralData(
    @PrimaryKey val id: UUID = UUID.randomUUID(),
    @ColumnInfo(name = "id_user") val idUser: String,
    @ColumnInfo(name = "on_way") var onWay: Boolean,//
    @ColumnInfo(name = "doing") var doing: Boolean,//
    @ColumnInfo(name = "municipality") var municipality: String,
    @ColumnInfo(name = "logo") var logo: String,
    @ColumnInfo(name = "logo_icon") var logoIcon: String,
    @ColumnInfo(name = "id_municipality") var idMunicipality: Int
)

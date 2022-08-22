package com.perseo.telecable.model.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "permissions")
data class Permissions(
    @PrimaryKey val id: UUID = UUID.randomUUID(),
    @ColumnInfo(name = "id_activity_father") val idActivityFather: Int,
    @ColumnInfo(name = "id_activity_son")val idActivitySon: Int,
    @ColumnInfo(name = "icon")val icon: String
)
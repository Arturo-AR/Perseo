package com.cv.perseo.model.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "permissions")
data class Permissions(
    @PrimaryKey val id: UUID = UUID.randomUUID(),
    val idActivityFather: Int,
    val idActivitySon: Int,
    val icon: String
)
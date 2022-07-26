package com.cv.perseo.utils

import androidx.room.TypeConverter
import java.util.*

class UUIDConverter {

    @TypeConverter
    fun stringFromUUID(uuid: UUID): String {
        return uuid.toString()
    }

    @TypeConverter
    fun uuidFromString(string: String): UUID {
        return UUID.fromString(string)
    }

}
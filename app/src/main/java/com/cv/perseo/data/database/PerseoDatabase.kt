package com.cv.perseo.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.cv.perseo.model.database.*
import com.cv.perseo.utils.UUIDConverter

@Database(
    entities = [
        ComplianceInfo::class,
        Equipment::class,
        GeneralData::class,
        Materials::class,
        Permissions::class,
        ServiceOrder::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(UUIDConverter::class)
abstract class PerseoDatabase : RoomDatabase() {
    abstract fun perseoDao(): PerseoDatabaseDao
}
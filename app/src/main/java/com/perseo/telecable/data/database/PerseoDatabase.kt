package com.perseo.telecable.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.perseo.telecable.model.database.*
import com.perseo.telecable.utils.UUIDConverter

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
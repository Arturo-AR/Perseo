package com.cv.perseo.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.cv.perseo.model.database.GeneralData
import kotlinx.coroutines.flow.Flow

@Dao
interface PerseoDatabaseDao {

    @Query("SELECT * FROM general_data")
    fun getGeneralData(): Flow<GeneralData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGeneralData(data: GeneralData)

    @Query("DELETE FROM general_data")
    suspend fun deleteGeneralData()
}
package com.cv.perseo.data.database

import androidx.room.*
import com.cv.perseo.model.database.GeneralData
import com.cv.perseo.model.database.Permissions
import kotlinx.coroutines.flow.Flow

@Dao
interface PerseoDatabaseDao {

    /**
     * Queries for GeneralData
     */
    @Query("SELECT * FROM general_data")
    fun getGeneralData(): Flow<List<GeneralData>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGeneralData(data: GeneralData)

    @Query("DELETE FROM general_data")
    suspend fun deleteGeneralData()

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateGeneralData(data: GeneralData)

    /**
     * Queries for Permissions
     */

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPermissions(permissions: Permissions)

    @Query("DELETE FROM permissions")
    suspend fun deletePermissions()

    @Query("SELECT * FROM permissions")
    fun getPermissions(): Flow<List<Permissions>>
}
package com.cv.perseo.data.database

import androidx.room.*
import com.cv.perseo.model.Rubro
import com.cv.perseo.model.database.GeneralData
import com.cv.perseo.model.database.Permissions
import com.cv.perseo.model.database.ServiceOrder
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

    /**
     * Queries for Service Orders
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertServiceOrder(order: ServiceOrder)

    @Query("DELETE FROM service_orders")
    suspend fun deleteServiceOrders()

    @Query("SELECT * FROM service_orders")
    fun getAllServiceOrders(): Flow<List<ServiceOrder>>

    @Query("SELECT DISTINCT zone FROM service_orders ")
    fun getZones(): Flow<List<String>>

    @Query("SELECT DISTINCT rubro_icon, rubro  FROM service_orders WHERE zone = :zone")
    fun getRubro(zone: String): Flow<List<Rubro>>

    @Query("SELECT * FROM service_orders WHERE zone = :zone AND rubro = :rubro")
    fun getServiceOrders(zone: String, rubro: String): Flow<List<ServiceOrder>>

    @Query("SELECT * FROM service_orders WHERE schedule_date IS NOT NULL ORDER BY hour_from")
    fun getScheduleOrders(): Flow<List<ServiceOrder>>
}
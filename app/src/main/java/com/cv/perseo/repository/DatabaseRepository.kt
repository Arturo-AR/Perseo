package com.cv.perseo.repository

import com.cv.perseo.data.database.PerseoDatabaseDao
import com.cv.perseo.model.Rubro
import com.cv.perseo.model.database.GeneralData
import com.cv.perseo.model.database.Permissions
import com.cv.perseo.model.database.ServiceOrder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class DatabaseRepository @Inject constructor(private val perseoDatabaseDao: PerseoDatabaseDao) {

    /**
     * Functions for General Data
     */
    suspend fun insertGeneralData(data: GeneralData) = perseoDatabaseDao.insertGeneralData(data)
    suspend fun updateGeneralData(data: GeneralData) = perseoDatabaseDao.updateGeneralData(data)
    suspend fun deleteGeneralData() = perseoDatabaseDao.deleteGeneralData()
    fun getGeneralData(): Flow<List<GeneralData>> =
        perseoDatabaseDao.getGeneralData().flowOn(Dispatchers.IO).conflate()

    /**
     * Functions for Permissions
     */
    suspend fun insertPermissions(permissions: Permissions) =
        perseoDatabaseDao.insertPermissions(permissions)

    suspend fun deletePermissions() = perseoDatabaseDao.deletePermissions()
    fun getPermissions(): Flow<List<Permissions>> =
        perseoDatabaseDao.getPermissions().flowOn(Dispatchers.IO).conflate()

    /**
     * Functions for ServiceOrders
     */

    suspend fun insertServiceOrders(order: ServiceOrder) =
        perseoDatabaseDao.insertServiceOrder(order)

    fun getZones(): Flow<List<String>> =
        perseoDatabaseDao.getZones().flowOn(Dispatchers.IO).conflate()

    fun getRubro(zone:String): Flow<List<Rubro>> =
        perseoDatabaseDao.getRubro(zone).flowOn(Dispatchers.IO).conflate()

    suspend fun deleteServiceOrders() = perseoDatabaseDao.deleteServiceOrders()

    fun getAllServiceOrders(): Flow<List<ServiceOrder>> =
        perseoDatabaseDao.getAllServiceOrders().flowOn(Dispatchers.IO).conflate()

    fun getScheduleOrders(): Flow<List<ServiceOrder>> =
        perseoDatabaseDao.getScheduleOrders().flowOn(Dispatchers.IO).conflate()
}
package com.perseo.telecable.repository

import com.perseo.telecable.data.database.PerseoDatabaseDao
import com.perseo.telecable.model.Rubro
import com.perseo.telecable.model.database.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import java.util.*
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

    fun getRubro(zone: String): Flow<List<Rubro>> =
        perseoDatabaseDao.getRubro(zone).flowOn(Dispatchers.IO).conflate()

    fun getServiceOrders(zone: String, rubro: String): Flow<List<ServiceOrder>> =
        perseoDatabaseDao.getServiceOrders(zone, rubro).flowOn(Dispatchers.IO).conflate()

    suspend fun deleteServiceOrders() = perseoDatabaseDao.deleteServiceOrders()

    fun getAllServiceOrders(): Flow<List<ServiceOrder>> =
        perseoDatabaseDao.getAllServiceOrders().flowOn(Dispatchers.IO).conflate()

    fun getScheduleOrders(): Flow<List<ServiceOrder>> =
        perseoDatabaseDao.getScheduleOrders().flowOn(Dispatchers.IO).conflate()

    /**
     * functions for materials
     */

    suspend fun insertMaterial(material: Materials) =
        perseoDatabaseDao.insertMaterial(material)

    suspend fun deleteMaterials() = perseoDatabaseDao.deleteMaterials()

    suspend fun deleteMaterialById(id: UUID) = perseoDatabaseDao.deleteMaterialById(id)

    fun getAllMaterials(): Flow<List<Materials>> =
        perseoDatabaseDao.getAllMaterials().flowOn(Dispatchers.IO).conflate()

    /**
     * functions for equipment
     */

    suspend fun insertEquipment(equipment: Equipment) =
        perseoDatabaseDao.insertEquipment(equipment)

    suspend fun deleteEquipment() = perseoDatabaseDao.deleteEquipment()

    suspend fun deleteEquipmentByUnit(equipment: Equipment) =
        perseoDatabaseDao.deleteEquipmentUnit(equipment)

    fun getAllEquipment(): Flow<List<Equipment>> =
        perseoDatabaseDao.getAllEquipment().flowOn(Dispatchers.IO).conflate()

    /**
     * functions for ComplianceInfo
     */

    fun getCompliance(): Flow<ComplianceInfo> =
        perseoDatabaseDao.getAllCompliance().flowOn(Dispatchers.IO).conflate()

    suspend fun insertCompliance(complianceInfo: ComplianceInfo) =
        perseoDatabaseDao.insertComplianceInfo(complianceInfo)

    suspend fun updateComplianceInfo(complianceInfo: ComplianceInfo) = perseoDatabaseDao.updateComplianceInfo(complianceInfo)

    suspend fun deleteComplianceInfo() = perseoDatabaseDao.deleteComplianceInfo()

}
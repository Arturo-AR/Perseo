package com.cv.perseo.repository

import com.cv.perseo.data.database.PerseoDatabaseDao
import com.cv.perseo.model.database.GeneralData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class DatabaseRepository @Inject constructor(private val perseoDatabaseDao: PerseoDatabaseDao) {

    suspend fun addGeneralData(data: GeneralData) = perseoDatabaseDao.insertGeneralData(data)
    suspend fun updateGeneralData(data: GeneralData) = perseoDatabaseDao.updateGeneralData(data)
    suspend fun deleteGeneralData() = perseoDatabaseDao.deleteGeneralData()
    fun getGeneralData(): Flow<List<GeneralData>> =
        perseoDatabaseDao.getGeneralData().flowOn(Dispatchers.IO).conflate()

}
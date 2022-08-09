package com.cv.perseo.data.sharedpreferences

import android.content.Context
import com.cv.perseo.utils.Constants.SHARED_DB
import com.cv.perseo.utils.Constants.SHARED_DOING
import com.cv.perseo.utils.Constants.SHARED_ON_WAY
import com.cv.perseo.utils.Constants.SHARED_OS_ID
import com.cv.perseo.utils.Constants.SHARED_RUBRO
import com.cv.perseo.utils.Constants.SHARED_ZONE
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class MyPreferences @Inject constructor(@ApplicationContext context: Context) {

    private val storage = context.getSharedPreferences(SHARED_DB, 0)

    fun saveId(id: Int) {
        storage.edit().putInt(SHARED_OS_ID, id).apply()
    }

    fun getId(): Int {
        return storage.getInt(SHARED_OS_ID, -1)
    }

    fun saveZone(zone: String) {
        storage.edit().putString(SHARED_ZONE, zone).apply()
    }

    fun getZone(): String {
        return storage.getString(SHARED_ZONE, "")!!
    }

    fun saveRubro(rubro: String) {
        storage.edit().putString(SHARED_RUBRO, rubro).apply()
    }

    fun getRubro(): String {
        return storage.getString(SHARED_RUBRO, "")!!
    }

    fun getDoing(): Boolean {
        return storage.getBoolean(SHARED_DOING, false)
    }

    fun saveDoing(doing: Boolean) {
        storage.edit().putBoolean(SHARED_DOING, doing).apply()
    }

    fun getOnWay(): Boolean {
        return storage.getBoolean(SHARED_ON_WAY, false)
    }

    fun saveOnWay(doing: Boolean) {
        storage.edit().putBoolean(SHARED_ON_WAY, doing).apply()
    }

}
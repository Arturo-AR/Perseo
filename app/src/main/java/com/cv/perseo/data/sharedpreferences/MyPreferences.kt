package com.cv.perseo.data.sharedpreferences

import android.content.Context
import com.cv.perseo.utils.Constants.SHARED_DB
import com.cv.perseo.utils.Constants.SHARED_OS_ID
import com.cv.perseo.utils.Constants.SHARED_ZONE
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class MyPreferences @Inject constructor(@ApplicationContext context: Context) {

    private val storage = context.getSharedPreferences(SHARED_DB, 0)

    fun saveId(id:Int) {
        storage.edit().putInt(SHARED_OS_ID, id).apply()
    }

    fun getId():Int{
        return storage.getInt(SHARED_OS_ID, -1)
    }

    fun deleteId() {
        storage.edit().remove(SHARED_OS_ID).apply()
    }

    fun saveZone(zone:String) {
        storage.edit().putString(SHARED_ZONE, zone).apply()
    }

    fun getZone():String {
        return storage.getString(SHARED_ZONE, "")!!
    }

    fun deleteZone() {
        storage.edit().remove(SHARED_ZONE).apply()
    }
}
package com.cv.perseo.data.sharedpreferences

import android.content.Context
import com.cv.perseo.utils.Constants.SHARED_DB
import com.cv.perseo.utils.Constants.SHARED_OS_ID
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
        storage.edit().clear().apply()
    }
}
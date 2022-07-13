package com.cv.perseo.repository

import com.cv.perseo.data.sharedpreferences.MyPreferences
import javax.inject.Inject

class SharedRepository @Inject constructor(private val prefs: MyPreferences) {

    fun saveOsId(id: Int) {
        prefs.saveId(id)
    }

    fun getId(): Int {
        return prefs.getId()
    }

    fun deleteId() {
        prefs.deleteId()
    }

    fun saveZone(zone: String) {
        prefs.saveZone(zone)
    }

    fun getZone(): String {
        return prefs.getZone()
    }

    fun deleteZone() {
        prefs.deleteZone()
    }
}
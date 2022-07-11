package com.cv.perseo.utils

import android.content.Context
import android.widget.Toast
import java.math.BigInteger
import java.security.MessageDigest

/**
 * Funcion para generar un MD5 Hash a partir de un String
 */
fun String.toMD5Hash(): String {
    val md = MessageDigest.getInstance("MD5")
    return BigInteger(1, md.digest(this.toByteArray())).toString(16).padStart(32, '0')
}

/**
 * Funcion para mostrar un mensaje toast en pantalla
 */

fun Context.toast(text: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, text, duration).show()
}

fun String.toHourFormat(): String {
    val slices = this.split(":")
    return "${slices[0]}:${slices[1]}"
}

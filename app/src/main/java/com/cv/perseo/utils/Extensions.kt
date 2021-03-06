package com.cv.perseo.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.widget.Toast
import com.google.gson.Gson
import java.io.ByteArrayOutputStream
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

fun Bitmap.toBase64String(): String {
    val bas = ByteArrayOutputStream()
    this.compress(Bitmap.CompressFormat.JPEG, 100, bas)
    val b = bas.toByteArray()
    return Base64.encodeToString(b, Base64.DEFAULT)
}

fun String.toBitmap(): Bitmap {
    val imageBytes = Base64.decode(this, 0)
    return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
}

fun Any.toJsonString(): String {
    val gson = Gson()
    return gson.toJson(this)
}

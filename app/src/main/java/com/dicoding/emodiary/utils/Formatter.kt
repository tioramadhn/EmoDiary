package com.dicoding.emodiary.utils

import android.os.Build
import android.util.Log
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

fun String.withDateFormat(): String {
    try {
        val format = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX", Locale.US)
        } else {
            throw IllegalArgumentException("Unparseable date")
        }
        val date = format.parse(this) as Date
        return DateFormat.getDateInstance(DateFormat.LONG).format(date)
    } catch (e: ParseException) {
        Log.d("Parse error", e.toString())
        e.printStackTrace()
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return this
}
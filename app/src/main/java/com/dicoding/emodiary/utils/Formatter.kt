package com.dicoding.emodiary.utils

import android.os.Build
import android.util.Log
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.LocalDateTime
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

fun getDateNow(): String {
    val current = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        LocalDateTime.now()
    } else {
        throw IllegalArgumentException("Unparseable date")
    }

    val formatted = "${current.dayOfWeek}, ${current.dayOfMonth} ${current.month} ${current.year}".lowercase()
    val words = formatted.split(' ');
    return words.joinToString(separator = " ") { word -> word.replaceFirstChar { it.uppercase() } }
}

fun getIconFromEmotion(emotion: String): String {
    return when (emotion) {
        "sadness" -> "😢"
        "joy" -> "😄"
        "anger" -> "😡"
        "fear" -> "🙁"
        "love" -> "😍"
        "surprise" -> "😱"
        else -> ""
    }
}
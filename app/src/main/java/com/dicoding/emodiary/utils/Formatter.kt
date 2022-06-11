package com.dicoding.emodiary.utils

import android.content.Context
import android.os.Build
import android.util.Log
import com.dicoding.emodiary.R
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*

fun String.withDateFormat(): String {
    try {
        val format = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            SimpleDateFormat("yyyy-MM-dd", Locale.US)
        } else {
            throw IllegalArgumentException("Unparseable date")
        }
        val date = format.parse(this) as Date

        return DateFormat.getDateInstance(DateFormat.FULL).format(date)
    } catch (e: ParseException) {
        Log.d("Parse error", e.toString())
        e.printStackTrace()
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return this
}

fun getDateNow(): LocalDateTime? {
    val current = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        LocalDateTime.now()
    } else {
        throw IllegalArgumentException("Unparseable date")
    }
    return current
}

data class Emotion (
    val icon: String,
    val motivation: String,
)

fun myEmotion(context: Context, mood: String): Pair<String, String>{
    return when (mood) {
        "fear" -> context.getString(R.string.fear) to context.getString(R.string.emo_fear)
        "anger" -> context.getString(R.string.anger) to context.getString(R.string.emo_anger)
        "sadness" -> context.getString(R.string.sadness) to context.getString(R.string.emo_sadness)
        "joy" -> context.getString(R.string.joy) to context.getString(R.string.emo_joy)
        "surprise" -> context.getString(R.string.surprise) to context.getString(R.string.emo_surprise)
        "love" -> context.getString(R.string.love) to context.getString(R.string.emo_love)
        else -> context.getString(R.string.unknown_emo) to context.getString(R.string.emo_unknown)
    }
}
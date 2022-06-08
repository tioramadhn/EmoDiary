package com.dicoding.emodiary.utils

import android.content.Context
import com.dicoding.emodiary.R

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
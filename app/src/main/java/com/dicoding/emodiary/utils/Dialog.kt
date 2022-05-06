package com.dicoding.emodiary.utils

import android.app.Activity
import androidx.appcompat.app.AlertDialog

fun onAlertDialog(
    activity: Activity,
    title: String,
    message: String,
    negativeTxt: String,
    positiveTxt: String,
    positiveActionCallback: () -> Unit
) {
    val builder = AlertDialog.Builder(activity)
    builder.setTitle(title)
    builder.setMessage(message)
    builder.setPositiveButton(positiveTxt) { _, _ ->
        positiveActionCallback() // callback when user clicked positive button
    }
    builder.setNegativeButton(negativeTxt) { _, _ ->
        // user canceled the dialog
    }
    builder.show()
}
package com.test.codingassignment.utils

import android.app.Activity
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar

fun Activity.showSnackbar(message: String){
    Snackbar.make(findViewById(android.R.id.content), message,
        Snackbar.LENGTH_LONG)
        .setActionTextColor(ContextCompat.getColor(this, android.R.color.holo_red_light))
        .show()
}
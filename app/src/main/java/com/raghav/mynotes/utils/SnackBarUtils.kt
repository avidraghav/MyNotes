package com.raghav.mynotes.utils

import android.content.Context
import android.view.View
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.raghav.mynotes.R

/**
 * Created by Raghav Aggarwal on 14/02/22.
 */
object SnackBarUtils {

    fun Context.showSnackBar(view: View, message: String, duration: Int = Snackbar.LENGTH_SHORT) {
        val snackBar = Snackbar.make(view, message, duration)
        if (duration != Snackbar.LENGTH_SHORT)
            snackBar.duration = duration
        snackBar.setBackgroundTint(ContextCompat.getColor(this, R.color.teal_200)).show()
    }

}
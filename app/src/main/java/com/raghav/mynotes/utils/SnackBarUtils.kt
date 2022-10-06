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

    fun Context.showSnackBar(
        rootView: View,
        message: String,
        anchorView: View? = null,
        actionText: String? = null,
        onAction: (() -> Unit)? = null
    ) {
        val snackBar = Snackbar.make(rootView, message, Snackbar.LENGTH_SHORT)
        if (anchorView != null)
            snackBar.anchorView = anchorView
        snackBar.setBackgroundTint(ContextCompat.getColor(this, R.color.snack_bar)).show()
        snackBar.setTextColor(ContextCompat.getColor(this, R.color.white)).show()
        if (actionText != null && onAction != null) {
            snackBar.setAction(actionText, View.OnClickListener {
                onAction()
            })
        }
    }
}
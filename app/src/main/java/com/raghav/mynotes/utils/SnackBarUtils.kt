package com.raghav.mynotes.utils

import android.content.Context
import android.util.Log
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
        actionText:String?=null,
        onClick: (() -> Unit)?=null
    ) {
        val snackBar = Snackbar.make(rootView, message, Snackbar.LENGTH_LONG)
        if (anchorView != null)
            snackBar.anchorView = anchorView
        snackBar.setBackgroundTint(ContextCompat.getColor(this, R.color.teal_200)).show()
        if(actionText!=null){
            snackBar.setAction(actionText, View.OnClickListener {
                if (onClick != null) {
                    onClick()
                }
            })
        }
    }
}
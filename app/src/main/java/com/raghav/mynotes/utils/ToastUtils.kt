package com.raghav.mynotes.utils

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast

/**
 * Created by Raghav Aggarwal on 14/02/22.
 */
object ToastUtils {

    var mToast: Toast? = null

    @SuppressLint("ShowToast")
    fun showToast(context: Context, message: String) {
        if (mToast != null) {
            mToast?.cancel()
        }
        mToast = Toast.makeText(context, message, Toast.LENGTH_SHORT)
        mToast?.show()
    }
}
package com.raghav.mynotes.utils

import android.annotation.SuppressLint
import com.raghav.mynotes.utils.Constants.DATE_FORMAT
import java.text.SimpleDateFormat

/**
 * Created by Raghav Aggarwal on 09/03/22.
 */
object DateTimeUtils {

    /**
     * converts date value of String type into equivalent milliseconds
     * */
    @SuppressLint("SimpleDateFormat")
    fun String.toTime(): Long? {
        val format = SimpleDateFormat(DATE_FORMAT)
        return format.parse(this)?.time
    }
}
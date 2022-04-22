package com.raghav.mynotes.ui

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Build
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import java.text.DateFormat
import java.util.*

/**
 * Created by Raghav Aggarwal on 26/02/22.
 */
class DatePickerFragment(private val deadLineListener: (String) -> Unit) : DialogFragment(),
    DatePickerDialog.OnDateSetListener {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val calendar = Calendar.getInstance()
        val year = calendar[Calendar.YEAR]
        val month = calendar[Calendar.MONTH]
        val day = calendar[Calendar.DAY_OF_MONTH]
        val datePickerDialog = DatePickerDialog(requireContext(), this, year, month, day)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            datePickerDialog.datePicker.minDate = System.currentTimeMillis() - 1000
        }
        return datePickerDialog
    }

    override fun onDateSet(view: DatePicker, year: Int, month: Int, day: Int) {
        val calendar = Calendar.getInstance()
        calendar[Calendar.YEAR] = year
        calendar[Calendar.MONTH] = month
        calendar[Calendar.DAY_OF_MONTH] = day
        val dateString: String =
            DateFormat.getDateInstance(DateFormat.FULL).format(calendar.time)
        deadLineListener(dateString)
    }
}
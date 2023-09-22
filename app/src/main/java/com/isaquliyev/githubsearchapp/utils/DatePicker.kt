package com.isaquliyev.githubsearchapp.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

class DatePicker {
    @SuppressLint("SimpleDateFormat")
    val formatter = SimpleDateFormat("yyyy-MM-dd")
    var currentDate = Date()
    var calendar: Calendar = Calendar.getInstance()

    fun lastDay(): String {
        calendar.time = currentDate
        calendar.add(Calendar.DAY_OF_MONTH, -1)
        val lastDayDate = calendar.time
        return formatter.format(lastDayDate)
    }
    fun lastWeek(): String {
        calendar.time = currentDate
        calendar.add(Calendar.WEEK_OF_YEAR, -1)
        val lastWeekDate = calendar.time
        return formatter.format(lastWeekDate)
    }
    fun lastMonth(): String {
        calendar.time = currentDate
        calendar.add(Calendar.MONTH, -1)
        val lastMonthDate = calendar.time
        return formatter.format(lastMonthDate)
    }


}
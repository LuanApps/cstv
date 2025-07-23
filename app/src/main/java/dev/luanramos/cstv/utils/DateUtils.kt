package dev.luanramos.cstv.utils

import android.content.Context
import dev.luanramos.cstv.R
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone

fun Date.formatMatchDate(context: Context): String {
    val now = Calendar.getInstance()
    val dateCal = Calendar.getInstance().apply { time = this@formatMatchDate }

    val timeFormatter = SimpleDateFormat("HH:mm", Locale.getDefault())
    timeFormatter.timeZone = TimeZone.getDefault()
    val timeString = timeFormatter.format(this)

    val isToday = now.get(Calendar.YEAR) == dateCal.get(Calendar.YEAR) &&
            now.get(Calendar.DAY_OF_YEAR) == dateCal.get(Calendar.DAY_OF_YEAR)

    if (isToday) {
        val todayLabel = context.getString(R.string.label_today)
        return "$todayLabel, $timeString"
    }

    val nowWeek = now.get(Calendar.WEEK_OF_YEAR)
    val dateWeek = dateCal.get(Calendar.WEEK_OF_YEAR)
    val sameYear = now.get(Calendar.YEAR) == dateCal.get(Calendar.YEAR)

    if (sameYear && nowWeek == dateWeek) {
        val weekdayFormatter = SimpleDateFormat("EEE", Locale.getDefault())
        weekdayFormatter.timeZone = TimeZone.getDefault()
        var weekday = weekdayFormatter.format(this)
            .replace(".", "")
            .replaceFirstChar { it.uppercaseChar() }
        return "$weekday, $timeString"
    }

    val dateFormatter = SimpleDateFormat("dd/MM", Locale.getDefault())
    dateFormatter.timeZone = TimeZone.getDefault()
    val dateString = dateFormatter.format(this)
    return "$dateString, $timeString"
}
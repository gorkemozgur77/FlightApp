package com.gorkemozgur.flightapp.util

import android.content.Context
import com.gorkemozgur.flightapp.R
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class CustomTimeFunctions(val context: Context){

    private val date = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.getDefault())


    fun getDifferenceByHoursAndTime(start: String, end: String): String {
        val startDate = date.parse(start)!!
        val endDate =date.parse(end)!!
        var different = endDate.time - startDate.time
        val secondsInMilli: Long = 1000
        val minutesInMilli = secondsInMilli * 60
        val hoursInMilli = minutesInMilli * 60
        val daysInMilli = hoursInMilli * 24
        different %= daysInMilli
        val elapsedHours = TimeUnit.MILLISECONDS.toHours(different)
        different %= hoursInMilli
        val elapsedMinutes = different / minutesInMilli
        different %= minutesInMilli
        return context.getString(R.string.flight_duration_time, elapsedHours, elapsedMinutes)
    }

    fun getDateByHourMonthDay(date: String): String{
        val dateFormat = SimpleDateFormat("EEE, d MMM\nkk:mm", Locale.getDefault())
        return dateFormat.format(this.date.parse(date)!!)
    }

    fun getDateByHourAndMinute(date: String): String{
        val dateFormat = SimpleDateFormat("kk:mm", Locale.getDefault())
        return dateFormat.format(this.date.parse(date)!!)
    }
}

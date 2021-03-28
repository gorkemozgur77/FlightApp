package com.gorkemozgur.flightapp.util

import java.util.*
import java.util.concurrent.TimeUnit

fun getDifferenceByHoursAndTime(startDate: Date, endDate: Date): String {
    //milliseconds
    var different = endDate.time - startDate.time
    println("startDate : $startDate")
    println("endDate : $endDate")
    println("different : $different")
    val secondsInMilli: Long = 1000
    val minutesInMilli = secondsInMilli * 60
    val hoursInMilli = minutesInMilli * 60
    val daysInMilli = hoursInMilli * 24
    different %= daysInMilli
    val elapsedHours = TimeUnit.MILLISECONDS.toHours(different)
    different %= hoursInMilli
    val elapsedMinutes = different / minutesInMilli
    different %= minutesInMilli

    return "${elapsedHours}s ${elapsedMinutes}dk"
}
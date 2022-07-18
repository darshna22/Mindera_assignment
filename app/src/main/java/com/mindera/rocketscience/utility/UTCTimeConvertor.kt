package com.mindera.rocketscience.utility

import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.Duration
import java.util.*
import java.util.concurrent.TimeUnit

object UTCTimeConvertor {
    private const val DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
    private const val DATE_FORMAT_ = "yyyy-MM-dd HH:mm a"
    private const val DAY_FORMAT = "EEEE-MMM-yyyy"
    fun getDateAndTime(utcDateString: String?): String {
        var strDateTime: String = ""
        try {
            val utcFormat: DateFormat = SimpleDateFormat(DATE_FORMAT)
            utcFormat.timeZone = TimeZone.getTimeZone("IST")

            val date = utcFormat.parse(utcDateString)
            val finalDateFormat: DateFormat = SimpleDateFormat(DATE_FORMAT_)
            strDateTime = finalDateFormat.format(date)
            return strDateTime
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return strDateTime

    }


    fun getRemainingDays(launchDateUnix: Long): String {
        var sign = ""
        val currentTimeStamp: Long = ((Calendar.getInstance().time).time / 1000L)
        val diffTimeStamp = (currentTimeStamp - launchDateUnix * 1000)

        if (diffTimeStamp > 0)
            sign = "+"
        else if (diffTimeStamp < 0)
            sign = "-"
        return (sign + TimeUnit.MILLISECONDS.toDays(diffTimeStamp))
    }
}
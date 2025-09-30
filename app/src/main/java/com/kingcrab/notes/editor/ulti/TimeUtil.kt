package com.kingcrab.notes.editor.ulti

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

object TimeUtil {
    const val TIME_FORMAT_MM_SS: String = "mm:ss"
    const val TIME_FORMAT_HH_MM_SS: String = "hh:mm:ss"
    const val TIME_AUDIO_FORMAT_WITHOUT_HOURS: String = "mm:ss:SS"
    const val TIME_AUDIO_FORMAT: String = "hh:mm:ss:SS"
    const val FILE_NAME_FORMAT_DEFAULT: String = "yyyyMMdd_HHmmss"

    fun convertLongToString(millisSecond: Long, format: String): String {
        val simpleDateFormat = SimpleDateFormat(format, Locale.getDefault())
        simpleDateFormat.timeZone = TimeZone.getTimeZone("UTC")
        val date = Date(millisSecond)
        return simpleDateFormat.format(date)
    }
}
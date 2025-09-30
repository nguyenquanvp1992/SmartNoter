package com.kingcrab.notes.editor.ulti.extension

import com.kingcrab.notes.editor.ulti.TimeUtil
import java.util.Locale
import kotlin.math.pow

fun Long.toFileSizeString(): String {
    val unit = arrayOf("B", "kB", "MB", "GB", "TB", "PB", "EB", "ZB", "YB")
    var position = 0
    var value = this.toDouble()
    while (value >= 1024 && position < (unit.size - 1)) {
        position++
        value = this / 1024.0.pow(position.toDouble())
    }
    return String.format(Locale.getDefault(), "%.2f %s", value, unit[position])
}

fun Long.toTimeString(): String {
    val hours = (this / (1000 * 60 * 60)) % 24
    val format = if (hours > 0) {
        TimeUtil.TIME_AUDIO_FORMAT
    } else {
        TimeUtil.TIME_AUDIO_FORMAT_WITHOUT_HOURS
    }
    return TimeUtil.convertLongToString(this, format)
}

fun Long.toTimeSecondString(): String {
    val hours = (this / (1000 * 60 * 60)) % 24
    val format = if (hours > 0) {
        TimeUtil.TIME_FORMAT_HH_MM_SS
    } else {
        TimeUtil.TIME_FORMAT_MM_SS
    }
    return TimeUtil.convertLongToString(this, format)
}

fun Long.toMillisecond(): Long {
    return this * 1000
}

fun Float.toString(decimal: Int = 0, prefix: String = "", suffix: String = ""): String {
    return String.format(Locale.getDefault(), "%s%.${decimal}f%s", prefix, this, suffix)
}
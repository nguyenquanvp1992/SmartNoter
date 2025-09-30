package com.kingcrab.notes.editor.ulti.extension

import android.view.View
import android.widget.TextView
import com.kingcrab.notes.editor.ulti.TimeUtil

fun TextView.setTextTime(time: Int, format: String = TimeUtil.TIME_FORMAT_MM_SS) {
    text = TimeUtil.convertLongToString(time.toLong(), format)
}

fun View.enabled(enabled: Boolean) {
    isEnabled = enabled
    alpha = if (enabled) 1f else 0.5f
}
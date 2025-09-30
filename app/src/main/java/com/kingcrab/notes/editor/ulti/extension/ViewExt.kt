package com.kingcrab.notes.editor.ulti.extension

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.TextView
import com.kingcrab.notes.editor.ulti.TimeUtil

fun TextView.setTextTime(time: Int, format: String = TimeUtil.TIME_FORMAT_MM_SS) {
    text = TimeUtil.convertLongToString(time.toLong(), format)
}

fun EditText.addOnTextChange(onTextChange: (CharSequence, Int, Int, Int) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(p0: Editable?) {
        }

        override fun beforeTextChanged(p0: CharSequence, start: Int, count: Int, after: Int) {
            onTextChange.invoke(p0, start, count, after)
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }
    })
}

fun View.enabled(enabled: Boolean) {
    isEnabled = enabled
    alpha = if (enabled) 1f else 0.5f
}
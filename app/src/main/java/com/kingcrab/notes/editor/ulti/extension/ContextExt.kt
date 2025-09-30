package com.kingcrab.notes.editor.ulti.extension

import android.content.Context
import android.graphics.Typeface
import androidx.core.content.res.ResourcesCompat

fun Context?.getTypeFace(fontResId: Int): Typeface? {
    return this?.let {
        ResourcesCompat.getFont(it, fontResId)
    } ?: run { null }
}
package com.kingcrab.notes.editor.ulti.extension

import android.os.Build
import android.os.Bundle

inline fun <reified T : Enum<T>> Bundle.getEnum(key: String): T? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        getSerializable(key, T::class.java)
    } else {
        @Suppress("DEPRECATION")
        getSerializable(key) as? T
    }
}
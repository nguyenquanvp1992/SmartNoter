package com.kingcrab.notes.editor.ulti.extension

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener

fun Fragment.setFragmentResultListener(keys: List<String>, listener: ((requestKey: String, bundle: Bundle) -> Unit)) {
    keys.forEach { key ->
        setFragmentResultListener(key, listener)
    }
}

fun Fragment.setResult(key: String, bundle: Bundle) {
    parentFragmentManager.clearFragmentResult(key)
    parentFragmentManager.setFragmentResult(key, bundle)
}
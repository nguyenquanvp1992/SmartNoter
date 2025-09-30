package com.convertertools.converter.video2audio.util.extension

fun String.getFileName(): String {
    val value = this.trim()
    return if (value.isNotEmpty() && value.contains("/")) {
        value.substring(value.lastIndexOf("/") + 1)
    } else {
        value
    }
}

fun String.getFileNameWithoutExtension(): String {
    val fileName = getFileName()
    return if (fileName.contains(".")) {
        fileName.substring(0, fileName.lastIndexOf("."))
    } else {
        fileName
    }
}

fun String.getFileExtension(): String {
    val fileName = getFileName()
    return if (fileName.contains(".")) {
        fileName.substring(fileName.lastIndexOf(".") + 1)
    } else {
        ""
    }
}
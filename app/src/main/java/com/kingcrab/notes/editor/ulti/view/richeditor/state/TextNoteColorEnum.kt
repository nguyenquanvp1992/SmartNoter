package com.kingcrab.notes.editor.ulti.view.richeditor.state

import androidx.annotation.ColorRes
import com.kingcrab.notes.editor.R

enum class TextNoteColorEnum(@param:ColorRes val colorRes: Int) {
    DEFAULT(R.color.text_color_default),
    RED(R.color.red),
    ORANGE(R.color.orange),
    BLUE(R.color.blue),
    YELLOW(R.color.yellow),
    GREEN(R.color.green),
    PINK(R.color.pink),
    WHITE(R.color.white),
    BLACK(R.color.black)
}
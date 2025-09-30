package com.kingcrab.notes.editor.ulti.view.richeditor.span

import android.content.Context
import android.graphics.Paint
import android.graphics.Typeface
import android.text.TextPaint
import android.text.style.MetricAffectingSpan
import com.kingcrab.notes.editor.ulti.extension.getTypeFace
import com.kingcrab.notes.editor.ulti.view.richeditor.state.NoteFont

class CustomTypefaceSpan(context: Context, fontFileName: String) : MetricAffectingSpan() {
    val typeface: Typeface?
    val family: String

    init {
        val noteFont = NoteFont.getFromName(fontFileName)
        typeface = context.getTypeFace(noteFont.fontResId)
        this.family = fontFileName
    }

    override fun updateMeasureState(paint: TextPaint) {
        apply(paint)
    }

    override fun updateDrawState(tp: TextPaint) {
        apply(tp)
    }

    private fun apply(paint: Paint) {
        val old = paint.typeface
        val oldStyle = old?.style ?: 0

        val fake = oldStyle and typeface!!.style.inv()
        if ((fake and Typeface.BOLD) != 0) {
            paint.isFakeBoldText = true
        }
        if ((fake and Typeface.ITALIC) != 0) {
            paint.textSkewX = -0.25f
        }

        paint.typeface = typeface
    }
}
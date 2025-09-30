package com.kingcrab.notes.editor.ulti.view.richeditor.span

import android.content.Context
import android.text.style.ForegroundColorSpan
import androidx.core.content.ContextCompat
import com.kingcrab.notes.editor.ulti.view.richeditor.state.TextNoteColorEnum

class ResourceColorSpan(context: Context, @JvmField val color: TextNoteColorEnum) :
    ForegroundColorSpan(ContextCompat.getColor(context, color.colorRes))
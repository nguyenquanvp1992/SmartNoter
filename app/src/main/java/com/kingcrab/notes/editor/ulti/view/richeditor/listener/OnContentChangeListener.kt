package com.kingcrab.notes.editor.ulti.view.richeditor.listener

import com.kingcrab.notes.editor.ulti.view.richeditor.span.RichEditorSpan

interface OnContentChangeListener {
    fun onContentChange(content: String, spans: MutableList<RichEditorSpan>)
}
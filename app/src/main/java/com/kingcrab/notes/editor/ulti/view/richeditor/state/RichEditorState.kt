package com.kingcrab.notes.editor.ulti.view.richeditor.state

import com.kingcrab.notes.editor.ulti.view.richeditor.state.NoteFont.Companion.getFromName

open class RichEditorState {
    var isBold: Boolean = false
    var isItalic: Boolean = false
    var isUnderLine: Boolean = false
    var isStrikethrough: Boolean = false
    var textSize: Int = RichEditorKey.TEXT_SIZE_DEFAULT
    var font: NoteFont? = null
    var textColor: TextNoteColorEnum = TextNoteColorEnum.DEFAULT

    fun parserSpan(style: MutableMap<String?, Any?>) {
        isBold = style.containsKey(RichEditorKey.KEY_IS_BOLD)
        isItalic = style.containsKey(RichEditorKey.KEY_IS_ITALIC)
        isStrikethrough = style.containsKey(RichEditorKey.KEY_ID_STRIKETHROUGH)
        isUnderLine = style.containsKey(RichEditorKey.KEY_IS_UNDERLINE)
        if (style.containsKey(RichEditorKey.KEY_FONT)) {
            font = getFromName(style[RichEditorKey.KEY_FONT] as String?)
        } else {
//            font = NoteFont.getDefault();
            // TODO
        }
        if (style.containsKey(RichEditorKey.KEY_COLOR)) {
            val color = style[RichEditorKey.KEY_COLOR]
            if (color is TextNoteColorEnum) {
                textColor = color
            }
        } else {
            textColor = TextNoteColorEnum.DEFAULT
        }
        if (style.containsKey(RichEditorKey.KEY_SIZE)) {
            val size = style[RichEditorKey.KEY_SIZE]
            if (size is Int) {
                textSize = size
            }
        } else {
            textSize = RichEditorKey.TEXT_SIZE_DEFAULT
        }
    }
}
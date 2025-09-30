package com.kingcrab.notes.editor.ulti.view.richeditor.listener

import com.kingcrab.notes.editor.ulti.extension.addOnTextChange
import com.kingcrab.notes.editor.ulti.view.richeditor.RichEditorView
import com.kingcrab.notes.editor.ulti.view.richeditor.span.CustomBulletSpan
import com.kingcrab.notes.editor.ulti.view.richeditor.span.SpanApplier

class BulletHandler(private val edittext: RichEditorView) {
    private val spanApplier: SpanApplier = SpanApplier(edittext)

    init {
        this.edittext.addOnTextChange { char, start, count, after ->
            if (after == 1 && count == 0 && char[start] == '\n') {
                checkApplyBulletsSpan(start)
            } else if (after == 0 && count == 1) {
                cleanupBullet()
            }
        }
    }

    fun setBullets(bullets: String?) {
        val currentLineRange = edittext.currentLineRange
        val editable = edittext.getText()
        if (editable == null) return
        if (currentLineRange.first == currentLineRange.second) {
            editable.insert(currentLineRange.first, TEXT_ZERO_WIDTH)
            spanApplier.setSpan(
                CustomBulletSpan(bullets),
                currentLineRange.first,
                currentLineRange.second
            )
            return
        }
        var customBulletSpan: CustomBulletSpan? = null
        val spans = editable.getSpans(
            currentLineRange.first,
            currentLineRange.second,
            Any::class.java
        )
        for (span in spans) {
            if (span is CustomBulletSpan) {
                customBulletSpan = span
                break
            }
        }
        if (customBulletSpan != null) {
            val oldBullets = customBulletSpan.bullets
            spanApplier.setSpan(
                customBulletSpan,
                editable.getSpanStart(customBulletSpan),
                editable.getSpanEnd(customBulletSpan),
                false
            )
            if (oldBullets != bullets) {
                spanApplier.setSpan(
                    CustomBulletSpan(bullets),
                    currentLineRange.first,
                    currentLineRange.second
                )
            }
        } else {
            spanApplier.setSpan(
                CustomBulletSpan(bullets),
                currentLineRange.first,
                currentLineRange.second
            )
        }
    }

    private fun checkApplyBulletsSpan(start: Int) {
        val editable = edittext.getText()
        if (editable != null) {
            val newLineStart = start + 1
            val spans =
                editable.getSpans(start, start, CustomBulletSpan::class.java)
            if (spans.size > 0) {
                val old = spans[0]
                val spanStart = editable.getSpanStart(old)
                var spanEnd = editable.getSpanEnd(old)
                val cursor = edittext.selectionStart
                if (cursor > spanStart && cursor < spanEnd) {
                    spanEnd = cursor
                }
                spanApplier.setSpan(old, false)
                spanApplier.setSpan(old, spanStart, spanEnd - 1)
                if (spanEnd > spanStart && (spanEnd - spanStart) > 2) {
                    val newBullet = CustomBulletSpan(old.bullets)
                    val rangeNewLine = edittext.getLineRange(newLineStart)
                    editable.insert(newLineStart, TEXT_ZERO_WIDTH)
                    val newLineEnd: Int
                    if (rangeNewLine.first < rangeNewLine.second - 1) {
                        newLineEnd = rangeNewLine.second
                    } else {
                        newLineEnd = newLineStart + TEXT_ZERO_WIDTH.length
                    }
                    spanApplier.setSpan(newBullet, newLineStart, newLineEnd)
                }
            }
        }
    }

    private fun cleanupBullet() {
        val editable = edittext.getText()
        if (editable != null) {
            val bulletSpans = editable.getSpans(0, editable.length, CustomBulletSpan::class.java)
            for (b in bulletSpans) {
                val start = editable.getSpanStart(b)
                val end = editable.getSpanEnd(b)
                if (start == end) {
                    editable.removeSpan(b)
                }
            }
        }
    }

    companion object {
        private const val TEXT_ZERO_WIDTH = "\u200B"
    }
}
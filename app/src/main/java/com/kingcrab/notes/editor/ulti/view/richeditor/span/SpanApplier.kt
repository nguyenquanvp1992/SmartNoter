package com.kingcrab.notes.editor.ulti.view.richeditor.span

import android.text.Spannable
import android.text.style.AbsoluteSizeSpan
import android.text.style.StrikethroughSpan
import android.text.style.StyleSpan
import android.text.style.TypefaceSpan
import android.text.style.UnderlineSpan
import com.kingcrab.notes.editor.ulti.view.richeditor.RichEditorView

class SpanApplier(private val editorView: RichEditorView) {
    fun setSpan(span: Any, isApply: Boolean) {
        if (isApply) {
            applySpan(span)
        } else {
            if (span is StyleSpan) {
                removeStyleSpan(span.style)
            } else {
                removeSpan(span.javaClass)
            }
        }
    }

    fun setSpan(span: Any?) {
        applySpan(span)
    }

    fun setSpan(span: Any?, start: Int, end: Int) {
        applySpan(span, start, end)
    }

    fun setSpan(span: Any, start: Int, end: Int, isApply: Boolean) {
        if (isApply) {
            applySpan(span, start, end)
        } else {
            removeSpan(span.javaClass, start, end)
        }
    }

    private fun applySpan(span: Any?) {
        val start = editorView.selectionStart
        val end = editorView.selectionEnd
        applySpan(span, start, end)
    }

    private fun applySpan(span: Any?, start: Int, end: Int) {
        if (start < 0 || end <= start) return
        val editable = editorView.getText()
        editable?.setSpan(span, start, end, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
    }

    private fun removeSpan(spanClass: Class<*>?) {
        val start = editorView.selectionStart
        val end = editorView.selectionEnd
        removeSpan(spanClass, start, end)
    }

    private fun removeSpan(spanClass: Class<*>?, start: Int, end: Int) {
        if (start < 0 || end <= start) return
        val editable = editorView.getText()
        if (editable == null) {
            return
        }
        val spans = editable.getSpans(start, end, spanClass)
        for (span in spans) {
            removeSpan(span, start, end)
        }
    }

    private fun removeStyleSpan(style: Int) {
        val start = editorView.selectionStart
        val end = editorView.selectionEnd
        removeStyleSpan(style, start, end)
    }

    private fun removeStyleSpan(style: Int, start: Int, end: Int) {
        val editable = editorView.getText()
        if (start < 0 || end <= start || editable == null) return
        val spans = editable.getSpans(start, end, StyleSpan::class.java)
        for (span in spans) {
            if (span is StyleSpan && span.style == style) {
                removeSpan(span, start, end)
            }
        }
    }

    private fun removeSpan(span: Any?, start: Int, end: Int) {
        val editable = editorView.getText()
        if (editable == null) {
            return
        }
        val spanStart = editable.getSpanStart(span)
        val spanEnd = editable.getSpanEnd(span)
        editable.removeSpan(span)
        if (start > spanStart) {
            editable.setSpan(cloneSpan(span), spanStart, start, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
        if (end < spanEnd) {
            editable.setSpan(cloneSpan(span), end, spanEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
    }

    private fun cloneSpan(span: Any?): Any? {
        when (span) {
            is StrikethroughSpan -> {
                return StrikethroughSpan()
            }

            is UnderlineSpan -> {
                return UnderlineSpan()
            }

            is StyleSpan -> {
                return StyleSpan(span.style)
            }

            is ResourceColorSpan -> {
                return ResourceColorSpan(editorView.context, span.color)
            }

            is AbsoluteSizeSpan -> {
                return AbsoluteSizeSpan(span.size, span.dip)
            }

            is TypefaceSpan -> {
                return TypefaceSpan(span.family)
            }

            else -> return null
        }
    }
}
package com.kingcrab.notes.editor.ulti.view.richeditor

import android.content.Context
import android.graphics.Typeface
import android.text.Editable
import android.text.TextWatcher
import android.text.style.AbsoluteSizeSpan
import android.text.style.StrikethroughSpan
import android.text.style.StyleSpan
import android.text.style.UnderlineSpan
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import com.kingcrab.notes.editor.ulti.view.richeditor.listener.BulletHandler
import com.kingcrab.notes.editor.ulti.view.richeditor.listener.OnContentChangeListener
import com.kingcrab.notes.editor.ulti.view.richeditor.listener.OnCursorChangeListener
import com.kingcrab.notes.editor.ulti.view.richeditor.span.CustomTypefaceSpan
import com.kingcrab.notes.editor.ulti.view.richeditor.span.ResourceColorSpan
import com.kingcrab.notes.editor.ulti.view.richeditor.span.RichEditorSpan
import com.kingcrab.notes.editor.ulti.view.richeditor.span.RichEditorSpan.Companion.createInstance
import com.kingcrab.notes.editor.ulti.view.richeditor.span.SpanApplier
import com.kingcrab.notes.editor.ulti.view.richeditor.state.RichEditorKey
import com.kingcrab.notes.editor.ulti.view.richeditor.state.TextNoteColorEnum
import kotlin.math.max

class RichEditorView : AppCompatEditText {
    private var currentCursor = -1
    private var isBold: Boolean? = null
    private var isItalic: Boolean? = null
    private var isStrikeThrough: Boolean? = null
    private var isUnderline: Boolean? = null
    private var fontName: String? = null
    private var textSize: Int? = null
    private var textColor: TextNoteColorEnum? = null
    private var cursorChangeListener: OnCursorChangeListener? = null
    private var onContentChangeListener: OnContentChangeListener? = null
    private var bulletHandler: BulletHandler? = null
    private var spanApplier: SpanApplier? = null

    private val textWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(char: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(char: CharSequence?, start: Int, count: Int, after: Int) {
            if (after > 0 && start == currentCursor) {
                applySetting(start, after)
            }
            resetSetting()
            currentCursor = -1
        }

        override fun afterTextChanged(editable: Editable) {
            if (onContentChangeListener != null) {
                onContentChangeListener?.onContentChange(editable.toString(), allSpan)
            }
        }
    }

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }

    private fun init() {
        addTextChangedListener(textWatcher)
        bulletHandler = BulletHandler(this)
        spanApplier = SpanApplier(this)
    }

    override fun onSelectionChanged(selStart: Int, selEnd: Int) {
        super.onSelectionChanged(selStart, selEnd)
        if (cursorChangeListener != null) {
            val styles = getCurrentStyleAtCursor(selStart)
            cursorChangeListener?.onCursorChange(styles)
        }
    }

    fun setOnCursorChangeListener(listener: OnCursorChangeListener?) {
        this.cursorChangeListener = listener
    }

    fun setOnContentChangeListener(listener: OnContentChangeListener?) {
        this.onContentChangeListener = listener
    }

    fun setFontName(fontName: String) {
        setFontFromAssets(fontName, selectionStart, selectionEnd)
        resetSetting()
        this.fontName = fontName
    }

    fun setTextSize(textSize: Int) {
        spanApplier?.setSpan(AbsoluteSizeSpan(textSize, true))
        resetSetting()
        this.textSize = textSize
    }

    fun setTextColor(color: TextNoteColorEnum) {
        if (color != TextNoteColorEnum.DEFAULT) {
            spanApplier?.setSpan(ResourceColorSpan(context, color))
        } else {
            val start = selectionStart
            spanApplier?.setSpan(ResourceColorSpan(context, color), start, start + 1, false)
        }
        resetSetting()
        this.textColor = color
    }

    fun setStrikeThrough(isStrikeThrough: Boolean) {
        spanApplier?.setSpan(StrikethroughSpan(), isStrikeThrough)
        resetSetting()
        this.isStrikeThrough = isStrikeThrough
    }

    fun setUnderline(isUnderline: Boolean) {
        spanApplier?.setSpan(UnderlineSpan(), isUnderline)
        resetSetting()
        this.isUnderline = isUnderline
    }

    fun setBold(isBold: Boolean) {
        spanApplier?.setSpan(StyleSpan(Typeface.BOLD), isBold)
        resetSetting()
        this.isBold = isBold
    }

    fun setItalic(isItalic: Boolean) {
        spanApplier?.setSpan(StyleSpan(Typeface.ITALIC), isItalic)
        resetSetting()
        this.isItalic = isItalic
    }

    fun setText(text: String?, style: MutableList<RichEditorSpan>) {
        addText(text)
        for (span in style) {
            spanApplier?.setSpan(span.getSpan(context), span.start, span.end)
        }
    }

    fun setBullets(bullets: String?) {
        bulletHandler?.setBullets(bullets)
    }

    fun addText(text: String?) {
        val editable = getText()
        if (editable != null) {
            val cursor = max(0, selectionStart)
            editable.insert(cursor, text)
        }
    }

    private fun resetSetting() {
        if (currentCursor != selectionStart) {
            currentCursor = selectionStart
            isBold = null
            isItalic = null
            isStrikeThrough = null
            isUnderline = null
            textSize = null
            textColor = null
        }
    }

    private fun applySetting(start: Int, count: Int) {
        val end = start + count
        val editable = getText()
        if (editable != null) {
            applySetting(isBold, StyleSpan(Typeface.BOLD), start, end)
            applySetting(isItalic, StyleSpan(Typeface.ITALIC), start, end)
            applySetting(isUnderline, UnderlineSpan(), start, end)
            applySetting(isStrikeThrough, StrikethroughSpan(), start, end)
            textSize?.let {
                spanApplier?.setSpan(AbsoluteSizeSpan(it, true), start, end)
            }
            textColor?.let {
                val isApply = textColor != TextNoteColorEnum.DEFAULT
                spanApplier?.setSpan(ResourceColorSpan(context, it), start, end, isApply)
            }
            fontName?.let { setFontFromAssets(it, start, end) }
        }
    }

    private fun applySetting(isEnable: Boolean?, span: Any, start: Int, end: Int) {
        if (isEnable != null) {
            spanApplier?.setSpan(span, start, end, isEnable)
        }
    }

    fun setFontFromAssets(fontFileName: String, start: Int, end: Int) {
        spanApplier?.setSpan(CustomTypefaceSpan(context, fontFileName), start, end)
    }

    val currentLineRange: Pair<Int, Int>
        get() = getLineRange(selectionStart)

    fun getLineRange(cursor: Int): Pair<Int, Int> {
        var lineStart = 0
        var lineEnd = 0
        val layout = getLayout()
        if (layout != null && cursor >= 0) {
            val line = layout.getLineForOffset(cursor)
            lineStart = layout.getLineStart(line)
            lineEnd = layout.getLineEnd(line)
        }
        return Pair(lineStart, lineEnd)
    }

    private fun getCurrentStyleAtCursor(pos: Int): MutableMap<String, Any> {
        val style: MutableMap<String, Any> = HashMap()
        val editable = getText()
        if (editable == null || editable.toString().isEmpty() || pos < 0) return style

        val spans = editable.getSpans(pos, pos, Any::class.java)
        for (span in spans) {
            val start = editable.getSpanStart(span)
            val end = editable.getSpanEnd(span)
            if (start > pos || end < pos || start == end) {
                continue
            }
            if (span is StyleSpan) {
                val s = span.style
                if (s == Typeface.BOLD) style.put(RichEditorKey.KEY_IS_BOLD, true)
                if (s == Typeface.ITALIC) style.put(RichEditorKey.KEY_IS_ITALIC, true)
            }
            if (span is UnderlineSpan) style.put(RichEditorKey.KEY_IS_UNDERLINE, true)
            if (span is StrikethroughSpan) style.put(RichEditorKey.KEY_ID_STRIKETHROUGH, true)
            if (span is ResourceColorSpan) {
                style.put(RichEditorKey.KEY_COLOR, span.color)
            }
            if (span is AbsoluteSizeSpan) {
                style.put(RichEditorKey.KEY_SIZE, span.size)
            }
            if (span is CustomTypefaceSpan) {
                style.put(RichEditorKey.KEY_FONT, span.family)
            }
        }
        return style
    }

    private val allSpan: MutableList<RichEditorSpan>
        get() {
            val result: MutableList<RichEditorSpan> = ArrayList()
            val editable = getText()
            if (editable == null) return result
            val spans = editable.getSpans(0, editable.length, Any::class.java)
            for (span in spans) {
                val start = editable.getSpanStart(span)
                val end = editable.getSpanEnd(span)
                val item = createInstance(span, start, end)
                if (item != null && start < end) {
                    result.add(item)
                }
            }
            return result
        }
}
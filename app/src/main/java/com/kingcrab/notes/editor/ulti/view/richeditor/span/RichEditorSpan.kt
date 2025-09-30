package com.kingcrab.notes.editor.ulti.view.richeditor.span

import android.content.Context
import android.graphics.Typeface
import android.os.Parcel
import android.os.Parcelable
import android.text.style.AbsoluteSizeSpan
import android.text.style.StrikethroughSpan
import android.text.style.StyleSpan
import android.text.style.UnderlineSpan
import com.kingcrab.notes.editor.ulti.view.richeditor.state.TextNoteColorEnum

open class RichEditorSpan : Parcelable {
    private var spanType: Int

    @JvmField
    var start: Int

    @JvmField
    var end: Int
    private var value: Any? = null

    constructor() {
        this.start = 0
        this.end = 0
        this.spanType = 0
        this.value = null
    }

    protected constructor(`in`: Parcel) {
        spanType = `in`.readInt()
        start = `in`.readInt()
        end = `in`.readInt()
    }

    fun setSpanType(type: Int) {
        this.spanType = type
    }

    fun setValue(value: Any?) {
        this.value = value
    }

    fun getSpan(context: Context): Any {
        if (spanType == SPAN_TYPE_BOLD) {
            return StyleSpan(Typeface.BOLD)
        } else if (spanType == SPAN_TYPE_ITALIC) {
            return StyleSpan(Typeface.ITALIC)
        } else if (spanType == SPAN_TYPE_UNDER_LINE) {
            return UnderlineSpan()
        } else if (spanType == SPAN_TYPE_STRIKETHROUGH) {
            return StrikethroughSpan()
        } else if (spanType == SPAN_TYPE_COLOR) {
            if (value is String) {
                val color = TextNoteColorEnum.valueOf(value as String)
                return ResourceColorSpan(context, color)
            }
        } else if (spanType == SPAN_TYPE_SIZE) {
            if (value is Number) {
                return AbsoluteSizeSpan((value as Number).toInt(), true)
            }
        } else if (spanType == SPAN_TYPE_FONT) {
            if (value is String) {
                try {
                    return CustomTypefaceSpan(context, value as String)
                } catch (_: Exception) {
                }
            }
        } else if (spanType == SPAN_TYPE_BULLETS) {
            if (value is String) {
                return CustomBulletSpan(value as String)
            }
        }
        return StyleSpan(Typeface.NORMAL)
    }

    val isAbsoluteSizeSpan: Boolean
        get() = spanType == SPAN_TYPE_SIZE

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(parcel: Parcel, i: Int) {
        parcel.writeInt(spanType)
        parcel.writeInt(start)
        parcel.writeInt(end)
    }

    companion object {
        private const val SPAN_TYPE_BOLD = 1
        private const val SPAN_TYPE_ITALIC = 2
        private const val SPAN_TYPE_UNDER_LINE = 3
        private const val SPAN_TYPE_STRIKETHROUGH = 4
        private const val SPAN_TYPE_COLOR = 5
        private const val SPAN_TYPE_SIZE = 6
        private const val SPAN_TYPE_FONT = 7
        private const val SPAN_TYPE_BULLETS = 8

        @JvmStatic
        fun createInstance(span: Any?, start: Int, end: Int): RichEditorSpan? {
            val result = RichEditorSpan()
            result.start = start
            result.end = end
            if (span is StyleSpan) {
                val s = span.style
                if (s == Typeface.BOLD) {
                    result.setSpanType(SPAN_TYPE_BOLD)
                }
                if (s == Typeface.ITALIC) {
                    result.setSpanType(SPAN_TYPE_ITALIC)
                }
            } else if (span is UnderlineSpan) {
                result.setSpanType(SPAN_TYPE_UNDER_LINE)
            } else if (span is StrikethroughSpan) {
                result.setSpanType(SPAN_TYPE_STRIKETHROUGH)
            } else if (span is ResourceColorSpan) {
                result.setSpanType(SPAN_TYPE_COLOR)
                result.setValue(span.color.name)
            } else if (span is AbsoluteSizeSpan) {
                result.setSpanType(SPAN_TYPE_SIZE)
                result.setValue(span.getSize())
            } else if (span is CustomTypefaceSpan) {
                result.setSpanType(SPAN_TYPE_FONT)
                result.setValue(span.family)
            } else if (span is CustomBulletSpan) {
                result.setSpanType(SPAN_TYPE_BULLETS)
                result.setValue(span.bullets)
            } else {
                return null
            }
            return result
        }

        @JvmField
        val CREATOR: Parcelable.Creator<RichEditorSpan?> =
            object : Parcelable.Creator<RichEditorSpan?> {
                override fun createFromParcel(`in`: Parcel): RichEditorSpan {
                    return RichEditorSpan(`in`)
                }

                override fun newArray(size: Int): Array<RichEditorSpan?> {
                    return arrayOfNulls(size)
                }
            }
    }
}
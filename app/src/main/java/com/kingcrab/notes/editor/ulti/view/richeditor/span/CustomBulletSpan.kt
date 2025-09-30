package com.kingcrab.notes.editor.ulti.view.richeditor.span

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Paint.Align
import android.text.Layout
import android.text.Spanned
import android.text.style.LeadingMarginSpan
import androidx.annotation.ColorInt
import androidx.annotation.IntRange

class CustomBulletSpan private constructor(
    private val mGapWidth: Int,
    @param:ColorInt private val mColor: Int,
    private val mWantColor: Boolean,
    @param:IntRange(from = 0) private val mBulletRadius: Int,
    val bullets: String?
) : LeadingMarginSpan {
    constructor(bulletsString: String?) : this(
        STANDARD_GAP_WIDTH,
        STANDARD_COLOR,
        false,
        STANDARD_BULLET_RADIUS,
        bulletsString
    )

    override fun getLeadingMargin(first: Boolean): Int {
        return 2 * mBulletRadius + mGapWidth
    }

    override fun drawLeadingMargin(
        canvas: Canvas,
        paint: Paint,
        x: Int,
        dir: Int,
        top: Int,
        baseline: Int,
        bottom: Int,
        text: CharSequence,
        start: Int,
        end: Int,
        first: Boolean,
        layout: Layout?
    ) {
        if ((text as Spanned).getSpanStart(this) != start) {
            return
        }

        // save paint state
        val oldStyle = paint.style
        val oldAlign = paint.textAlign
        val oldTextSize = paint.textSize
        val oldColor = paint.color
        if (mWantColor) {
            paint.color = mColor
        }
        paint.style = Paint.Style.FILL

        val yCenter = (top + bottom) / 2f

        if (this.bullets == null || bullets.isEmpty()) {
            canvas.drawCircle(START_X, yCenter, mBulletRadius.toFloat(), paint)
        } else {
            paint.textAlign = Align.CENTER
            val fm = paint.fontMetrics
            val textBaseline = yCenter - (fm.ascent + fm.descent) / 2f
            canvas.drawText(this.bullets, START_X, textBaseline, paint)
        }

        // restore paint
        paint.style = oldStyle
        paint.textAlign = oldAlign
        paint.textSize = oldTextSize
        paint.color = oldColor
    }

    companion object {
        private const val STANDARD_BULLET_RADIUS = 4
        const val STANDARD_GAP_WIDTH: Int = 100
        private const val STANDARD_COLOR = 0
        private const val START_X = 50f
    }
}
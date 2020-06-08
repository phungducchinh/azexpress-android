package com.lib.core.widget

import android.content.Context
import android.graphics.BlurMaskFilter
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import com.lib.core.R

class BlurView : View {

    companion object {
        val TAG = BlurView::class.java.simpleName
        const val DEFAULT_COLOR = 0x66000000
        const val DEFAULT_RADIUS = 40f
        const val DEFAULT_BLUR_MODE = 0
    }

    private var mRootRect: RectF = RectF()

    @ColorInt
    private var mColor: Int = 0
    private var mRadius: Float = 0f
    private var mMode: Int = 0

    private var mBlurPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(context, attrs)
    }

    fun init(ctx: Context, attrs: AttributeSet?) {

        val a = ctx.obtainStyledAttributes(attrs, R.styleable.BlurView)
        try {
            this.mColor = a.getColor(R.styleable.BlurView_color, DEFAULT_COLOR)
            this.mRadius = a.getFloat(R.styleable.BlurView_radiu, DEFAULT_RADIUS)
            this.mMode = a.getInt(R.styleable.BlurView_mode, DEFAULT_BLUR_MODE)
        } finally {
            a.recycle()
        }
    }

    override
    fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        // Try for a width based on our minimum
        val minw: Int = paddingLeft + paddingRight + suggestedMinimumWidth
        val w: Int = resolveSizeAndState(minw, widthMeasureSpec, 1)

        // Whatever the width ends up being, ask for a height that would let the pie
        // get as big as it can
        val h: Int = resolveSizeAndState(MeasureSpec.getSize(w), heightMeasureSpec, 0)
        Log.d(TAG, String.format("onMeasure: width => %d, height => %d", w, h))
        setMeasuredDimension(w, h)
    }

    override
    fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mRootRect.left = 0f
        mRootRect.top = 0f
        mRootRect.right = w.toFloat()
        mRootRect.bottom = h.toFloat()
        Log.d(
            TAG, String.format(
                "onSizeChanged: width => %d, height => %d, oldWidth => %d, oldHeight => %d, mRootRect => %s",
                w, h, oldw, oldh, mRootRect.toShortString()
            )
        )
        setup()
    }

    private fun setup() {
        mBlurPaint.apply {
//            color = ContextCompat.getColor(context, mColor)
            color = mColor
            when (mMode) {
                0 -> {
                    maskFilter = BlurMaskFilter(mRadius, BlurMaskFilter.Blur.OUTER)
                }
                1 -> {
                    maskFilter = BlurMaskFilter(mRadius, BlurMaskFilter.Blur.NORMAL)
                }
                2 -> {
                    maskFilter = BlurMaskFilter(mRadius, BlurMaskFilter.Blur.SOLID)
                }
            }

        }
    }

    override
    fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawRect(mRootRect, mBlurPaint)
    }

}
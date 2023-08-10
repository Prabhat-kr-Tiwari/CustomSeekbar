package com.example.progressbar

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

class ArcSeekBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val MAX_PROGRESS = 100
    private val arcPaint = Paint()
    private val thumbPaint = Paint()
    private lateinit var arcBounds: RectF
    private var thumbAngle = 0f // Current angle of the thumb
    private var progressChangeListener: OnProgressChangeListener? = null

    init {
        arcPaint.color = Color.RED
        arcPaint.style = Paint.Style.STROKE
        arcPaint.strokeWidth = 10f

        thumbPaint.color = Color.RED
        thumbPaint.style = Paint.Style.FILL
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        arcBounds = RectF(20f, 20f, (w - 20).toFloat(), (h - 20).toFloat())
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // Draw the arc
        canvas.drawArc(arcBounds, -90f, thumbAngle, false, arcPaint)

        // Draw the thumb at the end of the arc
        val thumbX = (arcBounds.centerX() + arcBounds.width() / 2 * Math.cos(Math.toRadians((thumbAngle - 90).toDouble()))).toFloat()
        val thumbY = (arcBounds.centerY() + arcBounds.height() / 2 * Math.sin(Math.toRadians((thumbAngle - 90).toDouble()))).toFloat()
        canvas.drawCircle(thumbX, thumbY, 15f, thumbPaint)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val x = event.x
        val y = event.y
        when (event.action) {
            MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE -> {
                thumbAngle = calculateAngle(x, y)
                if (thumbAngle < 0) thumbAngle = 0f
                if (thumbAngle > 180) thumbAngle = 180f
                invalidate()
                progressChangeListener?.onProgressChanged((thumbAngle / 180 * MAX_PROGRESS).toInt())
                return true
            }
        }
        return super.onTouchEvent(event)
    }

    private fun calculateAngle(x: Float, y: Float): Float {
        var angle = Math.toDegrees(Math.atan2((y - arcBounds.centerY()).toDouble(), (x - arcBounds.centerX()).toDouble()))
        if (angle < 0) {
            angle += 360
        }
        return angle.toFloat()
    }

    fun setOnProgressChangeListener(listener: OnProgressChangeListener) {
        progressChangeListener = listener
    }

    interface OnProgressChangeListener {
        fun onProgressChanged(progress: Int)
    }
}

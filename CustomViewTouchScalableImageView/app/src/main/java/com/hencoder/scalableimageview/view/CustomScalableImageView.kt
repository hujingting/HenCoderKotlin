package com.hencoder.scalableimageview.view

import android.animation.ObjectAnimator
import android.content.Context
import android.gesture.Gesture
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import com.hencoder.scalableimageview.dp
import com.hencoder.scalableimageview.getAvatar
import kotlin.math.max
import kotlin.math.min

private val BITMAP_SIZE = 300.dp.toInt()
private const val EXTRA_SCALE_FRACTION = 1.5f

class CustomScalableImageView(context: Context?, attrs: AttributeSet?) : View(context, attrs)
    , GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener{

    private val bitmap = getAvatar(resources, BITMAP_SIZE)
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var originOffsetX = 0f
    private var originOffsetY = 0f
    private var offsetX = 0f
    private var offsetY = 0f
    private var smallScale = 0f
    private var bigScale = 0f;
    private val gestureDetector = GestureDetector(context, this).apply {
        setOnDoubleTapListener(this@CustomScalableImageView)
    }
    private var isScale = false
    private var scaleFraction = 0f
    set(value) {
        field = value
        invalidate()
    }

    private val scaleAnimator : ObjectAnimator by lazy {
        ObjectAnimator.ofFloat(this, "scaleFraction", 0f, 1f)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        originOffsetX = ((width - bitmap.width) / 2).toFloat()
        originOffsetY= ((height - bitmap.height) / 2).toFloat()

        if (width / bitmap.width > height / bitmap.height) {
            smallScale = height / bitmap.height.toFloat()
            bigScale = width / bitmap.width.toFloat() * EXTRA_SCALE_FRACTION
        } else {
            smallScale = width / bitmap.width.toFloat()
            bigScale = height / bitmap.height.toFloat() * EXTRA_SCALE_FRACTION
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return gestureDetector.onTouchEvent(event)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.translate(offsetX, offsetY)
        val scale = smallScale + scaleFraction * (bigScale - smallScale)
        canvas.scale(scale, scale, width / 2f, height / 2f)
        canvas.drawBitmap(bitmap, originOffsetX, originOffsetY, paint)
    }

    override fun onDown(e: MotionEvent?): Boolean {
        return true
    }

    override fun onShowPress(e: MotionEvent?) {
    }

    override fun onSingleTapUp(e: MotionEvent?): Boolean {
        return false
    }

    override fun onScroll(
        e1: MotionEvent?,
        e2: MotionEvent?,
        distanceX: Float,
        distanceY: Float
    ): Boolean {
        if (isScale) {
            offsetX -= distanceX
            offsetX = min(offsetX, (bitmap.width * bigScale - width) / 2)
            offsetX = max(offsetX, - (bitmap.width * bigScale - width) / 2)

            offsetY -= distanceY
            offsetY = min(offsetY, (bitmap.height * bigScale - height) / 2)
            offsetY = max(offsetY, - (bitmap.height * bigScale - height) / 2)
            invalidate()
        }

        return false
    }

    override fun onLongPress(e: MotionEvent?) {

    }

    override fun onFling(
        e1: MotionEvent?,
        e2: MotionEvent?,
        velocityX: Float,
        velocityY: Float
    ): Boolean {
        return false
    }

    override fun onSingleTapConfirmed(e: MotionEvent?): Boolean {
        return false
    }

    override fun onDoubleTap(e: MotionEvent?): Boolean {
        isScale = !isScale
        if (isScale) {
            scaleAnimator.start()
        } else {
            scaleAnimator.reverse()
        }
        return true
    }

    override fun onDoubleTapEvent(e: MotionEvent?): Boolean {
        return false
    }
}
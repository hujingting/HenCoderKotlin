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

private val BITMAP_SIZE = 300.dp.toInt()

class CustomScalableImageView(context: Context?, attrs: AttributeSet?) : View(context, attrs)
    , GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener{

    private val bitmap = getAvatar(resources, BITMAP_SIZE)
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
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

        offsetX = ((width - bitmap.width) / 2).toFloat()
        offsetY= ((height - bitmap.height) / 2).toFloat()

        if (width / bitmap.width > height / bitmap.height) {
            smallScale = height / bitmap.height.toFloat()
            bigScale = width / bitmap.width.toFloat()
        } else {
            smallScale = width / bitmap.width.toFloat()
            bigScale = height / bitmap.height.toFloat()
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return gestureDetector.onTouchEvent(event)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val scale = smallScale + scaleFraction * (bigScale - smallScale)
        canvas.scale(scale, scale, width / 2f, height / 2f)
        canvas.drawBitmap(bitmap, offsetX, offsetY, paint)
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
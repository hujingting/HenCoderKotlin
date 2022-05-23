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
import android.widget.OverScroller
import android.widget.Scroller
import androidx.core.view.ViewCompat
import com.hencoder.scalableimageview.dp
import com.hencoder.scalableimageview.getAvatar
import kotlin.math.max
import kotlin.math.min

private val BITMAP_SIZE = 300.dp.toInt()
private const val EXTRA_SCALE_FRACTION = 1.5f

class CustomScalableImageView(context: Context?, attrs: AttributeSet?) : View(context, attrs)
    , GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener, Runnable {

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

    //看成一个滑动的计算器
    //overScroller 比 scroller 更适合做惯性滑动，不信可以试试
    private val scroller = OverScroller(context)

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

    override fun onFling(
        e1: MotionEvent?,
        e2: MotionEvent?,
        velocityX: Float,
        velocityY: Float
    ): Boolean {
        if (isScale) {
            scroller.fling(
                offsetX.toInt(), offsetY.toInt(), velocityX.toInt(), velocityY.toInt(),
                (-(bitmap.width * bigScale - width) / 2).toInt(),
                ((bitmap.width * bigScale - width) /2).toInt(),
                (-(bitmap.height * bigScale - height) / 2).toInt(),
                ((bitmap.height *bigScale - height) / 2).toInt(),
                30.dp.toInt(), 30.dp.toInt()
            )

            //下一帧会调用动画
            ViewCompat.postOnAnimation(this, this)
        }
        return false
    }

    override fun run() {
        //如果动画还没结束，不断更新刷新页面
        if (scroller.computeScrollOffset()) {
            offsetX = scroller.currX.toFloat()
            offsetY = scroller.currY.toFloat()
            invalidate()
            //循环调用
            postOnAnimation(this)
        }
    }

    override fun onLongPress(e: MotionEvent?) {

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
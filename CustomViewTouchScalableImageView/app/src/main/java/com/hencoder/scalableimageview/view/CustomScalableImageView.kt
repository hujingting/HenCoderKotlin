package com.hencoder.scalableimageview.view

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import android.widget.OverScroller
import androidx.core.view.GestureDetectorCompat
import androidx.core.view.ScaleGestureDetectorCompat
import androidx.core.view.ViewCompat
import com.hencoder.scalableimageview.dp
import com.hencoder.scalableimageview.getAvatar
import kotlin.math.max
import kotlin.math.min

private val BITMAP_SIZE = 300.dp.toInt()
private const val EXTRA_SCALE_FRACTION = 1.5f

/**
 * 练习
 */
class CustomScalableImageView(context: Context?, attrs: AttributeSet?) : View(context, attrs),
    Runnable {

    private val bitmap = getAvatar(resources, BITMAP_SIZE)
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var originOffsetX = 0f
    private var originOffsetY = 0f
    private var offsetX = 0f
    private var offsetY = 0f
    private var smallScale = 0f
    private var bigScale = 0f;
    private val customGestureListener = CustomGestureListener()
    private val customScaleGestureListener = CustomScaleGestureListener()
    // ScaleGestureDetector 比 ScaleGestureDetectorCompat 功能更全面
    private val scaleGesture = ScaleGestureDetector(context, customScaleGestureListener)
    // GestureDetectorCompat 是 GestureDetector的兼容版本
    private val gestureDetector = GestureDetectorCompat(context, customGestureListener)
    private var isScale = false
    private var scaleFraction = 0f
        set(value) {
            field = value
            invalidate()
        }

    private val scaleAnimator: ObjectAnimator by lazy {
        ObjectAnimator.ofFloat(this, "scaleFraction", 0f, 1f)
    }

    //看成一个滑动的计算器
    //overScroller 比 scroller 更适合做惯性滑动，不信可以试试
    private val scroller = OverScroller(context)

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        originOffsetX = ((width - bitmap.width) / 2).toFloat()
        originOffsetY = ((height - bitmap.height) / 2).toFloat()

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

        //1. 图片放大之后相对应的坐标系也会变散，所以可以先执行 translate, 再放大图片，
        // 这样滑动图片才会跟手，否则需要考虑放大系数
        //2. 这里 * scaleFraction，跟着动画系数变化 ，这样缩小图片的时候才不会留白回到正常位置，
        // 相当于重置偏移量（scaleFraction = 0）
        canvas.translate(offsetX * scaleFraction, offsetY * scaleFraction)
        val scale = smallScale + scaleFraction * (bigScale - smallScale)
        canvas.scale(scale, scale, width / 2f, height / 2f)
        canvas.drawBitmap(bitmap, originOffsetX, originOffsetY, paint)
    }

    //配合快滑 onFling（）实现动画
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

    private fun fixOffsetXY() {
        offsetX = min(offsetX, (bitmap.width * bigScale - width) / 2)
        offsetX = max(offsetX, -(bitmap.width * bigScale - width) / 2)
        offsetY = min(offsetY, (bitmap.height * bigScale - height) / 2)
        offsetY = max(offsetY, -(bitmap.height * bigScale - height) / 2)
    }

    //抽出一个内部类
    inner class CustomGestureListener : GestureDetector.SimpleOnGestureListener() {

        override fun onDown(e: MotionEvent?): Boolean {
            return true
        }

        override fun onScroll(
            e1: MotionEvent?,
            e2: MotionEvent?,
            distanceX: Float,
            distanceY: Float
        ): Boolean {
            if (isScale) {
                offsetX -= distanceX
                offsetY -= distanceY
                //边缘修正（设置图片滑动边界，防止图片可以无限滑动）
                fixOffsetXY()
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
                    ((bitmap.width * bigScale - width) / 2).toInt(),
                    (-(bitmap.height * bigScale - height) / 2).toInt(),
                    ((bitmap.height * bigScale - height) / 2).toInt(),
                    30.dp.toInt(), 30.dp.toInt()
                )

                //下一帧会调用动画
                ViewCompat.postOnAnimation(
                    this@CustomScalableImageView,
                    this@CustomScalableImageView
                )
            }
            return false
        }

        override fun onDoubleTap(e: MotionEvent): Boolean {
            isScale = !isScale
            if (isScale) {
                //如果放大的初始偏移量 offsetX = 0，offsetY = 0，图片始终会是中心放大，
                // 所以通过修改偏移量可以达到点击图片任意位置都跟手放大
                offsetX = (e.x - width / 2) * (1 - bigScale / smallScale)
                offsetY = (e.y - height / 2) * (1 - bigScale / smallScale)
                //边缘修正, 否则在图片边缘放大会留白
                fixOffsetXY()
                scaleAnimator.start()
            } else {
                scaleAnimator.reverse()
            }
            return true
        }
    }

    inner class CustomScaleGestureListener : ScaleGestureDetector.OnScaleGestureListener{

        override fun onScale(detector: ScaleGestureDetector?): Boolean {
            return true
        }

        override fun onScaleBegin(detector: ScaleGestureDetector?): Boolean {
            return false
        }

        override fun onScaleEnd(detector: ScaleGestureDetector?) {

        }

    }
}



package com.hencoder.animation.practice

import android.content.Context
import android.graphics.Camera
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.hencoder.animation.dp
import com.hencoder.animation.getAvatar


private val MARGIN = 100.dp
private val BITMAP_W = 200.dp

class CameraView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val bitmap = getAvatar(resources, BITMAP_W.toInt())
    private val camera = Camera()

    var bottomClip = 30f
    set(value) {
        field = value
        invalidate()
    }

    var topClip = 0f
    set(value) {
        field = value
        invalidate()
    }

    var flipRotation = 0f
        set(value) {
            field = value
            invalidate()
        }

    init {
        camera.setLocation(0f, 0f, -8 * resources.displayMetrics.density)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        //上半部分
        canvas.save()
        canvas.translate(BITMAP_W /2 + MARGIN, BITMAP_W /2 + MARGIN)
        canvas.rotate(-flipRotation)
        canvas.save()
        camera.rotateX(topClip)
        camera.applyToCanvas(canvas)
        canvas.restore()
        canvas.clipRect(- BITMAP_W, - BITMAP_W, BITMAP_W, 0f)
        canvas.rotate(flipRotation)
        canvas.translate(- (BITMAP_W / 2 + MARGIN), - (BITMAP_W / 2 + MARGIN))
        canvas.drawBitmap(bitmap, MARGIN, MARGIN, paint)
        canvas.restore()

        //下半部分
        canvas.save()
        canvas.translate(BITMAP_W /2 + MARGIN, BITMAP_W /2 + MARGIN)
        canvas.rotate(-flipRotation)
        canvas.save()
        camera.rotateX(bottomClip)
        camera.applyToCanvas(canvas)
        canvas.restore()
        canvas.clipRect(-BITMAP_W, 0f, BITMAP_W, BITMAP_W)
        canvas.rotate(flipRotation)
        canvas.translate(- (BITMAP_W / 2 + MARGIN), - (BITMAP_W / 2 + MARGIN))
        canvas.drawBitmap(bitmap, MARGIN, MARGIN, paint)
        canvas.restore()
    }
}
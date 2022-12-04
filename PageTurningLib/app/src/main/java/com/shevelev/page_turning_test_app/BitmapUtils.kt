package com.shevelev.page_turning_test_app

import android.content.Context
import android.graphics.*
import android.view.View


fun getBitmap(view: View): Bitmap? {
    val screenWidth: Int = getScreenWidthSize(view.context)
    val screenHeight: Int = getScreenHeightSize(view.context)
    val widthSpec: Int = View.MeasureSpec.makeMeasureSpec(screenWidth, View.MeasureSpec.EXACTLY)
    val heightSpec: Int = View.MeasureSpec.makeMeasureSpec(screenHeight, View.MeasureSpec.EXACTLY)
    view.measure(widthSpec, heightSpec)
    view.layout(0, 0, screenWidth, screenHeight)
    val bitmap: Bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    val paint = Paint()
    paint.isAntiAlias = true
    val rect = Rect(0, 0, bitmap.width, bitmap.height)
    val rectF = RectF(rect)
    canvas.drawRoundRect(rectF, 20f, 20f, paint)
    paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
    canvas.drawBitmap(bitmap, rect, rect, paint)
    canvas.drawColor(Color.WHITE)
    view.draw(canvas)
    return bitmap
}

fun getScreenWidthSize(context: Context) :Int{
    val displayMetrics = context.resources.displayMetrics
    return displayMetrics.widthPixels
}

fun getScreenHeightSize(context: Context) :Int{
    val displayMetrics = context.resources.displayMetrics
    return displayMetrics.heightPixels
}


fun dpTopx(dp: Int, context: Context): Float {
    val metrics = context.getResources().getDisplayMetrics()
    return dp * metrics.density
}

fun pxToDp(px: Int, context: Context): Float {
    val metrics = context.getResources().getDisplayMetrics()
    return px / metrics.density
}
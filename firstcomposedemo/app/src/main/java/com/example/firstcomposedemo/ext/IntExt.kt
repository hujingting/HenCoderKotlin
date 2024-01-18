package com.example.firstcomposedemo.ext

import android.content.res.Resources
import android.util.TypedValue

fun Int.pxToDp(): Float {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_PX,
        this.toFloat(),
        Resources.getSystem().displayMetrics
    )
}
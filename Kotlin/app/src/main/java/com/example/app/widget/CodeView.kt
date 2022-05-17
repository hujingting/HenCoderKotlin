package com.example.app.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import androidx.appcompat.widget.AppCompatTextView
import com.example.app.R
import com.example.core.utils.dp2px
import kotlin.random.Random

class CodeView constructor(context: Context, attributeSet: AttributeSet?) : AppCompatTextView(context, attributeSet) {

    constructor(context: Context) : this(context, null)

    private val paint = Paint();

    private val codeList = mutableListOf(
        "kotlin",
        "android",
        "java",
        "http",
        "https",
        "okhttp",
        "retrofit",
        "tcp/ip")

    init {
        setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f);
        gravity = Gravity.CENTER;
        setBackgroundColor(getContext().getColor(R.color.colorPrimary));
        setTextColor(Color.WHITE);

        paint.isAntiAlias = true;
        paint.style = Paint.Style.STROKE;
        paint.color = getContext().getColor(R.color.colorAccent);
        paint.strokeWidth = dp2px(6f);

        updateCode();
    }

    fun updateCode() {
        val random = Random.nextInt(codeList.size);
        val code = codeList[random];
        text = code;
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawLine(0f, height.toFloat(), width.toFloat(), 0f, paint);
    }

}
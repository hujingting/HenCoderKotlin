package com.example.core.utils

import android.content.res.Resources
import android.util.DisplayMetrics
import android.util.TypedValue
import android.widget.Toast
import com.example.core.BaseApplication

//kotlin里的顶层函数
val displayMetrics: DisplayMetrics = Resources.getSystem().displayMetrics;

//fun dp2px(dp: Float): Float {
//    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, displayMetrics);
//}

//利用 kotlin 的扩展函数，在Float类里添加一个方法
fun Float.dp2px(): Float  = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this, displayMetrics);

//fun toast(string: String) {
//    toast(string, Toast.LENGTH_SHORT);
//}

//简化写法，使用函数参数默认值
fun toast(string: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(BaseApplication.currentApplication, string, duration).show();
}
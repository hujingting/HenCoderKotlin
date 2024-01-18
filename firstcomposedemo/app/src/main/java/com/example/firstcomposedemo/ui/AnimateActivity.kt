package com.example.firstcomposedemo.ui

import android.content.res.Resources
import android.os.Bundle
import android.util.TypedValue
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

class AnimateActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var big by mutableStateOf(false)

        setContent {

//            val size by animateDpAsState(if (big) 196.dp else 96.dp)

//            Box(
//                Modifier
//                    .size(size)
//                    .background(Color.Blue)
//                    .clickable {
//                        big = !big
//                    }
//            )

            val size = if (big) 196.dp else 96.dp

            val anim = remember { Animatable(size, Dp.VectorConverter) }

            //启动协程
            LaunchedEffect(big) {
//                delay(3000)
                //会直接变成指定的大小，无动画效果
//                anim.snapTo(if (big) 300.dp else 0.dp)
                
                anim.animateTo(if (big) 300.dp else 0.dp)
                anim.animateTo(size)
            }

            Box(
                Modifier
                    .size(anim.value)
                    .background(Color.Blue)
                    .clickable {
                        big = !big
                    }
            )
        }
    }
}
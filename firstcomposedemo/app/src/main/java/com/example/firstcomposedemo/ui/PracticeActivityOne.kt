package com.example.firstcomposedemo.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.material.Text
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

class PracticeActivityOne : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //使用 mutableStateListOf() 添加数据即可以实现赋值操作，自动刷新控件数据
//        val nums =  mutableStateListOf<Int>(1,2,3)
//
//        setContent {
//            Column {
//
//                Button(onClick = {
//                    nums.add(nums.last() + 1)
//                }) {
//                    Text(text = "加1")
//                }
//
//                for (num in nums) {
//                    Text(text = "数字是$num")
//                }
//            }
//        }

        setContent {
            var name by remember { mutableStateOf("bruce") }

            val processName by remember { derivedStateOf { name.uppercase() } }
//            val processName = remember(name) { name.uppercase() }
            
            Text(processName, modifier = Modifier.clickable {
                name = "jingting"
            })

        }
    }

}
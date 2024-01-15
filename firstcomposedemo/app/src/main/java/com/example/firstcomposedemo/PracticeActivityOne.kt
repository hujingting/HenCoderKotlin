package com.example.firstcomposedemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf

class PracticeActivityOne : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //添加数据即可以实现赋值操作，自动刷新控件数据
        val nums =  mutableStateListOf<Int>(1,2,3)

        setContent {
            Column {
                
                Button(onClick = {
                    nums.add(nums.last() + 1)
                }) {
                    Text(text = "加1")
                }
                
                for (num in nums) {
                    Text(text = "数字是$num")
                }
            }
        }
    }

}
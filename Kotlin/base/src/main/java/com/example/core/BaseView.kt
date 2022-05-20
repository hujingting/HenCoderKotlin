package com.example.core

interface BaseView<T> {

//    fun getPresenter() : T

    //kotlin 里可以直接定义抽象属性
    val p : T
}
package com.example.core

import android.app.Application
import android.content.Context
import androidx.annotation.NonNull

class BaseApplication : Application() {

    //用伴生对象来代替java里的static
    companion object {
        @JvmStatic
        @get:JvmName("currentApplication")//方便java代码调用
         lateinit var currentApplication : Context
         private set//保证外部只能调用get方法
//        @NonNull
//        fun currentApplication() : Context {
//            return currentApplication;
//        }
    }

    @Override
    override fun attachBaseContext(base : Context) {
        super.attachBaseContext(base);
        currentApplication = this;
    }
}
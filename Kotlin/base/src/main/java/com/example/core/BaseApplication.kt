package com.example.core

import android.app.Application
import android.content.Context
import androidx.annotation.NonNull

class BaseApplication : Application() {

    //用伴生对象来代替java里的static
    companion object {
        private lateinit var currentApplication : Context;

        @NonNull
        fun currentApplication() : Context {
            return currentApplication;
        }
    }

    @Override
    override fun attachBaseContext(base : Context) {
        super.attachBaseContext(base);
        currentApplication = this;
    }
}
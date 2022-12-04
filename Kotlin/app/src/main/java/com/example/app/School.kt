package com.example.app


class School<T> {

    private var student: T? = null

    fun get(): T? {
        return student
    }

    fun set(newInstance: T) {
        student = newInstance
    }
}
package com.example.app

import com.example.app.entity.User

fun main() : Unit {
    val user = User()
    val copy = user.copy()
    println(user)
    println(copy)
    println(user == copy)
    println(user === copy)
}
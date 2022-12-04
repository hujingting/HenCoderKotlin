package com.example.app

interface Shop<T> {

    fun buy() : T

    fun refund(item: T) : Float
}
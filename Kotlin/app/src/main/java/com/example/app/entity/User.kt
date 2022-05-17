package com.example.app.entity

data class User constructor(
    var username : String?,
    var password : String?,
    var code : String?) {

    constructor() : this(null, null, null)

    //通过 @JvmField 注解可以让编译器只⽣成⼀个 public 的成员属性，不⽣成对应的 setter/getter 函数
//    var username : String? = username;
//    var password : String? = null;
//    var code : String? = null;
//
//    constructor()
//
//    constructor(username : String?, password : String?, code : String?) {
//        this.username = username
//        this.password = password
//        this.code = code
//    }
}
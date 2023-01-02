package com.example.myapplication

data class Message(
    var message : String?,
    var sendId : String?
){
    // 기본생성자 생성
    constructor():this("","")
}

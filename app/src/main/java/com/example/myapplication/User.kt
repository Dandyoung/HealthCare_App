package com.example.myapplication
//db임. uId는 firebase 인증 데이터에 있는 uid에서 가져올꺼임 ㅇㅋ?
data class User(
    var name : String,
    var email : String,
    var uId : String
){
    constructor() : this("","","")
}

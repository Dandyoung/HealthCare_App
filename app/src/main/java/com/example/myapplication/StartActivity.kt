package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class StartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        var weight=findViewById<EditText>(R.id.weight) //몸무게
        var age=findViewById<EditText>(R.id.age)   //나이
        var height=findViewById<EditText>(R.id.height) //키
        var basicE=findViewById<TextView>(R.id.basicE) //기초대사량
        var agenum:String;
        var weightnum:String;
        var heightnum:String;
        var start =findViewById<Button>(R.id.start) //기록버튼
        var startman=findViewById<Button>(R.id.startman) //남자일때
        var startwoman=findViewById<Button>(R.id.startwoman) //여자일때
        var result:Double; //기초대사량 계산 값 받기


        //남자일때 기초대사량 계산 버튼
        startman.setOnClickListener{
            weightnum=weight.text.toString()
            agenum=age.text.toString()
            heightnum=height.text.toString()
            result=66.47+13.75*Integer.parseInt(weightnum)+5*Integer.parseInt(heightnum)-6.76*Integer.parseInt(agenum)
            basicE.text="당신의(남자) 기초대사량은: "+String.format("%.0f",result)
            false
        }
        //여자일때 기초대사량 계산 버튼
        startwoman.setOnClickListener{
            weightnum=weight.text.toString()
            agenum=age.text.toString()
            heightnum=height.text.toString()
            result=655.1+9.56*Integer.parseInt(weightnum)+1.85*Integer.parseInt(heightnum)-4.68*Integer.parseInt(agenum)
            basicE.text="당신의(여자) 기초대사량은: "+String.format("%.0f",result)
            false

        }

        //기록 버튼 SecondActivity 이동
        start.setOnClickListener{
            var intent=Intent(this,SecondActivity::class.java)
            startActivity(intent)
        }
    }
}
package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore.Audio.Radio
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast

class SecondActivity:Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        var backbtn=findViewById<Button>(R.id.backbtn)
        var kcal=findViewById<EditText>(R.id.kcal)
        var getkcal:String;
        var rgroup=findViewById<RadioGroup>(R.id.rgroup)
        var hard=findViewById<RadioButton>(R.id.hard)
        var normal=findViewById<RadioButton>(R.id.normal)
        var basic=findViewById<RadioButton>(R.id.basic)
        var easy=findViewById<RadioButton>(R.id.easy)
        var playkcal=findViewById<TextView>(R.id.playkcal)
        var result:Double;
        var addfood=findViewById<Button>(R.id.addfood)

        var intent=Intent(applicationContext,AddFood::class.java)



        hard.setOnClickListener{
            getkcal=kcal.text.toString()
            result=Integer.parseInt(getkcal)*2.0
            playkcal.text="당신의 활동대사량은:" + result.toString() + "입니다 체중감량을 원하시면 활동대사량에서 200kcal을 뺀 식단을 하시길 바랍니다"

            intent.putExtra("playkcal",result.toString())
            startActivity(intent)

        }
        normal.setOnClickListener{
            getkcal=kcal.text.toString()
            result=Integer.parseInt(getkcal)*1.5
            playkcal.text="당신의 활동대사량은:" + result.toString() + "입니다 체중감량을 원하시면 활동대사량에서 200kcal을 뺀 식단을 하시길 바랍니다"

            intent.putExtra("playkcal",result.toString())
            startActivity(intent)
        }
        basic.setOnClickListener{
            getkcal=kcal.text.toString()
            result=Integer.parseInt(getkcal)*1.3
            playkcal.text="당신의 활동대사량은:" + result.toString() + "입니다 체중감량을 원하시면 활동대사량에서 200kcal을 뺀 식단을 하시길 바랍니다"
            intent.putExtra("playkcal",result.toString())
            startActivity(intent)
        }
        easy.setOnClickListener{
            getkcal=kcal.text.toString()
            result=Integer.parseInt(getkcal)*1.2
            playkcal.text="당신의 활동대사량은:" + result.toString() + "입니다 체중감량을 원하시면 활동대사량에서 200kcal을 뺀 식단을 하시길 바랍니다"
            intent.putExtra("playkcal",result.toString())
            startActivity(intent)
        }
        addfood.setOnClickListener{
            var intent:Intent=Intent(this,AddFood::class.java)
            startActivity(intent)
        }
        backbtn.setOnClickListener{
            finish()
        }
    }
}
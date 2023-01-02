package com.example.myapplication


import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.nfc.Tag
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class AddFood :AppCompatActivity(){

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.food)

        var foodDB: FoodDB
        var sqlDB:SQLiteDatabase

        var foodkcal=findViewById<EditText>(R.id.foodkcal)
        var foodid=findViewById<EditText>(R.id.foodid)
        var addTBL=findViewById<Button>(R.id.addTBL)
        var back = findViewById<Button>(R.id.back)
        var btninit=findViewById<Button>(R.id.btninit)
        var TBLshow=findViewById<Button>(R.id.TBLshow)
        var gochat = findViewById<Button>(R.id.chat)
        var showid=findViewById<TextView>(R.id.showid)
        var showkcal=findViewById<TextView>(R.id.showkcal)
        var remainkcal=findViewById<TextView>(R.id.remainkcal)
        var remain:String
        var foodcal:String
        var getkcal:String
        var calkcal=findViewById<Button>(R.id.calkcal)
        back.setOnClickListener {
            finish()
        }
        foodDB= FoodDB(this)
        btninit.setOnClickListener{
            sqlDB=foodDB.writableDatabase
            foodDB.onUpgrade(sqlDB,1,2)

            sqlDB.close()
            Toast.makeText(applicationContext,"음식리스트 초기화",Toast.LENGTH_SHORT).show()
        }
        addTBL.setOnClickListener{
            sqlDB=foodDB.writableDatabase
            sqlDB.execSQL("INSERT INTO foodTBL VALUES('"
                    +foodid.text.toString()+"',"
                    +foodkcal.text.toString()+");")
            sqlDB.close()
            Toast.makeText(applicationContext,"추가됨",Toast.LENGTH_SHORT).show()
        }
        TBLshow.setOnClickListener{
            sqlDB=foodDB.readableDatabase
            var cursor:Cursor

            cursor=sqlDB.rawQuery("SELECT*FROM foodTBL;",null)

            var foodname="먹은 음식은"+"\r\n"
            var kcal="칼로리" + "\r\n"

            while (cursor.moveToNext()){
                foodname+=cursor.getString(0)+"\r\n"
                kcal+=cursor.getString(1)+"\r\n"
            }
            showid.setText(foodname)
            showkcal.setText(kcal)
            cursor.close()
            sqlDB.close()
        }
        foodcal=foodkcal.text.toString()

        calkcal.setOnClickListener{
            getkcal=intent.getStringExtra("playkcal").toString()
            remainkcal.text="잔여 칼로리: "+getkcal
            false

        }

        gochat.setOnClickListener{
            Toast.makeText(this, "외롭냐?", Toast.LENGTH_SHORT).show()
            val intent: Intent = Intent(this@AddFood, MainActivity::class.java)
            startActivity(intent)
        }


    }
    inner class FoodDB(context: Context): SQLiteOpenHelper(context,"FoodDB",null,1){
        override fun onCreate(db: SQLiteDatabase?) {

            db!!.execSQL("CREATE TABLE foodTBL(foodid CHAR(20) PRIMARY KEY, foodkcal INTEGER);")
        }

        override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

            db!!.execSQL("DROP TABLE IF EXISTS foodTBL")
            onCreate(db)
        }
    }


}

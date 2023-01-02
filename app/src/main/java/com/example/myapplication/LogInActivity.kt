package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.myapplication.databinding.ActivityLogInBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LogInActivity : AppCompatActivity() {

    lateinit var binding: ActivityLogInBinding
    lateinit var mAuth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 객체 초기화
        binding = ActivityLogInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 로그인 떄 인증 초기화 해야하기 떄문에 인증 초기화
        mAuth = Firebase.auth

        //바인딩 설정을 하지 않으면 기존 방법처럼 버튼 객체를 만들어야 함. 이게 더 편하니까 이거써라
        //val SignUpBtn : Button = findViewById(R.id.signUp_btn)
        // 회원가입 버튼 이벤트

        binding.signUpBtn.setOnClickListener{
            val intent: Intent = Intent(this@LogInActivity, SignUpActivity::class.java)
            startActivity(intent)
        }

        // 로그인 버튼 이벤트
        binding.loginBtn.setOnClickListener{
            // 사용자가 입력한 정보를 담을 변수 선언
            val email = binding.emailEdit.text.toString()
            val password = binding.passwordEdit.text.toString()
            login(email, password)

        }
    }
    // 로그인 함수
    // 정보들은 Firebass의 realtimeDatabase에 저장할거임 여기서 관리 안해도됨 ㅎㅎ

    private fun login(email: String, password : String){
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // 성공 시 실행
                    val intent: Intent = Intent(this@LogInActivity, StartActivity::class.java)
                    startActivity(intent)
                    Toast.makeText(this,"로그인 성공!",Toast.LENGTH_SHORT).show()
                    //굳이 안해줘도되는데 이번에 배운것 처럼 메모리 관리 상 종료해주는게 좋은 것 같음
                    finish()

                } else {
                    // 실패 시 실행
                    Toast.makeText(this,"로그인 실패..",Toast.LENGTH_SHORT).show()
                    // 로그인 실패시 어떤 에러로 실패했는지 로그로 뿌려줌
                    // 무슨 에러인지는 task.exception으로 알 수 있다. ㅇㅋ?
                    Log.d("Login", "Error : ${task.exception}")

                }
            }
    }

}
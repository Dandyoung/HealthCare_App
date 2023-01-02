package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.myapplication.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class SignUpActivity : AppCompatActivity() {
    // 인증을 위한 객체들
    lateinit var binding: ActivitySignUpBinding
    lateinit var  mAuth: FirebaseAuth

    // 데이터베이스 객체 생성
    private lateinit var mDbRef : DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //인증을 위한 binding 객체 초기화
        mAuth = Firebase.auth

        //db 초기화
        mDbRef = Firebase.database.reference

        binding.signUpBtn.setOnClickListener{
            // 정보를 담은 변수 선언. trim을 써서 공백제거 디테일 ㅇㅈ?
            val name = binding.nameEdit.text.toString().trim()
            val email = binding.emailEdit.text.toString().trim()
            val password = binding.passwordEdit.text.toString().trim()

            signUp(name, email, password)

        }
    }

    // 회원가입 함수
    private fun signUp(name : String, email : String, password : String){
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // 성공시 실행. 메인으로 넘겨야겠쥐?
                    Toast.makeText(this, "회원가입 성공!", Toast.LENGTH_SHORT).show()
                    val intent: Intent = Intent(this@SignUpActivity, StartActivity::class.java)
                    startActivity(intent)
                    // 이부분 문서를 읽어도 이해는 안가는데, 한번 보는게 좋을듯?
                    addUserToDatabase(name, email, mAuth.currentUser?.uid!!)
                } else {
                    // 실패시 실행
                    //패스워드는 최소 6글자 이상 지켜주셈
                    Toast.makeText(this, "회원가입 실패ㅋㅋ", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun addUserToDatabase(name : String, email : String, uId : String){
        mDbRef.child("usre").child(uId).setValue(User(name, email, uId))
    }


}
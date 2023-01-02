package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    // 바인딩 객체 선언
    lateinit var binding : ActivityMainBinding
    lateinit var adapter: UserAdapter

    private lateinit var mAuth : FirebaseAuth
    private lateinit var mDbRef : DatabaseReference
    private lateinit var userList : ArrayList<User>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 인증 초기화
        mAuth = Firebase.auth
        // db 초기화
        mDbRef = Firebase.database.reference

        userList = ArrayList()
        //데이터는 Useradapter에서 넘겨받음(생성자)
        adapter = UserAdapter(this, userList)


        binding.userRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.userRecyclerView.adapter = adapter

        // realtimedatabass 사용자 정보 가져오기
        mDbRef.child("usre").addValueEventListener(object:ValueEventListener{

            // 데이터 변경되면 실행 ㅇㅋ?
            override fun onDataChange(snapshot: DataSnapshot) {
                for(postSnapshot in snapshot.children){

                    val currentUser = postSnapshot.getValue(User::class.java)

                    // 인증 서비스 객체를 통해 현재 로그인한 사용자 uid를 알 수 있음
                    //본인을 제외한 나머지 사람들 정보만 보여야댐
                    // 로그인한 아이디와 현재 uId가 다르다면 userList에 추가
                    if(mAuth.currentUser?.uid != currentUser?.uId){
                        userList.add(currentUser!!)
                    }
                }
                //notifyDataSetChanged를 통해서 들어온 데이터가 실제 화면에 적용
                adapter.notifyDataSetChanged()
            }
            // 에러가 발생하면 실행 ㅇㅋ?
            override fun onCancelled(error: DatabaseError) {

            }

        })

    }//onCreate

    //메뉴를 보여주기 위한 onCreateOptionMenu 함수 재정의
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        //메인화면에 메뉴 연결
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    // 로그아웃하면 로그인화면으로 돌려보내주는 기능
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.log_out){
            //signOut을 통해 인증서에서 로그아웃할 수 있음.
            mAuth.signOut()
            val intent = Intent(this@MainActivity, LogInActivity::class.java)
            startActivity(intent)
            finish()
            return true
        }
        return true
    }


}


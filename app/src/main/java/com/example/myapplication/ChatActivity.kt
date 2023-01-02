package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.databinding.ActivityChatBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class ChatActivity : AppCompatActivity() {

    // 대화할 상대를 선택하면 이름과 아이디를 담을 변수 설정
    private lateinit var receiverName : String
    private lateinit var receiverUid: String

    // 바인딩 객체 만들기
    private lateinit var binding : ActivityChatBinding
    //인증객체
    lateinit var mAuth: FirebaseAuth
    //디비객체
    lateinit var mDbRef : DatabaseReference

    //받는쪽 대화방과 보내는 쪽 대화방 만들기
    private lateinit var receiverRoom : String //받는쪽
    private lateinit var senderRoom : String //보내는쪽

    //메세지 담을 리스트 생성
    private lateinit var messageList : ArrayList<Message>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //메세지 리스트 초기화
        messageList = ArrayList()

        //메세지 어댑터 초기화 ㅎ
        val messageAdapter : MessageAdapter = MessageAdapter(this, messageList)

        //RecyclerView
        binding.chatRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.chatRecyclerView.adapter = messageAdapter

        //넘어온 데이터 변수에 담기, string으로 받기 떄문에 getStringExtra에 담으면 됨
        receiverName = intent.getStringExtra("name").toString()
        receiverUid = intent.getStringExtra("uId").toString()

        //인증객체 및 db에 있는 reference 받아오기
        mAuth = FirebaseAuth.getInstance()
        mDbRef = FirebaseDatabase.getInstance().reference

        //접속자 uid 받는 변수
        val senderUid = mAuth.currentUser?.uid

        //메세지를 보내는 사람 방
        senderRoom = receiverUid + senderUid
        //메세지를 받는 사람 방
        receiverRoom = senderUid + receiverUid

        //전송 버튼 기능
        //입력한 메세지는 db에 저장되고 db에 저장된 메세지를 화면에 보내줌
        binding.sendBtn.setOnClickListener{
            //입력한 메세지를 변수에 담고
            val message = binding.messageEdit.text.toString()
            // messageObject라는 변수 안에다 Message 클래스 형식을 넣음
            val messageObject = Message(message, senderUid)

            //데이터 저장 수행
          /*  0. 메세지를 보냄(보낸이)
            1. chat이라는 db를 만들고
            2. chat이라는 db 하위에 messages db를 만든다.
            3. messages db 안에 대화내용이 저장됨
            4. 이 부분이 성공되면 받는 이한테도 저장시키면됨*/
            mDbRef.child("chats").child(senderRoom).child("messages").push().
            setValue(messageObject).addOnSuccessListener {
                //저장에 성공한다면? 상대방 방에도 보내줌
                mDbRef.child("chats").child(receiverRoom).child("messages").push().
                setValue(messageObject)
            }

            // 메세지를 전송하고 입력 부분을 초기화(안해되는데 함)
            binding.messageEdit.setText("")

        }


        //db 객체를 이용해서 메세지 가져오기(위 순서와 같다)
        //chat >> senderRoom >> messages 안에 있는 데이터가 snapshot안에 있다.
        //이 snapshot의 데이터를 postSnapshot에 담아 반복문을 돌려 값을 message에 넣어
        //messageList에 담는다.
        //마지막으로 notifyDataSetChanged()를 실행시키면 메세지가 보이게 된다.
        mDbRef.child("chats").child(senderRoom).child("messages").
        addValueEventListener(object: ValueEventListener{
            // 데이터 변경 시 실행. 여기에 메세지 가져오는 기능 구현하면 됨
            override fun onDataChange(snapshot: DataSnapshot) {
                // 실행이되면 빈값으로 만들어 놓고
                messageList.clear()

                for(postSnapshat in snapshot.children){
                    val message = postSnapshat.getValue(Message::class.java)
                    messageList.add(message!!)
                }
                //적용
                messageAdapter.notifyDataSetChanged()
            }
            //오류 발생 시 실행.
            override fun onCancelled(error: DatabaseError) {
            }

        })


        //액션바에 상대방 이름 보여주기(사소하지만 카카오톡과 비슷하게 하려고)
        supportActionBar?.title = receiverName

    }
}
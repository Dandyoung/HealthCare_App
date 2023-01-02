package com.example.myapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import org.w3c.dom.Text

// 만약에 ViewHolder가 하나 사용한다면, 그냥 쓰겠지만 우리는 보낸쪽, 받는쪽 화면이 필요하기 떄문에
// RecyclerView.ViewHolder를 사용함!
class MessageAdapter(private val context: Context, private val messageList : ArrayList<Message>) :
 RecyclerView.Adapter<RecyclerView.ViewHolder>(){


    // SendViewHolder 보낸 쪽 화면을 전달받아 객체를 만듬
    class SendViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val sendMessage : TextView = itemView.findViewById(R.id.send_message_text)
    }
    // ReceiveViewHolder 받는 쪽 화면을 전달받아 객체를 만듬
    class ReceiveViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val receiveMessage : TextView = itemView.findViewById(R.id.receive_message_text)
    }

    private val receive = 1 // 받는 타입
    private val send = 2  // 보내는 타입

    // 이 함수를 사용해서 어떤 뷰를 사용할지 정함
    override fun getItemViewType(position: Int): Int {
        // 메세지 값
        val currentMessage = messageList[position]
        // 전달된 메세지를 currentmessage를 담아서 접속한 uid와  senduid가 같ㅇ다면 send 리턴 아니면
        // receive 리턴
        return if(FirebaseAuth.getInstance().currentUser?.uid.equals(currentMessage.sendId)){
            send
        }else{
            receive
        }
    }

    // 화면 연결. 위에서 return된 값이 viewType에 담겨져있음
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if(viewType == 1){ // 받는쪽이면 receiveViewHolder를 불러 객체가 생성됨
            val view : View = LayoutInflater.from(context).inflate(R.layout.receive, parent,false)
            ReceiveViewHolder(view)
        }else{
            val view : View = LayoutInflater.from(context).inflate(R.layout.send, parent,false)
            SendViewHolder(view)
        }
    }

    // 데이터 연결
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentMessage = messageList[position]

        if(holder.javaClass == SendViewHolder::class.java){
            val viewHolder = holder as SendViewHolder
            // hoder값을 sendviewholder 타입으로 바꿔서 viewHolder에 담음
            viewHolder.sendMessage.text = currentMessage.message
            //그러면 이렇게 viewHolder로 sendMessage(textview)에 접근을 해서
            // currentMessage의 message를 보여주게 되는 것임.
        }else{
            val viewHolder = holder as ReceiveViewHolder
            viewHolder.receiveMessage.text = currentMessage.message
        }

    }

    // messageList size를 return
    override fun getItemCount(): Int {
        return messageList.size
    }
}
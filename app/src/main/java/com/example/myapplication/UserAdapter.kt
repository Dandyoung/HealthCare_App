package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

// 자기 자신한테 자기 밑에 클래스를 덮어씌울 수 있구나,, ㄷㄷ
// 이부분 이해하기 좀 어려움 나도 찾아보고 구현한거라
// 여튼

class UserAdapter(private val context: Context,private val userList :ArrayList<User>) :
    RecyclerView.Adapter<UserAdapter.UserViewHolder>(){

    //Userlayout을 연결하는 기능
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view: View = LayoutInflater.from(context).
        inflate(R.layout.user_layout, parent, false)

        return UserViewHolder(view)
    }



    //화면 설정 : 데이터를 전달받아 user_layout에 보여주는 기능
    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {

        //데이터 담아서
        val currentUser = userList[position]

        // 화면에 보여주기
        holder.nameText.text = currentUser.name

        //상대방을 클릭하면 대화방으로 이동하는 버튼
        holder.itemView.setOnClickListener{
            val intent = Intent(context, ChatActivity::class.java)

            // 넘길 데이터 : 그냥 intent안에 넣어서 넘겨주면됨
            intent.putExtra("name",currentUser.name)
            intent.putExtra("uId", currentUser.uId)
            context.startActivity(intent)

        }
    }

    //데이터 설정 : 리스트의 갯수(size)를 돌려주는 기능
    override fun getItemCount(): Int {
        return userList.size
    }

    //클래스를 통해 뷰를 받아서 layout 텍스트퓨 객체를 만드려고 만든 클래스
    //RecyclerView.ViewHolder를 상속받아서 만든 클래스임
    class UserViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        val nameText: TextView = itemView.findViewById(R.id.name_text)

    }

}
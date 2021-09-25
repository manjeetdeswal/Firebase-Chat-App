package com.mddstudio.chatfirebase.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.mddstudio.chatfirebase.activity.ChatActivity
import com.mddstudio.chatfirebase.databinding.UserLayBinding
import com.mddstudio.chatfirebase.model.User

class UserAdapter(val context: Context,val userlist:ArrayList<User>): RecyclerView.Adapter<UserAdapter.UserViewHolfer>() {


    class UserViewHolfer(binding: UserLayBinding):RecyclerView.ViewHolder(binding.root){
        val username=binding.usertv
        val email=binding.lasttv
        var layout=binding.userlayout

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolfer {
     val binding=UserLayBinding.inflate(LayoutInflater.from(context),parent,false)
        return UserViewHolfer(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolfer, position: Int) {
        val user=userlist.get(position)
        holder.username.text=user.email
        holder.email.text=user.name
        holder.layout.setOnClickListener {
            val intent =Intent(context,ChatActivity::class.java)
            intent.putExtra("name",user.email)
            intent.putExtra("uid",user.uid)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
       return userlist.size
    }
}
package com.mddstudio.chatfirebase.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.mddstudio.chatfirebase.databinding.ReceiverBinding

import com.mddstudio.chatfirebase.databinding.SenderBinding
import com.mddstudio.chatfirebase.model.Chat

class ChatAdapter(val context: Context, val chatlist: ArrayList<Chat>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val RECIVERCHAT = 1
    val SENDERCHAT = 2

    class ReceiverViewHolder(binding: ReceiverBinding) : RecyclerView.ViewHolder(binding.root) {
        val receivedmsg = binding.recievetv
    }

    class SenderViewHolder(binding: SenderBinding) : RecyclerView.ViewHolder(binding.root) {
        val sendermsg = binding.sendertv

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType ==1){
val binding =ReceiverBinding.inflate(LayoutInflater.from(context),parent,false)
            return ReceiverViewHolder(binding)
        }else{
            val binding =SenderBinding.inflate(LayoutInflater.from(context),parent,false)
            return SenderViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val msg = chatlist[position]

        if (holder.javaClass == SenderViewHolder::class.java) {
            val holder = holder as SenderViewHolder
            holder.sendermsg.text = msg.message

        }
        if (holder.javaClass == ReceiverViewHolder::class.java) {
            val holder = holder as ReceiverViewHolder
            holder.receivedmsg.text = msg.message

        }
    }

    override fun getItemViewType(position: Int): Int {
        val msg= chatlist[position]
        if (FirebaseAuth.getInstance().currentUser?.uid ==msg.sender){
            return SENDERCHAT
        }
        else{
            return RECIVERCHAT
        }
    }
    override fun getItemCount(): Int {
      return chatlist.size
    }
}
package com.mddstudio.chatfirebase.activity

import android.icu.text.Transliterator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.mddstudio.chatfirebase.R
import com.mddstudio.chatfirebase.adapter.ChatAdapter
import com.mddstudio.chatfirebase.databinding.ActivityChatBinding
import com.mddstudio.chatfirebase.model.Chat

class ChatActivity : AppCompatActivity() {
    var recieveroom: String? = null
    var senderrrom: String? = null
    private lateinit var reference: DatabaseReference
    private lateinit var chatAdapter: ChatAdapter
    private lateinit var chatlist: ArrayList<Chat>
    private lateinit var binding: ActivityChatBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)
        reference =FirebaseDatabase.getInstance().getReference()
        val name = intent.getStringExtra("name")
        val reciveduid = intent.getStringExtra("uid")
        val senderuid = FirebaseAuth.getInstance().currentUser?.uid
        senderrrom = reciveduid + senderuid
        recieveroom = senderuid + reciveduid
        supportActionBar?.title = name

        chatlist = ArrayList()
        chatAdapter = ChatAdapter(this, chatlist)




        binding.apply {


            reference.child("chat").child(senderrrom!!)
                .child("message").addValueEventListener(object :ValueEventListener{
                    override fun onDataChange(snapshot: DataSnapshot) {
                        chatlist.clear()
                       for (chat in snapshot.children){
                           val chat =chat.getValue(Chat::class.java)
                           chatlist.add(chat!!)
                       }
                        chatAdapter.notifyDataSetChanged()
                    }

                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }

                })

            val linearLayoutManager=LinearLayoutManager(this@ChatActivity)



            recyleview.layoutManager =linearLayoutManager

            recyleview.adapter = chatAdapter
            recyleview.postDelayed({
                recyleview.scrollToPosition(chatAdapter.itemCount - 1)
            }, 1000)
            chatAdapter.notifyDataSetChanged()

            recyleview.smoothScrollToPosition(recyleview.getAdapter()?.getItemCount()!!);

            floatingActionButton.setOnClickListener {

                val message = sendtext.text.toString()
                val chat= Chat(message,senderuid)
                reference.child("chat").child(senderrrom!!)
                    .child("message").push()
                    .setValue(chat).addOnSuccessListener {
                        reference.child("chat").child(recieveroom!!)
                            .child("message").push()
                            .setValue(chat)
                    }
                recyleview.postDelayed({
                    recyleview.scrollToPosition(chatAdapter.itemCount - 1)
                }, 1000)

             //   recyleview.scrollToPosition(chatAdapter.itemCount -1)
                sendtext.setText("")
            }

        }
    }
}
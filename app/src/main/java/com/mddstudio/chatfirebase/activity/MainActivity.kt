package com.mddstudio.chatfirebase.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.mddstudio.chatfirebase.R
import com.mddstudio.chatfirebase.adapter.UserAdapter
import com.mddstudio.chatfirebase.databinding.ActivityMainBinding
import com.mddstudio.chatfirebase.model.User
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var userAdapter: UserAdapter
    private lateinit var userlist: ArrayList<User>
    private lateinit var mauth: FirebaseAuth
    private lateinit var reference: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        reference=FirebaseDatabase.getInstance().getReference()

        mauth= FirebaseAuth.getInstance()

        userlist= ArrayList<User>()


        userAdapter=UserAdapter(this, userlist)
        binding.apply {
            recyleview.layoutManager=LinearLayoutManager(this@MainActivity)
            recyleview.adapter=userAdapter
            reference.child("user").addValueEventListener(object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    userlist.clear()
                    for (ussnapsot in snapshot.children){
                        val user=ussnapsot.getValue(User::class.java)
                        if (mauth.currentUser?.uid != user?.uid){
                            userlist.add(user!!)
                        }

                    }
                    userAdapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {
                   Toast.makeText(this@MainActivity,"Check internet connection",Toast.LENGTH_LONG).show()
                }

            })


        }


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_log,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId ==R.id.logout){
            mauth.signOut()
            startActivity(Intent(this, LoginActivity::class.java))

        }
        return super.onOptionsItemSelected(item)
    }
}
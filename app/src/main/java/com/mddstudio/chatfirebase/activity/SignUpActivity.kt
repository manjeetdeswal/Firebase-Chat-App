package com.mddstudio.chatfirebase.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.mddstudio.chatfirebase.databinding.ActivitySignUpBinding
import com.mddstudio.chatfirebase.model.User

class SignUpActivity : AppCompatActivity() {
   private lateinit var binding: ActivitySignUpBinding
    private lateinit var mauth: FirebaseAuth
    private lateinit var mreferenc:DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding=ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mauth= FirebaseAuth.getInstance()


        binding.apply {
            login.setOnClickListener {
               val intent=Intent(this@SignUpActivity, LoginActivity::class.java)
                startActivity(intent)
            }

            buttonSignup.setOnClickListener {
                val name= textField.editText?.text .toString()
                val email= textField2.editText?.text .toString()
                val passwored=textField3.editText?.text.toString()
                singUp(name,email,passwored)
            }
        }

    }

    private fun singUp(name:String,email:String,password:String){
        mauth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {

                    adduser(name,email, mauth.currentUser?.uid.toString())
                    val user = mauth.currentUser


                    val intent=Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()

                } else {
                    Toast.makeText(this," $email already exist",Toast.LENGTH_LONG).show()

                }
            }
    }

    private fun adduser(name:String,email:String,uid:String){
mreferenc=FirebaseDatabase.getInstance().getReference()
        mreferenc.child("user").child(uid).setValue(User(name,email,uid))
    }
}
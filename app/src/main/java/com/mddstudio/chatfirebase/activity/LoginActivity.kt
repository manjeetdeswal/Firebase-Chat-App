package com.mddstudio.chatfirebase.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.mddstudio.chatfirebase.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var mauth:FirebaseAuth
    private lateinit var bining:ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bining=ActivityLoginBinding.inflate(layoutInflater)
        setContentView(bining.root)
        mauth= FirebaseAuth.getInstance()


        val currentUser = mauth.currentUser
        if(currentUser != null){
            bining.relative.visibility=View.GONE
            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
        }else{
            bining.relative.visibility=View.VISIBLE
        }
        bining.apply {
            signup.setOnClickListener {
                startActivity(Intent(this@LoginActivity, SignUpActivity::class.java))
            }

            buttonlogin.setOnClickListener {
               val email= textField.editText?.text .toString()
                val passwored=textField3.editText?.text.toString()
                login(email,passwored)
                Toast.makeText(this@LoginActivity,email+passwored,Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun  login(email:String,password:String){
        mauth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = mauth.currentUser
                    // Sign in success, update UI with the signed-in user's information
                    val intent=Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(this@LoginActivity,"Error Occured",Toast.LENGTH_LONG).show()

                }
            }
    }
}
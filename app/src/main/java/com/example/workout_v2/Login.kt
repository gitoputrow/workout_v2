package com.example.workout_v2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val database = FirebaseDatabase.getInstance().getReference()
        findViewById<TextView>(R.id.textView_regist).setOnClickListener {
            startActivity(Intent(this,register::class.java))
        }
        findViewById<Button>(R.id.Login_but).setOnClickListener {
            if (findViewById<TextInputEditText>(R.id.usernameinput).text.toString().isNotEmpty() &&
                    findViewById<TextInputEditText>(R.id.passwordinput).text.toString().isNotEmpty()){
                var username = findViewById<TextInputEditText>(R.id.usernameinput).text.toString()
                var passwword = findViewById<TextInputEditText>(R.id.passwordinput).text.toString()
                database.addListenerForSingleValueEvent(object : ValueEventListener{
                    override fun onCancelled(error: DatabaseError) {

                    }

                    override fun onDataChange(snapshot: DataSnapshot) {
                       if (snapshot.hasChild("data$username")){
                           if (snapshot.child("data$username").child("password").value.toString().equals(passwword)){
                               val pindah = Intent(this@Login,MainActivity::class.java)
                               pindah.putExtra("username",username)
                               pindah.putExtra("current","workout")
                               finish()
                               startActivity(pindah)
                           }
                           else{
                               Toast.makeText(baseContext,"Wrong Password",Toast.LENGTH_SHORT).show()
                           }
                       }
                       else{
                           Toast.makeText(baseContext,"Username not Found",Toast.LENGTH_SHORT).show()
                       }
                    }
                })
            }
            else{
                Toast.makeText(baseContext,"Please fill all the Data",Toast.LENGTH_SHORT).show()
            }
        }
    }
}
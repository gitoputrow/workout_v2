package com.example.workout_v2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        findViewById<TextView>(R.id.textView_regist).setOnClickListener {
            startActivity(Intent(this,register::class.java))
        }
        findViewById<Button>(R.id.Login_but).setOnClickListener {
            startActivity(Intent(this,MainActivity::class.java))
        }
    }
}
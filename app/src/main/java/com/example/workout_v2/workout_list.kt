package com.example.workout_v2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class workout_list : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_workout_list)
        findViewById<Button>(R.id.button_easy).setOnClickListener {
            startActivity(Intent(this,Workout_MainActivity::class.java))
        }
    }
}
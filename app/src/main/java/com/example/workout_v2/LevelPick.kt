package com.example.workout_v2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class LevelPick : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_level_pick)
        findViewById<ImageView>(R.id.back_level).setOnClickListener {
            startActivity(Intent(this,MainActivity::class.java))
        }
        findViewById<ImageView>(R.id.easy).setOnClickListener {
            startActivity(Intent(this,DayActivity::class.java))
        }
    }
}
package com.example.workout_v2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class workout_list_med : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_workout_list_med)
        val ambil = intent
        findViewById<ImageView>(R.id.back_workoutlist_med).setOnClickListener {
            back(ambil)
        }
    }
    fun back(ambil : Intent){
        var ahli = Intent(this,DayActivity::class.java)
        ahli.putExtra("username",ambil.getStringExtra("username").toString())
        ahli.putExtra("muscle",ambil.getStringExtra("muscle").toString())
        ahli.putExtra("level",ambil.getStringExtra("level").toString())
        finish()
        startActivity(ahli)
    }
}
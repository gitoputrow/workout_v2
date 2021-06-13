package com.example.workout_v2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class LevelPick : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_level_pick)
        val ambil = intent

        findViewById<ImageView>(R.id.back_level).setOnClickListener {
            val ahli = Intent(this,MainActivity::class.java)
            ahli.putExtra("username",ambil.getStringExtra("username").toString())
            startActivity(ahli)
        }
        findViewById<ImageView>(R.id.easy).setOnClickListener {
            pindah(ambil,"easy")
        }
        findViewById<ImageView>(R.id.medium).setOnClickListener {
            pindah(ambil,"medium")
        }
        findViewById<ImageView>(R.id.hard).setOnClickListener {
            pindah(ambil,"hard")
        }
    }
    fun pindah(ambil : Intent,level : String) {
        val ahli = Intent(this,DayActivity::class.java)
        ahli.putExtra("username",ambil.getStringExtra("username").toString())
        ahli.putExtra("muscle",ambil.getStringExtra("muscle").toString())
        ahli.putExtra("level",level)
        startActivity(ahli)
    }
}
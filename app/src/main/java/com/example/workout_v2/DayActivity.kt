package com.example.workout_v2

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView

class DayActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_day)
        val ambil = intent
        findViewById<ImageView>(R.id.day1).setOnClickListener {
            pindah(ambil,"1")
        }
        findViewById<TextView>(R.id.workout_name).setText(ambil.getStringExtra("muscle").toString())
    }
    fun pindah(ambil : Intent,day : String){
        var ahli = Intent()
        if (ambil.getStringExtra("level").toString().equals("easy")){
            ahli = Intent(this,workout_list::class.java)
        }
        else if (ambil.getStringExtra("level").toString().equals("medium")){
            ahli = Intent(this,workout_list_med::class.java)
        }
        else if (ambil.getStringExtra("level").toString().equals("hard")){
            ahli = Intent(this,workout_list_hard::class.java)
        }
        ahli.putExtra("name",ambil.getStringExtra("name").toString())
        ahli.putExtra("muscle",ambil.getStringExtra("muscle").toString())
        ahli.putExtra("day",day)
        startActivity(ahli)
    }
}
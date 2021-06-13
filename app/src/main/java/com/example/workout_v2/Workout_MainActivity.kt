package com.example.workout_v2

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView

class Workout_MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_workout__main)
        var i = 0
        findViewById<ImageView>(R.id.next_workout).setOnClickListener {
            i++
            startActivity(Intent(this,RestActivity::class.java))
            Log.d(ContentValues.TAG,i.toString())
        }
    }
}
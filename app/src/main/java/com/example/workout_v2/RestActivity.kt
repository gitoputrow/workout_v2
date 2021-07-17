package com.example.workout_v2

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.widget.Button
import android.widget.Chronometer
import androidx.annotation.RequiresApi

class RestActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rest)
        findViewById<Chronometer>(R.id.time_rest).base = SystemClock.elapsedRealtime() + 31000
        findViewById<Chronometer>(R.id.time_rest).setCountDown(true)
        findViewById<Chronometer>(R.id.time_rest).start()
        findViewById<Button>(R.id.extratime).setOnClickListener {
            findViewById<Chronometer>(R.id.time_rest).base = findViewById<Chronometer>(R.id.time_rest).base + 10000
        }
        findViewById<Button>(R.id.train_again).setOnClickListener {
            onBackPressed()
        }
        findViewById<Chronometer>(R.id.time_rest).setOnChronometerTickListener {
            if (it.text.toString().equals("00:00")){
                onBackPressed()
            }
        }
    }
}
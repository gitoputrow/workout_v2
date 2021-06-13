package com.example.workout_v2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView

class post : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)
        val ambil = intent
        if (ambil.getStringExtra("jenis").toString().equals("kamera")){
            findViewById<AppCompatImageView>(R.id.fotopost).setImageBitmap(ambil.getParcelableExtra("bitmap"))
        }
        else{
            findViewById<AppCompatImageView>(R.id.fotopost).setImageURI(ambil.getParcelableExtra("data"))
        }
        findViewById<ImageView>(R.id.back_post).setOnClickListener {
            startActivity(Intent(this,MainActivity::class.java))
        }

    }
}
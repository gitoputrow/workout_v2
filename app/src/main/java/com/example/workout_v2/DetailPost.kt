package com.example.workout_v2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import coil.load
import com.google.android.material.textview.MaterialTextView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class DetailPost : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_post)
        val ambil = intent
        val database = FirebaseDatabase.getInstance().getReference()
        findViewById<MaterialTextView>(R.id.feed_user_detail).setText(ambil.getStringExtra("usernamepost").toString())
        findViewById<ImageView>(R.id.feed_photo_detail).load(ambil.getStringExtra("foto").toString())
        database.child("data${ambil.getStringExtra("usernamepost").toString()}").addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                var name = snapshot.child("name").value.toString()
                findViewById<MaterialTextView>(R.id.feed_name_detail).setText(name)
            }
        })
        findViewById<ImageView>(R.id.back_detail).setOnClickListener {
            val ahli = Intent(this,MainActivity::class.java)
            ahli.putExtra("username",ambil.getStringExtra("username").toString())
            startActivity(ahli)
            finish()
        }
    }
}
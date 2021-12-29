package com.example.workout_v2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class LevelPick : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_level_pick)
        val ambil = intent
        val database = FirebaseDatabase.getInstance().getReference()
        findViewById<ImageView>(R.id.back_level).setOnClickListener {
            val ahli = Intent(this,MainActivity::class.java)
            ahli.putExtra("username",ambil.getStringExtra("username").toString())
            ahli.putExtra("current","workout")
            startActivity(ahli)
        }
        findViewById<ImageView>(R.id.easy).setOnClickListener {
            pindah(ambil, "easy")
        }
        database.child("data${ambil.getStringExtra("username")}").addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                var muscle = ambil.getStringExtra("muscle").toString().toLowerCase()
                var persen = snapshot.child("MuscleProgress").child(muscle).value.toString().toInt()
                findViewById<ImageView>(R.id.medium).setOnClickListener {
                    kondisi(ambil,"medium",33,persen,"finish easy level first")
                }
                findViewById<ImageView>(R.id.hard).setOnClickListener {
                    kondisi(ambil,"hard",66,persen,"finish medium level first")
                }
                level_check(persen)
            }

        })

    }
    fun level_check(persen: Int){
        if (persen >= 33){
            findViewById<ImageView>(R.id.easy).setImageResource(R.drawable.easyselesai)
        }
        if (persen >= 66){
            findViewById<ImageView>(R.id.medium).setImageResource(R.drawable.mediumselesai)
        }
        if(persen == 100){
            findViewById<ImageView>(R.id.hard).setImageResource(R.drawable.hardselesai)
        }
    }
    fun kondisi(ambil : Intent,level : String,kondisi : Int,persen : Int,txt : String){
        if (persen >= kondisi){
            pindah(ambil,level)
        }
        else{
            Toast.makeText(baseContext,txt,Toast.LENGTH_SHORT).show()
        }
    }
    fun pindah(ambil : Intent,level : String) {
        val ahli = Intent(this,DayActivity::class.java)
        ahli.putExtra("username",ambil.getStringExtra("username").toString())
        ahli.putExtra("muscle",ambil.getStringExtra("muscle").toString())
        ahli.putExtra("level",level)
        finish()
        startActivity(ahli)
    }
}
package com.example.workout_v2

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class DayActivity : AppCompatActivity() {
    val database = FirebaseDatabase.getInstance().getReference()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_day)
        val ambil = intent
        var muscle = ambil.getStringExtra("muscle").toString().toLowerCase()
        check(ambil)
        findViewById<ImageView>(R.id.day1).setOnClickListener {
            pindah(ambil,"1")
        }
        findViewById<TextView>(R.id.workout_name).setText(ambil.getStringExtra("muscle").toString())
        findViewById<ImageView>(R.id.back_day).setOnClickListener {
            back(ambil)
        }
        database.child("data${ambil.getStringExtra("username")}").child("MuscleProgress").child(muscle)
                .addListenerForSingleValueEvent(object : ValueEventListener{
                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }

                    override fun onDataChange(snapshot: DataSnapshot) {
                        var persen = snapshot.value.toString().toInt()
                        findViewById<ImageView>(R.id.day2).setOnClickListener {
                            day_but(ambil,"2",4,37,70,persen,"finish day 1 first")
                        }
                        findViewById<ImageView>(R.id.day3).setOnClickListener {
                            day_but(ambil,"3",9,42,74,persen,"finish day 2 first")
                        }
                        findViewById<ImageView>(R.id.day4).setOnClickListener {
                            day_but(ambil,"4",14,47,79,persen,"finish day 3 first")
                        }
                        findViewById<ImageView>(R.id.day5).setOnClickListener {
                            day_but(ambil,"5",18,51,84,persen,"finish day 4 first")
                        }
                        findViewById<ImageView>(R.id.day6).setOnClickListener {
                            day_but(ambil,"6",23,56,89,persen,"finish day 5 first")
                        }
                        findViewById<ImageView>(R.id.day7).setOnClickListener {
                            day_but(ambil,"7",28,61,94,persen,"finish day 6 first")
                        }
                    }
                })
    }
    fun check(ambil: Intent){
        var muscle = ambil.getStringExtra("muscle").toString().toLowerCase()
        if (ambil.getStringExtra("level").toString().equals("easy")){
            check_day(ambil,4,9,14,18,23,28,33,muscle)
        }
        else if (ambil.getStringExtra("level").toString().equals("medium")){
            check_day(ambil,37,42,47,51,56,61,66,muscle)
        }
        else if (ambil.getStringExtra("level").toString().equals("hard")){
            check_day(ambil,70,74,79,84,89,94,100,muscle)
        }
    }

    fun back(ambil : Intent){
        var ahli = Intent(this,LevelPick::class.java)
        ahli.putExtra("username",ambil.getStringExtra("username").toString())
        ahli.putExtra("muscle",ambil.getStringExtra("muscle").toString())
        finish()
        startActivity(ahli)
    }

    fun check_day(ambil: Intent, p1 : Int,p2 : Int,p3 : Int,p4 : Int,p5 : Int,p6 : Int,p7 : Int, muscle : String){
        database.child("data${ambil.getStringExtra("username")}").child("MuscleProgress").child(muscle).addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                var persen = snapshot.value.toString().toInt()
                if (persen >= p1){
                    findViewById<ImageView>(R.id.day1).setImageResource(R.drawable.day_checked)
                    findViewById<ImageView>(R.id.day2).setImageResource(R.drawable.day2_selected)
                }
                if (persen >= p2){
                    findViewById<ImageView>(R.id.day2).setImageResource(R.drawable.day_checked)
                    findViewById<ImageView>(R.id.day3).setImageResource(R.drawable.day3_selected)
                }
                if (persen >= p3){
                    findViewById<ImageView>(R.id.day3).setImageResource(R.drawable.day_checked)
                    findViewById<ImageView>(R.id.day4).setImageResource(R.drawable.day4_selected)
                }
                if (persen >= p4){
                    findViewById<ImageView>(R.id.day4).setImageResource(R.drawable.day_checked)
                    findViewById<ImageView>(R.id.day5).setImageResource(R.drawable.day5_selected)
                }
                if (persen >= p5){
                    findViewById<ImageView>(R.id.day5).setImageResource(R.drawable.day_checked)
                    findViewById<ImageView>(R.id.day6).setImageResource(R.drawable.day6_selected)
                }
                if (persen >= p6){
                    findViewById<ImageView>(R.id.day6).setImageResource(R.drawable.day_checked)
                    findViewById<ImageView>(R.id.day7).setImageResource(R.drawable.day7_selected)
                }
                if (persen >= p7){
                    findViewById<ImageView>(R.id.day7).setImageResource(R.drawable.day_checked)
                }
            }
        })
    }

    fun day_but(ambil : Intent,day : String,k1 : Int,k2 : Int,k3 : Int,persen : Int,txt : String){
        if (ambil.getStringExtra("level").toString().equals("easy")){
            if (persen >= k1){
                pindah(ambil,day)
            }
            else{
                Toast.makeText(baseContext,txt, Toast.LENGTH_SHORT).show()
            }
        }
        else if (ambil.getStringExtra("level").toString().equals("medium")){
            if (persen >= k2){
                pindah(ambil,day)
            }
            else{
                Toast.makeText(baseContext,txt, Toast.LENGTH_SHORT).show()
            }
        }
        else if (ambil.getStringExtra("level").toString().equals("hard")){
            if (persen >= k3){
                pindah(ambil,day)
            }
            else{
                Toast.makeText(baseContext,txt, Toast.LENGTH_SHORT).show()
            }
        }
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
        ahli.putExtra("level",ambil.getStringExtra("level").toString())
        ahli.putExtra("username",ambil.getStringExtra("username").toString())
        ahli.putExtra("muscle",ambil.getStringExtra("muscle").toString())
        ahli.putExtra("day",day)
        finish()
        startActivity(ahli)
    }
}
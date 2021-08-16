package com.example.workout_v2

import android.content.ContentValues
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.View
import android.widget.Chronometer
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import pl.droidsonroids.gif.GifImageView

class Workout_MainActivity : AppCompatActivity() {
    val woabsres :List<Int> = listOf(R.drawable.crunchsitup,R.drawable.situp,R.drawable.legraise,R.drawable.plank)
    val namewoabs :List<String> = listOf("Crunch Sit Up","Sit Up","Leg Raise","Plank")
    val repsabs :List<String> = listOf("6","6","8")
    val woarmres :List<Int> = listOf(R.drawable.kneepu,R.drawable.triceps_dips,R.drawable.mountain_climb,R.drawable.plank)
    val namewoarm :List<String> = listOf("Knee Push Up","Tricep Dips","Mountain Climb","Plank")
    val repsarm :List<String> = listOf("6","8","8")
    val wochestres :List<Int> = listOf(R.drawable.wallpu,R.drawable.triceps_dips,R.drawable.icpu,R.drawable.kneepu)
    val namewochest :List<String> = listOf("Wall Push Up","Tricep Dips","Incline Push Up","Knee Push Up")
    val repschest :List<String> = listOf("6","4","6","6")
    val wofullres :List<Int> = listOf(R.drawable.crunchsitup,R.drawable.kneepu,R.drawable.naikturun,R.drawable.plank)
    val namewofull :List<String> = listOf("Crunch Sit Up","Knee Push Up","Step Up and Down Onto Chair","Plank")
    val repsfull :List<String> = listOf("6","6","8")
    val wolegres :List<Int> = listOf(R.drawable.straight_leg_raise_left,R.drawable.straight_leg_raise_right,R.drawable.jinjit2,R.drawable.naikturun)
    val namewoleg :List<String> = listOf("Straight leg raise L","Straight leg raise R","TipToe","Step Up and Down")
    val repsleg :List<String> = listOf("6","6","8","6")
    val database = FirebaseDatabase.getInstance().getReference()
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_workout__main)
        findViewById<Chronometer>(R.id.lastreps_time).base = SystemClock.elapsedRealtime() - 30000
        var i = 0
        var ambil = intent
        if (i == 0){
            train(ambil,ambil.getStringExtra("day").toString(),i)
        }
        findViewById<ImageView>(R.id.next_workout).setOnClickListener {
            i++
            if (ambil.getStringExtra("level").toString().equals("easy")){
                if (i == 4){
                    database.child("data${ambil.getStringExtra("username").toString()}").child("MuscleProgress")
                            .child(ambil.getStringExtra("muscle").toString().toLowerCase()).addListenerForSingleValueEvent(object : ValueEventListener{
                                override fun onCancelled(error: DatabaseError) {
                                    TODO("Not yet implemented")
                                }

                                override fun onDataChange(snapshot: DataSnapshot) {
                                    var persen = snapshot.value.toString().toInt()
                                    muscle_kondisi(persen,ambil)
                                }
                            })
                    var ahli = Intent(this,Selesai::class.java)
                    ahli.putExtra("username",ambil.getStringExtra("username").toString())
                    startActivity(ahli)
                    finish()
                }
                else {
                    startActivity(Intent(this, RestActivity::class.java))
                    train(ambil, ambil.getStringExtra("day").toString(), i)
                    Log.d(ContentValues.TAG, i.toString())
                }
            }
        }
        findViewById<ImageView>(R.id.play).setOnClickListener {
            findViewById<ImageView>(R.id.next_workout).visibility = View.VISIBLE
            findViewById<Chronometer>(R.id.lastreps_time).base = SystemClock.elapsedRealtime() + 30000
            findViewById<Chronometer>(R.id.lastreps_time).setCountDown(true)
            findViewById<Chronometer>(R.id.lastreps_time).start()
            it.visibility = View.INVISIBLE
        }
        findViewById<Chronometer>(R.id.lastreps_time).setOnChronometerTickListener {
            if (it.text.toString().equals("00:00")){
                startActivity(Intent(this,Selesai::class.java))
            }
        }
    }
    fun train(ambil : Intent,day : String,i : Int){
        if (ambil.getStringExtra("muscle").toString().equals("Abs")){
            if (ambil.getStringExtra("level").toString().equals("easy")){
                training_easy(day,i,ambil.getStringExtra("muscle").toString(),repsabs,woabsres,namewoabs)
            }
        }
        else if (ambil.getStringExtra("muscle").toString().equals("Arm")){
            if (ambil.getStringExtra("level").toString().equals("easy")){
                training_easy(day,i,ambil.getStringExtra("muscle").toString(),repsarm,woarmres,namewoarm)
            }
        }
        else if (ambil.getStringExtra("muscle").toString().equals("Full body")){
            if (ambil.getStringExtra("level").toString().equals("easy")){
                training_easy(day,i,ambil.getStringExtra("muscle").toString(),repsfull,wofullres,namewofull)
            }
        }
        else if (ambil.getStringExtra("muscle").toString().equals("Chest")){
            if (ambil.getStringExtra("level").toString().equals("easy")){
                training_easy(day,i,ambil.getStringExtra("muscle").toString(),repschest,wochestres,namewochest)
            }
        }
        else if (ambil.getStringExtra("muscle").toString().equals("Leg")){
            if (ambil.getStringExtra("level").toString().equals("easy")){
                training_easy(day,i,ambil.getStringExtra("muscle").toString(),repsleg,wolegres,namewoleg)
            }
        }
    }
    fun training_easy(day: String,i: Int,muscle : String,reps : List<String>,source : List<Int>,namewo : List<String>){
        if (i == 3 && (muscle.equals("Abs") || muscle.equals("Arm") || muscle.equals("Full body"))) {
            findViewById<TextView>(R.id.workout_reps).visibility = View.INVISIBLE
            findViewById<Chronometer>(R.id.lastreps_time).visibility = View.VISIBLE
            findViewById<ImageView>(R.id.play).visibility = View.VISIBLE
            findViewById<ImageView>(R.id.next_workout).visibility = View.INVISIBLE
        }
        else{
            findViewById<TextView>(R.id.workout_reps).setText("x ${(reps[i].toInt() + ((day.toInt() - 1) * 2)).toString()}")
        }
        findViewById<GifImageView>(R.id.train_anim).setImageResource(source[i])
        findViewById<TextView>(R.id.workout_names).setText(namewo[i])
    }

    fun muscle_kondisi(persen:Int,ambil: Intent){
        val kondisi = listOf<Int>(0,4,9,14,18,23,28,33,37,42,47,51,56,61,66,70,74,79,84,89,94)
        val syarat = listOf<Int>(4,9,14,18,23,28,33,37,42,47,51,56,61,66,70,74,79,84,89,94,100)
        var index = 0
        if (ambil.getStringExtra("level").toString().equals("easy")){
            index = ambil.getStringExtra("day").toString().toInt() - 1
        }
        if (ambil.getStringExtra("level").toString().equals("medium")){
            index = ambil.getStringExtra("day").toString().toInt() + 6
        }
        if (ambil.getStringExtra("level").toString().equals("medium")){
            index = ambil.getStringExtra("day").toString().toInt() + 13
        }
        if (persen == kondisi[index]) {
            database.child("data${ambil.getStringExtra("username").toString()}").child("MuscleProgress")
                    .child(ambil.getStringExtra("muscle").toString().toLowerCase()).setValue((syarat.get(kondisi.indexOf(persen))).toString())
        }
    }
}
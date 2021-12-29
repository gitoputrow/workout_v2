package com.example.workout_v2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.google.firebase.database.FirebaseDatabase
import pl.droidsonroids.gif.GifImageView

class workout_list_med : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_workout_list_med)
        val woabsres :List<Int> = listOf(R.drawable.crunchsitup,R.drawable.situp,R.drawable.legraise,R.drawable.crunchbicyle,R.drawable.plank)
        val namewoabs :List<String> = listOf("Crunch Sit Up","Sit Up","Leg Raise","Bicycle Crunches","Plank")
        val repsabs :List<String> = listOf("8","8","8","8")
        val woarmres :List<Int> = listOf(R.drawable.pushup,R.drawable.triceps_dips,R.drawable.mountain_climb,R.drawable.st,R.drawable.du)
        val namewoarm :List<String> = listOf("Push Up","Tricep Dips","Mountain Climb","Shoulder Tap","Diamond Push Up")
        val repsarm :List<String> = listOf("6","10","8","10")
        val wochestres :List<Int> = listOf(R.drawable.icpu,R.drawable.pushup,R.drawable.decup,R.drawable.wpu,R.drawable.du)
        val namewochest :List<String> = listOf("Incline Push Up","Push Up","Decline Push Up","Wide Push Up","Diamond Push Up")
        val repschest :List<String> = listOf("8","8","8","6")
        val wolegres :List<Int> = listOf(R.drawable.straight_leg_raise_left,R.drawable.straight_leg_raise_right,R.drawable.jinjit2,R.drawable.naikturun,R.drawable.squat)
        val namewoleg :List<String> = listOf("Straight leg raise L","Straight leg raise R","TipToe","Step Up and Down","Squat")
        val repsleg :List<String> = listOf("8","8","8","6")
        val wofullres :List<Int> = listOf(R.drawable.crunchsitup,R.drawable.pushup,R.drawable.naikturun,R.drawable.mountain_climb,R.drawable.plank)
        val namewofull :List<String> = listOf("Crunch Sit Up","Push Up","Step Up and Down","Mountain Climb","Plank")
        val repsfull :List<String> = listOf("6","6","8","8")
        val database = FirebaseDatabase.getInstance().getReference()
        val ambil = intent

        findViewById<Button>(R.id.button_med).setOnClickListener {
            pindah(ambil)
        }
        findViewById<ImageView>(R.id.back_workoutlist_med).setOnClickListener{
            back(ambil)
        }
        if (ambil.getStringExtra("muscle").toString().equals("Abs")){
            list_wo(woabsres,namewoabs,ambil.getStringExtra("day").toString(),repsabs,"00:40")
        }
        else if (ambil.getStringExtra("muscle").toString().equals("Arm")){
            list_wo(woarmres,namewoarm,ambil.getStringExtra("day").toString(),repsarm,"6")
        }
        else if (ambil.getStringExtra("muscle").toString().equals("Chest")){
            list_wo(wochestres,namewochest,ambil.getStringExtra("day").toString(),repschest,"6")
        }
        else if (ambil.getStringExtra("muscle").toString().equals("Leg")){
            list_wo(wolegres,namewoleg,ambil.getStringExtra("day").toString(),repsleg,"6")
        }
        else if (ambil.getStringExtra("muscle").toString().equals("Full body")){
            list_wo(wofullres,namewofull,ambil.getStringExtra("day").toString(),repsfull,"00:40")
        }
        findViewById<TextView>(R.id.day_name_med).setText("Day ${ambil.getStringExtra("day").toString()}")

    }
    fun list_wo(source : List<Int>,nameWo : List<String>,day :String,repsWo : List<String>,Lrepswo :String){
        findViewById<GifImageView>(R.id.workout1_med).setImageResource(source[0])
        findViewById<GifImageView>(R.id.workout2_med).setImageResource(source[1])
        findViewById<GifImageView>(R.id.workout3_med).setImageResource(source[2])
        findViewById<GifImageView>(R.id.workout4_med).setImageResource(source[3])
        findViewById<GifImageView>(R.id.workout5_med).setImageResource(source[4])
        findViewById<TextView>(R.id.nama_workoutlist_med).setText(nameWo[0])
        findViewById<TextView>(R.id.nama_workoutlist2_med).setText(nameWo[1])
        findViewById<TextView>(R.id.nama_workoutlist3_med).setText(nameWo[2])
        findViewById<TextView>(R.id.nama_workoutlist4_med).setText(nameWo[3])
        findViewById<TextView>(R.id.nama_workoutlist5_med).setText(nameWo[4])
        findViewById<TextView>(R.id.reps_workoutlist_med).setText("x ${(repsWo[0].toInt() + ((day.toInt() - 1) * 2)).toString()}")
        findViewById<TextView>(R.id.reps_workoutlist2_med).setText("x ${(repsWo[1].toInt() + ((day.toInt() - 1) * 2)).toString()}")
        findViewById<TextView>(R.id.reps_workoutlist3_med).setText("x ${(repsWo[2].toInt() + ((day.toInt() - 1) * 2)).toString()}")
        findViewById<TextView>(R.id.reps_workoutlist4_med).setText("x ${(repsWo[3].toInt() + ((day.toInt() - 1) * 2)).toString()}")
        if (Lrepswo.equals("00:40")){
            findViewById<TextView>(R.id.reps_workoutlist5_med).setText(Lrepswo)
        }
        else{
            findViewById<TextView>(R.id.reps_workoutlist5_med).setText("x ${(Lrepswo.toInt() + ((day.toInt() - 1) * 2)).toString()}")
        }
    }
    fun pindah(ambil : Intent){
        var ahli = Intent(this,Workout_MainActivity::class.java)
        ahli.putExtra("level",ambil.getStringExtra("level").toString())
        ahli.putExtra("username",ambil.getStringExtra("username").toString())
        ahli.putExtra("muscle",ambil.getStringExtra("muscle").toString())
        ahli.putExtra("day",ambil.getStringExtra("day").toString())
        startActivity(ahli)
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
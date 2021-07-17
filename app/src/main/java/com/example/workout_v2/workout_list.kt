package com.example.workout_v2

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.google.firebase.database.FirebaseDatabase
import pl.droidsonroids.gif.GifImageView

class workout_list : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_workout_list)
        val woabsres :List<Int> = listOf(R.drawable.crunchsitup,R.drawable.situp,R.drawable.legraise,R.drawable.plank)
        val namewoabs :List<String> = listOf("Crunch Sit Up","Sit Up","Leg Raise","Plank")
        val repsabs :List<String> = listOf("6","6","8")
        val woarmres :List<Int> = listOf(R.drawable.kneepu,R.drawable.triceps_dips,R.drawable.mountain_climb,R.drawable.plank)
        val namewoarm :List<String> = listOf("Knee Push Up","Tricep Dips","Mountain Climb","Plank")
        val repsarm :List<String> = listOf("6","8","8")
        val wochestres :List<Int> = listOf(R.drawable.wallpu,R.drawable.triceps_dips,R.drawable.icpu,R.drawable.kneepu)
        val namewochest :List<String> = listOf("Wall Push Up","Tricep Dips","Incline Push Up","Knee Push Up")
        val repschest :List<String> = listOf("6","4","6")
        val wolegres :List<Int> = listOf(R.drawable.straight_leg_raise_left,R.drawable.straight_leg_raise_right,R.drawable.jinjit2,R.drawable.naikturun)
        val namewoleg :List<String> = listOf("Straight leg raise L","Straight leg raise R","TipToe","Step Up and Down")
        val repsleg :List<String> = listOf("6","6","8")
        val wofullres :List<Int> = listOf(R.drawable.crunchsitup,R.drawable.kneepu,R.drawable.naikturun,R.drawable.plank)
        val namewofull :List<String> = listOf("Crunch Sit Up","Knee Push Up","Step Up and Down","Plank")
        val repsfull :List<String> = listOf("6","6","8")
        val database = FirebaseDatabase.getInstance().getReference()
        val ambil = intent

        findViewById<Button>(R.id.button_easy).setOnClickListener {
            pindah(ambil)
        }
        findViewById<ImageView>(R.id.back_workoutlist).setOnClickListener{
            back(ambil)
        }
        if (ambil.getStringExtra("muscle").toString().equals("Abs")){
            list_wo(woabsres,namewoabs,ambil.getStringExtra("day").toString(),repsabs,"00:30")
        }
        else if (ambil.getStringExtra("muscle").toString().equals("Arm")){
            list_wo(woarmres,namewoarm,ambil.getStringExtra("day").toString(),repsarm,"00:30")
        }
        else if (ambil.getStringExtra("muscle").toString().equals("Chest")){
            list_wo(wochestres,namewochest,ambil.getStringExtra("day").toString(),repschest,"6")
        }
        else if (ambil.getStringExtra("muscle").toString().equals("Leg")){
            list_wo(wolegres,namewoleg,ambil.getStringExtra("day").toString(),repsleg,"6")
        }
        else if (ambil.getStringExtra("muscle").toString().equals("Full body")){
            list_wo(wofullres,namewofull,ambil.getStringExtra("day").toString(),repsfull,"00:30")
        }
        findViewById<TextView>(R.id.day_name).setText("Day ${ambil.getStringExtra("day").toString()}")

    }
    fun list_wo(source : List<Int>,nameWo : List<String>,day :String,repsWo : List<String>,Lrepswo :String){
        findViewById<GifImageView>(R.id.workout1).setImageResource(source[0])
        findViewById<GifImageView>(R.id.workout2).setImageResource(source[1])
        findViewById<GifImageView>(R.id.workout3).setImageResource(source[2])
        findViewById<GifImageView>(R.id.workout4).setImageResource(source[3])
        findViewById<TextView>(R.id.nama_workoutlist).setText(nameWo[0])
        findViewById<TextView>(R.id.nama_workoutlist2).setText(nameWo[1])
        findViewById<TextView>(R.id.nama_workoutlist3).setText(nameWo[2])
        findViewById<TextView>(R.id.nama_workoutlist4).setText(nameWo[3])
        findViewById<TextView>(R.id.reps_workoutlist).setText("x ${(repsWo[0].toInt() + ((day.toInt() - 1) * 2)).toString()}")
        findViewById<TextView>(R.id.reps_workoutlist2).setText("x ${(repsWo[1].toInt() + ((day.toInt() - 1) * 2)).toString()}")
        findViewById<TextView>(R.id.reps_workoutlist3).setText("x ${(repsWo[2].toInt() + ((day.toInt() - 1) * 2)).toString()}")
        if (Lrepswo.equals("00:30")){
            findViewById<TextView>(R.id.reps_workoutlist4).setText(Lrepswo)
        }
        else{
            findViewById<TextView>(R.id.reps_workoutlist4).setText("x ${(Lrepswo.toInt() + ((day.toInt() - 1) * 2)).toString()}")
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
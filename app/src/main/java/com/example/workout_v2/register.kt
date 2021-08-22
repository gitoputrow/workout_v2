package com.example.workout_v2

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class register : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        val database = FirebaseDatabase.getInstance().getReference()
        val storage = Firebase.storage("gs://workout-v2-3f3b3.appspot.com").reference
        findViewById<ImageView>(R.id.back_regist).setOnClickListener {
            finish()
        }
        findViewById<Button>(R.id.button_regist).setOnClickListener {
            if (findViewById<TextInputEditText>(R.id.nameinput).text.toString().isEmpty() ||
                    findViewById<TextInputEditText>(R.id.usernameinput_regist).text.toString().isEmpty() ||
                    findViewById<TextInputEditText>(R.id.passwordinput_regist).text.toString().isEmpty() ||
                    findViewById<TextInputEditText>(R.id.emailinput_regist).text.toString().isEmpty()){
                Toast.makeText(baseContext,"Please Fill All the Data",Toast.LENGTH_SHORT).show()
            }
            else{
                if (findViewById<TextInputEditText>(R.id.usernameinput_regist).text.toString().indexOf(" ") >= 0){
                    Toast.makeText(baseContext,"Username can't contain any spaces",Toast.LENGTH_SHORT).show()
                }
                else{
                    database.addListenerForSingleValueEvent(object : ValueEventListener{
                        override fun onCancelled(error: DatabaseError) {
                            TODO("Not yet implemented")
                        }

                        override fun onDataChange(snapshot: DataSnapshot) {
                            if (snapshot.hasChild("data${findViewById<TextInputEditText>(R.id.usernameinput_regist).text.toString()}")){
                                Toast.makeText(baseContext,"Username already taken",Toast.LENGTH_SHORT).show()
                            }
                            else{
                                findViewById<Button>(R.id.button_regist).visibility = View.INVISIBLE
                                findViewById<ProgressBar>(R.id.progressBar_regist).visibility = View.VISIBLE
                                var hashMapdata = HashMap<String,Any>()
                                hashMapdata.put("name",findViewById<TextInputEditText>(R.id.nameinput).text.toString())
                                hashMapdata.put("username",findViewById<TextInputEditText>(R.id.usernameinput_regist).text.toString())
                                hashMapdata.put("password",findViewById<TextInputEditText>(R.id.passwordinput_regist).text.toString())
                                hashMapdata.put("email",findViewById<TextInputEditText>(R.id.emailinput_regist).text.toString())
                                var hashMapmuscle = HashMap<String,Any>()
                                hashMapmuscle.put("full body","0")
                                hashMapmuscle.put("abs","0")
                                hashMapmuscle.put("chest","0")
                                hashMapmuscle.put("arm","0")
                                hashMapmuscle.put("leg","0")
                                storage.child("profile2.png").downloadUrl.addOnSuccessListener(object : OnSuccessListener<Uri>{
                                    override fun onSuccess(p0: Uri?) {
                                        hashMapdata.put("fp",p0.toString())
                                        database.child("data${findViewById<TextInputEditText>(R.id.usernameinput_regist).text.toString()}")
                                                .setValue(hashMapdata)
                                                .addOnSuccessListener {
                                                    database.child("data${findViewById<TextInputEditText>(R.id.usernameinput_regist).text.toString()}")
                                                            .child("MuscleProgress")
                                                            .setValue(hashMapmuscle).addOnSuccessListener {
                                                                finish()
                                                            }
                                                }
                                    }

                                })

                            }
                        }

                    })
                }
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}
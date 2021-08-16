package com.example.workout_v2

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatImageView
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.io.ByteArrayOutputStream
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.HashMap

class post : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)
        val storage = Firebase.storage("gs://workout-v2-3f3b3.appspot.com").reference
        val ambil = intent
        val database = FirebaseDatabase.getInstance().getReference()
        var id = database.push().key.toString()
        var date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        var hashMapisi = HashMap<String, Any>()
        hashMapisi.put("date",date.toString())
        if (ambil.getStringExtra("jenis").toString().equals("kamera")){
            findViewById<AppCompatImageView>(R.id.fotopost).setImageBitmap(ambil.getParcelableExtra("bitmap"))
        }
        else{
            findViewById<AppCompatImageView>(R.id.fotopost).setImageURI(ambil.getParcelableExtra("data"))
        }
        findViewById<ImageView>(R.id.back_post).setOnClickListener {
            val ahli = Intent(this,MainActivity::class.java)
            ahli.putExtra("username",ambil.getStringExtra("username").toString())
            ahli.putExtra("current","home")
            finish()
            startActivity(ahli)
        }
        findViewById<ImageView>(R.id.upload_post).setOnClickListener {
            if (findViewById<EditText>(R.id.nulis_sp).text.isEmpty()){
                Toast.makeText(baseContext,"Write a Caption",Toast.LENGTH_SHORT).show()
            }
            else{
                findViewById<ImageView>(R.id.upload_post).visibility = View.INVISIBLE
                findViewById<ProgressBar>(R.id.progressBar).visibility = View.VISIBLE
                hashMapisi.put("text",findViewById<EditText>(R.id.nulis_sp).text.toString())
                val bitmap1 = (findViewById<AppCompatImageView>(R.id.fotopost).drawable as BitmapDrawable).bitmap
                val baos = ByteArrayOutputStream()
                bitmap1.compress(Bitmap.CompressFormat.JPEG, 100, baos)
                val data = baos.toByteArray()
                storage.child("post${ambil.getStringExtra("username").toString()}")
                    .child(id).putBytes(data).addOnCompleteListener{
                        if (it.isSuccessful){
                            storage.child("post${ambil.getStringExtra("username").toString()}")
                                .child(id).downloadUrl.addOnSuccessListener(object : OnSuccessListener<Uri> {
                                    override fun onSuccess(p0: Uri?) {
                                        hashMapisi.put("foto",p0.toString())
                                        database.child("data${ambil.getStringExtra("username").toString()}").child("postingan")
                                            .child(id).setValue(hashMapisi).addOnSuccessListener {
                                                val ahli = Intent(this@post,MainActivity::class.java)
                                                ahli.putExtra("username",ambil.getStringExtra("username").toString())
                                                ahli.putExtra("current","home")
                                                finish()
                                                startActivity(ahli)
                                            }
                                    }
                                })
                        }
                    }
            }
        }


    }
}
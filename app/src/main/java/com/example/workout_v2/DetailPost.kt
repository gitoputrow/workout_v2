package com.example.workout_v2

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textview.MaterialTextView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import de.hdodenhof.circleimageview.CircleImageView
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class DetailPost : AppCompatActivity() {
    private var layoutManager: RecyclerView.LayoutManager? = null
    private lateinit var adapter: komenadapter
    private val list = ArrayList<komenitem>()
    data class list_class(var username: String, var text: String, var date: String, var fp :String)
    val listt = mutableListOf<list_class>()
    val database = FirebaseDatabase.getInstance().getReference()
    val storage = Firebase.storage("gs://workout-v2-3f3b3.appspot.com").reference
    @RequiresApi(Build.VERSION_CODES.O)
    var date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
    var id = database.push().key.toString()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_post)
        val ambil = intent
        findViewById<MaterialTextView>(R.id.feed_user_detail).setText(ambil.getStringExtra("usernamepost").toString())
        findViewById<ImageView>(R.id.feed_photo_detail).load(ambil.getStringExtra("foto").toString())
        findViewById<CircleImageView>(R.id.fp_detail).load(ambil.getStringExtra("fp").toString())

        if (ambil.getStringExtra("usernamepost").toString().equals(ambil.getStringExtra("username").toString())){
            findViewById<ImageView>(R.id.delete_but).visibility = View.VISIBLE
        }

        findViewById<ImageView>(R.id.delete_but).setOnClickListener {
            findViewById<ConstraintLayout>(R.id.popup_layout).visibility = View.VISIBLE
            findViewById<Button>(R.id.no).setOnClickListener {
                findViewById<ConstraintLayout>(R.id.popup_layout).visibility = View.INVISIBLE
            }
            findViewById<Button>(R.id.yes).setOnClickListener {
                findViewById<LinearLayout>(R.id.option).visibility = View.INVISIBLE
                findViewById<ProgressBar>(R.id.progressBar_yes).visibility = View.VISIBLE
                storage.child("post${ambil.getStringExtra("username")}/${ambil.getStringExtra("postid")}")
                        .delete().addOnCompleteListener {
                            if (it.isSuccessful) {
                                database.child("data${ambil.getStringExtra("username")}").child("postingan")
                                        .child(ambil.getStringExtra("postid").toString()).removeValue().addOnSuccessListener {
                                            val pindah = Intent(this, MainActivity::class.java)
                                            pindah.putExtra("username", ambil.getStringExtra("username").toString())
                                            pindah.putExtra("current","home")
                                            startActivity(pindah)
                                            overridePendingTransition(0, 0)
                                            finish()
                                        }
                            }
                        }
            }
        }

        database.child("data${ambil.getStringExtra("usernamepost").toString()}").addValueEventListener(object : ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                var name = snapshot.child("name").value.toString()
                var childpostlike = snapshot.child("postingan").child(ambil.getStringExtra("postid").toString()).child("like")
                var childpost = snapshot.child("postingan").child(ambil.getStringExtra("postid").toString())
                var komen = 0
                if (childpostlike.hasChild(ambil.getStringExtra("username").toString())){
                    findViewById<ImageView>(R.id.like).visibility = View.INVISIBLE
                    findViewById<ImageView>(R.id.liked).visibility = View.VISIBLE
                }
                if (childpost.hasChild("comments")){
                    for (childuser in childpost.child("comments").children){
                        var chilid = childuser.key.toString()
                        if (childuser.hasChild("comment")){
                            komen += childpost.child("comments").child(chilid).child("comment").childrenCount.toInt()
                        }
                    }
                    findViewById<TextView>(R.id.angka_comment).setText(komen.toString())
                }
                findViewById<TextView>(R.id.angka_like).setText(childpostlike.childrenCount.toInt().toString())
                findViewById<MaterialTextView>(R.id.feed_name_detail).setText(name)
            }
        })
        findViewById<ImageView>(R.id.back_detail).setOnClickListener {
            val ahli = Intent(this,MainActivity::class.java)
            ahli.putExtra("username",ambil.getStringExtra("username").toString())
            ahli.putExtra("current","home")
            startActivity(ahli)
            finish()
        }
        findViewById<ImageView>(R.id.like).setOnClickListener {
            it.visibility = View.INVISIBLE
            findViewById<ImageView>(R.id.liked).visibility = View.VISIBLE
            database.child("data${ambil.getStringExtra("usernamepost").toString()}").child("postingan")
                    .child(ambil.getStringExtra("postid").toString()).child("like")
                    .child(ambil.getStringExtra("username").toString()).setValue("true")
        }
        findViewById<ImageView>(R.id.liked).setOnClickListener {
            it.visibility = View.INVISIBLE
            findViewById<ImageView>(R.id.like).visibility = View.VISIBLE
            database.child("data${ambil.getStringExtra("usernamepost").toString()}").child("postingan")
                    .child(ambil.getStringExtra("postid").toString()).child("like")
                    .child(ambil.getStringExtra("username").toString()).removeValue()
        }
        findViewById<ImageView>(R.id.upload_komen).setOnClickListener{
            if (findViewById<TextInputEditText>(R.id.comment_input).text.toString().isNotEmpty()){
                it.visibility = View.INVISIBLE
                findViewById<ProgressBar>(R.id.progressBar_komen).visibility = View.VISIBLE
                var text = findViewById<TextInputEditText>(R.id.comment_input).text.toString()
                var hashMapkomen = HashMap<String,Any>()
                hashMapkomen.put("text",text)
                hashMapkomen.put("date",date)
                findViewById<TextInputEditText>(R.id.comment_input).setText("")
                database.child("data${ambil.getStringExtra("usernamepost").toString()}").child("postingan")
                        .child(ambil.getStringExtra("postid").toString()).child("comments")
                        .child(ambil.getStringExtra("username").toString()).child("comment").child(id).setValue(hashMapkomen).addOnSuccessListener {
                            findViewById<ProgressBar>(R.id.progressBar_komen).visibility = View.INVISIBLE
                            findViewById<ImageView>(R.id.upload_komen).visibility = View.VISIBLE
                        }
            }
            else{
                Toast.makeText(baseContext,"fill the comment",Toast.LENGTH_SHORT).show()
            }
        }
        recyclerViewInflater(ambil)
    }
    private fun recyclerViewInflater(ambil : Intent) {
        database.child("data${ambil.getStringExtra("usernamepost")}").addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.child("postingan").child(ambil.getStringExtra("postid").toString()).hasChild("comments")){
                    for (username_cm in snapshot.child("postingan").child(ambil.getStringExtra("postid").toString()).child("comments").children){
                        var username_cmid = username_cm.key.toString()
                        database.child("data$username_cmid").addListenerForSingleValueEvent(object : ValueEventListener{
                            override fun onCancelled(error: DatabaseError) {
                                TODO("Not yet implemented")
                            }

                            override fun onDataChange(snapshot: DataSnapshot) {
                                database.child("data${ambil.getStringExtra("usernamepost")}").child("postingan")
                                    .child(ambil.getStringExtra("postid").toString()).child("comments").child(username_cmid).child("fp")
                                    .setValue(snapshot.child("fp").value.toString())
                            }

                        })
                        for (comment_id in snapshot.child("postingan").child(ambil.getStringExtra("postid").toString()).child("comments")
                                .child(username_cmid).child("comment").children){
                            var comment_idd = comment_id.key.toString()
                            var text = snapshot.child("postingan").child(ambil.getStringExtra("postid").toString()).child("comments")
                                    .child(username_cmid).child("comment").child(comment_idd).child("text").value.toString()
                            var date = snapshot.child("postingan").child(ambil.getStringExtra("postid").toString()).child("comments")
                                    .child(username_cmid).child("comment").child(comment_idd).child("date").value.toString()
                            var fp :String = snapshot.child("postingan").child(ambil.getStringExtra("postid").toString()).child("comments")
                                .child(username_cmid).child("fp").value.toString()
                            listt.add(list_class(username_cmid,text,date,fp))
                        }
                    }
                    listt.sortBy { listClass -> listClass.date }
                    for (i in 0..listt.size-1){
                        list(i,listt)
                    }
                }
            }

        })
    }
    fun list(i: Int, listt : MutableList<list_class>){
        list.add(komenitem(listt[i].username,listt[i].text,listt[i].fp))
        findViewById<RecyclerView>(R.id.rv_comment).setHasFixedSize(true)
        findViewById<RecyclerView>(R.id.rv_comment).layoutManager = LinearLayoutManager(this)
        val adapter = komenadapter(list)
        findViewById<RecyclerView>(R.id.rv_comment).adapter = adapter
    }
}
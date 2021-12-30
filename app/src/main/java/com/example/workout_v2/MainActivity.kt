package com.example.workout_v2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.fragment.app.replace
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        val ambil = intent
        var bundle=Bundle()
        val database = FirebaseDatabase.getInstance().getReference()
        val firebaseauth = FirebaseAuth.getInstance()
        val firebaseAuthuserLogin = firebaseauth.currentUser
        if (firebaseAuthuserLogin != null){
            val email = firebaseAuthuserLogin.email
            database.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (childmain in snapshot.children){
                        var childdata = childmain.key.toString()
                        var emailUser = snapshot.child(childdata).child("email").value.toString()
                        var username = snapshot.child(childdata).child("username").value.toString()
                        if (email.toString().equals(emailUser)){
                            bundle.putString("username",username)
                            val workoutFragment = WorkoutFragment()
                            val profileFragment = ProfileFragment()
                            val homeFragment = HomeFragment()

                            workoutFragment.arguments = bundle
                            profileFragment.arguments = bundle
                            homeFragment.arguments = bundle

                            if (ambil.getStringExtra("current").equals("home")){
                                MakeCurrentFragment(homeFragment)
                            }
                            else{
                                MakeCurrentFragment(workoutFragment)
                            }

                            findViewById<BottomNavigationView>(R.id.bottom_navigation).setOnNavigationItemSelectedListener {
                                when (it.itemId){
                                    R.id.ic_workout -> {
                                        MakeCurrentFragment(workoutFragment)
                                    }
                                    R.id.ic_profile -> {
                                        MakeCurrentFragment(profileFragment)
                                    }
                                    R.id.ic_home -> {
                                        MakeCurrentFragment(homeFragment)
                                    }
                                }
                                true
                            }
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
        }
        else{
            val pindah = Intent(this@MainActivity,Login::class.java)
            finish()
            startActivity(pindah)
            overridePendingTransition(0,0)
        }

    }
    private fun MakeCurrentFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flayout,fragment)
            commit()
        }
    }
}

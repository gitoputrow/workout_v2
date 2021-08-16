package com.example.workout_v2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.fragment.app.replace
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        val ambil = intent
        var bundle=Bundle()
        bundle.putString("username",ambil.getStringExtra("username").toString())
        val workoutFragment = WorkoutFragment()
        val profileFragment = ProfileFragment()
        val homeFragment = HomeFragment()

        workoutFragment.arguments = bundle
        profileFragment.arguments = bundle
        homeFragment.arguments = bundle

        if (ambil.getStringExtra("current").equals("workout")){
            MakeCurrentFragment(workoutFragment)
        }
        else{
            MakeCurrentFragment(homeFragment)
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
    private fun MakeCurrentFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flayout,fragment)
            commit()
        }
    }
}

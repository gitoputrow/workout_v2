package com.example.workout_v2

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ProfileFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val database = FirebaseDatabase.getInstance().getReference()
        val ambil :String = this.requireArguments().getString("username").toString()
        view.findViewById<TextView>(R.id.username_profile).setText(ambil)
        database.child("data$ambil").addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                view.findViewById<TextView>(R.id.name_profile).setText(snapshot.child("name").value.toString())
                view.findViewById<TextView>(R.id.full_progress).setText(snapshot.child("MuscleProgress").child("full").value.toString())
                view.findViewById<TextView>(R.id.abs_progress).setText(snapshot.child("MuscleProgress").child("abs").value.toString())
                view.findViewById<TextView>(R.id.arm_progress).setText(snapshot.child("MuscleProgress").child("arm").value.toString())
                view.findViewById<TextView>(R.id.leg_progress).setText(snapshot.child("MuscleProgress").child("leg").value.toString())
                view.findViewById<TextView>(R.id.chest_progress).setText(snapshot.child("MuscleProgress").child("chest").value.toString())
            }

        })
    }
}
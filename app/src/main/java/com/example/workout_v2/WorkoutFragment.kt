package com.example.workout_v2

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView

class WorkoutFragment : Fragment() {
    var cekk = true
    var muscle = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)
        return inflater.inflate(R.layout.fragment_workout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val ambil :String = this.requireArguments().getString("username").toString()
        val full :CheckBox = view.findViewById(R.id.full)
        val abs :CheckBox = view.findViewById(R.id.abs)
        val chest :CheckBox = view.findViewById(R.id.chest)
        val arm :CheckBox = view.findViewById(R.id.arm)
        val leg :CheckBox = view.findViewById(R.id.leg)
        val gambar :ImageView = view.findViewById(R.id.kosong)

        Log.d(TAG,ambil)

        full.setOnClickListener {
            check(gambar,full,chest,arm,abs,leg,R.drawable.gambar_full1,"Full body")
            animbutton(full)
        }

        abs.setOnClickListener {
            check(gambar,abs,chest,arm,full,leg,R.drawable.gambar_perut1,"Abs")
            animbutton(abs)
        }

        chest.setOnClickListener {
            check(gambar,chest,abs,arm,full,leg,R.drawable.gambar_dada1,"Chest")
            animbutton(chest)
        }

        arm.setOnClickListener {
            check(gambar,arm,abs,chest,full,leg,R.drawable.gambar_tangan1,"Arm")
            animbutton(arm)
        }

        leg.setOnClickListener {
            check(gambar,leg,chest,arm,full,abs,R.drawable.gambar_kaki1,"Leg")
            animbutton(leg)
        }

        view.findViewById<ImageView>(R.id.button).setOnClickListener {
            val ahli = Intent(activity,LevelPick::class.java)
            ahli.putExtra("username",ambil)
            ahli.putExtra("muscle",muscle)
            startActivity(ahli)
            requireActivity().finish()
        }
    }

    override fun onStop() {
        super.onStop()
        uncheck()
        cekk = true
    }

    fun check(vsble :ImageView, cektrue :CheckBox, cekfalse1 :CheckBox, cekfalse2 :CheckBox, cekfalse3 :CheckBox, cekfalse4 : CheckBox,x :Int,musclee :String) {
        if (cektrue.isChecked == true){
            vsble.setImageResource(x)
            muscle = musclee
            cekfalse1.isChecked = false
            cekfalse2.isChecked = false
            cekfalse3.isChecked = false
            cekfalse4.isChecked = false
        }
        else{
            muscle = ""
            vsble.setImageResource(R.drawable.gambar1)
        }
    }

    fun animbutton(cek :CheckBox){
        if((cek.isChecked==true) and (cekk == true)){
            requireView().findViewById<ImageView>(R.id.button).visibility = View.VISIBLE
            val anim1 : ObjectAnimator = ObjectAnimator.ofFloat(requireView().findViewById<ImageView>(R.id.button),View.ALPHA,0f,1f)
            anim1.duration = 1200
            val anim2 :ObjectAnimator = ObjectAnimator.ofFloat(requireView().findViewById<ImageView>(R.id.button),View.SCALE_Y,0f,1.5f,1f)
            anim2.duration = 1200
            val anim3 :ObjectAnimator = ObjectAnimator.ofFloat(requireView().findViewById<ImageView>(R.id.button),View.SCALE_X,0f,1.5f,1f)
            anim3.duration = 1200
            val anim : AnimatorSet = AnimatorSet()
            anim.playTogether(anim1,anim2,anim3)
            anim.start()
            cekk = false
        }
        else if ((cek.isChecked==false) and (cekk == false)){
            val anim1 :ObjectAnimator = ObjectAnimator.ofFloat(requireView().findViewById<ImageView>(R.id.button),View.ALPHA,1f,0f)
            anim1.duration = 1200
            val anim2 :ObjectAnimator = ObjectAnimator.ofFloat(requireView().findViewById<ImageView>(R.id.button),View.SCALE_Y,1f,0f)
            anim2.duration = 1200
            val anim3 :ObjectAnimator = ObjectAnimator.ofFloat(requireView().findViewById<ImageView>(R.id.button),View.SCALE_X,1f,0f)
            anim3.duration = 1200
            val anim :AnimatorSet = AnimatorSet()
            anim.playTogether(anim1,anim2,anim3)
            anim.start()
            cekk = true
        }
    }

    fun uncheck(){
        requireView().findViewById<CheckBox>(R.id.full).isChecked = false
        requireView().findViewById<CheckBox>(R.id.abs).isChecked = false
        requireView().findViewById<CheckBox>(R.id.arm).isChecked = false
        requireView().findViewById<CheckBox>(R.id.leg).isChecked = false
        requireView().findViewById<CheckBox>(R.id.chest).isChecked = false
    }

}
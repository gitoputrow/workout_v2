package com.example.workout_v2

import android.app.AlertDialog
import android.content.ContentValues
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.fragment.findNavController
import coil.load
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import de.hdodenhof.circleimageview.CircleImageView
import java.io.ByteArrayOutputStream

class ProfileFragment : Fragment() {
    val database = FirebaseDatabase.getInstance().getReference()
    val storage = Firebase.storage("gs://workout-v2-3f3b3.appspot.com").reference
    var x = ""
    val firebaseAuth = FirebaseAuth.getInstance()
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
        val ambil :String = this.requireArguments().getString("username").toString()
        x = ambil
        view.findViewById<TextView>(R.id.username_profile).setText(ambil)
        view.findViewById<TextView>(R.id.logout).setOnClickListener {
            firebaseAuth.signOut()
            startActivity(Intent(activity,Login::class.java))
            requireActivity().finish()
        }
        view.findViewById<CircleImageView>(R.id.foto_profile).setOnClickListener {
            val array = arrayOf("Take Picture","Select Picture","cancel")
            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle("Add Picture")
            builder.setItems(array, DialogInterface.OnClickListener { dialog, which ->
                if (array[which].equals("Take Picture")){
                    val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    startActivityForResult(intent,0)
                }
                else if (array[which].equals("Select Picture")){
                    val intent = Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                    startActivityForResult(intent,1)
                }
                else{
                    dialog.dismiss()
                }
            })
            builder.show()
        }
        database.child("data$ambil").addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.hasChild("postingan")){
                    view.findViewById<TextView>(R.id.jumlah_post).setText(snapshot.child("postingan").childrenCount.toInt().toString())
                    var jumlah_like = 0
                    for (childname in snapshot.child("postingan").children){
                        if (childname.hasChild("like")){
                            jumlah_like += childname.child("like").childrenCount.toInt()
                        }
                    }
                    view.findViewById<TextView>(R.id.jumlah_like).setText(jumlah_like.toString())
                }
                view.findViewById<CircleImageView>(R.id.foto_profile).load(snapshot.child("fp").value.toString())
                view.findViewById<TextView>(R.id.name_profile).setText(snapshot.child("name").value.toString())
                view.findViewById<TextView>(R.id.full_progress).setText("${snapshot.child("MuscleProgress").child("full body").value.toString()}%")
                view.findViewById<TextView>(R.id.abs_progress).setText("${snapshot.child("MuscleProgress").child("abs").value.toString()}%")
                view.findViewById<TextView>(R.id.arm_progress).setText("${snapshot.child("MuscleProgress").child("arm").value.toString()}%")
                view.findViewById<TextView>(R.id.leg_progress).setText("${snapshot.child("MuscleProgress").child("leg").value.toString()}%")
                view.findViewById<TextView>(R.id.chest_progress).setText("${snapshot.child("MuscleProgress").child("chest").value.toString()}%")
            }

        })
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 0){
            if (data != null){
                if (data.extras != null) {
                    requireView().findViewById<ConstraintLayout>(R.id.progressbar_bg).visibility = View.VISIBLE
                    val bitmap: Bitmap = data!!.extras!!.get("data") as Bitmap
                    requireView().findViewById<CircleImageView>(R.id.foto_profile).setImageBitmap(bitmap)
                    val bitmap1 = (requireView().findViewById<CircleImageView>(R.id.foto_profile).drawable as BitmapDrawable).bitmap
                    val baos = ByteArrayOutputStream()
                    bitmap1.compress(Bitmap.CompressFormat.JPEG, 100, baos)
                    val data = baos.toByteArray()
                    storage.child("profile${x}")
                            .putBytes(data).addOnCompleteListener{
                                if (it.isSuccessful){
                                    storage.child("profile${x}").downloadUrl.addOnSuccessListener(object : OnSuccessListener<Uri> {
                                        override fun onSuccess(p0: Uri?) {
                                            requireView().findViewById<ConstraintLayout>(R.id.progressbar_bg).visibility = View.INVISIBLE
                                            database.child("data$x").child("fp").setValue(p0.toString())
                                        }
                                    })
                                }
                            }
                }
            }
        }
        else {
            if (data != null) {
                requireView().findViewById<ConstraintLayout>(R.id.progressbar_bg).visibility = View.VISIBLE
                requireView().findViewById<CircleImageView>(R.id.foto_profile).setImageURI(data?.data)
                val bitmap1 = (requireView().findViewById<CircleImageView>(R.id.foto_profile).drawable as BitmapDrawable).bitmap
                val baos = ByteArrayOutputStream()
                bitmap1.compress(Bitmap.CompressFormat.JPEG, 100, baos)
                val data = baos.toByteArray()
                storage.child("profile${x}")
                        .putBytes(data).addOnCompleteListener{
                            if (it.isSuccessful){
                                storage.child("profile${x}").downloadUrl.addOnSuccessListener(object : OnSuccessListener<Uri> {
                                    override fun onSuccess(p0: Uri?) {
                                        requireView().findViewById<ConstraintLayout>(R.id.progressbar_bg).visibility = View.INVISIBLE
                                        database.child("data$x").child("fp").setValue(p0.toString())
                                    }
                                })
                            }
                        }
            }
        }
    }
}
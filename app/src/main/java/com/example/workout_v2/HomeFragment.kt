package com.example.workout_v2

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import java.io.ByteArrayOutputStream

class HomeFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<ImageView>(R.id.upload_button).setOnClickListener {
            val array = arrayOf("Take Picture","Select Picture","Cancel")
            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle("Add Picture")
            builder.setItems(array,DialogInterface.OnClickListener { dialog, which ->
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
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 0){
            if (data != null){
                if (data.extras != null) {
                    val bitmap: Bitmap = data!!.extras!!.get("data") as Bitmap
                    val pindah = Intent(activity,post::class.java)
                    pindah.putExtra("bitmap",bitmap)
                    pindah.putExtra("jenis","kamera")
                    startActivity(pindah)
                    requireActivity().finish()
                }
            }
        }
        else {
            if (data != null) {
                val pindah = Intent(activity,post::class.java)
                pindah.putExtra("data",data?.data)
                pindah.putExtra("jenis","file")
                startActivity(pindah)
                requireActivity().finish()
            }
        }
    }
}

package com.example.workout_v2

import android.app.AlertDialog
import android.content.ContentValues
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.contentValuesOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.io.ByteArrayOutputStream

class HomeFragment : Fragment(), clicklistener {

    private var layoutManager: RecyclerView.LayoutManager? = null
    private lateinit var adapter: feedadapter
    private val list_feed = ArrayList<feeditem>()
    val database = FirebaseDatabase.getInstance().getReference()
    var username = ""
    data class list_class(var foto: String, var username: String, var childname: String, var name: String, var text: String, var date: String,var fp: String)
    val listt = mutableListOf<list_class>()
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
        val ambil: String = this.requireArguments().getString("username").toString()
        username = ambil
        view.findViewById<ImageView>(R.id.upload_button).setOnClickListener {
            val array = arrayOf("Take Picture", "Select Picture", "Cancel")
            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle("Add Picture")
            builder.setItems(array, DialogInterface.OnClickListener { dialog, which ->
                if (array[which].equals("Take Picture")) {
                    val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    startActivityForResult(intent, 0)
                } else if (array[which].equals("Select Picture")) {
                    val intent = Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                    startActivityForResult(intent, 1)
                } else {
                    dialog.dismiss()
                }
            })
            builder.show()
        }
        view.findViewById<ImageView>(R.id.search_but).setOnClickListener {
            listt.clear()
            list_feed.clear()
            if (view.findViewById<TextView>(R.id.search_text).text.isEmpty()){
                recyclerViewInflater("")
            }
            else{
                recyclerViewInflater(view.findViewById<TextView>(R.id.search_text).text.toString())
            }
        }
        recyclerViewInflater("")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 0) {
            if (data != null) {
                if (data.extras != null) {
                    val bitmap: Bitmap = data!!.extras!!.get("data") as Bitmap
                    val pindah = Intent(activity, post::class.java)
                    pindah.putExtra("bitmap", bitmap)
                    pindah.putExtra("jenis", "kamera")
                    pindah.putExtra("username", username)
                    startActivity(pindah)
                    requireActivity().finish()
                }
            }
        } else {
            if (data != null) {
                val pindah = Intent(activity, post::class.java)
                pindah.putExtra("data", data?.data)
                pindah.putExtra("jenis", "file")
                pindah.putExtra("username", username)
                startActivity(pindah)
                requireActivity().finish()
            }
        }
    }

    private fun recyclerViewInflater(cari : String) {
        if (cari.equals("")) {
            database.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    for (childmain in snapshot.children) {
                        var childdata = childmain.key.toString()
                        var username = snapshot.child(childdata).child("username").value.toString()
                        var name = snapshot.child(childdata).child("name").value.toString()
                        var fp :String = snapshot.child(childdata).child("fp").value.toString()
                        if (snapshot.child(childdata).hasChild("postingan")) {
                            var text = ""
                            for (child in snapshot.child(childdata).child("postingan").children) {
                                val childname = child.key.toString()
                                var childnametext = snapshot.child(childdata).child("postingan").child(childname).child("text").value.toString()
                                var date = snapshot.child(childdata).child("postingan").child(childname).child("date").value.toString()
                                var foto = snapshot.child(childdata).child("postingan").child(childname).child("foto").value.toString()
                                if (childnametext.length > 20) {
                                    text = "${childnametext.removeRange(20, childnametext.length)}...."
                                } else {
                                    text = childnametext
                                }
                                listt.add(list_class(foto, username, childname, name, text, date,fp))
                            }
                        }
                    }
                    listt.sortByDescending { listClass -> listClass.date }
                    Log.d(ContentValues.TAG, listt.toString());
                    for (i in 0 until listt.size) {
                        Log.d(ContentValues.TAG, i.toString())
                        list(i, listt)
                    }

                }
            })
        }
        else{
            database.child("data$cari").addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    var username = snapshot.child("username").value.toString()
                    var name = snapshot.child("name").value.toString()
                    var fp :String = snapshot.child("fp").value.toString()
                    if (snapshot.hasChild("postingan")) {
                        var text = ""
                        for (child in snapshot.child("postingan").children) {
                            val childname = child.key.toString()
                            var childnametext = snapshot.child("postingan").child(childname).child("text").value.toString()
                            var date = snapshot.child("postingan").child(childname).child("date").value.toString()
                            var foto = snapshot.child("postingan").child(childname).child("foto").value.toString()
                            if (childnametext.length > 20) {
                                text = "${childnametext.removeRange(20, childnametext.length)}...."
                            } else {
                                text = childnametext
                            }
                            listt.add(list_class(foto, username, childname, name, text, date,fp))
                        }
                    }
                    listt.sortByDescending { listClass -> listClass.date }
                    Log.d(ContentValues.TAG, listt.toString());
                    for (i in 0 until listt.size) {
                        Log.d(ContentValues.TAG, i.toString())
                        list(i, listt)
                    }
                }
            })
        }

    }

    fun list(i: Int, listt: MutableList<list_class>) {
        list_feed.add(feeditem(listt[i].foto, listt[i].name, listt[i].username, listt[i].childname, listt[i].text,listt[i].fp))
        requireView().findViewById<RecyclerView>(R.id.rv_feed).setHasFixedSize(true)
        requireView().findViewById<RecyclerView>(R.id.rv_feed).layoutManager = LinearLayoutManager(this.requireContext())
        val adapter = feedadapter(list_feed, this)
        requireView().findViewById<RecyclerView>(R.id.rv_feed).adapter = adapter
    }

    override fun onStop() {
        super.onStop()
        listt.clear()
        list_feed.clear()
    }

    override fun onitemclick(item: feeditem, position: Int) {
        val pindah = Intent(activity, DetailPost::class.java)
        pindah.putExtra("usernamepost", item.username)
        pindah.putExtra("username", username)
        pindah.putExtra("postid", item.postid)
        pindah.putExtra("foto", item.photo)
        pindah.putExtra("fp", item.fp)
        startActivity(pindah)
        requireActivity().finish()
    }
}



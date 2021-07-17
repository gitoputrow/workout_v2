package com.example.workout_v2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.workout_v2.feeditem
import com.google.android.material.textview.MaterialTextView
import com.google.android.material.imageview.ShapeableImageView
import coil.load


class feedadapter(private val item : List<feeditem>,var clicklistener : clicklistener ) : RecyclerView.Adapter<feedadapter.ViewHolder>(){
    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun insert(item: feeditem, id: Int, action: clicklistener) {
            view.findViewById<ImageView>(R.id.feed_photo).load(item.photo)
            view.findViewById<TextView>(R.id.feed_name).text = item.name
            view.findViewById<MaterialTextView>(R.id.feed_user).text = item.username
            view.findViewById<MaterialTextView>(R.id.feed_caption).text = item.content
            itemView.setOnClickListener {
                action.onitemclick(item,adapterPosition)
            }
//            view.findViewById<ImageView>(R.id.delete_feed).setOnClickListener {
//                action.deleteitem(item,adapterPosition)
//            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.activity_feed_layout, parent, false))

    override fun getItemCount(): Int {
        return item.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.insert(item.get(position), position + 1,clicklistener)
    }
}

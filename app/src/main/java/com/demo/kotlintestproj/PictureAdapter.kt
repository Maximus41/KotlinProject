package com.demo.kotlintestprojtestapp

import Repository.Model.Picture
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.gallery_item.view.*

class PictureAdapter(private var context : Context, private var pics : ArrayList<Picture>, private var onClick : (pic_id : Int) -> Unit) : RecyclerView.Adapter<PictureAdapter.PictureViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PictureViewHolder {
        return PictureViewHolder(
            LayoutInflater.from(context).inflate(R.layout.gallery_item,
            parent,
            false))
    }

    override fun getItemCount(): Int {
        return pics.size
    }

    override fun onBindViewHolder(holder: PictureViewHolder, position: Int) {
        var picture = pics[position]
        holder.title.text = picture.title
        holder.uploader.text = picture.uploader
        Picasso.get().load(picture.avatar)
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.error)
            .fit()
            .centerCrop()
            .into(holder.imageView)
        holder.imageView.setOnClickListener { v -> onClick(picture.pic_id)}

    }

    fun addPics(images : ArrayList<Picture>) {
        pics.addAll(images)
        notifyDataSetChanged()
    }

    class PictureViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val parent = itemView.parent
        val imageView = itemView.dpView
        val title = itemView.tvTitle
        val uploader = itemView.tvUploader
    }
}
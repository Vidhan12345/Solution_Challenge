package com.example.solution_challenge.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.solution_challenge.databinding.MyPostDesignBinding
import com.example.solution_challenge.model.Post
import com.squareup.picasso.Picasso

class PostAdapter(var context: Context, var postList: ArrayList<Post>):RecyclerView.Adapter<PostAdapter.ViewHolder>() {


    inner class ViewHolder(var binding: MyPostDesignBinding): RecyclerView.ViewHolder(binding.root)

    @SuppressLint("SuspiciousIndentation")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
      var binding = MyPostDesignBinding.inflate(LayoutInflater.from(context),parent,false)
        return ViewHolder(binding)
//        val view =  LayoutInflater.from(parent.context).inflate(R.layout.note_item,parent,false)
//        return NoteViewHolder(view)
    }

    override fun getItemCount(): Int {
 return postList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.userPostImage
        holder.binding.userPostCaption.text =  postList[position].caption

//        holder.binding.userPostName.text = postList.get(position).name
//        Picasso.get().load(postList[position].postUrl).into(holder.binding.userPostImage)
    }

}
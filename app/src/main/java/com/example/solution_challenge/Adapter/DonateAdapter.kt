package com.example.solution_challenge.Adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.solution_challenge.databinding.ActivityDonateBinding
import com.example.solution_challenge.databinding.DonateItemBinding
import com.example.solution_challenge.model.Donate
import com.squareup.picasso.Picasso

class DonateAdapter(var context:Context,var donateList: ArrayList<Donate>):RecyclerView.Adapter<DonateAdapter.ViewHolder>() {

    public class ViewHolder(var binding: DonateItemBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
var binding = DonateItemBinding.inflate(LayoutInflater.from(context),parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
      return donateList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.contactNumber.text = donateList[position].number
        holder.binding.location.text = donateList[position].location
        holder.binding.userName.text = donateList[position].name
        holder.binding.foodDescription.text=donateList[position].description
     holder.binding.foodImg
        Picasso.get().load(donateList[position].imgUrl).into(holder.binding.foodImg)
//      Picasso.get().load(postList[position].postUrl).into(holder.binding.userPostImage)
                holder.binding.dir.setOnClickListener {
                    change()
        }
    }

    private fun change() {
        val source = "21.105510529892424, 79.00322354747368"
        val destination = "21.095100665790703, 78.9783755653517"

        val uri = Uri.parse("https://www.google.com/maps/dir/$source/$destination")
        val intent = Intent(Intent.ACTION_VIEW, uri)
         intent.`package` = "com.google.android.apps.maps"
         startActivity(context,intent,null)

    }
}
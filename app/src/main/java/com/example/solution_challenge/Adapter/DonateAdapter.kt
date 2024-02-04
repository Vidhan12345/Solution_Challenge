package com.example.solution_challenge.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
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
    }
}
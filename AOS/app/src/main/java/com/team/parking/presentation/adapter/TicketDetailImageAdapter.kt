package com.team.parking.presentation.adapter

import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.team.parking.R
import com.team.parking.data.model.map.AddressResponse
import com.team.parking.databinding.ItemAddShareParkingLotBinding
import com.team.parking.databinding.ItemSearchPlaceBinding

class TicketDetailImageAdapter : RecyclerView.Adapter<TicketDetailImageAdapter.ImageViewHolder>(){

    private val callback = object : DiffUtil.ItemCallback<String>(){
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

    }
    val differ = AsyncListDiffer(this,callback)

    inner class ImageViewHolder(val binding : ItemAddShareParkingLotBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(data : String){
            Glide.with(binding.imageItemAddShareParkingLot.context)
                .load(data.toUri())
                .error(R.drawable.icon_no_image)
                .into(binding.imageItemAddShareParkingLot)
            binding.imageItemAddShareParkingLot.scaleType = ImageView.ScaleType.CENTER_CROP
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val binding = ItemAddShareParkingLotBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}
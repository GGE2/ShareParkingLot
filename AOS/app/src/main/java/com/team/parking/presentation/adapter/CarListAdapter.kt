package com.team.parking.presentation.adapter

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.team.parking.R
import com.team.parking.data.model.car.CarListResponse
import com.team.parking.data.model.map.Place
import com.team.parking.databinding.ItemMyCarBinding
class CarListAdapter(val resources: Resources) : RecyclerView.Adapter<CarListAdapter.CarViewHolder>() {

    private lateinit var onCarClickListener: CarOnClickListener

    private val callback = object : DiffUtil.ItemCallback<CarListResponse>(){
        override fun areItemsTheSame(oldItem: CarListResponse, newItem: CarListResponse): Boolean {
            return oldItem.carId == newItem.carId
        }

        override fun areContentsTheSame(oldItem: CarListResponse, newItem: CarListResponse): Boolean {
            return oldItem == newItem
        }

    }
    val differ = AsyncListDiffer(this,callback)

    inner class CarViewHolder(val binding : ItemMyCarBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(data : CarListResponse){
            binding.apply {
                root.background = ResourcesCompat.getDrawable(resources, R.drawable.day_select_white_background, null)
                textNumberItemMyCar.text = data.carStr
                if(!data.carRep){
                    imageRepresentMyCar.visibility = View.GONE
                }
                root.setOnClickListener {
                    onCarClickListener.onClick(it, layoutPosition, data)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarViewHolder {
        val binding = ItemMyCarBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CarViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CarViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun setOnCarClickListener(onItemClickListener: CarOnClickListener){
        this.onCarClickListener = onItemClickListener
    }

    interface CarOnClickListener{
        fun onClick(view: View,position:Int,data: CarListResponse)
    }
}
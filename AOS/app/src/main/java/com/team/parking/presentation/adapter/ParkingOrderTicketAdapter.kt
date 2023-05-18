package com.team.parking.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.team.parking.databinding.ItemTicketBinding

class ParkingOrderTicketAdapter(val datas:List<String>) : RecyclerView.Adapter<ParkingOrderTicketAdapter.OrderTicketViewHolder>() {
    class OrderTicketViewHolder(val binding : ItemTicketBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(data:String){
            binding.tvItemTicketTitle.text = data
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderTicketViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemTicketBinding.inflate(inflater)

        return OrderTicketViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OrderTicketViewHolder, position: Int) {
        holder.bind(datas[position])
    }

    override fun getItemCount(): Int {
        return datas.size
    }
}
package com.team.parking.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.team.parking.data.model.ticket.TicketBoughtListResponse
import com.team.parking.databinding.ItemMyTicketBinding

class BoughtTicketAdapter : RecyclerView.Adapter<BoughtTicketAdapter.BoughtTicketViewHolder>(){

    private lateinit var onBoughtTicketClickListener: BoughtTicketClickListener

    private val callback = object : DiffUtil.ItemCallback<TicketBoughtListResponse>(){
        override fun areItemsTheSame(oldItem: TicketBoughtListResponse, newItem: TicketBoughtListResponse): Boolean {
            return oldItem.ticketId == newItem.ticketId
        }

        override fun areContentsTheSame(oldItem: TicketBoughtListResponse, newItem: TicketBoughtListResponse): Boolean {
            return oldItem == newItem
        }

    }
    val differ = AsyncListDiffer(this,callback)

    inner class BoughtTicketViewHolder(val binding : ItemMyTicketBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(data : TicketBoughtListResponse){
            binding.apply {
                root.setOnClickListener {
                    onBoughtTicketClickListener.onClick(it, layoutPosition, data)
                }
                textParkingLotNameItemMyTicket.text = data.parkingRegion
                textDayItemMyTicket.text = data.parkingDate
                textPriceItemMyTicket.text = "${data.ptToLose}원"
                textEntranceTimeItemMyTicket.text = "입차 시간 : ${data.inTiming}:00"
                textExitTimeItemMyTicket.text = "출차 시간 : ${data.outTime}:00"
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BoughtTicketViewHolder {
        val binding = ItemMyTicketBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return BoughtTicketViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BoughtTicketViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
    fun setOnBoughtTicketClickListener(onItemClickListener: BoughtTicketClickListener){
        this.onBoughtTicketClickListener = onItemClickListener
    }

    interface BoughtTicketClickListener{
        fun onClick(view: View,position:Int,data:TicketBoughtListResponse)
    }
}
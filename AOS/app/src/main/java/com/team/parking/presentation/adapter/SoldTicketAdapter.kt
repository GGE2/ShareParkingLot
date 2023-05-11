package com.team.parking.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.team.parking.data.model.ticket.TicketSoldListResponse
import com.team.parking.databinding.ItemMyTicketBinding

class SoldTicketAdapter : RecyclerView.Adapter<SoldTicketAdapter.SoldTicketViewHolder>(){

    private lateinit var onSoldTicketClickListener: SoldTicketClickListener

    private val callback = object : DiffUtil.ItemCallback<TicketSoldListResponse>(){
        override fun areItemsTheSame(oldItem: TicketSoldListResponse, newItem: TicketSoldListResponse): Boolean {
            return oldItem.ticketId == newItem.ticketId
        }

        override fun areContentsTheSame(oldItem: TicketSoldListResponse, newItem: TicketSoldListResponse): Boolean {
            return oldItem == newItem
        }

    }
    val differ = AsyncListDiffer(this,callback)

    inner class SoldTicketViewHolder(val binding : ItemMyTicketBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(data : TicketSoldListResponse){
            binding.apply {
                root.setOnClickListener {
                    onSoldTicketClickListener.onClick(it, layoutPosition, data)
                }
                textParkingLotNameItemMyTicket.text = data.parkingRegion
                textDayItemMyTicket.text = data.parkingDate
                textPriceItemMyTicket.text = "${data.ptToEarn}원"
                textEntranceTimeItemMyTicket.text = "입차 시간 : ${data.inTiming}:00"
                textExitTimeItemMyTicket.text = "출차 시간 : ${data.outTime}:00"
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SoldTicketViewHolder {
        val binding = ItemMyTicketBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return SoldTicketViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SoldTicketViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
    fun setOnSoldTicketClickListener(onItemClickListener: SoldTicketClickListener){
        this.onSoldTicketClickListener = onItemClickListener
    }

    interface SoldTicketClickListener{
        fun onClick(view: View,position:Int,data:TicketSoldListResponse)
    }
}
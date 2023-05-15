package com.team.parking.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.team.parking.R
import com.team.parking.data.model.map.MapDetailResponse
import com.team.parking.data.model.map.MapOrderResponse
import com.team.parking.databinding.ItemParkingLotBinding

class ParkingOrderByAdapter : RecyclerView.Adapter<ParkingOrderByAdapter.ParkingOrderViewHolder>(){

    private lateinit var onParkingItemClickListener : ParkingItemClickListener
    private lateinit var parkingOrderTicketAdapter: ParkingOrderTicketAdapter

    private val callback = object : DiffUtil.ItemCallback<MapOrderResponse>(){
        override fun areItemsTheSame(
            oldItem: MapOrderResponse,
            newItem: MapOrderResponse
        ): Boolean {
            return oldItem.parkId == newItem.parkId
        }

        override fun areContentsTheSame(
            oldItem: MapOrderResponse,
            newItem: MapOrderResponse
        ): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this,callback)
    inner class ParkingOrderViewHolder(val binding : ItemParkingLotBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(data:MapOrderResponse){
            var tickets = ArrayList<String>()
            if(data.feeBasic==0){
                tickets.add("무료")
            }
            else{
                for(i in 0 until 3)
                    tickets.add("${i+1}시간 ${data.feeBasic*(i+1)}원")
            }
            parkingOrderTicketAdapter = ParkingOrderTicketAdapter(tickets)
            binding.rvItemParkingTickets.adapter = parkingOrderTicketAdapter
            binding.rvItemParkingTickets.layoutManager = LinearLayoutManager(binding.rvItemParkingTickets.context,LinearLayoutManager.HORIZONTAL,false)
            binding.clParent.setOnClickListener {
                onParkingItemClickListener.onClick(it,layoutPosition,data)
            }
            binding.data = data
        }

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParkingOrderViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemParkingLotBinding>(inflater, R.layout.item_parking_lot,parent,false)

        return ParkingOrderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ParkingOrderViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
    fun setOnParkingItemClickListener(onParkingItemClickListener: ParkingItemClickListener){
        this.onParkingItemClickListener = onParkingItemClickListener
    }
    interface ParkingItemClickListener{
        fun onClick(view: View, position: Int,data:MapOrderResponse)
    }
}
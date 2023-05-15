package com.team.parking.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.team.parking.data.model.point.SpentPointResponse
import com.team.parking.databinding.ItemTransactionHistoryBinding

class SpentPointAdapter : RecyclerView.Adapter<SpentPointAdapter.SpentPointViewHolder>() {
    private val callback = object : DiffUtil.ItemCallback<SpentPointResponse>(){
        override fun areItemsTheSame(oldItem: SpentPointResponse, newItem: SpentPointResponse): Boolean {
            return oldItem.lotName == newItem.lotName && oldItem.transactionDate == newItem.transactionDate
        }

        override fun areContentsTheSame(oldItem: SpentPointResponse, newItem: SpentPointResponse): Boolean {
            return oldItem == newItem
        }

    }
    val differ = AsyncListDiffer(this,callback)

    inner class SpentPointViewHolder(val binding : ItemTransactionHistoryBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(data : SpentPointResponse){
            binding.apply {
                textParkingLotItemTransactionHistory.text = data.lotName
                textDayItemTransactionHistory.text = data.transactionDate
                textPriceItemTransactionHistory.text = "${data.ptLose}원"
                textTicketTypeItemTransactionHistory.text = when(data.type){
                    0 -> "1시간"
                    1 -> "3시간"
                    2 -> "5시간"
                    3 -> "종일권"
                    else -> ""
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpentPointViewHolder {
        val binding = ItemTransactionHistoryBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return SpentPointViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SpentPointViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}
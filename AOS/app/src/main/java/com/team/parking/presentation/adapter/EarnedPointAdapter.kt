package com.team.parking.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.team.parking.data.model.point.EarnedPointResponse
import com.team.parking.databinding.ItemTransactionHistoryBinding

class EarnedPointAdapter : RecyclerView.Adapter<EarnedPointAdapter.EarnedPointViewHolder>() {

    private val callback = object : DiffUtil.ItemCallback<EarnedPointResponse>(){
        override fun areItemsTheSame(oldItem: EarnedPointResponse, newItem: EarnedPointResponse): Boolean {
            return oldItem.lotName == newItem.lotName && oldItem.transactionDate == newItem.transactionDate
        }

        override fun areContentsTheSame(oldItem: EarnedPointResponse, newItem: EarnedPointResponse): Boolean {
            return oldItem == newItem
        }

    }
    val differ = AsyncListDiffer(this,callback)

    inner class EarnedPointViewHolder(val binding : ItemTransactionHistoryBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(data : EarnedPointResponse){
            binding.apply {
                textParkingLotItemTransactionHistory.text = data.lotName
                textDayItemTransactionHistory.text = data.transactionDate
                textPriceItemTransactionHistory.text = "${data.ptGet}원"
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EarnedPointViewHolder {
        val binding = ItemTransactionHistoryBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return EarnedPointViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EarnedPointViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}
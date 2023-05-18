package com.team.parking.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.team.parking.data.model.favorite.FavoriteListResponse
import com.team.parking.data.model.ticket.TicketBoughtListResponse
import com.team.parking.databinding.ItemFavoriteBinding
import com.team.parking.databinding.ItemMyTicketBinding

class FavoriteAdapter : RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>(){

    private lateinit var onFavoriteClickListener: FavoriteClickListener
    private lateinit var onFavoriteImageClickListener: FavoriteImageClickListener

    private val callback = object : DiffUtil.ItemCallback<FavoriteListResponse>(){
        override fun areItemsTheSame(oldItem: FavoriteListResponse, newItem: FavoriteListResponse): Boolean {
            return oldItem.parkId == newItem.parkId
        }

        override fun areContentsTheSame(oldItem: FavoriteListResponse, newItem: FavoriteListResponse): Boolean {
            return oldItem == newItem
        }

    }
    val differ = AsyncListDiffer(this,callback)

    inner class FavoriteViewHolder(val binding : ItemFavoriteBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(data : FavoriteListResponse){
            binding.apply {
                root.setOnClickListener {
                    onFavoriteClickListener.onClick(it, layoutPosition, data)
                }
                imageFavoriteItemFavorite.setOnClickListener {
                    onFavoriteImageClickListener.onClick(it, layoutPosition, data)
                }
                textTitleItemFavorite.text = data.parkingName
                textPayTypeItemFavorite.text = data.payType
                textAllDayPriceFavorite.text = "평일 종일 ${data.feeBasic * 24}원"
                textTimePriceFavorite.text = "평일 오후 ${data.feeBasic}원"
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val binding = ItemFavoriteBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return FavoriteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
    fun setOnFavoriteClickListener(onItemClickListener: FavoriteClickListener){
        this.onFavoriteClickListener = onItemClickListener
    }

    fun setOnFavoriteImageClickListener(onItemClickListener: FavoriteImageClickListener){
        this.onFavoriteImageClickListener = onItemClickListener
    }

    interface FavoriteClickListener{
        fun onClick(view: View,position:Int,data:FavoriteListResponse)
    }

    interface FavoriteImageClickListener{
        fun onClick(view: View,position:Int,data:FavoriteListResponse)
    }
}
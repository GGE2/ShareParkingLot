package com.team.parking.presentation.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.team.parking.data.model.map.AddressResponse
import com.team.parking.databinding.ItemSearchPlaceBinding

private const val TAG = "SearchAdapter_지훈"
class SearchAddressAdapter : RecyclerView.Adapter<SearchAddressAdapter.SearchViewHolder>(){

    private lateinit var onSearchItemClickListener: SearchItemClickListener

    private val callback = object : DiffUtil.ItemCallback<AddressResponse>(){
        override fun areItemsTheSame(oldItem: AddressResponse, newItem: AddressResponse): Boolean {
            return oldItem.x == newItem.x && oldItem.y == newItem.y
        }

        override fun areContentsTheSame(oldItem: AddressResponse, newItem: AddressResponse): Boolean {
            return oldItem == newItem
        }

    }
    val differ = AsyncListDiffer(this,callback)

    inner class SearchViewHolder(val binding : ItemSearchPlaceBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(data : AddressResponse){
            binding.tvItemSearchPlaceName.text = data.address?.address_name
            binding.tvItemSearchPlaceAddress.text = data.road_address?.address_name
            binding.clItemSearchPlace.setOnClickListener {
                onSearchItemClickListener.onClick(it,layoutPosition,data)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val binding = ItemSearchPlaceBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return SearchViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
    fun setOnSearchItemClickListener(onItemClickListener: SearchItemClickListener){
        this.onSearchItemClickListener = onItemClickListener
    }

    interface SearchItemClickListener{
        fun onClick(view: View,position:Int,data:AddressResponse)
    }
}
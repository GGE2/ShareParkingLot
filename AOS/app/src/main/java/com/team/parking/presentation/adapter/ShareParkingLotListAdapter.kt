package com.team.parking.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.team.parking.data.model.parkinglot.ShareLotResponse
import com.team.parking.databinding.ItemMyShareParkingLotBinding

class ShareParkingLotListAdapter : RecyclerView.Adapter<ShareParkingLotListAdapter.ListViewHolder>(){

    private lateinit var onShareParkingLotDeleteClickListener: ShareParkingLotDeleteClickListener
    private lateinit var onShareParkingLotCheckClickListener: ShareParkingLotCheckClickListener
    private lateinit var onShareParkingLotEditClickListener: ShareParkingLotEditClickListener

    private val callback = object : DiffUtil.ItemCallback<ShareLotResponse>(){
        override fun areItemsTheSame(oldItem: ShareLotResponse, newItem: ShareLotResponse): Boolean {
            return oldItem.shareLotId == newItem.shareLotId
        }

        override fun areContentsTheSame(oldItem: ShareLotResponse, newItem: ShareLotResponse): Boolean {
            return oldItem == newItem
        }

    }
    val differ = AsyncListDiffer(this,callback)

    inner class ListViewHolder(val binding : ItemMyShareParkingLotBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(data : ShareLotResponse){
            binding.textNameItemMyShareParkingLot.text = data.shaName
            binding.imageDeleteItemMyShareParkingLot.setOnClickListener {
                onShareParkingLotDeleteClickListener.onClick(it, layoutPosition, data.shareLotId)
            }
            binding.layoutCheckItemMyShareParkingLot.setOnClickListener {
                onShareParkingLotCheckClickListener.onClick(it, layoutPosition, data.shareLotId)
            }
            binding.layoutEditItemMyShareParkingLot.setOnClickListener {
                onShareParkingLotEditClickListener.onClick(it, layoutPosition, data.shareLotId)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemMyShareParkingLotBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun setShareParkingLotDeleteClickListener(onItemClickListener: ShareParkingLotDeleteClickListener){
        onShareParkingLotDeleteClickListener = onItemClickListener
    }

    fun setShareParkingLotCheckClickListener(onItemClickListener: ShareParkingLotCheckClickListener){
        onShareParkingLotCheckClickListener = onItemClickListener
    }

    fun setShareParkingLotEditClickListener(onItemClickListener: ShareParkingLotEditClickListener){
        onShareParkingLotEditClickListener = onItemClickListener
    }

    interface ShareParkingLotDeleteClickListener{
        fun onClick(view: View,position:Int,data:Long)
    }

    interface ShareParkingLotCheckClickListener{
        fun onClick(view: View,position:Int,data:Long)
    }

    interface ShareParkingLotEditClickListener{
        fun onClick(view: View,position:Int,data:Long)
    }
}
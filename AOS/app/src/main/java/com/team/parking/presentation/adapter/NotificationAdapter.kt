package com.team.parking.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.team.parking.data.model.notification.GetNotiListRequest
import com.team.parking.databinding.ItemNotificationBinding

class NotificationAdapter : RecyclerView.Adapter<NotificationAdapter.CustomViewHolder>() {

    private lateinit var onNotificationClickListener: NotificationAdapter.NotificationClickListener
    private lateinit var onNotificationDragListener: NotificationAdapter.NotificationDragListener

    private val callback = object : DiffUtil.ItemCallback<GetNotiListRequest>() {
        override fun areItemsTheSame(
            oldItem: GetNotiListRequest,
            newItem: GetNotiListRequest,
        ): Boolean {
            return oldItem.noti_id == newItem.noti_id
        }

        override fun areContentsTheSame(
            oldItem: GetNotiListRequest,
            newItem: GetNotiListRequest
        ): Boolean {
            return oldItem == newItem
        }

    }

    inner class CustomViewHolder(val binding: ItemNotificationBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data : GetNotiListRequest){
            binding.apply {
                root.setOnClickListener {
                    onNotificationClickListener.onClick(it, layoutPosition, data)
                }
                root.setOnDragListener{it,event ->
                    onNotificationDragListener.onDrag(it,layoutPosition,data)
                    true
                }
                title.text = data.title
                body.text = data.content
            }
        }
    }

    val differ = AsyncListDiffer(this,callback)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CustomViewHolder {
        val binding =
            ItemNotificationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CustomViewHolder(binding)
    }



    override fun onBindViewHolder(holder: NotificationAdapter.CustomViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun setOnNotificationClickListener(onItemClickListener: NotificationAdapter.NotificationClickListener){
        this.onNotificationClickListener = onItemClickListener
    }
    fun setOnNotificationDragListener(onItemDragListener: NotificationAdapter.NotificationDragListener){
        this.onNotificationDragListener = onItemDragListener
    }

    interface NotificationClickListener{
        fun onClick(view: View,position:Int,data: GetNotiListRequest)
    }
    interface  NotificationDragListener{
        fun onDrag(view:View,position: Int,data: GetNotiListRequest)
    }
}

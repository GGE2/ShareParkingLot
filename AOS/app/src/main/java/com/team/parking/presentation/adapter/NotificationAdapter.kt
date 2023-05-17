package com.team.parking.presentation.adapter

import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.team.parking.data.model.car.CarListResponse
import com.team.parking.data.model.notification.Notification
import com.team.parking.data.model.ticket.TicketBoughtListResponse
import com.team.parking.databinding.FragmentNotificationBinding
import com.team.parking.databinding.ItemNotificationBinding

class NotificationAdapter : RecyclerView.Adapter<NotificationAdapter.CustomViewHolder>() {

    private lateinit var onNotificationClickListener: NotificationAdapter.NotificationClickListener

    private val callback = object : DiffUtil.ItemCallback<Notification>() {
        override fun areItemsTheSame(
            oldItem: Notification,
            newItem: Notification
        ): Boolean {
            return oldItem.place == newItem.place
        }

        override fun areContentsTheSame(
            oldItem: Notification,
            newItem: Notification
        ): Boolean {
            return oldItem == newItem
        }

    }

    inner class CustomViewHolder(val binding: ItemNotificationBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data : Notification){
            binding.apply {
                root.setOnClickListener {
                    onNotificationClickListener.onClick(it, layoutPosition, data)
                }
                title.text = data.title
                body.text = data.body
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

    interface NotificationClickListener{
        fun onClick(view: View,position:Int,data: Notification)
    }
}

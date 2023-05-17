package com.team.parking.presentation.fragment

import android.os.Bundle
import android.view.ContextMenu
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.team.parking.MainActivity
import com.team.parking.R
import com.team.parking.databinding.FragmentLoginBinding
import com.team.parking.databinding.FragmentNotificationBinding
import com.team.parking.databinding.FragmentParkingLotDetailBinding
import com.team.parking.presentation.adapter.NotificationAdapter
import com.team.parking.presentation.viewmodel.FavoriteViewModel
import com.team.parking.presentation.viewmodel.MapViewModel
import com.team.parking.presentation.viewmodel.UserViewModel


class NotificationFragment : Fragment() {
    private lateinit var binding: FragmentNotificationBinding
    private lateinit var userViewModel: UserViewModel
    private lateinit var notiAdapter: NotificationAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNotificationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        userViewModel = (activity as MainActivity).userViewModel
        notiAdapter = NotificationAdapter()

        binding.apply {
            handlers = this@NotificationFragment
            lifecycleOwner = this@NotificationFragment
            viewModel = userViewModel
            notiRecycler.adapter = notiAdapter
        }

    }

    fun getNotiList(){

    }





}
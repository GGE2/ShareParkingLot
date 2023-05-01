package com.team.parking.presentation.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.team.parking.databinding.FragmentPurchaseTicketBinding

class PurchaseTicketFragment : Fragment() {

    private lateinit var binding: FragmentPurchaseTicketBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPurchaseTicketBinding.inflate(inflater, container, false)
        return binding.root
    }


}
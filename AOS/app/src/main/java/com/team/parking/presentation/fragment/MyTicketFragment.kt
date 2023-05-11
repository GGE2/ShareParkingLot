package com.team.parking.presentation.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.team.parking.MainActivity
import com.team.parking.R
import com.team.parking.data.model.ticket.TicketBoughtListResponse
import com.team.parking.data.model.ticket.TicketSoldListResponse
import com.team.parking.databinding.FragmentMyTicketBinding
import com.team.parking.presentation.adapter.BoughtTicketAdapter
import com.team.parking.presentation.adapter.SoldTicketAdapter
import com.team.parking.presentation.viewmodel.MyTicketViewModel
import com.team.parking.presentation.viewmodel.ShareParkingLotViewModel
import com.team.parking.presentation.viewmodel.TicketDetailViewModel
import com.team.parking.presentation.viewmodel.UserViewModel

class MyTicketFragment : Fragment() {

    private lateinit var binding: FragmentMyTicketBinding
    private lateinit var myTicketViewModel: MyTicketViewModel
    private lateinit var userViewModel: UserViewModel
    private lateinit var shareParkingLotViewModel: ShareParkingLotViewModel
    private lateinit var boughtTicketAdapter: BoughtTicketAdapter
    private lateinit var soldTicketAdapter: SoldTicketAdapter
    private lateinit var ticketDetailViewModel: TicketDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyTicketBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        myTicketViewModel = (activity as MainActivity).myTicketViewModel
        userViewModel = (activity as MainActivity).userViewModel
        shareParkingLotViewModel = (activity as MainActivity).shareParkingLotViewModel
        ticketDetailViewModel = (activity as MainActivity).ticketDetailViewModel
        boughtTicketAdapter = BoughtTicketAdapter()
        boughtTicketAdapter.setOnBoughtTicketClickListener(object : BoughtTicketAdapter.BoughtTicketClickListener{
            override fun onClick(view: View, position: Int, data: TicketBoughtListResponse) {
                ticketDetailViewModel.ticketId = data.ticketId
                ticketDetailViewModel.buyer = true
                findNavController().navigate(R.id.action_myTicketFragment_to_ticketDetailFragment)
            }
        })
        soldTicketAdapter = SoldTicketAdapter()
        soldTicketAdapter.setOnSoldTicketClickListener(object : SoldTicketAdapter.SoldTicketClickListener{
            override fun onClick(view: View, position: Int, data: TicketSoldListResponse) {
                ticketDetailViewModel.ticketId = data.ticketId
                ticketDetailViewModel.buyer = false
                findNavController().navigate(R.id.action_myTicketFragment_to_ticketDetailFragment)
            }
        })
        if(myTicketViewModel.bought){
            binding.textTitleMyShareParkingLotTicket.text = "내 주차권"
            binding.recyclerViewMyShareParkingLotTicket.adapter = boughtTicketAdapter
            myTicketViewModel.getBoughtList(userViewModel.user!!.user_id)
            //bought
        } else {
            binding.textTitleMyShareParkingLotTicket.text = "구매 요청"
            binding.recyclerViewMyShareParkingLotTicket.adapter = soldTicketAdapter
            myTicketViewModel.getSoldList(userViewModel.user!!.user_id, shareParkingLotViewModel.sharelotId)
            //sold
        }
        myTicketViewModel.boughtList.observe(viewLifecycleOwner){
            boughtTicketAdapter.differ.submitList(it)
        }
        myTicketViewModel.soldList.observe(viewLifecycleOwner){
            soldTicketAdapter.differ.submitList(it)
        }
        binding.recyclerViewMyShareParkingLotTicket.layoutManager = LinearLayoutManager(requireContext())
        binding.imageBackMyShareParkingLotTicket.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }
}
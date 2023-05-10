package com.team.parking.presentation.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.team.parking.MainActivity
import com.team.parking.databinding.FragmentTicketDetailBinding
import com.team.parking.presentation.adapter.TicketDetailImageAdapter
import com.team.parking.presentation.viewmodel.TicketDetailViewModel
import com.team.parking.presentation.viewmodel.UserViewModel

class TicketDetailFragment : Fragment() {

    private lateinit var binding: FragmentTicketDetailBinding
    private lateinit var ticketDetailViewModel: TicketDetailViewModel
    private lateinit var userViewModel: UserViewModel
    private lateinit var ticketDetailImageAdapter: TicketDetailImageAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTicketDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ticketDetailViewModel = (activity as MainActivity).ticketDetailViewModel
        userViewModel = (activity as MainActivity).userViewModel
        ticketDetailViewModel.getTicketDetail()
        ticketDetailImageAdapter = TicketDetailImageAdapter()
        ticketDetailViewModel.ticketDetail.observe(viewLifecycleOwner){
            ticketDetailImageAdapter.differ.submitList(it.images)
            binding.apply {
                textParkingLotNameTicketDetail.text = it.parkingRegion
                textNameCustomerInfoTicketDetail.text = it.nickname
                textCarCustomerInfoTicketDetail.text = it.carNumber
//                address information is missing
//                textAddressTicketDetail.text =
                textPriceTicketDetail.text = "${it.cost}원"
                textTypeTicketDetail.text = when(it.type){
                    0 -> "1시간"
                    1 -> "3시간"
                    2 -> "5시간"
                    3 -> "종일권"
                    else -> ""
                }
                recyclerViewImagePictureTicketDetail.apply {
                    adapter = ticketDetailImageAdapter
                    layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                }
                textStatusTicketDetail.text =
                    if(!it.buyConfirm && !it.sellConfirm){
                        "등록 대기중"
                    } else if(!it.buyConfirm && it.sellConfirm){
                        "등록 완료"
                    } else {
                        "거래 완료"
                    }
                buttonSellConfirmTicketDetail.isEnabled = !ticketDetailViewModel.buyer && !it.sellConfirm
                buttonBuyConfirmTicketDetail.isEnabled = ticketDetailViewModel.buyer && !it.buyConfirm && it.sellConfirm
            }
        }
        binding.apply {
            buttonSellConfirmTicketDetail.setOnClickListener {
                ticketDetailViewModel.putTicketSellConfirm(userViewModel.user!!.user_id)
            }
            buttonBuyConfirmTicketDetail.setOnClickListener {
                ticketDetailViewModel.putTicketBuyConfirm(userViewModel.user!!.user_id)
            }
        }
    }

}
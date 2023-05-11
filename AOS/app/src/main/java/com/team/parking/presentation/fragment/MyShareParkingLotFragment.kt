package com.team.parking.presentation.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.team.parking.MainActivity
import com.team.parking.R
import com.team.parking.databinding.DialogCheckBinding
import com.team.parking.databinding.FragmentMyShareParkingLotBinding
import com.team.parking.presentation.adapter.ShareParkingLotListAdapter
import com.team.parking.presentation.viewmodel.MyTicketViewModel
import com.team.parking.presentation.viewmodel.ShareParkingLotViewModel
import com.team.parking.presentation.viewmodel.UserViewModel

class MyShareParkingLotFragment : Fragment() {

    private lateinit var binding: FragmentMyShareParkingLotBinding
    private lateinit var shareParkingLotViewModel: ShareParkingLotViewModel
    private lateinit var myTicketViewModel: MyTicketViewModel
    private lateinit var shareParkingLotListAdapter: ShareParkingLotListAdapter
    private lateinit var userViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyShareParkingLotBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        shareParkingLotViewModel = (activity as MainActivity).shareParkingLotViewModel
        myTicketViewModel = (activity as MainActivity).myTicketViewModel
        userViewModel = (activity as MainActivity).userViewModel
        binding.imageBackMyShareParkingLot.setOnClickListener {
            requireActivity().onBackPressed()
        }
        binding.imageAddMyShareParkingLot.setOnClickListener {
            findNavController().navigate(R.id.action_myShareParkingLotFragment_to_addShareParkingLotFragment)
        }
        shareParkingLotListAdapter = ShareParkingLotListAdapter()
        shareParkingLotListAdapter.setShareParkingLotDeleteClickListener(object :ShareParkingLotListAdapter.ShareParkingLotDeleteClickListener{
            override fun onClick(view: View, position: Int, data: Long) {
                showCheckDialog(data)
            }
        })
        shareParkingLotListAdapter.setShareParkingLotCheckClickListener(object :ShareParkingLotListAdapter.ShareParkingLotCheckClickListener{
            override fun onClick(view: View, position: Int, data: Long) {
                //check ticket
                myTicketViewModel.bought = false
                shareParkingLotViewModel.sharelotId = data
                findNavController().navigate(R.id.action_myShareParkingLotFragment_to_myShareParkingLotTicketFragment)
            }
        })
        shareParkingLotListAdapter.setShareParkingLotEditClickListener(object :ShareParkingLotListAdapter.ShareParkingLotEditClickListener{
            override fun onClick(view: View, position: Int, data: Long) {
                shareParkingLotViewModel.sharelotId = data
                findNavController().navigate(R.id.action_myShareParkingLotFragment_to_daySelectFragment)
            }
        })
        binding.recyclerViewMyShareParkingLot.apply {
            adapter = shareParkingLotListAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
        shareParkingLotViewModel.shareLotList.observe(viewLifecycleOwner){
            shareParkingLotListAdapter.differ.submitList(it)
        }
        shareParkingLotViewModel.getShareLotList(userViewModel.user!!.user_id)
    }

    override fun onResume() {
        super.onResume()
        shareParkingLotViewModel.getShareLotList(userViewModel.user!!.user_id)
    }

    fun showCheckDialog(parkId: Long){
        val builder = AlertDialog.Builder(requireContext())
        val dialogBinding = DialogCheckBinding.inflate(layoutInflater)
        builder.setView(dialogBinding.root)
        val dialog = builder.create()
        dialogBinding.apply {
            buttonCancelCheckDialog.setOnClickListener {
                dialog.dismiss()
            }
            buttonOkCheckDialog.setOnClickListener {
                shareParkingLotViewModel.deleteShareLot(parkId, userViewModel.user!!.user_id)
                dialog.dismiss()
            }
        }
        dialog.show()
    }
}
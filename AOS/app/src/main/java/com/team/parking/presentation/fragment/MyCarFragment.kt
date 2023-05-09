package com.team.parking.presentation.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.team.parking.MainActivity
import com.team.parking.R
import com.team.parking.data.model.car.CarListResponse
import com.team.parking.databinding.DialogAddCarBinding
import com.team.parking.databinding.FragmentMyCarBinding
import com.team.parking.presentation.adapter.CarListAdapter
import com.team.parking.presentation.viewmodel.CarViewModel
import com.team.parking.presentation.viewmodel.UserViewModel


class MyCarFragment : Fragment() {

    private lateinit var binding: FragmentMyCarBinding
    private lateinit var carViewModel: CarViewModel
    private lateinit var userViewModel: UserViewModel
    private lateinit var carListAdapter: CarListAdapter
    var previousView: View? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyCarBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        carViewModel = (activity as MainActivity).carViewModel
        userViewModel = (activity as MainActivity).userViewModel
        carListAdapter = CarListAdapter(resources)
        carListAdapter.setOnCarClickListener(object : CarListAdapter.CarOnClickListener {
            override fun onClick(view: View, position: Int, data: CarListResponse) {
                if(!data.carRep){
                    if(previousView != null){
                        previousView!!.background = ResourcesCompat.getDrawable(resources, R.drawable.day_select_white_background, null)
                    }
                    if(previousView != null && previousView == view){
                        previousView!!.background = ResourcesCompat.getDrawable(resources, R.drawable.day_select_white_background, null)
                        previousView = null
                        carViewModel.selectedCar = null
                    } else {
                        view.background = ResourcesCompat.getDrawable(resources, R.drawable.day_selected_background, null)
                        carViewModel.selectedCar = data
                        previousView = view
                    }
                }
            }
        })
        binding.recyclerViewMyCar.apply {
            adapter = carListAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
        carViewModel.carList.observe(viewLifecycleOwner){
            carListAdapter.differ.submitList(it)
            previousView = null
        }
        carViewModel.getCarList(userViewModel.user!!.user_id)
        binding.imageBackMyCar.setOnClickListener {
            requireActivity().onBackPressed()
        }
        binding.imageAddMyCar.setOnClickListener {
            showDialog()
        }
        binding.buttonMyCar.setOnClickListener {
            if(carViewModel.selectedCar == null){
                Toast.makeText(requireContext(), "차량을 먼저 선택해주세요.", Toast.LENGTH_SHORT).show()
            } else {
                carViewModel.setRepCar(userViewModel.user!!.user_id)
            }
        }
    }

    fun showDialog(){
        val builder = AlertDialog.Builder(requireContext())
        val dialogBinding = DialogAddCarBinding.inflate(layoutInflater)
        builder.setView(dialogBinding.root)
        val dialog = builder.create()
        dialogBinding.apply {
            buttonAddDialogAddCar.setOnClickListener {
                if(editTextNumberDialogAddCar.text.isNotEmpty()){
                    carViewModel.postCar(editTextNumberDialogAddCar.text.toString(), userViewModel.user!!.user_id)
                    dialog.dismiss()
                } else {
                    Toast.makeText(requireContext(), "차량 번호를 입력해주세요.", Toast.LENGTH_SHORT).show()
                }
            }
            buttonCancelDialogAddCar.setOnClickListener {
                dialog.dismiss()
            }
        }
        dialog.show()
    }
}
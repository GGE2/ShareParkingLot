package com.team.parking.presentation.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.team.parking.R
import com.team.parking.databinding.FragmentAddShareParkingLotBinding

class AddShareParkingLotFragment : Fragment() {

    private lateinit var binding: FragmentAddShareParkingLotBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddShareParkingLotBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonAddShareParkngLot.setOnClickListener {
            findNavController().navigate(R.id.action_addShareParkingLotFragment_to_daySelectFragment)
        }
    }

}
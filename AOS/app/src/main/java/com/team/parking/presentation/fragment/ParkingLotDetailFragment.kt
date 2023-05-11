package com.team.parking.presentation.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.team.parking.MainActivity
import com.team.parking.R
import com.team.parking.data.util.Resource
import com.team.parking.databinding.FragmentParkingLotDetailBinding
import com.team.parking.presentation.viewmodel.FavoriteViewModel
import com.team.parking.presentation.viewmodel.MapViewModel
import com.team.parking.presentation.viewmodel.UserViewModel

private const val TAG = "ParkingLotDetailFragment_지훈"

class ParkingLotDetailFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentParkingLotDetailBinding
    private lateinit var mapViewModel: MapViewModel
    private lateinit var favoriteViewModel: FavoriteViewModel
    private lateinit var userViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentParkingLotDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mapViewModel = (activity as MainActivity).mapViewModel
        favoriteViewModel = (activity as MainActivity).favoriteViewModel
        userViewModel = (activity as MainActivity).userViewModel
        if (mapViewModel.selectedPark.value == 0) {
            binding.buttonPurchaseParkingLotDetail.visibility = View.INVISIBLE
            mapViewModel.getDetailMapData(favoriteViewModel.parkId, userViewModel.user!!.user_id)
            mapViewModel.parkingLot.observe(viewLifecycleOwner) { response ->
                when (response) {
                    is Resource.Success -> {
                        mapViewModel.updatePark(response.data!!)
                        mapViewModel.updateSelectedPark(0)
                        Glide.with(this).load(R.drawable.icon_no_image).skipMemoryCache(true)
                            .diskCacheStrategy(
                                DiskCacheStrategy.NONE
                            ).into(binding.imageView2)
                        mapViewModel.park.observe(viewLifecycleOwner) {
                            setFavoriteDrawable(it.favorite)
                        }
                    }
                    is Resource.Error -> {
                        //Log.i(TAG, "서버와 통신이 원활하지 않습니다.")
                    }
                    else -> {
                        //Log.i(TAG, "getMapDetailDataL: ")
                    }
                }

            }
        } else {
            binding.buttonPurchaseParkingLotDetail.visibility = View.VISIBLE
            mapViewModel.getSharedParkingLotDetail(
                favoriteViewModel.parkId.toLong(),
                userViewModel.user!!.user_id
            )
            mapViewModel.sharedPark.observe(viewLifecycleOwner) { response ->
                when (response) {
                    is Resource.Success -> {
                        mapViewModel.updatePark(response.data!!)
                        mapViewModel.updateSelectedPark(1)
                        if (response.data.imageUrl.size > 0) {
                            Glide.with(this).load(response.data.imageUrl[0]).skipMemoryCache(true)
                                .diskCacheStrategy(
                                    DiskCacheStrategy.NONE
                                ).into(binding.imageView2)
                        } else {
                            Glide.with(this).load(R.drawable.icon_no_image).skipMemoryCache(true)
                                .diskCacheStrategy(
                                    DiskCacheStrategy.NONE
                                ).into(binding.imageView2)
                        }
                        mapViewModel.park.observe(viewLifecycleOwner) {
                            setFavoriteDrawable(it.favorite)
                        }
                    }
                    is Resource.Error -> {
                        //Log.i(TAG, "서버와 통신이 원활하지 않습니다.")
                    }
                    else -> {
                        //Log.i(TAG, "getMapDetailDataL: ")
                    }
                }
            }
        }
        mapViewModel.park.observe(viewLifecycleOwner) {
            binding.textTitleParkingLotDetail.text = it.lotName
            binding.textView4.text = it.jibun
            binding.textDefaultInfoParkingLotDetail.text = "시간당 요금:${it.feeBasic}"
            binding.textAdditionalInfoParkingLotDetail.text = it.specialProp
        }
        binding.apply {
            vm = mapViewModel
            lifecycleOwner = this@ParkingLotDetailFragment
        }
        binding.apply {
            imageFavoriteParkingLotDetail.background = ResourcesCompat.getDrawable(
                resources,
                R.drawable.icon_star_filled,
                null
            )
            imageFavoriteParkingLotDetail.setOnClickListener {
                favoriteViewModel.setFavorite(
                    mapViewModel.park.value!!.parkId.toLong(),
                    mapViewModel.selectedPark.value!!,
                    userViewModel.user!!.user_id
                )
            }
            buttonPurchaseParkingLotDetail.setOnClickListener {
                findNavController().navigate(R.id.action_parkinLotDetailFragment_to_purchaseTicketFragment)
            }
        }
    }

    private fun setFavoriteDrawable(value: Boolean) {
        binding.imageFavoriteParkingLotDetail.background =
            if (value) {
                ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.icon_star_filled,
                    null
                )
            } else {
                ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.icon_star_outline,
                    null
                )
            }
    }
}
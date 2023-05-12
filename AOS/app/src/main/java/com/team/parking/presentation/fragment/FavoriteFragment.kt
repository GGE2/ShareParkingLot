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
import com.team.parking.data.model.favorite.FavoriteListResponse
import com.team.parking.databinding.FragmentFavoriteBinding
import com.team.parking.presentation.adapter.FavoriteAdapter
import com.team.parking.presentation.viewmodel.FavoriteViewModel
import com.team.parking.presentation.viewmodel.MapViewModel
import com.team.parking.presentation.viewmodel.UserViewModel


class FavoriteFragment : Fragment() {
    private lateinit var fragmentFavoriteBinding: FragmentFavoriteBinding
    private lateinit var favoriteViewModel: FavoriteViewModel
    private lateinit var userViewModel: UserViewModel
    private lateinit var mapViewModel: MapViewModel
    private lateinit var favoriteAdapter: FavoriteAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentFavoriteBinding = FragmentFavoriteBinding.bind(view)
        favoriteViewModel = (activity as MainActivity).favoriteViewModel
        userViewModel = (activity as MainActivity).userViewModel
        mapViewModel = (activity as MainActivity).mapViewModel
        favoriteAdapter = FavoriteAdapter()
        favoriteAdapter.setOnFavoriteClickListener(object : FavoriteAdapter.FavoriteClickListener{
            override fun onClick(view: View, position: Int, data: FavoriteListResponse) {
                //navigate to parkinglotdetail
                mapViewModel.updateSelectedPark(data.parkType)
                favoriteViewModel.parkId = data.parkId
                findNavController().navigate(R.id.action_favoriteFragment_to_parkinLotDetailFragment)
            }
        })
        favoriteAdapter.setOnFavoriteImageClickListener(object : FavoriteAdapter.FavoriteImageClickListener{
            override fun onClick(view: View, position: Int, data: FavoriteListResponse) {
                favoriteViewModel.setFavoriteInList(data.parkId.toLong(), data.parkType, userViewModel.userLiveData.value!!.user_id)
            }
        })
        fragmentFavoriteBinding.recyclerView.apply {
            adapter = favoriteAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        favoriteViewModel.favoriteList.observe(viewLifecycleOwner){
            favoriteAdapter.differ.submitList(it)
        }
        favoriteViewModel.getFavoriteList(userViewModel.userLiveData.value!!.user_id)
        fragmentFavoriteBinding.imageBackFavorite.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }


}
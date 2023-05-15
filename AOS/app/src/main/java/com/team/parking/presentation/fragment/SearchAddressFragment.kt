package com.team.parking.presentation.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.team.parking.BuildConfig
import com.team.parking.MainActivity
import com.team.parking.data.model.map.AddressResponse
import com.team.parking.data.model.map.Place
import com.team.parking.data.util.Resource
import com.team.parking.databinding.FragmentSearchAddressBinding
import com.team.parking.presentation.adapter.SearchAdapter
import com.team.parking.presentation.adapter.SearchAddressAdapter
import com.team.parking.presentation.viewmodel.SearchAddressViewModel

private const val TAG = "SearchAddressFragment"

class SearchAddressFragment : Fragment() {

    private lateinit var binding: FragmentSearchAddressBinding
    private lateinit var viewModel: SearchAddressViewModel
    private lateinit var searchAdapter : SearchAddressAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchAddressBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).searchAddressViewModel
        init()
    }

    private fun init(){
        initDataBinding()
        initAdapter()
        changeSearchKeyword()
    }

    /**
     * 검색창 실시간으로 반영 및 빈 검색창 될 시 목록창 초기화
     */

    private fun changeSearchKeyword(){
        viewModel.query.observe(viewLifecycleOwner){ query->
            if(query.equals("")) {
                searchAdapter.differ.submitList(emptyList())
            }else{
                getSearchData(query)
            }

        }
    }
    /**
     * 화면 닫을시 기존 남아있는 검색창 초기화
     */
    override fun onPause() {
        super.onPause()
        viewModel.query.postValue("")
    }

    /**
     * 검색 데이터 받아오기
     */
    private fun getSearchData(query:String){
        viewModel.getAddressByKeyword(BuildConfig.KAKAO_REST_API_KEY,query)
        viewModel.searchAddress.observe(viewLifecycleOwner) { response ->
            when (response){
                is Resource.Success ->{
                    response?.data?.let {
                        searchAdapter.differ.submitList(it.documents)
                        Log.d(TAG, "getSearchData: ${it.documents}")
                    }
                }
                is Resource.Error ->{
                    response?.data?.let {
                        Toast.makeText(context,"데이터 생성 오류", Toast.LENGTH_SHORT).show()
                    }
                }
                else->{
                    response?.data.let {
                    }
                }
            }

        }


    }

    /**
     * databinding 변수 초기화
     */
    private fun initDataBinding(){
        binding.apply {
            lifecycleOwner = this@SearchAddressFragment
            vm = viewModel
        }
    }

    /**
     * Adapter 초기화 및 Item 콜백 등록
     */

    private fun initAdapter(){
        searchAdapter = SearchAddressAdapter()
        searchAdapter.setOnSearchItemClickListener(object : SearchAddressAdapter.SearchItemClickListener{
            override fun onClick(view: View, position: Int, data: AddressResponse) {
                viewModel.searchedAddress.postValue(data)
                popBackStack()
            }
        })
        binding.rvFragmentSearchAddressList.apply {
            adapter = searchAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    /**
     * 이전 페이지(MapFragment)로 이동
     */
    fun popBackStack(){
        findNavController().popBackStack()
    }
}
package com.team.parking.presentation.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.team.parking.BuildConfig
import com.team.parking.MainActivity
import com.team.parking.R
import com.team.parking.data.model.map.Place
import com.team.parking.data.util.Resource
import com.team.parking.databinding.FragmentSearchBinding
import com.team.parking.presentation.adapter.SearchAdapter
import com.team.parking.presentation.viewmodel.SearchViewModel


private const val TAG = "SearchFragment_지훈"
class SearchFragment : Fragment() {

    private lateinit var searchBinding : FragmentSearchBinding
    private lateinit var searchViewModel: SearchViewModel
    private lateinit var searchAdapter : SearchAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchBinding = DataBindingUtil.bind<FragmentSearchBinding>(view)!!
        searchViewModel = (activity as MainActivity).searchViewModel
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
        searchViewModel.query.observe(viewLifecycleOwner){ query->
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
        searchViewModel.query.postValue("")
    }

    /**
     * 검색 데이터 받아오기
     */
    private fun getSearchData(query:String){
        searchViewModel.getAddressByKeyword(BuildConfig.KAKAO_REST_API_KEY,query)
        searchViewModel.searchAddress.observe(viewLifecycleOwner) { response ->
            when (response){
                is Resource.Success ->{
                    response?.data?.let {
                        searchAdapter.differ.submitList(it.documents)
                    }
                }
                is Resource.Error ->{
                    response?.data?.let {
                        Toast.makeText(context,"데이터 생성 오류",Toast.LENGTH_SHORT).show()
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
        searchBinding.apply {
            handlers = this@SearchFragment
            lifecycleOwner = this@SearchFragment
            vm = searchViewModel
        }
    }

    /**
     * Adapter 초기화 및 Item 콜백 등록
     */

    private fun initAdapter(){
        searchAdapter = SearchAdapter()
        searchAdapter.setOnSearchItemClickListener(object : SearchAdapter.SearchItemClickListener{
            override fun onClick(view: View, position: Int, data: Place) {
                searchViewModel.searchedPlace.postValue(data)
                popBackStack()
            }
        })
        searchBinding.rvFragmentSearchList.apply {
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
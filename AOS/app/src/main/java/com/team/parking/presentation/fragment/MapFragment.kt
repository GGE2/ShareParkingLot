package com.team.parking.presentation.fragment

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.gms.location.*
import com.team.parking.R
import com.google.android.gms.tasks.OnSuccessListener
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.util.FusedLocationSource
import com.team.parking.MainActivity
import com.team.parking.data.model.map.MapRequest
import com.team.parking.data.util.Resource
import com.team.parking.databinding.FragmentMapBinding
import com.team.parking.presentation.viewmodel.MapViewModel
import com.team.parking.presentation.viewmodel.SearchViewModel


private const val TAG = "MapFragment_지훈"

class MapFragment : Fragment() , OnMapReadyCallback {
    private lateinit var fragmentMapBinding: FragmentMapBinding
    private lateinit var locationSource: FusedLocationSource
    private lateinit var naverMap: NaverMap
    private lateinit var locationClient : FusedLocationProviderClient
    private lateinit var mapViewModel: MapViewModel
    private lateinit var searchViewModel: SearchViewModel
    private val permissionList = Manifest.permission.ACCESS_FINE_LOCATION
    //GPS 권한 생성
    private val requestPermission = registerForActivityResult(
        ActivityResultContracts.RequestPermission()){
        when(it){
            true ->{
            }else->{
                naverMap.locationTrackingMode = LocationTrackingMode.None
            }
        }
    }
    private var cacheData = ArrayList<Marker>()
    private var currentZoom:Double = 0.0
    companion object {
        private const val PERMISSION_REQUEST_CODE = 1000
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        locationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        locationSource = FusedLocationSource(this, PERMISSION_REQUEST_CODE)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentMapBinding = DataBindingUtil.bind<FragmentMapBinding>(view)!!
        mapViewModel = (activity as MainActivity).mapViewModel
        searchViewModel = (activity as MainActivity).searchViewModel
        init()
    }

    /**
     * 서버로부터 주차장 데이터 가져오기
     */
    private fun getMapData(mapRequest: MapRequest){
        mapViewModel.getMapDatas(mapRequest)
        mapViewModel.parkingLots.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    //Log.i(TAG, "서버로부터 주차장 데이터를 가져오는데 성공했습니다.")
                    response.data.let{ data->
                        if(currentZoom<15.0){
                            for(i in 0 until data!!.size){
                                val marker = Marker()
                                cacheData.add(marker)
                                marker.position = LatLng(data[i].lat,data[i].lng)
                                marker.iconTintColor = Color.RED
                                marker.map = naverMap
                            }
                        }else{
                            for(i in 0 until data!!.size){
                                val marker = Marker()
                                cacheData.add(marker)
                                marker.position = LatLng(data[i].lat,data[i].lng)
                                marker.iconTintColor = Color.BLUE
                                marker.map = naverMap
                            }
                        }
                    }
                }
                is Resource.Error -> {
                    //Log.i(TAG, "서버로부터 주차장 데이터 가져오는데 실패하였습니다.")
                }
                else -> {
                    //Log.i(TAG, "서버로부터 주차장 데이터를 가져오고 있습니다.")
                }
            }
        }
    }
    private fun removeMapData(){
        for(i in 0 until cacheData.size){
            cacheData[i].map = null
        }
    }



    /**
     * 초기화 함수 모음
     */
    private fun init() {
        setDatabinding()
        setOnClickNavigationDrawerItem()
        initMap()
    }

    /**
     * SearchFragment 검색후 해당 장소로 좌표이동
     */
    private fun changeLocation(){
        searchViewModel.searchedPlace.observe(viewLifecycleOwner){
            val cameraUpdate = CameraUpdate.scrollTo(LatLng(it.y.toDouble(),it.x.toDouble()))
            val zoomUpdate = CameraUpdate.zoomTo(15.0)
            naverMap.moveCamera(cameraUpdate)
            naverMap.moveCamera(zoomUpdate)
        }
    }

    override fun onMapReady(naverMap: NaverMap) {
        this.naverMap = naverMap
        requestPermission.launch(permissionList)
        naverMap.locationSource = locationSource
        naverMap.uiSettings.isLocationButtonEnabled = true
        mapSetting()
        getMapDataFromRemote()
        changeLocation()
    }




    /**
     * NaverMap Option
     */
    private fun initMap() {
        val fm = childFragmentManager
        val mapFragment = fm.findFragmentById(R.id.fragment_fragment_map_maps) as com.naver.maps.map.MapFragment?
            ?:com.naver.maps.map.MapFragment.newInstance().also{
                fm.beginTransaction().add(R.id.fragment_fragment_map_maps,it).commit()
            }
        mapFragment.getMapAsync(this)
    }


    /**
     * 네이게이션 뷰 클릭리스너
     */
    fun setOnClickNavigationDrawerItem() {
        val activity = activity as MainActivity
        activity.navigationView.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.item_point -> {
                    findNavController().navigate(R.id.action_map_fragment_to_pointFragment)
                }
                R.id.item_trasaction -> {
                    findNavController().navigate(R.id.action_map_fragment_to_transactionHistoryFragment)
                }
                else -> {
                    findNavController().navigate(R.id.action_map_fragment_to_favoriteFragment)
                }
            }
            activity.navigationDrawer.closeDrawer(GravityCompat.START)
            true
        }
    }


    /**
     * databinding 초기화
     */
    private fun setDatabinding() {
        fragmentMapBinding.apply {
            handlers = this@MapFragment
            lifecycleOwner = this@MapFragment
        }
    }


    /**
     * 햄버거 클릭시 drawer 생성
     */
    fun onNavigationDrawer() {
        (activity as MainActivity).navigationDrawer.openDrawer(GravityCompat.START)
    }

    /**
     * 내위치 버튼 추가 및 최소 줌 최대 줌 추가
     */
    private fun mapSetting(){
        naverMap.uiSettings.isLocationButtonEnabled = true
        naverMap.uiSettings.logoGravity = (Gravity.END)
        naverMap.minZoom = 8.0
        naverMap.maxZoom = 18.0
    }

    /**
     * 장소 검색 클릭시 SearchFragment로 이동
     */
    fun setOnClickSearchListener(){
        findNavController().navigate(R.id.action_map_fragment_to_searchFragment)
    }

    /**
     * 맵 화면 이동 리스너
     * CameraChange : 카메라 이동시 마다 호출
     * CameraIdle : 카메라 이동 끝날시 호출
     */
    private fun getMapDataFromRemote(){

        naverMap.addOnCameraChangeListener { i, b ->
            //Log.i(TAG, "onMapReady: camera=${naverMap.cameraPosition.target.latitude},${naverMap.cameraPosition.target.longitude}")
            //Log.i(TAG, "getMapDataFromRemote: ${getAddress(naverMap.cameraPosition.target.latitude,naverMap.cameraPosition.target.longitude)}")
        }
        naverMap.addOnCameraIdleListener {

            currentZoom =  naverMap.cameraPosition.zoom
            //Log.i(TAG, "줌 레벨 : ${naverMap.cameraPosition.zoom}")
            /*Log.i(TAG, "줌 레벨 : ${naverMap.cameraPosition.zoom}")
            Log.i(TAG, "중심 좌표 : ${naverMap.cameraPosition.target.latitude},${naverMap.cameraPosition.target.longitude}")
            Log.i(TAG, "${naverMap.contentBounds.southWest.latitude} ,${naverMap.contentBounds.northWest.latitude} ")
            Log.i(TAG, "${naverMap.contentBounds.southWest.longitude} ,${naverMap.contentBounds.northEast.longitude}")*/

            //getAddress(naverMap.cameraPosition.target.latitude,naverMap.cameraPosition.target.longitude)
            if(currentZoom>=13.8&& currentZoom<17.2){
                val mapRequest = MapRequest(
                    naverMap.cameraPosition.target.latitude,naverMap.cameraPosition.target.longitude,
                    naverMap.contentBounds.northWest.latitude,naverMap.contentBounds.northEast.longitude,
                    naverMap.contentBounds.southWest.latitude,naverMap.contentBounds.southWest.longitude,
                    naverMap.cameraPosition.zoom
                )
                removeMapData()
                getMapData(mapRequest)
            }
            else{
                removeMapData()
            }
        }
    }





}



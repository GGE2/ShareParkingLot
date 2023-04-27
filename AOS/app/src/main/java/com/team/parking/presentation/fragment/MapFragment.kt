package com.team.parking.presentation.fragment

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.team.parking.R
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.OnSuccessListener
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.util.FusedLocationSource
import com.team.parking.MainActivity
import com.team.parking.data.api.MapAPIService
import com.team.parking.data.model.map.MapRequest
import com.team.parking.data.util.Resource
import com.team.parking.databinding.FragmentMapBinding
import com.team.parking.presentation.utils.App
import com.team.parking.presentation.viewmodel.MapViewModel
import com.team.parking.presentation.viewmodel.MapViewModelFactory
import dagger.hilt.android.scopes.FragmentScoped
import kotlinx.coroutines.*
import javax.inject.Inject


private const val TAG = "MapFragment_지훈"

class MapFragment : Fragment() , OnMapReadyCallback {
    private lateinit var fragmentMapBinding: FragmentMapBinding
    private lateinit var locationSource: FusedLocationSource
    private lateinit var naverMap: NaverMap
    private lateinit var currentLocation : Location
    private lateinit var fusedLocationClient : FusedLocationProviderClient
    private lateinit var mapRequest : MapRequest
    private lateinit var viewModel: MapViewModel
    private  var job : Job? = null

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1000
        private val PERMISSIONS = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentMapBinding = DataBindingUtil.bind<FragmentMapBinding>(view)!!
        init()
        locationSource = FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE)
        viewModel = (activity as MainActivity).mapViewModel
    }


    /**
     * 서버로부터 주차장 데이터 가져오기
     */
    private fun getMapData(){
        viewModel.getMapDatas(mapRequest)
        viewModel.parkingLots.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    Log.i(TAG, "서버로부터 주차장 데이터를 가져오는데 성공했습니다.")
                    response.data.let{ data->
                        Log.i(TAG, "getMapData: ${data!!.size}")
                        for(i in 0 until data!!.size){
                            val marker = Marker()
                            marker.position = LatLng(data[i].lat,data[i].lng)
                            marker.map = naverMap
                        }
                    }
                }
                is Resource.Error -> {
                    Log.i(TAG, "서버로부터 주차장 데이터 가져오는데 실패하였습니다.")
                }
                else -> {
                    Log.i(TAG, "서버로부터 주차장 데이터를 가져오고 있습니다.")
                }
            }
        }
    }



    /**
     * 초기화 함수 모음
     */
    private fun init() {
        setDatabinding()
        setOnClickNavigationDrawerItem()
        fetchLastLocation()
    }

    /**
     * 사용자 현재위치 받기
     */
    private fun fetchLastLocation(){
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                PERMISSIONS,
                LOCATION_PERMISSION_REQUEST_CODE
            )
            return
        }
        var task = fusedLocationClient.lastLocation
        task.addOnSuccessListener(object : OnSuccessListener<Location?> {
            override fun onSuccess(location: Location?) {
                if(location!=null) {
                    currentLocation = location
                }
                initMap()
            }

        })


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
        naverMap.minZoom = 8.0
        naverMap.maxZoom = 18.0
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
            /*val marker = Marker()
            marker.position = LatLng(naverMap.cameraPosition.target.latitude,naverMap.cameraPosition.target.longitude)
            marker.map = naverMap*/
            //Log.i(TAG, "getMapDataFromRemote 1: ${naverMap.contentBounds.center.latitude},${naverMap.contentBounds.center.longitude}")
            /*Log.i(TAG, "줌 레벨 : ${naverMap.cameraPosition.zoom}")
            Log.i(TAG, "중심 좌표 : ${naverMap.cameraPosition.target.latitude},${naverMap.cameraPosition.target.longitude}")
            Log.i(TAG, "${naverMap.contentBounds.southWest.latitude} ,${naverMap.contentBounds.northWest.latitude} ")
            Log.i(TAG, "${naverMap.contentBounds.southWest.longitude} ,${naverMap.contentBounds.northEast.longitude}")*/

            //getAddress(naverMap.cameraPosition.target.latitude,naverMap.cameraPosition.target.longitude)
            /*mapRequest = MapRequest(
                naverMap.cameraPosition.target.latitude,naverMap.cameraPosition.target.longitude,
                130.0,130.0,0.0,0.0,0.0
            )*/

        }
    }



   /* // 좌표 -> 주소 변환
    private fun getAddress(lat: Double, lng: Double): String {
        val geoCoder = Geocoder(requireContext(), Locale.KOREA)
        val address: ArrayList<Address>
        var addressResult = "주소를 가져 올 수 없습니다."
        try {
            //세번째 파라미터는 좌표에 대해 주소를 리턴 받는 갯수로
            //한좌표에 대해 두개이상의 이름이 존재할수있기에 주소배열을 리턴받기 위해 최대갯수 설정
            address = geoCoder.getFromLocation(lat, lng, 1) as ArrayList<Address>
            if (address.size > 0) {
                // 주소 받아오기
                val currentLocationAddress = address[0].getAddressLine(0)
                    .toString()
                Log.i(TAG, "getAddress: ${address[0].thoroughfare.toString()}")
                addressResult = currentLocationAddress
            }

        } catch (e: IOException) {
            e.printStackTrace()
        }
        return addressResult
    }*/


    override fun onMapReady(naverMap: NaverMap) {
        this.naverMap = naverMap

        val cameraUpdate = CameraUpdate.scrollTo(LatLng(currentLocation.latitude,currentLocation.longitude))
        val zoomUpdate = CameraUpdate.zoomTo(14.0)
        naverMap.moveCamera(cameraUpdate)
        naverMap.moveCamera(zoomUpdate)
        //Log.i(TAG, "onMapReady: ${currentLocation.latitude},${currentLocation.longitude}")
        mapSetting()
        getMapDataFromRemote()
        mapRequest = MapRequest(
            naverMap.cameraPosition.target.latitude,naverMap.cameraPosition.target.longitude,
            130.0,130.0,0.0,0.0,0.0
        )
        getMapData()


        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                PERMISSIONS,
                LOCATION_PERMISSION_REQUEST_CODE
            )
            return
        }
    }


    /**
     * GPS 권한 요청 CallBack
     */
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (locationSource.onRequestPermissionsResult(
                requestCode,
                permissions,
                grantResults
            )
        ) {
            if (!locationSource.isActivated) { // 권한 거부됨
                Toast.makeText(context, "권한이 거부되어 현위치를 표시할 수 없습니다.", Toast.LENGTH_SHORT)
                    .show()
            } else {
                fetchLastLocation()
                naverMap.locationTrackingMode = LocationTrackingMode.Follow
                Log.i(TAG, "onRequestPermissionsResult: ")
            }
            return
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

}



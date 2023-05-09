package com.team.parking

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment

import com.google.android.gms.tasks.OnCompleteListener
import com.bumptech.glide.Glide
import com.google.android.material.navigation.NavigationView
import com.google.firebase.messaging.FirebaseMessaging
import com.kakao.sdk.common.util.Utility
import com.team.parking.data.api.UserService
import com.team.parking.data.model.user.User
import com.team.parking.databinding.ActivityMainBinding
import com.team.parking.presentation.utils.App
import com.team.parking.databinding.SideHeaderBinding
import com.team.parking.presentation.viewmodel.*
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

private const val TAG = "MainActivity_지훈"
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var mapViewModelFactory : MapViewModelFactory
    lateinit var mapViewModel: MapViewModel

    @Inject
    lateinit var searchViewModelFactory: SearchViewModelFactory
    lateinit var searchViewModel: SearchViewModel

    lateinit var searchAddressViewModel: SearchAddressViewModel

    @Inject
    lateinit var shareParkingLotViewModelFactory: ShareParkingLotViewModelFactory
    lateinit var shareParkingLotViewModel: ShareParkingLotViewModel

    @Inject
    lateinit var daySelectViewModelFactory: DaySelectViewModelFactory
    lateinit var daySelectViewModel: DaySelectViewModel

    @Inject
    lateinit var pointViewModelFactory: PointViewModelFactory
    lateinit var pointViewModel: PointViewModel

    private lateinit var binding : ActivityMainBinding
    lateinit var userViewModel: UserViewModel
    lateinit var navigationDrawer : DrawerLayout
    lateinit var navigationView : NavigationView
    
    private lateinit var headerBinding: SideHeaderBinding
    lateinit var navController: NavController



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        headerBinding = SideHeaderBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setNavigationController()
        setNavigationDrawerInit()
        setOnClickNavigationDrawerItem()
        //setFullScreen()
        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]
        initMapViewModel()
        onLoginSuccess()

    }

    fun initMapViewModel(){
        mapViewModel = ViewModelProvider(this,mapViewModelFactory)[MapViewModel::class.java]
        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]
        daySelectViewModel = ViewModelProvider(this, daySelectViewModelFactory)[DaySelectViewModel::class.java]
        searchViewModel = ViewModelProvider(this,searchViewModelFactory)[SearchViewModel::class.java]
        shareParkingLotViewModel = ViewModelProvider(this, shareParkingLotViewModelFactory)[ShareParkingLotViewModel::class.java]
        pointViewModel = ViewModelProvider(this,pointViewModelFactory)[PointViewModel::class.java]
        setProfileFragmentNavigation()
    }


    fun setOnClickNavigationDrawerItem(){
        binding.navigationFragmentMap.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.item_point -> {
                    Log.i(TAG, "onNavigationItemSelected:1")
                }
                else -> {
                    Log.i(TAG, "onNavigationItemSelected:2")
                }
            }
            true
        }
    }


    /**
     * MapFragment에서 사용할 drawer 초기화
     */
    private fun setNavigationDrawerInit(){
        navigationDrawer = binding.drawer
        navigationView = binding.navigationFragmentMap
        binding.drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
    }

    /**
     * Navigation Controller 등록
     */
    private fun setNavigationController(){
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fcv_activity_main) as NavHostFragment
        navController = navHostFragment.navController
    }

    /**
     * 하단 네비게이션 삭제
     */
    fun setFullScreen(){
        //Android 11(R) 대응
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.R){
            supportActionBar?.hide()
            window.setDecorFitsSystemWindows(false)
            val controller = window.insetsController
            if(controller!=null){
                controller.hide(WindowInsets.Type.navigationBars())
                controller.systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        }else{ //R버전 이하 대응
            supportActionBar?.hide()
            window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE or
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_FULLSCREEN)
        }
    }

    /**
     * Profile Fragment Navigation
     */
    private fun setProfileFragmentNavigation(){
        binding.navigationFragmentMap.addHeaderView(headerBinding.root)
        headerBinding.layoutNicknameSideHeader.setOnClickListener {
            navController.navigate(R.id.action_map_fragment_to_profileFragment)
            binding.drawer.closeDrawers()
        }
        headerBinding.buttonSideHeader.setOnClickListener {
            navController.navigate(R.id.action_map_fragment_to_myShareParkingLotFragment)
            binding.drawer.closeDrawers()
        }
    }


    private fun onLoginSuccess(){
        userViewModel.userLiveData.observe(this){
            runOnUiThread {
                headerBinding.textNicknameSideHeader.text = it.name
                Glide.with(this@MainActivity)
                    .load(it.profile_img)
                    .error(R.drawable.ic_baseline_person_24)
                    .into(headerBinding.imageProfileSideHeader)
            }
        }
    }

    override fun onBackPressed() {
        if(daySelectViewModel.add){
            daySelectViewModel.add = false
            super.onBackPressed()
            super.onBackPressed()
        } else {
            super.onBackPressed()
        }
    }

}
package com.team.parking

import com.team.parking.R
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController

import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext

import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen

import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.navigation.NavigationView
import com.team.parking.data.model.notification.GetNotiListRequest
import com.team.parking.data.model.user.User
import com.team.parking.databinding.ActivityMainBinding
import com.team.parking.databinding.SideHeaderBinding
import com.team.parking.presentation.fragment.TicketDetailFragment
import com.team.parking.presentation.viewmodel.*
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


private const val TAG = "MainActivity_지훈"

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var mapViewModelFactory: MapViewModelFactory
    lateinit var mapViewModel: MapViewModel

    @Inject
    lateinit var searchViewModelFactory: SearchViewModelFactory
    lateinit var searchViewModel: SearchViewModel


    @Inject
    lateinit var userViewModelFactory: UserViewModelFactory

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

    @Inject
    lateinit var carViewModelFactory: CarViewModelFactory
    lateinit var carViewModel: CarViewModel

    @Inject
    lateinit var myTicketViewModelFactory: MyTicketViewModelFactory
    lateinit var myTicketViewModel: MyTicketViewModel

    @Inject
    lateinit var ticketDetailViewModelFactory: TicketDetailViewModelFactory
    lateinit var ticketDetailViewModel: TicketDetailViewModel

    @Inject
    lateinit var favoriteViewModelFactory: FavoriteViewModelFactory
    lateinit var favoriteViewModel: FavoriteViewModel

    @Inject
    lateinit var notificationViewModelFactory: NotificationViewModelFactory
    lateinit var notificationViewModel: NotificationViewModel

    private lateinit var binding: ActivityMainBinding
    lateinit var userViewModel: UserViewModel
    lateinit var navigationDrawer: DrawerLayout
    lateinit var navigationView: NavigationView


    private lateinit var headerBinding: SideHeaderBinding
    lateinit var navController: NavController

    init{
        instance = this
    }



    private lateinit var sp: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor


    companion object{
        private var instance:MainActivity?=null
        fun getInstance():MainActivity?{
            return instance
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        headerBinding = SideHeaderBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setNavigationController()
        setNavigationDrawerInit()
        setOnClickNavigationDrawerItem()
        //setFullScreen()
        notificationViewModel = ViewModelProvider(this,notificationViewModelFactory)[NotificationViewModel::class.java]
        userViewModel = ViewModelProvider(this, userViewModelFactory)[UserViewModel::class.java]
        initMapViewModel()
        onLoginSuccess()


        sp = PreferenceManager.getDefaultSharedPreferences(this)

        editor = sp.edit()
        notificationViewModel.notiList.observe(this){
            Log.i(TAG, "onCreate: $it")
        }
        if (sp.getBoolean("auto_login", false)) {
            userViewModel.login(
                User(
                    sp.getLong("user_id", 0),
                    sp.getString("phone", "00000000000")!!,
                    sp.getString("email", "000@000.com")!!,
                    sp.getString("name", "oooo")!!,
                    sp.getString("profile_img", ""),
                    sp.getInt("pt_has", 0),
                    sp.getString("type", "")!!,
                    sp.getString("social_id", ""),
                    sp.getString("fcm_token", ""),
                )
            )
            
            notificationViewModel.getNotiList(sp.getLong("user_id",0)!!)
            Log.d(TAG, "onCreate: gsdgsgdsgdsgsgd")
            if (intent != null && "NOTIFICATION_CLICK" == intent.action) {
                // Retrieve any necessary data from the intent extras
                Log.i("종건", "onCreate: ${intent.action}")
                val type: Int? = intent.getIntExtra("type",-1)
                notificationViewModel.getNotiList(sp.getLong("user_id",-1))
                when (type) {
                    0 -> {

                    }
                    2 -> {
                        
                        val noti_id = intent.getLongExtra("noti_id", -1)
                        val user_id = intent.getLongExtra("user_id", -1)
                        val ticket_id = intent.getLongExtra("ticket_id", -1)
                        ticketDetailViewModel.buyer=true
                        ticketDetailViewModel.ticketId = ticket_id!!.toLong()
                        notificationViewModel.readNoti(noti_id!!.toLong(),sp.getLong("user_id",0))
                        navController.navigate(R.id.action_loginFragment_to_mapFragment)
                        Log.d(TAG, "onCreate: asdasdasdasdasdasdas")
                        navController.navigate(R.id.action_map_fragment_to_ticketDetailFragment)
                    }
                    else -> {
                        Log.i("종건", "type : ${intent.getIntExtra("type",-1)}")
                        val noti_id = intent.getLongExtra("noti_id", -1)
                        Log.d("종건", "onCreate: $noti_id")

                        val user_id = intent.getLongExtra("user_id", -1)
                        Log.d("종건", "onCreate: $user_id")

                        val ticket_id = intent.getLongExtra("ticket_id", -1)
                        Log.d("종건", "onCreate: $ticket_id")



                        ticketDetailViewModel.buyer=false
                        ticketDetailViewModel.ticketId = ticket_id!!.toLong()
                        Log.d(TAG, "onCreate: asdasdasdasdasdasasdasddas")
                        notificationViewModel.readNoti(noti_id!!,sp.getLong("user_id",0))
                        
                        navController.navigate(R.id.action_loginFragment_to_mapFragment)
                        navController.navigate(R.id.action_map_fragment_to_ticketDetailFragment)
                    }

                }


            }else{
                navController.navigate(R.id.action_loginFragment_to_mapFragment)
            }
        }
    }


    fun initMapViewModel() {
        mapViewModel = ViewModelProvider(this, mapViewModelFactory)[MapViewModel::class.java]
        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]
        daySelectViewModel =
            ViewModelProvider(this, daySelectViewModelFactory)[DaySelectViewModel::class.java]
        searchViewModel =
            ViewModelProvider(this, searchViewModelFactory)[SearchViewModel::class.java]
        shareParkingLotViewModel = ViewModelProvider(
            this,
            shareParkingLotViewModelFactory
        )[ShareParkingLotViewModel::class.java]
        pointViewModel = ViewModelProvider(this, pointViewModelFactory)[PointViewModel::class.java]
        carViewModel = ViewModelProvider(this, carViewModelFactory)[CarViewModel::class.java]
        myTicketViewModel =
            ViewModelProvider(this, myTicketViewModelFactory)[MyTicketViewModel::class.java]
        ticketDetailViewModel =
            ViewModelProvider(this, ticketDetailViewModelFactory)[TicketDetailViewModel::class.java]
        favoriteViewModel =
            ViewModelProvider(this, favoriteViewModelFactory)[FavoriteViewModel::class.java]
        setProfileFragmentNavigation()
    }


    fun setOnClickNavigationDrawerItem() {
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
    private fun setNavigationDrawerInit() {
        navigationDrawer = binding.drawer
        navigationView = binding.navigationFragmentMap
        binding.drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
    }

    /**
     * Navigation Controller 등록
     */
    private fun setNavigationController() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fcv_activity_main) as NavHostFragment
        navController = navHostFragment.navController
    }

    /**
     * 하단 네비게이션 삭제
     */
    fun setFullScreen() {
        //Android 11(R) 대응
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            supportActionBar?.hide()
            window.setDecorFitsSystemWindows(false)
            val controller = window.insetsController
            if (controller != null) {
                controller.hide(WindowInsets.Type.navigationBars())
                controller.systemBarsBehavior =
                    WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        } else { //R버전 이하 대응
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
    private fun setProfileFragmentNavigation() {
        binding.navigationFragmentMap.addHeaderView(headerBinding.root)
        headerBinding.layoutNicknameSideHeader.setOnClickListener {
            navController.navigate(R.id.action_map_fragment_to_profileFragment)
            binding.drawer.closeDrawers()
        }
        headerBinding.buttonSideHeader.setOnClickListener {
            navController.navigate(R.id.action_map_fragment_to_myShareParkingLotFragment)
            binding.drawer.closeDrawers()
        }
        headerBinding.textMyCarSideHeader.setOnClickListener {
            navController.navigate(R.id.action_map_fragment_to_myCarFragment)
            binding.drawer.closeDrawers()
        }
        headerBinding.imageNotificationSideHeader.setOnClickListener {
            navController.navigate(R.id.action_map_fragment_to_notification_fragment)
            binding.drawer.closeDrawers()
        }
        notificationViewModel.notiList.observe(this ){
            checkList(it!!)
        }
    }
    fun checkList(it : List<GetNotiListRequest>){
        if (it!!.isNotEmpty()){
            headerBinding.circle.visibility=View.VISIBLE
        }else{
            Log.d(TAG, "checkList: ${it}")
            headerBinding.circle.visibility=View.GONE
        }
    }

    private fun onLoginSuccess() {
        userViewModel.userLiveData.observe(this) {
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
        if (daySelectViewModel.add) {
            daySelectViewModel.add = false
            super.onBackPressed()
            super.onBackPressed()
        } else {
            super.onBackPressed()
        }
    }

}
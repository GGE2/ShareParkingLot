package com.team.parking.presentation.fragment

import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.team.parking.MainActivity
import com.team.parking.R
import com.team.parking.data.model.notification.GetNotiListRequest
import com.team.parking.databinding.FragmentNotificationBinding
import com.team.parking.presentation.adapter.CarListAdapter

import com.team.parking.presentation.adapter.NotificationAdapter
import com.team.parking.presentation.viewmodel.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


private const val TAG="종건"
class NotificationFragment : Fragment() {
    private lateinit var binding: FragmentNotificationBinding
    private lateinit var userViewModel: UserViewModel
    private lateinit var notiViewModel: NotificationViewModel
    private lateinit var notiAdapter: NotificationAdapter
    private lateinit var ticketDetailViewModel: TicketDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNotificationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        userViewModel = (activity as MainActivity).userViewModel
        notiViewModel = (activity as MainActivity).notificationViewModel
        ticketDetailViewModel = (activity as MainActivity).ticketDetailViewModel

        notiAdapter = NotificationAdapter()

        binding.apply {
            handlers = this@NotificationFragment
            lifecycleOwner = this@NotificationFragment
            userViewModel = userViewModel
            notiViewModel = notiViewModel
            ticketDetailViewModel = ticketDetailViewModel
            notiRecycler.adapter = notiAdapter
            notiRecycler.layoutManager = LinearLayoutManager(requireContext())
        }


        getNotiList()
        notiAdapter.setOnNotificationClickListener(object : NotificationAdapter.NotificationClickListener{
            override fun onClick(view: View, position: Int, data: GetNotiListRequest) {
                Log.d("종건", "onClick: ${data.noti_id}")
                notiViewModel.readNoti(data.noti_id!!,userViewModel.userLiveData.value!!.user_id)
                ticketDetailViewModel.ticketId = data.ticket_id!!
                when(data.type){
                    2->{
                        ticketDetailViewModel.buyer=true
                    }
                    else->{
                        ticketDetailViewModel.buyer=false
                    }
                }
                findNavController().navigate(R.id.action_notification_fragment_to_ticketDetailFragment)
            }

        })
        notiAdapter.setOnNotificationDragListener(object : NotificationAdapter.NotificationDragListener{
            override fun onDrag(view: View, position: Int, data: GetNotiListRequest) {

                notiViewModel.readNoti(data.noti_id!!,userViewModel.userLiveData.value!!.user_id)
                CoroutineScope(Dispatchers.IO).launch {
                    delay(500)
                    notiViewModel.getNotiList(userViewModel.userLiveData.value!!.user_id)
                }
            }
        })
        notiViewModel.notiList.observe(viewLifecycleOwner){
            Log.i(TAG, "onViewCreated: ")
            (activity as MainActivity).checkList(it!!)
            notiAdapter.differ.submitList(it)
        }


        binding.imageBackMyCar.setOnClickListener{
            requireActivity().onBackPressed()
        }
        binding.eraseButton.setOnClickListener{
            notiViewModel.deleteAllNoti(userViewModel.userLiveData.value!!.user_id)
        }
    }

    fun getNotiList(){
        Log.d("종건", "getNotiList: ")
        notiViewModel.getNotiList(userViewModel.userLiveData.value!!.user_id)
    }

//    override fun onResume() {
//        super.onResume()
//
//        getNotiList()
//    }





}
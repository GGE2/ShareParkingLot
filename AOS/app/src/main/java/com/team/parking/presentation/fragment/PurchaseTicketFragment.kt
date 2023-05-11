package com.team.parking.presentation.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.team.parking.MainActivity
import com.team.parking.databinding.FragmentPurchaseTicketBinding
import com.team.parking.presentation.viewmodel.*
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog.OnTimeSetListener
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.logging.SimpleFormatter
import javax.inject.Inject

@AndroidEntryPoint
class PurchaseTicketFragment : Fragment() {

    private lateinit var binding: FragmentPurchaseTicketBinding
    private lateinit var userViewModel: UserViewModel
    private lateinit var mapViewModel: MapViewModel
    private lateinit var carViewModel: CarViewModel
    private var carAvailable = false

    @Inject
    lateinit var purchaseTicketViewModelFactory: PurchaseTicketViewModelFactory
    private lateinit var purchaseTicketViewModel: PurchaseTicketViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPurchaseTicketBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mapViewModel = (activity as MainActivity).mapViewModel
        userViewModel = (activity as MainActivity).userViewModel
        carViewModel = (activity as MainActivity).carViewModel
        carViewModel.getCarList(userViewModel.user!!.user_id)
        purchaseTicketViewModel = ViewModelProvider(this, purchaseTicketViewModelFactory)[PurchaseTicketViewModel::class.java]
        purchaseTicketViewModel.ticketCreateRequest.shaId = mapViewModel.sharedPark.value!!.data!!.parkId.toLong()
        carViewModel.carList.observe(viewLifecycleOwner){
            if (it.isNotEmpty()){
                purchaseTicketViewModel.ticketCreateRequest.carNumber = carViewModel.carList.value!![0].carStr
                carAvailable = true
            }
        }
        purchaseTicketViewModel.ticketAvailable.observe(viewLifecycleOwner){
            binding.apply {
                select1HourTicket.isEnabled = it.oneHour
                select3HourTicket.isEnabled = it.threeHours
                select5HourTicket.isEnabled = it.fiveHours
                selectAllDayTicket.isEnabled = it.allDay
                if(!it.oneHour){
                    select1HourTicket.isChecked = false
                    if(purchaseTicketViewModel.ticketCreateRequest.type == 0){
                        purchaseTicketViewModel.ticketCreateRequest.type = -1
                        purchaseTicketViewModel.setExpectedPrice(0)
                    }
                }
                if(!it.threeHours){
                    select3HourTicket.isChecked = false
                    if(purchaseTicketViewModel.ticketCreateRequest.type == 1){
                        purchaseTicketViewModel.ticketCreateRequest.type = -1
                        purchaseTicketViewModel.setExpectedPrice(0)
                    }
                }
                if(!it.fiveHours){
                    select5HourTicket.isChecked = false
                    if(purchaseTicketViewModel.ticketCreateRequest.type == 2){
                        purchaseTicketViewModel.ticketCreateRequest.type = -1
                        purchaseTicketViewModel.setExpectedPrice(0)
                    }
                }
                if(!it.allDay){
                    selectAllDayTicket.isChecked = false
                    if(purchaseTicketViewModel.ticketCreateRequest.type == 3){
                        purchaseTicketViewModel.ticketCreateRequest.type = -1
                        purchaseTicketViewModel.setExpectedPrice(0)
                    }
                }
            }
        }
        purchaseTicketViewModel.startHour.observe(viewLifecycleOwner){
            binding.textStartTimePurchaseTicket.text = "${it}:00"
        }
        purchaseTicketViewModel.expectedPrice.observe(viewLifecycleOwner){
            binding.textExpectedPricePurchaseTicket.text = "${it} P"
        }
        binding.apply {
            textParkingLotNamePurchaseTicket.text = mapViewModel.sharedPark.value?.data?.lotName
            textParkingLotAddressPurchaseTicket.text = mapViewModel.sharedPark.value?.data?.jibun
            val calendar = Calendar.getInstance()
            val dayFormatter = SimpleDateFormat("yyyy-MM-dd")
            textTimePurchaseTicket.text = "${dayFormatter.format(calendar.time)} ${
                when(calendar.get(Calendar.DAY_OF_WEEK)){
                    1 -> "일요일"
                    2 -> "월요일"
                    3 -> "화요일"
                    4 -> "수요일"
                    5 -> "목요일"
                    6 -> "금요일"
                    7 -> "토요일"
                    else -> ""
                }
            }"
            select1HourTicket.isEnabled = false
            select3HourTicket.isEnabled = false
            select5HourTicket.isEnabled = false
            selectAllDayTicket.isEnabled = false
            textRemainPointPurchaseTicket.text = "${userViewModel.user!!.pt_has} P"
            textExpectedPricePurchaseTicket.text = "0 P"
            layoutStartTimePurchaseTicket.setOnClickListener {
                showTimePickerDialog { view, hourOfDay, minute, second ->
                    purchaseTicketViewModel.setStartHour(hourOfDay)
                    purchaseTicketViewModel.ticketCreateRequest.inTiming = hourOfDay
                    purchaseTicketViewModel.getTicketAvailable(mapViewModel.sharedPark.value!!.data!!.parkId.toLong())
                }
            }
            select1HourTicket.setOnCheckedChangeListener {it, checked ->
                if(checked){
                    clearCheck()
                    it.isChecked = true
                    purchaseTicketViewModel.ticketCreateRequest.type = 0
                    purchaseTicketViewModel.setExpectedPrice(mapViewModel.sharedPark.value!!.data!!.feeBasic)
                }
            }
            select3HourTicket.setOnCheckedChangeListener {it, checked ->
                if(checked){
                    clearCheck()
                    it.isChecked = true
                    purchaseTicketViewModel.ticketCreateRequest.type = 1
                    purchaseTicketViewModel.setExpectedPrice(mapViewModel.sharedPark.value!!.data!!.feeBasic * 3)
                }
            }
            select5HourTicket.setOnCheckedChangeListener {it, checked ->
                if(checked){
                    clearCheck()
                    it.isChecked = true
                    purchaseTicketViewModel.ticketCreateRequest.type = 2
                    purchaseTicketViewModel.setExpectedPrice(mapViewModel.sharedPark.value!!.data!!.feeBasic * 5)
                }
            }
            selectAllDayTicket.setOnCheckedChangeListener {it, checked ->
                if(checked){
                    clearCheck()
                    it.isChecked = true
                    purchaseTicketViewModel.ticketCreateRequest.type = 3
                    purchaseTicketViewModel.setExpectedPrice(mapViewModel.sharedPark.value!!.data!!.feeBasic * 24)
                }
            }
            buttonPurchasePurchaseTicket.setOnClickListener {
                if(checkAgreementPurchaseTicket.isChecked){
                    //purchase
                    if(purchaseTicketViewModel.ticketCreateRequest.type != -1){
                        if(purchaseTicketViewModel.expectedPrice.value!! <= userViewModel.user!!.pt_has){
                            if(carAvailable){
                                purchaseTicketViewModel.postTicketAvailable(userViewModel.user!!.user_id)
                                Toast.makeText(requireContext(), "주차권이 구매되었습니다.", Toast.LENGTH_SHORT).show()
                                requireActivity().onBackPressed()
                            } else {
                                Toast.makeText(requireContext(), "차량을 등록해주세요.", Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            Toast.makeText(requireContext(), "포인트 잔액이 부족합니다.", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(requireContext(), "주차권을 선택해주세요.", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(requireContext(), "결제 약관에 동의해주세요.", Toast.LENGTH_SHORT).show()
                }
            }
            buttonCancelPurchaseTicket.setOnClickListener {
                requireActivity().onBackPressed()
            }
        }
    }

    fun showTimePickerDialog(onTimeSetListener: OnTimeSetListener){
        val timePickerDialog = TimePickerDialog.newInstance(onTimeSetListener, false)
        timePickerDialog.enableMinutes(false)
        timePickerDialog.setMinTime(Calendar.getInstance().get(Calendar.HOUR_OF_DAY) + 1, 0, 0)
        timePickerDialog.show(parentFragmentManager, "")
    }

    fun clearCheck(){
        binding.apply {
            select1HourTicket.isChecked = false
            select3HourTicket.isChecked = false
            select5HourTicket.isChecked = false
            selectAllDayTicket.isChecked = false
        }
    }
}
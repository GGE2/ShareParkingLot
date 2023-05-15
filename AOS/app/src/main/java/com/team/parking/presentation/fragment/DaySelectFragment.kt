package com.team.parking.presentation.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import com.team.parking.MainActivity
import com.team.parking.R
import com.team.parking.databinding.FragmentDaySelectBinding
import com.team.parking.presentation.viewmodel.DaySelectViewModel
import com.team.parking.presentation.viewmodel.ShareParkingLotViewModel
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog.OnTimeSetListener

private const val TAG = "DaySelectFragment"

class DaySelectFragment : Fragment() {

    private lateinit var binding: FragmentDaySelectBinding
    private lateinit var daySelectViewModel: DaySelectViewModel
    private lateinit var shareParkingLotViewModel: ShareParkingLotViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDaySelectBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        shareParkingLotViewModel = (activity as MainActivity).shareParkingLotViewModel
        binding.imageBackDaySelect.setOnClickListener {
            requireActivity().onBackPressed()
        }
        daySelectViewModel = (activity as MainActivity).daySelectViewModel
        if(daySelectViewModel.add){
            daySelectViewModel.initDayList()
            binding.apply {
                textTitleDaySelect.text = "요일 등록"
                buttonFinishDaySelect.text = "요일 등록"
            }
        } else {
            daySelectViewModel.getShareLotDay(shareParkingLotViewModel.sharelotId)
        }
        daySelectViewModel.currentIndex.observe(viewLifecycleOwner){
            onObserveData()
        }
        daySelectViewModel.dayList.observe(viewLifecycleOwner){
            onObserveData()
        }
        daySelectViewModel.setCurrentIndex(0)
        binding.apply {
            daySelectViewModel.layoutList = listOf(
                layoutMonDaySelect,
                layoutTueDaySelect,
                layoutWedDaySelect,
                layoutThuDaySelect,
                layoutFriDaySelect,
                layoutSatDaySelect,
                layoutSunDaySelect
            )
            daySelectViewModel.resource = resources
            layoutMonDaySelect.setOnClickListener {
                layoutWeekDayOnClick(0, it)
            }
            layoutTueDaySelect.setOnClickListener {
                layoutWeekDayOnClick(1, it)
            }
            layoutWedDaySelect.setOnClickListener {
                layoutWeekDayOnClick(2, it)
            }
            layoutThuDaySelect.setOnClickListener {
                layoutWeekDayOnClick(3, it)
            }
            layoutFriDaySelect.setOnClickListener {
                layoutWeekDayOnClick(4, it)
            }
            layoutSatDaySelect.setOnClickListener {
                layoutWeekDayOnClick(5, it)
            }
            layoutSunDaySelect.setOnClickListener {
                layoutWeekDayOnClick(6, it)
            }
            checkEnableDaySelect.setOnCheckedChangeListener { compoundButton, b ->
                daySelectViewModel.dayList.value!![daySelectViewModel.currentIndex.value!!].enable = b
            }
            layoutStartTimeDaySelect.setOnClickListener {
                showTimePickerDialog(object : OnTimeSetListener{
                    override fun onTimeSet(
                        view: TimePickerDialog?,
                        hourOfDay: Int,
                        minute: Int,
                        second: Int
                    ) {
                        daySelectViewModel.dayList.value!![daySelectViewModel.currentIndex.value!!].dayStart = hourOfDay
                        binding.textValueStartTimeDaySelect.text = "${hourOfDay}:00"
                    }
                }, true)
            }
            layoutEndTimeDaySelect.setOnClickListener {
                showTimePickerDialog(object : OnTimeSetListener{
                    override fun onTimeSet(
                        view: TimePickerDialog?,
                        hourOfDay: Int,
                        minute: Int,
                        second: Int
                    ) {
                        daySelectViewModel.dayList.value!![daySelectViewModel.currentIndex.value!!].dayEnd = hourOfDay
                        binding.textValueEndTimeDaySelect.text = "${hourOfDay}:00"
                    }
                }, false)
            }
            buttonFinishDaySelect.setOnClickListener {
                daySelectViewModel.putShareLotDay(shareParkingLotViewModel.sharelotId)
                (activity as MainActivity).onBackPressed()
            }
        }
    }

    fun layoutWeekDayOnClick(currentIndex: Int, it: View){
        daySelectViewModel.setCurrentIndex(currentIndex)
        daySelectViewModel.unselectAllLayout()
        it.background = ResourcesCompat.getDrawable(resources, R.drawable.day_selected_background, null)
    }

    fun showTimePickerDialog(onTimeSetListener: OnTimeSetListener, min: Boolean){
        val timePickerDialog = TimePickerDialog.newInstance(onTimeSetListener, false)
        timePickerDialog.enableMinutes(false)
        if(!min){
            timePickerDialog.setMinTime(
                if(daySelectViewModel.dayList.value!![daySelectViewModel.currentIndex.value!!].dayStart == -1) 0
                else daySelectViewModel.dayList.value!![daySelectViewModel.currentIndex.value!!].dayStart,
                0, 0)
        } else {
            timePickerDialog.setMaxTime(
                if(daySelectViewModel.dayList.value!![daySelectViewModel.currentIndex.value!!].dayEnd == -1) 23
                else daySelectViewModel.dayList.value!![daySelectViewModel.currentIndex.value!!].dayEnd,
                59, 59
            )
        }
        timePickerDialog.show(parentFragmentManager, "")
    }

    fun onObserveData(){
        binding.apply {
            val data = daySelectViewModel.dayList.value!![daySelectViewModel.currentIndex.value!!]
            checkEnableDaySelect.isChecked = data.enable
            if(data.dayStart != -1){
                textValueStartTimeDaySelect.text = "${data.dayStart}:00"
            } else {
                textValueStartTimeDaySelect.text = ""
            }
            if(data.dayEnd != -1){
                textValueEndTimeDaySelect.text = "${data.dayEnd}:00"
            } else {
                textValueEndTimeDaySelect.text = ""
            }
        }
    }
}
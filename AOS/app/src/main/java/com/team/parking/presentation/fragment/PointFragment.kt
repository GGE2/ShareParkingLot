package com.team.parking.presentation.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import com.team.parking.BuildConfig
import com.team.parking.MainActivity
import com.team.parking.R
import com.team.parking.databinding.FragmentPointBinding
import com.team.parking.presentation.viewmodel.PointViewModel
import com.team.parking.presentation.viewmodel.UserViewModel
import kr.co.bootpay.android.Bootpay
import kr.co.bootpay.android.events.BootpayEventListener
import kr.co.bootpay.android.models.BootItem
import kr.co.bootpay.android.models.BootUser
import kr.co.bootpay.android.models.Payload

class PointFragment : Fragment() {
   private lateinit var fragmentPointBinding: FragmentPointBinding
   private lateinit var userViewModel: UserViewModel
   private lateinit var pointViewModel: PointViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_point, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentPointBinding = FragmentPointBinding.bind(view)
        userViewModel = (activity as MainActivity).userViewModel
        pointViewModel = (activity as MainActivity).pointViewModel
        pointViewModel.ptHas.observe(viewLifecycleOwner){
            userViewModel.userLiveData.value!!.pt_has = it
            fragmentPointBinding.apply {
                textCurrentPointPoint.text = "${pointViewModel.ptHas.value} P"
                textAfterPoint.text = "${pointViewModel.ptHas.value!! + pointViewModel.point.value!!} P"
                textPricePoint.text = "${pointViewModel.point.value} 원"
            }
        }
        pointViewModel.point.observe(viewLifecycleOwner){
            fragmentPointBinding.apply {
                textAfterPoint.text = "${pointViewModel.ptHas.value!! + pointViewModel.point.value!!} P"
                textPricePoint.text = "${pointViewModel.point.value} 원"
            }
        }
        pointViewModel.getCurrentPoint(userViewModel.userLiveData.value!!.user_id)
        fragmentPointBinding.apply {
            textNicknamePoint.text = userViewModel.userLiveData.value!!.name
            textCurrentPointPoint.text = "${pointViewModel.ptHas.value} P"
        }
        fragmentPointBinding.apply {
            Glide.with(requireContext())
                .load(userViewModel.userLiveData.value!!.profile_img)
                .error(R.drawable.ic_baseline_person_24)
                .into(imageProfilePoint)
            select1000Point.isChecked = true
            pointViewModel.setPoint(1000)
            select1000Point.setOnClickListener {
                clearCheck()
                select1000Point.isChecked = true
                pointViewModel.setPoint(1000)
            }
            select3000Point.setOnClickListener {
                clearCheck()
                select3000Point.isChecked = true
                pointViewModel.setPoint(3000)
            }
            select5000Point.setOnClickListener {
                clearCheck()
                select5000Point.isChecked = true
                pointViewModel.setPoint(5000)
            }
            select10000Point.setOnClickListener {
                clearCheck()
                select10000Point.isChecked = true
                pointViewModel.setPoint(10000)
            }
            imageBackPoint.setOnClickListener {
                requireActivity().onBackPressed()
            }
            buttonCancelPoint.setOnClickListener {
                requireActivity().onBackPressed()
            }
            buttonPayPoint.setOnClickListener {
                val phoneOrigin = userViewModel.userLiveData.value!!.phone
                val phone = if (userViewModel.userLiveData.value!!.phone.isNotEmpty())"${phoneOrigin.substring(0 until 3)}-${phoneOrigin.substring(3 until 7)}-${phoneOrigin.substring(7 until 11)}"
                else "010-1234-5678"
                //bootpay
                val payload = Payload().setApplicationId(BuildConfig.BOOTPAY_CLIENT_KEY)
                    .setOrderName("${pointViewModel.point.value} P")
                    .setOrderId("${pointViewModel.point.value}")
                    .setPrice(pointViewModel.point.value!!.toDouble())
                    .setItems(listOf(BootItem()
                        .setName("${pointViewModel.point.value} P")
                        .setPrice(pointViewModel.point.value!!.toDouble())
                        .setQty(1)
                        .setId("${pointViewModel.point.value!! / 100}")
                    ))
                    .setUser(BootUser()
                            //번호 받을 시 지울 것
                        .setPhone(if(userViewModel.userLiveData.value!!.phone.isEmpty()) "010-1234-5678" else phone)
                    )
                Bootpay.init(requireActivity(), requireContext())
                    .setPayload(payload)
                    .setEventListener(object : BootpayEventListener{
                        override fun onCancel(data: String?) {
                            Log.d("bootpay", data ?: "")
                            Toast.makeText(requireContext(), "결제를 취소하셨습니다.", Toast.LENGTH_SHORT).show()
                        }

                        override fun onError(data: String?) {
                            Log.d("bootpay", data ?: "")
                        }

                        override fun onClose() {
                            Log.d("bootpay", "close" );
                            Bootpay.removePaymentWindow();
                        }

                        override fun onIssued(data: String?) {
                            Log.d("bootpay", data ?: "")
                        }

                        override fun onConfirm(data: String?): Boolean {
                            Log.d("bootpay", data ?: "")
                            return true
                        }

                        override fun onDone(data: String?) {
                            Toast.makeText(requireContext(), "결제가 완료되었습니다.", Toast.LENGTH_SHORT).show()
                            pointViewModel.putChargePoint(userViewModel.userLiveData.value!!.user_id)
                            Bootpay.removePaymentWindow()
                        }
                    }).requestPayment()
            }
        }
    }

    fun clearCheck(){
        fragmentPointBinding.apply {
            select1000Point.isChecked = false
            select3000Point.isChecked = false
            select5000Point.isChecked = false
            select10000Point.isChecked = false
        }
    }
}
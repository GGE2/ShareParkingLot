package com.team.parking.presentation.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.team.parking.MainActivity
import com.team.parking.R
import com.team.parking.databinding.FragmentTransactionHistoryBinding
import com.team.parking.presentation.adapter.EarnedPointAdapter
import com.team.parking.presentation.adapter.SpentPointAdapter
import com.team.parking.presentation.viewmodel.TransactionHistoryViewModel
import com.team.parking.presentation.viewmodel.TransactionHistoryViewModelFactory
import com.team.parking.presentation.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

private const val TAG = "TransactionHistoryFragm"

@AndroidEntryPoint
class TransactionHistoryFragment : Fragment() {

    private lateinit var fragmentTransactionHistoryBinding: FragmentTransactionHistoryBinding

    @Inject
    lateinit var transactionHistoryViewModelFactory: TransactionHistoryViewModelFactory
    private lateinit var transactionHistoryViewModel: TransactionHistoryViewModel
    private lateinit var userViewModel: UserViewModel
    val calendar = Calendar.getInstance()
    private lateinit var earnedPointAdapter: EarnedPointAdapter
    private lateinit var spentPointAdapter: SpentPointAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_transaction_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentTransactionHistoryBinding = FragmentTransactionHistoryBinding.bind(view)
        transactionHistoryViewModel = ViewModelProvider(this,transactionHistoryViewModelFactory)[TransactionHistoryViewModel::class.java]
        userViewModel = (activity as MainActivity).userViewModel
        transactionHistoryViewModel.earned = true
        earnedPointAdapter = EarnedPointAdapter()
        spentPointAdapter = SpentPointAdapter()
        transactionHistoryViewModel.month.observe(viewLifecycleOwner){
            calendar.set(Calendar.MONTH, it-1)
            setMonthText()
            getList()
        }
        transactionHistoryViewModel.year.observe(viewLifecycleOwner){
            calendar.set(Calendar.YEAR, it)
            setMonthText()
            getList()
        }
        transactionHistoryViewModel.earnedPointList.observe(viewLifecycleOwner){
            var total = 0
            if(transactionHistoryViewModel.earned){
                for (response in it){
                    total += response.ptGet
                }
                fragmentTransactionHistoryBinding.textTotalPriceTransactionHistory.text = "${total}"
            }
            earnedPointAdapter.differ.submitList(it)
        }
        transactionHistoryViewModel.spentPointList.observe(viewLifecycleOwner){
            var total = 0
            if(!transactionHistoryViewModel.earned){
                for (response in it){
                    total += response.ptLose
                }
                fragmentTransactionHistoryBinding.textTotalPriceTransactionHistory.text = "${total}"
            }
            spentPointAdapter.differ.submitList(it)
        }
        transactionHistoryViewModel.setMonth(calendar.get(Calendar.MONTH) + 1)
        transactionHistoryViewModel.setYear(calendar.get(Calendar.YEAR))
        fragmentTransactionHistoryBinding.apply {
            imageBackTransactionHistory.setOnClickListener {
                requireActivity().onBackPressed()
            }
            recyclerViewTransactionHistory.adapter = earnedPointAdapter
            recyclerViewTransactionHistory.layoutManager = LinearLayoutManager(requireContext())
            layoutPurchaseTransactionHistory.setOnClickListener {
                textTitleSellTransactionHistory.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
                underlineSellTransactionHistory.visibility = View.GONE
                textTitlePurchaseTransactionHistory.setTextColor(ContextCompat.getColor(requireContext(), R.color.main_color2))
                underlinePurchaseTransactionHistory.visibility = View.VISIBLE
                textTotalTransactionHistory.text = "누적 구매 금액"
                textTotalPriceTransactionHistory.setTextColor(ContextCompat.getColor(requireContext(), R.color.main_color2))
                //recyclerview
                transactionHistoryViewModel.earned = false
                recyclerViewTransactionHistory.adapter = spentPointAdapter
                getList()
            }
            layoutSellTransactionHistory.setOnClickListener {
                textTitlePurchaseTransactionHistory.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
                underlinePurchaseTransactionHistory.visibility = View.GONE
                textTitleSellTransactionHistory.setTextColor(ContextCompat.getColor(requireContext(), R.color.main_color))
                underlineSellTransactionHistory.visibility = View.VISIBLE
                textTotalTransactionHistory.text = "누적 판매 금액"
                textTotalPriceTransactionHistory.setTextColor(ContextCompat.getColor(requireContext(), R.color.main_color))
                //recyclerview
                transactionHistoryViewModel.earned = true
                recyclerViewTransactionHistory.adapter = earnedPointAdapter
                getList()
            }
            imageLeftMonthTransactionHistory.setOnClickListener {
                transactionHistoryViewModel.setPreviousMonth()
                Log.d(TAG, "onViewCreated: left")
            }
            imageRightMonthTransactionHistory.setOnClickListener {
                transactionHistoryViewModel.setNextMonth()
                Log.d(TAG, "onViewCreated: right ${transactionHistoryViewModel.month.value}")
            }
        }
    }

    fun setMonthText(){
        fragmentTransactionHistoryBinding.apply {
            val formatter = SimpleDateFormat("yyyy.MM")
            textMonthTransactionHistory.text = formatter.format(calendar.time)
        }
    }

    fun getList(){
        if(transactionHistoryViewModel.earned){
            transactionHistoryViewModel.getEarnedPoint(userViewModel.userLiveData.value!!.user_id)
        } else {
            transactionHistoryViewModel.getSpentPoint(userViewModel.userLiveData.value!!.user_id)
        }
    }
}
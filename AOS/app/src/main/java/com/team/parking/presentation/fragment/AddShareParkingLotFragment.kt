package com.team.parking.presentation.fragment

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentResolver
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.team.parking.MainActivity
import com.team.parking.R
import com.team.parking.databinding.FragmentAddShareParkingLotBinding
import com.team.parking.presentation.adapter.ShareParkingLotImageAdapter
import com.team.parking.presentation.viewmodel.*
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.scopes.FragmentScoped
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okio.BufferedSink
import okio.source
import javax.inject.Inject

private const val TAG = "AddShareParkingLotFragm"

@AndroidEntryPoint
class AddShareParkingLotFragment : Fragment() {

    @Inject
    lateinit var searchAddressViewModelFactory: SearchAddressViewModelFactory
    private lateinit var binding: FragmentAddShareParkingLotBinding
    private lateinit var searchAddressViewModel: SearchAddressViewModel
    private lateinit var shareParkingLotViewModel: ShareParkingLotViewModel
    private lateinit var shareParkingLotImageAdapter: ShareParkingLotImageAdapter
    private lateinit var userViewModel: UserViewModel
    private lateinit var daySelectViewModel: DaySelectViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddShareParkingLotBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchAddressViewModel = ViewModelProvider(this, searchAddressViewModelFactory)[SearchAddressViewModel::class.java]
        userViewModel = (activity as MainActivity).userViewModel
        daySelectViewModel = (activity as MainActivity).daySelectViewModel
        binding.buttonAddShareParkingLot.setOnClickListener {
            if(checkEmpty()){
                //api
                shareParkingLotViewModel.post(
                    if (binding.radioButtonEntrancePayAddShareParkingLot.isChecked) 0 else 1,
                    binding.editTextParkingLotNameAddShareParkingLot.text.toString(),
                    searchAddressViewModel.searchedAddress.value!!.address.address_name,
                    searchAddressViewModel.searchedAddress.value!!.road_address.address_name,
                    binding.editTextPriceAddShareParkingLot.text.toString().toInt(),
                    if(binding.editTextAdditionalInfoAddShareParkingLot.text.isEmpty()) "" else binding.editTextAdditionalInfoAddShareParkingLot.text.toString(),
                    searchAddressViewModel.searchedAddress.value!!.y.toFloat(),
                    searchAddressViewModel.searchedAddress.value!!.x.toFloat(),
                    userViewModel.user!!.user_id
                )
                binding.apply {
                    editTextParkingLotNameAddShareParkingLot.text.clear()
                    editTextPriceAddShareParkingLot.text.clear()
                    editTextAdditionalInfoAddShareParkingLot.text.clear()
                    radioButtonEntrancePayAddShareParkingLot.isChecked = false
                    radioButtonExitPayAddShareParkingLot.isChecked = false
                    searchAddressViewModel.searchedAddress.value = null
                }
                daySelectViewModel.add = true
                findNavController().navigate(R.id.action_addShareParkingLotFragment_to_daySelectFragment)
            }
        }
        binding.textAddressAddShareParkingLot.setOnClickListener {
            findNavController().navigate(R.id.action_addShareParkingLotFragment_to_searchAddressFragment)
        }
        (activity as MainActivity).searchAddressViewModel = searchAddressViewModel
        searchAddressViewModel.searchedAddress.observe(viewLifecycleOwner){
            requireActivity().runOnUiThread {
                if(it != null){
                    binding.textAddressAddShareParkingLot.text = it.address.address_name
                } else {
                    binding.textAddressAddShareParkingLot.text = "눌러서 주소를 검색해주세요."
                }
            }
        }
        shareParkingLotViewModel = (activity as MainActivity).shareParkingLotViewModel
        shareParkingLotViewModel.clearList()
        shareParkingLotImageAdapter = ShareParkingLotImageAdapter()
        binding.recyclerViewAddPictureAddShareParkingLot.apply {
            adapter = shareParkingLotImageAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }
        shareParkingLotViewModel.uriList.observe(viewLifecycleOwner){
            shareParkingLotImageAdapter.differ.submitList(it.toList())
        }
        binding.imageAddPictureAddShareParkingLot.setOnClickListener {
            checkPermisson()
        }
    }

    val result = registerForActivityResult(
    ActivityResultContracts.StartActivityForResult()
    ){
        if (it.resultCode == Activity.RESULT_OK){
            val uri = it.data!!.data
            if (uri != null){
                shareParkingLotViewModel.addImage(uri)
                shareParkingLotViewModel.multipartBodyList.add(uri.asMultipart("files", requireContext().contentResolver)!!)
            }
        }
    }

    private fun openGallery(){
        val intent = Intent(Intent.ACTION_PICK)
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*")
        result.launch(intent)
    }

    val permission = registerForActivityResult(ActivityResultContracts.RequestPermission()){
        if (it){
            openGallery()
        }
    }

    private fun checkPermisson(){
        permission.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
    }

    // uri to multipart
    @SuppressLint("Range")
    private fun Uri.asMultipart(name: String, contentResolver: ContentResolver): MultipartBody.Part?{
        return contentResolver.query(this, null, null, null, null)?.let {
            if (it.moveToNext()){
                val displayName = it.getString(it.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                val requestBody = object : RequestBody(){
                    override fun contentType(): MediaType? {
                        return contentResolver.getType(this@asMultipart)?.toMediaType()
                    }

                    @SuppressLint("Recycle")
                    override fun writeTo(sink: BufferedSink) {
                        sink.writeAll(contentResolver.openInputStream(this@asMultipart)?.source()!!)
                    }
                }
                it.close()
                MultipartBody.Part.createFormData(name, displayName, requestBody)
            } else{
                it.close()
                null
            }
        }
    }

    private fun checkEmpty() : Boolean{
        binding.apply {
            if(editTextParkingLotNameAddShareParkingLot.text.isEmpty()){
                Toast.makeText(requireContext(), "주차장 이름을 입력해주세요.", Toast.LENGTH_SHORT).show()
                return false
            } else if(searchAddressViewModel.searchedAddress.value == null){
                Toast.makeText(requireContext(), "주차장 주소를 입력해주세요.", Toast.LENGTH_SHORT).show()
                return false
            } else if(editTextPriceAddShareParkingLot.text.isEmpty()){
                Toast.makeText(requireContext(), "주차 요금을 입력해주세요.", Toast.LENGTH_SHORT).show()
                return false
            } else if(editTextPriceAddShareParkingLot.text.toString().toInt() % 100 != 0){
                Toast.makeText(requireContext(), "주차 요금을 100원 단위로 입력해주세요.", Toast.LENGTH_SHORT).show()
                return false
            } else if(!radioButtonEntrancePayAddShareParkingLot.isChecked && !radioButtonExitPayAddShareParkingLot.isChecked){
                Toast.makeText(requireContext(), "결제 방식을 선택해주세요.", Toast.LENGTH_SHORT).show()
                return false
            } else {
                return true
            }
        }
    }
}
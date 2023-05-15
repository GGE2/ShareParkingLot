package com.team.parking.presentation.fragment

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
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.team.parking.MainActivity
import com.team.parking.R
import com.team.parking.databinding.FragmentLoginBinding
import com.team.parking.databinding.FragmentProfileBinding
import com.team.parking.presentation.viewmodel.UserViewModel
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okio.BufferedSink
import okio.source
import java.io.File
import kotlin.math.log

private const val TAG = "ProfileFragment종건"

class ProfileFragment : Fragment() {
    private val GALLERY_REQUEST_CODE = 123
    private var selectImage : Uri? = "".toUri()
    private lateinit var binding: FragmentProfileBinding
    private lateinit var mainActivity: MainActivity
    private lateinit var userViewModel: UserViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        binding = FragmentProfileBinding.bind(view)
        userViewModel = (activity as MainActivity).userViewModel
        binding.apply {
            handlers = this@ProfileFragment
            lifecycleOwner = this@ProfileFragment
            viewModel = userViewModel
        }

        userViewModel.getProfile()

        binding.imageBackProfile.setOnClickListener {
            requireActivity().onBackPressed()
        }
        binding.textMainCarChangeProfile.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_myCarFragment)
        }
        Log.d(TAG, "onViewCreated: ${userViewModel.userLiveData.value?.profile_img}")

        Glide.with(this@ProfileFragment)
            .load(userViewModel.userLiveData.value?.profile_img)
            .error(R.drawable.ic_baseline_person_24)
            .into(binding.imageProfilePoint)

        binding.pencil.setOnClickListener() {
            it.visibility = View.INVISIBLE
            binding.check.visibility = View.VISIBLE
            binding.cancel.visibility = View.VISIBLE
        }

        binding.check.setOnClickListener(){



            it.visibility = View.INVISIBLE
            binding.cancel.visibility = View.INVISIBLE
            binding.pencil.visibility = View.VISIBLE

            if(selectImage!="".toUri()){
                var multipart : MultipartBody.Part = selectImage!!.asMultipart("image",requireContext().contentResolver)!!
                userViewModel.updateProfileImage(multipart)

                selectImage = "".toUri()
            }
        }
        binding.cancel.setOnClickListener(){
            it.visibility = View.INVISIBLE
            binding.check.visibility = View.INVISIBLE
            binding.pencil.visibility = View.VISIBLE
        }

        binding.imageProfilePoint.setOnClickListener(){
            if(binding.check.visibility == View.VISIBLE){
                val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(intent, GALLERY_REQUEST_CODE)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == GALLERY_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
            selectImage = data.data

            // Use the selectedImageUri to load the image into an ImageView or perform any other operation
            val imageView = binding.imageProfilePoint
            imageView?.setImageURI(selectImage)

            Log.d(TAG, "onActivityResult: $selectImage")

        }
    }


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

}
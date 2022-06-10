package com.dicoding.emodiary.ui.view

import android.Manifest
import android.content.Intent
import android.content.Intent.ACTION_GET_CONTENT
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.dicoding.emodiary.R
import com.dicoding.emodiary.databinding.ActivityProfileBinding
import com.dicoding.emodiary.ui.viewmodel.MainViewModel
import com.dicoding.emodiary.ui.viewmodel.ViewModelFactory
import com.dicoding.emodiary.utils.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File


class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    private var getFile: File? = null
    private lateinit var session: SessionManager
    companion object {
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 10
    }

    private val viewModel: MainViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        session = SessionManager(this)
        setContentView(binding.root)
        setupView()
        setupAction()
        supportActionBar?.title = "Profile"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(
                this,
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (!allPermissionsGranted()) {
                Toast.makeText(
                    this,
                    getString(R.string.permission),
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }


    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val selectedImg: Uri = result.data?.data as Uri
            val myFile = uriToFile(selectedImg, this@ProfileActivity)
            getFile = myFile

            getFile?.let { uploadImage(it) }
        }
    }

    private fun uploadImage(file: File) {
        val requestImageFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
        val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
            "photo",
            file.name,
            requestImageFile
        )

        viewModel.uploadPhoto(session.getString(USER_ID), imageMultipart).observe(this){
            when (it){
                is State.Loading -> {
                    binding.imgProfile.visibility = View.INVISIBLE
                    binding.progressBar.visibility = View.VISIBLE
                }
                is State.Success -> {
                    binding.imgProfile.visibility = View.VISIBLE
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(this, "Foto berhasil diubah", Toast.LENGTH_SHORT).show()
                    it.data.data?.photo?.let { foto -> session.setString(PHOTO_URL, foto) }
                    Glide.with(binding.imgProfile.context)
                        .load(it.data.data?.photo)
                        .circleCrop()
                        .into(binding.imgProfile)
                }
                is State.Error -> {
                    binding.imgProfile.visibility = View.VISIBLE
                    binding.progressBar.visibility = View.GONE
                    Log.d("pantau", it.error)
                    Toast.makeText(this, it.error, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun startGallery() {
        val intent = Intent()
        intent.action = ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Choose a Picture")
        launcherIntentGallery.launch(chooser)
    }


    private fun setupAction() {
        val modalBottomSheet = ModalBottomSheet()


        binding.fabEditPicture.setOnClickListener {
            startGallery()
        }

        binding.boxName.setOnClickListener {
            modalBottomSheet.show(supportFragmentManager, ModalBottomSheet.TAG)

            binding.boxEmail.setOnClickListener {
                modalBottomSheet.show(supportFragmentManager, ModalBottomSheet.TAG)
            }

            binding.boxPassword.setOnClickListener {
                modalBottomSheet.show(supportFragmentManager, ModalBottomSheet.TAG)
            }
        }
    }

    private fun setupView() {
        val session = SessionManager(this)
        Glide.with(binding.imgProfile.context)
            .load(R.drawable.default_profile)
            .circleCrop()
            .into(binding.imgProfile)

        binding.tvProfileName.text = session.getString(FULL_NAME)
        binding.tvProfileEmail.text = session.getString(EMAIL)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
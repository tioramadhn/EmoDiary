package com.dicoding.emodiary.ui.view

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.dicoding.emodiary.R
import com.dicoding.emodiary.databinding.ActivityProfileBinding
import com.dicoding.emodiary.utils.EMAIL
import com.dicoding.emodiary.utils.FULL_NAME
import com.dicoding.emodiary.utils.SessionManager

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupView()
        setupAction()
        supportActionBar?.title = "Profile"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setupAction() {
        val modalBottomSheet = ModalBottomSheet()


        binding.fabEditPicture.setOnClickListener {

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
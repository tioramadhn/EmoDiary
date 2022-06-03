package com.dicoding.emodiary.ui.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.dicoding.emodiary.R
import com.dicoding.emodiary.databinding.ActivityMainBinding
import com.dicoding.emodiary.ui.viewmodel.MainViewModel
import com.dicoding.emodiary.ui.viewmodel.ViewModelFactory
import com.dicoding.emodiary.utils.DataDummy
import com.dicoding.emodiary.utils.SessionManager
import com.dicoding.emodiary.utils.getDateNow
import com.dicoding.storyapp.adapter.DiaryListAdapter

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        setupView()
    }

    private fun setupView() {

        val session = SessionManager(this)

        //Set profile picture
        Glide.with(binding.imgProfil.context)
            .load(R.drawable.default_profile)
            .circleCrop()
            .into(binding.imgProfil)

        //Set say halo
        binding.tvHalo.text = getString(R.string.say_halo, session.getString("fullname"))

        //Set Date Now
        binding.tvDateNow.text = getDateNow()

        //Set all diary of user
        val adapter = DiaryListAdapter()
        binding.rvDiary.layoutManager = LinearLayoutManager(this@MainActivity)
        adapter.submitList(DataDummy.generateDummyDiaryResponse())
        binding.rvDiary.adapter = adapter

    }
}
package com.dicoding.emodiary.ui.view.menu.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.dicoding.emodiary.R
import com.dicoding.emodiary.databinding.FragmentHomeBinding
import com.dicoding.emodiary.ui.view.SettingsActivity
import com.dicoding.emodiary.utils.DataDummy
import com.dicoding.emodiary.utils.SessionManager
import com.dicoding.emodiary.utils.getDateNow
import com.dicoding.storyapp.adapter.DiaryListAdapter

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        setupView()
        setupAction()
        return root
    }

    private fun setupAction() {
        binding.btnSettings.setOnClickListener{
            startActivity(Intent(requireActivity(), SettingsActivity::class.java))
        }
    }


    private fun setupView() {

        val session = SessionManager(requireActivity())

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
        binding.rvDiary.layoutManager = LinearLayoutManager(requireActivity())
        adapter.submitList(DataDummy.generateDummyDiaryResponse())
        binding.rvDiary.adapter = adapter

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
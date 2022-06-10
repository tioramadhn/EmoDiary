package com.dicoding.emodiary.ui.view.menu.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.dicoding.emodiary.R
import com.dicoding.emodiary.databinding.FragmentHomeBinding
import com.dicoding.emodiary.ui.view.SettingsActivity
import com.dicoding.emodiary.ui.viewmodel.MainViewModel
import com.dicoding.emodiary.ui.viewmodel.ViewModelFactory
import com.dicoding.emodiary.utils.*
import com.dicoding.storyapp.adapter.DiaryListAdapter

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by viewModels {
        ViewModelFactory.getInstance(requireActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        setupView()
        setupAction()
        getEmotions()
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
        val photoUrl = session.getString(PHOTO_URL)
        Log.d("photoUrl : ", photoUrl )
        if(photoUrl.isEmpty()){
            Glide.with(binding.imgProfil.context)
                .load(R.drawable.default_profile)
                .circleCrop()
                .into(binding.imgProfil)
        }else{
            Glide.with(binding.imgProfil.context)
                .load(photoUrl)
                .circleCrop()
                .into(binding.imgProfil)
        }


        //Set say halo
        binding.tvHalo.text = getString(R.string.say_halo, session.getString(FULL_NAME))

        //Set Date Now
        binding.tvDateNow.text = getDateNow().toString().withDateFormat()

        //Set all diary of user
        val adapter = DiaryListAdapter()
        binding.rvDiary.layoutManager = LinearLayoutManager(requireActivity())

        viewModel.getDiaries().observe(viewLifecycleOwner){
            when(it){
                is State.Loading ->{
                    binding.progressBar.visibility = View.VISIBLE
                }
                is State.Success -> {
                    binding.progressBar.visibility = View.INVISIBLE
                    val result = it.data
                    if(result.data?.isNotEmpty() == true){
                        adapter.submitList(result.data)
                        binding.rvDiary.adapter = adapter
                    }else {
                        binding.imgNoDiary.visibility = View.VISIBLE
                        binding.tvTitleNoDiary.visibility = View.VISIBLE
                        binding.tvDescNoDiary.visibility = View.VISIBLE
                    }
                }
                is State.Error -> {
                    binding.progressBar.visibility = View.INVISIBLE
                    Toast.makeText(
                        requireActivity(),
                        it.error,
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    private fun getEmotions() {
        viewModel.getEmotions().observe(viewLifecycleOwner) {
            when (it) {
                is State.Loading -> {
                    binding.tvYourMoodToday.text = "loading..."
                    binding.imgMood.text = myEmotion(requireActivity(), "unknown").second
                }
                is State.Success -> {
                    val res = it.data.data
                    if (res != null) {
                        binding.tvYourMoodToday.text = myEmotion(
                            requireActivity(),
                            res.emotion.toString()
                        ).first
                        binding.imgMood.text =
                            myEmotion(requireActivity(), res.emotion.toString()).second
                    }
                }
                is State.Error -> {
                    binding.tvYourMoodToday.text = "Something wrong.."
                    binding.imgMood.text = myEmotion(requireActivity(), "unknown").second
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
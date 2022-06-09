package com.dicoding.emodiary.ui.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.dicoding.emodiary.R
import com.dicoding.emodiary.databinding.FragmentEditProfileBinding
import com.dicoding.emodiary.databinding.FragmentHomeBinding
import com.dicoding.emodiary.ui.viewmodel.MainViewModel
import com.dicoding.emodiary.ui.viewmodel.ViewModelFactory
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ModalBottomSheet : BottomSheetDialogFragment() {
    private var _binding: FragmentEditProfileBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    companion object {
        const val TAG = "ModalBottomSheet"
    }


    private val viewModel: MainViewModel by viewModels {
        ViewModelFactory.getInstance(requireActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEditProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root
//        setupView()
//        setupAction()
        return root
    }

//    private fun setupAction() {
//        TODO("Not yet implemented")
//    }
//
//    private fun setupView() {
//        TODO("Not yet implemented")
//    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
package com.dicoding.emodiary.ui.view.menu.article

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.dicoding.emodiary.R
import com.dicoding.emodiary.adapter.ArticleListAdapter
import com.dicoding.emodiary.databinding.FragmentArticleBinding
import com.dicoding.emodiary.databinding.FragmentHomeBinding
import com.dicoding.emodiary.databinding.UnderDevelopmentBinding
import com.dicoding.emodiary.ui.viewmodel.MainViewModel
import com.dicoding.emodiary.ui.viewmodel.ViewModelFactory
import com.dicoding.emodiary.utils.State

class ArticleFragment : Fragment() {
    private var _binding: UnderDevelopmentBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by viewModels {
        ViewModelFactory.getInstance(requireActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        _binding = FragmentArticleBinding.inflate(inflater, container, false)
//        val root: View = binding.root
//        setupView()
//        return root
        _binding = UnderDevelopmentBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    private fun setupView() {
        val emotions = listOf(
            getString(R.string.joy),
            getString(R.string.sadness),
            getString(R.string.fear),
            getString(R.string.anger),
            getString(R.string.surprise),
            getString(R.string.love)
        )
        viewModel.getArticles(emotions).observe(viewLifecycleOwner){
            when (it){
                is State.Loading -> {}
                is State.Success -> {}
                is State.Error -> {}
            }
        }

        val adapter = ArticleListAdapter()
    }

}
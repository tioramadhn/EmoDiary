package com.dicoding.emodiary.ui.view.menu.article

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.emodiary.R
import com.dicoding.emodiary.adapter.ArticleListAdapter
import com.dicoding.emodiary.databinding.FragmentArticleBinding
import com.dicoding.emodiary.ui.viewmodel.MainViewModel
import com.dicoding.emodiary.ui.viewmodel.ViewModelFactory
import com.dicoding.emodiary.utils.State

class ArticleFragment : Fragment() {
    private var _binding: FragmentArticleBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by viewModels {
        ViewModelFactory.getInstance(requireActivity())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentArticleBinding.inflate(inflater, container, false)
        val root: View = binding.root
        setupView()
        return root
    }

    private fun setupView() {
        val emotions = listOf(
            getString(R.string.sadness),
            getString(R.string.joy),
            getString(R.string.anger),
            getString(R.string.fear),
            getString(R.string.love),
            getString(R.string.surprise)
        )

        val adapter = ArticleListAdapter()
        binding.apply {
            rvArticle.layoutManager = LinearLayoutManager(requireActivity())
            rvArticle.adapter = adapter
        }

        viewModel.getArticles(emotions).observe(viewLifecycleOwner) {
            when (it) {
                is State.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is State.Success -> {
                    binding.progressBar.visibility = View.GONE
                    val result = it.data
                    if (result.data?.isNotEmpty() == true) adapter.submitList(result.data)
                }
                is State.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(requireActivity(), it.error, Toast.LENGTH_SHORT).show()
                    Log.d("ArticleFragment", it.error)
                }
            }
        }
    }
}
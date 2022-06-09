package com.dicoding.emodiary.ui.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.emodiary.R
import com.dicoding.emodiary.adapter.ArticleListAdapter
import com.dicoding.emodiary.data.remote.response.DiaryItem
import com.dicoding.emodiary.databinding.ActivityMainBinding
import com.dicoding.emodiary.databinding.FragmentHomeBinding
import com.dicoding.emodiary.ui.viewmodel.MainViewModel
import com.dicoding.emodiary.ui.viewmodel.ViewModelFactory
import com.dicoding.emodiary.utils.State
import com.dicoding.emodiary.utils.myEmotion
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetBehavior

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    companion object {
        const val EXTRA_DIARY = "extra_diary"
    }

    private val viewModel: MainViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        //Go to add diary
        binding.fab.setOnClickListener {
            val intent = Intent(this, DetailOrAddOrEditDiaryActivity::class.java)
            intent.putExtra(DetailOrAddOrEditDiaryActivity.FLAG_ADD, true)
            startActivity(intent)
        }

        val bottomSheetBehavior = BottomSheetBehavior.from(binding.bottomSheet)
        val diaryResponse = intent.getParcelableExtra<DiaryItem>(EXTRA_DIARY)
        if (diaryResponse != null) {
            Log.d("diaryResponse : ", diaryResponse.articles.toString())
            binding.bgOverlay.visibility = View.VISIBLE
            val adapter = ArticleListAdapter()
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
            binding.apply {
                tvMood.text = diaryResponse.emotion
                tvEmoji.text =
                    diaryResponse.emotion?.let { myEmotion(this@MainActivity, it).second }
                rvArticle.layoutManager = LinearLayoutManager(this@MainActivity)
                adapter.submitList(diaryResponse.articles)
                rvArticle.adapter = adapter
            }

        } else {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
            binding.bgOverlay.visibility = View.GONE
        }

        val bottomSheetCallback = object : BottomSheetBehavior.BottomSheetCallback() {

            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_HIDDEN -> binding.bgOverlay.visibility = View.GONE
                }

            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                // Do something for slide offset.
            }
        }

        // To add the callback:
        bottomSheetBehavior.addBottomSheetCallback(bottomSheetCallback)

        val navView: BottomNavigationView = binding.bottomNavigationView
        navView.background = null
        val navController = findNavController(R.id.nav_host_fragment_activity_home)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home,
                R.id.navigation_profile,
                R.id.navigation_search,
                R.id.navigation_article
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }


}
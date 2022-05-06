package com.dicoding.emodiary.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.dicoding.emodiary.R
import com.dicoding.emodiary.ui.viewmodel.MainViewModel
import com.dicoding.emodiary.ui.viewmodel.ViewModelFactory

class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
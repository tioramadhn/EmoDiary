package com.dicoding.emodiary.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.dicoding.emodiary.R

fun ImageView.setImage(context: Context, url: String?) {
    Glide.with(context).load(url).placeholder(R.drawable.default_profile).circleCrop().into(this)
}
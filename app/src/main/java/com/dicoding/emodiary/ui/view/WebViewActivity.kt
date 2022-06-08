package com.dicoding.emodiary.ui.view

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import com.dicoding.emodiary.R
import com.dicoding.emodiary.databinding.ActivityWebViewBinding
import com.dicoding.emodiary.utils.onAlertDialog

class WebViewActivity : AppCompatActivity() {
    private lateinit var binding : ActivityWebViewBinding
    companion object{
        const val EXTRA_URL = "extra_url"
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val url = intent.getStringExtra(EXTRA_URL)
        binding.apply{
            webView.settings.javaScriptEnabled = true

            webView.webViewClient = object : WebViewClient() {
                override fun onPageFinished(view: WebView, url: String) {
                    Toast.makeText(this@WebViewActivity, "Artikel berhasil dimuat", Toast.LENGTH_LONG).show()
                }
            }

            if (url != null) {
                webView.loadUrl(url)
            }
        }

        supportActionBar?.title = "Artikel"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
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
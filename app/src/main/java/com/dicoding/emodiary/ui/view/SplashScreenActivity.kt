package com.dicoding.emodiary.ui.view

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowInsets
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.emodiary.R
import com.dicoding.emodiary.utils.ACCESS_TOKEN
import com.dicoding.emodiary.utils.DELAY
import com.dicoding.emodiary.utils.IS_USER_SEEN_ONBOARDING_SCREEN
import com.dicoding.emodiary.utils.SessionManager

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        setupView()

        Handler(Looper.getMainLooper()).postDelayed({
            val session = SessionManager(this)
            if(session.getBoolean(IS_USER_SEEN_ONBOARDING_SCREEN ) && session.getString(ACCESS_TOKEN).isNotEmpty()){
                val intent = Intent(this@SplashScreenActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            }else if(session.getBoolean(IS_USER_SEEN_ONBOARDING_SCREEN ) && session.getString(ACCESS_TOKEN).isEmpty()){
                val intent = Intent(this@SplashScreenActivity, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }else{
                val intent = Intent(this@SplashScreenActivity, OnBoardingActivity::class.java)
                startActivity(intent)
                finish()
            }
        }, DELAY)
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }
}
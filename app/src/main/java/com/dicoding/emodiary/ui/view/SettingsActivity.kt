package com.dicoding.emodiary.ui.view

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.emodiary.R
import com.dicoding.emodiary.databinding.ActivitySettingsBinding
import com.dicoding.emodiary.utils.IS_USER_SEEN_ONBOARDING_SCREEN
import com.dicoding.emodiary.utils.SessionManager
import com.dicoding.emodiary.utils.onAlertDialog

class SettingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingsBinding

    //    private lateinit var binding: UnderDevelopmentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = getString(R.string.settings)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setupAction()
    }

    private fun setupAction() {
        //Logout
        binding.btnLogout.setOnClickListener {
            onAlertDialog(
                this,
                getString(R.string.title_logout_msg),
                getString(R.string.desc_logout_msg),
                getString(R.string.back),
                getString(R.string.next),
            ) {
                logOut()
            }

        }
    }

    private fun logOut() {
        val sessionManager = SessionManager(this)
        sessionManager.clearSession()
        sessionManager.setBoolean(IS_USER_SEEN_ONBOARDING_SCREEN, true)
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
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
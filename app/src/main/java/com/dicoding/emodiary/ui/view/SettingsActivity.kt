package com.dicoding.emodiary.ui.view

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.emodiary.BuildConfig
import android.provider.Settings
import android.widget.CompoundButton
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import com.dicoding.emodiary.R
import com.dicoding.emodiary.databinding.ActivitySettingsBinding
import com.dicoding.emodiary.ui.viewmodel.MainViewModel
import com.dicoding.emodiary.ui.viewmodel.ViewModelFactory
import com.dicoding.emodiary.utils.*
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

class SettingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingsBinding
    private lateinit var gso: GoogleSignInOptions
    private lateinit var gsc: GoogleSignInClient
    private val viewModel: MainViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = getString(R.string.settings)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(BuildConfig.SERVER_CLIENT_ID)
            .requestEmail()
            .build()
        gsc = GoogleSignIn.getClient(this, gso)

        setupAction()
        setupView()
    }

    private fun setupView() {
        val session = SessionManager(this)
        val photoUrl = session.getString(PHOTO_URL)
        binding.apply {
            tvName.text = session.getString(FULL_NAME)
            tvEmail.text = session.getString(EMAIL)

            if (photoUrl.isNotEmpty()) imgProfilePicture.setImage(this@SettingsActivity, photoUrl)

            viewModel.getThemeSetting().observe(this@SettingsActivity) { isDarkModeActive: Boolean ->
                darkModeSwitch.isChecked = isDarkModeActive
                val default = if(isDarkModeActive) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
                AppCompatDelegate.setDefaultNightMode(default)
            }

            darkModeSwitch.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
                viewModel.saveThemeSetting(isChecked)
            }
        }
    }

    private fun setupAction() {
        binding.box.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }

        binding.btnLanguange.setOnClickListener {
            startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
        }

        binding.btnLogout.setOnClickListener {
            onAlertDialog(
                this,
                getString(R.string.title_logout_msg),
                getString(R.string.desc_logout_msg),
                getString(R.string.cancel),
                getString(R.string.yes),
            ) {
                logOut()
            }

        }
    }

    private fun logOut() {
        gsc.signOut().addOnCompleteListener {
            val sessionManager = SessionManager(this)
            sessionManager.clearSession()
            sessionManager.setBoolean(IS_USER_SEEN_ONBOARDING_SCREEN, true)
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
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
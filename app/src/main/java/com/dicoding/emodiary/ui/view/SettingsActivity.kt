package com.dicoding.emodiary.ui.view

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.dicoding.emodiary.BuildConfig
import com.dicoding.emodiary.R
import com.dicoding.emodiary.databinding.ActivitySettingsBinding
import com.dicoding.emodiary.utils.*
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

class SettingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingsBinding
    private lateinit var gso: GoogleSignInOptions
    private lateinit var gsc: GoogleSignInClient

    //    private lateinit var binding: UnderDevelopmentBinding
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
        binding.tvName.text = session.getString(FULL_NAME)
        binding.tvEmail.text = session.getString(EMAIL)
        val photoUrl = session.getString(PHOTO_URL)
        if(photoUrl.isEmpty()){
            Glide.with(binding.imgProfilePicture.context)
                .load(R.drawable.default_profile)
                .circleCrop()
                .into(binding.imgProfilePicture)
        }else{
            Glide.with(binding.imgProfilePicture.context)
                .load(photoUrl)
                .circleCrop()
                .into(binding.imgProfilePicture)
        }
    }

    private fun setupAction() {
        //go to profile
        binding.box.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
            finish()
        }

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
package com.dicoding.emodiary.ui.view

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.emodiary.BuildConfig
import com.dicoding.emodiary.R
import com.dicoding.emodiary.data.remote.response.LoginResponse
import com.dicoding.emodiary.databinding.ActivityLoginBinding
import com.dicoding.emodiary.ui.viewmodel.MainViewModel
import com.dicoding.emodiary.ui.viewmodel.ViewModelFactory
import com.dicoding.emodiary.utils.*
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var gso: GoogleSignInOptions
    private lateinit var gsc: GoogleSignInClient
    private val viewModel: MainViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupView()
        setupAction()
        setupGoogleAuthServices()
    }

    private fun setupAction() {
        binding.apply {
            btnDaftar.setOnClickListener {
                startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
            }

            btnMasuk.setOnClickListener {
                val email = emailEditText.text.toString()
                val password = passwordEditText.text.toString()

                val validEmail = isEmailValid(this@LoginActivity, email)
                val validPassword = isPasswordValid(this@LoginActivity, password)

                when {
                    !validEmail.second -> {
                        emailEditTextLayout.error = validEmail.first
                    }
                    !validPassword.second -> {
                        passwordEditTextLayout.error = validPassword.first
                    }
                    else -> {
                        viewModel.login(email, password).observe(this@LoginActivity) {
                            when (it) {
                                is State.Loading -> {
                                    progressBar.visibility = View.VISIBLE
                                    btnMasuk.visibility = View.INVISIBLE
                                    btnMasukGoogle.isEnabled = false
                                }
                                is State.Success -> {
                                    progressBar.visibility = View.GONE
                                    btnMasuk.visibility = View.VISIBLE
                                    btnMasukGoogle.isEnabled = true
                                    val data = it.data
                                    login(data)
                                }
                                is State.Error -> {
                                    progressBar.visibility = View.GONE
                                    btnMasuk.visibility = View.VISIBLE
                                    btnMasukGoogle.isEnabled = true
                                    Toast.makeText(this@LoginActivity, it.error, Toast.LENGTH_SHORT).show()
                                    Log.d("LoginActivity", it.error)
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun login(data: LoginResponse) {
        val session = SessionManager(this)

        data.apply {
            accessToken?.let { session.setString(ACCESS_TOKEN, it) }
            refreshToken?.let { session.setString(REFRESH_TOKEN, it) }
            photo?.let { session.setString(PHOTO_URL, it) }
            fullname?.let { session.setString(FULL_NAME, it) }
            email?.let { session.setString(EMAIL, it) }
            id?.let { session.setString(USER_ID, it) }
        }
        moveToMainActivity()
    }

    private fun moveToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
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

    @Suppress("DEPRECATION")
    private fun setupGoogleAuthServices() {
        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(BuildConfig.SERVER_CLIENT_ID)
            .requestEmail()
            .build()
        gsc = GoogleSignIn.getClient(this, gso)

        binding.btnMasukGoogle.setOnClickListener {
            val signInIntent = gsc.signInIntent
            launcherIntentGoogleSignIn.launch(signInIntent)
        }
    }

    private val launcherIntentGoogleSignIn = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            try {
                val response = task.getResult(ApiException::class.java)
                if (response != null) swapToken(response.idToken.toString())
            } catch (e: ApiException) {
                Toast.makeText(applicationContext, getString(R.string.unknown_error), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun swapToken(accessToken: String) {
        viewModel.loginWithGoogle(accessToken).observe(this) {
            when (it) {
                is State.Loading -> {
                    binding.apply {
                        progressBarGoogle.visibility = View.VISIBLE
                        btnMasukGoogle.visibility = View.INVISIBLE
                    }
                }
                is State.Success -> {
                    binding.apply {
                        progressBarGoogle.visibility = View.INVISIBLE
                        btnMasukGoogle.visibility = View.VISIBLE
                    }
                    login(it.data)
                }
                is State.Error -> {
                    binding.apply {
                        progressBarGoogle.visibility = View.INVISIBLE
                        btnMasukGoogle.visibility = View.VISIBLE
                    }
                }
            }
        }
    }
}
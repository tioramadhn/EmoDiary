package com.dicoding.emodiary.ui.view

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
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
        initGoogleAuthServices()
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
                                    Toast.makeText(
                                        this@LoginActivity,
                                        getString(R.string.login_success_msg),
                                        Toast.LENGTH_LONG
                                    ).show()
                                    Log.d("Response LOGIN SUKSES", data.toString())

                                    login(data)
                                }
                                is State.Error -> {
                                    progressBar.visibility = View.GONE
                                    btnMasuk.visibility = View.VISIBLE
                                    btnMasukGoogle.isEnabled = true
                                    Toast.makeText(
                                        this@LoginActivity,
                                        it.error,
                                        Toast.LENGTH_LONG
                                    ).show()

                                    Log.d("Response LOGIN ERR", it.error)
                                }

                                else -> {}
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
    private fun initGoogleAuthServices() {
        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(BuildConfig.SERVER_CLIENT_ID)
            .requestEmail()
            .build()
        gsc = GoogleSignIn.getClient(this, gso)

        binding.btnMasukGoogle.setOnClickListener {
            val signInIntent = gsc.signInIntent
            startActivityForResult(signInIntent, 1000)
        }
    }

    @Suppress("DEPRECATION")
    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == 1000) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val result = task.getResult(ApiException::class.java)
                if (result != null) swapToken(result.idToken.toString())
            } catch (e: ApiException) {
                Toast.makeText(applicationContext, "Something went wrong", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun swapToken(accessToken: String) {
        viewModel.loginWithGoogle(accessToken).observe(this) {
            when(it) {
                is State.Loading -> {
                    // TODO: fill the ui logic
                    Log.d("pantau dari backend", "loading...")
                }
                is State.Success -> {
                    login(it.data)
                    Log.d("pantau dari backend", it.data.accessToken.toString())
                }
                is State.Error -> {
                    // TODO: fill the ui logic
                    Log.d("pantau dari backend", it.error)
                }
            }
        }
    }
}
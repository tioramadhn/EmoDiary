package com.dicoding.emodiary.ui.view

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
import com.dicoding.emodiary.R
import com.dicoding.emodiary.data.remote.body.RegisterBody
import com.dicoding.emodiary.databinding.ActivityRegisterBinding
import com.dicoding.emodiary.ui.viewmodel.MainViewModel
import com.dicoding.emodiary.ui.viewmodel.ViewModelFactory
import com.dicoding.emodiary.utils.State
import com.dicoding.emodiary.utils.isEmailValid
import com.dicoding.emodiary.utils.isPasswordValid
import com.dicoding.emodiary.utils.onAlertDialog

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private val viewModel: MainViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupView()
        setupAction()
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

    private fun setupAction() {
        binding.apply {
            btnLogin.setOnClickListener {
                startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
            }

            btnDaftar.setOnClickListener {
                val name = nameEditText.text.toString()
                val email = emailEditText.text.toString()
                val password = passwordEditText.text.toString()
                val validEmail = isEmailValid(this@RegisterActivity, email)
                val validPassword = isPasswordValid(this@RegisterActivity, password)

                when {
                    name.isEmpty() -> {
                        nameEditTextLayout.error = getString(R.string.name_is_empty)
                    }
                    !validEmail.second -> {
                        emailEditTextLayout.error = validEmail.first
                    }
                    !validPassword.second -> {
                        passwordEditTextLayout.error = validPassword.first
                    }
                    else -> {
                        val registerBody = RegisterBody(email, name, password)
                        viewModel.register(registerBody).observe(this@RegisterActivity) {
                            when (it) {
                                is State.Loading -> {
                                    progressBar.visibility = View.VISIBLE
                                    btnDaftar.visibility = View.INVISIBLE
                                }
                                is State.Success -> {
                                    progressBar.visibility = View.GONE
                                    btnDaftar.visibility = View.VISIBLE

                                    onAlertDialog(
                                        this@RegisterActivity,
                                        getString(R.string.regist_success_msg),
                                        getString(R.string.regist_success_msg_desc),
                                        getString(R.string.cancel),
                                        getString(R.string.yes)
                                    ) {
                                        startActivity(
                                            Intent(
                                                this@RegisterActivity,
                                                LoginActivity::class.java
                                            )
                                        )
                                    }
                                }
                                is State.Error -> {
                                    progressBar.visibility = View.GONE
                                    btnDaftar.visibility = View.VISIBLE
                                    Toast.makeText(this@RegisterActivity, it.error, Toast.LENGTH_SHORT).show()
                                    Log.d("RegisterActivity", it.error)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
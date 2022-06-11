package com.dicoding.emodiary.ui.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.emodiary.R
import com.dicoding.emodiary.data.remote.body.CreateDiaryBody
import com.dicoding.emodiary.data.remote.response.BaseResponse
import com.dicoding.emodiary.data.remote.response.DiaryItem
import com.dicoding.emodiary.databinding.ActivityDetailOrAddOrEditDiaryBinding
import com.dicoding.emodiary.ui.viewmodel.MainViewModel
import com.dicoding.emodiary.ui.viewmodel.ViewModelFactory
import com.dicoding.emodiary.utils.State
import com.dicoding.emodiary.utils.onAlertDialog
import com.dicoding.emodiary.utils.withDateFormat

class DetailOrAddOrEditDiaryActivity : AppCompatActivity() {
    private var isEdit = false
    private var diary: DiaryItem? = null
    private var isAdd = false
    private val viewModel: MainViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }

    private lateinit var binding: ActivityDetailOrAddOrEditDiaryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailOrAddOrEditDiaryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionBarTitle: String
        diary = intent.getParcelableExtra(EXTRA_DIARY)
        isAdd = intent.getBooleanExtra(FLAG_ADD, false)
        if (diary != null) {
            actionBarTitle = getString(R.string.my_diary)
            diary?.let {
                binding.tvDetailTitle.text = it.title
                binding.tvDetailContent.text = it.content
                binding.tvDetailDate.text = getString(R.string.upload_on, it.timeCreated?.withDateFormat())
            }
        } else {
            actionBarTitle = getString(R.string.add_diary_title)
            showAddOrEditView(getString(R.string.add_diary_btn))
        }

        supportActionBar?.title = actionBarTitle
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setupAction()
    }

    private fun setupAction() {
        binding.fab.setOnClickListener {
            isEdit = true
            showAddOrEditView(getString(R.string.update_diary))
            diary?.let {
                binding.titleEditText.setText(it.title)
                binding.contentEditText.setText(it.content)
            }
        }

        binding.titleEditText.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus || binding.titleEditText.text?.isNotEmpty() == true) binding.titleEditTextLayout.hint = null
            else binding.titleEditText.hint = getString(R.string.judul_hint)
        }

        binding.contentEditText.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus || binding.contentEditText.text?.isNotEmpty() == true) binding.contentEditTextLayout.hint = null
            else binding.contentEditText.hint = getString(R.string.content_hint)
        }

        binding.btnSubmit.setOnClickListener {
            it.hideKeyboard()

            val title = binding.titleEditText.text.toString()
            val content = binding.contentEditText.text.toString()
            if (title.isNotEmpty() && content.isNotEmpty()) {
                binding.progressBar.visibility = View.VISIBLE
                binding.btnSubmit.visibility = View.INVISIBLE
            }
            val diaryBody = CreateDiaryBody(title, content)
            when {
                title.isEmpty() -> binding.titleEditTextLayout.error = getString(R.string.title_is_empty)
                content.isEmpty() -> binding.contentEditTextLayout.error = getString(R.string.content_is_empty)
                else -> {
                    if (isAdd) createDiary(title, content)
                    else if (isEdit) updateDiary(diary?.id, diaryBody)
                }
            }
        }
    }

    private fun View.hideKeyboard() {
        val inputManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(windowToken, 0)
    }

    private fun updateDiary(id: String?, diaryBody: CreateDiaryBody) {
        if (id != null) {
            viewModel.updateDiary(id, diaryBody).observe(this) {
                when (it) {
                    is State.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is State.Success -> {
                        binding.progressBar.visibility = View.GONE
                        binding.btnSubmit.visibility = View.VISIBLE
                        showBottomSheet(it.data)
                    }
                    is State.Error -> {
                        binding.progressBar.visibility = View.GONE
                        binding.btnSubmit.visibility = View.VISIBLE
                        Toast.makeText(this@DetailOrAddOrEditDiaryActivity, it.error, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun createDiary(title: String, content: String) {
        val diaryBody = CreateDiaryBody(title, content)
        viewModel.createDiary(diaryBody).observe(this) {
            when (it) {
                is State.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is State.Success -> {
                    binding.progressBar.visibility = View.GONE
                    binding.btnSubmit.visibility = View.VISIBLE
                    showBottomSheet(it.data)
                }
                is State.Error -> {
                    binding.progressBar.visibility = View.GONE
                    binding.btnSubmit.visibility = View.VISIBLE
                    Toast.makeText(this@DetailOrAddOrEditDiaryActivity, it.error, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun showBottomSheet(data: BaseResponse<DiaryItem?>) {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra(MainActivity.EXTRA_DIARY, data.data)
        startActivity(intent)
    }

    private fun showAddOrEditView(btnTitle: String) {
        binding.apply {
            tvEditTitleDiary.visibility = View.VISIBLE
            titleEditTextLayout.visibility = View.VISIBLE
            tvContentDiary.visibility = View.VISIBLE
            contentEditTextLayout.visibility = View.VISIBLE
            btnSubmit.visibility = View.VISIBLE
            btnSubmit.text = btnTitle
            divider.visibility = View.INVISIBLE
            fab.visibility = View.INVISIBLE
            tvDetailDate.visibility = View.INVISIBLE
            tvDetailTitle.visibility = View.INVISIBLE
            tvDetailContent.visibility = View.INVISIBLE
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        return if (isAdd) {
            false
        } else {
            val inflater = menuInflater
            inflater.inflate(R.menu.option_menu, menu)
            true
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> if (isEdit) onAlertDialog(
                this,
                getString(R.string.title_edit_dialog),
                getString(R.string.title_contentEdit_dialog),
                getString(R.string.cancel),
                getString(R.string.yes)
            ) {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                finish()
                return true
            }
            R.id.menu_delete -> {
                onAlertDialog(
                    this,
                    getString(R.string.title_delete_dialog),
                    getString(R.string.content_delete_dialog),
                    getString(R.string.cancel),
                    getString(R.string.yes)
                ) {
                    deleteDiary()
                }
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteDiary() {
        binding.btnSubmit.visibility = View.INVISIBLE
        diary?.id?.let {
            viewModel.deleteDiary(it).observe(this) { state ->
                when (state) {
                    is State.Loading -> {
                        binding.progressBar.visibility = View.INVISIBLE
                    }
                    is State.Success -> {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(this@DetailOrAddOrEditDiaryActivity, getString(R.string.delete_success_msg), Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    }
                    is State.Error -> {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(this@DetailOrAddOrEditDiaryActivity, state.error, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    override fun onBackPressed() {
        if (isEdit) {
            onAlertDialog(
                this,
                getString(R.string.title_edit_dialog),
                getString(R.string.title_contentEdit_dialog),
                getString(R.string.cancel),
                getString(R.string.yes)
            ) {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        } else {
            finish()
        }
    }

    companion object {
        const val EXTRA_DIARY = "EXTRA_DIARY"
        const val FLAG_ADD = "FLAG_ADD"
    }
}
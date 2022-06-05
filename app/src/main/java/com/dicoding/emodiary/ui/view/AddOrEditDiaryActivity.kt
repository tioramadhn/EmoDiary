package com.dicoding.emodiary.ui.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.dicoding.emodiary.R
import com.dicoding.emodiary.data.remote.response.DiaryItem
import com.dicoding.emodiary.databinding.ActivityAddOrEditDiaryBinding
import com.dicoding.emodiary.utils.onAlertDialog

class AddOrEditDiaryActivity : AppCompatActivity() {
    private var isEdit = false
    private var diary: DiaryItem? = null
    companion object {
        const val EXTRA_DIARY = "extra_diary"
    }
    private lateinit var binding: ActivityAddOrEditDiaryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddOrEditDiaryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionBarTitle: String
        val btnTitle: String
        diary = intent.getParcelableExtra(EXTRA_DIARY)
        if(diary != null){
            actionBarTitle = "Ubah Diary"
            btnTitle = "Ubah"
            isEdit = true
            diary?.let{
                binding.titleEditText.setText(it.title)
                binding.contentEditText.setText(it.content)
            }
        }else{
            actionBarTitle = "Tambah Diary"
            btnTitle = "Tambah"
        }

        supportActionBar?.title = actionBarTitle
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.btnSubmit.text = btnTitle
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> if(isEdit) onAlertDialog(
                this,
                getString(R.string.title_edit_dialog),
                getString(R.string.title_contentEdit_dialog),
                getString(R.string.back),
                getString(R.string.next)
            ){
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        if(isEdit){
            onAlertDialog(
                this,
                getString(R.string.title_edit_dialog),
                getString(R.string.title_contentEdit_dialog),
                getString(R.string.back),
                getString(R.string.next)
            ){
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }else{
            finish()
        }
    }
}
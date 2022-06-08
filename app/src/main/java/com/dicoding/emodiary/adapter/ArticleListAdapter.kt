package com.dicoding.emodiary.adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.emodiary.data.remote.response.ArticleItem
import com.dicoding.emodiary.databinding.ItemArticleBinding
import com.dicoding.emodiary.databinding.ItemDiaryBinding
import com.dicoding.emodiary.ui.view.DetailOrAddOrEditDiaryActivity
import com.dicoding.emodiary.ui.view.WebViewActivity
import com.dicoding.emodiary.utils.withDateFormat

class ArticleListAdapter :
    ListAdapter<ArticleItem, ArticleListAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemArticleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null) {
            Log.d("PAGING : adapter :", data.toString())
            holder.bind(data)
        }
    }

    class MyViewHolder(private val binding: ItemArticleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: ArticleItem) {
            binding.tvArticleTitle.text = data.title
            binding.tvArticleDesc.text = data.snippet

            itemView.setOnClickListener {
                val moveWithObjectIntent = Intent(itemView.context, WebViewActivity::class.java)
                moveWithObjectIntent.putExtra(WebViewActivity.EXTRA_URL, data.link)
                itemView.context.startActivity(moveWithObjectIntent)
            }

        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ArticleItem>() {
            override fun areItemsTheSame(oldItem: ArticleItem, newItem: ArticleItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: ArticleItem,
                newItem: ArticleItem
            ): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}
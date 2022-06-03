package com.dicoding.storyapp.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.emodiary.data.remote.response.DiaryItem
import com.dicoding.emodiary.databinding.ItemDiaryBinding
import com.dicoding.emodiary.utils.withDateFormat

class DiaryListAdapter :
    ListAdapter<DiaryItem, DiaryListAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemDiaryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null) {
            Log.d("PAGING : adapter :", data.toString())
            holder.bind(data)
        }
    }

    class MyViewHolder(private val binding: ItemDiaryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: DiaryItem) {
            binding.tvDateDiary.text = data.timeCreated?.withDateFormat()
            binding.tvDiaryTitle.text = data.title
            binding.tvDiaryDesc.text = data.content
//            itemView.setOnClickListener {
//
//                val moveWithObjectIntent = Intent(itemView.context, DetailActivity::class.java)
//                moveWithObjectIntent.putExtra(DetailActivity.EXTRA_PERSON, data)
//                itemView.context.startActivity(moveWithObjectIntent)
//            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DiaryItem>() {
            override fun areItemsTheSame(oldItem: DiaryItem, newItem: DiaryItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: DiaryItem,
                newItem: DiaryItem
            ): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}
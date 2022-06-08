package com.dicoding.emodiary.utils

import com.dicoding.emodiary.data.remote.response.DiaryItem

object DataDummy {
        fun generateDummyDiaryResponse(): List<DiaryItem> {
            val items: MutableList<DiaryItem> = arrayListOf()
            for (i in 0..10) {
                val story = DiaryItem(
                    id = i.toString(),
                    title = "Hari Ke-$i",
                    content = "Hari ini aku senang sekali karena bertemu dengan teman baru di kampus. Teman ku bernama Sinta, Jono dan Gono...",
                    timeCreated = getDateNow().toString().withDateFormat(),

                )
                items.add(story)
            }
            return items
        }
}
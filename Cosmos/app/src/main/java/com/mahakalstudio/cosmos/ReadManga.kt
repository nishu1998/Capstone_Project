package com.mahakalstudio.cosmos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.mahakalstudio.cosmos.databinding.ActivityReadMangaBinding

class ReadManga : AppCompatActivity() {
    private lateinit var binding: ActivityReadMangaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReadMangaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val title = intent.getStringExtra("MANGA_TITLE")
        val thumb = intent.getStringExtra("MANGA_THUMB")
        val chapters = intent.getIntExtra("MANGA_CHAPTERS", 0)
        val summary = intent.getStringExtra("MANGA_SUMMARY")

        binding.titleTextView.text = title
        binding.chapterTextView.text = "Chapters: $chapters"
        binding.summaryTextView.text = summary

        Glide.with(this).load(thumb).into(binding.imageView2)
    }
}
package com.mahakalstudio.cosmos

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mahakalstudio.cosmos.databinding.ActivityReadMangaBinding

class ReadManga : AppCompatActivity() {
    private lateinit var binding: ActivityReadMangaBinding
    private val sharedPreferences by lazy {
        getSharedPreferences("fav_manga", Context.MODE_PRIVATE)
    }

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

        binding.BookMarkBtn.setOnClickListener {
            saveManga(BookmarkedManga(thumb!!, title!!))
        }

        binding.floatingActionButton.setOnClickListener() {
            finish()

        }
    }

    private fun saveManga(manga: BookmarkedManga) {
        val gson = Gson()
        val mangaListType = object : TypeToken<MutableList<BookmarkedManga>>() {}.type
        val savedMangaListJson = sharedPreferences.getString("saved_manga_list", null)
        val mangaList: MutableList<BookmarkedManga> = if (savedMangaListJson != null) {
            gson.fromJson(savedMangaListJson, mangaListType)
        } else {
            mutableListOf()
        }

        mangaList.add(manga)
        val editor = sharedPreferences.edit()
        editor.putString("saved_manga_list", gson.toJson(mangaList))
        editor.apply()

        Toast.makeText(this, "Manga saved to favorites", Toast.LENGTH_SHORT).show()
    }
}

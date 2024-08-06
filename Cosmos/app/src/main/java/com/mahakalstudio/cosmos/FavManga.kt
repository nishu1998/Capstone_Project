package com.mahakalstudio.cosmos

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mahakalstudio.cosmos.databinding.ActivityFavMangaBinding
import com.mahakalstudio.cosmos.utils.applyBackgroundSetting

class FavManga : AppCompatActivity() {
    private lateinit var binding: ActivityFavMangaBinding
    private val sharedPreferences by lazy {
        getSharedPreferences("fav_manga", Context.MODE_PRIVATE)
    }
    private lateinit var adapter: MangaAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavMangaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        applyBackgroundSetting(this)

        // Setup RecyclerView
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = MangaAdapter(loadSavedManga())
        binding.recyclerView.adapter = adapter

        // Setup click listeners for the buttons
        setupClick(binding.homeButton, MainActivity::class.java)
        setupClick(binding.messagesButton, Wallpaper::class.java)
        setupClick(binding.userButton, Favourites::class.java)
        setupClick(binding.settingsButton, Setting::class.java)
    }

    private fun setupClick(button: View, activityClass: Class<*>) {
        button.setOnClickListener {
            startActivity(Intent(this, activityClass))
        }
    }

    private fun loadSavedManga(): List<Manga> {
        val gson = Gson()
        val mangaListType = object : TypeToken<List<Manga>>() {}.type
        val savedMangaListJson = sharedPreferences.getString("saved_manga_list", null)
        return if (savedMangaListJson != null) {
            gson.fromJson(savedMangaListJson, mangaListType)
        } else {
            emptyList()
        }
    }
}

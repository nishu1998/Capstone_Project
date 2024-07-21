package com.mahakalstudio.cosmos

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.mahakalstudio.cosmos.databinding.ActivityReadWalpaperBinding

class ReadWalpaper : AppCompatActivity() {

    private lateinit var binding: ActivityReadWalpaperBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReadWalpaperBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val wallpaperUrl = intent.getStringExtra("WALLPAPER_URL")
        Log.d("ReadWallpaper", "Wallpaper URL: $wallpaperUrl")

        if (wallpaperUrl != null) {
            Glide.with(this)
                .load(wallpaperUrl)
                .placeholder(R.drawable.loading3)
                .error(R.drawable.errorimage)
                .into(binding.fullScreenImageView)
        } else {
            Log.e("ReadWallpaper", "Wallpaper URL is null")
        }

        binding.floatingButton1.setOnClickListener {
            if (wallpaperUrl != null) {
                PreferenceManager.saveFavorite(this, wallpaperUrl)
                Toast.makeText(this, "Wallpaper Added To Favorite", Toast.LENGTH_SHORT).show()
            }
        }

        binding.floatingButton2.setOnClickListener {
            // Handle button 2 click
        }
    }
}

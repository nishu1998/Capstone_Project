//LatestManga.kt

package com.mahakalstudio.cosmos

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.View
import com.mahakalstudio.cosmos.databinding.ActivityLatestMangaBinding


class LatestManga : AppCompatActivity() {

    private lateinit var binding: ActivityLatestMangaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLatestMangaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setup click listeners for the buttons
        setupClick(binding.homeButton, MainActivity::class.java)
        setupClick(binding.messagesButton, Wallpaper::class.java)
        setupClick(binding.userButton, Favourites::class.java)
        setupClick(binding.settingsButton, Settings::class.java)




    }

    private fun setupClick(button: View, activityClass: Class<*>) {
        button.setOnClickListener {
            // Start the corresponding activity
            startActivity(Intent(this, activityClass))

            // Finish this activity if needed
            if (activityClass != Wallpaper::class.java) {
                finish()
            }
        }
    }
}
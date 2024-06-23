package com.mahakalstudio.cosmos

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.mahakalstudio.cosmos.databinding.ActivityWallpaperBinding

class Wallpaper : AppCompatActivity() {

    private lateinit var binding: ActivityWallpaperBinding
    private lateinit var itemList: List<Pair<Item, Item>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWallpaperBinding.inflate(layoutInflater)
        setContentView(binding.root)

       binding.customButton.setOnClickListener {
           val intent = Intent(this, PopularWallpaper::class.java)
           startActivity(intent)
       }

        // Set the wallpaper button in the after-click state
        binding.messagesTooltip.visibility = View.VISIBLE
        ViewCompat.animate(binding.messagesTooltip).scaleX(1f).scaleY(1f).setDuration(300).start()

        // Setup click listeners for the buttons
        setupClick(binding.homeButton, MainActivity::class.java)
        setupClick(binding.messagesButton, Wallpaper::class.java)
        setupClick(binding.userButton, Favourites::class.java)
        setupClick(binding.settingsButton, Setting::class.java)



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
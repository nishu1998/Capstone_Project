//Favourites.kt

package com.mahakalstudio.cosmos

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.view.ViewCompat
import com.mahakalstudio.cosmos.databinding.ActivityFavouritesBinding
import com.mahakalstudio.cosmos.databinding.ActivityMainBinding

class Favourites : AppCompatActivity() {

    lateinit var binding: ActivityFavouritesBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavouritesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set the favourite button in the after-click state
        binding.userTooltip.visibility = View.VISIBLE
        ViewCompat.animate(binding.userTooltip).scaleX(1f).scaleY(1f).setDuration(300).start()

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
            if (activityClass != Favourites::class.java) {
                finish()
            }
        }
    }
}
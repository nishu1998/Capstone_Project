//Setting.kt

package com.mahakalstudio.cosmos

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.view.ViewCompat
import com.mahakalstudio.cosmos.databinding.ActivitySettingBinding

class Setting : AppCompatActivity() {
    private lateinit var binding: ActivitySettingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Set the setting button in the after-click state
        binding.settingsTooltip.visibility = View.VISIBLE
        ViewCompat.animate(binding.settingsTooltip).scaleX(1f).scaleY(1f).setDuration(300).start()

        // Setup click listeners for the buttons
        setupClick(binding.homeButton, binding.homeTooltip, MainActivity::class.java)
        setupClick(binding.messagesButton, binding.messagesTooltip, Wallpaper::class.java)
        setupClick(binding.userButton, binding.userTooltip, Favourites::class.java)
    }

    private fun setupClick(button: View, tooltip: View, activityClass: Class<*>) {
        button.setOnClickListener {
            // Start the corresponding activity
            startActivity(Intent(this, activityClass))

            // Finish this activity if needed
            if (activityClass != MainActivity::class.java) {
                finish()
            }
        }

    }
}

package com.mahakalstudio.cosmos

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import com.mahakalstudio.cosmos.databinding.ActivitySettingBinding

class Setting : AppCompatActivity() {
    private lateinit var binding: ActivitySettingBinding
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Load the theme from SharedPreferences
        sharedPreferences = getSharedPreferences("settings", MODE_PRIVATE)
        if (sharedPreferences.getBoolean("dark_mode", false)) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set the initial state of the switch
        binding.switchDarkMode.isChecked = sharedPreferences.getBoolean("dark_mode", false)

        // Set the setting button in the after-click state
        binding.settingsTooltip.visibility = View.VISIBLE
        ViewCompat.animate(binding.settingsTooltip).scaleX(1f).scaleY(1f).setDuration(300).start()

        // Setup click listeners for the buttons
        setupClick(binding.homeButton, binding.homeTooltip, MainActivity::class.java)
        setupClick(binding.messagesButton, binding.messagesTooltip, Wallpaper::class.java)
        setupClick(binding.userButton, binding.userTooltip, Favourites::class.java)

        // Setup listener for the switch
        binding.switchDarkMode.setOnCheckedChangeListener { _, isChecked ->
            sharedPreferences.edit().putBoolean("dark_mode", isChecked).apply()
            restartActivity()
        }
    }

    private fun restartActivity() {
        Handler(Looper.getMainLooper()).post {
            val intent = Intent(this, Setting::class.java)
            startActivity(intent)
            finish()
        }
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

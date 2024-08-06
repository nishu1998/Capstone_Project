package com.mahakalstudio.cosmos

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import com.mahakalstudio.cosmos.databinding.ActivitySettingBinding
import com.mahakalstudio.cosmos.utils.applyBackgroundSetting

class Setting : AppCompatActivity() {
    private lateinit var binding: ActivitySettingBinding
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        applyBackgroundSetting(this)

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

        // Setup click listeners for the navigation buttons
        setupClick(binding.homeButton, binding.homeTooltip, MainActivity::class.java)
        setupClick(binding.messagesButton, binding.messagesTooltip, Wallpaper::class.java)
        setupClick(binding.userButton, binding.userTooltip, Favourites::class.java)

        // Setup listener for the switch
        binding.switchDarkMode.setOnCheckedChangeListener { _, isChecked ->
            sharedPreferences.edit().putBoolean("dark_mode", isChecked).apply()
            restartActivity()
        }

        // Setup listeners for the switch buttons
        setupSwitchButtons()
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

    private fun setupSwitchButtons() {
        val switchButtons = listOf(
            binding.switchButton1,
            binding.switchButton2,
            binding.switchButton3
        )

        switchButtons.forEachIndexed { index, switch ->
            switch.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    activateSwitch(index, switchButtons)
                } else {
                    deactivateSwitch(index)
                }
            }
        }
    }

    private fun activateSwitch(index: Int, switches: List<Switch>) {
        switches.forEachIndexed { i, switch ->
            switch.isChecked = i == index
            if (i == index) {
                // Apply the corresponding functionality for the activated switch
                applyFunctionality(index, true)
            }
        }
    }

    private fun deactivateSwitch(index: Int) {
        // Reset the background to default or remove gradient
        applyFunctionality(index, false)
    }

    private fun applyFunctionality(index: Int, isActive: Boolean) {
        val editor = sharedPreferences.edit()
        if (isActive) {
            when (index) {
                0 -> {
                    // Apply functionality for Switch 1
                    // Set gradient background
                    binding.root.setBackgroundResource(R.drawable.gradient)
                    editor.putInt("background", R.drawable.gradient)
                }
                1 -> {
                    // Apply functionality for Switch 2
                    // Set gradient background
                    binding.root.setBackgroundResource(R.drawable.gradient2)
                    editor.putInt("background", R.drawable.gradient2)
                }
                2 -> {
                    // Apply functionality for Switch 3
                    // Set gradient background
                    binding.root.setBackgroundResource(R.drawable.gradient3)
                    editor.putInt("background", R.drawable.gradient3)
                }
            }
        } else {
            // Reset to default background when switch is turned off
            binding.root.setBackgroundResource(android.R.color.background_light) // Use default background resource
            editor.remove("background")
        }
        editor.apply()
    }
}

package com.mahakalstudio.cosmos

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.mahakalstudio.cosmos.databinding.ActivityMangaCategoryBinding

class MangaCategory : AppCompatActivity() {

    private lateinit var binding: ActivityMangaCategoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMangaCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setup click listeners for the buttons
        binding.A.setOnClickListener {
            hideButtons()
            replaceFragment(FragmentA())
        }
        binding.B.setOnClickListener {
            hideButtons()
            replaceFragment(FragmentB())
        }
        binding.C.setOnClickListener {
            hideButtons()
            replaceFragment(FragmentC())
        }
        binding.D.setOnClickListener {
            hideButtons()
            replaceFragment(FragmentD())
        }
        binding.E.setOnClickListener {
            hideButtons()
            replaceFragment(FragmentE())
        }

        // Setup click listeners for navigation buttons
        setupClick(binding.homeButton, MainActivity::class.java)
        setupClick(binding.messagesButton, Wallpaper::class.java)
        setupClick(binding.userButton, Favourites::class.java)
        setupClick(binding.settingsButton, Setting::class.java)
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(binding.fragmentContainer.id, fragment)
            .commit()
    }

    private fun setupClick(button: View, activityClass: Class<*>) {
        button.setOnClickListener {
            startActivity(Intent(this, activityClass))
            if (activityClass != MangaCategory::class.java) {
                finish()
            }
        }
    }

    private fun hideButtons() {
        binding.A.visibility = View.GONE
        binding.B.visibility = View.GONE
        binding.C.visibility = View.GONE
        binding.D.visibility = View.GONE
        binding.E.visibility = View.GONE
    }
}

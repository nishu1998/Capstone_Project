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
            replaceFragment(FragmentA())
        }
        binding.B.setOnClickListener {
            replaceFragment(FragmentB())
        }
        binding.C.setOnClickListener {
            replaceFragment(FragmentC())
        }
        binding.D.setOnClickListener {
            replaceFragment(FragmentD())
        }
        binding.E.setOnClickListener {
            replaceFragment(FragmentE())
        }
        binding.F.setOnClickListener {
            replaceFragment(FragmentB())
        }
        binding.G.setOnClickListener {
            replaceFragment(FragmentB())
        }

        // Setup click listeners for navigation buttons
        setupClick(binding.homeButton, MainActivity::class.java)
        setupClick(binding.messagesButton, Wallpaper::class.java)
        setupClick(binding.userButton, Favourites::class.java)
        setupClick(binding.settingsButton, Setting::class.java)
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
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
}
package com.mahakalstudio.cosmos

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mahakalstudio.cosmos.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set up the VideoView to play the splash screen video
        val videoPath = "android.resource://" + packageName + "/" + R.raw.splash_screen
        binding.videoView.setVideoURI(Uri.parse(videoPath))
        binding.videoView.start()

        // Listener to move to the MainActivity after the video ends
        binding.videoView.setOnCompletionListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }



    }
}
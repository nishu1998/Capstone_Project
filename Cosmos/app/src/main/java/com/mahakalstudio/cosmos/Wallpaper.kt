package com.mahakalstudio.cosmos

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mahakalstudio.cosmos.databinding.ActivityWallpaperBinding
import com.mahakalstudio.cosmos.utils.applyBackgroundSetting
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.random.Random

class Wallpaper : AppCompatActivity() {

    private lateinit var binding: ActivityWallpaperBinding
    private lateinit var wallpaperAdapter: WallpaperAdapter
    private val wallpaperList = mutableListOf<WallpaperData>()
    private var page: Int = Random.nextInt(1, 21)
    private var isLoading = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWallpaperBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        fetchWallpapers()
        applyBackgroundSetting(this)

        // Set the setting button in the after-click state
        binding.messagesTooltip.visibility = View.VISIBLE
        ViewCompat.animate(binding.messagesTooltip).scaleX(1f).scaleY(1f).setDuration(300).start()

        // Setup click listeners for the buttons
        setupClick(binding.homeButton, MainActivity::class.java)
        setupClick(binding.messagesButton, Wallpaper::class.java)
        setupClick(binding.userButton, Favourites::class.java)
        setupClick(binding.settingsButton, Setting::class.java)

        // Add click listener for the Popular Wallpaper button
        binding.customButton.setOnClickListener {
            startActivity(Intent(this, PopularWallpaper::class.java))
        }
    }

    private fun setupRecyclerView() {
        wallpaperAdapter = WallpaperAdapter(this, wallpaperList)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = wallpaperAdapter

        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (!recyclerView.canScrollVertically(1) && !isLoading) {
                    fetchWallpapers()
                }
            }
        })
    }

    private fun fetchWallpapers() {
        isLoading = true
        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Please wait while data is fetching")
        progressDialog.show()

        WallpaperApiObj.apiInterface.searchPhotos(
            query = "anime wallpaper",
            page = page,
            perPage = 20,
            clientId = WallpaperApiObj.getAccessKey()
        ).enqueue(object : Callback<UnsplashResponse> {
            override fun onResponse(call: Call<UnsplashResponse>, response: Response<UnsplashResponse>) {
                progressDialog.dismiss()
                isLoading = false
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        wallpaperList.addAll(body.results)
                        wallpaperAdapter.notifyDataSetChanged()
                        page++
                    } else {
                        Log.d("Wallpaper", "Response body is null")
                        Toast.makeText(this@Wallpaper, "Response body is null", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Log.d("Wallpaper", "API call failed: ${response.code()} - ${response.message()}")
                    Toast.makeText(this@Wallpaper, "Failed to fetch wallpapers: ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<UnsplashResponse>, t: Throwable) {
                progressDialog.dismiss()
                isLoading = false
                Log.e("Wallpaper", "API call failed", t)
                Toast.makeText(this@Wallpaper, "Failed to fetch wallpapers: ${t.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun setupClick(button: View, activityClass: Class<*>) {
        button.setOnClickListener {
            startActivity(Intent(this, activityClass))
            if (activityClass != Wallpaper::class.java) {
                finish()
            }
        }
    }
}

// PopularWallpaper.kt
package com.mahakalstudio.cosmos

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mahakalstudio.cosmos.databinding.ActivityPopularWallpaperBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PopularWallpaper : AppCompatActivity() {

    private lateinit var binding: ActivityPopularWallpaperBinding
    private lateinit var wallpaperAdapter: WallpaperAdapter
    private val wallpaperList = mutableListOf<WallpaperData>()
    private var page = 1
    private var isLoading = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPopularWallpaperBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        fetchWallpapers()

        // Setup click listeners for the buttons
        setupClick(binding.homeButton, MainActivity::class.java)
        setupClick(binding.messagesButton, Wallpaper::class.java)
        setupClick(binding.userButton, Favourites::class.java)
        setupClick(binding.settingsButton, Setting::class.java)
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
            query = "popular anime wallpaper",
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
                        Log.d("PopularWallpaper", "Response body is null")
                        Toast.makeText(this@PopularWallpaper, "Response body is null", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Log.d("PopularWallpaper", "API call failed: ${response.code()} - ${response.message()}")
                    Toast.makeText(this@PopularWallpaper, "Failed to fetch wallpapers: ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<UnsplashResponse>, t: Throwable) {
                progressDialog.dismiss()
                isLoading = false
                Log.e("PopularWallpaper", "API call failed", t)
                Toast.makeText(this@PopularWallpaper, "Failed to fetch wallpapers: ${t.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun setupClick(button: View, activityClass: Class<*>) {
        button.setOnClickListener {
            startActivity(Intent(this, activityClass))
            if (activityClass != PopularWallpaper::class.java) {
                finish()
            }
        }
    }
}


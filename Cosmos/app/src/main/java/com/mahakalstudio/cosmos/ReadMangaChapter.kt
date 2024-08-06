package com.mahakalstudio.cosmos

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.mahakalstudio.cosmos.databinding.ActivityReadMangaChapterBinding
import com.mahakalstudio.cosmos.utils.applyBackgroundSetting
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONException
import org.json.JSONObject

class ReadMangaChapter : AppCompatActivity() {

    private lateinit var binding: ActivityReadMangaChapterBinding
    private lateinit var handler: Handler
    private lateinit var autoScrollRunnable: Runnable
    private var isAutoScrolling = false
    private lateinit var gestureDetector: GestureDetector

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReadMangaChapterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        applyBackgroundSetting(this)

        val chapterId = intent.getIntExtra("CHAPTER_ID", 0)
        fetchChapterImages(chapterId)

        binding.floatingActionButton.setOnClickListener {
            finish()
        }

        binding.buttonAutoScroll.setOnClickListener {
            if (isAutoScrolling) {
                stopAutoScroll()
            } else {
                startAutoScroll()
            }
        }

        handler = Handler(Looper.getMainLooper())
        autoScrollRunnable = object : Runnable {
            override fun run() {
                val layoutManager = binding.recyclerView.layoutManager as LinearLayoutManager
                val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
                if (lastVisibleItemPosition < binding.recyclerView.adapter!!.itemCount - 1) {
                    binding.recyclerView.smoothScrollToPosition(lastVisibleItemPosition + 1)
                    handler.postDelayed(this, 2200)
                }
            }
        }

        // Initialize GestureDetector
        gestureDetector = GestureDetector(this, object : GestureDetector.SimpleOnGestureListener() {
            override fun onDoubleTap(e: MotionEvent): Boolean {
                handleZoom()
                return true
            }
        })

        // Set touch listener on root layout
        binding.root.setOnTouchListener { _, event ->
            gestureDetector.onTouchEvent(event)
            true
        }
    }

    private fun startAutoScroll() {
        isAutoScrolling = true
        binding.buttonAutoScroll.text = "Stop Auto Scroll"
        handler.post(autoScrollRunnable)
    }

    private fun stopAutoScroll() {
        isAutoScrolling = false
        binding.buttonAutoScroll.text = "Start Auto Scroll"
        handler.removeCallbacks(autoScrollRunnable)
    }

    private fun fetchChapterImages(chapterId: Int) {
        Log.d("ReadMangaChapter", "Fetching images for Chapter ID: $chapterId")

        Thread {
            val client = OkHttpClient()
            val request = Request.Builder()
                .url("https://mangaverse-api.p.rapidapi.com/manga/image?id=659524e9597f3b00281f070d")
                .get()
                .addHeader("x-rapidapi-key", "991217ddf3msh7bcf1357bd7da71p1859f9jsn4cc144f8c41c")
                .addHeader("x-rapidapi-host", "mangaverse-api.p.rapidapi.com")
                .build()

            val response: Response = client.newCall(request).execute()
            val responseData = response.body()?.string()

            Log.d("ReadMangaChapter", "Response Data: $responseData")

            runOnUiThread {
                try {
                    val images = parseImageUrls(responseData)
                    setupRecyclerView(images)
                } catch (e: JSONException) {
                    Log.e("ReadMangaChapter", "Error parsing JSON response: ${e.message}")
                    // Handle the error appropriately
                }
            }
        }.start()
    }

    private fun parseImageUrls(responseData: String?): List<String> {
        if (responseData == null) {
            throw JSONException("Response data is null")
        }

        val jsonResponse = JSONObject(responseData)
        val dataArray = jsonResponse.getJSONArray("data")
        val imageUrls = mutableListOf<String>()

        for (i in 0 until dataArray.length()) {
            val imageObject = dataArray.getJSONObject(i)
            val imageUrl = imageObject.getString("link")
            imageUrls.add(imageUrl)
        }

        return imageUrls
    }

    private fun setupRecyclerView(images: List<String>) {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = ImageAdapter(images)
    }

    private fun handleZoom() {
        // Implement your zoom logic here
        Log.d("ReadMangaChapter", "Double-tap detected, handle zoom")
    }
}
package com.mahakalstudio.cosmos

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.mahakalstudio.cosmos.databinding.ActivityReadMangaChapterBinding
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONException
import org.json.JSONObject

class ReadMangaChapter : AppCompatActivity() {

    private lateinit var binding: ActivityReadMangaChapterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReadMangaChapterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val chapterId = intent.getIntExtra("CHAPTER_ID", 0)
        fetchChapterImages(chapterId)

        binding.floatingActionButton.setOnClickListener(){
            finish()
        }

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
}

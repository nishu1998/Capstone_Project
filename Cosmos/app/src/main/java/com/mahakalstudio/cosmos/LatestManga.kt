package com.mahakalstudio.cosmos

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.mahakalstudio.cosmos.databinding.ActivityLatestMangaBinding
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LatestManga : AppCompatActivity() {

    private lateinit var binding: ActivityLatestMangaBinding
    private lateinit var adapter: ItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLatestMangaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setup RecyclerView
        adapter = ItemAdapter(emptyList())
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        // Setup click listeners for the buttons
        setupClick(binding.homeButton, MainActivity::class.java)
        setupClick(binding.messagesButton, Wallpaper::class.java)
        setupClick(binding.userButton, Favourites::class.java)
        setupClick(binding.settingsButton, Settings::class.java)

        // Fetch the latest manga from the API
        fetchLatestManga()
    }

    private fun setupClick(button: View, activityClass: Class<*>) {
        button.setOnClickListener {
            // Start the corresponding activity
            startActivity(Intent(this, activityClass))

            // Finish this activity if needed
            if (activityClass != Wallpaper::class.java) {
                finish()
            }
        }
    }

    private fun fetchLatestManga() {
        val apiKey = "991217ddf3msh7bcf1357bd7da71p1859f9jsn4cc144f8c41c"

        val client = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val original: Request = chain.request()
                val request: Request = original.newBuilder()
                    .header("x-rapidapi-key", apiKey)
                    .header("x-rapidapi-host", "mangaverse-api.p.rapidapi.com")
                    .method(original.method(), original.body())
                    .build()
                chain.proceed(request)
            }
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://mangaverse-api.p.rapidapi.com/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api = retrofit.create(MangaApiInterface::class.java)

        val call = api.getLatestManga(page = 1, genres = "Harem,Fantasy", nsfw = true, type = "all")

        call.enqueue(object : Callback<MangaResponseDataClass> {
            override fun onResponse(call: Call<MangaResponseDataClass>, response: Response<MangaResponseDataClass>) {
                if (response.isSuccessful) {
                    val mangaList = response.body()?.data ?: emptyList()
                    Log.d("LatestManga", "Fetched ${mangaList.size} mangas")
                    adapter.updateData(mangaList)
                } else {
                    Log.e("LatestManga", "Failed to fetch data: ${response.code()}")
                    val errorBody = response.errorBody()?.string()
                    if (errorBody != null) {
                        Log.e("LatestManga", "Error body: $errorBody")
                    }
                }
            }

            override fun onFailure(call: Call<MangaResponseDataClass>, t: Throwable) {
                t.printStackTrace()
                Log.e("LatestManga", "Failed to fetch data: ${t.message}")
            }
        })
    }
}

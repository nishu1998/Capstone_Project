package com.mahakalstudio.cosmos

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mahakalstudio.cosmos.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var itemAdapter: ItemAdapter
    private val mangaList = mutableListOf<Manga>()
    private var page = 1
    private var isLoading = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize RecyclerView adapter with an empty list
        itemAdapter = ItemAdapter(mangaList)

        // Set up RecyclerView with GridLayoutManager for 2 columns
        binding.recyclerView.layoutManager = GridLayoutManager(this, 2)
        binding.recyclerView.adapter = itemAdapter

        // Set scroll listener
        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (!recyclerView.canScrollVertically(1) && !isLoading) {
                    // Reached the end of the list
                    fetchManga("Harem,Fantasy", false, "all")
                }
            }
        })

        // Fetch initial manga data from API
        fetchManga("Harem,Fantasy,Action", false, "all")

        // Set up click listeners for buttons
        binding.customButton.setOnClickListener {
            startActivity(Intent(this, LatestManga::class.java))
        }

        binding.button2.setOnClickListener {
            startActivity(Intent(this, MangaCategory::class.java))
        }

        // Set up home button visibility and animation
        binding.homeTooltip.visibility = View.VISIBLE
        ViewCompat.animate(binding.homeTooltip).scaleX(1f).scaleY(1f).setDuration(300).start()

        // Set up click listeners for other buttons
        setupClick(binding.homeButton, MainActivity::class.java)
        setupClick(binding.messagesButton, Wallpaper::class.java)
        setupClick(binding.userButton, Favourites::class.java)
        setupClick(binding.settingsButton, Setting::class.java)

        // Set up search view listener
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                // Perform search or API call here
                filterManga(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                // Filter search results as the user types
                filterManga(newText)
                return true
            }
        })

        // Set up genre search
        binding.genreSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                // Perform genre search
                filterByGenre(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                // Update genre search as user types
                filterByGenre(newText)
                return true
            }
        })
    }

    private fun setupClick(button: View, activityClass: Class<*>) {
        button.setOnClickListener {
            startActivity(Intent(this, activityClass))
            if (activityClass != MainActivity::class.java) {
                finish()
            }
        }
    }

    private fun fetchManga(genres: String, nsfw: Boolean, type: String) {
        isLoading = true
        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Please wait while data is fetching")
        progressDialog.show()

        val apiInterface = MangaApiObj.apiInterface

        // Function to fetch next page
        apiInterface.getData(page, genres, nsfw, type).enqueue(object : Callback<MangaResponseDataClass> {
            override fun onResponse(call: Call<MangaResponseDataClass>, response: Response<MangaResponseDataClass>) {
                progressDialog.dismiss()
                isLoading = false
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        val fetchedManga = body.data
                        mangaList.addAll(fetchedManga)
                        itemAdapter.updateData(mangaList)
                        // Check if there are more results to fetch
                        if (!fetchedManga.isNullOrEmpty()) {
                            // Increment page number for next request
                            page++
                        }
                    } else {
                        Log.d("Main", "Response body is null")
                        Toast.makeText(this@MainActivity, "Response body is null", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Log.d("Main", "API call failed: ${response.code()} - ${response.message()}")
                    Toast.makeText(this@MainActivity, "Failed to fetch manga", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<MangaResponseDataClass>, t: Throwable) {
                progressDialog.dismiss()
                isLoading = false
                Log.e("Main", "API call failed", t)
                Toast.makeText(this@MainActivity, "Failed to fetch manga: ${t.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun filterManga(query: String) {
        itemAdapter.filterList(query, binding.genreSearchView.query.toString())
    }

    private fun filterByGenre(genreQuery: String) {
        itemAdapter.filterList(binding.searchView.query.toString(), genreQuery)
    }
}
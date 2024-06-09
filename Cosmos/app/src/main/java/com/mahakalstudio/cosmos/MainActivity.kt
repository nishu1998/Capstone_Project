package com.mahakalstudio.cosmos

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.view.ViewCompat
import com.mahakalstudio.cosmos.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager


class MainActivity : AppCompatActivity() {
    //API URL - https://mangaverse-api.p.rapidapi.com/manga

    lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Fetch manga with a specific ID
        //fetchManga("659524dd597f3b00281f06ff")

        binding.customButton.setOnClickListener {
            val intent = Intent(this, LatestManga::class.java)
            startActivity(intent)
        }

        binding.button2.setOnClickListener {
            val intent = Intent(this, MangaCategory::class.java)
            startActivity(intent)
        }

        // Set the home button in the after-click state
        binding.homeTooltip.visibility = View.VISIBLE
        ViewCompat.animate(binding.homeTooltip).scaleX(1f).scaleY(1f).setDuration(300).start()

        // Setup click listeners for the buttons
        setupClick(binding.homeButton, MainActivity::class.java)
        setupClick(binding.messagesButton, Wallpaper::class.java)
        setupClick(binding.userButton, Favourites::class.java)
        setupClick(binding.settingsButton, Setting::class.java)


        val searchView = binding.searchView
        searchView.setOnQueryTextListener(object :android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                // Perform search or API call here
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                // Filter search results as the user types
                return true
            }
        })

        // Sample data for RecyclerView
        val itemList = listOf(
            Pair(Item("Item 1", R.drawable.image1), Item("Item 2", R.drawable.image2)),
            Pair(Item("Item 3", R.drawable.image3), Item("Item 4", R.drawable.image2)),
            Pair(Item("Item 1", R.drawable.image1), Item("Item 2", R.drawable.image2)),
            Pair(Item("Item 3", R.drawable.image3), Item("Item 4", R.drawable.image2))
        )

        // Set up RecyclerView (vertical)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = ItemAdapter(itemList)

        }
    private fun setupClick(button: View, activityClass: Class<*>) {
        button.setOnClickListener {
            // Start the corresponding activity
            startActivity(Intent(this, activityClass))

            // Finish this activity if needed
            if (activityClass != MainActivity::class.java) {
                finish()

            }
        }
    }
}



   /* private fun fetchManga(id:String) {

       val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Please wait while data is fetching")
        progressDialog.show()

        mangaApiObj.apiInterface.getData(id).enqueue(object : Callback<mangaResponceDataClass?> {
            override fun onResponse(
                call: Call<mangaResponceDataClass?>,
                response: Response<mangaResponceDataClass?>
            ) {
                progressDialog.dismiss()
                if (response.isSuccessful) {
                    // Log success message
                    Log.d("Main", "API call successful: ${response.body()}")
                    binding.summaryTextView.text = response.body()?.summary

                } else {
                    // Log response code and message if not successful
                    Log.d("Main", "API call failed: ${response.code()} - ${response.message()}")
                    binding.summaryTextView.text = response.body()?.summary


                }
            }

            override fun onFailure(call: Call<mangaResponceDataClass?>, t: Throwable) {
                Toast.makeText(this@MainActivity,"${t.localizedMessage}",Toast.LENGTH_SHORT)
                    .show()
                Log.d("Main", "fail")
                progressDialog.dismiss()

            }
        })
    }


}*/
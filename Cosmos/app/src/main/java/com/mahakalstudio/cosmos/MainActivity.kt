package com.mahakalstudio.cosmos

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.mahakalstudio.cosmos.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    //API URL - https://mangaverse-api.p.rapidapi.com/manga

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getmanga()

        binding.nextButton.setOnClickListener {
            getmanga()
        }
    }

    private fun getmanga() {


       val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Please wait while data is fetching")
        progressDialog.show()

        mangaApiObj.apiInterface.getData().enqueue(object : Callback<mangaResponceDataClass?> {
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


}
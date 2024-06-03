package com.mahakalstudio.cosmos

import android.util.Log
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object mangaApiObj {
    private const val API_KEY = "991217ddf3msh7bcf1357bd7da71p1859f9jsn4cc144f8c41c"
    private val retrofit by lazy {
        Retrofit.Builder().baseUrl("https://mangaverse-api.p.rapidapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(createOkHttpClient())
            .build()
    }

    val apiInterface by lazy {
        retrofit.create(mangaApiInterface::class.java)
    }


    private fun createOkHttpClient(): OkHttpClient {
        val interceptor = Interceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader("X-RapidAPI-Key", API_KEY)
                .addHeader("X-RapidAPI-Host", "mangaverse-api.p.rapidapi.com")
                .build()
            Log.d("Request", "URL: ${request.url()}")
            Log.d("Request", "Headers: ${request.headers()}")
            chain.proceed(request)
        }

        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
    }
}
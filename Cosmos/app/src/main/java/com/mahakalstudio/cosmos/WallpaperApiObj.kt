package com.mahakalstudio.cosmos

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object WallpaperApiObj {
    private const val BASE_URL = "https://api.unsplash.com/"
    private const val ACCESS_KEY = "aclC1kam3P2u-QAHWNMQXgJvsy5WdLeB9Axqm52xUMg"

    private val client = OkHttpClient.Builder().build()

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    val apiInterface: WallpaperApiInterface by lazy {
        retrofit.create(WallpaperApiInterface::class.java)
    }

    fun getAccessKey() = ACCESS_KEY
}

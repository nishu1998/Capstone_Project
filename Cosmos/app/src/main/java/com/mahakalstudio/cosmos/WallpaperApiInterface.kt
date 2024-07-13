package com.mahakalstudio.cosmos

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WallpaperApiInterface {
    @GET("search/photos")
    fun searchPhotos(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
        @Query("client_id") clientId: String
    ): Call<UnsplashResponse>
}

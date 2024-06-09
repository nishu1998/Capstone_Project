//MangaApiInterface.kt

package com.mahakalstudio.cosmos

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface mangaApiInterface {

    @GET("manga")
    fun getData(@Query("id") id: String):Call<mangaResponceDataClass>

    @GET("manga")
    fun getData(): Call<mangaResponceDataClass>

}
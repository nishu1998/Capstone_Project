package com.mahakalstudio.cosmos

import retrofit2.Call
import retrofit2.http.GET

interface mangaApiInterface {

    @GET("manga")
    fun getData():Call<mangaResponceDataClass>

}
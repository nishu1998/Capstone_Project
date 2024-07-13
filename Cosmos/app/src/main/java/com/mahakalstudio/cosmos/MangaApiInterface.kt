package com.mahakalstudio.cosmos

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MangaApiInterface {

    @GET("manga/fetch")
    fun getData( @Query("page") page: Int,
                 @Query("genres") genres: String,
                 @Query("nsfw") nsfw: Boolean,
                 @Query("type") type: String
    ):Call<MangaResponseDataClass>

    @GET("manga/latest")
    fun getLatestManga(
        @Query("page") page: Int,
        @Query("genres") genres: String,
        @Query("nsfw") nsfw: Boolean,
        @Query("type") type: String
    ): Call<MangaResponseDataClass>
}
package com.mahakalstudio.cosmos

data class UnsplashResponse(
    val results: List<WallpaperData>
)

data class WallpaperData(
    val id: String,
    val urls: Urls,
    val description: String?
)

data class Urls(
    val small: String,
    val full: String
)

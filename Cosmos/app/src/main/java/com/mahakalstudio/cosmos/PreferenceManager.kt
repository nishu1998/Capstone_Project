package com.mahakalstudio.cosmos

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object PreferenceManager {
    private const val PREF_NAME = "favorites_pref"
    private const val FAVORITES_KEY = "favorites_key"

    private fun getPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun saveFavorite(context: Context, url: String) {
        val favorites = getFavorites(context).toMutableList()
        favorites.add(url)
        val json = Gson().toJson(favorites)
        getPreferences(context).edit().putString(FAVORITES_KEY, json).apply()
    }

    fun getFavorites(context: Context): List<String> {
        val json = getPreferences(context).getString(FAVORITES_KEY, null)
        return if (json != null) {
            Gson().fromJson(json, object : TypeToken<List<String>>() {}.type)
        } else {
            emptyList()
        }
    }
}

package com.mahakalstudio.cosmos.utils

import android.app.Activity
import android.content.Context
import com.mahakalstudio.cosmos.R

fun applyBackgroundSetting(activity: Activity) {
    val sharedPreferences = activity.getSharedPreferences("settings", Context.MODE_PRIVATE)
    val backgroundResId = sharedPreferences.getInt("background", R.color.lightModeBackground) // Use your default background
    activity.window.decorView.setBackgroundResource(backgroundResId)
}

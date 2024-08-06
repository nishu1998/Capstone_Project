package com.mahakalstudio.cosmos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mahakalstudio.cosmos.utils.applyBackgroundSetting

class ReadingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reading)

        applyBackgroundSetting(this)
    }
}
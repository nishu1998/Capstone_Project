package com.mahakalstudio.cosmos

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.mahakalstudio.cosmos.databinding.ActivityReadChaptersBinding
import com.mahakalstudio.cosmos.utils.applyBackgroundSetting

class ReadChapters : AppCompatActivity() {

    private lateinit var binding: ActivityReadChaptersBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReadChaptersBinding.inflate(layoutInflater)
        setContentView(binding.root)

        applyBackgroundSetting(this)

        val title = intent.getStringExtra("MANGA_TITLE")
        val chapters = intent.getIntExtra("MANGA_CHAPTERS", 0)

        binding.textView.text = title

        binding.back.setOnClickListener {
            finish()
        }


        val chapterList = (1..chapters).map { Chapter(it, "Chapter $it") }

        binding.recyclerView.adapter = ChapterAdapter(chapterList) { chapterId ->
            val intent = Intent(this, ReadMangaChapter::class.java).apply {
                putExtra("CHAPTER_ID", chapterId)
            }
            startActivity(intent)
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
    }
}

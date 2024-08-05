package com.mahakalstudio.cosmos

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ChapterAdapter(
    private val chapters: List<Chapter>,
    private val onItemClick: (Int) -> Unit
) : RecyclerView.Adapter<ChapterAdapter.ChapterViewHolder>() {

    inner class ChapterViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val chapterTextView: TextView = view.findViewById(R.id.chapterTextView)

        init {
            view.setOnClickListener {
                onItemClick(chapters[adapterPosition].id)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChapterViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_chapter, parent, false)
        return ChapterViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChapterViewHolder, position: Int) {
        holder.chapterTextView.text = chapters[position].title
    }

    override fun getItemCount(): Int = chapters.size
}



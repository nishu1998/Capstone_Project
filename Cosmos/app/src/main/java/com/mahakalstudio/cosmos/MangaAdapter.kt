package com.mahakalstudio.cosmos

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mahakalstudio.cosmos.databinding.ItemLayoutBinding

class MangaAdapter(private val mangaList: List<Manga>) : RecyclerView.Adapter<MangaAdapter.MangaViewHolder>() {

    inner class MangaViewHolder(private val binding: ItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(manga: Manga) {
            binding.itemText1.text = manga.title
            Glide.with(binding.itemImage1.context).load(manga.thumb).into(binding.itemImage1)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MangaViewHolder {
        val binding = ItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MangaViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MangaViewHolder, position: Int) {
        holder.bind(mangaList[position])
    }

    override fun getItemCount(): Int = mangaList.size
}

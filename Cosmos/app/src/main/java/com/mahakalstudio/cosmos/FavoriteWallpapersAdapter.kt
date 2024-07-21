package com.mahakalstudio.cosmos

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mahakalstudio.cosmos.databinding.ItemFavoriteWallpaperBinding

class FavoriteWallpapersAdapter(private val wallpapers: List<String>) :
    RecyclerView.Adapter<FavoriteWallpapersAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemFavoriteWallpaperBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(wallpaperUrl: String) {
            Glide.with(binding.root.context)
                .load(wallpaperUrl)
                .placeholder(R.drawable.loading3)
                .error(R.drawable.errorimage)
                .into(binding.imageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemFavoriteWallpaperBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(wallpapers[position])
    }

    override fun getItemCount(): Int {
        return wallpapers.size
    }
}

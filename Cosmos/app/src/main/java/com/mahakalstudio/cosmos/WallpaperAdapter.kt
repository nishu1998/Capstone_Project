package com.mahakalstudio.cosmos

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mahakalstudio.cosmos.databinding.ItemWallpaperBinding

class WallpaperAdapter(
    private val context: Context,
    private val wallpaperList: List<WallpaperData>
) : RecyclerView.Adapter<WallpaperAdapter.WallpaperViewHolder>() {

    inner class WallpaperViewHolder(val binding: ItemWallpaperBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WallpaperViewHolder {
        val binding = ItemWallpaperBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WallpaperViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WallpaperViewHolder, position: Int) {
        val wallpaper = wallpaperList[position]
        Glide.with(context).load(wallpaper.urls.small).into(holder.binding.wallpaperImageView)

        holder.binding.wallpaperImageView.setOnClickListener {
            // Navigate to ReadWallpaper activity with the wallpaper URL
            val intent = Intent(context, ReadWalpaper::class.java)
            intent.putExtra("WALLPAPER_URL", wallpaper.urls.full)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return wallpaperList.size
    }
}

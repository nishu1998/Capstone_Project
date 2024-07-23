package com.mahakalstudio.cosmos

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mahakalstudio.cosmos.R

class FavoriteWallpapersAdapter(private val wallpapers: List<String>) :
    RecyclerView.Adapter<FavoriteWallpapersAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.imageView)

        fun bind(wallpaperUrl: String) {
            Glide.with(itemView.context)
                .load(wallpaperUrl)
                .placeholder(R.drawable.loading3)
                .error(R.drawable.errorimage)
                .into(imageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_favorite_wallpaper, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(wallpapers[position])
    }

    override fun getItemCount(): Int {
        return wallpapers.size
    }
}

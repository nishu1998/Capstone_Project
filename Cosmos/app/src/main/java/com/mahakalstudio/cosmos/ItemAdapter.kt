package com.mahakalstudio.cosmos

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mahakalstudio.cosmos.databinding.ItemLayoutBinding

class ItemAdapter(private var itemList: List<Manga>) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(private val binding: ItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(manga: Manga) {
            Glide.with(binding.itemImage1.context).load(manga.thumb).into(binding.itemImage1)
            binding.itemText1.text = manga.title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(itemList[position])
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    // Function to update adapter's dataset
    fun updateData(newItemList: List<Manga>) {
        itemList = newItemList
        notifyDataSetChanged()
    }
}

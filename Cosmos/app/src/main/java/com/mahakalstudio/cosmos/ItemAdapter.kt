//ItemAdapter.kt

package com.mahakalstudio.cosmos

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mahakalstudio.cosmos.databinding.ItemLayoutBinding

class ItemAdapter(private val itemList: List<Pair<Item, Item>>) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(private val binding: ItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(itemPair: Pair<Item, Item>) {
            val (item1, item2) = itemPair
            binding.itemImage1.setImageResource(item1.imageResId)
            binding.itemText1.text = item1.name
            binding.itemImage2.setImageResource(item2.imageResId)
            binding.itemText2.text = item2.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(itemList[position])
    }

    override fun getItemCount(): Int = itemList.size
}

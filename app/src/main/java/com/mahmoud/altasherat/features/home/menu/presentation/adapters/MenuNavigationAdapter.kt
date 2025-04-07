package com.mahmoud.altasherat.features.home.menu.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mahmoud.altasherat.databinding.ItemMenuDestinationBinding
import com.mahmoud.altasherat.features.home.menu.data.models.NavigationItem

class MenuNavigationAdapter(private val onItemClick: (NavigationItem) -> Unit) :
    ListAdapter<NavigationItem, MenuNavigationAdapter.MenuItemViewHolder>(
        ProfileDestinationDiffCallback()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuItemViewHolder {
        val binding = ItemMenuDestinationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MenuItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MenuItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class MenuItemViewHolder(private val binding: ItemMenuDestinationBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(destination: NavigationItem) {
            binding.destinationTitle.text = destination.title
            binding.destinationIcon.setImageResource(destination.iconRes)

            binding.root.setOnClickListener { onItemClick(destination) }
        }
    }

    class ProfileDestinationDiffCallback : DiffUtil.ItemCallback<NavigationItem>() {
        override fun areItemsTheSame(oldItem: NavigationItem, newItem: NavigationItem): Boolean =
            oldItem.destinationId == newItem.destinationId

        override fun areContentsTheSame(oldItem: NavigationItem, newItem: NavigationItem): Boolean =
            oldItem == newItem
    }
}
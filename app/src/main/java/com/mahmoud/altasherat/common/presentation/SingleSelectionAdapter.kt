package com.mahmoud.altasherat.common.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mahmoud.altasherat.R
import com.mahmoud.altasherat.common.domain.models.Country
import com.mahmoud.altasherat.common.domain.models.Language
import com.mahmoud.altasherat.common.domain.models.ListItem
import com.mahmoud.altasherat.databinding.ItemCountryBinding
import com.mahmoud.altasherat.databinding.ItemLanguageInputBinding

class SingleSelectionAdapter(
    private val items: List<ListItem>,
    private val clickListener: OnItemClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        private const val VIEW_TYPE_COUNTRY = 0
        private const val VIEW_TYPE_LANGUAGE = 1
    }

    var checkedPosition = -1

    // ViewHolder for Country Item
    inner class CountryViewHolder(private val binding: ItemCountryBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(item: ListItem) {
            val isItemSelected = checkedPosition == adapterPosition
            binding.countryName.text = item.name
            binding.checkIcon.visibility = if (isItemSelected) View.VISIBLE else View.GONE
            item.isSelected = isItemSelected

        }

        override fun onClick(view: View?) {
            val current = items[adapterPosition]
            val position = items.indexOf(current)
            checkedPosition = position
            clickListener.onItemSelected(current)
        }
    }

    //ViewHolder for Language Item
    inner class LanguageViewHolder(private val binding: ItemLanguageInputBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(item: ListItem) {
            val isItemSelected = checkedPosition == adapterPosition
            binding.languageName.text = item.name
            binding.languageFlag.text = item.flag
            item.isSelected = isItemSelected
            if (isItemSelected) {
                binding.languageChecked.setImageResource(R.drawable.ic_checked)
            } else {
                binding.languageChecked.setImageResource(R.drawable.ic_unchecked)
            }

        }

        override fun onClick(view: View?) {
            val current = items[adapterPosition]
            val position = items.indexOf(current)
            checkedPosition = position
            clickListener.onItemSelected(current)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is Country -> VIEW_TYPE_COUNTRY
            is Language -> VIEW_TYPE_LANGUAGE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_COUNTRY -> {
                val binding =
                    ItemCountryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                CountryViewHolder(binding)
            }

            VIEW_TYPE_LANGUAGE -> {
                val binding =
                    ItemLanguageInputBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                LanguageViewHolder(binding)
            }

            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = items[position]) {
            is Country -> (holder as CountryViewHolder).bind(item)
            is Language -> (holder as LanguageViewHolder).bind(item)
        }
    }
}

interface OnItemClickListener {
    fun onItemSelected(item: ListItem)
}
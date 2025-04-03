package com.mahmoud.altasherat.common.presentation.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mahmoud.altasherat.R
import com.mahmoud.altasherat.common.util.Constants.VIEW_TYPE_COUNTRY
import com.mahmoud.altasherat.common.util.Constants.VIEW_TYPE_LANGUAGE
import com.mahmoud.altasherat.databinding.ItemCountryBinding
import com.mahmoud.altasherat.databinding.ItemLanguageInputBinding
import com.mahmoud.altasherat.features.al_tashirat_services.language_country.domain.models.Country
import com.mahmoud.altasherat.features.al_tashirat_services.language_country.domain.models.Language
import com.mahmoud.altasherat.features.al_tashirat_services.language_country.domain.models.ListItem

class SingleSelectionAdapter(
    private val items: List<ListItem>,
    private val clickListener: OnItemClickListener,
    private val defaultPosition: Int = 0,
//    private val selectedCountryPosition: Int = 0
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var checkedLanguagePosition = -1
    var checkedCountryPosition = -1

    // ViewHolder for Country Item
    inner class CountryViewHolder(private val binding: ItemCountryBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        init {
            checkedCountryPosition = defaultPosition
            itemView.setOnClickListener(this)
        }

        fun bind(item: ListItem) {
            val isItemSelected = checkedCountryPosition == absoluteAdapterPosition
            binding.countryName.text = item.name
            item.isSelected = isItemSelected
            Log.d("IsItemSelected?", isItemSelected.toString())
            binding.checkIcon.visibility = if (isItemSelected) View.VISIBLE else View.INVISIBLE
        }

        override fun onClick(view: View?) {
            val current = items[adapterPosition]
            val position = items.indexOf(current)
            checkedCountryPosition = position
            notifyDataSetChanged()
            clickListener.onItemSelected(current)
        }
    }

    //ViewHolder for Language Item
    inner class LanguageViewHolder(private val binding: ItemLanguageInputBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        init {
            checkedLanguagePosition = defaultPosition
            itemView.setOnClickListener(this)
        }

        fun bind(item: ListItem) {
            val isItemSelected = checkedLanguagePosition == adapterPosition
            binding.languageName.text = item.name
            binding.languageFlag.text = item.flag
            item.isSelected = isItemSelected
            binding.root.isActivated = (checkedLanguagePosition == adapterPosition)
            binding.root.setBackgroundResource(R.drawable.bg_language_selector)

            if (isItemSelected) {
                binding.languageChecked.setImageResource(R.drawable.ic_checked)
            } else {
                binding.languageChecked.setImageResource(R.drawable.ic_unchecked)

            }

        }

        override fun onClick(view: View?) {
            val current = items[adapterPosition]
            val position = items.indexOf(current)
            checkedLanguagePosition = position
            notifyDataSetChanged()
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
package com.mahmoud.altasherat.common.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mahmoud.altasherat.R

class CountryPickerAdapter(
    private val items: List<String>,
    private val clickListener: OnItemClickListener
) : RecyclerView.Adapter<CountryPickerAdapter.ViewHolder>() {
    var checkedPosition = -1

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        val countryNameTxt = itemView.findViewById<TextView>(R.id.country_name)
        val checkIconImg = itemView.findViewById<ImageView>(R.id.check_icon)

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(item: String) {
            val isItemSelected = checkedPosition == adapterPosition
            checkIconImg.visibility = if (isItemSelected) View.VISIBLE else View.GONE
            countryNameTxt.text = item
//            item.isSelected = isItemSelected
        }

        override fun onClick(view: View) {
            val current = items[adapterPosition]
            val position = items.indexOf(current)
            checkedPosition = position
            clickListener.onItemSelected(current)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_country, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = items[position]
        holder.bind(currentItem)
    }
}

interface OnItemClickListener {
    fun onItemSelected(item: String)
}
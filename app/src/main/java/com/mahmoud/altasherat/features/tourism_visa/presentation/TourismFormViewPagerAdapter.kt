package com.mahmoud.altasherat.features.tourism_visa.presentation

import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.recyclerview.widget.RecyclerView

class TourismFormViewPagerAdapter(
    private val pages: List<View>
) : RecyclerView.Adapter<TourismFormViewPagerAdapter.PagerViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PagerViewHolder {
        return PagerViewHolder(FrameLayout(parent.context).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        })
    }

    override fun onBindViewHolder(
        holder: PagerViewHolder,
        position: Int
    ) {
        holder.container.removeAllViews()
        holder.container.addView(pages[position])
    }

    override fun getItemCount(): Int {
        return pages.size
    }

    inner class PagerViewHolder(val container: FrameLayout) :
        RecyclerView.ViewHolder(container) {
    }

}
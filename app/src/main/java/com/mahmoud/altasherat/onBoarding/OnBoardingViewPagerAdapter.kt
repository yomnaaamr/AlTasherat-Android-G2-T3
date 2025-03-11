package com.mahmoud.altasherat.onBoarding

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mahmoud.altasherat.databinding.ItemOnboardingBinding

class OnBoardingViewPagerAdapter(
    private val context: Context,
    private val onBoardingPages:List<OnBoardingContent>
):RecyclerView.Adapter<OnBoardingViewPagerAdapter.OnBoardingViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnBoardingViewHolder =
        OnBoardingViewHolder(ItemOnboardingBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: OnBoardingViewHolder, position: Int) =
        holder.bind(context , page = onBoardingPages[position])

    override fun getItemCount() = onBoardingPages.size

    inner class OnBoardingViewHolder(private val binding: ItemOnboardingBinding):
        RecyclerView.ViewHolder(binding.root){
        fun bind(context: Context , page:OnBoardingContent) = with(binding){
            onBoardingImg.setImageResource(page.imgID)
            onBoardingSubTitle.text = context.getString(page.imageDescription)
        }

        }

}
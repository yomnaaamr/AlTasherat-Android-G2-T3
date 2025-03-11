package com.mahmoud.altasherat.onBoarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.mahmoud.altasherat.R
import com.mahmoud.altasherat.databinding.FragmentViewPagerBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ViewPagerFragment : Fragment() {

    private lateinit var tabLayout: TabLayout
    private lateinit var binding: FragmentViewPagerBinding
    private lateinit var onBoardingViewPagerAdapter: OnBoardingViewPagerAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentViewPagerBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun initializeViews() {
        tabLayout = binding.tabLayout
        setupViewPager()
        setupTabLayout()
    }

    private fun setupViewPager() {
        val pages = listOf(
            OnBoardingContent(R.drawable.img_bag, R.string.onBoarding1_SubTitle),
            OnBoardingContent(R.drawable.img_openpassport, R.string.onBoarding2_subTitle),
            OnBoardingContent(R.drawable.img_visa, R.string.onBoarding3_subTitle)
        )
        onBoardingViewPagerAdapter = OnBoardingViewPagerAdapter(requireContext(), pages)
        binding.viewPager2.adapter = onBoardingViewPagerAdapter
    }

    private fun setupTabLayout() {
        TabLayoutMediator(tabLayout, binding.viewPager2) { tab, position ->
        }.attach()
    }
}
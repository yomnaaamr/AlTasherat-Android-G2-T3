package com.mahmoud.altasherat.onBoarding.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.mahmoud.altasherat.R
import com.mahmoud.altasherat.databinding.FragmentOnBoardingBinding
import com.mahmoud.altasherat.onBoarding.presentation.viewModel.OnBoardingViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnBoardingFragment : Fragment() {

    private lateinit var tabLayout: TabLayout
    private lateinit var binding: FragmentOnBoardingBinding
    private lateinit var viewPager2: ViewPager2
    private lateinit var onBoardingViewPagerAdapter: OnBoardingViewPagerAdapter
    private val onBoardingViewModel:OnBoardingViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentOnBoardingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onBoardingViewModel.setOnBoardingVisibilityShown()
        initializeViews()

    }

    private fun initializeViews() {
        tabLayout = binding.tabLayout
        setupViewPager()
        setupTabLayout()
        binding.buttonNext.setOnClickListener {
            val nextScreen = viewPager2.currentItem + 1
            if (nextScreen < onBoardingViewPagerAdapter.itemCount) {
                viewPager2.currentItem = nextScreen
            }
        }
    }

    private fun setupViewPager() {
        val pages = listOf(
            OnBoardingContent(R.drawable.img_bag, R.string.onBoarding1_SubTitle),
            OnBoardingContent(R.drawable.img_openpassport, R.string.onBoarding2_subTitle),
            OnBoardingContent(R.drawable.img_visa, R.string.onBoarding3_subTitle)
        )
        onBoardingViewPagerAdapter = OnBoardingViewPagerAdapter(requireContext(), pages)
        binding.viewPager2.adapter = onBoardingViewPagerAdapter
        viewPager2 = binding.viewPager2
    }

    private fun setupTabLayout() {
        TabLayoutMediator(tabLayout, binding.viewPager2) { tab, position ->
        }.attach()
    }
}
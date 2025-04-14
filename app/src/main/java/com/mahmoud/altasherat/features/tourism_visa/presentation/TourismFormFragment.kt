package com.mahmoud.altasherat.features.tourism_visa.presentation

import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.mahmoud.altasherat.common.presentation.base.BaseFragment
import com.mahmoud.altasherat.databinding.FragmentTourismFormBinding
import com.mahmoud.altasherat.databinding.TourismFormPage1Binding
import com.mahmoud.altasherat.databinding.TourismFormPage2Binding

class TourismFormFragment :
    BaseFragment<FragmentTourismFormBinding>(FragmentTourismFormBinding::inflate) {

    private lateinit var page1Binding: TourismFormPage1Binding
    private lateinit var page2Binding: TourismFormPage2Binding
    private lateinit var viewPager2: ViewPager2


    override fun FragmentTourismFormBinding.initialize() {
        (requireActivity() as AppCompatActivity).setSupportActionBar(tourismFormToolbar)

        (requireActivity() as AppCompatActivity).supportActionBar?.apply {
            setDisplayShowTitleEnabled(true)
        }

        // Handle back navigation
        binding.tourismFormToolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        // Inflate two views
        page1Binding = TourismFormPage1Binding.inflate(layoutInflater)
        page2Binding = TourismFormPage2Binding.inflate(layoutInflater)
        val pages: List<View> = listOf(
            page1Binding.root,
            page2Binding.root
        )

        val formAdapter = TourismFormViewPagerAdapter(pages)
        binding.formViewPager.adapter = formAdapter
        viewPager2 = binding.formViewPager

        page1Binding.nextBtn.setOnClickListener {
            viewPager2.currentItem = 1
        }
        page2Binding.sendBtn.setOnClickListener {
            submitForm()
        }
    }

    private fun submitForm() {
        Toast.makeText(requireContext(), "Submit", Toast.LENGTH_SHORT).show()
    }
}
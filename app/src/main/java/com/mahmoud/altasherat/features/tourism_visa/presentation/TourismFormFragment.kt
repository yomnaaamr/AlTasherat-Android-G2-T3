package com.mahmoud.altasherat.features.tourism_visa.presentation

import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.mahmoud.altasherat.common.presentation.base.BaseFragment
import com.mahmoud.altasherat.databinding.FragmentTourismFormBinding

class TourismFormFragment :
    BaseFragment<FragmentTourismFormBinding>(FragmentTourismFormBinding::inflate) {

    override fun FragmentTourismFormBinding.initialize() {
        (requireActivity() as AppCompatActivity).setSupportActionBar(tourismFormToolbar)

        (requireActivity() as AppCompatActivity).supportActionBar?.apply {
            setDisplayShowTitleEnabled(true)
        }

        // Handle back navigation
        binding.tourismFormToolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }
}
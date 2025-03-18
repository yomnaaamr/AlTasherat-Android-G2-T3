package com.mahmoud.altasherat.features.language_country.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.mahmoud.altasherat.common.data.LanguageDataSource
import com.mahmoud.altasherat.common.domain.models.ListItem
import com.mahmoud.altasherat.common.presentation.CountryPickerBottomSheet
import com.mahmoud.altasherat.common.presentation.OnItemClickListener
import com.mahmoud.altasherat.common.presentation.SingleSelectionAdapter
import com.mahmoud.altasherat.databinding.FragmentLanguageBinding
import com.mahmoud.altasherat.features.onBoarding.presentation.OnBoardingViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class LanguageFragment : Fragment(), OnItemClickListener {

    private lateinit var binding: FragmentLanguageBinding
    private lateinit var languageAdapter: SingleSelectionAdapter
    private val onBoardingViewModel: OnBoardingViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentLanguageBinding.inflate(inflater, container, false)

        languageAdapter =
            SingleSelectionAdapter(LanguageDataSource.getLanguages(requireContext()), this@LanguageFragment)

        binding.languageRecycler.apply {
            adapter = languageAdapter
            layoutManager = LinearLayoutManager(requireActivity())
        }
        binding.chooseCountryLayout.setOnClickListener {
            val bottomSheet = CountryPickerBottomSheet { selectedCountry ->
                binding.countryFlag.text = selectedCountry.flag
                binding.countryName.text = selectedCountry.name
            }
            bottomSheet.show(childFragmentManager, "CountryPickerBottomSheet")
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.continueBtn.setOnClickListener {
            // add navigation and any validation here

            // set onBoarding visibility to shown
            Log.d("AITASHERAAT", "Setting onBoarding Visibility To Shown")
            lifecycleScope.launch {
                onBoardingViewModel.setOnBoardingVisibilityShown()
            }

        }
    }

    override fun onItemSelected(item: ListItem) {
        Toast.makeText(requireActivity(), item.name, Toast.LENGTH_SHORT).show()
    }

}
package com.mahmoud.altasherat.features.language_country.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.mahmoud.altasherat.common.data.LanguageDataSource
import com.mahmoud.altasherat.common.domain.models.ListItem
import com.mahmoud.altasherat.common.presentation.CountryPickerBottomSheet
import com.mahmoud.altasherat.common.presentation.OnItemClickListener
import com.mahmoud.altasherat.common.presentation.SingleSelectionAdapter
import com.mahmoud.altasherat.databinding.FragmentLanguageBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LanguageFragment : Fragment(), OnItemClickListener {

    private lateinit var binding: FragmentLanguageBinding
    private lateinit var languageAdapter: SingleSelectionAdapter
    private val viewModel: LanguageViewModel by viewModels()
    private lateinit var bottomSheet: CountryPickerBottomSheet


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

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.countries.collect { countries ->
                        bottomSheet = CountryPickerBottomSheet(countries) { selectedCountry ->
                            binding.countryFlag.text = selectedCountry.flag
                            binding.countryName.text = selectedCountry.name
                            Toast.makeText(
                                requireActivity(),
                                selectedCountry.name,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }

        binding.chooseCountryLayout.setOnClickListener {
            bottomSheet.show(childFragmentManager, "CountryPickerBottomSheet")
        }
        return binding.root
    }

    override fun onItemSelected(item: ListItem) {
        Toast.makeText(requireActivity(), item.name, Toast.LENGTH_SHORT).show()
    }

}
package com.mahmoud.altasherat.features.language_country.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.mahmoud.altasherat.common.data.LanguageDataSource
import com.mahmoud.altasherat.common.domain.models.ListItem
import com.mahmoud.altasherat.common.presentation.CountryPickerBottomSheet
import com.mahmoud.altasherat.common.presentation.OnItemClickListener
import com.mahmoud.altasherat.common.presentation.SingleSelectionAdapter
import com.mahmoud.altasherat.databinding.FragmentLanguageBinding


class LanguageFragment : Fragment(), OnItemClickListener {

    private lateinit var binding: FragmentLanguageBinding
    private lateinit var languageAdapter: SingleSelectionAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

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

    override fun onItemSelected(item: ListItem) {
        Toast.makeText(requireActivity(), item.name, Toast.LENGTH_SHORT).show()
    }

}
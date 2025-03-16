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
import com.mahmoud.altasherat.R
import com.mahmoud.altasherat.common.data.LanguageDataSource
import com.mahmoud.altasherat.common.domain.models.ListItem
import com.mahmoud.altasherat.common.presentation.adapters.CountryPickerBottomSheet
import com.mahmoud.altasherat.common.presentation.adapters.OnItemClickListener
import com.mahmoud.altasherat.common.presentation.adapters.SingleSelectionAdapter
import com.mahmoud.altasherat.common.presentation.utils.changeLocale
import com.mahmoud.altasherat.databinding.FragmentLanguageBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LanguageFragment : Fragment(), OnItemClickListener {

    private lateinit var binding: FragmentLanguageBinding
    private lateinit var languageAdapter: SingleSelectionAdapter
    private val viewModel: LanguageViewModel by viewModels()
    private lateinit var bottomSheet: CountryPickerBottomSheet

    private var selectedLanguage: ListItem? = null
    private var selectedCountry: ListItem? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

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
                            this@LanguageFragment.selectedCountry = selectedCountry
                            updateContinueButtonState()
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

        binding.continueBtn.setOnClickListener {
            if (selectedLanguage != null && selectedCountry != null) {
                viewModel.onAction(LanguageAction.SaveSelections(selectedLanguage!!, selectedCountry!!))
                Toast.makeText(requireContext(), "Selections saved", Toast.LENGTH_SHORT).show()
                requireContext().changeLocale(selectedLanguage!!.code)
            } else {
                Toast.makeText(requireContext(), getString(R.string.selection_required), Toast.LENGTH_SHORT).show()
            }

        }
        return binding.root
    }

    override fun onItemSelected(item: ListItem) {
        Toast.makeText(requireActivity(), item.name, Toast.LENGTH_SHORT).show()
        selectedLanguage = item
        updateContinueButtonState()
    }


    private fun updateContinueButtonState() {
        binding.continueBtn.isEnabled = selectedLanguage != null && selectedCountry != null
    }

}
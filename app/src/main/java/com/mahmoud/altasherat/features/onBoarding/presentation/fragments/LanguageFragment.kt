package com.mahmoud.altasherat.features.onBoarding.presentation.fragments

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
import androidx.navigation.fragment.findNavController
import com.mahmoud.altasherat.features.al_tashirat_services.language_country.data.LanguageDataSource
import com.mahmoud.altasherat.features.al_tashirat_services.language_country.domain.models.Country
import com.mahmoud.altasherat.features.al_tashirat_services.language_country.domain.models.Language
import com.mahmoud.altasherat.features.al_tashirat_services.language_country.domain.models.ListItem
import com.mahmoud.altasherat.common.presentation.adapters.CountryPickerBottomSheet
import com.mahmoud.altasherat.common.presentation.adapters.OnItemClickListener
import com.mahmoud.altasherat.common.presentation.adapters.SingleSelectionAdapter
import com.mahmoud.altasherat.common.presentation.utils.changeLocale
import com.mahmoud.altasherat.databinding.FragmentLanguageBinding
import com.mahmoud.altasherat.features.onBoarding.presentation.LanguageAction
import com.mahmoud.altasherat.features.onBoarding.presentation.LanguageViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.Locale


@AndroidEntryPoint
class LanguageFragment : Fragment(), OnItemClickListener {

    private lateinit var binding: FragmentLanguageBinding
    private lateinit var languageAdapter: SingleSelectionAdapter
    private val viewModel: LanguageViewModel by viewModels()
    private lateinit var bottomSheet: CountryPickerBottomSheet

    private var selectedLanguage: Language? = null
    private var selectedCountry: Country? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentLanguageBinding.inflate(inflater, container, false)

        val languages = LanguageDataSource.getLanguages(requireContext())
        val defaultLanguageIndex =
            languages.indexOfFirst { it.code == Locale.getDefault().language }
        languageAdapter =
            SingleSelectionAdapter(
                languages,
                this@LanguageFragment,
                defaultLanguageIndex
                )

        binding.languageRecycler.apply {
            adapter = languageAdapter
            layoutManager = LinearLayoutManager(requireActivity())
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.countries.collect { countries ->
                        bottomSheet = CountryPickerBottomSheet(countries) { selectedCountry ->
                            this@LanguageFragment.selectedCountry = selectedCountry as Country
                            binding.countryFlag.text = selectedCountry.flag
                            binding.countryName.text = selectedCountry.name
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
                viewModel.onAction(
                    LanguageAction.SaveSelections(
                        selectedLanguage!!,
                        selectedCountry!!
                    )
                )
                Toast.makeText(requireContext(), "Selection saved", Toast.LENGTH_SHORT).show()
                viewModel.setOnBoardingVisibilityShown()
                findNavController().navigate(R.id.action_languageFragment_to_authFragment)
                requireContext().changeLocale(selectedLanguage!!.code)
            } else {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.selection_required),
                    Toast.LENGTH_SHORT
                ).show()
            }

        }
        return binding.root
    }

    override fun onItemSelected(item: ListItem) {
        selectedLanguage = item as Language
    }


}
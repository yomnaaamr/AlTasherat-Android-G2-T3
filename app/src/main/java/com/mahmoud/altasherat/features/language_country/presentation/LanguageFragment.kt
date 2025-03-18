package com.mahmoud.altasherat.features.language_country.presentation

import android.os.Bundle
import android.util.Log
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
import com.mahmoud.altasherat.common.domain.models.Country
import com.mahmoud.altasherat.common.domain.models.Language
import com.mahmoud.altasherat.common.domain.models.ListItem
import com.mahmoud.altasherat.common.presentation.adapters.CountryPickerBottomSheet
import com.mahmoud.altasherat.common.presentation.adapters.OnItemClickListener
import com.mahmoud.altasherat.common.presentation.adapters.SingleSelectionAdapter
import com.mahmoud.altasherat.common.presentation.utils.changeLocale
import com.mahmoud.altasherat.databinding.FragmentLanguageBinding
import com.mahmoud.altasherat.features.onBoarding.presentation.OnBoardingViewModel
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
    private val onBoardingViewModel: OnBoardingViewModel by viewModels()

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
                viewModel.onAction(
                    LanguageAction.SaveSelections(
                        selectedLanguage!!,
                        selectedCountry!!
                    )
                )
                Toast.makeText(requireContext(), "Selections saved", Toast.LENGTH_SHORT).show()
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
        selectedLanguage = item as Language
    }


}
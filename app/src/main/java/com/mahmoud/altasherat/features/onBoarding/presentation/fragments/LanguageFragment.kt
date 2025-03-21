package com.mahmoud.altasherat.features.onBoarding.presentation.fragments

import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mahmoud.altasherat.R
import com.mahmoud.altasherat.common.presentation.adapters.CountryPickerBottomSheet
import com.mahmoud.altasherat.common.presentation.adapters.OnItemClickListener
import com.mahmoud.altasherat.common.presentation.adapters.SingleSelectionAdapter
import com.mahmoud.altasherat.common.presentation.base.BaseFragment
import com.mahmoud.altasherat.common.presentation.base.delegators.MessageType
import com.mahmoud.altasherat.common.presentation.utils.changeLocale
import com.mahmoud.altasherat.common.presentation.utils.toErrorMessage
import com.mahmoud.altasherat.databinding.FragmentLanguageBinding
import com.mahmoud.altasherat.features.al_tashirat_services.language_country.data.LanguageDataSource
import com.mahmoud.altasherat.features.al_tashirat_services.language_country.domain.models.Country
import com.mahmoud.altasherat.features.al_tashirat_services.language_country.domain.models.Language
import com.mahmoud.altasherat.features.al_tashirat_services.language_country.domain.models.ListItem
import com.mahmoud.altasherat.features.onBoarding.presentation.LanguageContract
import com.mahmoud.altasherat.features.onBoarding.presentation.LanguageViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale


@AndroidEntryPoint
class LanguageFragment : BaseFragment<FragmentLanguageBinding>(FragmentLanguageBinding::inflate), OnItemClickListener {

    private lateinit var languageAdapter: SingleSelectionAdapter
    private val viewModel: LanguageViewModel by viewModels()
    private lateinit var bottomSheet: CountryPickerBottomSheet

    private var selectedLanguage: Language? = null
    private var selectedCountry: Country? = null


    override fun FragmentLanguageBinding.initialize() {
        val languages = LanguageDataSource.getLanguages(requireContext())
        val defaultLanguageIndex =
            languages.indexOfFirst { it.code == Locale.getDefault().language }
        selectedLanguage = languages[defaultLanguageIndex]
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

        setupObservers()
        setupListeners()
    }


    private fun setupObservers(){

        collectFlow(viewModel.state){ state ->
            when(state) {
                is LanguageContract.LanguageState.Error -> {}
                is LanguageContract.LanguageState.Idle -> {}
                is LanguageContract.LanguageState.Loading -> {}
                is LanguageContract.LanguageState.Success -> {
                    val countries = state.data
                    binding.countryFlag.text = countries[0].flag
                    binding.countryName.text = countries[0].name
                    bottomSheet = CountryPickerBottomSheet(state.data) { selectedCountry ->
                        this@LanguageFragment.selectedCountry = selectedCountry as Country
                        binding.countryFlag.text = selectedCountry.flag
                        binding.countryName.text = selectedCountry.name
                    }
                }
            }
        }


        collectFlow(viewModel.events){ event ->
            when(event){
                is LanguageContract.LanguageEvent.Error -> {
                    val error = event.error.toErrorMessage(requireContext())
                    showMessage(error, MessageType.TOAST,this)
                }
                is LanguageContract.LanguageEvent.NavigationToAuth -> {
                    findNavController().navigate(R.id.action_languageFragment_to_authFragment)
                }
            }
        }
    }


    private fun setupListeners() {


        binding.chooseCountryLayout.setOnClickListener {
            bottomSheet.show(childFragmentManager, "CountryPickerBottomSheet")
        }

        binding.continueBtn.setOnClickListener {
            if (selectedLanguage != null && selectedCountry != null) {
                viewModel.onAction(
                    LanguageContract.LanguageAction.SaveSelections(
                        selectedLanguage!!,
                        selectedCountry!!
                    )
                )
                Toast.makeText(requireContext(), "Selection saved", Toast.LENGTH_SHORT).show()
                viewModel.onAction(
                    LanguageContract.LanguageAction.SetOnBoardingState
                )
                requireContext().changeLocale(selectedLanguage!!.code)
            } else {
                showMessage(getString(R.string.selection_required), MessageType.TOAST, this)
            }


        }
    }

    override fun onItemSelected(item: ListItem) {
        selectedLanguage = item as Language
    }


}
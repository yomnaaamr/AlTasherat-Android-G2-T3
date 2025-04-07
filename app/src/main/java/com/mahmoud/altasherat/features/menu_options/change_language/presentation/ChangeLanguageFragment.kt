package com.mahmoud.altasherat.features.menu_options.change_language.presentation

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mahmoud.altasherat.R
import com.mahmoud.altasherat.common.presentation.adapters.OnItemClickListener
import com.mahmoud.altasherat.common.presentation.adapters.SingleSelectionAdapter
import com.mahmoud.altasherat.common.presentation.base.BaseFragment
import com.mahmoud.altasherat.common.presentation.base.delegators.MessageType
import com.mahmoud.altasherat.common.presentation.utils.changeLocale
import com.mahmoud.altasherat.common.presentation.utils.toErrorMessage
import com.mahmoud.altasherat.common.util.Constants
import com.mahmoud.altasherat.databinding.FragmentChangeLanguageBinding
import com.mahmoud.altasherat.features.al_tashirat_services.common.domain.models.ListItem
import com.mahmoud.altasherat.features.al_tashirat_services.language.data.LanguageDataSource
import com.mahmoud.altasherat.features.al_tashirat_services.language.domain.models.Language
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ChangeLanguageFragment : BaseFragment<FragmentChangeLanguageBinding>(
    FragmentChangeLanguageBinding::inflate
), OnItemClickListener {


    private lateinit var languageAdapter: SingleSelectionAdapter
    private val viewModel: ChangeLanguageViewModel by viewModels()
    private lateinit var selectedLanguage: Language


    override fun FragmentChangeLanguageBinding.initialize() {


        setupObservers()

        binding.saveButton.setOnClickListener {
            viewModel.onAction(
                ChangeLanguageContract.ChangeLanguageAction.SaveSelectedLanguage(
                    selectedLanguage
                )
            )
            requireContext().changeLocale(selectedLanguage.code)
        }

        binding.backButton.setOnClickListener {
            findNavController().navigateUp()
        }


    }


    private fun setupObservers() {
        collectFlow(viewModel.events) { event ->
            when (event) {
                is ChangeLanguageContract.ChangeLanguageEvent.NavigationToProfile -> {
                    findNavController().navigate(R.id.action_changeLanguageFragment_to_menuFragment)
                }

                is ChangeLanguageContract.ChangeLanguageEvent.Error -> {
                    val error = event.error.toErrorMessage(requireContext())
                    showMessage(error, MessageType.SNACKBAR, this)
                }

            }
        }

        collectFlow(viewModel.languageCode) { languageCode ->
            val userLanguageCode = languageCode ?: Constants.LOCALE_AR
            val languages = LanguageDataSource.getLanguages(requireContext())
            selectedLanguage = languages.find { it.code == userLanguageCode }!!

            languageAdapter =
                SingleSelectionAdapter(
                    languages,
                    this@ChangeLanguageFragment,
                    selectedLanguage.id
                )

            binding.languageRecycler.apply {
                adapter = languageAdapter
                layoutManager = LinearLayoutManager(requireActivity())
            }
        }
    }

    override fun onItemSelected(item: ListItem) {
        selectedLanguage = item as Language
    }


}
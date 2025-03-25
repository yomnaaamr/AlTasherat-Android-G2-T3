package com.mahmoud.altasherat.features.menu.changeLanguage.presentation

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
import com.mahmoud.altasherat.databinding.FragmentChangeLanguageBinding
import com.mahmoud.altasherat.features.al_tashirat_services.language_country.data.LanguageDataSource
import com.mahmoud.altasherat.features.al_tashirat_services.language_country.domain.models.Language
import com.mahmoud.altasherat.features.al_tashirat_services.language_country.domain.models.ListItem
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ChangeLanguageFragment : BaseFragment<FragmentChangeLanguageBinding>(
    FragmentChangeLanguageBinding::inflate), OnItemClickListener {


    private lateinit var languageAdapter: SingleSelectionAdapter
    private val viewModel: ChangeLanguageViewModel by viewModels()
    private var selectedLanguage: Language? = null


    override fun FragmentChangeLanguageBinding.initialize() {

        val languages = LanguageDataSource.getLanguages(requireContext())
        val defaultLanguageIndex = languages[1].id
        languageAdapter =
            SingleSelectionAdapter(
                languages,
                this@ChangeLanguageFragment,
                defaultLanguageIndex
            )

        binding.languageRecycler.apply {
            adapter = languageAdapter
            layoutManager = LinearLayoutManager(requireActivity())
        }


        binding.saveButton.setOnClickListener {
            if (selectedLanguage != null) {

                viewModel.onAction(
                    ChangeLanguageContract.ChangeLanguageAction.SaveSelectedLanguage(
                        selectedLanguage!!
                    )
                )
                requireContext().changeLocale(selectedLanguage!!.code)
                requireActivity().recreate()
            }else {
                showMessage(
                    getString(R.string.selection_required),
                    MessageType.TOAST,
                    this@ChangeLanguageFragment
                )
            }
        }


        setupObservers()
    }


    private fun setupObservers(){
        collectFlow(viewModel.events){ event ->
            when(event){
                is ChangeLanguageContract.ChangeLanguageEvent.NavigationToProfile -> {
                    findNavController().navigate(R.id.action_changeLanguageFragment_to_menuFragment)
                }
                is ChangeLanguageContract.ChangeLanguageEvent.Error -> {
                    val error = event.error.toErrorMessage(requireContext())
                    showMessage(error, MessageType.SNACKBAR, this)
                }

            }
        }
    }

    override fun onItemSelected(item: ListItem) {
        selectedLanguage = item as Language
    }


}
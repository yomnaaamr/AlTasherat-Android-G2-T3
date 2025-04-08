package com.mahmoud.altasherat.features.menu_options.change_password.presentation

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.mahmoud.altasherat.common.domain.util.error.AltasheratError
import com.mahmoud.altasherat.common.domain.util.error.ValidationError
import com.mahmoud.altasherat.common.presentation.base.BaseFragment
import com.mahmoud.altasherat.common.presentation.base.delegators.MessageType
import com.mahmoud.altasherat.common.presentation.utils.toErrorMessage
import com.mahmoud.altasherat.databinding.FragmentChangePasswordBinding
import com.mahmoud.altasherat.features.menu_options.change_password.data.models.request.ChangePassRequest
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChangePasswordFragment : BaseFragment<FragmentChangePasswordBinding>(
    FragmentChangePasswordBinding::inflate
) {

    private val changePassViewModel: ChangePasswordViewModel by viewModels()
    override fun FragmentChangePasswordBinding.initialize() {
        (requireActivity() as AppCompatActivity).setSupportActionBar(changePassToolbar)

        (requireActivity() as AppCompatActivity).supportActionBar?.apply {
            setDisplayShowTitleEnabled(true)
        }
        setupObservers()
        setupListeners()
    }

    private fun setupObservers() {

        collectFlow(changePassViewModel.state) { state ->
            state.response?.let {
                showMessage(state.response.message, MessageType.SNACKBAR, this)
            }
            when (state.screenState) {
                is ChangePasswordContract.ChangePasswordState.Idle -> hideLoading()
                is ChangePasswordContract.ChangePasswordState.Loading -> showLoading()
                is ChangePasswordContract.ChangePasswordState.Success -> hideLoading()
                is ChangePasswordContract.ChangePasswordState.Error -> hideLoading()
            }
        }

        collectFlow(changePassViewModel.events) { event ->
            when (event) {
                is ChangePasswordContract.ChangePasswordEvent.NavigationToMenu -> {
                    findNavController().navigateUp()
                }

                is ChangePasswordContract.ChangePasswordEvent.Error -> {
                    when (event.error) {
                        is AltasheratError.ValidationErrors -> {
                            displayValidationErrors(event.error.errors)
                        }

                        else -> {
                            val errorMessage = event.error.toErrorMessage(requireContext())
                            showMessage(errorMessage, MessageType.SNACKBAR, this)
                        }
                    }
                }

            }
        }

    }

    private fun setupListeners() {
        // Handle back navigation
        binding.changePassToolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        binding.saveBtn.setOnClickListener {
            val oldPassword = binding.oldPasswordEdit.text.toString()
            val newPassword = binding.newPasswordEdit.text.toString()
            val confirmPassword = binding.confirmPasswordEdit.text.toString()
            changePassViewModel.onAction(
                ChangePasswordContract.ChangePasswordAction.ChangePassword(
                    ChangePassRequest(
                        oldPassword = oldPassword,
                        newPassword = newPassword,
                        newPasswordConfirmation = confirmPassword
                    )
                )
            )
        }
    }

    private fun displayValidationErrors(errors: List<ValidationError>) {
        val errorFields = mapOf(
            setOf(
                ValidationError.EMPTY_OLD_PASSWORD,
                ValidationError.INVALID_OLD_PASSWORD
            ) to binding.oldPasswordLayout,
            setOf(
                ValidationError.EMPTY_NEW_PASSWORD,
                ValidationError.INVALID_NEW_PASSWORD
            ) to binding.newPasswordLayout,
            setOf(
                ValidationError.EMPTY_PASSWORD_CONFIRMATION,
                ValidationError.INVALID_PASSWORD_CONFIRMATION
            ) to binding.confirmPasswordLayout
        )

        binding.oldPasswordLayout.isErrorEnabled = false
        binding.oldPasswordLayout.errorIconDrawable = null
        binding.oldPasswordLayout.error = null

        binding.newPasswordLayout.isErrorEnabled = false
        binding.newPasswordLayout.errorIconDrawable = null
        binding.newPasswordLayout.error = null

        binding.confirmPasswordLayout.isErrorEnabled = false
        binding.confirmPasswordLayout.errorIconDrawable = null
        binding.confirmPasswordLayout.error = null

        errors.forEach { error ->
            errorFields.entries.find { it.key.contains(error) }?.value?.let { view ->
                view.isErrorEnabled = true
                view.error = error.toErrorMessage(requireContext())
            }
        }
    }
}

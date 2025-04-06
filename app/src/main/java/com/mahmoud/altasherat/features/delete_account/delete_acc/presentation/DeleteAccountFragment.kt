package com.mahmoud.altasherat.features.delete_account.delete_acc.presentation

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.mahmoud.altasherat.R
import com.mahmoud.altasherat.common.presentation.base.BaseFragment
import com.mahmoud.altasherat.common.presentation.base.delegators.MessageType
import com.mahmoud.altasherat.common.presentation.utils.toErrorMessage
import com.mahmoud.altasherat.databinding.FragmentDeleteAccountBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DeleteAccountFragment : BaseFragment<FragmentDeleteAccountBinding>(
    FragmentDeleteAccountBinding::inflate
) {
    private lateinit var confirmBottomSheet: ConfirmDeleteBottomSheet
    private val deleteAccViewModel: DeleteAccountViewModel by viewModels()

    override fun FragmentDeleteAccountBinding.initialize() {
        (requireActivity() as AppCompatActivity).setSupportActionBar(deleteAccToolbar)

        (requireActivity() as AppCompatActivity).supportActionBar?.apply {
            setDisplayShowTitleEnabled(true)
        }
        setupObservers()
        setupListeners()
    }

    private fun setupObservers() {
        collectFlow(deleteAccViewModel.events) { event ->
            when (event) {
                is DeleteAccountContract.DeleteAccountEvent.NavigationToDashboard -> {
                    findNavController().navigate(R.id.action_deleteAccountFragment_to_dashboardFragment)
                }

                is DeleteAccountContract.DeleteAccountEvent.Error -> {
                    showMessage(
                        event.error.toErrorMessage(requireContext()), MessageType.SNACKBAR, this
                    )
                }
            }
        }
        collectFlow(deleteAccViewModel.state) { state ->
            when (state) {
                is DeleteAccountContract.DeleteAccountState.Idle -> {}
                is DeleteAccountContract.DeleteAccountState.Loading -> showLoading()
                is DeleteAccountContract.DeleteAccountState.Success -> {
                    hideLoading()
                    showMessage(state.message, MessageType.SNACKBAR, this)
                }

                is DeleteAccountContract.DeleteAccountState.Error -> hideLoading()
            }
        }
    }

    private fun setupListeners() {
        // Handle back navigation
        binding.deleteAccToolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        binding.confirmDeleteAccBtn.setOnClickListener {
            confirmBottomSheet = ConfirmDeleteBottomSheet { password ->
                deleteAccViewModel.onAction(
                    DeleteAccountContract.DeleteAccountAction.DeleteAccount(password)
                )
            }
            confirmBottomSheet.show(childFragmentManager, "Confirm delete account bottom sheet")
        }
        binding.cancelDeleteAccBtn.setOnClickListener {
            findNavController().navigateUp()
        }
    }
}
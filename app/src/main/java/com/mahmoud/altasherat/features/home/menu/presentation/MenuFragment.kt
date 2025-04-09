package com.mahmoud.altasherat.features.home.menu.presentation

import android.view.View
import android.widget.Toast
import androidx.core.net.toUri
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.mahmoud.altasherat.R
import com.mahmoud.altasherat.common.presentation.base.BaseFragment
import com.mahmoud.altasherat.common.presentation.base.delegators.MessageType
import com.mahmoud.altasherat.common.presentation.utils.toErrorMessage
import com.mahmoud.altasherat.databinding.FragmentMenuBinding
import com.mahmoud.altasherat.features.al_tashirat_services.user.domain.models.User
import com.mahmoud.altasherat.features.home.menu.data.MenuDataSource
import com.mahmoud.altasherat.features.home.menu.presentation.adapters.MenuNavigationAdapter
import com.mahmoud.altasherat.features.home.menu.presentation.verify_email.VerifyActions
import com.mahmoud.altasherat.features.home.menu.presentation.verify_email.VerifySnackBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MenuFragment : BaseFragment<FragmentMenuBinding>(FragmentMenuBinding::inflate) {


    private val viewModel: MenuViewModel by viewModels()
    private val adapter = MenuNavigationAdapter { destination ->
        findNavController().navigate(destination.destinationId)
    }


    override fun FragmentMenuBinding.initialize() {

        binding.menuRecyclerView.adapter = adapter
        viewModel.onAction(MenuContract.MenuAction.GetUserData)

        setupObservers()

        binding.editProfileButton.setOnClickListener {
            findNavController().navigate(R.id.action_menuFragment_to_profileInfoFragment)
        }

        binding.logoutButton.setOnClickListener {
            viewModel.onAction(MenuContract.MenuAction.Logout)
        }
    }


    private fun updateUserData(user: User?) {
        binding.profileImg.editIcon.visibility = View.GONE

        user?.let {
            val middleName = if (it.middleName.isNullOrEmpty()) "" else "${it.middleName}"
            binding.userName.text =
                getString(R.string.user_full_name, it.firstname, middleName, it.lastname)

            it.image?.path?.let { path ->
                Glide.with(requireContext())
                    .load(path.toUri())
                    .centerCrop()
                    .placeholder(R.drawable.ic_default_profile)
                    .into(binding.profileImg.profileImg)
            }
        }

    }


    private fun setupObservers() {
        collectFlow(viewModel.events) { event ->
            when (event) {
                is MenuContract.MenuEvent.Error -> {
                    showMessage(
                        event.error.toErrorMessage(requireContext()),
                        MessageType.SNACKBAR,
                        this
                    )
                }

                is MenuContract.MenuEvent.NavigationToAuth -> {
                    findNavController().navigate(R.id.action_menu_to_authFragment)
                }
            }

        }


        collectFlow(viewModel.state) { state ->

            when (state.screenState) {
                is MenuContract.MenuScreenState.Loading -> {
                    showLoading()
                }

                else -> {
                    hideLoading()
                }
            }


            val menuItems = MenuDataSource.getNavigationItems(requireContext())

            val hasUserLoggedIn = state.isAuthenticated
            val filteredItems = if (hasUserLoggedIn) {
                VerifySnackBar.showVerificationSnackbar(
                    activity = requireActivity(),
                    message = resources.getString(R.string.email_not_verified),
                    verifyActions = object : VerifyActions.OnConfirmClickListener,
                        VerifyActions.OnCloseClickListener {
                        override fun onConfirmClicked() {
                            // handle confirm
                            Toast.makeText(requireContext(), "Confirm", Toast.LENGTH_SHORT).show()
                        }

                        override fun onCloseClicked() {
                            // handle close
                            Toast.makeText(requireContext(), "Close", Toast.LENGTH_SHORT).show()

                        }
                    },
                )
//             exclude auth fragment from menu
                menuItems.filter { it.id != 1 }
            } else {
                menuItems.filter { !it.requiresAuth }
            }


            adapter.submitList(filteredItems)

            binding.logoutAndVersionLayout.visibility =
                if (hasUserLoggedIn) View.VISIBLE else View.GONE

            binding.userDataLayout.visibility =
                if (hasUserLoggedIn) View.VISIBLE else View.GONE

            updateUserData(state.user)

        }

    }


}
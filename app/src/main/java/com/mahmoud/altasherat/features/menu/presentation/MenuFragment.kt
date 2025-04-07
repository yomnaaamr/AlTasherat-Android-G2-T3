package com.mahmoud.altasherat.features.menu.presentation

import android.view.View
import androidx.core.net.toUri
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.mahmoud.altasherat.R
import com.mahmoud.altasherat.common.presentation.base.BaseFragment
import com.mahmoud.altasherat.databinding.FragmentMenuBinding
import com.mahmoud.altasherat.features.al_tashirat_services.user_services.domain.models.User
import com.mahmoud.altasherat.features.menu.data.MenuDataSource
import com.mahmoud.altasherat.features.menu.presentation.adapters.MenuNavigationAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MenuFragment : BaseFragment<FragmentMenuBinding>(FragmentMenuBinding::inflate) {


    private val viewModel: MenuViewModel by viewModels()
    private val adapter = MenuNavigationAdapter { destination ->
        findNavController().navigate(destination.destinationId)
    }


    override fun FragmentMenuBinding.initialize() {

        binding.menuRecyclerView.adapter = adapter
        val menuItems = MenuDataSource.getNavigationItems(requireContext())

        collectFlow(viewModel.state) { state ->

            when (state.screenState) {
                is MenuContract.MenuScreenState.Error -> {
                    hideLoading()
                }

                is MenuContract.MenuScreenState.Idle -> {}
                is MenuContract.MenuScreenState.Loading -> {
                    showLoading()
                }

                is MenuContract.MenuScreenState.Success -> {
                    hideLoading()
                }
            }


            val hasUserLoggedIn = state.isAuthenticated
            val filteredItems = if (hasUserLoggedIn) {
                viewModel.onAction(MenuContract.MenuAction.GetUserData)

//                        exclude auth fragment from menu
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
        binding.editProfileButton.setOnClickListener {
            findNavController().navigate(R.id.action_menuFragment_to_profileInfoFragment)
        }
    }


    private fun updateUserData(user: User?) {
        binding.profileImg.editIcon.visibility = View.GONE

        user?.let {
            val middleName = if (it.middleName.isNullOrEmpty()) "" else " ${it.middleName}"
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


}
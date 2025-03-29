package com.mahmoud.altasherat.features.menu.presentation

import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.mahmoud.altasherat.common.presentation.base.BaseFragment
import com.mahmoud.altasherat.databinding.FragmentMenuBinding
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

        collectFlow(viewModel.state) { isUserAuthenticated ->
            when (isUserAuthenticated) {
                is MenuContract.MenuState.Error -> {}
                is MenuContract.MenuState.Idle -> {}
                is MenuContract.MenuState.Loading -> {}
                is MenuContract.MenuState.Success -> {
                    val hasUserLoggedIn = isUserAuthenticated.isAuthenticated
                    val filteredItems = if (hasUserLoggedIn) {
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
                }
            }
        }
        binding.editProfileButton.setOnClickListener {
            findNavController().navigate(R.id.action_menuFragment_to_profileInfoFragment)
        }
    }


}
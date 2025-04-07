package com.mahmoud.altasherat.features.menu.presentation.fragments

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.mahmoud.altasherat.R
import com.mahmoud.altasherat.common.presentation.base.BaseFragment
import com.mahmoud.altasherat.databinding.FragmentContactUsBinding
import com.mahmoud.altasherat.features.al_tashirat_services.user.domain.models.User
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ContactUsFragment :
    BaseFragment<FragmentContactUsBinding>(FragmentContactUsBinding::inflate) {


    private val viewModel: ContactUsViewModel by viewModels()


    override fun FragmentContactUsBinding.initialize() {
        binding.backButton.setOnClickListener {
            findNavController().navigateUp()
        }



        collectFlow(viewModel.state) { state ->
            when (state.screenState) {
                is ContactUsContract.ContactUsScreenState.Error -> hideLoading()
                is ContactUsContract.ContactUsScreenState.Idle -> {}
                is ContactUsContract.ContactUsScreenState.Loading -> showLoading()
                is ContactUsContract.ContactUsScreenState.Success -> hideLoading()
            }

            updateUserData(state.user)

        }
    }


    private fun updateUserData(user: User?) {
        user?.let {
            val middleName = if (it.middleName.isNullOrEmpty()) "" else " ${it.middleName}"
            val fullName = getString(R.string.user_full_name, it.firstname, middleName, it.lastname)
            binding.etName.setText(fullName)
            binding.etEmail.setText(it.email)
        }
    }

}
package com.mahmoud.altasherat.features.home.visa_requests.presentation

import androidx.fragment.app.viewModels
import com.mahmoud.altasherat.common.presentation.base.BaseFragment
import com.mahmoud.altasherat.databinding.FragmentRequestsBinding
import com.mahmoud.altasherat.features.home.visa_requests.presentation.adapters.TourismVisaRequestsAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RequestsFragment : BaseFragment<FragmentRequestsBinding>(FragmentRequestsBinding::inflate) {

    private val viewModel: TourismVisaRequestsViewModel by viewModels()
    private val adapter = TourismVisaRequestsAdapter{ clickItem ->
    }


    override fun FragmentRequestsBinding.initialize() {

        binding.visaRequestRecyclerView.adapter = adapter

        collectFlow(viewModel.state){ state ->

            when(state.screenState){
                is TourismVisaRequestsContract.TourismVisaRequestsScreenState.Loading -> {
                    showLoading()
                }
                else -> {
                    hideLoading()
                }
            }


            val requests = state.response

            adapter.submitList(requests)

        }

    }


}
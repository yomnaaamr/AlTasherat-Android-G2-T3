package com.mahmoud.altasherat.common.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewbinding.ViewBinding
import com.mahmoud.altasherat.common.domain.util.error.AltasheratError
import com.mahmoud.altasherat.common.domain.util.error.NetworkError
import com.mahmoud.altasherat.common.domain.util.error.ValidationError
import com.mahmoud.altasherat.common.presentation.base.delegators.IMessageDelegator
import com.mahmoud.altasherat.common.presentation.base.delegators.MessageDelegator
import com.mahmoud.altasherat.common.presentation.base.delegators.MessageType
import com.mahmoud.altasherat.common.presentation.LoadingDialog
import com.mahmoud.altasherat.common.presentation.utils.toErrorMessage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

abstract class BaseFragment<VB : ViewBinding>(private val inflateMethod: (LayoutInflater, ViewGroup?, Boolean) -> VB) :
    Fragment(), IMessageDelegator by MessageDelegator() {

    private var _binding: VB? = null
    val binding get() = _binding!!

    private var loadingDialog: LoadingDialog? = null

    open fun VB.initialize() {}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflateMethod.invoke(inflater, container, false)
        binding.initialize()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        loadingDialog?.dismiss()
        loadingDialog = null
        _binding = null
    }


    fun showLoading() {
        initLoadingDialog()
        loadingDialog?.show()
    }

    fun hideLoading() {
        loadingDialog?.dismiss()
    }

    private fun initLoadingDialog() {
        if (loadingDialog == null) {
            loadingDialog = LoadingDialog(requireContext())
        }
    }

    fun <T> collectFlow(
        flow: Flow<T>,
        state: Lifecycle.State = Lifecycle.State.STARTED,
        collector: suspend (T) -> Unit
    ) {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(state) {
                flow.collect(collector)
            }
        }
    }


    fun handleError(error: AltasheratError) {
        when (error) {
            is ValidationError -> {
                showMessage(error.toErrorMessage(requireContext()), MessageType.SNACKBAR, this)
            }

            is NetworkError -> {
                showMessage(error.toErrorMessage(requireContext()), MessageType.SNACKBAR, this)
            }

            else -> {
                showMessage(error.toErrorMessage(requireContext()), MessageType.SNACKBAR, this)
            }
        }
    }


}

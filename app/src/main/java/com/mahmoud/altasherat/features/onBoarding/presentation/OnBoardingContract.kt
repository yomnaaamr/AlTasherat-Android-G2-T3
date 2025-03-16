package com.mahmoud.altasherat.features.onBoarding.presentation

interface OnBoardingContract {
    data class OnBoardingState(
        val isLoading: Boolean,
        val exception: Exception?,
        val action: String?
    ) {
        companion object {
            fun initialState() = OnBoardingState(
                isLoading = false,
                exception = null,
                action = null
            )
        }
    }
}
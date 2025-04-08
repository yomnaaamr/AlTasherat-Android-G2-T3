package com.mahmoud.altasherat.features.authentication.reset_password.presentation

import android.graphics.Color
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import com.mahmoud.altasherat.common.presentation.base.BaseFragment
import com.mahmoud.altasherat.databinding.FragmentResetPassword2Binding


class ResetPassFragment2 :BaseFragment<FragmentResetPassword2Binding>(FragmentResetPassword2Binding::inflate)  {


    override fun FragmentResetPassword2Binding.initialize() {

        val digit1 = binding.digit1
        val digit2 = binding.digit2
        val digit3 = binding.digit3
        val digit4 = binding.digit4

        digit1.addTextChangedListener(DigitTextWatcher(digit1, digit2))
        digit2.addTextChangedListener(DigitTextWatcher(digit2, digit3))
        digit3.addTextChangedListener(DigitTextWatcher(digit3, digit4))
        digit4.addTextChangedListener(DigitTextWatcher(digit4, null))

    }


    inner class DigitTextWatcher(
        private val currentEditText: EditText,
        private val nextEditText: EditText?
    ) : TextWatcher {

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

        override fun afterTextChanged(s: Editable?) {
            if (s?.length == 1 && nextEditText != null) {
                nextEditText.requestFocus()
            }
//            currentEditText.setTextColor(Color.BLUE)
        }
    }
}
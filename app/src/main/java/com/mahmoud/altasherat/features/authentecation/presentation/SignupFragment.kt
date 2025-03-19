package com.mahmoud.altasherat.features.authentecation.presentation

import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.mahmoud.altasherat.R
import com.mahmoud.altasherat.databinding.FragmentSignupBinding
import java.util.Locale

class SignupFragment : Fragment() {

    private lateinit var binding: FragmentSignupBinding
    private lateinit var authViewModel: AuthViewModel



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSignupBinding.inflate(inflater, container, false)
        authViewModel = ViewModelProvider(requireActivity()).get(AuthViewModel::class.java)

        binding.signInTxt.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.auth_fragment_container, LoginFragment())
                .commit()

            authViewModel.switchToTab(1)

        }

        return binding.root
    }

}
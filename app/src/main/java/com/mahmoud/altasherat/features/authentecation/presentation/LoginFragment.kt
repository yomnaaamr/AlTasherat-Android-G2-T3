package com.mahmoud.altasherat.features.authentecation.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.mahmoud.altasherat.R
import com.mahmoud.altasherat.common.domain.models.Country
import com.mahmoud.altasherat.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var authViewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        authViewModel = ViewModelProvider(requireActivity()).get(AuthViewModel::class.java)

        //Use Original country list from local instead of this.
        val countryList = listOf(
            Country(1, "Saudi Arabia", "SAR", "sa", "00966", "🇸🇦"),
            Country(2, "Egypt", "EGP", "eg", "0020", "🇪🇬"),
            Country(3, "Afghanistan", "AFN", "af", "0093", "🇦🇫")
        )
        val countryDisplayList = countryList.map { "${it.flag} (${it.phoneCode})" }
        val adapter = ArrayAdapter(
            this.requireContext(),
            android.R.layout.simple_dropdown_item_1line,
            countryDisplayList
        )
        binding.phoneCodePicker.apply {
            setText(countryDisplayList[0], false)
            setOnClickListener {
                setAdapter(adapter)
                showDropDown()
            }
            //Handle item click
            setOnItemClickListener { parent, _, position, _ ->

                val selectedItem = parent.getItemAtPosition(position).toString()
                val selectedCountry = countryList[position]  // Get the selected country object

                setText(selectedItem, false)
            }

        }
        binding.createNewAccTxt.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.auth_fragment_container, SignupFragment())
                .commit()

            authViewModel.switchToTab(0)
        }
        return binding.root
    }

}
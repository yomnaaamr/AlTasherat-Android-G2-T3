package com.mahmoud.altasherat.features.authentication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.mahmoud.altasherat.R
import com.mahmoud.altasherat.databinding.FragmentAuthBinding
import com.mahmoud.altasherat.features.authentication.login.presentation.LoginFragment
import com.mahmoud.altasherat.features.authentication.signup.presentation.SignupFragment

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthFragment : Fragment() {

    private lateinit var binding: FragmentAuthBinding
    private lateinit var authViewmodel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentAuthBinding.inflate(inflater, container, false)
        authViewmodel = ViewModelProvider(requireActivity()).get(AuthViewModel::class.java)
        setupTab(binding.authTabLayout)

        parentFragmentManager.beginTransaction()
            .replace(R.id.auth_fragment_container, SignupFragment())
            .commit()

        updateTabSelection(binding.authTabLayout, 0)

        binding.authTabLayout.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {

                val fragment = when (tab?.position) {
                    0 -> SignupFragment()
                    1 -> LoginFragment()
                    else -> SignupFragment()
                }
                parentFragmentManager.beginTransaction()
                    .replace(R.id.auth_fragment_container, fragment)
                    .commit()
                updateTabSelection(binding.authTabLayout, tab?.position ?: 0)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabReselected(tab: TabLayout.Tab?) {}

        })
        authViewmodel.switchTabLiveData.observe(viewLifecycleOwner) { tabIndex ->
            binding.authTabLayout.getTabAt(tabIndex)?.select()
        }


        binding.skipTxt.setOnClickListener {
            findNavController().navigate(R.id.action_authFragment_to_menuFragment)
        }

        return binding.root
    }

    private fun setupTab(authTabLayout: TabLayout) {
        val tabTitles =
            listOf(resources.getString(R.string.signup), resources.getString(R.string.sign_in))

        for (i in tabTitles.indices) {
            val tab = authTabLayout.newTab()
            tab.customView = createCustomTabView(tabTitles[i])
            authTabLayout.addTab(tab)
        }

    }

    private fun createCustomTabView(title: String): View {
        val view =
            LayoutInflater.from(this.requireContext()).inflate(R.layout.custom_tab_item, null)
        val text = view.findViewById<TextView>(R.id.tab_text)
        text.text = title
        return view
    }

    fun updateTabSelection(tabLayout: TabLayout, selectedIndex: Int) {
        for (i in 0 until tabLayout.tabCount) {
            val tab = tabLayout.getTabAt(i)
            val view = tab?.customView
            val planeIcon = view?.findViewById<ImageView>(R.id.tab_image)
            val indicator = view?.findViewById<View>(R.id.tab_indicator)
            val text = view?.findViewById<TextView>(R.id.tab_text)

            if (i == selectedIndex) {
                planeIcon?.visibility = View.VISIBLE
                indicator?.visibility = View.VISIBLE
                text?.setTextColor(
                    ContextCompat.getColor(
                        this.requireContext(),
                        R.color.md_theme_onSurface
                    )
                )
            } else {
                planeIcon?.visibility = View.INVISIBLE
                indicator?.visibility = View.INVISIBLE
                text?.setTextColor(
                    ContextCompat.getColor(
                        this.requireContext(),
                        R.color.md_theme_outline
                    )
                )
            }
        }
    }

}
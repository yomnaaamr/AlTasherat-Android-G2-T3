package com.mahmoud.altasherat.common.presentation.adapters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mahmoud.altasherat.R
import com.mahmoud.altasherat.features.language_country.presentation.LanguageCountryViewModel
import com.mahmoud.altasherat.features.singleSelection.data.models.SingleItem
import com.mahmoud.altasherat.features.splash.domain.models.Country
import kotlinx.coroutines.launch

class CountryPickerBottomSheet(private val onCountrySelected: (SingleItem) -> Unit) :
    BottomSheetDialogFragment(), CountryPickerAdapter.OnItemClickListener {

    private lateinit var singleSelectionAdapter: CountryPickerAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.bottom_sheet_country, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.country_picker_recycler)
//        val countries = listOf("السعودية", "مصر", "أفغانستان", "ألبانيا", "الجزائر")

        singleSelectionAdapter = CountryPickerAdapter(emptyList(), this@CountryPickerBottomSheet)

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = singleSelectionAdapter





    }

    override fun onItemSelected(item: SingleItem) {
        onCountrySelected(item)
        Toast.makeText(context, item.name, Toast.LENGTH_SHORT).show()
        dismiss()
    }
}
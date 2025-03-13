package com.mahmoud.altasherat.common.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mahmoud.altasherat.R

class CountryPickerBottomSheet(private val onCountrySelected: (String) -> Unit) :
    BottomSheetDialogFragment(), OnItemClickListener {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.bottom_sheet_country, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.country_picker_recycler)
        val countries = listOf("السعودية", "مصر", "أفغانستان", "ألبانيا", "الجزائر")

        val adapter = CountryPickerAdapter(countries, this@CountryPickerBottomSheet)

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
    }

    override fun onItemSelected(item: String) {
        onCountrySelected(item)
        dismiss()
    }
}
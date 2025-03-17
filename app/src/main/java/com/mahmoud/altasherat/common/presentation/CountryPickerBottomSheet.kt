package com.mahmoud.altasherat.common.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mahmoud.altasherat.R
import com.mahmoud.altasherat.common.domain.models.Country
import com.mahmoud.altasherat.common.domain.models.ListItem

class CountryPickerBottomSheet(private val onCountrySelected: (ListItem) -> Unit) :
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
        val countries = listOf(
            Country(id = 1, name = "Saudi Arabia", currency = "SAR", code = "sa", phoneCode = "00966", flag = "🇸🇦"),
            Country(id = 2, name = "Egypt", currency = "EGP", code = "eg", phoneCode = "0020", flag = "🇪🇬"),
            Country(id = 3, name = "Afghanistan", currency = "AFN", code = "af", phoneCode = "0093", flag = "🇦🇫"),
            Country(id = 4, name = "Albania", currency = "ALL", code = "al", phoneCode = "00355", flag = "🇦🇱")
        )

        val adapter = SingleSelectionAdapter(countries, this@CountryPickerBottomSheet)

        val itemDecoration: RecyclerView.ItemDecoration =
            DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        recyclerView.addItemDecoration(itemDecoration)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
    }

    override fun onItemSelected(item: ListItem) {
        onCountrySelected(item)
        dismiss()
    }
}
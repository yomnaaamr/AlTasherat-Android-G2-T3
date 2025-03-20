package com.mahmoud.altasherat.common.presentation.adapters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mahmoud.altasherat.R
import com.mahmoud.altasherat.features.al_tashirat_services.language_country.domain.models.ListItem

class CountryPickerBottomSheet(
    private val list: List<ListItem>,
    private val onCountrySelected: (ListItem) -> Unit
) :
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

        val adapter = SingleSelectionAdapter(
            list,
            this@CountryPickerBottomSheet,

        )


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
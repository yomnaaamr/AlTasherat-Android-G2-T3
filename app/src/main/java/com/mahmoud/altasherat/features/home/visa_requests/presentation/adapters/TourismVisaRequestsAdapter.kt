package com.mahmoud.altasherat.features.home.visa_requests.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.graphics.toColorInt
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mahmoud.altasherat.R
import com.mahmoud.altasherat.databinding.ItemVisaRequestBinding
import com.mahmoud.altasherat.features.home.visa_requests.domain.models.TourismVisaRequest
import java.text.SimpleDateFormat
import java.util.Locale

class TourismVisaRequestsAdapter(
    private val onItemClick: (TourismVisaRequest) -> Unit
) : ListAdapter<TourismVisaRequest, TourismVisaRequestsAdapter.TourismVisaRequestsViewHolder>(
    TourismVisaRequestDiffCallback()
) {

    class TourismVisaRequestsViewHolder(private val binding: ItemVisaRequestBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val inputDateFormat =
            SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'", Locale.getDefault())
        private val outputDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

        fun bind(item: TourismVisaRequest, onItemClick: (TourismVisaRequest) -> Unit) {
            binding.apply {

                orderId.text = itemView.context.getString(R.string.request_id_format, item.id)

                val activeStatus = item.statuses.find { it.active }
                orderState.text = activeStatus?.name ?: ""

                activeStatus?.color?.let {
                    try {
                        orderState.setTextColor(it.toColorInt())
                    } catch (e: IllegalArgumentException) {
                        orderState.setTextColor(android.graphics.Color.BLACK)
                    }
                } ?: run {
                    orderState.setTextColor(android.graphics.Color.BLACK)
                }

                orderDate.text = formatDate(activeStatus?.activatedAt)
                personsNum.text = item.adultsCount.toString()
                childrenNum.text = item.childrenCount.toString()

                showOrder.setOnClickListener {
                    onItemClick(item)
                }
            }
        }

        private fun formatDate(dateString: String?): String =
            dateString?.let {
                try {
                    inputDateFormat.parse(it)?.let { date -> outputDateFormat.format(date) } ?: it
                } catch (e: Exception) {
                    it
                }
            } ?: ""
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TourismVisaRequestsViewHolder {
        val binding =
            ItemVisaRequestBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TourismVisaRequestsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TourismVisaRequestsViewHolder, position: Int) {
        holder.bind(getItem(position), onItemClick)
    }

    class TourismVisaRequestDiffCallback : DiffUtil.ItemCallback<TourismVisaRequest>() {
        override fun areItemsTheSame(
            oldItem: TourismVisaRequest,
            newItem: TourismVisaRequest
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: TourismVisaRequest,
            newItem: TourismVisaRequest
        ): Boolean {
            return oldItem == newItem
        }
    }
}
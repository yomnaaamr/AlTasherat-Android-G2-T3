package com.mahmoud.altasherat.features.home.visa_requests.presentation.adapters


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mahmoud.altasherat.databinding.ItemVisaRequestBinding
import com.mahmoud.altasherat.features.home.visa_requests.domain.models.TourismVisaRequest

class TourismVisaRequestsAdapter(
    private val onItemClick: (TourismVisaRequest) -> Unit
) : ListAdapter<TourismVisaRequest, TourismVisaRequestsAdapter.TourismVisaRequestsViewHolder>(TourismVisaRequestDiffCallback()) {

    class TourismVisaRequestsViewHolder(private val binding: ItemVisaRequestBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: TourismVisaRequest, onItemClick: (TourismVisaRequest) -> Unit) {
            binding.orderId.text = item.id.toString()
            binding.orderState.text = item.statuses.find { it.active }?.name ?: ""
            binding.orderDate.text = item.statuses.find { it.active }?.activatedAt
            binding.personsNum.text = item.adultsCount.toString()
            binding.childrenNum.text = item.childrenCount.toString()

            binding.showOrder.setOnClickListener {
                onItemClick(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TourismVisaRequestsViewHolder {
        val binding = ItemVisaRequestBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TourismVisaRequestsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TourismVisaRequestsViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem, onItemClick)
    }

    class TourismVisaRequestDiffCallback : DiffUtil.ItemCallback<TourismVisaRequest>() {
        override fun areItemsTheSame(oldItem: TourismVisaRequest, newItem: TourismVisaRequest): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: TourismVisaRequest, newItem: TourismVisaRequest): Boolean {
            return oldItem == newItem
        }
    }
}

//package com.mahmoud.altasherat.features.home.visa_requests.presentation.adapters
//
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.recyclerview.widget.RecyclerView
//import com.mahmoud.altasherat.R
//import com.mahmoud.altasherat.features.home.visa_requests.domain.models.TourismVisaRequest
//
//class TourismVisaRequestsAdapter(
//    private val requests: List<TourismVisaRequest>,
//    private val onItemClick: (TourismVisaRequest) -> Unit
//) : RecyclerView.Adapter<TourismVisaRequestsAdapter.TourismVisaRequestsViewHolder>() {
//
//
//    class TourismVisaRequestsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        val orderIdTextView: TextView = itemView.findViewById(R.id.order_id)
//        val orderStateTextView: TextView = itemView.findViewById(R.id.order_state)
//        val orderDateTextView: TextView = itemView.findViewById(R.id.order_date)
//        val personsNumTextView: TextView = itemView.findViewById(R.id.persons_num)
//        val childrenNumTextView: TextView = itemView.findViewById(R.id.children_num)
//        val showOrderImageView: ImageView = itemView.findViewById(R.id.show_order)
//
//    }
//
//    override fun onCreateViewHolder(
//        parent: ViewGroup,
//        viewType: Int
//    ): TourismVisaRequestsViewHolder {
//        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_visa_request, parent, false)
//        return TourismVisaRequestsViewHolder(itemView)
//    }
//
//    override fun getItemCount(): Int = requests.size
//
//    override fun onBindViewHolder(holder: TourismVisaRequestsViewHolder, position: Int) {
//
//        val currentItem = requests[position]
//        holder.orderIdTextView.text = currentItem.orderId
//        holder.orderStateTextView.text = currentItem.orderState
//        holder.orderDateTextView.text = currentItem.orderDate
//        holder.personsNumTextView.text = currentItem.personsNum.toString()
//        holder.childrenNumTextView.text = currentItem.childrenNum.toString()
//
//        // Set click listener for the "show order" image
//        holder.showOrderImageView.setOnClickListener {
//            // Handle click event (e.g., navigate to order details)
//        }
//    }
//}
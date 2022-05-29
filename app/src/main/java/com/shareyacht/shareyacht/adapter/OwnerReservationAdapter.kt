package com.shareyacht.shareyacht.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shareyacht.shareyacht.databinding.RvItemOwnerReservationBinding
import com.shareyacht.shareyacht.model.OwnerYachtReservation
import com.shareyacht.shareyacht.utils.API
import com.shareyacht.shareyacht.utils.formatter

/* [사업자] 예약현황 - 상태별 목록 - list adapter */
class OwnerReservationAdapter(private val clickListener: (reservation: OwnerYachtReservation) -> Unit) :
    ListAdapter<OwnerYachtReservation, OwnerReservationAdapter.OwnerReservationViewHolder>(
        RESERVATION_COMPARATOR
    ) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OwnerReservationViewHolder {
        val binding = RvItemOwnerReservationBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return OwnerReservationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OwnerReservationViewHolder, position: Int) {
        val currentItem = getItem(position)

        if (currentItem != null) {
            holder.bind(currentItem, clickListener)
        }
    }

    inner class OwnerReservationViewHolder(private val binding: RvItemOwnerReservationBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            reservation: OwnerYachtReservation,
            clickListener: (OwnerYachtReservation) -> Unit
        ) {

            itemView.setOnClickListener { clickListener(reservation) }
            val url = "${API.BASE_URL}/image/${reservation.yacht.imageid}"

            // 출항 시각, 입항 시각
            val start = if (reservation.departure.substring(15, 17).contains(":")) {
                reservation.departure.substring(15, 16).toInt()
            } else {
                reservation.departure.substring(15, 17).toInt()
            }

            val end = if (reservation.arrival.substring(15, 17).contains(":")) {
                reservation.arrival.substring(15, 16).toInt()
            } else {
                reservation.arrival.substring(15, 17).toInt()
            }
            val time = end - start
            // 금액 합계
            val totalPrice = reservation.yacht.price.toInt() * reservation.embarkCount * time

            binding.apply {
                yachtReservation = reservation
                price = formatter.format(totalPrice)

                Glide.with(itemView)
                    .load(url)
                    .centerCrop()
                    .into(yachtImage)
            }
        }
    }

    // COMPARATOR
    companion object {
        private val RESERVATION_COMPARATOR =
            object : DiffUtil.ItemCallback<OwnerYachtReservation>() {
                override fun areItemsTheSame(
                    oldItem: OwnerYachtReservation,
                    newItem: OwnerYachtReservation
                ): Boolean =
                    oldItem.id == newItem.id && oldItem.status == newItem.status

                override fun areContentsTheSame(
                    oldItem: OwnerYachtReservation,
                    newItem: OwnerYachtReservation
                ): Boolean =
                    oldItem == newItem
            }
    }
}

data class ClickListener(val clickListener: (reservation: OwnerYachtReservation) -> Unit)
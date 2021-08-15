package com.shareyacht.shareyacht.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shareyacht.shareyacht.databinding.RvItemReservationBinding
import com.shareyacht.shareyacht.model.YachtReservation
import com.shareyacht.shareyacht.utils.API
import com.shareyacht.shareyacht.utils.formatter

class ReservationListAdapter :
    ListAdapter<YachtReservation, ReservationListAdapter.ReservationViewHolder>(
        RESERVATION_COMPARATOR
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReservationViewHolder {
        val binding = RvItemReservationBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ReservationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReservationViewHolder, position: Int) {
        val currentItem = getItem(position)

        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }

    class ReservationViewHolder(private val binding: RvItemReservationBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(reservation: YachtReservation) {
            val url = "${API.BASE_URL}/image/${reservation.yacht.imageid}"

            val status = when (reservation.status) {
                0 -> "신청중"
                else -> ""
            }
            val start = reservation.departure.substring(15, 17).toInt()
            val end = reservation.arrival.substring(15, 17).toInt()
            Log.d("로그", "start : $start, end : $end")
            val time = end - start
            val totalPrice = reservation.yacht.price.toInt() * reservation.embarkCount * time

            binding.apply {
                yachtReservation = reservation
                reservationStatus = status
                price = formatter.format(totalPrice)

                Glide.with(itemView)
                    .load(url)
                    .centerCrop()
                    .into(yachtImage)

                itemView.setOnClickListener {
                    // 나중에
                }
            }
        }
    }

    companion object {
        private val RESERVATION_COMPARATOR = object : DiffUtil.ItemCallback<YachtReservation>() {
            override fun areItemsTheSame(
                oldItem: YachtReservation,
                newItem: YachtReservation
            ): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: YachtReservation,
                newItem: YachtReservation
            ): Boolean =
                oldItem == newItem
        }
    }

}
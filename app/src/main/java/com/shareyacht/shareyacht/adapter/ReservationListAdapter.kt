package com.shareyacht.shareyacht.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shareyacht.shareyacht.databinding.RvItemReservationBinding
import com.shareyacht.shareyacht.model.YachtReservation
import com.shareyacht.shareyacht.utils.Constants.STATE_CANCEL
import com.shareyacht.shareyacht.utils.Constants.STATE_COMPLETED
import com.shareyacht.shareyacht.utils.Constants.STATE_CONFIRMED
import com.shareyacht.shareyacht.utils.Constants.STATE_MOVING
import com.shareyacht.shareyacht.utils.Constants.STATE_WAIT
import com.shareyacht.shareyacht.utils.Constants.STATE_WAIT_DRIVER
import com.shareyacht.shareyacht.utils.formatter
import com.shareyacht.shareyacht.utils.getImageUrl

/* [일반] 예약내역 - 상태별 목록 - list adapter */
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
            val url = getImageUrl(reservation.yacht.imageid)

            val status = when (reservation.status) {
                STATE_WAIT -> "신청중"
                STATE_WAIT_DRIVER -> "운전자 매칭 대기"
                STATE_CONFIRMED -> "예약확정"
                STATE_MOVING -> "운항중"
                STATE_COMPLETED -> "운항완료"
                STATE_CANCEL -> "취소"
                else -> ""
            }
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

    // COMPARATOR
    companion object {
        private val RESERVATION_COMPARATOR = object : DiffUtil.ItemCallback<YachtReservation>() {
            override fun areItemsTheSame(
                oldItem: YachtReservation,
                newItem: YachtReservation
            ): Boolean =
                oldItem.id == newItem.id && oldItem.status == newItem.status

            override fun areContentsTheSame(
                oldItem: YachtReservation,
                newItem: YachtReservation
            ): Boolean =
                oldItem == newItem
        }
    }

}
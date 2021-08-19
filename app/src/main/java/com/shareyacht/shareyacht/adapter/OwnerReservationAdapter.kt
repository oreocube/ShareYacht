package com.shareyacht.shareyacht.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shareyacht.shareyacht.databinding.RvItemOwnerReservationBinding
import com.shareyacht.shareyacht.model.OwnerYachtReservation
import com.shareyacht.shareyacht.utils.API
import com.shareyacht.shareyacht.utils.Keyword
import com.shareyacht.shareyacht.utils.formatter
import com.shareyacht.shareyacht.view.owner.OwnerReservationDetailActivity

/* [사업자] 예약현황 - 상태별 목록 - list adapter */
class OwnerReservationAdapter :
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
            holder.bind(currentItem)
        }
    }

    class OwnerReservationViewHolder(private val binding: RvItemOwnerReservationBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(reservation: OwnerYachtReservation) {
            val url = "${API.BASE_URL}/image/${reservation.yacht.imageid}"

            // 출항 시각, 입항 시각
            val start = reservation.departure.substring(15, 17).toInt()
            val end = reservation.arrival.substring(15, 17).toInt()
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

                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, OwnerReservationDetailActivity::class.java)
                    intent.putExtra(Keyword.RESERVATION_ID, reservation.id)
                    startActivity(itemView.context, intent, null)
                }
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
                    oldItem.id == newItem.id

                override fun areContentsTheSame(
                    oldItem: OwnerYachtReservation,
                    newItem: OwnerYachtReservation
                ): Boolean =
                    oldItem == newItem
            }
    }

}
package com.shareyacht.shareyacht.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.shareyacht.shareyacht.model.OwnerYachtReservation

/* [사업자] 예약현황 - 상태별 목록 - list adapter */
class OwnerReservationAdapter :
    ListAdapter<OwnerYachtReservation, RecyclerView.ViewHolder>(RESERVATION_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        TODO()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        TODO("Not yet implemented")
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
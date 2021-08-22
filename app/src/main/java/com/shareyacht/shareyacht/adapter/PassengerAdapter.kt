package com.shareyacht.shareyacht.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.shareyacht.shareyacht.databinding.RvItemPassengerBinding
import com.shareyacht.shareyacht.model.Passenger

class PassengerAdapter : ListAdapter<Passenger, PassengerAdapter.ViewHolder>(COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            RvItemPassengerBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = getItem(position)

        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }


    class ViewHolder(private val binding: RvItemPassengerBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(passenger: Passenger) {
            binding.apply {
                this.passenger = passenger
            }
        }
    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<Passenger>() {
            override fun areItemsTheSame(oldItem: Passenger, newItem: Passenger): Boolean =
                oldItem.name == newItem.name

            override fun areContentsTheSame(oldItem: Passenger, newItem: Passenger): Boolean =
                oldItem == newItem
        }
    }
}
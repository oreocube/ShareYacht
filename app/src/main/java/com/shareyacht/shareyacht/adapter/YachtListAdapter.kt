package com.shareyacht.shareyacht.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shareyacht.shareyacht.databinding.RvItemBinding
import com.shareyacht.shareyacht.model.Yacht
import com.shareyacht.shareyacht.utils.API
import com.shareyacht.shareyacht.utils.Keyword
import com.shareyacht.shareyacht.view.normal.YachtDetailActivity

class YachtListAdapter : ListAdapter<Yacht, YachtListAdapter.YachtViewHolder>(YACHT_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): YachtViewHolder {
        val binding = RvItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return YachtViewHolder(binding)
    }

    override fun onBindViewHolder(holder: YachtViewHolder, position: Int) {
        val currentItem = getItem(position)

        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }

    class YachtViewHolder(private val binding: RvItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(yacht: Yacht) {
            val url = "${API.BASE_URL}/image/${yacht.imageid}"

            binding.apply {
                this.yacht = yacht

                Glide.with(itemView)
                    .load(url)
                    .centerCrop()
                    .into(imageView)

                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, YachtDetailActivity::class.java)
                    intent.putExtra(Keyword.YACHT_ID, yacht.id.toInt())
                    startActivity(itemView.context, intent, null)
                }
            }
        }
    }

    companion object {
        private val YACHT_COMPARATOR = object : DiffUtil.ItemCallback<Yacht>() {
            override fun areItemsTheSame(oldItem: Yacht, newItem: Yacht): Boolean =
                oldItem.number == newItem.number

            override fun areContentsTheSame(oldItem: Yacht, newItem: Yacht): Boolean =
                oldItem == newItem
        }
    }

}
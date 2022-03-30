package com.example.weather.screens.locationList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.weather.databinding.ListItemLocationBinding
import com.example.weather.room.Location

class LocationAdapter : ListAdapter<Location, RecyclerView.ViewHolder>(LocationDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return LocationViewHolder(
            ListItemLocationBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val location = getItem(position)
        (holder as LocationViewHolder).bind(location)
    }

    class LocationViewHolder(private val binding: ListItemLocationBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.setClickListener {
                //TODO
            }
        }

        fun bind(item: Location) {
            binding.apply {
                location = item
            }
        }
    }
}

private class LocationDiffCallback : DiffUtil.ItemCallback<Location>() {

    override fun areItemsTheSame(oldItem: Location, newItem: Location): Boolean {
        return oldItem.lat == newItem.lat && oldItem.lon == newItem.lon
    }

    override fun areContentsTheSame(oldItem: Location, newItem: Location): Boolean {
        return oldItem == newItem
    }
}

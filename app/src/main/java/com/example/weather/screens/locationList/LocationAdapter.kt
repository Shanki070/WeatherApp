package com.example.weather.screens.locationList

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.weather.databinding.ListItemLocationBinding
import com.example.weather.room.Location
import com.example.weather.screens.weatherDetail.WeatherDetailActivity

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
        fun bind(item: Location) {
            binding.apply {
                location = item
            }

            binding.setClickListener {
                Intent(it.context, WeatherDetailActivity::class.java).apply {
                    putExtra(WeatherDetailActivity.KEY_LAT, item.lat)
                    putExtra(WeatherDetailActivity.KEY_LON, item.lon)
                    it.context.startActivity(this)
                }
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

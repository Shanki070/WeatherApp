package com.example.weather.screens.weatherDetail

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.weather.databinding.ListHeaderWeatherDetailBinding
import com.example.weather.databinding.ListItemWeatherDetailBinding
import com.example.weather.room.Forecast
import com.example.weather.util.DateUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

const val ITEM_VIEW_TYPE_HEADER = 0
const val ITEM_VIEW_TYPE_ITEM = 1

class WeatherDetailAdapter:
    ListAdapter<DataItem, RecyclerView.ViewHolder>(ForecastDiffCallback()) {

    private val adapterScope = CoroutineScope(Dispatchers.Default)

    fun addHeaderAndSubmitList(list: List<Forecast>?) {
        adapterScope.launch {
            val finalList = ArrayList<DataItem>().toMutableList()
            list?.groupBy { DateUtil.getDateWithoutTime(it.date) }?.forEach { map ->
                finalList += (listOf(DataItem.Header(map.key)) + map.value.map { DataItem.ForecastItem(it) })
            }
            withContext(Dispatchers.Main) {
                submitList(finalList)
            }
        }
    }

    fun isItemPosition(position: Int) = getItem(position) is DataItem.ForecastItem

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ViewHolder -> {
                val forecastItem = getItem(position) as DataItem.ForecastItem
                holder.bind(forecastItem.forecast)
            }
            is TextViewHolder -> {
                val header = getItem(position) as DataItem.Header
                holder.bind(header.date)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_VIEW_TYPE_HEADER -> TextViewHolder.from(parent)
            ITEM_VIEW_TYPE_ITEM -> ViewHolder.from(parent)
            else -> throw ClassCastException("Unknown viewType ${viewType}")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is DataItem.Header -> ITEM_VIEW_TYPE_HEADER
            is DataItem.ForecastItem -> ITEM_VIEW_TYPE_ITEM
        }
    }

    class TextViewHolder(val binding: ListHeaderWeatherDetailBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(title: String) {
            binding.TextViewName.text = title
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): TextViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = ListHeaderWeatherDetailBinding.inflate(layoutInflater, parent, false)
                return TextViewHolder(view)
            }
        }
    }


    class ViewHolder private constructor(val binding: ListItemWeatherDetailBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(forecast: Forecast) {
            binding.forecast = forecast
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemWeatherDetailBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}

class ForecastDiffCallback : DiffUtil.ItemCallback<DataItem>() {
    override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return oldItem.id == newItem.id
    }
    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return oldItem == newItem
    }
}

sealed class DataItem {
    data class ForecastItem(val forecast: Forecast): DataItem() {
        override val id = forecast.id.toString()
    }

    class Header(val date: String) : DataItem() {
        override val id = date
    }

    abstract val id: String
}
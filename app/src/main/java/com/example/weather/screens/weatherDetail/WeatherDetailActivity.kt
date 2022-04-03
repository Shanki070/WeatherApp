package com.example.weather.screens.weatherDetail

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import com.example.weather.R
import com.example.weather.databinding.ActivityWeatherDetailsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class WeatherDetailActivity: AppCompatActivity() {

    private val viewModel by lazy {
        ViewModelProvider(this)[WeatherDetailViewModel::class.java]
    }

    private val binding: ActivityWeatherDetailsBinding by lazy {
        DataBindingUtil.setContentView(this, R.layout.activity_weather_details)
    }

    companion object {
        const val KEY_LAT = "LAT"
        const val KEY_LON = "LON"
    }

    var lat = 0.0
    var lon = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState != null) {
            lat = savedInstanceState.getDouble(KEY_LAT)
            lon = savedInstanceState.getDouble(KEY_LON)
        } else {
            intent.extras?.apply {
                lat =getDouble(KEY_LAT, 0.0)
                lon =getDouble(KEY_LAT, 0.0)
            }
        }

        val adapter = WeatherDetailAdapter()
        binding.RecyclerViewWeatherList.adapter = adapter

        lifecycleScope.launch(Dispatchers.Main) {
            viewModel.getForecast(lat, lon).observe(this@WeatherDetailActivity) { forecasts ->
                adapter.addHeaderAndSubmitList(forecasts)
            }
        }

        val layoutManager = GridLayoutManager(this@WeatherDetailActivity, 3)
        layoutManager.spanSizeLookup = object : SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when (adapter.getItemViewType(position)) {
                    ITEM_VIEW_TYPE_HEADER -> 3
                    else -> 1
                }
            }
        }
        binding.RecyclerViewWeatherList.layoutManager = layoutManager
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        outState.putDouble(KEY_LAT, lat)
        outState.putDouble(KEY_LON, lon)
    }
}

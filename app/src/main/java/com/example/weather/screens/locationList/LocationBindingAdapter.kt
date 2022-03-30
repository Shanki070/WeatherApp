package com.example.weather.screens.locationList

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.weather.room.Location

@BindingAdapter("locationName")
fun bindImageFromUrl(view: TextView, location: Location?) {
    location?.apply {
        val name = StringBuilder(name)
        state?.apply { name.append(", $this")  }
        name.append(", $country")
        view.text = name
    }
}
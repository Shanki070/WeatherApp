package com.example.weather.screens.locationList

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter


@BindingAdapter("weatherIcon")
fun bindImageFromUrl(imageView: ImageView, iconName: String ) {
    val uri = "@drawable/d_$iconName"
    val imageResource: Int = imageView.resources.getIdentifier(uri, null, imageView.context.packageName)

    val res: Drawable = imageView.resources.getDrawable(imageResource)
    imageView.setImageDrawable(res)
}
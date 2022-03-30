package com.example.weather.screens.locationList

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weather.R
import com.example.weather.databinding.ActivityLocationListBinding
import com.example.weather.screens.addLocation.AddLocationActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LocationListActivity: AppCompatActivity() {

    private val adapter = LocationAdapter()

    private val viewModel by lazy {
        ViewModelProvider(this)[LocationListViewModel::class.java]
    }

    private val binding: ActivityLocationListBinding by lazy {
        DataBindingUtil.setContentView(this, R.layout.activity_location_list)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.RecyclerViewLocationList.adapter = adapter
        binding.RecyclerViewLocationList.layoutManager = LinearLayoutManager(this)
        viewModel.dBLocationList.observe(this) { locations ->
            adapter.submitList(locations)

            //handle empty list case
            binding.ButtonAddLocation.visibility = if (locations?.isNotEmpty() == true) View.GONE else View.VISIBLE
        }

        binding.ButtonAddLocation.setOnClickListener {
            binding.FloatingActionButtonAddLocation.performClick()
        }

        binding.FloatingActionButtonAddLocation.setOnClickListener {
            startActivity(Intent(this, AddLocationActivity::class.java))
        }
    }
}

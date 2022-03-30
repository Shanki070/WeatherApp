package com.example.weather.screens.addLocation

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.weather.R
import com.example.weather.databinding.ActivityAddLocationBinding
import com.example.weather.room.Location
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AddLocationActivity : AppCompatActivity() {

    private val viewModel: AddLocationViewModel by lazy {
        ViewModelProvider(this)[AddLocationViewModel::class.java]
    }

    private val binding: ActivityAddLocationBinding by lazy {
        DataBindingUtil.setContentView(this, R.layout.activity_add_location)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_location)
        renderSelectedLocation(viewModel.location)
        renderLocationList()
        binding.ButtonSaveLocation.setOnClickListener {
            lifecycleScope.launch {
                viewModel.location?.apply {
                    viewModel.insertLocation(this)
                } ?: run {
                    //validation
                    Toast.makeText(this@AddLocationActivity, R.string.select_location, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun renderSelectedLocation(location: Location?) {
        binding.TextViewCity.text = location?.name ?: "--"
        binding.TextViewState.text = location?.state ?: "--"
        binding.TextViewCountry.text = location?.country ?: "--"
    }

    private fun renderLocationList() {
        var locations: List<Location>? = null

        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(this,
            android.R.layout.simple_dropdown_item_1line)
        adapter.setNotifyOnChange(true)
        val textView = binding.AppCompatAutoCompleteTextViewLocation
        textView.setAdapter(adapter)
        textView.threshold = 1
        textView.setOnItemClickListener { _, _, position, _ ->
            locations?.get(position)?.apply {
                viewModel.location = this //to survive activity-recreation
                renderSelectedLocation(this)
            }
        }

        textView.addTextChangedListener {
            if (it?.toString()?.isNotEmpty() == true) {
                lifecycleScope.launch(Dispatchers.Main) {
                    locations = viewModel.searchLocation(it.toString())
                    locations?.map {
                        var address = it.name
                        if (it.state?.isNotEmpty() == true) address += ", ${it.state}"
                        address += ", ${it.country}"
                        address
                    }?.apply {
                        adapter.clear()
                        adapter.addAll(this)
                    } ?: adapter.clear()
                }
            } else {
                adapter.clear()
            }
        }
    }
}
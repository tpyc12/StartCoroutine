package com.myhome.android.startcoroutine

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.myhome.android.startcoroutine.databinding.ActivityMainBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.buttonLoad.setOnClickListener {
            lifecycleScope.launch {
                loadData()
            }
        }
    }

    private suspend fun loadData() {
        binding.progress.isVisible = true
        binding.buttonLoad.isEnabled = false
        val city = loadCity()
        binding.tvLocation.text = city
        val temperature = loadTemperature(city)
        binding.tvTemperature.text = temperature.toString()
        binding.progress.isVisible = false
        binding.buttonLoad.isEnabled = true
    }

    private suspend fun loadTemperature(city: String): Int {
        Toast.makeText(
            this,
            "Loading temperature for city:$city",
            Toast.LENGTH_SHORT
        ).show()
        delay(5000)
        return 17
    }

    private suspend fun loadCity(): String {
        delay(5000)
        return "Moscow"
    }
}
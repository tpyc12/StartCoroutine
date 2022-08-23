package com.myhome.android.startcoroutine

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.myhome.android.startcoroutine.databinding.ActivityMainBinding
import kotlinx.coroutines.delay

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.buttonLoad.setOnClickListener {
//            lifecycleScope.launch {
//                loadData()
//            }
            loadWithoutCoroutine()
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

    private fun loadWithoutCoroutine(step: Int = 0, obj: Any? = null) {
        when (step) {
            0 -> {
                binding.progress.isVisible = true
                binding.buttonLoad.isEnabled = false
                loadCityWithoutCoroutine {
                    loadWithoutCoroutine(1, it)
                }
            }
            1 -> {
                val city = obj as String
                binding.tvLocation.text = city
                loadTemperatureWithoutCoroutine(city) {
                    loadWithoutCoroutine(2, it)
                }
            }
            2 -> {
                val temperature = obj as Int
                binding.tvTemperature.text = temperature.toString()
                binding.progress.isVisible = false
                binding.buttonLoad.isEnabled = true
            }
        }
    }

    private fun loadCityWithoutCoroutine(callback: (String) -> Unit) {
        Handler(Looper.getMainLooper()).postDelayed({
            callback("Moscow")
        }, 5000)
    }

    private fun loadTemperatureWithoutCoroutine(city: String, callback: (Int) -> Unit) {
        Toast.makeText(
            this,
            "Loading temperature for city:$city",
            Toast.LENGTH_SHORT
        ).show()
        Handler(Looper.getMainLooper()).postDelayed({
            callback(17)
        }, 5000)
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
package com.myhome.android.startcoroutine

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    fun method() {
        val job = viewModelScope.launch(Dispatchers.Default) {
            Log.d(LOG_TAG, "Started")
            val begin = System.currentTimeMillis()
            var count = 0
            for (i in 0 until 100_000_000) {
                for (j in 0 until 10) {
                    ensureActive()
                    count++
                }
            }
            Log.d(LOG_TAG, "Finished ${System.currentTimeMillis() - begin}")
        }
        job.invokeOnCompletion {
            Log.d(LOG_TAG, "Coroutine was canceled, $it")
        }
        viewModelScope.launch {
            delay(3000)
            job.cancel()
        }
    }

    companion object {

        private const val LOG_TAG = "MainViewModel"
    }
}
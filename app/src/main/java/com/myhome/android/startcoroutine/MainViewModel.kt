package com.myhome.android.startcoroutine

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*

class MainViewModel: ViewModel() {

    private val parentJob = Job()
    private val exceptionHandler = CoroutineExceptionHandler{ _, throwable ->
        Log.d(LOG_TAG, "Exception catch: $throwable")
    }
    private val coroutineScope = CoroutineScope(Dispatchers.Main + parentJob + exceptionHandler)

    fun method(){
        val childJob1 = coroutineScope.launch {
            delay(3000)
            Log.d(LOG_TAG,  "first coroutine finished")
        }
        val childJob2 = coroutineScope.launch {
            delay(2000)
            Log.d(LOG_TAG,  "second coroutine finished")
        }
        val childJob3 = coroutineScope.async {
            delay(1000)
                error()
            Log.d(LOG_TAG, "third coroutine finished")
        }
    }

    private fun error(){
        throw RuntimeException()
    }

    override fun onCleared() {
        super.onCleared()
        coroutineScope.cancel()
    }

    companion object{

        private const val LOG_TAG = "MainViewModel"
    }
}
package com.myhome.android.startcoroutine

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import kotlin.concurrent.thread

class MainViewModel: ViewModel() {

    private val parentJob = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.Main + parentJob)

    fun method(){
        val childJob1 = coroutineScope.launch {
            delay(3000)
            Log.d(LOG_TAG,  "first coroutine finished")
        }
        val childJob2 = coroutineScope.launch {
            delay(2000)
            Log.d(LOG_TAG,  "second coroutine finished")
        }
        thread {
            Thread.sleep(1000)
            parentJob.cancel()
            Log.d(LOG_TAG, "Parent job is active: ${parentJob.isActive}")
        }
        Log.d(LOG_TAG, parentJob.children.contains(childJob1).toString())
        Log.d(LOG_TAG, parentJob.children.contains(childJob2).toString())
    }

    override fun onCleared() {
        super.onCleared()
        coroutineScope.cancel()
    }

    companion object{

        private const val LOG_TAG = "MainViewModel"
    }
}
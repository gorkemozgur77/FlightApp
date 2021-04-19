package com.gorkemozgur.flightapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

open class BaseViewModel(application: Application) : AndroidViewModel(application), CoroutineScope {

    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main


    var dataUpdateTime = 10 * 10 * 1000 * 1000 * 1000L

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}
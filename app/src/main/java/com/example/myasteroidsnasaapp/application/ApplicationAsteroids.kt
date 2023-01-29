package com.example.myasteroidsnasaapp.application

import android.app.Application
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class ApplicationAsteroids : Application() {

    private val applicationScope = CoroutineScope(Dispatchers.Default)

    override fun onCreate() {
        super.onCreate()
        delayUnit()
    }

    private fun delayUnit(){
        applicationScope.launch {
            setupRecurringWork()
        }

    }

    private fun setupRecurringWork() {
        //Wifi and Charging is Enabled :
        val constraints = Constraints.Builder().setRequiredNetworkType(NetworkType.UNMETERED).
        setRequiresCharging(true).
        build()

        val repeatingRequest =
            PeriodicWorkRequest.Builder(Worker::class.java, 1, TimeUnit.DAYS)
                .setConstraints(constraints).build()
        WorkManager.getInstance(this).enqueue(repeatingRequest)
    }
}
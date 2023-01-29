package com.example.myasteroidsnasaapp.application

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.myasteroidsnasaapp.models.getNextSevenDaysFormattedDates
import com.example.myasteroidsnasaapp.repository.RoomRepository
import com.example.myasteroidsnasaapp.roomDatabase.RoomDatabaseClass
import com.example.myasteroidsnasaapp.ui.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.text.SimpleDateFormat
import java.util.*

class Worker (appContext: Context, params: WorkerParameters) :
    CoroutineWorker(appContext, params){
    override suspend fun doWork(): Result {

        val db = RoomDatabaseClass.getInstance(applicationContext)
        val asteroidDao = db.asteroidDao()
        val repository = RoomRepository(asteroidDao)
        val startDay = getNextSevenDaysFormattedDates()[0]
        val endDate = getNextSevenDaysFormattedDates()[7]

        //previous day :
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, -1)
        val previousDayTime = calendar.time
        val dateFormat = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            SimpleDateFormat(Constants.API_QUERY_DATE_FORMAT, Locale.getDefault())
        } else {
            TODO("VERSION.SDK_INT < N")
        }
        val previousDayDate = dateFormat.format(previousDayTime)

        // here i catch the data that come using fun from repository
        repository.getDataAndPutItIntoRoomDBByDao(startDay, endDate, Constants.API_KEY)

        //To delete previous date :
        asteroidDao.deletePreviousDayAsteroids(previousDayDate)

        return try {
            Result.success()
        } catch (E: HttpException) {
            Result.retry()
        }
    }
}
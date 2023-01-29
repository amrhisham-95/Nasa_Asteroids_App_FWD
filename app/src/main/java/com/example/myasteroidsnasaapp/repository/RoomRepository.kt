package com.example.myasteroidsnasaapp.repository

import androidx.lifecycle.LiveData
import com.example.myasteroidsnasaapp.models.*
import com.example.myasteroidsnasaapp.roomDatabase.Dao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject


class RoomRepository(private val asteroidDao: Dao) {

    val readAllAsteroidsDetails: LiveData<List<AsteroidData>> = asteroidDao.readAllAsteroidDetails()

    // To get asteroid for a week :
    val getWeekAsteroid: LiveData<List<AsteroidData>> = asteroidDao.getWeekAsteroids(
        getNextSevenDaysFormattedDates()[0], getNextSevenDaysFormattedDates()[6]
    )

    // To get asteroid for Today :
    val getTodayAsteroid: LiveData<List<AsteroidData>> = asteroidDao.getTodayAsteroids(
        getNextSevenDaysFormattedDates()[0]
    )


    //suspend fun To add asteroid and use it in view model scope in (Main view model):
    suspend fun addAsteroidDetails(asteroid: List<AsteroidData>) {
        asteroidDao.addAsteroidDetails(asteroid)
    }

    //suspend fun To update asteroid and use it in view model scope in (Main view model):
    suspend fun updateAsteroidDetails(asteroid: List<AsteroidData>) {
        asteroidDao.updateAsteroidDetails(asteroid)
    }

    //suspend fun To delete asteroid and use it in view model scope in (Main view model):
    suspend fun deleteAsteroidDetails(asteroid: List<AsteroidData>) {
        asteroidDao.deleteAsteroidDetails(asteroid)
    }

    //suspend fun To delete all asteroid and use it in view model scope in (Main view model):
    suspend fun deleteAllAsteroidDetails() {
        asteroidDao.deleteAllAsteroidDetails()
    }

    //to parse and insert data to database by dao between string data to list of asteroid data (with room data base)
    suspend fun getDataAndPutItIntoRoomDBByDao(startDate: String, endDate: String, apiKey: String) {
            val resultTypeString = AsteroidObjectApi.retrofitAsteroidService.getAsteroidDetails(startDate,endDate,apiKey)
            asteroidDao.addAsteroidDetails(parseAsteroidsJsonResult(JSONObject(resultTypeString)))

    }

}
